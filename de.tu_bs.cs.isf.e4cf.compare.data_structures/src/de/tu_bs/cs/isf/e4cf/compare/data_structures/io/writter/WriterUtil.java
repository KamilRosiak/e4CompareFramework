package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class WriterUtil {
	
	public static String visitWriter(List<Node> l) {
		return "";
	}
	
	public static String visitWriter(Node n) {
		String code = new String();
		
		// What are you, node?
		code += n.getNodeType() + "\n";
		
		// Run along children
		code += visitWriter(n.getChildren());
		
		return code;
	}
}
