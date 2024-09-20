package com.ans.jaxb;

/**
 * Main principal de l'application
 * 
 * @author bensalem Nizar
 */
public final class MyLauncher {

	/**
	 * MyLauncher
	 */
	private MyLauncher() {
		// not called
	}

	/**
	 * void main solution de contournement pour que le compilateur java (après la
	 * version 1.9) prend en charge le javaFX
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		AddXmlNode.main(args);
	}
}
