package com.ans.jaxb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Terminology
 * 
 * @author bensa Nizar
 */
public final class Terminology implements Serializable {

	/**
	 * serialVersionUID
	 */
	public static final long serialVersionUID = -1911855754956064975L;
	/**
	 * textLogin
	 */
	public String textLogin;
	/**
	 * textPwd
	 */
	public String textPwd;
	/**
	 * listTerminology
	 */
	public List<String> listTerminology;
	/**
	 * map
	 */
	public Map<String, String> map;
	/**
	 * tokenurl
	 */
	public String tokenurl;
	/**
	 * downloadurl
	 */
	public String downloadurl;
	/**
	 * tokenopen
	 */
	public String tokenopen;
	/**
	 * outputD
	 */
	public String outputD;

	/**
	 * constructor
	 * 
	 * @param textLogin       @param textPwd
	 * @param listTerminology @param map
	 * @param tokenurl        @param downloadurl
	 * @param tokenopen       @param outputD
	 */
	public Terminology(final String textLogin, final String textPwd, final List<String> listTerminology,
			final Map<String, String> map, final String tokenurl, final String downloadurl, final String tokenopen,
			final String outputD) {
		this.textLogin = textLogin;
		this.textPwd = textPwd;
		this.listTerminology = listTerminology;
		this.map = map;
		this.tokenurl = tokenurl;
		this.downloadurl = downloadurl;
		this.tokenopen = tokenopen;
		this.outputD = outputD;

	}

	/**
	 * constructor
	 */
	public Terminology() {
		// default constructor
	}

	/**
	 * @return the textLogin
	 */
	public String getTextLogin() {
		return textLogin;
	}

	/**
	 * @param textLogin the textLogin to set
	 */
	public void setTextLogin(final String textLogin) {
		this.textLogin = textLogin;
	}

	/**
	 * @return the textPwd
	 */
	public String getTextPwd() {
		return textPwd;
	}

	/**
	 * @param textPwd the textPwd to set
	 */
	public void setTextPwd(final String textPwd) {
		this.textPwd = textPwd;
	}

	/**
	 * @return the listTerminology
	 */
	public List<String> getListTerminology() {
		return listTerminology;
	}

	/**
	 * @param listTerminology the listTerminology to set
	 */
	public void setListTerminology(final List<String> listTerminology) {
		this.listTerminology = listTerminology;
	}

	/**
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(final Map<String, String> map) {
		this.map = map;
	}

	/**
	 * @return the tokenurl
	 */
	public String getTokenurl() {
		return tokenurl;
	}

	/**
	 * @param tokenurl the tokenurl to set
	 */
	public void setTokenurl(final String tokenurl) {
		this.tokenurl = tokenurl;
	}

	/**
	 * @return the downloadurl
	 */
	public String getDownloadurl() {
		return downloadurl;
	}

	/**
	 * @param downloadurl the downloadurl to set
	 */
	public void setDownloadurl(final String downloadurl) {
		this.downloadurl = downloadurl;
	}

	/**
	 * @return the tokenopen
	 */
	public String getTokenopen() {
		return tokenopen;
	}

	/**
	 * @param tokenopen the tokenopen to set
	 */
	public void setTokenopen(final String tokenopen) {
		this.tokenopen = tokenopen;
	}

	/**
	 * @return the outputD
	 */
	public String getOutputD() {
		return outputD;
	}

	/**
	 * @param outputD the outputD to set
	 */
	public void setOutputD(final String outputD) {
		this.outputD = outputD;
	}

	@Override
	public String toString() {
		return "Terminology [textLogin=" + textLogin + ", textPwd=" + textPwd + ", listTerminology=" + listTerminology
				+ ", map=" + map + ", tokenurl=" + tokenurl + ", downloadurl=" + downloadurl + ", tokenopen="
				+ tokenopen + ", outputD=" + outputD + "]";
	}

}
