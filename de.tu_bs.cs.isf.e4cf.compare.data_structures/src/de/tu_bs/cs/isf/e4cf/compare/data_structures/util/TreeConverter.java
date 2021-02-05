package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

/**
 * Converts a Tree to Dot (graph description language) for visualization. Use
 * can paste the generated to e.g. http://www.webgraphviz.com/.
 * 
 * @author Paulo Haas
 *
 */
public class TreeConverter {
	/**
	 * Converts a Tree to DOT code
	 * 
	 * @no
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/DOT_%28graph_description_language%29">Wikipedia
	 *      - DOT (Graph Description Language)</a>
	 * @param t Tree
	 * @return DOT Code
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
		String s = new String(); // s will be the output
		for (Node c : n.getChildren()) {
			/*
			 * Append s by the dot code of every child recursively and connect the param
			 * node with the child. The dot node IDs are created by their hash codes.
			 */
			s += dotBuilder(c);
			s += "\t" + n.hashCode() + " -> " + c.hashCode() + ";\n";
		}
		/*
		 * When all children are done, convert the param node. Append s by it's ID
		 * (hashcode) and label the node by it's type. Replace line breaks in the type
		 * with equivalent control character.
		 */
		s += "\t" + n.hashCode() + "[label=\"" + n.getNodeType().replace("\r\n", " \\n ");
		for (Attribute a : n.getAttributes()) {
			/*
			 * Then add all attributes to the label of the node. Every attribute gets their
			 * own line. Line breaks and quotation marks need to be replaced.
			 */
			s += "\\nAttribute: " + a.getAttributeKey().replace("\r\n", " \\n ") + " --- "
					+ a.getAttributeValues().toString().replace("\"", "\\\"").replace("\n", " \\n ");
		}
		return s + "\"];\n"; // return output and close the node stmt
	}
}
