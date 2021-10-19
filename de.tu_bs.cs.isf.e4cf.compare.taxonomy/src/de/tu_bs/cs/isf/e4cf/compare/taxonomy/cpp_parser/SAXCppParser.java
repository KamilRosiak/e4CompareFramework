package de.tu_bs.cs.isf.e4cf.compare.taxonomy.cpp_parser;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

import java.util.*;
import java.io.*;

public class SAXCppParser extends DefaultHandler {

	Hashtable tags;
	//private StringBuilder currentValue = new StringBuilder();
	private String currentInsideValue = "";
 
	private static Node rootNode;
	Node compilationUnitNode = null;
	private final Stack<Node> nodeStack = new Stack<Node>();
	Node lastNode = null;
	private List<String> languageDictionary = new ArrayList<String>();

	static public void startParsing(String fileURL, Node _rootNode) throws Exception {
		String filename = fileURL;
		rootNode = _rootNode;
		
		if (filename == null) {
			usage();
		}

		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new SAXCppParser());
		xmlReader.setErrorHandler(new MyErrorHandler(System.err));
		xmlReader.parse(filename);
	}

	public void startDocument() throws SAXException {
		tags = new Hashtable();
		implementDictionary();
		
		// create starting Node
		compilationUnitNode = new NodeImpl("Compilation Unit", rootNode);
	}

	@SuppressWarnings("unchecked")
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

		String key = localName;
		Object value = tags.get(key);
		Node elementNode = null;


		if (languageDictionary.contains(key)) {
			System.out.printf("Starting Element: key=%s  qName=%s\n", key, qName);
			
			if (nodeStack.peek() != null) {
				 elementNode = new NodeImpl(key+key.getClass().hashCode(), nodeStack.peek());
			} else 
			{
				elementNode = new NodeImpl(key+key.getClass().hashCode(), null);
			}
			
			// Get language struct "type=xx"
			if (key.equals("literal") || key.equals("block")) {
				String typeAttrValue = atts.getValue("type");
				System.out.println("You have a type of: " + typeAttrValue);
			}
		}

		if (value == null) {
			tags.put(key, new Integer(1));
		} else {
			int count = ((Integer) value).intValue();
			count++;
			tags.put(key, new Integer(count));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
//      eleStack.pop();
		String key = localName;
		Object value = tags.get(key);
		
		if (languageDictionary.contains(key)) {
			System.out.printf("Ending Element: key=%s  qName=%s\n", key, qName);
		}
		
		nodeStack.pop();
	}

	public void endDocument() throws SAXException {
		Enumeration e = tags.keys();
		while (e.hasMoreElements()) {
			String tag = (String) e.nextElement();
			int count = ((Integer) tags.get(tag)).intValue();
			System.out.println("Local Name \"" + tag + "\" occurs " + count + " times");
		}
		
		
	}
	
	public void characters(char ch[], int start, int length) {
        //currentValue.append(ch, start, length);
        currentInsideValue = ch.toString();
        System.out.println("Value inside current Tag: " + ch.length);

    }
	private static void usage() {
		System.err.println("Usage: SAXLocalNameCount <file.xml>");
		System.err.println("       -usage or -help = this message");
		System.exit(1);
	}

	private void implementDictionary() {
		languageDictionary.addAll(new ArrayList<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1130839144112597073L;

			{
				add("class");
				add("function");
				add("constructor");
				add("decl_stmt");
				add("expr_stmt");
				add("typedef");
				add("if");
				add("else");
				add("for");
				add("while");
				add("do");
				add("return");
			}
		});
	}

	private static class MyErrorHandler implements ErrorHandler {

		private PrintStream out;

		MyErrorHandler(PrintStream out) {
			this.out = out;
		}

		private String getParseExceptionInfo(SAXParseException spe) {
			String systemId = spe.getSystemId();

			if (systemId == null) {
				systemId = "null";
			}

			String info = "URI=" + systemId + " Line=" + spe.getLineNumber() + ": " + spe.getMessage();

			return info;
		}

		public void warning(SAXParseException spe) throws SAXException {
			out.println("Warning: " + getParseExceptionInfo(spe));
		}

		public void error(SAXParseException spe) throws SAXException {
			String message = "Error: " + getParseExceptionInfo(spe);
			throw new SAXException(message);
		}

		public void fatalError(SAXParseException spe) throws SAXException {
			String message = "Fatal Error: " + getParseExceptionInfo(spe);
			throw new SAXException(message);
		}
	}
}