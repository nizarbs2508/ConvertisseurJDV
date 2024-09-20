package com.ans.jaxb;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.controlsfx.control.CheckListView;

import com.spire.xls.CellRange;
import com.spire.xls.Worksheet;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * NodeUtility
 * 
 * @author bensalem Nizar
 */
public class NodeUtility2 implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8254183759397058418L;

	/**
	 * newCellRange1
	 * 
	 * @param worksheet
	 * @param row
	 */
	public static CellRange newCellRange1(final Worksheet worksheet, final int row) {
		return worksheet.getCellRange(row, 1);
	}

	/**
	 * newClassLoader
	 * 
	 */
	public static ClassLoader newClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * getListRetrieveResp
	 * 
	 * @param mapentry
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<RetrieveValueSetResponse> getListRetrieveResp(final Map.Entry mapentry) {
		return new ArrayList<>((List<RetrieveValueSetResponse>) mapentry.getValue());
	}

	/**
	 * isEmptyOrNull
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isEmptyOrNull(final Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * newObsList
	 * 
	 * @param vBox
	 */
	public static ObservableList<Node> newObsList(final VBox vBox) {
		return vBox.getChildren();
	}
	
	/**
	 * newFlowList
	 * 
	 * @param pane
	 */
	public static ObservableList<Node> newFlowList(final FlowPane pane) {
		return pane.getChildren();
	}

	/**
	 * newSpane
	 * 
	 * @param sPane
	 */
	public static ObservableList<Node> newSpane(final SplitPane sPane) {
		return sPane.getItems();
	}

	/**
	 * newHObsList
	 * 
	 * @param hBox
	 */
	public static ObservableList<Node> newHObsList(final HBox hBox) {
		return hBox.getChildren();
	}

	/**
	 * newDimension
	 * 
	 * @param size
	 */
	public static Dimension newDimension(final Toolkit size) {
		return size.getScreenSize();
	}

	/**
	 * newTitledPane
	 * 
	 * @param accordione
	 */
	public static ObservableList<TitledPane> newTitledPane(final Accordion accordione) {
		return accordione.getPanes();
	}
	
	/**
	 * construcList
	 * 
	 * @param finalFiles
	 * @param listB
	 * @param index
	 */
	public static void construcList(final List<File> finalFiles, final CheckListView<File> listB, final int index) {
		if (!finalFiles.contains(FileUtility.getItems(listB).get(index))) {
			finalFiles.add(FileUtility.getItems(listB).get(index));
		}
	}
}
