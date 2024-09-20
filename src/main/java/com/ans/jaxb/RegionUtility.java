package com.ans.jaxb;

import java.io.Serializable;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * RegionUtility
 * 
 * @author bensalem Nizar
 */
public class RegionUtility implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 399415950033493546L;

	/**
	 * getRegion1
	 * 
	 * @return
	 */
	public static Region getRegion1() {
		final Region spacer = new Region();
		spacer.setMaxWidth(20);
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	/**
	 * getItemsSplit
	 * 
	 * @param splitPane
	 * @return
	 */
	public static ObservableList<Node> getItemsSplit(final SplitPane splitPane) {
		return splitPane.getItems();
	}

	/**
	 * getToggle
	 * 
	 * @param group
	 * @return
	 */
	public static Toggle getToggle(final ToggleGroup group) {
		return group.getSelectedToggle();
	}

	/**
	 * getHBox
	 * 
	 * @return
	 */
	public static HBox getHBox() {
		final HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 0, 5, 0));
		hbox.setAlignment(Pos.CENTER);
		return hbox;
	}

	/**
	 * getHBox1
	 * 
	 * @return
	 */
	public static HBox getHBox1() {
		final HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 0, 0, 0));
		hbox.setAlignment(Pos.BASELINE_LEFT);
		return hbox;
	}

	/**
	 * getLabel
	 * 
	 * @return
	 */
	public static Label getLabel() {
		final Label label = new Label();
		label.setText(Constant.LABEL);
		label.setPadding(new Insets(5, 5, 5, 5));
		label.setStyle(Constant.STYLE5);
		return label;
	}

	/**
	 * getLabel1
	 * 
	 * @return
	 */
	public static Label getLabel1() {
		final Label label1 = new Label();
		label1.setText(Constant.LABEL1);
		label1.setPadding(new Insets(5, 5, 5, 5));
		label1.setStyle(Constant.STYLE5);
		return label1;
	}

	/**
	 * getLabel2
	 * 
	 * @return
	 */
	public static Label getLabel2() {
		final Label label2 = new Label();
		label2.setText(Constant.LABEL2);
		label2.setPadding(new Insets(5, 5, 5, 5));
		label2.setStyle(Constant.STYLE5);
		return label2;
	}

	/**
	 * getLabel3
	 * 
	 * @return
	 */
	public static Label getLabel3() {
		final Label labelL = new Label();
		labelL.setText(Constant.LABELL);
		labelL.setPadding(new Insets(5, 5, 5, 5));
		labelL.setStyle(Constant.STYLE5);
		return labelL;
	}

	/**
	 * getLabel4
	 * 
	 * @return
	 */
	public static Label getLabel4() {
		final Label labelT = new Label();
		labelT.setText(Constant.LABELT);
		labelT.setPadding(new Insets(5, 5, 5, 5));
		labelT.setStyle(Constant.STYLE5);
		return labelT;
	}

}
