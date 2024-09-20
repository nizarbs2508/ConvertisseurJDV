package com.ans.jaxb;

import java.io.Serializable;

import org.w3c.dom.Node;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.layout.HBox;

/**
 * XmlViewUtility
 * 
 * @author bensa Nizar
 */
public class XmlViewUtility implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7440454734131233413L;

	/**
	 * 
	 * @param root
	 * @return
	 */
	public static Node getValue(final TreeItem<Node> root) {
		Node node = null;
		if (root != null && getValueRoot(root) != null) {
			node = getValueRoot(root);
		}
		return node;
	}

	/**
	 * getValueRoot
	 * 
	 * @param root
	 */
	private static Node getValueRoot(final TreeItem<Node> root) {
		return root.getValue();
	}

	/**
	 * newTreeItem
	 * 
	 * @param childNode
	 */
	public static TreeItem<Node> newTreeItem(final Node childNode) {
		return new TreeItem<>(childNode);
	}

	/**
	 * newHObsList
	 * 
	 * @param hBox
	 */
	public static ObservableList<javafx.scene.Node> newHObsList(final HBox hBox) {
		return hBox.getChildren();
	}

	/**
	 * getUserData
	 * 
	 * @param node
	 */
	public static Object getUserDataT(final Node node) {
		return node.getUserData(Constant.LINENUMBER);
	}

	/**
	 * getTreeTableView
	 * 
	 * @param treeTableView
	 */
	public static ObservableList<TreeTableColumn<Node, ?>> getTreeTableView(final TreeTableView<Node> treeTableView) {
		return treeTableView.getColumns();
	}

	/**
	 * newTreeTableView
	 * 
	 * @param treeTableView
	 */
	public static TreeTableViewSelectionModel<Node> newTreeTableView(final TreeTableView<Node> treeTableView) {
		return treeTableView.getSelectionModel();
	}

	/**
	 * getChildrenRoot
	 * 
	 * @param root
	 */
	public static ObservableList<TreeItem<Node>> getChildrenRoot(final TreeItem<Node> root) {
		return root.getChildren();
	}

	/**
	 * getSkin
	 * 
	 * @param textArea
	 */
	public static Skin<?> getSkin(final TextArea textArea) {
		return textArea.getSkin();
	}

	/**
	 * getItemValue
	 * 
	 * @param item
	 */
	public static Node getItemValue(final TreeItem<Node> item) {
		return item.getValue();
	}

	/**
	 * getStyleScene
	 * 
	 * @param scene
	 */
	public static ObservableList<String> getStyleScene(final Scene scene) {
		return scene.getStylesheets();
	}

	/**
	 * newObsClassArea
	 * 
	 * @param textArea
	 */
	public static ObservableList<String> newObsClassArea(final TextArea textArea) {
		return textArea.getStyleClass();
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
	 * setText
	 * 
	 * @param name
	 * @param obj
	 * @return
	 */
	public static String textValue(final String name, final Object obj) {
		String result;
		if (Constant.VALUESET2.equals(name)) {
			result = name == null ? "" : name.trim() + "  [" + obj + "]";
		} else {
			result = name == null ? "" : name.trim();
		}
		return result;
	}

}
