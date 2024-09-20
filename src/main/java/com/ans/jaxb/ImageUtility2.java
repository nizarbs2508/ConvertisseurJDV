package com.ans.jaxb;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * ImageUtility
 * 
 * @author bensalem Nizar
 */
public class ImageUtility2 implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4586788187818409697L;

	/**
	 * getRegion2
	 * 
	 * @return
	 */
	public static Region getRegion2() {
		final Region spacer = new Region();
		spacer.setMaxHeight(20);
		VBox.setVgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	/**
	 * getRegion1
	 * 
	 * @return
	 */
	public static Region getRegion1() {
		final Region spacer = new Region();
		spacer.setMaxHeight(10);
		VBox.setVgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	/**
	 * getRegion3
	 * 
	 * @return
	 */
	public static Region getRegion3() {
		final Region spacer = new Region();
		spacer.setMaxWidth(5);
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	/**
	 * getRegion4
	 * 
	 * @return
	 */
	public static Region getRegion4() {
		final Region spacer2 = new Region();
		spacer2.setPrefHeight(10);
		VBox.setVgrow(spacer2, Priority.ALWAYS);
		return spacer2;
	}

	/**
	 * getRegion5
	 * 
	 * @return
	 */
	public static Region getRegion5() {
		final Region spacer14 = new Region();
		spacer14.setMaxWidth(20);
		HBox.setHgrow(spacer14, Priority.ALWAYS);
		return spacer14;
	}

	/**
	 * getRegion7
	 * 
	 * @return
	 */
	public static Region getRegion7() {
		final Region spacer14 = new Region();
		spacer14.setMaxWidth(800);
		HBox.setHgrow(spacer14, Priority.ALWAYS);
		return spacer14;
	}

	/**
	 * getRegion8
	 * 
	 * @return
	 */
	public static Region getRegion8() {
		final Region spacer14 = new Region();
		spacer14.setMaxWidth(50);
		HBox.setHgrow(spacer14, Priority.ALWAYS);
		return spacer14;
	}

	/**
	 * getRegion6
	 * 
	 * @return
	 */
	public static Region getRegion6() {
		final Region spacer13 = new Region();
		spacer13.setMaxWidth(10);
		HBox.setHgrow(spacer13, Priority.ALWAYS);
		return spacer13;
	}

	/**
	 * imgViewD
	 * 
	 * @return
	 */
	public static ImageView imgViewD() {
		final ImageView imgViewD = new ImageView(Constant.IMAGE6);
		imgViewD.setFitWidth(20);
		imgViewD.setFitHeight(20);
		return imgViewD;
	}

	/**
	 * getImageView
	 * 
	 * @return
	 */
	public static ImageView getImageView() {
		final Image ansImage = new Image(AddXmlNode.class.getResource(Constant.IMAGE7).toExternalForm());
		final ImageView imageView = new ImageView();
		imageView.setImage(ansImage);
		imageView.setFitWidth(120);
		imageView.setFitHeight(80);
		imageView.setPreserveRatio(true);
		imageView.setSmooth(true);
		return imageView;
	}

}
