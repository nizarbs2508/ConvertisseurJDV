package com.ans.jaxb;

import java.io.File;
import java.io.Serializable;

import org.controlsfx.control.CheckListView;

import javafx.collections.ObservableList;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * JavaFxUtility
 * 
 * @author bensalem Nizar
 */
public class JavaFxUtility implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6222244674155536590L;

	/**
	 * newObsMenu
	 * 
	 * @param file
	 */
	public static ObservableList<MenuItem> newObsMenu(final Menu file) {
		return file.getItems();
	}

	/**
	 * newObsMenu
	 * 
	 * @param fileChooser
	 */
	public static ObservableList<ExtensionFilter> newExtFilter(final FileChooser fileChooser) {
		return fileChooser.getExtensionFilters();
	}

	/**
	 * newObsListStr
	 * 
	 * @param list
	 */
	public static ObservableList<String> newObsListStr(final CheckListView<File> list) {
		return list.getStylesheets();
	}

	/**
	 * newObsListDPane
	 * 
	 * @param dialogPane
	 */
	public static ObservableList<String> newObsListDPane(final DialogPane dialogPane) {
		return dialogPane.getStylesheets();
	}

	/**
	 * newObsTitledPane
	 * 
	 * @param titledPane
	 */
	public static ObservableList<String> newObsTitledPane(final TitledPane titledPane) {
		return titledPane.getStylesheets();
	}

	/**
	 * newObsListTArea
	 * 
	 * @param textArea
	 */
	public static ObservableList<String> newObsListTArea(final TextArea textArea) {
		return textArea.getStylesheets();
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
	 * newSelectionModel
	 * 
	 * @param list
	 */
	public static MultipleSelectionModel<File> newSelectionModel(final CheckListView<File> list) {
		return list.getSelectionModel();
	}
}
