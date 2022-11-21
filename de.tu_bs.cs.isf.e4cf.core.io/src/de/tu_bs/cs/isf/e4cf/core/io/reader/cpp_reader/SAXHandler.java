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
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
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
			rootNode.setNodeType("CompilationUnit");
		}

		nodeStack.push(elementNode);

	}

	@Override
	public void characters(char ch[], int start, int length) {
		String value = "";
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
		if (value.equals("string")) {
			value = "String";
		}
		if (nodeType.equals("operator") || (isLegalString(value) && isntRedundant(value))) {
			AttributeImpl attribute = new AttributeImpl("Name");
			attribute.addAttributeValue(new StringValueImpl(value));
			if (containsTypeInfo(node)) {
				if (parent.getParent().getNodeType().equals("MethodDeclaration")) {
					attribute.setAttributeKey("ReturnType");
				} else {
					attribute.setAttributeKey("Type");
				}
				overWriteAttribute(parent.getParent(), attribute);
			} else if (nodeType.equals("Name")) {
				parent.addAttribute(attribute);
			} else {
				overWriteAttribute(node, attribute);
			}

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
			oldAttribute.getAttributeValues().get(0).setValue(oldValue + newValue);
		} else {
			if (node.getNodeType().equals("Name") && node.getParent() != null) {
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
		return parentType.equals("type") && (nodeType.equals("modifier") || nodeType.equals("Name"));
	}

	private boolean isntRedundant(String string) {
		return (!string.equals("enum") && (!string.equals("for")
				&& (!string.equals("while") && (!string.equals("if") && (!string.equals("switch"))
						&& (!string.equals("else if") && (!string.equals("case") && (!string.equals("break;")
								&& (!string.equals("return;")) && (!string.equals("default:")))))))));
	}

	private boolean isLegalString(String string) {
		return (!string.contains("\t")) && (!string.equals("")) && (!string.equals("#")) && (!string.equals("<"))
				&& (!string.equals(">")) && (!string.equals(".")) && (!string.equals(",")) && (!string.equals(", "))
				&& (!string.equals(":")) && (!string.equals(";")) && (!string.equals("{")) && (!string.equals("}"))
				&& (!string.equals("(")) && (!string.equals(")")) && (!string.equals("()")) && (!string.equals("\n")) && (!string.equals("\n\n"));
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
