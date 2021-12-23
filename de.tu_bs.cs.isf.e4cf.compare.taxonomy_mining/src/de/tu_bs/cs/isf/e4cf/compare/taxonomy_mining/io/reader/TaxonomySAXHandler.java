package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.io.reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class TaxonomySAXHandler extends AbstractSAXHandler {

	private Stack<Node> nodeStack;
	private String sourceCodeAttributeKey = "SourceCode";
	private String weightAttributeKey = "Weight";
	private String positionAttributeKey = "Position";	

	
	public TaxonomySAXHandler() {
		nodeStack = new Stack<Node>();
		nodeTypes = ParserDictionary.CPP_TAXONOMY_NODE_TYPES;
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
		} else {
			elementNode = new NodeImpl(key, null);
			rootNode = elementNode;
		}
		nodeStack.push(elementNode);
		elementNode.addAttribute(new AttributeImpl(sourceCodeAttributeKey, new StringValueImpl("")));

	}

	@Override
	public void characters(char ch[], int start, int length) {
		String currentInsideValue = new String(ch, start, length).replaceAll("//s+", "").trim();

		if (!currentInsideValue.equals("")) {

			Iterator<Node> iterator = nodeStack.iterator();
			Value<String> value = new StringValueImpl(currentInsideValue);
			while (iterator.hasNext()) {

				Node node = iterator.next();
				Attribute attribute = node.getAttributeForKey(sourceCodeAttributeKey);
				attribute.addAttributeValue(value);

			}

		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (nodeStack.size() > 0) {
			nodeStack.pop();
		}

	}

	@Override
	public void endDocument() throws SAXException {
		List<Value> completeSourceCode = new ArrayList<Value>(
				rootNode.getAttributeForKey(sourceCodeAttributeKey).getAttributeValues());
		removeUnknownNodeTypes(rootNode);
		propagateAttributes(rootNode);
		addPositionAttribute(rootNode);
		addWeightAttribute(completeSourceCode, rootNode);
	}

	public void removeUnknownNodeTypes(Node node) {

		List<Node> children = new ArrayList<Node>(node.getChildren());
		boolean found = false;
		for (Node child : children) {
			if (!nodeTypes.contains(child.getNodeType())) {
				int index = node.getChildren().indexOf(child);
				node.getChildren().remove(index);
				if (!child.getChildren().isEmpty()) {
					node.getChildren().addAll(index, child.getChildren());
				}
				found = true;
			}

		}

		if (found) {
			removeUnknownNodeTypes(node);
		} else {
			for (Node child : node.getChildren()) {
				removeUnknownNodeTypes(child);
			}
		}

	}

	public void addWeightAttribute(List<Value> allContent, Node node) {

		List<Value> nodeValues = node.getAttributeForKey(sourceCodeAttributeKey).getAttributeValues();

		float weight = (float) nodeValues.size() / allContent.size();

		node.addAttribute(new AttributeImpl(weightAttributeKey, new StringValueImpl("" + weight)));

		for (Node child : node.getChildren()) {
			addWeightAttribute(allContent, child);
		}

	}

	public void propagateAttributes(Node node) {

		Attribute nodeAttribute = node.getAttributeForKey(sourceCodeAttributeKey);

		for (Node child : node.getChildren()) {
			Attribute childAttribute = child.getAttributeForKey(sourceCodeAttributeKey);
			nodeAttribute.getAttributeValues().removeAll(childAttribute.getAttributeValues());
		}

		for (Node child : node.getChildren()) {
			propagateAttributes(child);
		}

	}

	public void addPositionAttribute(Node node) {

		if (node.isRoot()) {
			node.addAttribute(new AttributeImpl(positionAttributeKey, new StringValueImpl("0")));
		}

		int position = 0;

		for (Node child : node.getChildren()) {
			child.addAttribute(new AttributeImpl(positionAttributeKey, new StringValueImpl("" + position)));
			addPositionAttribute(child);
			position++;
		}

	}

}
