package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class provides default methods for @see Visitor to remove redundancy and enforce standards.
 * 
 * @author Paulo Haas
 *
 */

public class JavaVisitorUtil {
	/**
	 * Creates a leaf node
	 * 
	 * @param n JavaParser Node
	 * @param arg Parent Node
	 */
	public static Node Leaf(com.github.javaparser.ast.Node n, Node arg) {
		n = n.removeComment();
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		AddAttribute(c, JavaAttributesTypes.Value, n.toString());
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
		n = n.removeComment();
		return new NodeImpl(n.getClass().getSimpleName(), arg);
	}
	
	/**
	 * Creates a new node which should get leaves
	 * 
	 * @param type Type of the new Node
	 * @param arg Parent Node of the new node
	 * @return New Node
	 */
	public static Node Node(JavaNodeTypes type, Node arg) {
		return new NodeImpl(type.name(), arg);
	}
	
	public static Node NodeSetElement(JavaNodeTypes type, int index, Node arg) {
		return new NodeImpl(type.name() + index, arg);
	}
	
	public static void AddAttribute(Node arg, JavaAttributesTypes key, String val) {
		arg.addAttribute(key.name(), val);
	}
}
