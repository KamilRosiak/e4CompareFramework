package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader;

import java.util.HashMap;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public final class RenamerCpp {
	private static RenamerCpp instance = null;
	private Map<String,String> map;
	
	private RenamerCpp()
	{
		map = new HashMap<>();
		fillMap();
	}
	
	public static RenamerCpp getInstance()
	{
		if (instance == null) {
			instance = new RenamerCpp();
		}
		return instance;
	}
	
	private void fillMap()
	{
		map.put("function", "MethodDeclaration");
		map.put("parameter_list", "Argument");
		map.put("parameter", "Argument");
		map.put("block", "Body");
		map.put("include", "Import");
		map.put("decl", "FieldDeclaration");
		map.put("constructor", "ConstructorDeclaration");
		map.put("expr_stmt", "UnaryExpr");
		map.put("if", "IfStmt");
		map.put("else", "Else");
		map.put("for", "ForStmt");
		map.put("while", "WhileStmt");
		map.put("do", "DoStmt");
		map.put("return", "ReturnStmt");
		map.put("break", "Break");
		map.put("continue", "Continue");
	}
	
	public void renameNode(Node node)
	{
		if (!map.containsKey(node.getNodeType())) {
			return;
		}
		String newName = map.get(node.getNodeType());
		node.setNodeType(newName);
	}
}
