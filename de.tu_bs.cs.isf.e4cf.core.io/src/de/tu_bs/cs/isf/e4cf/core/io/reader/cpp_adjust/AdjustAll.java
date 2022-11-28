package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class is the entry point for adjusting a tree.
 * It adjusts the root node and calls all existing TreeAdjuster to make the tree as similar as possible to
 * a tree generated from Java source code.
 * 
 * @author David Bumm
 *
 */
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
		node.addChildWithParent(rootNode);
		rootNode.addAttribute(new AttributeImpl("IsInterface", new StringValueImpl("false")));
		//TODO implement this correctly: rootNode.addAttribute(new AttributeImpl("AccessModifier", new StringValueImpl("PUBLIC")));
		
		TreeAdjuster arrAdjuster = new AdjustArray();
		arrAdjuster.recursiveAdjust(rootNode);
		
		recursiveAdjust(rootNode);
		
		TreeAdjuster methodAdjuster = new AdjustMethodCall();
		methodAdjuster.recursiveAdjust(rootNode);
		
		TreeAdjuster conAdjuster = new AdjustConditon();
		conAdjuster.recursiveAdjust(rootNode);
		


		return node;
	}
	
	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("control") || nodeType.equals("Body") && (parent.getNodeType().equals("EnumDeclaration")
				|| (parent.getNodeType().equals("Body")))) {
			node.cutWithoutChildren();
		}
		if (nodeType.equals("FieldDeclaration") // enum edge case
				&& parent.getNodeType().equals("EnumDeclaration")) {
			node.setNodeType("EnumConstantDeclaration");
		}
		if (nodeType.equals("FieldDeclaration") && parent.getNodeType().equals("Argument")) {
			node.setNodeType("Argument");
			parent.cutWithoutChildren();
		}

		TreeAdjuster literalAdjuster = new AdjustLiterals();
		literalAdjuster.adjust(node, parent, nodeType);

		TreeAdjuster forAdjuster = new AdjustForLoop();
		forAdjuster.adjust(node, parent, nodeType);

		if (nodeType.equals("Name") || nodeType.equals("type")) {
			node.cutWithoutChildren();
		}
		
		if (nodeType.equals("ReturnStmt") && node.getChildren().size() > 0) {
			node.setAttributes(new ArrayList<Attribute>());
			if (node.getChildren().get(0).getNodeType().equals("expr")) {
				node.getChildren().get(0).cutWithoutChildren();
			}
		}

		TreeAdjuster ifAdjuster = new AdjustIfCase();
		ifAdjuster.adjust(node, parent, nodeType);

		TreeAdjuster switchAdjuster = new AdjustSwitchCase();
		switchAdjuster.adjust(node, parent, nodeType);

		
		TreeAdjuster opAdjuster = new AdjustOperator();
		opAdjuster.adjust(node, parent, nodeType);
		
	
		TreeAdjuster commentAdjuster = new AdjustComment();
		commentAdjuster.adjust(node, parent, nodeType);
		
		

	}

}
