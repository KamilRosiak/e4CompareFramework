package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

/**
 * Converts a Tree to Dot (graph description language) for visualization.
 * 
 * @author Paulo Haas
 *
 */
public class TreeConverter {
	/**
	 * Converts a Tree to Dot code
	 * 
	 * @param t Tree
	 * @return Dot Code
	 */
	public static String treeToDot(Tree t) {
		String s = "digraph G {\n";
		s += dotBuilder(t.getRoot());
		return s + "\n}";
	}

	/**
	 * Recursively converts node and attributes to Dot nodes.
	 * 
	 * @param n Node
	 * @return Partial Dot code for Node
	 */
	private static String dotBuilder(Node n) {
		String s = new String();
		for (Node c : n.getChildren()) {
			s += dotBuilder(c);
			s += n.hashCode() + " -> " + c.hashCode() + ";\n";
		}
		s += n.hashCode() + "[label=\"" + n.getNodeType().replace("\r\n", " \\n ");
		for (Attribute a : n.getAttributes()) {
			s += "\\nAttribute: " + a.getAttributeKey().replace("\r\n", " \\n ");
		}
		s += "\"];\n";
		return s;
	}
}
