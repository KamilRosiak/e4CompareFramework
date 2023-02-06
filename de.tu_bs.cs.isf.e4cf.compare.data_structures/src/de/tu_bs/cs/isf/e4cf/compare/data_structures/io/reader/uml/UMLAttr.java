package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml;

enum UMLAttr {
	// NamedElement
	NAME, VISIBILITY, QUALIFIED_NAME, NAMESPACE,
	// Classifier
	ABSTRACT, FINAL, LEAF,
	// Class
	ACTIVE,
	// Association
	DERIVED,
	// Property
	AGGREGATION, DERIVED_UNION, ID, ORDERED,
	READ_ONLY, STATIC, UNIQUE, TYPE, UPPER, LOWER,
	// OPERATION
	QUERY, CONCURRENCY,
	//ElementImport
	ALIAS,
	// Parameter
	EXCEPTION, STREAM, DIRECTION, EFFECT,
	//Literals
	VALUE,
	// Generalization
	GENERAL, SUBSTITUTABLE, IMPORTED_ELEMENT,
	// Interface Realization
	CLIENT, CONTRACT, SUPPLIER
	;
}