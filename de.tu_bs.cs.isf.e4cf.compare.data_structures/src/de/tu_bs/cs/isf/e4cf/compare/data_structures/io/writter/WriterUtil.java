package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.JavaNodeTypes;

public class WriterUtil {
	
	public static String visitWriter(List<Node> l) {
		return "";
	}
	
	public static String visitWriter(Node n) {
		String code = new String();
		
		// What are you, node?
		code += n.getNodeType() + "\n";
		
		if (n.getNodeType() == "Class") {
			for (Attribute a : n.getAttributes()) {
				if (a.getAttributeKey() == JavaNodeTypes.Modifier.toString()) {
					for (String s : a.getAttributeValues()) {
						// look for right sorting!
						code += s + " ";
					}
				}
			}
			code += "class ";
			for (Attribute a : n.getAttributes()) {
				if (a.getAttributeKey() == JavaNodeTypes.Name.toString()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					code += tmpList.get(0); // to get the name, which is an attribute with one value
				}
			}
			
			// here extends
			// then implements
			
			code += "{\n";
			
			// work along block content
			// maybe indentation level must be given to the next recursive step
			
			code += "}\n";
		}
		
		
		
		
		return code;
	}
}