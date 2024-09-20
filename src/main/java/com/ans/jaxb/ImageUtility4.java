package com.ans.jaxb;

import java.io.Serializable;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * ImageUtility
 * 
 * @author bensalem Nizar
 */
public class ImageUtility4 implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 922458070974028473L;

	/**
	 * constructView7
	 * 
	 * @param file  @param item
	 * @param item1 @param apropos
	 * @param item2
	 */
	public static void constructView7(final Menu file, final MenuItem item, final MenuItem item1, final Menu apropos,
			final MenuItem item2, final Number newSceneWidth) {
		file.setStyle(Constant.STYLE2);
		item.setStyle(Constant.STYLE2);
		item1.setStyle(Constant.STYLE2);
		apropos.setStyle(Constant.STYLE2);
		item2.setStyle(Constant.STYLE2);
	}
}
