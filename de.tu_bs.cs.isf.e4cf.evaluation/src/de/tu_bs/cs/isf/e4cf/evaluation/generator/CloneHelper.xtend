package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonExportService
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonImportService
import java.util.List
import java.util.NoSuchElementException
import java.util.Random
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*

@Creatable
@Singleton
class CloneHelper {
	
	@Inject CloneLogger logger
	@Inject GsonExportService exporter
	@Inject GsonImportService importer
	Tree trackingTree;
	
	/** Sets up the shadow tree, to track modifications on original tree and remove invalid ones */
	def setTrackingTree(Tree original) {
		trackingTree = deepCopy(original)
	}
	
	def getTrackingTree() {
		return trackingTree
	}
	
	def deepCopy(Tree t) {
		val originalTree = exporter.exportTree((t as TreeImpl))
		return importer.importTree(originalTree)
	}
	
	/**
	 * Create a shallow copy of a node and assigns it to a target. 
	 * (Without the source children)
	 * @param source The node to be cloned.
	 * @param targetParent The parent for newly created source clone.
	 * @param preserveUUID If true, the source uuid will be reused instead of a new one being created
	 * @return The clone of the source node
	 */
	def copy(Node source, Node targetParent, boolean preserveUUID) {
		if (!(source instanceof NodeImpl)) {
			return null;
		}
		
		// Create a clone
		val clone = new NodeImpl()
		if (preserveUUID) {
			clone.UUID = UUID.fromString(source.UUID.toString)
		}
		clone.nodeType = source.nodeType
		clone.standardizedNodeType = source.standardizedNodeType
		clone.variabilityClass = source.variabilityClass
		clone.parent = targetParent
		source.attributes.forEach[a | clone.addAttribute( new AttributeImpl(a.attributeKey, a.attributeValues))]
		targetParent.addChild(clone);
		
		// create another identical clone (same UUIDs) on the tracking tree for object persistence and correct logging
		val shadowClone = new NodeImpl()
		shadowClone.nodeType = clone.nodeType
		shadowClone.UUID = UUID.fromString(clone.UUID.toString)
		shadowClone.standardizedNodeType = clone.standardizedNodeType
		shadowClone.variabilityClass = clone.variabilityClass
		val shadowParent = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == targetParent.UUID]
		shadowParent.children.add(shadowClone)
		shadowClone.parent = shadowParent
		source.attributes.forEach[a | shadowClone.addAttribute( new AttributeImpl(a.attributeKey, a.attributeValues))]
		
