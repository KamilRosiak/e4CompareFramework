package de.tu_bs.cs.isf.e4cf.core.io.reader.python_reader;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.Const;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.RenamerCpp;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader.AbstractSAXHandler;

public class PythonSAXHandler extends AbstractSAXHandler {
	
	private Stack<Node> nodeStack;
	
	public PythonSAXHandler() {
		nodeStack = new Stack<Node>();
	}

	@Override
	public void startDocument() throws SAXException {
		nodeStack.clear();

	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		String key = localName;

		Node elementNode = null;
		if (nodeStack.size() > 0) {
			elementNode = new NodeImpl(key, nodeStack.peek());
			//TODO: rename node
		} else {
			elementNode = new NodeImpl(key, null);
			rootNode = elementNode;
			rootNode.setNodeType(Const.C_UNIT);
		}
		nodeStack.push(elementNode);

	}

	@Override
	public void characters(char[] ch, int start, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (nodeStack.size() > 0) {
			nodeStack.pop();
		}

	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

}
