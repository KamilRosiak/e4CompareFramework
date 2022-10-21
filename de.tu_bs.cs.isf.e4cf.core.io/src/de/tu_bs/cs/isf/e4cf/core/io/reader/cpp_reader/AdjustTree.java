package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import java.util.ArrayList;
import java.util.List;

public final class AdjustTree {
	private Node rootNode;

	public AdjustTree(Node rootNode)
	{
		this.rootNode = rootNode;
	}


	public Node adjustAllNodes()
	{
		if (rootNode == null) {
			return null; 
		}
		Node node = new NodeImpl("JAVA");
		node.addChild(rootNode);
		rootNode.setParent(node);
		rootNode.addAttribute(new AttributeImpl("IsInterface", new StringValueImpl("false")));
		rootNode.addAttribute(new AttributeImpl("AccessModifier", new StringValueImpl("PUBLIC")));
		recursiveAdjust(rootNode);
		return node;
	}

	private void recursiveAdjust(Node node) {
		if (node == null) {
			return;
		}
		if (node.getNodeType().equals("Name") || node.getNodeType().equals("type")) {
			node.setParent(null);

			return;
		}
		if (node.getNodeType().equals("FieldDeclaration") //enum edge case
				&& node.getParent().getParent().getNodeType().equals("EnumDeclaration")) {
			node.setNodeType("EnumConstantDeclaration");
		}
		
		if (node.getNumberOfChildren() > 0)
		{
			List<Node> children = new ArrayList<Node>(node.getChildren());
			for (Node child : children) {
				recursiveAdjust(child);
			}
		}
	}

}
