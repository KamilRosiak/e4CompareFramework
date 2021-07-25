/**
 * This Class performs edits on a given tree with provided constraints
 * 
 * TODO Ensure Syntactical Correctness after an operation
 */
package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*
import static extension de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper.random
import static extension de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper.getAllChildren
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType
import java.lang.reflect.Method

@Creatable
@Singleton
class Taxonomy {
	
	@Inject CloneHelper helper;
	@Inject CloneLogger logger;
	
	// TYPE I
	
	// Plain text clones not applicable while generating variants
	// Comments and Whitespace Changes not applicable
	// Formatting Changes not applicable
	
	// TYPE II
	
	/**
	 * @param container is the container of the declaration of the value to modify 
	 */
	def refactorIdentifiers(Node container, String newValue) {
		logger.logRaw(REFACTOR_IDENT)
		helper.refactor(container, newValue)
	}
	
	def replaceIdentifier(Node container, String newName) {
		logger.logRaw(REPLACE_IDENT)
		helper.setAttributeValue(container, "Name", newName)
	}
	
	def literalChange(Node container, String newValue) {
		logger.logRaw(LITERAL_CHANGE)
		helper.setAttributeValue(container, "Value", newValue)
	}
	
	def typeChange(Node container, String newType) {
		logger.logRaw(TYPE_CHANGE)
		helper.setAttributeValue(container, "Type", newType)
	}
	
	/** Returns a random method of clone type II */
	def getType2Method() {
		val rng = new Random()
		val type2Methods = #[
			this.class.methods.filter[m | m.name == "refactorIdentifiers"],
			this.class.methods.filter[m | m.name == "replaceIdentifier"],
			this.class.methods.filter[m | m.name == "literalChange"],
			this.class.methods.filter[m | m.name == "changeType"]
		].flatten
		return type2Methods.get(rng.nextInt(type2Methods.size));
	}
	
	def performType2Modification(Tree tree) {	
		val m = getType2Method()
		switch (m.name) {
			case "refactorIdentifiers": {
				val declaration = tree.root.allChildren.filter[ n | 
					n.standardizedNodeType == NodeType.VARIABLE_DECLARATION 
					&& !n.attributes.filter[a | a.attributeKey == "Name"].nullOrEmpty
				].random as Node	
				
				m.tryInvoke(declaration, "I" + new Random().nextInt(Integer.MAX_VALUE))
			}
			
			case "replaceIdentifier": {
				val ident = tree.root.allChildren.filter[ n | 
					!n.attributes.filter[a | a.attributeKey == "Name"].nullOrEmpty
				].random as Node	
				
				m.tryInvoke(ident, "N" + new Random().nextInt(Integer.MAX_VALUE))
			}
			
			case "literalChange": {
				val literal = tree.root.allChildren.filter[ n | 
					n.standardizedNodeType == NodeType.LITERAL
				].random as Node 
				
				m.tryInvoke(literal, "L" + new Random().nextInt(Integer.MAX_VALUE))
			}
			
			case "changeType": {
				val declaration = tree.root.allChildren.filter[ n | 
					n.standardizedNodeType == NodeType.VARIABLE_DECLARATION 
					&& !n.attributes.filter[a | a.attributeKey == "Type"].nullOrEmpty
				].random as Node 
				
				m.tryInvoke(declaration, #["boolean", "int", "String", "float", "Object"].random as String)
			}
		}
	}
	
	private def tryInvoke(Method m, Node n, String value) {
		if (n !== null) {
			m.invoke(this, n, value)
		} else {
			System.err.println('''Error with «m.name» modification''')  // TODO: how often does this happen?
		}
	}
	
	// TYPE III
	
	def copyAndPaste(Node source, Node target) {
		logger.logRaw("Tax_CopyPaste")
		helper.copyRecursively(source, target)
	}
	
}