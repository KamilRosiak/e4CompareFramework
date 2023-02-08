package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml;

import java.util.Map;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;

public class XMLTagMap {
	private final Map<String, NodeType> mapping;
	
	public XMLTagMap(Map<String, NodeType> mapping) {
		this.mapping = mapping;
	}
	
	public Map<String, NodeType> nameToType() {
		return mapping;
	}

	public Map<NodeType, String> typeToName() {
		return invertMapping(nameToType());
	}
	
	private static Map<NodeType, String> invertMapping(Map<String, NodeType> original) {
		return original.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
	}
}
