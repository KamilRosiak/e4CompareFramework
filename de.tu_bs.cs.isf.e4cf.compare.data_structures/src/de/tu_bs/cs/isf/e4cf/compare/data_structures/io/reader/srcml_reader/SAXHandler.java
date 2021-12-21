package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.srcml_reader;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class SAXHandler extends DefaultHandler implements ErrorHandler {

	private String currentInsideValue;
	private Node rootNode;
	private Node compilationUnitNode;
	private Stack<Node> nodeStack;
	private Stack<AttributeImpl> attributeStack;
	private Node lastNode;
	private boolean isLastAttributeKey;		

	@Override
	public void startDocument() throws SAXException {
		isLastAttributeKey = false;
		nodeStack = new Stack<Node>();
		attributeStack = new Stack<AttributeImpl>();
		lastNode = null;
		rootNode = new NodeImpl("CPP");
		currentInsideValue = "";
		compilationUnitNode = new NodeImpl("CompilationUnit", rootNode);
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		isLastAttributeKey = false;
		String key = localName;

		
		// Check if key is type in node dictionary
		if (ParserDictionary.LANGUAGE_DICTIONARY.contains(key)) {
					
			Node elementNode = null;
			if (nodeStack.size() > 0) {
				elementNode = new NodeImpl(key, nodeStack.peek());
			} else {
				elementNode = new NodeImpl(key, null);
			}
			nodeStack.push(elementNode);
		}

		// Check if attribute in dictionary
		if (ParserDictionary.ATTRIBUTE_DICTIONARY.contains(key) && nodeStack.size() > 0) {			
			
			AttributeImpl elementAttribute = new AttributeImpl(key);
			if (attributeStack.size() > 0) {
				if (!(attributeStack.peek().getAttributeKey().equals("name"))) {
					elementAttribute.setAttributeKey(attributeStack.peek().getAttributeKey());
					attributeStack.push(elementAttribute);
				}
			} else {
				attributeStack.push(elementAttribute);
			}
		}

		// Signal that attribute key can now be extracted from XML
		if (ParserDictionary.ATTRIBUTE_KEY_DICTIONARY.contains(key)) {	
			isLastAttributeKey = true;
		}

	}

	@Override
	public void characters(char ch[], int start, int length) {
		currentInsideValue = new String(ch, start, length).replaceAll("//s+", "");

		// Extract signal value from xml string
		if (isLastAttributeKey && !currentInsideValue.equals("")) {
			if (attributeStack.size() > 0) {						
				attributeStack.peek().addAttributeValue(new StringValueImpl(currentInsideValue));
			}
		} else {
			if (attributeStack.size() > 0) {
				attributeStack.pop();
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String key = localName;

		// Set End element conditions for main language constructs
		if (ParserDictionary.LANGUAGE_DICTIONARY.contains(key)) {

			if (nodeStack.size() > 0) {
				lastNode = nodeStack.pop();
			}

			if (nodeStack.size() == 0) {
				compilationUnitNode.addChild(lastNode);
			}
		}

		// Set End element conditions for attributes
		if (ParserDictionary.ATTRIBUTE_DICTIONARY.contains(key)) {
			if (attributeStack.size() > 0 && nodeStack.size() > 0) {
				// Only Add attributes with values
				if (attributeStack.peek().getAttributeValues().size() > 0) {
					nodeStack.peek().addAttribute(attributeStack.pop());
				}
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		
	}
	
	public Node getRootNode() {
		return rootNode;
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

	

}
