package com.ans.jaxb;

import java.io.Serializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 * ImageUtility
 * 
 * @author bensalem Nizar
 */
public class ImageUtility3 implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5181129819962239535L;

	/**
	 * constructView1
	 * 
	 * @param button1        @param button10 @param button2 @param button3
	 * @param button4        @param buttonDownload @param buttonTermino
	 * @param buttonTermino1 @param chooseFile
	 */
	public static void constructView1(final Button button1, final Button button10, final Button button3,
			final Button buttonDownload, final Button buttonTermino, final Button buttonTermino1,
			final Button chooseFile, final Number newSceneWidth) {
		button1.setStyle(Constant.STYLE1);
		button10.setStyle(Constant.STYLE1);
		button3.setStyle(Constant.STYLE1);
		buttonDownload.setStyle(Constant.STYLE1);
		buttonTermino.setStyle(Constant.STYLE1);
		buttonTermino1.setStyle(Constant.STYLE1);
		chooseFile.setStyle(Constant.STYLE1);
	}

	/**
	 * constructView2
	 * 
	 * @param textLogin
	 * @param textPwd
	 */
	public static void constructView2(final TextField textLogin, final PasswordField textPwd,
			final Number newSceneWidth) {
		textPwd.setStyle(Constant.STYLE8);
		textLogin.setStyle(Constant.STYLE8);
		textPwd.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		textLogin.setMinSize(PasswordField.USE_PREF_SIZE, PasswordField.USE_PREF_SIZE);
	}

	/**
	 * constructView3
	 * 
	 * @param label1     @param label2
	 * @param textField2 @param labelL
	 * @param textFieldL @param labelT
	 */
	public static void constructView3(final Label label1, final Label label2, final TextField textField2,
			final Label labelL, final TextField textFieldL, final Label labelT, final Number newSceneWidth,
			final TitledPane firstPane, final TitledPane secondPane) {
		label1.setStyle(Constant.STYLE5);
		label2.setStyle(Constant.STYLE5);
		textField2.setStyle(Constant.STYLE5);
		labelL.setStyle(Constant.STYLE5);
		textFieldL.setStyle(Constant.STYLE5);
		labelT.setStyle(Constant.STYLE5);
		firstPane.setStyle(Constant.STYLE5);
		secondPane.setStyle(Constant.STYLE5);
		label1.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		label2.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		textField2.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		labelL.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		textFieldL.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		labelT.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		firstPane.setMinSize(TitledPane.USE_PREF_SIZE, TitledPane.USE_PREF_SIZE);
		secondPane.setMinSize(TitledPane.USE_PREF_SIZE, TitledPane.USE_PREF_SIZE);

	}

	/**
	 * constructView5
	 * 
	 * @param labelPwd        @param labelLog
	 * @param firstTitledPane @param labelContent
	 * @param label           @param lContent
	 */
	public static void constructView5(final Label labelPwd, final Label labelLog, final TitledPane firstTitledPane,
			final Label labelContent, final Label label, final Label lContent, final Number newSceneWidth) {
		labelPwd.setStyle(Constant.STYLE5);
		labelLog.setStyle(Constant.STYLE5);
		firstTitledPane.setStyle(Constant.STYLE5);
		labelContent.setStyle(Constant.STYLE5);
		label.setStyle(Constant.STYLE5);
		lContent.setStyle(Constant.STYLE5);
		labelPwd.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		labelLog.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		firstTitledPane.setMinSize(TitledPane.USE_PREF_SIZE, TitledPane.USE_PREF_SIZE);
		labelContent.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		lContent.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
	}

	/**
	 * constructView4
	 * 
	 * @param textFieldT  @param labelP
	 * @param textFieldP  @param labelPS
	 * @param textFieldPS @param labelUrl
	 */
	public static void constructView4(final TextField textFieldT, final Label labelP, final TextField textFieldP,
			final Label labelPS, final TextField textFieldPS, final Label labelUrl, final Number newSceneWidth) {
		textFieldT.setStyle(Constant.STYLE5);
		labelP.setStyle(Constant.STYLE5);
		textFieldP.setStyle(Constant.STYLE5);
		labelPS.setStyle(Constant.STYLE5);
		textFieldPS.setStyle(Constant.STYLE5);
		labelUrl.setStyle(Constant.STYLE5);
		textFieldT.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		labelP.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		textFieldP.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		labelPS.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		textFieldPS.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		labelUrl.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
	}

	/**
	 * constructView6
	 * 
	 * @param textFieldUrl @param field
	 * @param labelEmpty   @param comboBox
	 * @param titledPane   @param picker
	 */
	public static void constructView6(final TextField textFieldUrl, final TextField field, final Label labelEmpty,
			final ComboBox<String> comboBox, final TitledPane titledPane, final DateTimePicker picker,
			final Number newSceneWidth) {
		textFieldUrl.setStyle(Constant.STYLE5);
		field.setStyle(Constant.STYLE5);
		labelEmpty.setStyle(Constant.STYLE5);
		comboBox.setStyle(Constant.STYLE5);
		titledPane.setStyle(Constant.STYLE5);
		picker.setStyle(Constant.STYLE5);
		textFieldUrl.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		field.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
		labelEmpty.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		comboBox.setMinSize(ComboBox.USE_PREF_SIZE, ComboBox.USE_PREF_SIZE);
		titledPane.setMinSize(TitledPane.USE_PREF_SIZE, TitledPane.USE_PREF_SIZE);
		picker.setMinSize(DateTimePicker.USE_PREF_SIZE, DateTimePicker.USE_PREF_SIZE);
	}
}
