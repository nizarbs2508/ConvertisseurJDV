package com.ans.jaxb;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * RegionUtility
 * 
 * @author bensalem Nizar
 */
public class RegionUtility2 implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -761464065684058233L;

	/**
	 * getHBox
	 * 
	 * @return
	 */
	public static HBox getHBox() {
		final HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 0, 0, 0));
		hbox.setAlignment(Pos.BASELINE_LEFT);
		return hbox;
	}

	/**
	 * getHBox1
	 * 
	 * @return
	 */
	public static HBox getHBox1() {
		final HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 0, 15, 0));
		hbox.setAlignment(Pos.BASELINE_LEFT);
		return hbox;
	}

	/**
	 * getTextField
	 * 
	 * @return
	 */
	public static TextField getTextField() {
		final TextField textField2 = new TextField();
		textField2.setText(Constant.TEXTFIELD2);
		textField2.setPrefWidth(40);
		textField2.setPadding(new Insets(5, 5, 5, 5));
		textField2.setStyle(Constant.STYLE8);
		return textField2;
	}

	/**
	 * getTextField2
	 * 
	 * @return
	 */
	public static TextField getTextField2() {
		final TextField textFieldL = new TextField();
		textFieldL.setPadding(new Insets(5, 5, 5, 5));
		textFieldL.setText(Constant.TEXTFIELDL);
		textFieldL.setPrefWidth(40);
		textFieldL.setStyle(Constant.STYLE8);
		return textFieldL;
	}

	/**
	 * getTextField3
	 * 
	 * @return
	 */
	public static TextField getTextField3() {
		final TextField textFieldT = new TextField();
		textFieldT.setPadding(new Insets(5, 5, 5, 5));
		textFieldT.setText(Constant.TEXTFIELDT);
		textFieldT.setPrefWidth(40);
		textFieldT.setStyle(Constant.STYLE8);
		return textFieldT;
	}

	/**
	 * getPicker
	 * 
	 * @return
	 */
	public static DateTimePicker getPicker() {
		final DateTimePicker picker = new DateTimePicker();
		picker.setPadding(new Insets(5, 5, 5, 5));
		final LocalDateTime localDateTime = LocalDateTime.of(2021, Month.MARCH, 15, 00, 00, 00);
		picker.setDateTimeValue(localDateTime);
		picker.setPrefWidth(190);
		picker.setStyle(Constant.STYLE8);
		picker.setEditable(false);
		return picker;
	}

	/**
	 * getLabel
	 * 
	 * @return
	 */
	public static Label getLabel() {
		final Label label = new Label();
		label.setPadding(new Insets(5, 5, 5, 5));
		label.setStyle(Constant.STYLE5);
		return label;
	}
	
	/**
	 * getField
	 * 
	 * @return
	 */
	public static TextField getField() {
		final TextField textFieldP = new TextField(Constant.TEXTFIELDP);
		textFieldP.setPadding(new Insets(5, 5, 5, 5));
		textFieldP.setPrefWidth(200);
		textFieldP.setStyle(Constant.STYLE8);
		return textFieldP;
	}

	/**
	 * getField1
	 * 
	 * @return
	 */
	public static TextField getField1() {
		final TextField textFieldPS = new TextField(Constant.TEXTFIELDPS);
		textFieldPS.setPadding(new Insets(5, 5, 5, 5));
		textFieldPS.setPrefWidth(200);
		textFieldPS.setStyle(Constant.STYLE8);
		return textFieldPS;
	}

	/**
	 * getField2
	 * 
	 * @return
	 */
	public static TextField getField2() {
		final TextField textFieldUrl = new TextField();
		textFieldUrl.setPadding(new Insets(5, 5, 5, 5));
		textFieldUrl.setText(Constant.TEXTFIELDURL);
		textFieldUrl.setMinWidth(800);
		textFieldUrl.setPrefWidth(800);
		textFieldUrl.setStyle(Constant.STYLE8);
		return textFieldUrl;
	}

}
