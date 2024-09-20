package com.ans.jaxb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

/**
 * TerminologyDownloader
 * 
 * @author bensalem Nizar
 */
public class TerminologyDownloader implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8948623175751727533L;
	/**
	 * username
	 */
	private static String username = "";
	/**
	 * password
	 */
	private static String password = "";
	/**
	 * newFile
	 */
	private static File newFile;
	/**
	 * tokenurl
	 */
	private String tokenurl;
	/**
	 * downloadurl
	 */
	private String downloadurl;
	/**
	 * tokenopen
	 */
	private String tokenopen;
	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(TerminologyDownloader.class);

	/**
	 * constructor
	 * 
	 * @param tokenurl
	 * @param downloadurl
	 * @param tokenopen
	 */
	public TerminologyDownloader(final String tokenurl, final String downloadurl, final String tokenopen) {
		this.tokenurl = tokenurl;
		this.downloadurl = downloadurl;
		this.tokenopen = tokenopen;

	}

	/**
	 * constructor
	 */
	public TerminologyDownloader() {
		// default constructor
	}

	/**
	 * main principale
	 * 
	 * @param textLogin, textPwd, listTerminology, map, tokenurl, downloadurl
	 * @param tokenopen
	 * @return
	 * @throws IOException
	 */
	public List<File> main(final Terminology terminology) {
		this.tokenurl = terminology.getTokenurl();
		this.downloadurl = terminology.getDownloadurl();
		this.tokenopen = terminology.getTokenopen();
		username = terminology.getTextLogin().trim();
		password = terminology.getTextPwd().trim();
		final List<String> terminologyIdList = new ArrayList<>();
		if (terminology.getMap() != null && !terminology.getMap().isEmpty()) {
			for (@SuppressWarnings("rawtypes")
			final Map.Entry mapentry : terminology.getMap().entrySet()) {
				final String key = (String) mapentry.getKey();
				final String value = (String) mapentry.getValue();
				for (final String termino : terminology.getListTerminology()) {
					if (termino.equalsIgnoreCase(key)) {
						terminologyIdList.add(value);
					}
				}
			}
		}
		final String outputDir = terminology.getOutputD();
		if (!new File(outputDir).exists()) {
			new File(outputDir).mkdir();
		}
		return getListTermino(terminologyIdList, outputDir);
	}

	/**
	 * getListTerminology
	 * 
	 * @param zipFilename   @param outputDir
	 * @param filename      @param str1
	 * @param latestVersion @param list
	 * @return list
	 */
	private List<File> getListTerminology(final String outputDir, final String str1, final String latestVersion,
			final List<File> list) {
		final String localFilename = outputDir + str1.trim() + "_" + latestVersion + Constant.EXTENSIONZIP;
		Utility.createNewFile(localFilename);
		final String zipFilename = downloadZip(str1.trim(), latestVersion, outputDir);
		try {
			if (zipFilename != null) {
				final String str = Utility.stringFromZip(zipFilename);
				final int dotIndex = str.lastIndexOf('.');
				final String filename = str.substring(0, dotIndex);
				final File file = Utility.unzipFile(newZipName(zipFilename), newFileName(outputDir, filename));
				findFile(file);
				list.add(newFile);
			}
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
		return list;
	}

	/**
	 * getListTermino
	 * 
	 * @param terminologyIdList
	 * @param outputDir
	 * @return list
	 */
	private List<File> getListTermino(final List<String> terminologyIdList, final String outputDir) {
		final List<File> list = new ArrayList<>();
		for (final String str1 : terminologyIdList) {
			final String latestVersion = getLatestVersionDetails(str1.trim());
			if (latestVersion == null) {
				final String str2 = str1.trim();
				if (LOG.isInfoEnabled()) {
					final String error = "No zip file available for " + str2 + " or failed to extract RDF.";
					LOG.error(error);
				}
			} else {
				getListTerminology(outputDir, str1, latestVersion, list);
			}
		}
		return list;
	}

	/**
	 * findFile
	 * 
	 * @param file
	 */
	private static void findFile(final File file) {
		final File[] list = file.listFiles();
		if (list != null) {
			for (final File fil : list) {
				if (fil.isDirectory() && Constant.DAT.equals(fil.getName())) {
					findFile(fil);
				} else if (fil.getName().endsWith(Constant.EXTRDF)) {
					newFile = fil;
				}
			}
		}
	}

	/**
	 * getLatestVersionDetails
	 * 
	 * @param terminologyId
	 * @return
	 */
	private String getLatestVersionDetails(final String terminologyId) {
		final String token = getToken();
		final String tokenUrl = this.tokenurl + terminologyId;
		String lastVersion = null;
		try {
			final URL url = new URL(tokenUrl);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer " + token);

			final int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (InputStream inputStream = Utility.getInputStream(connection)) {
					final byte[] responseBytes = inputStream.readAllBytes();
					final String response = new String(responseBytes);
					final ObjectMapper mapper = new ObjectMapper();
					final List<Map<String, Object>> versions = mapper.readValue(response, new TypeReference<>() {
					});
					if (!versions.isEmpty()) {
						final Map<String, Object> latestVersion = versions.stream()
								.max((v1, v2) -> LocalDateTime
										.parse((String) v1.get(Constant.PUBLISHEDDATE), DateTimeFormatter.ISO_DATE_TIME)
										.compareTo(LocalDateTime.parse((String) v2.get(Constant.PUBLISHEDDATE),
												DateTimeFormatter.ISO_DATE_TIME)))
								.orElse(null);
						if (latestVersion != null) {
							lastVersion = (String) latestVersion.get(Constant.VERSIONINFO);
						}
					}
				}
			}

			else {
				if (LOG.isInfoEnabled()) {
					final String error = "No versions found for " + terminologyId;
					LOG.error(error);
				}
			}
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = "An error occurred while fetching version details for " + terminologyId + ": "
						+ e.getMessage();
				LOG.error(error);
			}
		}
		return lastVersion;
	}

	/**
	 * downloadZip
	 * 
	 * @param terminologyId
	 * @param version
	 * @return
	 */
	private String downloadZip(final String terminologyId, final String version, final String outputD) {
		final String token = getToken();
		String localFile = null;
		try {
			final String encodedVersion = URLEncoder.encode(version, StandardCharsets.UTF_8.toString());
			final String downloadUrl = this.downloadurl + terminologyId + "&version=" + encodedVersion
					+ "&licenceConsent=true&dataTransferConsent=true";
			final String localFilename = outputD + terminologyId + "_" + version + ".zip";
			final URL url = new URL(downloadUrl);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer " + token);
			final int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (InputStream inputStream = connection.getInputStream()) {
					Files.copy(inputStream, Path.of(localFilename), StandardCopyOption.REPLACE_EXISTING);
					localFile = localFilename;
				}
			} else {
				if (LOG.isInfoEnabled()) {
					final String error = "An error occurred while downloading zip for " + terminologyId + ": "
							+ responseCode;
					LOG.error(error);
				}
			}
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = "An error occurred while downloading zip for " + terminologyId + ": "
						+ e.getMessage();
				LOG.error(error);
			}
		}
		return localFile;
	}

	/**
	 * getToken
	 * 
	 * @return
	 */
	private String getToken() {
		final String tokenUrl = this.tokenopen;
		final String data = "grant_type=password&username=" + username + "&password=" + password + "&client_id="
				+ Constant.CLIENTID + "&client_secret= ";
		String responseJs = null;
		try {
			final URL url = new URL(tokenUrl);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			Utility.getOutputStream(connection).write(data.getBytes());

			final int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (InputStream inputStream = Utility.getInputStream(connection)) {
					final byte[] responseBytes = inputStream.readAllBytes();
					final String response = new String(responseBytes);
					final ObjectMapper mapper = new ObjectMapper();
					final Map<String, Object> responseJson = mapper.readValue(response, new TypeReference<>() {
					});
					responseJs = (String) responseJson.get("access_token");
				}
			} else {
				if (LOG.isInfoEnabled()) {
					final String error = "An error occurred while getting token: " + responseCode;
					LOG.error(error);
				}
				final Alert alert = new Alert(AlertType.ERROR);
				final DialogPane dialogPane = alert.getDialogPane();
				newObsListDPane(dialogPane).add(getClass().getResource(Constant.STYLE).toExternalForm());
				Utility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
				dialogPane.setMinHeight(130);
				dialogPane.setMaxHeight(130);
				dialogPane.setPrefHeight(130);
				alert.setContentText(INUtility.get("button.edit.text"));
				alert.setHeaderText(null);
				alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
				alert.showAndWait();
			}
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = "An error occurred while getting token: " + e.getMessage();
				LOG.error(error);
			}
		}
		return responseJs;
	}

	/**
	 * newFileName
	 * 
	 * @param outputDir
	 * @param filename
	 */
	public static File newFileName(final String outputDir, final String filename) {
		return new File(outputDir + filename);
	}

	/**
	 * newZipName
	 * 
	 * @param zipFilename
	 */
	public static String newZipName(final String zipFilename) {
		return new File(zipFilename).getAbsolutePath();
	}

	/**
	 * newObsListDPane
	 * 
	 * @param dialogPane
	 */
	public static ObservableList<String> newObsListDPane(final DialogPane dialogPane) {
		return dialogPane.getStylesheets();
	}

}