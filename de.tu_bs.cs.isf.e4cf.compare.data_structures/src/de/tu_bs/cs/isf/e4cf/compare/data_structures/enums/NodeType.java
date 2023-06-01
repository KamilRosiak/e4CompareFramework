package de.tu_bs.cs.isf.e4cf.compare.data_structures.enums;

/**
 * This enumeration holds standardized node types that occur independently of
 * the tree source (as in: it doesn't matter if the tree is generated form
 * directories, java-source, c++ source...)
 */
public enum NodeType {
	// TODO: Remove `// INTERESTING` marks from clone generator
	ANNOTATION, ARGUMENT, // INTERESTING
	ASSERTION, ASSIGNMENT, // INTERESTING
	BLOCK, // INTERESTING
	CASE, CAST, CATCH, CLASS, // INTERESTING
	COMMENT,
	/**
	 * A container for programming artifacts (source code, imports...)
	 */
	COMPILATION_UNIT, CONDITION, CONSTRUCTION, DIRECTORY, ELSE, ENUM, EXPRESSION, // INTERESTING
	FIELD_DECLARATION, // INTERESTING
	FILE, FINALLY, IF,
	/**
	 * Addition of compiled artifacts to a compilation unit
	 */
	IMPORT,
	/**
	 * Addition of source code artifacts to a compilation unit
	 */
	INCLUDE,
	/**
	 * Used to group expressions in count controlled for statements. Sometimes
	 * necessary to differentiate between init, update and body
	 */
	INITIALIZATION, INTERFACE,
	/**
	 * Used to express a conjunction of some sets, e.g. for expressing inheritance
	 * of multiple super classes without creating an explicit class.
	 */
	INTERSECTION,
	/**
	 * e.g. break, GOTO, continue, return, yield
	 */
	JUMP,
	/**
	 * Source code that defined marks to jump to e.g. for GOTO or break statements
	 */
	LABELED_STATEMENT, LAMBDA_EXPRESSION, LITERAL, LOOP_COLLECTION_CONTROLLED, LOOP_DO, LOOP_COUNT_CONTROLLED,
	LOOP_WHILE, METHOD_CALL, // INTERESTING
	METHOD_DECLARATION, // INTERESTING
	FOR,
	/**
	 * Usually denoted by `::`, e.g. Java Method Reference Expression
	 */
	REFERENCE, STRUCT, SWITCH,
	/**
	 * Nodes that explicitly take over thread safety, e.g. Java's `synchronized`,
	 * Locks, Mutex...
	 */
	SYNCHRONIZE,
	/**
	 * Also known as generics
	 */
	TEMPLATE, TERNARY, THEN, THROW, TRY,
	/**
	 * Used to compare the type of an artifact against another type, e.g. Java's
	 * `instanceof`
	 */
	TYPE_CHECK,
	/**
	 * Default Node Type if no other is defined via the interface. May also be used
	 * when the node does not represent anything fitting into other types
	 */
	UNDEFINED,
	/**
	 * Used to express a disjunction of some sets, e.g. for expressing inheritance
	 * of one or more super classes of a given set.
	 */
	UNION,
	/**
	 * Used in iterator controlled loops (for statements) to distinguish between
	 * body and update expressions
	 */
	UPDATE, VARIABLE_DECLARATION, // INTERESTING
	VARIABLE_DECLARATOR,
	// UML component types
	UML_PACKAGED_ELEMENT, UML_OWNED_ATTRIBUTE, UML_OWNED_END, UML_OWNED_PARAMETER, UML_NESTED_CLASSIFIER,
	UML_ELEMENT_IMPORT, UML_MODEL, UML_TYPE, UML_OWNED_OPERATION, UML_OWNED_LITERAL, UML_LOWER_VALUE, UML_UPPER_VALUE,
	UML_GENERALIZATION, UML_DEFAULT_VALUE, UML_INTERFACE_REALIZATION, UML_IMPORTED_ELEMENT, UML_NAME,

	// XML types
	XML_METADATA, XML_ATTRIBUTE, XML_CONTENT,

	// ARCH TYPES
	ARCH_ARCHITECTURE, ARCH_SIGNAL, ARCH_COMPONENT, ARCH_PORT, ARCH_CONNECTOR, ARCH_OWNED_COMMENT,

	// IEC61131-3 Types
	POUS, POU, CONFIGURATION, VARIABLE, GLOBAL_VARIABELS, RESOURCE, RESOURCES, TASKS, TASK, ACTIONS, ACTION,
	IMPLEMENTATION, STEP, INCOMING_TRANSISTIONS, OUTGOING_TRANSISTIONS, PARAMETER, PARAMETERS, IF_BLOCK, OUTGOING_TRANSISTION, INCOMING_TRANSISTION;

	;

	public static NodeType fromString(String name) {
		try {
			return valueOf(name);
		} catch (IllegalArgumentException ex) {
			if (VERBOSE) {
				System.err.println("Tried to create unkown Standardized Node Type: " + name);
			}

			return UNDEFINED;
		}
	}

	public static boolean VERBOSE;
}
