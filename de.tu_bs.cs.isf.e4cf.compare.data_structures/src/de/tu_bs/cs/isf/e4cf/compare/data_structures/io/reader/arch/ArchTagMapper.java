package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.arch;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;

public class ArchTagMapper {
	
	private static Map<String, NodeType> mapping = new HashMap<>();
	
	static {
		mapping.put("xml", NodeType.XML_METADATA);
		mapping.put("architecture:Architecture", NodeType.ARCH_ARCHITECTURE);
		mapping.put("components", NodeType.ARCH_COMPONENT);
		mapping.put("signals", NodeType.ARCH_SIGNAL);
		mapping.put("ports", NodeType.ARCH_PORT);
		mapping.put("connectors", NodeType.ARCH_CONNECTOR);
		mapping.put("ownedComments", NodeType.ARCH_OWNED_COMMENT);
	}
	
	public static Map<String, NodeType> getNameToType() {
		return mapping;
	}
	
	public static Map<NodeType, String> getTypeToName() {
		return invert(getNameToType());
	}
	
	private static Map<NodeType, String> invert(Map<String, NodeType> original) {
		return original.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
	}
}
