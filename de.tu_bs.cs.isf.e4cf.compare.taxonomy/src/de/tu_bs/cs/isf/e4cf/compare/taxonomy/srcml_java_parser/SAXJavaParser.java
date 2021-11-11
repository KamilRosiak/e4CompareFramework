package de.tu_bs.cs.isf.e4cf.compare.taxonomy.srcml_java_parser;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

import java.util.*;
import java.io.*;

public class SAXJavaParser extends DefaultHandler {

	Hashtable tags;
	private String currentInsideValue = "";
 
	private static Node rootNode;
	Node compilationUnitNode = null;
	private final Stack<Node> nodeStack = new Stack<Node>();
	private final Stack<AttributeImpl> attributeStack = new Stack<AttributeImpl>();
	Node lastNode = null;
	boolean isLastAttributeKey = false;
	private List<String> languageDictionary = new ArrayList<String>();
	private List<String> attributeDictionary = new ArrayList<String>();
	private List<String> attributeKeyDictionary = new ArrayList<String>();


	static public Node startParsing(InputStream fileInputStream, Node _rootNode) throws Exception {
		//String filename = fileURL;
		rootNode = _rootNode;
		
		if (fileInputStream == null) {
			usage();
		}
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		
		try (InputStream xmlInputStream = fileInputStream) {
			
            spf.setNamespaceAware(true);
    		SAXParser saxParser = spf.newSAXParser();
    		XMLReader xmlReader = saxParser.getXMLReader();
    		xmlReader.setContentHandler(new SAXJavaParser());
    		xmlReader.setErrorHandler(new MyErrorHandler(System.err));
    		
    		InputSource source = new InputSource(xmlInputStream);
    		xmlReader.parse(source);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
		
		
		return rootNode;
	}

	public void startDocument() throws SAXException {
		tags = new Hashtable();
		implementDictionaryAndAttributes();
		
		System.out.println("Parsing JavaFile");
		
		// create starting Node
		compilationUnitNode = new NodeImpl("Compilation Unit", rootNode);
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		isLastAttributeKey = false;
		String key = localName;

		// Check if struct in language dictionary
		if (languageDictionary.contains(key)) {
			Node elementNode = null;
//			System.out.printf("Starting Element: key=%s  qName=%s\n", key, qName);
			
			if (nodeStack.size() > 0) {
				 elementNode = new NodeImpl(key, nodeStack.peek());
			} else {
				elementNode = new NodeImpl(key, null);
			}
			nodeStack.push(elementNode);
		}
		
		// Check if attribute in dictionary
		if (attributeDictionary.contains(key) && nodeStack.size() > 0) {
			AttributeImpl elementAttribute = new AttributeImpl(key);
			if (attributeStack.size() > 0) {
				if (!(attributeStack.peek().getAttributeKey().equals("name"))) {
//					System.out.printf("Found an Attr.: key=%s  qName=%s\n", attributeStack.peek().getAttributeKey(), key);
					elementAttribute.setAttributeKey(attributeStack.peek().getAttributeKey());
					attributeStack.push(elementAttribute);
				}
			} else {
				attributeStack.push(elementAttribute);
			}
		}
		
		if (attributeKeyDictionary.contains(key)) {
			// Signal that attribute key can now be extracted from XML
			isLastAttributeKey = true;
		}
		
		
	}
	
	public void characters(char ch[], int start, int length) {
		currentInsideValue = new String(ch, start, length).replaceAll("//s+", "");
//		System.out.printf("Attribute value=%s: \n", currentInsideValue);

		// Extract signal value from xml string
		if (isLastAttributeKey && !currentInsideValue.equals("")) {
			if (attributeStack.size() > 0) {
//				System.out.printf("Inserting attribute value length=%s, string value=%s \n", currentInsideValue.length(), currentInsideValue);
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
		if (languageDictionary.contains(key)) {
//			System.out.printf("Ending Element: key=%s  qName=%s\n", key, qName);
			
			if (nodeStack.size() > 0) {
				lastNode = nodeStack.pop();
			}
			
			if (nodeStack.size() == 0) {
			  compilationUnitNode.addChild(lastNode);
			}
		}
		
		// Set End element conditions for attributes
		if (attributeDictionary.contains(key)) {
//			System.out.printf("Ending Attribute: key=%s  qName=%s\n", key, qName);
			if (attributeStack.size() > 0 && nodeStack.size() > 0) {
				// Only Add attributes with values
				if (attributeStack.peek().getAttributeValues().size() > 0) {
					nodeStack.peek().addAttribute(attributeStack.pop());
				}
			}
		}
	}

	public void endDocument() throws SAXException {
	}
	
	
	private static void usage() {
		System.err.println("Usage: SAXLocalNameCount <file.xml>");
		System.err.println("       -usage or -help = this message");
		System.exit(1);
	}

	private void implementDictionaryAndAttributes() {
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
		
		attributeDictionary.addAll(new ArrayList<String>() {/**
			 * 
			 */
			private static final long serialVersionUID = 2768596879716055759L;

		{
				add("expr");
				add("type");
				add("name");
			}
		});
		
		
		attributeKeyDictionary.addAll(new ArrayList<String>() {/**
			 * 
			 */
			private static final long serialVersionUID = -3717663497299769476L;

		{
				add("name");
				add("operator");
				add("literal");
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