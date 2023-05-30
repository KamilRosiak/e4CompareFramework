package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public abstract class AbstractSAXHandler extends DefaultHandler implements ErrorHandler {

	protected String extension;
	protected Node rootNode;
	protected List<String> nodeTypes;

	public Node getRootNode() {
		return rootNode;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	private String getParseExceptionInfo(SAXParseException spe) {
		String systemId = spe.getSystemId();

		if (systemId == null) {
			systemId = "null";
		}

		String info = "URI=" + systemId + " Line=" + spe.getLineNumber() + ": " + spe.getMessage();

		return info;
	}

	@Override
	public void warning(SAXParseException spe) throws SAXException {
		System.out.println("Warning: " + getParseExceptionInfo(spe));
	}

	@Override
	public void error(SAXParseException spe) throws SAXException {
		String message = "Error: " + getParseExceptionInfo(spe);
		throw new SAXException(message);
	}

	@Override
	public void fatalError(SAXParseException spe) throws SAXException {
		String message = "Fatal Error: " + getParseExceptionInfo(spe);
		throw new SAXException(message);
	}

	@Override
	public abstract void startDocument() throws SAXException;

	@Override
	public abstract void startElement(String namespaceURI, String localName, String qName, Attributes atts)
			throws SAXException;

	@Override
	public abstract void characters(char ch[], int start, int length);

	@Override
	public abstract void endElement(String uri, String localName, String qName) throws SAXException;

	@Override
	public abstract void endDocument() throws SAXException;

}
