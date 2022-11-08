package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustIfCase extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("if_stmt")) {
			List<Node> children = node.getChildren();
			for (int i = 0; i < children.size(); i++) {
				Node child = children.get(i);

				if (child.getNodeType().equals("IfStmt")) {
					Node thenNode = new NodeImpl("Then");
					thenNode.setParent(child);
					child.addChild(thenNode);

					Node bodyNode = getChild(child, "Body");
					if (bodyNode == null) {
						return;
					}
					thenNode.addChild(bodyNode);
					bodyNode.cut();
					bodyNode.setParent(thenNode);

					if ((i + 1) == children.size()) {
						return;
					}
					Node nextChild = children.get(i + 1);
					if (nextChild.getNodeType().equals("Else")) {
						nextChild.setAttributes(new ArrayList<Attribute>());
						nextChild.setParent(child);
						child.addChild(nextChild);
					} else {
						Node elseNode = new NodeImpl("Else");
						elseNode.setParent(child);
						child.addChild(elseNode);
						nextChild.setParent(elseNode);
						elseNode.addChild(nextChild);
					}

				}
			}
			Node ifStmt = node.getChildren().get(0);
			ifStmt.setParent(parent);
			parent.addChild(ifStmt, node.cut());
		}

	}

}
