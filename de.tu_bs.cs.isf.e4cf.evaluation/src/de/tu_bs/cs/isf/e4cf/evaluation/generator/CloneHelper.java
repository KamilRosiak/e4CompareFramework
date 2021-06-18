/**
 * 
 * A collection of static helper functions that ease common operations on nodes.
 * 
 */
package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

@Singleton
@Creatable
public class CloneHelper {
	
	@Inject 
	private CloneLogger logger;
	
	/**
	 * Create a shallow copy of a node and assigns it to a target. 
	 * (Without the source children)
	 * @param source: The node to be cloned.
	 * @param targetParent: The parent for newly created source clone.
	 * @return The clone of the source node
	 */
	public Node copy(Node source, Node targetParent) {
		if (!(source instanceof NodeImpl)) {
			return null;
		}
		
		NodeImpl clone = new NodeImpl();
		clone.setNodeType(source.getNodeType());
		clone.setVariabilityClass(source.getVariabilityClass());
		clone.setParent(targetParent);
		
		for (Attribute sourceAttr : source.getAttributes()) {
			clone.addAttribute(new AttributeImpl(
				sourceAttr.getAttributeKey(), 
				sourceAttr.getAttributeValues()));
		}
		
		targetParent.addChild(clone);
		
		logger.logRaw("Copy source:" + source.getUUID() + 
				" target:" + targetParent.getUUID() + 
				" clone:" + clone.getUUID());
		return clone;
	}
	
	/**
	 * Creates a deep copy of a subtree.
	 * @param source: The subtrees root node to be moved
	 * @param targetParent: The parent for newly created source clone
	 * @return The clone of the source node
	 */
	public Node copyRecursively(Node source, Node targetParent) {
		if (!(source instanceof NodeImpl)) {
			return null;
		}
		
		logger.logRaw("RCopy source:" + source.getUUID() + " target:" + targetParent.getUUID());

		Node clone = rcopy(source, targetParent);
		
		return clone;
	}
	
	private Node rcopy(Node source, Node targetParent) {
		
		Node clone = copy(source, targetParent);
		for (Node omgChild : source.getChildren()) {
			//copy(omgChild, clone);
			rcopy(omgChild, clone);
		}
		
		return clone;
	}
	
	/**
	 * Moves a node (subtree) by reassigning the references
	 * @param source: The subtrees root node to be moved
	 * @param targetParent: The new parent for the source
	 * @return always the source
	 */
	public Node move(Node source, Node targetParent) {
		logger.logRaw("Move source:" + source.getUUID() + " target:" + targetParent.getUUID());

		Node oldParent = source.getParent();
		oldParent.getChildren().remove(source);
		targetParent.addChild(source);
		source.setParent(targetParent);
		
		return source;
	}
	
	/***
	 * Moves a node to the specified index in its current parent
	 * @param source: Node to move
	 * @param targetIndex: Position in parents children list
	 * @return the moved Node
	 */
	public Node move(Node source, int targetIndex) {
		
		// TODO this does not work quite right 
		Node parent = source.getParent();
		
		if (targetIndex < 0 || targetIndex > parent.getNumberOfChildren()-1 ) {
			System.err.println("Error while moving node position: Appending node to the end");
			targetIndex = parent.getNumberOfChildren()-1;
		}
		
		logger.logRaw("MovePos source:" + source.getUUID() + " index:" + targetIndex);
		
		parent.getChildren().remove(source);
		parent.getChildren().add(targetIndex, source);
		
		return source;
	}
	
	/***
	 * Moves a node to the specified index in the specified parent
	 * @param source: Node to move
	 * @param targetParent: The new parent for the source
	 * @param targetIndex: Position in targetParents children list
	 * @return the moved Node
	 */
	public Node move(Node source, Node targetParent, int targetIndex) {
		move(source, targetParent);
		move(source, targetIndex);
		return source;
	}
	
	// TODO add move implementation for moving a node around the children list 
	
	/***
	 * Deletes a node from the tree by removing it from its parent
	 * @param source: Node to be removed
	 */
	public void delete(Node source) {
		logger.logRaw("Delete " + source.getUUID());
		source.getParent().getChildren().remove(source);
	}
	
	// TODO: Create Nodes Function from Seed-Repository
	

}
