package com.ans.jaxb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * create xml file from excel
 * 
 * @author bensalem Nizar
 */
public final class CreateXMLFile {
	/**
	 * LOG
	 */
	private static final Logger LOG = Logger.getLogger(CreateXMLFile.class);

	/**
	 * createdFile
	 */
	private static File createdFile;

	/**
	 * CreateXMLFile
	 */
	private CreateXMLFile() {
		// not called
	}

	/**
	 * createXML
	 * 
	 * @param valueSetResponse
	 * @param conceptList
	 * @param doc
	 */
	public static void createXML(final RetrieveValueSetResponse valueSetResponse, final Node conceptList,
			final Document doc) {
		final String code = valueSetResponse.getCode();
		final String displayName = valueSetResponse.getDisplayName();
		final String codeSystem = valueSetResponse.getCodeSystem();
		final String dateValid = valueSetResponse.getDateDebut();
		final String dateFin = valueSetResponse.getDateFin();
		String dateValidFinal = "";
		String dateFinFinal = "";
		if (dateValid != null && !dateValid.isEmpty()) {
			final String[] words = dateValid.split("/");
			for (@SuppressWarnings("unused")
			final String word : words) {
				dateValidFinal = words[2].substring(0, 4) + words[1] + words[0] + Constant.ZERO;
			}
		}
		if (dateFin != null && !dateFin.isEmpty()) {
			final String[] words = dateFin.split("/");
			for (@SuppressWarnings("unused")
			final String word : words) {
				dateFinFinal = words[2].substring(0, 4) + words[1] + words[0] + Constant.ZERO;
			}
		}
		// append first child element to root element
		conceptList.appendChild(createConceptElement(doc, code, displayName, codeSystem, dateValidFinal, dateFinFinal));

	}

	/**
	 * createXMLFile
	 * 
	 * @param list
	 * @param out
	 */
	public static boolean createXMLFile(final List<RetrieveValueSetResponse> list, final String out) {
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setIgnoringElementContentWhitespace(true);
		dbFactory.setNamespaceAware(true);
		DocumentBuilder dBuilder;
		boolean isOk = false;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			final Document doc = dBuilder.newDocument();
			// add elements to Document
			final Element rootElement = doc.createElement("RetrieveValueSetResponse");
			rootElement.setAttribute("xmlns:xsi", Constant.ATTRIBUTE);
			rootElement.setAttribute("xsi:schemaLocation", Constant.ATTRIBUTE1);
			rootElement.setAttribute("xmlns", Constant.ATTRIBUTE2);
			// append root element to document
			doc.appendChild(rootElement);
			if (list != null && !list.isEmpty()) {
				final String oid = list.get(0).getValueSetOID();
				final String name = list.get(0).getValueSetName();
				final String revision = list.get(0).getRevisionDate();
				// append first child element to root element
				final Element valueSet = createValueSetElement(doc, oid, name, "test", revision);
				rootElement.appendChild(valueSet);
				final Node conceptList = valueSet.getFirstChild();
				for (final RetrieveValueSetResponse valueSetResponse : list) {
					createXML(valueSetResponse, conceptList, doc);
				}
				doc.normalize();
				// write to console or file
				if (!new File(out).exists()) {
					Files.createDirectories(new File(out).toPath());
				}

				try (OutputStream output = Files.newOutputStream(Paths.get(out + "\\" + name + ".xml"))) {
					final TransformerFactory transformerFac = TransformerFactory.newInstance();
					Transformer transformer = transformerFac.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, Constant.YES);
					transformer.setOutputProperty(OutputKeys.ENCODING, Constant.UTF8);
					transformer.setOutputProperty(OutputKeys.METHOD, Constant.XML);
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, Constant.YES);
					final ClassLoader classloader = NodeUtility2.newClassLoader();
					try (InputStream iStream = classloader.getResourceAsStream(Constant.PRETTY)) {
						transformer = transformerFac.newTransformer(new StreamSource(iStream));
						final DOMSource source = new DOMSource(doc);
						final StreamResult result = new StreamResult(output);
						if (name.startsWith(Constant.JDVFIRST)) {
							// write data
							transformer.transform(source, result);
							createdFile = new File(out + "\\" + name + ".xml");
							isOk = true;
						}
					} catch (final IOException e) {
						if (LOG.isInfoEnabled()) {
							final String error = e.getMessage();
							LOG.error(error);
						}
					}
				} catch (final IOException | TransformerException e) {
					if (LOG.isInfoEnabled()) {
						final String error = e.getMessage();
						LOG.error(error);
					}
				}
			}
		} catch (final ParserConfigurationException | IOException e) {
			isOk = false;
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
		return isOk;
	}

	/**
	 * createValueSetElement
	 * 
	 * @param doc, ident, name, version
	 * @return
	 */
	private static Element createValueSetElement(final Document doc, final String ident, final String name,
			final String version, final String revision) {
		final Element valueSet = doc.createElement("ValueSet");
		// set id attribute
		valueSet.setAttribute("id", ident);
		// set id attribute
		valueSet.setAttribute("displayName", name);
		// set id attribute
		valueSet.setAttribute("version", version);
		// set id attribute
		valueSet.setAttribute("revisionDate", revision);
		// create firstName element
		final Element node = createValueSet(doc, "ConceptList", "");
		valueSet.appendChild(node);
		boolean hasAttribute = valueSet.hasAttribute("xmlns");
		if (hasAttribute) {
			valueSet.removeAttribute("xmlns");
		}
		return valueSet;
	}

	/**
	 * createConceptElement
	 * 
	 * @param doc, code, displayName, codeSystem, dateValid, dateFin
	 * @return
	 */
	private static Element createConceptElement(final Document doc, final String code, final String displayName,
			final String codeSystem, final String dateValid, final String dateFin) {
		final Element concept = doc.createElement("Concept");
		// set id attribute
		concept.setAttribute("code", code);
		// set id attribute
		concept.setAttribute("displayName", displayName);
		// set id attribute
		concept.setAttribute("codeSystem", codeSystem);
		// set id attribute
		concept.setAttribute("dateValid", dateValid);
		// set id attribute
		concept.setAttribute("dateFin", dateFin);
		return concept;
	}

	/**
	 * utility method to create text node
	 * 
	 * @param doc, element, name, value
	 * @return
	 */
	private static Element createValueSet(final Document doc, final String name, final String value) {
		final Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	/**
	 * @return the createdFile
	 */
	public static File getCreatedFile() {
		return createdFile;
	}

	/**
	 * modifyFile
	 * 
	 * @param filePath
	 * @param oldString
	 * @param newString
	 */
	public static void modifyFile(String filePath, String oldString, String newString) {
		File fileToBeModified = new File(filePath);
		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));
			String line = reader.readLine();
			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();
				line = reader.readLine();
			}
			String newContent = oldContent.replaceAll(oldString, newString);
			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}