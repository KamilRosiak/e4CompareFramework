package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import java.util.List
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl

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
		
		logger.logRaw("Copy source:" + source.UUID + " target:" + targetParent.UUID + " clone:" + clone.UUID);
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
		
		logger.logRaw("RCopy source:" + source.UUID + " target:" + targetParent.UUID)
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
		logger.logRaw("Move source:" + source.UUID + " target:" + targetParent.UUID)
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
		
		logger.logRaw("MovePos source:" + source.UUID + " index:" + index)
		
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
	 * Deletes a node from the tree by removing it from its parent
	 * @param source Node to be removed
	 */
	def delete(Node source) {
		logger.logRaw("Delete " + source.UUID)
		source.parent.children.remove(source)
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
	
	def findFirst(Node root, String type) {
		return root.allChildren.findFirst[n | n.nodeType == "VariableDeclarator"]
	}
	
	def refactor(Node container, String newValue) {
		
		if (container instanceof NodeImpl) {
			
			// Refactor variable declarations
			if (container.nodeType == "VariableDeclarator") {
				
				val oldValue = container.getAttributeForKey("Name").attributeValues.head.value.toString
				// All children in the same scope of the changed var with the same name attribute
				val body = container.parent.parent
				body.allChildren.filter[
					n | n.attributes.exists[
						a | a.attributeKey == "Name" && (a.attributeValues.head.value as String) == oldValue
					]
				].forEach[
					n | n.getAttributeForKey("Name").attributeValues.remove(0);
					n.getAttributeForKey("Name").addAttributeValue(new StringValueImpl(newValue))
				]
				// All children in the same scope of the changed var with the variable contained in a value attribute
				// TODO make this robust with regex because right now changing i=>g turns a reference variable=>vargable
				body.allChildren.filter[
					n | n.attributes.exists[
						a | a.attributeKey == "Value" && (a.attributeValues.head.value as String).contains(oldValue) 
					]
				].forEach[
					n | var v = (n.getAttributeForKey("Value").attributeValues.head.value as String);
					n.getAttributeForKey("Value").attributeValues.head.value = v.replaceAll(oldValue, newValue);
				]
			
			// Refactor function Arguments		
			} else if (container.nodeType.startsWith("Argument")) {
				
				// TODO change in Initilization, Name, Value
				container.getAttributeForKey("Name").attributeValues.head.value = newValue
				val oldValue = container.getAttributeForKey("Name").attributeValues.head.value.toString
				val body = container.parent.parent.children.get(1)
				body.allChildren.filter[
					n | n.attributes.exists[
						a | (a.attributeKey == "Initilization" || a.attributeKey == "Name" || a.attributeKey == "Value") 
							&& (a.attributeValues.head.value as String).contains(oldValue)
					]
				]
				
				
			}
			
			
		}
		
		/* what can be refactored:
		 * 1. variables in Declarations
		 * 	- Scope: Body Downwards
		 * 2. method arguments in Declaration/Arguments
		 *  - Scope: Function Body Downwards
		 * 3. Class Fields
		 *  - Scope: Class Wide
		 * 4. Method Names
		 *  - Scope: Language Dependent! -> Unsupported
		*/
		
	}
	
}