package com.ans.jaxb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ConceptList object
 * 
 * @author bensalem Nizar
 */
public class ConceptList implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -792071954118089662L;
	/**
	 * concept
	 */
	private List<Concept> concept = new ArrayList<>();
	/**
	 * system
	 */
	private String system;
	
	/**
	 * constructor
	 * 
	 * @param valueSetOID
	 * @param obsolete
	 */
	public ConceptList(final List<Concept> concept, final String system) {
		this.concept = concept;
		this.system = system;
	}

	/**
	 * constructor
	 * 
	 */
	public ConceptList() {
		//default 
	}
	/**
	 * @return the concept
	 */
	public List<Concept> getConcept() {
		return concept;
	}
	/**
	 * @param concept the concept to set
	 */
	public void setConcept(final List<Concept> concept) {
		this.concept = concept;
	}
	/**
	 * @return the system
	 */
	public String getSystem() {
		return system;
	}
	/**
	 * @param system the system to set
	 */
	public void setSystem(final String system) {
		this.system = system;
	}
	

}
