package com.ans.jaxb;

import java.io.Serializable;

import javafx.scene.image.ImageView;

/**
 * ImageUtility
 * 
 * @author bensalem Nizar
 */
public class ImageUtility implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -698361833933851453L;

	/**
	 * imageView
	 * 
	 * @return
	 */
	public static ImageView imageView() {
		final ImageView imgView = new ImageView(Constant.IMAGE10);
		imgView.setFitWidth(20);
		imgView.setFitHeight(20);
		return imgView;
	}

	/**
	 * imageViewM
	 * 
	 * @return
	 */
	public static ImageView imageViewM() {
		final ImageView imgViewM = new ImageView(Constant.IMAGE11);
		imgViewM.setFitWidth(20);
		imgViewM.setFitHeight(20);
		return imgViewM;
	}

	/**
	 * imgValid
	 * 
	 * @return
	 */
	public static ImageView imgValid() {
		final ImageView imgValid = new ImageView(Constant.IMAGE12);
		imgValid.setFitWidth(20);
		imgValid.setFitHeight(20);
		return imgValid;
	}

	/**
	 * imgRead
	 * 
	 * @return
	 */
	public static ImageView imgRead() {
		final ImageView imgRead = new ImageView(Constant.IMAGE13);
		imgRead.setFitWidth(20);
		imgRead.setFitHeight(20);
		return imgRead;
	}

	/**
	 * imgExit
	 * 
	 * @return
	 */
	public static ImageView imgExit() {
		final ImageView imgExit = new ImageView(Constant.IMAGE14);
		imgExit.setFitWidth(20);
		imgExit.setFitHeight(20);
		return imgExit;
	}

	/**
	 * imgViewA
	 * 
	 * @return
	 */
	public static ImageView imgViewA() {
		final ImageView imgViewA = new ImageView(Constant.IMAGE1);
		imgViewA.setFitWidth(20);
		imgViewA.setFitHeight(20);
		return imgViewA;
	}

	/**
	 * imgViewO
	 * 
	 * @return
	 */
	public static ImageView imgViewO() {
		final ImageView imgViewO = new ImageView(Constant.IMAGE2);
		imgViewO.setFitWidth(20);
		imgViewO.setFitHeight(20);
		return imgViewO;
	}

	/**
	 * imgViewT
	 * 
	 * @return
	 */
	public static ImageView imgViewT() {
		final ImageView imgViewT = new ImageView(Constant.IMAGE3);
		imgViewT.setFitWidth(20);
		imgViewT.setFitHeight(20);
		return imgViewT;
	}

	/**
	 * imgViewI
	 * 
	 * @return
	 */
	public static ImageView imgViewI() {
		final ImageView imgViewI = new ImageView(Constant.IMAGE5);
		imgViewI.setFitWidth(20);
		imgViewI.setFitHeight(20);
		return imgViewI;
	}

	/**
	 * imgViewR
	 * 
	 * @return
	 */
	public static ImageView imgViewR() {
		final ImageView imgViewR = new ImageView(Constant.IMAGE4);
		imgViewR.setFitWidth(20);
		imgViewR.setFitHeight(20);
		return imgViewR;
	}

}
