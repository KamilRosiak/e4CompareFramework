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
import static de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType.*
import static extension de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper.random
import static extension de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper.getAllChildren
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import java.lang.reflect.Method

@Creatable
@Singleton
class Taxonomy {
	
	@Inject CloneHelper helper;
	@Inject CloneLogger logger;
	
	// ==========================================================
	// TYPE I
	// ==========================================================
	
	// Plain text clones not applicable while generating variants
	// Comments and Whitespace Changes not applicable
	// Formatting Changes not applicable
	
	// ==========================================================
	// TYPE II
	// ==========================================================
	
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
		return #[
			this.class.methods.filter[m | m.name == "refactorIdentifiers"],
			this.class.methods.filter[m | m.name == "replaceIdentifier"],
			this.class.methods.filter[m | m.name == "literalChange"],
			this.class.methods.filter[m | m.name == "typeChange"]
		].flatten.random
	}
	
	def performType2Modification(Tree tree) {	
		val m = getType2Method()
		switch (m.name) {
			case "refactorIdentifiers": {
				val declaration = tree.root.allChildren.filter[ n | 
					#[ARGUMENT, CLASS, COMPILATION_UNIT, METHOD_DECLARATION, VARIABLE_DECLARATOR].contains(n.standardizedNodeType) &&
					!n.attributes.filter[a | a.attributeKey == "Name"].nullOrEmpty
				].random
				
				m.tryInvoke(declaration, "I" + new Random().nextInt(Integer.MAX_VALUE))
			}
			
			case "replaceIdentifier": {
				val ident = tree.root.allChildren.filter[ n | 
					#[ARGUMENT, CLASS, COMPILATION_UNIT, EXPRESSION, METHOD_CALL, METHOD_DECLARATION, VARIABLE_DECLARATOR].contains(n.standardizedNodeType) &&
					!n.attributes.filter[a | a.attributeKey == "Name"].nullOrEmpty
				].random	
				
				m.tryInvoke(ident, "N" + new Random().nextInt(Integer.MAX_VALUE))
			}
			
			case "literalChange": {
				val literal = tree.root.allChildren.filter[ n | 
					n.standardizedNodeType == LITERAL
				].random 
				
				// TODO read type field and generate new value accordingly
				m.tryInvoke(literal, "L" + new Random().nextInt(Integer.MAX_VALUE))
			}
			
			case "changeType": {
				val declaration = tree.root.allChildren.filter[ n | 
					#[ARGUMENT, VARIABLE_DECLARATOR].contains(n.standardizedNodeType) && 
					!n.attributes.filter[a | a.attributeKey == "Type"].nullOrEmpty
				].random
				
				m.tryInvoke(declaration, #["boolean", "int", "String", "float", "Object"].random as String)
			}
		}
	}
	
	private def tryInvoke(Method m, Node n, String value) {
		if (n !== null) {
			m.invoke(this, n, value)
		} else {
			System.err.println('''Error with «m.name» modification: No Target Found''')
		}
	}
	
	// ==========================================================
	// TYPE III
	// ==========================================================
	
	/** Adds a given source Node composition to the target Node at specified index */
	def add(Node source, Node target) {
		logger.logRaw(TAX_ADD)
		helper.copyRecursively(source, target)
	}
	
	/** Delete a sub tree */
	def delete(Node target) {
		logger.logRaw(TAX_DELETE)
		helper.delete(target)
	}
	
	/** Moves a source Node composition to a new target parent at specified index */
	def move(Node source, Node target, int index) {
		logger.logRaw(TAX_MOVE)
		helper.move(source, target, index)
	}
	
	/** Returns a random method of clone type III */
	def getType3Method() {
		return #[
			this.class.methods.filter[m | m.name == "add"],
			this.class.methods.filter[m | m.name == "delete"],
			this.class.methods.filter[m | m.name == "move"]
		].flatten.random
	}
	
	def performType3Modification(Tree tree) {	
		val m = getType3Method()
		switch (m.name) {
			case "add",
			case "move": {
				val source = tree.root.allChildren.filter[n | 
					#[ASSIGNMENT, CLASS, CONSTRUCTION, FIELD_DECLARATION,
						^IF, LOOP_COLLECTION_CONTROLLED, LOOP_COUNT_CONTROLLED, LOOP_DO,
						LOOP_WHILE, METHOD_CALL, METHOD_DECLARATION, SWITCH, TRY, VARIABLE_DECLARATION
					].contains(n.standardizedNodeType)
				].random 
				
				if (source === null) {
					System.err.println('''Error with «m.name» modification: No Source found''')
					return;
				}
				
				// no containment source<->target
				// reasonable target depending on source type
				var Node target
				switch (source.standardizedNodeType) {
					// BODY, CASE Targets
					case ASSIGNMENT,
					case ^IF,
					case LOOP_COLLECTION_CONTROLLED,
					case LOOP_COUNT_CONTROLLED,
					case LOOP_DO,
					case LOOP_WHILE,
					case METHOD_CALL,
					case SWITCH,
					case TRY,
					case VARIABLE_DECLARATION: {
						target = tree.root.allChildren.filter[ n | 
							#[BLOCK, CASE].contains(n.standardizedNodeType)
							&& n.UUID != source.UUID // Make sure to not target the same node
							&& !n.allChildren.exists[c | c.UUID == n.UUID] // Watch out for containment as we copy procedurally and run into infinite loops
							&& !source.allChildren.exists[c | c.UUID == n.UUID]
						].random
					}
					// CU, CLASS Targets
					case CONSTRUCTION,
					case FIELD_DECLARATION,
					case METHOD_DECLARATION,
					case CLASS: {
						target = tree.root.allChildren.filter[ n | 
							#[COMPILATION_UNIT, CLASS].contains(n.standardizedNodeType)
							&& n.UUID != source.UUID // Make sure to not target the same node
							&& !n.allChildren.exists[c | c.UUID == n.UUID] // Watch out for containment as we copy procedurally and run into infinite loops
							&& !source.allChildren.exists[c | c.UUID == n.UUID]
						].random
					}
					default: {
						System.err.println('''Error with «m.name» modification: Target for source «source.standardizedNodeType» cannot be determined''')
						return;
					}
				}
				
				if (target === null) {
					System.err.println('''Error with «m.name» modification: No target found.''')
					return;	
				}
				
				if (m.name == "add") {
					// add
					add(source, target)	
					
				} else {
					// move
					val index = target.children.nullOrEmpty ? 0 : new Random().nextInt(target.children.size)
					move(source, target, index)
				}
				
			}
			case "delete": {
				val target = tree.root.allChildren.filter[n | 
					#[ASSIGNMENT, ARGUMENT,	CLASS, CONSTRUCTION, EXPRESSION, FIELD_DECLARATION, 
						^IF, LITERAL, LOOP_COLLECTION_CONTROLLED, LOOP_COUNT_CONTROLLED, LOOP_DO, 
						LOOP_WHILE, METHOD_CALL, METHOD_DECLARATION, SWITCH, TRY, VARIABLE_DECLARATION
					].contains(n.standardizedNodeType) &&
					// Only root expressions may be deleted safely
					(n.standardizedNodeType != EXPRESSION || n.parent.standardizedNodeType != EXPRESSION)
				].random
				
				if (target === null) {
					System.err.println('''Error with «m.name» modification: No target found''')
					return;	
				}
				delete(target)
			}
		}
	}
	
}