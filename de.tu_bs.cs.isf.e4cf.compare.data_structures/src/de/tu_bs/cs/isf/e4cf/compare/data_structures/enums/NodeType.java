package de.tu_bs.cs.isf.e4cf.compare.data_structures.enums;

/**
 * This enumeration holds standardized node types that occur independently of the tree source 
 * (as in: it doesn't matter if the tree is generated form directories, java-source, c++ source...)
 */
public enum NodeType {
	// TODO: Remove `// INTERESTING` marks from clone generator
	ANNOTATION,
	ARGUMENT, // INTERESTING
	ASSERTION,
	ASSIGNMENT, // INTERESTING
	BLOCK, // INTERESTING
	CASE,
	CAST,
	CATCH,
	CLASS, // INTERESTING
	COMMENT,
	/** 
	 * A container for programming artifacts (source code, imports...)
	 */
	COMPILATION_UNIT,
	CONSTRUCTION,
	DIRECTORY,
	ELSE,
	ENUM,
	EXPRESSION, // INTERESTING
	FIELD_DECLARATION, // INTERESTING
	FILE,
	FINALLY,
	IF,
	/** 
	 * Addition of compiled artifacts to a compilation unit 
	 */
	IMPORT,
	/** 
	 * Addition of source code artifacts to a compilation unit
	 */
	INCLUDE,
	INTERFACE,
	/** 
	 * Used to express a conjunction of some sets, e.g. for expressing inheritance of multiple 
	 * super classes without creating an explicit class.
	 */
	INTERSECTION,
	/**
	 * e.g. break, GOTO, continue, return, yield
	 */
	JUMP,
	/** 
	 * Source code that defined marks to jump to e.g. for GOTO or break statements 
	 */
	LABELED_STATEMENT,
	LAMBDA_EXPRESSION,
	LITERAL,
	LOOP_COLLECTION_CONTROLLED,
	LOOP_DO,
	LOOP_COUNT_CONTROLLED,
	LOOP_WHILE,
	METHOD_CALL, // INTERESTING
	METHOD_DECLARATION, // INTERESTING
	/**
	 * Usually denoted by `::`, e.g. Java Method Reference Expression
	 */
	REFERENCE,
	STRUCT,
	SWITCH,
	/**
	 * Nodes that explicitly take over thread safety, e.g. Java's `synchronized`, Locks, Mutex...
	 */
	SYNCHRONIZE,
	/**
	 * Also known as generics
	 */
	TEMPLATE,
	TERNARY,
	THEN,
	THROW,
	TRY,
	/**
	 * Used to compare the type of an artifact against another type, e.g. Java's `instanceof`
	 */
	TYPE_CHECK,
	/**
	 * Default Node Type if no other is defined via the interface.
	 * May also be used when the node does not represent anything fitting into other types
	 */
	UNDEFINED,
	/** 
	 * Used to express a disjunction of some sets, e.g. for expressing inheritance of  
	 * one or more super classes of a given set.
	 */
	UNION,
	VARIABLE_DECLARATION, // INTERESTING
	;
	
	public static NodeType fromString(String name) {
	    try {
	    	return valueOf(name);
	    } catch (IllegalArgumentException ex) {
	    	System.err.println("Tried to create unkown Standardized Node Type: " + name);
	    	return UNDEFINED;
	    }
	}
}