		// No further check because a copy itself is always a valid mutation
		logger.logRaw(COPY + SOURCE + source.UUID + TARGET + targetParent.UUID + CLONE + clone.UUID);
		return clone;
	}
	
	/**
	 * Creates a deep copy of a subtree.
	 * 
	 * Make sure that the target parent is not contained in source!
	 * 
	 * @param source The subtrees root node to be moved
	 * @param targetParent The parent for newly created source clone
	 * @param preserveUUIDs If true, the sources uuids will be reused instead of new ones being created
	 * @return The clone of the source node
	 */
	def copyRecursively(Node source, Node targetParent, boolean preserveUUIDs) {
		if (!(source instanceof NodeImpl)) {
			return null
		}
		
		logger.logRaw(RCOPY + SOURCE + source.UUID + TARGET + targetParent.UUID)
		val clone = _copyRecursively(source, targetParent, preserveUUIDs)
		return clone;
	}
	
	/**
	 * Creates a deep copy of a subtree.
	 * 
	 * Make sure that the target parent is not contained in source!
	 * 
	 * @param source The subtrees root node to be moved
	 * @param targetParent The parent for newly created source clone
	 * @return The clone of the source node
	 */
	def copyRecursively(Node source, Node targetParent) {
		copyRecursively(source, targetParent, false)
	}
	
	private def NodeImpl _copyRecursively(Node source, Node targetParent, boolean preserveUUIDs) {		
		val clone = copy(source, targetParent, preserveUUIDs)
		source.children.forEach[c | c._copyRecursively(clone, preserveUUIDs)]
		return clone
	}
	
	/**
	 * Moves a node (subtree) by reassigning the references
	 * @param source The subtrees root node to be moved
	 * @param targetParent The new parent for the source
	 * @return always the source
	 */
	def move(Node source, Node targetParent) {
		// Invariant at most one move per source		
		val previousMove = logger.log.findLast[n | n.startsWith(MOVE + SOURCE + source.UUID)]
		val oldParent = source.parent
		
		if(previousMove !== null) {
			logger.log.remove(previousMove)
			
			val originalParentUuid = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == source.UUID].parent.UUID
			// A move back (target parent == original source parent) kills a previous move
			if(originalParentUuid != targetParent.UUID) {
				// If the node was already moved then the target of the previous move operation is replaced by a new one
				// move (a,b) + move (a,c) --> move (a,c)
				logger.logRaw(MOVE + SOURCE + source.UUID + TARGET + targetParent.UUID)
			}
			
		} else {
			// A move to the current parent should not be logged
			if(oldParent.UUID != targetParent.UUID) {
				logger.logRaw(MOVE + SOURCE + source.UUID + TARGET + targetParent.UUID)
			}
		}
		
		// Apply operation on tree
		oldParent.children.remove(source)
		targetParent.addChild(source)
		source.parent = targetParent
		
		// Apply operation on shadow tree
		val shadowSource = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == source.UUID]
		trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == oldParent.UUID].children.remove(shadowSource) // Remove source from old parent
		val shadowTargetParent = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == targetParent.UUID]
		shadowTargetParent.addChild(shadowSource) // add source to new parent
		shadowSource.parent = shadowTargetParent // set parent of source to new parent
		
		return source
	}
	
	/**
	 * Moves a node to the specified index in its current parent
	 * @param source Node to move
	 * @param targetIndex Position in parents children list
	 * @return the moved Node
	 */
	def move(Node source, int targetIndex) {
		// Invariant at most one movepos per source		
		val previousMovePos = logger.log.findLast[n | n.startsWith(MOVEPOS + SOURCE + source.UUID)]
		val parent = source.parent
		var index = targetIndex
		var previousIndex = parent.children.indexOf(source)
		
		if (targetIndex < 0 || targetIndex > parent.numberOfChildren-1 ) {
			System.err.println("Error while moving node position: Appending node to the end")
			index = parent.numberOfChildren-1
		}
		
		if(previousMovePos !== null) {
			logger.log.remove(previousMovePos)
			
			val originalSource = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == source.UUID]
			val originalIndex = originalSource.parent.children.indexOf(originalSource)
			// A move back (target index == original source index) kills a previous move
			if(originalIndex != index) {
				// If the node was already moved then the index of the previous movepos operation is replaced by a new one
				logger.logRaw(MOVEPOS + SOURCE + source.UUID + FROM + originalIndex + TO + index)
			}
			
		} else {
			// A move to the current index should not be logged
			if(previousIndex != index) {
				logger.logRaw(MOVEPOS + SOURCE + source.UUID + FROM + previousIndex + TO + index)
			}
		}
		
		// Apply operation on tree
		parent.children.remove(source)
		parent.children.add(index, source)
		
		// Apply operation on shadow tree
		val shadowParent = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == parent.UUID]
		val shadowSource = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == source.UUID]
		shadowParent.children.remove(shadowSource)
		shadowParent.children.add(index, shadowSource)
		
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
		// remove node subtree
		source.parent.children.remove(source)
		// also remove subtree from tracking tree
		val shadowSource = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID.toString == source.UUID.toString]
		shadowSource.parent.children.remove(shadowSource)
	}
	
	/**
	 * Logs the uuids of all deleted nodes
	 * @param source Node to be removed
	 */
	private def _delete(Node source) {
		
		// Delete invalidates all actions on nodes that are deleted (except clone operations)
		logger.deleteLogsContainingString(source.UUID.toString)
		logger.logRaw(DELETE + TARGET + source.UUID)
		
		source.depthFirstSearch.forEach[
			c | 
			// Delete invalidates all actions on nodes that are deleted (except clone operations)
			logger.deleteLogsContainingString(c.UUID.toString);
			logger.logRaw(DELETE + TARGET + c.UUID);
		]
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
			// CLASS, COMPILATION_UNIT,
			switch (container.standardizedNodeType) {
				case VARIABLE_DECLARATOR: {
					if (container.attributes.filter[a | a.attributeKey == "Name"].nullOrEmpty) {
						//abort
						println("A container for multiple declarations was input here: "+ container.UUID.toString)
						return
					}
					body = container.parent.parent
				}
				case ARGUMENT: {
					// refactor method definition argument itself and every occurrence in the block next to it
					container.setAttributeValue("Name", newValue)
					if (container.parent.parent.standardizedNodeType == NodeType.METHOD_DECLARATION) {
						// Interfaces have no body
						if(container.parent.parent.children.size > 1) {
							body = container.parent.parent.children.get(1)
						}
					} else {
						// probably lambda
						body = container.parent.children.findFirst[n | n.standardizedNodeType == NodeType.BLOCK]
					}
					
					
				}
				case METHOD_DECLARATION: {
					// Scope is the container (~class) of the method definition
					body = container.parent					
				}
				case COMPILATION_UNIT,
				case CLASS: {
					// Note that we do not change anything out of file scope
					body = container
					while (body.standardizedNodeType != NodeType.COMPILATION_UNIT && body.parent !== null) {
						body = body.parent
					}
					
				}
				default: {
					println("Could not refactor node type: " + container.standardizedNodeType)
					return
				}
			}
			
			if(body === null) {
				System.err.println('''Error with refactoring «oldValue»: No body found''')
			} else {
				logger.logRaw(REFACTOR + CONTAINER + container.UUID + TYPE + container.nodeType + SCOPE + body.UUID + FROM + oldValue + TO + newValue)
				_refactor(body, #["Name", "Value"], oldValue, newValue)
			}
		}
	}
	
	private def _refactor(Node body, List<String> attrKeys, String oldValue, String newValue) {
		attrKeys.forEach[k | _refactor(body, k, oldValue, newValue)]
	}
	
	private def _refactor(Node body, String attrKey, String oldValue, String newValue) {
		body.depthFirstSearch.filter[
			n | n.attributes.exists[
				a | a.attributeKey == attrKey && n.getAttributeValue(attrKey) == oldValue
			]
		].forEach[
			n | n.setAttributeValue(attrKey, newValue)
		]
	}
	
	// Attribute Value Helpers
	
	def setAttributeValue(Node node, String attributeKey, String newValue) {
		// Invariant at most one set per source	+ key combination
		val previousSetAttr = logger.log.findLast[n | n.startsWith(SETATTR + TARGET + node.UUID + KEY + attributeKey)]
		
		if(previousSetAttr !== null) {
			logger.log.remove(previousSetAttr)
			
			val originaNode = trackingTree.root.depthFirstSearch.findFirst[n | n.UUID == node.UUID]		
			val originalValue = originaNode.getAttributeValue(attributeKey)
			
			// A move back (new value == original value) kills a previous move
			if(originalValue != newValue) {
				// If the attribute was already set then the value is changed
				logger.logRaw(SETATTR + TARGET + node.UUID + KEY + attributeKey + FROM + originalValue + TO + newValue)
			}
		} else {
			// A change to the current value should not be logged
			if(node.getAttributeValue(attributeKey) != newValue) {
				logger.logRaw(SETATTR + TARGET + node.UUID + KEY + attributeKey + FROM + node.getAttributeValue(attributeKey) + TO + newValue)
			}
		}
		
		// Apply operation on tree
		node.getAttributeForKey(attributeKey).attributeValues.head.value = newValue
	}
	
	def String getAttributeValue(Node node, String attributeKey) {
		try {
			return node.getAttributeForKey(attributeKey).attributeValues.head.value as String
		} catch (NoSuchElementException e) {
			return null
		} catch (NullPointerException e) {
			return null
		}
	}
	
	/** Returns a random entry of an iterable */
	def static <T> random(Iterable<T> l){
		if (l.nullOrEmpty) return null;
		return l.get(new Random().nextInt(l.size))
	}
	
	/** Returns the root node of a node composite */
	def static Node getRoot(Node n) {
		if (n.parent !== null) {
			return n.parent
		} else {
			return n
		}
	}
	
	// utility: nest nodes e.g. wrap statsequence in control block
	
}