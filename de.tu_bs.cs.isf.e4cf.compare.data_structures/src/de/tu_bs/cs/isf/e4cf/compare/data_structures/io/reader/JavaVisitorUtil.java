package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class provides default methods for @see Visitor to remove redundancy and
 * enforce standards.
 * 
 * @author Paulo Haas
 *
 */

public class JavaVisitorUtil {
	/**
	 * Creates a new node with attribute key "Value" (of type
	 * {@link JavaAttributesTypes} as string) and n converted to string as a value.
	 * 
	 * @param n   JavaParser Node
	 * @param arg Parent Node
	 */
	public static Node createNodeWithValue(com.github.javaparser.ast.Node n, Node arg) {
		Node c = createNode(n, arg);
		addAttribute(arg, JavaAttributesTypes.Value, n.toString());
		return c;
	}

	/**
	 * Creates a new node without attributes.
	 * 
	 * @param n   JavaParser Node
	 * @param arg Parent Node of the new node
	 * @return New Node
	 */
	public static Node createNode(com.github.javaparser.ast.Node n, Node arg) {
		return new NodeImpl(n.getClass().getSimpleName(), arg);
	}

	/**
	 * Creates a new node without attributes.
	 * 
	 * @param type Type of the new Node
	 * @param arg  Parent Node of the new node
	 * @return New Node
	 */
	public static Node createNode(JavaNodeTypes type, Node arg) {
		return new NodeImpl(type.name(), arg);
	}

	/**
	 * Creates a new node without attributes but with an index appended to the name.
	 * 
	 * @param type  Type of the new node
	 * @param index Index appended to type
	 * @param arg   Parent Node of the new node
	 * @return New Node
	 */
	public static Node createNodeWithIndex(JavaNodeTypes type, int index, Node arg) {
		return new NodeImpl(type.name() + index, arg);
	}

	/**
	 * Adds an attribute to a node.
	 * 
	 * @param arg Node
	 * @param key Attribute key
	 * @param val Attribute value
	 */
	public static void addAttribute(Node arg, JavaAttributesTypes key, String val) {
		arg.addAttribute(key.name(), val);
	}
}
