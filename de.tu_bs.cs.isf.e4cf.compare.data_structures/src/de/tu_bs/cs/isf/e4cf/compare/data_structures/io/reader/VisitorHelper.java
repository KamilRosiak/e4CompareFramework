package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class provides default methods for @see Visitor to remove redundancy and enforce standards.
 * 
 * @author Paulo Haas
 *
 */

public class VisitorHelper {
	/**
	 * Creates a leaf node
	 * 
	 * @param n JavaParser Node
	 * @param arg Parent Node
	 */
	public static Node Leaf(com.github.javaparser.ast.Node n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaNodeTypes.Value.name(), n.toString());
		return c;
	}
	
	/**
	 * Creates a new node which should get leaves
	 * 
	 * @param n JavaParser Node
	 * @param arg Parent Node of the new node
	 * @return New Node
	 */
	public static Node Parent(com.github.javaparser.ast.Node n, Node arg) {
		return new NodeImpl(n.getClass().getSimpleName(), arg);
	}
}
