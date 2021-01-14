package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.compare.util.Tuple;

/**
 * Converts a Tree to Dot (graph description language) for visualization. Use
 * can paste the generated to e.g. http://www.webgraphviz.com/.
 * 
 * @author Paulo Haas
 *
 */
public class TreeConverter {
	private static int nodeCounter = 0;
	
	/**
	 * Converts a Tree to Dot code
	 * 
	 * @param t Tree
	 * @return Dot Code
	 */
	public static String treeToDot(Tree t) {
		return "digraph G {\n" + dotBuilder(t.getRoot()) + "}";
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
			s += "\\nAttribute: " + a.getAttributeKey().replace("\r\n", " \\n ") + " --- " + a.getAttributeValues().toString().replace("\"", "\\\"").replace("\n", " \\n ");
		}
		return s + "\"];\n";
	}
}
