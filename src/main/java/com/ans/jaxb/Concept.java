package com.ans.jaxb;

import java.io.Serializable;

/**
 * class Concept
 * 
 * @author bensalem Nizar
 */
public class Concept implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 494791675154690853L;
	/**
	 * code
	 */
	private String code;
	/**
	 * display
	 */
	private String display;
	
	/**
	 * constructor
	 * 
	 * @param valueSetOID
	 * @param obsolete
	 */
	public Concept(final String code, final String display) {
		this.code = code;
		this.display = display;
	}

	/**
	 * constructor
	 * 
	 */
	public Concept() {
		//default 
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(final String display) {
		this.display = display;
	}

	
}
