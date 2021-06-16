/**
 * 
 * A collection of static helper functions that ease common operations on nodes.
 * 
 */
package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class CloneHelper {
	
	/**
	 * Create a shallow copy of a node and assigns it to a target. 
	 * (Without the source children)
	 * @param source: The node to be cloned.
	 * @param targetParent: The parent for newly created source clone.
	 * @return The clone of the source node
	 */
	public static Node copy(Node source, Node targetParent) {
		if (!(source instanceof NodeImpl)) {
			return null;
		}
		
		NodeImpl target = new NodeImpl();
		target.setNodeType(source.getNodeType());
		target.setVariabilityClass(source.getVariabilityClass());
		target.setParent(targetParent);
		
		for (Attribute sourceAttr : source.getAttributes()) {
			source.addAttribute(new AttributeImpl(
				sourceAttr.getAttributeKey(), 
				sourceAttr.getAttributeValues()));
		}
		
		return target;
	}
	
	/**
	 * Creates a deep copy of a subtree.
	 * @param source: The subtrees root node to be moved
	 * @param targetParent: The parent for newly created source clone
	 * @return The clone of the source node
	 */
	public static Node copyRecursively(Node source, Node targetParent) {
		if (!(source instanceof NodeImpl)) {
			return null;
		}
		
		Node clone = copy(source, targetParent);
		
		for (Node sourceChild : source.getChildren()) {
			Node cloneChild = copyRecursively(sourceChild, clone);
			clone.addChild(cloneChild);
		}
		
		return clone;
	}
	
	/**
	 * Moves a node (subtree) by reassigning the references
	 * @param source: The subtrees root node to be moved
	 * @param targetParent: The new parent for the source
	 * @return always the source
	 */
	public static Node move(Node source, Node targetParent) {	
		Node oldParent = source.getParent();
		oldParent.getChildren().remove(source);
		targetParent.getChildren().add(source);
		source.setParent(targetParent);
		
		return source;
	}
	
	
	/***
	 * Deletes a node from the tree by removing it from its parent
	 * @param source: Node to be removed
	 */
	public static void delete(Node source) {
		source.getParent().getChildren().remove(source);
	}

}
