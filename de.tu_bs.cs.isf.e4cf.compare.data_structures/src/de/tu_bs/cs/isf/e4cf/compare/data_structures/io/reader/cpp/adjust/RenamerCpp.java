package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust;

import java.util.HashMap;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class is made for renaming NodeTypes for the parsed Tree of c++ source code.
 * The NodeTypes are renamed so they are more similar to the NodeTypes of Java source code
 * and can be easier compared to Java source code
 * 
 * @author david bumm
 *
 */
public final class RenamerCpp {
	private static RenamerCpp instance = null;
	private final Map<String, String> map;

	private RenamerCpp() {
		map = new HashMap<>();
		fillMap();
	}

	/**
	 * Singleton Constructor, so the hash map only has to be build once.
	 * @return RenamerCpp as a Singleton
	 */
	public static RenamerCpp getInstance() {
		if (instance == null) {
			instance = new RenamerCpp();
		}
		return instance;
	}

	private void fillMap() {
		map.put(Const.FUNCTION, Const.M_DECL);
		map.put(Const.PARAM_LIST, Const.ARGUMENT_BIG);
		map.put(Const.PARAM, Const.ARGUMENT_BIG);
		map.put(Const.BLOCK, Const.BODY);
		map.put(Const.DECL, Const.FIELD_DECL);
		map.put(Const.CONSTRUCTOR, Const.CONSTRUCTOR_DECLARATION);
		map.put(Const.IF, Const.IF_STMT_BIG);
		map.put(Const.ELSE_SMALL, Const.ELSE_BIG);
		map.put(Const.FOR, Const.FOR_STMT);
		map.put(Const.WHILE, Const.WHILE_STMT);
		map.put(Const.DO, Const.DO_STMT);
		map.put(Const.RETURN, Const.RETURN_STMT);
		map.put(Const.BREAK_SMALL, Const.BREAK_BIG);
		map.put(Const.CONTINUTE_SMALL, Const.CONTINUTE_BIG);
		map.put(Const.EMPTY_STMT_SMALL, Const.EMPTY_STMT_BIG);
		map.put(Const.LABEL, Const.LABEL_STMT);
		map.put(Const.SWITCH, Const.SWITCH_STMT);
		map.put(Const.THEN_SMALL, Const.THEN_BIG);
		map.put(Const.FUN_DECL, Const.M_DECL);
		map.put(Const.LAMBDA, Const.LAMBDA_EXPR);
		map.put(Const.DECL_STMT, Const.FIELD_DECL);
		map.put(Const.CONSTRUCTOR_DECL, Const.CONSTRUCTOR_DECLARATION);
		map.put(Const.ENUM_DECL, Const.ENUM_DECLARATION);
		map.put(Const.ENUM, Const.ENUM_DECLARATION);
		map.put(Const.CATCH, Const.CATCH_CLAUSE);
		map.put(Const.TRY, Const.TRY_STMT);
		map.put(Const.THROW, Const.THROW_STMT);
		map.put(Const.COMMENT_SMALL, Const.LINE_COMMENT);
		map.put(Const.ARG_LIST, Const.ARGUMENT_BIG);
		map.put(Const.BLOCK_CONTENT,Const.BODY);
		map.put(Const.INIT, Const.INITIALIZATION);
		map.put(Const.CONDITION_SMALL, Const.CONDITION_BIG);
		map.put(Const.INCR, Const.UPDATE);
		map.put(Const.NAME_SMALL, Const.NAME_BIG);
		map.put(Const.CASE, Const.SWITCH_ENTRY);
		map.put(Const.DEFAULT_SMALL, Const.SWITCH_ENTRY);
		map.put(Const.CALL, Const.METHOD_CALL);
		
		addLiterals();

	}
	
	private void addLiterals() {
		map.put(Const.INT, Const.INT_LIT);
		map.put(Const.DOUBLE, Const.DOUBLE_LIT);
		map.put(Const.BOOL, Const.BOOLEAN_LIT);
		map.put(Const.CHAR, Const.CHAR_LIT);
		map.put(Const.LONG, Const.LONG_LIT);
		map.put(Const.STRING, Const.STRING_LIT);
		map.put(Const.NULL, Const.NULL_LIT);
		map.put(Const.FLOAT, Const.FLOAT_LIT);
	}

	/**
	 * renames the NodeType of a Node to their equivalent of a Java NodeType.
	 * If the NodeType has no equivalent (e.g "using namespace") the NodeType won't be renamed
	 * 
	 * @param node is the Node that might be renamed
	 */
	public void renameNode(Node node) {
		if (!map.containsKey(node.getNodeType())) {
			if (node.getNodeType().equals(Const.LITERAL)) {
				renameLiteral(node);
			}
			return;
		}
		String newName = map.get(node.getNodeType());
		node.setNodeType(newName);
	}
	
	private void renameLiteral(Node node) {
		Node parent = node;
		while (!parent.getNodeType().equals(Const.FIELD_DECL)) {	
			parent = parent.getParent();
			if (parent == null) {
				return;
			}
		}
		String value = null;
		for (Attribute attribute: parent.getAttributes()) {
			if (attribute.getAttributeKey().equals(Const.TYPE_BIG)) {
				value = attribute.getAttributeValues().get(0).getValue().toString();
				if (map.containsKey(value)) {
					String newName = map.get(value);
					node.setNodeType(newName);
				}
				node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(value)));
				return;
			}
		}
		
	}
}
