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
			s += "\\nAttribute: " + a.getAttributeKey().replace("\r\n", " \\n ") + " --- " + a.getAttributeValues().toString().replace("\"", "\\\"").replace("\r\n", " \\n ");
		}
		return s + "\"];\n";
	}

	/**
	 * This method takes any Node of the JavaParser and visualizes it's child nodes.  The nodes are named by their class name.  If a node is a leaf, it is converted to a string and the resulted is also displayed.
	 * 
	 * @param n Node to visualize
	 * @return Dot Code for the node and it's children
	 */
	public static String javaParserNodeToDot(com.github.javaparser.ast.Node n) {
		return "digraph H {\n" + javaParserNodeToDotInternal(n).getSecondElement() + "}";
	}
	
	/**
	 * Recursively callable internal of the JavaParser visualizer.
	 * 
	 * @param n Node to visualize
	 * @return Dot code for the node and all it's children
	 */
	private static Tuple<Integer, String> javaParserNodeToDotInternal(com.github.javaparser.ast.Node n) {
		String dotCode = new String();
		int nodeNumber = nodeCounter++;
		Tuple<Integer, String> childRes;
		for(com.github.javaparser.ast.Node child : n.getChildNodes()) {
			childRes = javaParserNodeToDotInternal(child);
			dotCode += childRes.getSecondElement();
			dotCode += nodeNumber + " -> " + childRes.getFirstElement() + ";\n";
		}
		dotCode += nodeNumber + "[label=\"" +  n.getClass().getSimpleName();
		if(n.getChildNodes().size() == 0) {
			dotCode += "\\nValue: " + n.toString().replace("\r\n", "\\n");
		}
		return new Tuple<Integer, String>(nodeNumber, dotCode + "\"];\n");
	}
}
