package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import java.util.ArrayList;
import java.util.List;

public final class AdjustAll extends TreeAdjuster {
	private Node rootNode;

	public AdjustAll(Node rootNode) {
		this.rootNode = rootNode;
	}

	public Node adjustAllNodes() {
		if (rootNode == null) {
			return null;
		}
		
		Node node = new NodeImpl("JAVA"); //TODO change this to C++
		node.addChild(rootNode);
		rootNode.setParent(node);
		rootNode.addAttribute(new AttributeImpl("IsInterface", new StringValueImpl("false")));
		rootNode.addAttribute(new AttributeImpl("AccessModifier", new StringValueImpl("PUBLIC")));
		recursiveAdjust(rootNode);
		return node;
	}

	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("control") || nodeType.equals("Body")
				&& (parent.getNodeType().equals("EnumDeclaration") || (parent.getNodeType().equals("Body")))) {
			removeNodeInbetween(node);
		}
		if (nodeType.equals("FieldDeclaration") // enum edge case
				&& parent.getNodeType().equals("EnumDeclaration")) {
			node.setNodeType("EnumConstantDeclaration");
		}
		if (nodeType.equals("FieldDeclaration") && parent.getNodeType().equals("Argument")) {
			List<Attribute> attrList = new ArrayList<Attribute>(node.getAttributes());
			for (Attribute attribute : attrList) {
				parent.addAttribute(attribute);
			}
			node.cut();
		}

		AdjustLiterals literalAdjuster = new AdjustLiterals();
		literalAdjuster.adjust(node, parent, nodeType);

		AdjustForLoop forAdjuster = new AdjustForLoop();
		forAdjuster.adjust(node, parent, nodeType);

		if (nodeType.equals("Name") || nodeType.equals("type")) {
			node.cut();
		}

		AdjustIfCase ifAdjuster = new AdjustIfCase();
		ifAdjuster.adjust(node, parent, nodeType);

		AdjustSwitchCase switchAdjuster = new AdjustSwitchCase();
		switchAdjuster.adjust(node, parent, nodeType);

	}

}
