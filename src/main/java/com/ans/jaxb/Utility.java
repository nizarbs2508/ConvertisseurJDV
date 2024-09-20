package com.ans.jaxb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

/**
 * Utility
 * 
 * @author bensalem Nizar
 */
public class Utility implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4413382614891584341L;
	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(Utility.class);

	/**
	 * load task api
	 */
	public static final void runTask(final Stage taskUpdateStage, final ProgressIndicator progress) {
		final Task<Void> longTask = new Task<>() {
			@Override
			protected Void call() throws Exception {
				final int max = 20;
				for (int i = 1; i <= max; i++) {
					if (isCancelled()) {
						break;
					}
					updateProgress(i, max);
					updateMessage(Constant.TASK_PART + i + Constant.COMPLETE);
				}
				return null;
			}
		};
		longTask.setOnSucceeded(new EventHandler<>() {
			@Override
			public void handle(final WorkerStateEvent event) {
				taskUpdateStage.hide();
			}
		});
		progress.progressProperty().bind(longTask.progressProperty());
		taskUpdateStage.show();
		new Thread(longTask).start();
	}

	/**
	 * createDemo
	 * 
	 * @param file
	 */
	public static final void createDemo(final File file) {
		final StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(Constant.APPEND1).append(Constant.APPEND2).append(Constant.APPEND3).append(Constant.APPEND4)
				.append(Constant.APPEND5).append(Constant.APPEND6).append(Constant.APPEND7).append(Constant.APPEND8)
				.append(Constant.APPEND9).append(Constant.APPEND10).append(Constant.APPEND11);
		try {
			Files.write(file.toPath(), sBuilder.toString().getBytes());
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
	}

	/**
	 * typeIndex
	 * 
	 * @param t
	 * @return
	 */
	public static final int typeIndex(final int type) {
		final int retVal;
		if (type == Node.ELEMENT_NODE) {
			retVal = 1;
		} else if (type == Node.ATTRIBUTE_NODE) {
			retVal = 2;
		} else if (type == Node.TEXT_NODE) {
			retVal = 3;
		} else if (type == Node.CDATA_SECTION_NODE) {
			retVal = 4;
		} else if (type == Node.PROCESSING_INSTRUCTION_NODE) {
			retVal = 5;
		} else if (type == Node.COMMENT_NODE) {
			retVal = 6;
		} else if (type == Node.DOCUMENT_NODE) {
			retVal = 7;
		} else if (type == Node.DOCUMENT_TYPE_NODE) {
			retVal = 8;
		} else {
			retVal = 0;
		}
		return retVal;

	}

	/**
	 * createNewZip
	 * 
	 * @param destDir
	 * @param zipEntry
	 */
	private static void createNewZip(final String destDir, final ZipEntry zipEntry) {
		if (new File(destDir, zipEntry.getName()).exists()) {
			new File(destDir, zipEntry.getName()).delete();
		}
	}

	/**
	 * gettoken
	 * 
	 * @param tokenopen
	 * @param user
	 * @param pwd
	 * @return
	 */
	public static final String getFirstToken(final String tokenopen, final String user, final String pwd) {
		final String tokenUrl = tokenopen;
		final String data = "grant_type=password&username=" + user.trim() + "&password=" + pwd.trim() + "&client_id="
				+ Constant.CLIENTID + "&client_secret= ";
		String responseJs = null;
		try {
			final URL url = new URL(tokenUrl);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			getOutputStream(connection).write(data.getBytes());
			final int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (InputStream inputStream = getInputStream(connection)) {
					final byte[] responseBytes = inputStream.readAllBytes();
					final String response = new String(responseBytes);
					final ObjectMapper mapper = new ObjectMapper();
					final Map<String, Object> responseJson = mapper.readValue(response, new TypeReference<>() {
					});
					responseJs = (String) responseJson.get("access_token");
				}
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
	 * createNewFile
	 * 
	 * @param localFilename
	 */
	public static void createNewFile(final String localFilename) {
		if (new File(localFilename).exists()) {
			new File(localFilename).delete();
		}
	}

	/**
	 * writeFileContent
	 * 
	 * @param newFile
	 * @throws IOException
	 */
	private static void writeFileContent(final File newFile, final ZipInputStream zis, final byte[] buffer)
			throws IOException {
		final File parent = newFile.getParentFile();
		if (!parent.isDirectory() && !parent.mkdirs()) {
			throw new IOException("Failed to create directory " + parent);
		}
		// write file content
		try (OutputStream fileOut = Files.newOutputStream(Paths.get(newFile.toURI()))) {
			int len = zis.read(buffer);
			while (len > 0) {
				fileOut.write(buffer, 0, len);
				len = zis.read(buffer);
			}
		}
	}

	/**
	 * unzipFile
	 * 
	 * @param fileZip
	 * @param destDir
	 * @return
	 * @throws IOException
	 */
	public static File unzipFile(final String fileZip, final File destDir) throws IOException {
		final byte[] buffer = new byte[1024];
		try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(Paths.get(fileZip)))) {
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				createNewZip(fileZip, zipEntry);
				final String name = destDir.getAbsolutePath() + '\\' + zipEntry.getName();
				final File newFile = getFile(name);
				if (zipEntry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile);
					}
				} else {
					writeFileContent(newFile, zis, buffer);
				}
				zipEntry = zis.getNextEntry();
			}

		}
		return destDir;
	}

	/**
	 * getOutputStream
	 * 
	 * @param connection
	 * @throws IOException
	 */
	public static OutputStream getOutputStream(final HttpURLConnection connection) throws IOException {
		return connection.getOutputStream();
	}

	/**
	 * getInputStream
	 * 
	 * @param connection
	 * @throws IOException
	 */
	public static InputStream getInputStream(final HttpURLConnection connection) throws IOException {
		return connection.getInputStream();
	}

	/**
	 * newListDPaneClass
	 * 
	 * @param dialogPane
	 */
	public static ObservableList<String> newListDPaneClass(final DialogPane dialogPane) {
		return dialogPane.getStyleClass();
	}

	/**
	 * stringFromZip
	 * 
	 * @param zipFilename
	 */
	public static String stringFromZip(final String zipFilename) {
		return new File(zipFilename).getName();
	}

	/**
	 * getFile
	 * 
	 * @param name
	 */
	public static File getFile(final String name) {
		return new File(name);
	}
}
