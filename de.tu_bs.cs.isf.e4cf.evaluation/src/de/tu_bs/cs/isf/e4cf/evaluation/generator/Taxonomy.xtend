/**
 * This Class wraps the clone editing taxonomy of 
 * 2009 - Roy, C. - A Mutation Injectionbased Automatic Framework for Evaluating Code Clone Detection Tools
 * Using the generic data structure and clone helper.
 */
package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*

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
	
	/** Returns a random method of clone type I */
	def getType1Method() {
		return this.class.methods.findFirst[m | m.name == "copyAndPaste"]
	}
	
	// TYPE II
	
	/**
	 * @param container is the container of the declaration of the value to modify 
	 */
	def systematicRenaming(Node container, String newValue) {
		logger.logRaw(SYSTEMATIC_RENAMING)
		helper.refactor(container, newValue)
	}
	
	def expressionsForParameters(Node scope, String parameter, String expression) {
		// logger.logRaw(EXPRESSION)
		// TODO: this would take a lot of work for a result that may not always make sense (type- and operational semantics)
		// generally one would:
		// build expression from NodeImpl (!!HARD!!), maybe reduce possibilities to arithmetics
		// replace all occurrences of the parameter NameExpressions with the build expression tree
	}
	
	/**
	 * Swap two function argument nodes
	 */
	def arbitraryRenaming(Node n1, Node n2) {
		logger.logRaw(ARBITRARY_RENAMING)
		helper.swap(n1, n2)
	}
	
	/** Returns a random method of clone type II */
	def getType2Method() {
		val rng = new Random()
		val type2Methods = #[
			this.class.methods.filter[m | m.name == "systematicRenaming"],
			this.class.methods.filter[m | m.name == "expressionsForParameters"],
			this.class.methods.filter[m | m.name == "arbitraryRenaming"]
		].flatten
		return type2Methods.get(rng.nextInt(type2Methods.size));
	}
	
	// TYPE III
	
	def smallInlineInsertion(Node parent, Node insertion) {
		logger.logRaw(INLINE_INSERTION_NODE)
		helper.move(insertion, parent)
	}
	
	def smallInlineInsertion(Node parent, Node insertion, int index) {
		logger.logRaw(INLINE_INSERTION_NODE)
		helper.move(insertion, parent, index)
	}
	
	def smallInlineInsertion(Node parent, String attributeKey, String appendage) {
		logger.logRaw(INLINE_INSERTION_ATTR)
		val oldValue = helper.getAttributeValue(parent, attributeKey)
		helper.setAttributeValue(parent, attributeKey, oldValue + appendage)
	}
	
	def smallInlineInsertion(Node parent, String attributeKey, String insertion, int startIndex) {
		logger.logRaw(INLINE_INSERTION_ATTR)
		val oldValue = helper.getAttributeValue(parent, attributeKey)
		var newValue = oldValue.substring(0, startIndex) + insertion + oldValue.substring(startIndex)
		helper.setAttributeValue(parent, attributeKey, newValue)
	}
	
	def smallInlineDeletion(Node removal) {
		logger.logRaw(INLINE_DELETION_NODE)
		helper.delete(removal)
	}
	
	def smallInlineDeletion(Node parent, String attributeKey, String removePhrase) {
		logger.logRaw(INLINE_DELETION_ATTR)
		var value = helper.getAttributeValue(parent, attributeKey)
		value.replaceAll(removePhrase, "")
		helper.setAttributeValue(parent, attributeKey, value)
	}
	
	def deleteLines(Node... nodes) {
		logger.logRaw(DELETE_LINES)
		nodes.forEach[ n |
			helper.delete(n)
		]
	}
	
	def insertLines(Node parent, int targetIndex, Node... insertions) {
		logger.logRaw(INSERT_LINES)
		var index = targetIndex
		for (n : insertions)
			helper.move(n, parent, index++)
	}
	
	def modifyLines() {
		// TODO This is hard
	}
	
	/** Returns a random method of clone type III */
	def getType3Method() {
		val rng = new Random()
		val type3Methods = #[
			this.class.methods.filter[m | m.name == "smallInlineInsertion"],
			this.class.methods.filter[m | m.name == "smallInlineDeletion"],
			this.class.methods.filter[m | m.name == "deleteLines"],
			this.class.methods.filter[m | m.name == "insertLines"]/*, TODO add once modifyLines is implemented
			this.class.methods.filter[m | m.name == "insertLines"], */
		].flatten
		return type3Methods.get(rng.nextInt(type3Methods.size));
	}
	
// Removed as we don't consider Type IV clones	
//	def reorderDeclarations() {}
//	
//	def reorderStatements() {}
//	
//	def controlReplacements() {} 
}