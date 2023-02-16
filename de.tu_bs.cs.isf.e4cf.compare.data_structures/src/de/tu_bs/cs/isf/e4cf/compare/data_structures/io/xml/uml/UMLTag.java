package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.uml;

import java.util.HashMap;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.XMLTagMap;

public class UMLTag {
	
	public static final XMLTagMap MAP;
	
	static {
		Map<String, NodeType> mapping = new HashMap<>();
		
		mapping.put("uml:Model", NodeType.UML_MODEL);
		mapping.put("packagedElement", NodeType.UML_PACKAGED_ELEMENT);
		mapping.put("generalization", NodeType.UML_GENERALIZATION);
		mapping.put("elementImport", NodeType.UML_ELEMENT_IMPORT);
		mapping.put("ownedAttribute", NodeType.UML_OWNED_ATTRIBUTE);
		mapping.put("ownedEnd", NodeType.UML_OWNED_END);
		mapping.put("ownedParameter", NodeType.UML_OWNED_PARAMETER);
		mapping.put("ownedOperation", NodeType.UML_OWNED_OPERATION);
		mapping.put("ownedLiteral", NodeType.UML_OWNED_LITERAL);
		mapping.put("defaultValue", NodeType.UML_DEFAULT_VALUE);
		mapping.put("lowerValue", NodeType.UML_LOWER_VALUE);
		mapping.put("upperValue", NodeType.UML_UPPER_VALUE);
		mapping.put("type", NodeType.UML_TYPE);
		mapping.put("interfaceRealization", NodeType.UML_INTERFACE_REALIZATION);
		mapping.put("nestedClassifier", NodeType.UML_NESTED_CLASSIFIER);
		mapping.put("name", NodeType.UML_NAME);
		mapping.put("importedElement", NodeType.UML_IMPORTED_ELEMENT);
		
		MAP = new XMLTagMap(mapping);
		
	}	
}
