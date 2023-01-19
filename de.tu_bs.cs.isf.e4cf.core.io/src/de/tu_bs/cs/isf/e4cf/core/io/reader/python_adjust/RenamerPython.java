package de.tu_bs.cs.isf.e4cf.core.io.reader.python_adjust;


import java.util.HashMap;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.Const;

/**
 * This class is made for renaming NodeTypes for the parsed Tree of python source code.
 * The NodeTypes are renamed so they are more similar to the NodeTypes of Java source code
 * and can be easier compared to Java source code
 * 
 * @author david bumm
 *
 */
public final class RenamerPython {
	private static RenamerPython instance = null;
	private final Map<String, String> map;

	private RenamerPython() {
		map = new HashMap<>();
		fillMap();
	}

	/**
	 * Singleton Constructor, so the hash map only has to be build once.
	 * @return RenamerCpp as a Singleton
	 */
	public static RenamerPython getInstance() {
		if (instance == null) {
			instance = new RenamerPython();
		}
		return instance;
	}

	private void fillMap() {
		map.put("Module", Const.C_UNIT);
		map.put("FunctionDef", Const.M_DECL);
		map.put("If", Const.IF_STMT_BIG);
		map.put("Return", Const.RETURN_STMT);
		map.put("Assign", Const.ASSIGNMENT);

		
	}
	
	

	/**
	 * renames the NodeType of a Node to their equivalent of a Java NodeType.
	 * If the NodeType has no equivalent the NodeType won't be renamed
	 * 
	 * @param node is the Node that might be renamed
	 */
	public void renameNode(Node node) {
		if (!map.containsKey(node.getNodeType())) {

			return;
		}
		String newName = map.get(node.getNodeType());
		node.setNodeType(newName);
	}
}

