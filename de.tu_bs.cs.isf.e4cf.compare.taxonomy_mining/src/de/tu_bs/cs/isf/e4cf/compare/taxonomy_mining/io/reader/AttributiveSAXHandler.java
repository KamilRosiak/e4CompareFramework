package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.io.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class AttributiveSAXHandler extends AbstractSAXHandler {

	private Stack<String> typeStack;
	private Stack<Node> nodeStack;
	
	
	public AttributiveSAXHandler() {
		nodeStack = new Stack<Node>();
		typeStack = new Stack<String>();
	}
	

	@Override
	public void startDocument() throws SAXException {
		if (extension.equals("cpp")) {
			nodeTypes = ParserDictionary.CPP_NODE_TYPES;
		} else if(extension.equals("java")) {
			nodeTypes = ParserDictionary.JAVA_NODE_TYPES;
		}
		else {
			nodeTypes = ParserDictionary.CSHARP_NODE_TYPES;
		}
		nodeStack.clear();
		typeStack.clear();

	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		String key = localName;

		Node elementNode = null;
		if (nodeStack.size() > 0) {
			elementNode = new NodeImpl(key, nodeStack.peek());
		} else {
			elementNode = new NodeImpl(key, null);
			rootNode = elementNode;
		}
		nodeStack.push(elementNode);

		if (!key.equals("name")) {
			typeStack.push(key);
		}

	}

	@Override
	public void characters(char ch[], int start, int length) {
		String currentInsideValue = new String(ch, start, length).replaceAll("//s+", "").trim();

		if (!currentInsideValue.isEmpty()) {
			nodeStack.peek().addAttribute(new AttributeImpl(typeStack.peek(), new StringValueImpl(currentInsideValue)));
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (nodeStack.size() > 0) {
			nodeStack.pop();
		}

		if (!typeStack.isEmpty() && !localName.equals("name")) {
			typeStack.pop();
		}

	}

	@Override
	public void endDocument() throws SAXException {
		removeUnknownNodeTypes(rootNode);
		concatAttributes(rootNode);
	}


	public void removeUnknownNodeTypes(Node node) {
		List<Node> children = new ArrayList<Node>(node.getChildren());
		for (Node child : children) {
			removeUnknownNodeTypes(child);
		}

		if (!nodeTypes.contains(node.getNodeType())) {
			if (node.getParent() != null) {
				for (Attribute attribute : node.getAttributes()) {
					node.getParent().addAttribute(attribute);
				}
				node.getParent().getChildren().remove(node);
				node.getParent().getChildren().addAll(node.getChildren());

			}
		}
	}

	private void concatAttributes(Node node) {

		Map<String, List<Value>> map = new HashMap<String, List<Value>>();

		for (Attribute attribute : node.getAttributes()) {
			if (!map.containsKey(attribute.getAttributeKey())) {
				map.put(attribute.getAttributeKey(), new ArrayList<Value>());
			}
			for (Value value : attribute.getAttributeValues()) {
				map.get(attribute.getAttributeKey()).add(value);
			}

		}

		List<Attribute> reducedAttributes = new ArrayList<Attribute>();
		for (Entry<String, List<Value>> entry : map.entrySet()) {
			reducedAttributes.add(new AttributeImpl(entry.getKey(), entry.getValue()));
		}

		node.getAttributes().clear();
		node.getAttributes().addAll(reducedAttributes);

		for (Node child : node.getChildren()) {
			concatAttributes(child);
		}

	}

}
