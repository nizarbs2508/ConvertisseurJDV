package com.ans.jaxb;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.spire.xls.CellRange;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import javafx.scene.control.CheckBox;

/**
 * NodeUtility
 * 
 * @author bensalem Nizar
 */
public class NodeUtility implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8254183759397058418L;
	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(NodeUtility.class);

	/**
	 * newCheck
	 * 
	 * @param name
	 */
	public static CheckBox newCheck(final String name) {
		return new CheckBox(name);
	}

	/**
	 * RetrieveValueSet
	 * 
	 */
	public static RetrieveValueSet newRetrieveValueSet() {
		return new RetrieveValueSet();
	}

	/**
	 * RetrieveValueSetResponse
	 * 
	 */
	public static RetrieveValueSetResponse newRetrieveSetResponse() {
		return new RetrieveValueSetResponse();
	}

	/**
	 * Workbook
	 * 
	 */
	public static Workbook newWorkbook() {
		return new Workbook();
	}

	/**
	 * newArrayList
	 * 
	 */
	public static List<RetrieveValueSetResponse> newArrayList() {
		return new ArrayList<>();
	}

	/**
	 * newArrayListValue
	 * 
	 */
	public static List<RetrieveValueSet> newArrayListValue() {
		return new ArrayList<>();
	}

	/**
	 * newCellRange
	 * 
	 * @param worksheet
	 * @param row
	 */
	public static CellRange newCellRange(final Worksheet worksheet, final int row) {
		return worksheet.getCellRange(row, 10);
	}

	/**
	 * newCellRange8
	 * 
	 * @param worksheet
	 * @param row
	 */
	public static CellRange newCellRange8(final Worksheet worksheet, final int row) {
		return worksheet.getCellRange(row, 8);
	}

	/**
	 * newCellRangeCol
	 * 
	 * @param worksheet
	 * @param row
	 * @param col
	 */
	public static CellRange newCellRangeCol(final Worksheet worksheet, final int row, final int col) {
		return worksheet.getCellRange(row, col);
	}

	/**
	 * pretty Print
	 * 
	 * @param xml
	 * @throws Exception
	 */
	public static final File prettyPrint(final Document xml, final String path, final String dest) {
		final Source source = new DOMSource(xml);
		final File file = new File(path + Constant.TERMINO);
		if (!file.exists()) {
			file.mkdir();
		}
		if (new File(path + Constant.TERMINOLOGIE + dest + Constant.EXTRDF).exists()) {
			new File(path + Constant.TERMINOLOGIE + dest + Constant.EXTRDF).delete();
		}
		final File xmlFile = new File(path + Constant.TERMINOLOGIE + dest + Constant.EXTRDF);
		StreamResult result;
		try {
			result = new StreamResult(
					new OutputStreamWriter(Files.newOutputStream(Paths.get(xmlFile.toURI())), Constant.UTF8));
			final Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.setOutputProperty(OutputKeys.ENCODING, Constant.UTF8);
			xformer.setOutputProperty(OutputKeys.INDENT, Constant.YES);
			xformer.transform(source, result);
		} catch (final IOException | TransformerFactoryConfigurationError | TransformerException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}

		return xmlFile;
	}

}
