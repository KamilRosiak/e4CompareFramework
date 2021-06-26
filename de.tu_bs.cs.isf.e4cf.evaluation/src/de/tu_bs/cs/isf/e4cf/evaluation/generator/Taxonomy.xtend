/**
 * This Class wraps the clone editing taxonomy of 
 * 2009 - Roy, C. - A Mutation Injectionbased Automatic Framework for Evaluating Code Clone Detection Tools
 * Using the generic data structure and clone helper.
 */
package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

@Creatable
@Singleton
class Taxonomy {
	
	@Inject CloneHelper helper;
	@Inject CloneLogger logger;
	
	// TYPE I
	
	def copyAndPaste(Node source, Node target) {
		logger.logRaw("Tax_CopyPaste")
		helper.copyRecursively(source, target)
	}
	
	// Comments and Whitespace Changes not applicable
	// Formatting Changes not applicable
	
	// TYPE II
	
	/**
	 * @param container is the container of the declaration of the value to modify 
	 */
	def systematicRenaming(Node container, String newValue) {
		logger.logRaw("Tax_SystematicRenaming")
		helper.refactor(container, newValue)
	}
	
	def expressionsForParameters(Node body, String parameter, String expression) {
		logger.logRaw("Tax_ExpressionForParameter")
		helper.getAllChildren(body).forEach[ n |
			n.attributes.forEach[ a |
				helper.setAttributeValue(
					n, 
					a.attributeKey,
					helper.getAttributeValue(n, a.attributeKey).replace(parameter, expression)
				)
			]
		]
	}
	
	/**
	 * Swap two function argument nodes
	 */
	def arbitraryRenaming(Node n1, Node n2) {
		logger.logRaw("Tax_ArbitraryRenaming")
		helper.swap(n1, n2)
	}
	
	// TYPE III
	
	def smallInlineInsertion(Node parent, Node insertion) {
		logger.logRaw("Tax_SmallInlineInsertion_Node")
		helper.move(insertion, parent)
	}
	
	def smallInlineInsertion(Node parent, Node insertion, int index) {
		logger.logRaw("Tax_SmallInlineInsertion_Node")
		helper.move(insertion, parent, index)
	}
	
	def smallInlineInsertion(Node parent, String attributeKey, String appendage) {
		logger.logRaw("Tax_SmallInlineInsertion_Attribute")
		val oldValue = helper.getAttributeValue(parent, attributeKey)
		helper.setAttributeValue(parent, attributeKey, oldValue + appendage)
	}
	
	def smallInlineInsertion(Node parent, String attributeKey, String insertion, int startIndex) {
		logger.logRaw("Tax_SmallInlineInsertion_Attribute")
		val oldValue = helper.getAttributeValue(parent, attributeKey)
		var newValue = oldValue.substring(0, startIndex) + insertion + oldValue.substring(startIndex)
		helper.setAttributeValue(parent, attributeKey, newValue)
	}
	
	def smallInlineDeletion(Node removal) {
		logger.logRaw("Tax_SmallInlineDeletion_Node")
		helper.delete(removal)
	}
	
	def smallInlineDeletion(Node parent, String attributeKey, String removePhrase) {
		logger.logRaw("Tax_SmallInlineDeletion_Attribute")
		var value = helper.getAttributeValue(parent, attributeKey)
		value.replaceAll(removePhrase, "")
		helper.setAttributeValue(parent, attributeKey, value)
	}
	
	def deleteLines(Node... nodes) {
		logger.logRaw("Tax_DeleteLines")
		nodes.forEach[ n |
			helper.delete(n)
		]
	}
	
	def insertLines(Node parent, int targetIndex, Node... insertions) {
		logger.logRaw("Tax_InsertLines")
		var index = targetIndex
		for (n : insertions)
			helper.move(n, parent, index++)
	}
	
	def modifyLines() {
		// TODO This is hard
	}
	
// Removed as we don't consider Type IV clones	
//	def reorderDeclarations() {}
//	
//	def reorderStatements() {}
//	
//	def controlReplacements() {} 
}