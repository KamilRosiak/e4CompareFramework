package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.Const;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.RenamerCpp;

public class SAXHandler extends AbstractSAXHandler {

	private Stack<Node> nodeStack;

	public SAXHandler() {
		nodeStack = new Stack<Node>();
	}

	@Override
	public void startDocument() throws SAXException {
		nodeTypes = ParserDictionary.CPP_NODE_TYPES;
		nodeStack.clear();
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		String key = localName;

		Node elementNode = null;
		if (nodeStack.size() > 0) {
			elementNode = new NodeImpl(key, nodeStack.peek());
			RenamerCpp renamer = RenamerCpp.getInstance(); // rename NodeTypes to be more similar to JavaNodeTypes
			renamer.renameNode(elementNode);
		} else {
			elementNode = new NodeImpl(key, null);
			rootNode = elementNode;
			rootNode.setNodeType(Const.C_UNIT);
		}

		nodeStack.push(elementNode);

	}

	@Override
	public void characters(char ch[], int start, int length) {
		String value = Const.EMPTY;
		for (int i = 0; i < length; i++) {
			value += ch[start + i];
		}
		value = value.trim();
		Node node = nodeStack.peek();
		String nodeType = node.getNodeType();
		Node parent = node.getParent();

		if (value.equals(nodeType)) {
			return; // redundant value
		}
		if (value.equals(Const.STRING.toLowerCase())) {
			value = Const.STRING;
		}
		if (nodeType.equals(Const.OPERATOR_SMALL) || (isLegalString(value) && isntRedundant(value))) {
			AttributeImpl attribute = new AttributeImpl(Const.NAME_BIG);
			attribute.addAttributeValue(new StringValueImpl(value));
			if (containsTypeInfo(node)) {
				if (parent.getParent().getNodeType().equals(Const.M_DECL)) {
					attribute.setAttributeKey(Const.R_TYPE);
				} else {
					attribute.setAttributeKey(Const.TYPE_BIG);
				}
				overWriteAttribute(parent.getParent(), attribute);
			} else if (nodeType.equals(Const.NAME_BIG)) {
				node.addAttribute(attribute);
				addNameAttribute(node, attribute);
			} else {
				overWriteAttribute(node, attribute);
			}

		}
	}

	private void addNameAttribute(Node node, Attribute attribute) {
		Node next = node;
		while (next != null && next.getParent() != null && next.getNodeType().equals(Const.NAME_BIG)) {
			next.getParent().addAttribute(attribute);
			next = next.getParent();
		}
	}

	private void overWriteAttribute(Node node, Attribute attribute) {
		String key = attribute.getAttributeKey();
		String newValue = attribute.getAttributeValues().get(0).getValue().toString();
		Attribute oldAttribute = null;
		for (Attribute a : node.getAttributes()) {
			if (a.getAttributeKey().equals(key)) {
				oldAttribute = a;
			}
		}
		if (oldAttribute != null) {
			String oldValue = oldAttribute.getAttributeValues().get(0).getValue().toString();
			if (node.getNodeType().equals(Const.OPERATOR_SMALL)) {
				oldAttribute.getAttributeValues().get(0).setValue(oldValue + newValue);
			} else {
				oldAttribute.getAttributeValues().get(0).setValue(oldValue + Const.SPACE + newValue);
			}
			
		} else {
			if (node.getNodeType().equals(Const.NAME_BIG) && node.getParent() != null) {
				node.getParent().addAttribute(attribute);
			} else {
				node.addAttribute(attribute);
			}
		}
	}

	private boolean containsTypeInfo(Node node) {
		String nodeType = node.getNodeType();
		if (node == rootNode) {
			return false;
		}
		String parentType = node.getParent().getNodeType();
		return parentType.equals(Const.TYPE_SMALL) && (nodeType.equals(Const.MODIFIER) || nodeType.equals(Const.NAME_BIG));
	}

	private boolean isntRedundant(String string) {
		return (!string.equals(Const.ENUM) && (!string.equals(Const.FOR)
				&& (!string.equals(Const.WHILE) && (!string.equals(Const.IF) && (!string.equals(Const.SWITCH))
				&& (!string.equals(Const.ELSE_IF) && (!string.equals(Const.CASE) && (!string.equals(Const.BREAK_SMALL + Const.SEMICOLON)
				&& (!string.equals(Const.RETURN + Const.SEMICOLON))&& (!string.equals(Const.DEFAULT_SMALL + Const.COLON)))))))));
	}

	private boolean isLegalString(String string) {
		return (!string.contains(Const.T)) && (!string.equals(Const.EMPTY)) && (!string.equals(Const.HASHTAG))
				&& (!string.equals(Const.LESS_OP)) && (!string.equals(Const.GREATER_OP)) && (!string.equals(Const.DOT))
				&& (!string.equals(Const.COMMA)) && (!string.equals(Const.COMMA + Const.SPACE))
				&& (!string.equals(Const.COLON)) && (!string.equals(Const.SEMICOLON))
				&& (!string.equals(Const.BRACKET_CURVED_L)) && (!string.equals(Const.BRACKET_CURVED_R))
				&& (!string.equals(Const.BRACKET_L)) && (!string.equals(Const.BRACKET_R))
				&& (!string.equals(Const.BRACKET_L + Const.BRACKET_R)) && (!string.equals(Const.LINE_BREAK))
				&& (!string.equals(Const.LINE_BREAK + Const.LINE_BREAK));
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (nodeStack.size() > 0) {
			nodeStack.pop();
		}

	}

	@Override
	public void endDocument() throws SAXException {

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

}
