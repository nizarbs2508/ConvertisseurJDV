package com.ans.jaxb;

import java.io.Serializable;
import java.util.Objects;

/**
 * RetrieveValueSet object
 * 
 * @author bensalem Nizar
 */
public class RetrieveValueSet implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2262061124176631777L;
	/**
	 * valueSetOID
	 */
	private String valueSetOID;
	/**
	 * obsolete
	 */
	private String obsolete;

	/**
	 * constructor
	 * 
	 * @param valueSetOID
	 * @param obsolete
	 */
	public RetrieveValueSet(final String valueSetOID, final String obsolete) {
		this.valueSetOID = valueSetOID;
		this.obsolete = obsolete;
	}

	/**
	 * constructor
	 * 
	 */
	public RetrieveValueSet() {
		// default
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
	 * @return the obsolete
	 */
	public String getObsolete() {
		return obsolete;
	}

	/**
	 * @param obsolete the obsolete to set
	 */
	public void setObsolete(final String obsolete) {
		this.obsolete = obsolete;
	}

	@Override
	public int hashCode() {
		return Objects.hash(obsolete, valueSetOID);
	}

	@Override
	public boolean equals(final Object obj) {
		Boolean bool = null;
		if (this == obj) {
			bool = true;
		}
		if (obj == null) {
			bool = false;
		}
		if (getClass() != obj.getClass()) {
			bool = false;
		}
		final RetrieveValueSet other = (RetrieveValueSet) obj;
		return (bool != null) ? bool
				: Objects.equals(obsolete, other.obsolete) && Objects.equals(valueSetOID, other.valueSetOID);
	}

	@Override
	public String toString() {
		return "RetrieveValueSet [valueSetOID=" + valueSetOID + ", obsolete=" + obsolete + "]";
	}
}