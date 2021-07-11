package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import java.util.List
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable
import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*;

@Creatable
@Singleton
class CloneHelper {
	
	@Inject 
	CloneLogger logger;
	
	/**
	 * Create a shallow copy of a node and assigns it to a target. 
	 * (Without the source children)
	 * @param source The node to be cloned.
	 * @param targetParent The parent for newly created source clone.
	 * @return The clone of the source node
	 */
	def copy(Node source, Node targetParent) {
		if (!(source instanceof NodeImpl)) {
			return null;
		}
		
		val clone = new NodeImpl()
		clone.nodeType = source.nodeType
		clone.variabilityClass = source.variabilityClass
		clone.parent = targetParent
		source.attributes.forEach[a | clone.addAttribute( new AttributeImpl(a.attributeKey, a.attributeValues))]	
		
		targetParent.addChild(clone);
		
		logger.logRaw(COPY + SOURCE + source.UUID + TARGET + targetParent.UUID + CLONE + clone.UUID);
		return clone;
	}
	
	/**
	 * Creates a deep copy of a subtree.
	 * @param source The subtrees root node to be moved
	 * @param targetParent The parent for newly created source clone
	 * @return The clone of the source node
	 */
	def copyRecursively(Node source, Node targetParent) {
		if (!(source instanceof NodeImpl)) {
			return null
		}
		
		logger.logRaw(RCOPY + SOURCE + source.UUID + TARGET + targetParent.UUID)
		val clone = _copyRecursively(source, targetParent)
		return clone;
	}
	
	private def NodeImpl _copyRecursively(Node source, Node targetParent) {		
		val clone = copy(source, targetParent)
		source.children.forEach[c | c._copyRecursively(clone)]
		return clone
	}
	
	/**
	 * Moves a node (subtree) by reassigning the references
	 * @param source The subtrees root node to be moved
	 * @param targetParent The new parent for the source
	 * @return always the source
	 */
	def move(Node source, Node targetParent) {
		logger.logRaw(MOVE + SOURCE + source.UUID + TARGET + targetParent.UUID)
		val oldParent = source.parent
		oldParent.children.remove(source)
		targetParent.addChild(source)
		source.parent = targetParent
		return source
	}
	
	/**
	 * Moves a node to the specified index in its current parent
	 * @param source Node to move
	 * @param targetIndex Position in parents children list
	 * @return the moved Node
	 */
	def move(Node source, int targetIndex) {
		
		val parent = source.parent
		var index = targetIndex
		
		if (targetIndex < 0 || targetIndex > parent.numberOfChildren-1 ) {
			System.err.println("Error while moving node position: Appending node to the end")
			index = parent.numberOfChildren-1
		}
		
		logger.logRaw(MOVEPOS + SOURCE + source.UUID + INDEX + index)
		
		parent.children.remove(source)
		parent.children.add(index, source)
		
		return source
	}
	
	/**
	 * Moves a node to the specified index in the specified parent
	 * @param source Node to move
	 * @param targetParent The new parent for the source
	 * @param targetIndex Position in targetParents children list
	 * @return the moved Node
	 */
	def move(Node source, Node targetParent, int targetIndex) {
		source.move(targetParent)
		source.move(targetIndex)
		return source
	}
	
	
	/**
	 * Swaps two nodes
	 */
	def swap(Node n1, Node n2) {
		logger.logRaw(SWAP +  SOURCE + n1.UUID + TARGET + n2.UUID)
		val parent1 = n1.parent
		val index1 = parent1.children.indexOf(n1)
		val parent2 = n2.parent
		val index2 = parent2.children.indexOf(n2)
		move(n1, parent2, index2)
		move(n2, parent1, index1)
	}
	
	/**
	 * Deletes a node from the tree by removing it from its parent
	 * @param source Node to be removed
	 */
	def delete(Node source) {
		logger.logRaw(RDELETE + TARGET + source.UUID)
		_delete(source)
		source.parent.children.remove(source)
	}
	
	/**
	 * Logs the uuids of all deleted nodes
	 * @param source Node to be removed
	 */
	private def _delete(Node source) {
		logger.logRaw(DELETE + TARGET + source.UUID)
		source.allChildren.forEach[
			c | logger.logRaw(DELETE + TARGET + c.UUID)
		]
	}
	
	// TODO: Create Nodes Function from Seed-Repository
	
	/**
	 * Returns all children of the given node in depth first order
	 * @param root start node
	 */
	def getAllChildren(Node root) {
		var nodes = newArrayList
		root._getAllChildren(nodes)
		return nodes
	}
	
	private def void _getAllChildren(Node root, List<Node> nodes) {
		root.children.forEach[c | nodes.add(c); c._getAllChildren(nodes)
		]
	}
	
	/** Finds the first element of type belowroot 
	 * @param root start node
	 * @param type node type to find
	 */
	def findFirst(Node root, String type) {
		return root.allChildren.findFirst[n | n.nodeType == type]
	}
	
	
	// Refactoring
	
	
	/** Performs refactoring depending on what type of container is given and 
	 *  replaces the occurences of the old value
	 */
	def refactor(Node container, String newValue) {
		
		if (container instanceof NodeImpl) {
			var Node body;
			val String oldValue = container.getAttributeValue("Name")
			
			// Refactor variable declarations (Class and Methods)
			// Note: also refactors method arguments with the same name as the field which may not be applicable to every language
			if (container.nodeType == "VariableDeclarator") {
				body = container.parent.parent

			// Refactor function Arguments		
			} else if (container.nodeType.startsWith("Argument")) {
				// refactor method argument
				container.setAttributeValue("Name", newValue)
				body = container.parent.parent.children.get(1)
				
			// Refactor method names
			} else if (container.nodeType == "MethodDeclaration") {
				body = container.parent
				
			// Stop
			} else {
				return
			}
			
			logger.logRaw(REFACTOR + CONTAINER + container.UUID + TYPE + container.nodeType + 
				SCOPE + body.UUID + FROM + oldValue + TO + newValue)
			_refactor(body, #["Name", "Initilization", "Value", "Comparison", "Condition", "Update"], oldValue, newValue)
		}
	}
	
	private def _refactor(Node body, List<String> attrKeys, String oldValue, String newValue) {
		attrKeys.forEach[k | _refactor(body, k, oldValue, newValue)]
	}
	
	private def _refactor(Node body, String attrKey, String oldValue, String newValue) {
		// TODO make this robust with regex because right now changing i=>g turns a reference variable=>vargable
		body.allChildren.filter[
			n | n.attributes.exists[
				a | a.attributeKey == attrKey && (a.attributeValues.head.value as String).contains(oldValue) 
			]
		].forEach[
			n | 
			val newAttrValue = n.getAttributeValue(attrKey).replaceAll(oldValue, newValue)
			n.setAttributeValue(attrKey, newAttrValue)
		]
	}
	
	// Attribute Value Helpers
	
	def setAttributeValue(Node node, String attributeKey, String newValue) {
		logger.logRaw(SETATTR + TARGET + node.UUID + KEY + attributeKey + FROM + node.getAttributeValue(attributeKey) + TO + newValue)
		node.getAttributeForKey(attributeKey).attributeValues.head.value = newValue
	}
	
	def String getAttributeValue(Node node, String attributeKey) {
		return node.getAttributeForKey(attributeKey).attributeValues.head.value as String
	}
	
	// utility: nest nodes e.g. wrap statsequence in control block
	
}