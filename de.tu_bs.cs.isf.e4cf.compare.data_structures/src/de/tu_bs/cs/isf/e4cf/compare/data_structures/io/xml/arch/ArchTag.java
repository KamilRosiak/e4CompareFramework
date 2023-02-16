package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.arch;

import java.util.HashMap;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.xml.XMLTagMap;

public class ArchTag {
	
	public static final XMLTagMap MAP;
	
	static {
		HashMap<String, NodeType> mapping = new HashMap<>();

		mapping.put("architecture:Architecture", NodeType.ARCH_ARCHITECTURE);
		mapping.put("components", NodeType.ARCH_COMPONENT);
		mapping.put("signals", NodeType.ARCH_SIGNAL);
		mapping.put("ports", NodeType.ARCH_PORT);
		mapping.put("connectors", NodeType.ARCH_CONNECTOR);
		mapping.put("ownedComments", NodeType.ARCH_OWNED_COMMENT);
		
		MAP = new XMLTagMap(mapping);
	}
}
