package com.ans.jaxb;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.IndexedCheckModel;

import com.spire.xls.Worksheet;
import com.spire.xls.collections.WorksheetsCollection;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FileUtility
 * 
 * @author bensalem Nizar
 */
public class FileUtility implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4278005408013310921L;
	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(Utility.class);
	/**
	 * countLine
	 */
	private static Integer countLine = 1;

	/**
	 * newZipName
	 * 
	 * @param zipFilename
	 */
	public static String newFileName(final String text) {
		return new File(text).getParent();
	}

	/**
	 * toPath
	 * 
	 * @param file
	 */
	public static Path toPath(final File file) {
		return file.toPath();
	}

	/**
	 * getFileName
	 * 
	 * @param file
	 */
	public static String getFileName(final File file) {
		return file.getName();
	}

	/**
	 * getDirectory
	 * 
	 * @param filePath
	 */
	public static File getDirectory(final String filePath) {
		return new File(filePath);
	}

	/**
	 * readFileContents
	 * 
	 * @param selectedFile
	 * @throws IOException
	 */
	public static String readFileContents(final InputStream file) throws IOException {
		String singleString;
		try (BufferedReader bReader = new BufferedReader(new InputStreamReader(file))) {
			final StringBuilder sbuilder = new StringBuilder();
			String line = bReader.readLine();
			while (line != null) {
				sbuilder.append(line).append(Constant.RETOURCHARIOT);
				line = bReader.readLine();
			}
			singleString = sbuilder.toString();
		}
		return singleString;
	}

	/**
	 * deleteDirectory
	 * 
	 * @param directoryToBeDeleted
	 * @return
	 */
	public static void deleteDirectory(final File directory) {
		try {
			FileUtils.cleanDirectory(directory);
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
	}

	/**
	 * readFileContents
	 * 
	 * @param selectedFile
	 * @throws IOException
	 */
	public static final String readFileContents(final File file) {
		String singleString = null;
		try (BufferedReader bReader = Files.newBufferedReader(Paths.get(file.toURI()))) {
			final StringBuilder sBuilder = new StringBuilder();
			String line = bReader.readLine();
			while (line != null) {
				sBuilder.append(countLine++).append(line).append('\n');
				line = bReader.readLine();
			}
			singleString = sBuilder.toString();
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		} finally {
			countLine = 1;
		}
		return singleString;
	}

	/**
	 * open html file in browser
	 * 
	 * @param file
	 */
	public static void openFile(final File file) {
		try {
			final Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
	}

	/**
	 * getExtension
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtension(final String filename) {
		return FilenameUtils.getExtension(filename);
	}

	/**
	 * downloadUsingNIO
	 * 
	 * @param urlStr
	 * @param file
	 * @throws IOException
	 */
	public static void downloadUsingNIO(final String urlStr, final String file) throws IOException {
		final URL url = new URL(urlStr);
		FileUtils.copyURLToFile(url, new File(file));
	}

	/**
	 * removeExtension
	 * 
	 * @param fname
	 * @return
	 */
	public static String removeExtension(final String fname) {
		final int pos = fname.lastIndexOf('.');
		String name;
		if (pos > -1) {
			name = fname.substring(0, pos);
		} else {
			name = fname;
		}
		return name;
	}

	/**
	 * getStyleParent
	 * 
	 * @param parent
	 */
	public static ObservableList<String> getStyleParent(final Parent parent) {
		return parent.getStylesheets();
	}

	/**
	 * getIcons
	 * 
	 * @param stage
	 */
	public static ObservableList<Image> getIcons(final Stage stage) {
		return stage.getIcons();
	}

	/**
	 * getWorksheet
	 * 
	 * @param work
	 * @param i
	 */
	public static Worksheet getWorksheet(final WorksheetsCollection work, final int index) {
		return work.get(index);
	}

	/**
	 * getCheckedIndices
	 * 
	 * @param index
	 */
	public static ObservableList<Integer> getCheckedIndices(final IndexedCheckModel<File> index) {
		return index.getCheckedIndices();
	}

	/**
	 * getPicker
	 * 
	 * @param picker
	 */
	public static LocalDateTime getPicker(final DateTimePicker picker) {
		return picker.getDateTimeValue();
	}

	/**
	 * getItems
	 * 
	 * @param list
	 */
	public static ObservableList<File> getItems(final CheckListView<File> list) {
		return list.getItems();
	}

	/**
	 * checkModel
	 * 
	 * @param check
	 */
	public static ObservableList<File> checkModel(final IndexedCheckModel<File> check) {
		return check.getCheckedItems();
	}

	/**
	 * getLastRow
	 * 
	 * @param worksheet
	 */
	public static int getLastRow(final Worksheet worksheet) {
		return worksheet.getLastRow();
	}

}
