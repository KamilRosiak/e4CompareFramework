/**
 * This Class performs edits on a given tree with provided constraints
 * 
 * TODO Ensure Syntactical Correctness after an operation
 */
package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import java.lang.reflect.Method
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType.*
import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*

import static extension de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper.random

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
		val rng = new Random
		switch (m.name) {
			case "refactorIdentifiers": {
				val declaration = tree.root.depthFirstSearch.filter[ n | 
					#[ARGUMENT, CLASS, COMPILATION_UNIT, METHOD_DECLARATION, VARIABLE_DECLARATOR].contains(n.standardizedNodeType) &&
					!n.attributes.filter[a | a.attributeKey == "Name"].nullOrEmpty &&
					n.parent.standardizedNodeType != TEMPLATE
				].random
				
				m.tryInvoke(declaration, "I" + rng.nextInt(Integer.MAX_VALUE))
			}
			
			case "replaceIdentifier": {
				val ident = tree.root.depthFirstSearch.filter[ n |
					// Named expression
					#[ARGUMENT, EXPRESSION, METHOD_DECLARATION, VARIABLE_DECLARATOR //, METHOD_CALL
					].contains(n.standardizedNodeType) &&
					!n.attributes.filter[a | a.attributeKey == "Name"].nullOrEmpty 
				].random
				
				var String newIdent = null
				val identName = helper.getAttributeValue(ident, "Name")
				
				// Find a valid substitute:
				switch (ident.standardizedNodeType) {
					// A variable declaration with the same type (or method with the right return type and no arguments (not implemented))
					case ARGUMENT,
					case EXPRESSION: {
						
						// This could also be a parameter of a calling method (not implemented)
						val declaration = tree.root.depthFirstSearch.findFirst[ n | n.standardizedNodeType == VARIABLE_DECLARATOR
							&& helper.getAttributeValue(n, "Name") == identName
						]
						
						if(declaration === null) {
							System.err.println('''Error with «m.name» modification: No Declaration Found For «identName»''')
						} else {
							
							val substitute = tree.root.depthFirstSearch.findFirst[ n | n.standardizedNodeType == VARIABLE_DECLARATOR
								&& helper.getAttributeValue(declaration, "Type") == helper.getAttributeValue(n, "Type")
								&& n != declaration
							]
							
							if(substitute === null) {
								System.err.println('''Error with «m.name» modification: No Substitute Found For «identName»''')
							} else {
								newIdent = helper.getAttributeValue(substitute, "Name").toString
							}
								
						}
					}
					// A method declaration with the same number of arguments with the same type and the same return type
					case METHOD_CALL: {
						throw new UnsupportedOperationException("Not yet implemented")
					}
					// Can be safely renamed if it was never called anywhere
					case METHOD_DECLARATION: {
						if(tree.getMethodDeclarationCalls(ident).isEmpty) {
							newIdent = "N" + new Random().nextInt(Integer.MAX_VALUE)
						} else {
							System.err.println('''Error with «m.name» modification: Can't Substitute Method Decl «identName», because calls exist''')
						}
					}
					// Can be safely renamed if it was never referenced anywhere
					case VARIABLE_DECLARATOR: {
						var references = tree.root.depthFirstSearch.filter[ n | n.standardizedNodeType == EXPRESSION
							&& helper.getAttributeValue(n, "Name") == identName]
							
						if(references.empty) {
							newIdent = "N" + new Random().nextInt(Integer.MAX_VALUE)
						} else {
							System.err.println('''Error with «m.name» modification: Can't Substitute Variable Decl «identName», because references exist''')
						}
					}
					default: { }
				}
				
				if(newIdent !== null) {
					m.tryInvoke(ident, newIdent)
				}
			}
			
			case "literalChange": {
				val literal = tree.root.depthFirstSearch.filter[ n | 
					n.standardizedNodeType == LITERAL
				].random 
				
				val oldValue = helper.getAttributeValue(literal, "Value")
				var newValue = ""
				
				switch (helper.getAttributeValue(literal, "Type")) {
					case "int",
					case "long": {
						var l = Long.parseLong(oldValue.replaceAll("[Ll]",""))
						var max = Math.min(Math.abs(l), Integer.MAX_VALUE) as int
						max = max == 0 ? max=Short.MAX_VALUE : max=max // we don't want zero
						newValue += rng.nextInt(max) * (Math.signum(l) as int)
					}
					case "char": {
						newValue += rng.nextInt(Character.MAX_VALUE) as char
					}
					case "double",
					case "float": {
						newValue += rng.nextDouble * (Math.signum(Double.parseDouble(oldValue)) as int)
					}
					case "String": {
						newValue = "L" + new Random().nextInt(Integer.MAX_VALUE);
					}
				}
				
				m.tryInvoke(literal, newValue)
			}
			
			case "changeType": {
				val declaration = tree.root.depthFirstSearch.filter[ n | 
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
				val source = tree.root.depthFirstSearch.filter[n | 
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
						target = tree.root.depthFirstSearch.filter[ n | 
							#[BLOCK, CASE].contains(n.standardizedNodeType)
							&& n.UUID != source.UUID // Make sure to not target the same node
							&& !source.depthFirstSearch.exists[c | c.UUID == n.UUID] // The source is not allowed to contain the target
						].random
					}
					// CU, CLASS Targets
					case CONSTRUCTION,
					case FIELD_DECLARATION,
					case METHOD_DECLARATION,
					case CLASS: {
						target = tree.root.depthFirstSearch.filter[ n | 
							#[COMPILATION_UNIT, CLASS].contains(n.standardizedNodeType)
							&& n.UUID != source.UUID // Make sure to not target the same node
							&& !source.depthFirstSearch.exists[c | c.UUID == n.UUID] // The source is not allowed to contain the target
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
				val target = tree.root.depthFirstSearch.filter[n | 
					// Assignments, if, loops, switch, try can be deleted safely, method calls
					#[ASSIGNMENT, CONSTRUCTION, EXPRESSION, FIELD_DECLARATION, 
						^IF, LITERAL, LOOP_COLLECTION_CONTROLLED, LOOP_COUNT_CONTROLLED, LOOP_DO, 
						LOOP_WHILE, METHOD_CALL, METHOD_DECLARATION, SWITCH, TRY, VARIABLE_DECLARATION
						// , CLASS, ARGUMENT
					].contains(n.standardizedNodeType) &&
					// In general subexpressions of any kind can't be deleted safely
					n.parent.standardizedNodeType != EXPRESSION
				].random
				
				var isViable = false;
				
				if (target === null) {
					System.err.println('''Error with «m.name» modification: No target found''')
					return;	
				}
				
				// Further restrictions
				switch (target.standardizedNodeType) {
					// Only literals at the end of an variable declaration can be deleted safely (-> uninitialized variable)
					case LITERAL: {
						if(target.parent.standardizedNodeType == VARIABLE_DECLARATOR) isViable = true;
					}
					// Only variable and field declarations that are not referenced (unused) can be deleted safely
					case VARIABLE_DECLARATION,
					case FIELD_DECLARATION: {
						// Uses the name in the variable declarator
						var references = tree.root.depthFirstSearch.filter[ n | n.standardizedNodeType == EXPRESSION
							&& helper.getAttributeValue(target.children.head, "Name") == helper.getAttributeValue(n, "Name")]
							
						if(references.isEmpty) isViable = true;
					}
					// Only method declarations that are not called (unused) can be deleted safely
					case METHOD_DECLARATION: {
						if(tree.getMethodDeclarationCalls(target).isEmpty) isViable = true;
					}
					// Only constructions that are not called anywhere (same arguments) can be deleted safely
					case CONSTRUCTION: {
						// So they should not be the type of an object creation expression
						var calls = tree.root.depthFirstSearch.filter[ n | n.standardizedNodeType == EXPRESSION
							&& helper.getAttributeValue(target, "Name") == helper.getAttributeValue(n, "Type")]
							
						if(calls.isEmpty) isViable = true;
					}
					case ARGUMENT: {
						// Only arguments of methods that are not called anywhere (same arguments)
						// And that are not used in the function below (e.g. as arguments or in expressions) can be deleted safely
//						if (target.parent.parent.standardizedNodeType == METHOD_DECLARATION) {
//							if(tree.getMethodDeclarationCalls(target.parent.parent).isEmpty) isViable = true;
//						}
						throw new UnsupportedOperationException("Not yet implemented")
					}
					// Only classes that are not used (construction, field / variable, type cast, ...) can be deleted safely
					case CLASS: {
						throw new UnsupportedOperationException("Not yet implemented")
					}
					default: {
						isViable = true
					}
				}
				
				if (!isViable) {
					System.err.println('''Error with «m.name» modification: Target not viable for «target.UUID.toString»''')
					return;
				}
				delete(target)
			}
		}
	}
	
	def getMethodDeclarationCalls(Tree tree, Node methodDecl) {
		return tree.root.depthFirstSearch.filter[ n | n.standardizedNodeType == METHOD_CALL
			&& helper.getAttributeValue(methodDecl, "Name") == helper.getAttributeValue(n, "Name")
			// Deal with polymorphism
			// Same attribute count
			&& n.children.head.children.size == methodDecl.children.head.children.size
			// Ideal check same attribute types here
			]
	}	
}