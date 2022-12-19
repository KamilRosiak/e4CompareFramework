package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml_reader;

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
	READ_ONLY, STATIC, UNIQUE, TYPE,
	// OPERATION
	QUERY, CONCURRENCY;
}