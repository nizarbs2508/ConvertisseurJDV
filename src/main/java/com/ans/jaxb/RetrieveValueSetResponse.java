package com.ans.jaxb;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * RetrieveValueSetResponse object
 * 
 * @author bensalem Nizar
 */
public class RetrieveValueSetResponse implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1316479101842213325L;
	/**
	 * valueSetOID
	 */
	private String valueSetOID;
	/**
	 * valueSetName
	 */
	private String valueSetName;
	/**
	 * code
	 */
	private String code;
	/**
	 * displayName
	 */
	private String displayName;
	/**
	 * codeSystemName
	 */
	private String codeSystemName;
	/**
	 * codeSystem
	 */
	private String codeSystem;
	/**
	 * dateDebut
	 */
	private String dateDebut;
	/**
	 * dateFin
	 */
	private String dateFin;
	/**
	 * dateDebut
	 */
	private String dateDebutBarre;
	/**
	 * dateFin
	 */
	private String dateFinBarre;
	/**
	 * revisionDate
	 */
	private String revisionDate;
	/**
	 * revisionFormatDate
	 */
	private Date revisionFormatDate;

	/**
	 * constructor
	 * 
	 * @param valueSetOID    @param valueSetName
	 * @param code           @param displayName
	 * @param codeSystemName @param codeSystem
	 * @param dateDebut      @param dateFin
	 */
	public RetrieveValueSetResponse(final String valueSetOID, final String valueSetName, final String code,
			final String displayName, final String codeSystemName, final String codeSystem, final String dateDebut,
			final String dateFin, final String revisionDate, final String dateDebutBarre, final String dateFinBarre,
			final String revisionDateBarre, final Date revisionFormatDate) {
		this.valueSetOID = valueSetOID;
		this.valueSetName = valueSetName;
		this.code = code;
		this.displayName = displayName;
		this.codeSystemName = codeSystemName;
		this.codeSystem = codeSystem;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.revisionDate = revisionDate;
		this.dateDebutBarre = dateDebutBarre;
		this.dateFinBarre = dateFinBarre;
		this.revisionFormatDate = revisionFormatDate;
	}

	/**
	 * constructor
	 */
	public RetrieveValueSetResponse() {
		// default constructor
	}

	/**
	 * @return the valueSetOID
	 */
	public String getValueSetOID() {
		return valueSetOID;
	}

	/**
	 * @param valueSetOID the valueSetOID to set
	 */
	public void setValueSetOID(final String valueSetOID) {
		this.valueSetOID = valueSetOID;
	}

	/**
	 * @return the valueSetName
	 */
	public String getValueSetName() {
		return valueSetName;
	}

	/**
	 * @param valueSetName the valueSetName to set
	 */
	public void setValueSetName(final String valueSetName) {
		this.valueSetName = valueSetName;
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
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @param codeSystemName the codeSystemName to set
	 */
	public void setCodeSystemName(final String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}

	/**
	 * @return the codeSystem
	 */
	public String getCodeSystem() {
		return codeSystem;
	}

	/**
	 * @param codeSystem the codeSystem to set
	 */
	public void setCodeSystem(final String codeSystem) {
		this.codeSystem = codeSystem;
	}

	/**
	 * @return the dateDebut
	 */
	public String getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(final String dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public String getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(final String dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return the revisionDate
	 */
	public String getRevisionDate() {
		return revisionDate;
	}

	/**
	 * @param revisionDate the revisionDate to set
	 */
	public void setRevisionDate(final String revisionDate) {
		this.revisionDate = revisionDate;
	}

	/**
	 * @return the dateDebutBarre
	 */
	public String getDateDebutBarre() {
		return dateDebutBarre;
	}

	/**
	 * @param dateDebutBarre the dateDebutBarre to set
	 */
	public void setDateDebutBarre(final String dateDebutBarre) {
		this.dateDebutBarre = dateDebutBarre;
	}

	/**
	 * @return the dateFinBarre
	 */
	public String getDateFinBarre() {
		return dateFinBarre;
	}

	/**
	 * @param dateFinBarre the dateFinBarre to set
	 */
	public void setDateFinBarre(final String dateFinBarre) {
		this.dateFinBarre = dateFinBarre;
	}

	/**
	 * @return the codeSystemName
	 */
	public String getCodeSystemName() {
		return codeSystemName;
	}

	/**
	 * @return the revisionFormatDate
	 */
	public Date getRevisionFormatDate() {
		return revisionFormatDate;
	}

	/**
	 * @param revisionFormatDate the revisionFormatDate to set
	 */
	public void setRevisionFormatDate(final Date revisionFormatDate) {
		this.revisionFormatDate = revisionFormatDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, codeSystem, codeSystemName, dateDebut, dateDebutBarre, dateFin, dateFinBarre,
				displayName, revisionDate, revisionFormatDate, valueSetName, valueSetOID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetrieveValueSetResponse other = (RetrieveValueSetResponse) obj;
		return Objects.equals(code, other.code) && Objects.equals(codeSystem, other.codeSystem)
				&& Objects.equals(codeSystemName, other.codeSystemName) && Objects.equals(dateDebut, other.dateDebut)
				&& Objects.equals(dateDebutBarre, other.dateDebutBarre) && Objects.equals(dateFin, other.dateFin)
				&& Objects.equals(dateFinBarre, other.dateFinBarre) && Objects.equals(displayName, other.displayName)
				&& Objects.equals(revisionDate, other.revisionDate)
				&& Objects.equals(revisionFormatDate, other.revisionFormatDate)
				&& Objects.equals(valueSetName, other.valueSetName) && Objects.equals(valueSetOID, other.valueSetOID);
	}

	@Override
	public String toString() {
		return "RetrieveValueSetResponse [valueSetOID=" + valueSetOID + ", valueSetName=" + valueSetName + ", code="
				+ code + ", displayName=" + displayName + ", codeSystemName=" + codeSystemName + ", codeSystem="
				+ codeSystem + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", dateDebutBarre="
				+ dateDebutBarre + ", dateFinBarre=" + dateFinBarre + ", revisionDate=" + revisionDate
				+ ", revisionFormatDate=" + revisionFormatDate + "]";
	}
	
	
}