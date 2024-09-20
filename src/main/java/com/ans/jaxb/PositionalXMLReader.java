package com.ans.jaxb;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * PositionalXMLReader
 * 
 * @author bensalem Nizar
 * 
 *         XML-Reader which can return XML Node at a given line/col location of
 *         a document
 * 
 */
public final class PositionalXMLReader implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7136356535586314656L;

	/**
	 * readXML
	 * 
	 * @param iStream
	 * @return
	 * @throws IOException, SAXException, ParserConfigurationException
	 */
	public Document readXML(final InputStream iStream) throws IOException, SAXException, ParserConfigurationException {
		final Document doc;
		SAXParser parser;
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		parser = factory.newSAXParser();
		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();

		final Stack<Element> elementStack = new Stack<>();
		final StringBuilder textBuffer = new StringBuilder();
		final Map<String, Integer> map = new ConcurrentHashMap<>();
		final DefaultHandler handler = new DefaultHandler() {
			/**
			 * locator
			 */
			private Locator locator;

			@Override
			public void setDocumentLocator(final Locator locator) {
				this.locator = locator;
			}

			@Override
			public void startElement(final String uri, final String localName, final String qName,
					final Attributes attributes) throws SAXException {
				addTextIfNeeded();
				final Element elem = doc.createElement(qName);
				for (int i = 0; i < attributes.getLength(); i++) {
					elem.setAttribute(attributes.getQName(i), attributes.getValue(i));
				}
				elem.setUserData(Constant.LINE_NUMBER, this.locator.getLineNumber(), null);
				elem.setUserData(Constant.COLUMN_NUMBER, this.locator.getColumnNumber(), null);
				int counter = map.containsKey(qName) ? map.get(qName) : 1;
				elem.setUserData(Constant.ELEMENT_ID, qName + "_" + counter++, null);
				map.put(qName, counter);
				elementStack.push(elem);
			}

			@Override
			public void endElement(final String uri, final String localName, final String qName) {
				addTextIfNeeded();
				final Element closedEl = elementStack.pop();
				closedEl.setUserData(Constant.LINE_LAST_KEY, this.locator.getLineNumber(), null);
				closedEl.setUserData(Constant.COLUMN_LAST_KEY, this.locator.getColumnNumber(), null);
				if (elementStack.isEmpty()) {
					doc.appendChild(closedEl);
				} else {
					final Element parentEl = elementStack.peek();
					parentEl.appendChild(closedEl);
				}
			}

			@Override
			public void characters(final char character[], final int start, final int length) throws SAXException {
				textBuffer.append(character, start, length);
			}

			// Outputs text accumulated under the current node
			private void addTextIfNeeded() {
				if (textBuffer.length() > 0) {
					final Element elem = elementStack.peek();
					final Node textNode = doc.createTextNode(textBuffer.toString());
					elem.appendChild(textNode);
					textBuffer.delete(0, textBuffer.length());
				}
			}
		};
		parser.parse(iStream, handler);
		return doc;
	}
}