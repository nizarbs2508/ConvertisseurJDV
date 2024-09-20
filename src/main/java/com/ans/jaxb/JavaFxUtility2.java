package com.ans.jaxb;

import java.io.File;
import java.io.Serializable;

import org.controlsfx.control.CheckListView;
import org.controlsfx.control.IndexedCheckModel;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;

/**
 * JavaFxUtility2
 * 
 * @author bensalem Nizar
 */
public class JavaFxUtility2 implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2401667520017738358L;

	/**
	 * newSingleModel
	 * 
	 * @param comboBox
	 */
	public static SingleSelectionModel<String> newSingleModel(final ComboBox<String> comboBox) {
		return comboBox.getSelectionModel();
	}

	/**
	 * newStyleCombo
	 * 
	 * @param comboBox
	 */
	public static ObservableList<String> newStyleCombo(final ComboBox<String> comboBox) {
		return comboBox.getStylesheets();
	}

	/**
	 * newIndexModel
	 * 
	 * @param list
	 */
	public static IndexedCheckModel<File> newIndexModel(final CheckListView<File> list) {
		return list.getCheckModel();
	}
}
