package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

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
		map.put("function", "MethodDeclaration");
		map.put("parameter_list", "Argument");
		map.put("parameter", "Argument");
		map.put("block", "Body");
		map.put("include", "Import");
		map.put("decl", "FieldDeclaration");
		map.put("constructor", "ConstructorDeclaration");
		map.put("if", "IfStmt");
		map.put("else", "Else");
		map.put("for", "ForStmt");
		map.put("while", "WhileStmt");
		map.put("do", "DoStmt");
		map.put("return", "ReturnStmt");
		map.put("break", "Break");
		map.put("continue", "Continue");
		map.put("empty_stmt", "EmptyStmt");
		map.put("label", "LabeledStmt");
		map.put("switch", "SwitchStmt");
		map.put("then", "Then");
		map.put("function_decl", "MethodDeclaration");
		map.put("lambda", "LambdaExpr");
		map.put("decl_stmt", "FieldDeclaration");
		map.put("class_decl", "LocalClassDeclarationStmt");
		map.put("constructor_decl", "ConstructorDeclaration");
		map.put("enum_decl", "EnumDeclaration");
		map.put("enum", "EnumDeclaration");
		map.put("catch", "CatchClause");
		map.put("try", "TryStmt");
		map.put("throw", "ThrowStmt");
		map.put("comment", "LineComment");
		map.put("argument_list", "Argument");
		map.put("block_content", "Body");
		map.put("init", "Initialization");
		map.put("condition", "Condition");
		map.put("incr", "Update");
		map.put("name", "Name");
		map.put("case", "SwitchEntry");
		map.put("default", "SwitchEntry");
		
		addLiterals();

	}
	
	private void addLiterals() {
		map.put("int", "IntegerLiteralExpr");
		map.put("double", "DoubleLiteralExpr");
		map.put("bool", "BooleanLiteralExpr");
		map.put("char", "CharLiteralExpr");
		map.put("long", "LongLiteralExpr");
		map.put("String", "StringLiteralExpr");
		map.put("null", "NullLiteralExpr");
		map.put("float", "FloatLiteralExpr");
	}

	/**
	 * renames the NodeType of a Node to their equivalent of a Java NodeType.
	 * If the NodeType has no equivalent (e.g "using namespace") the NodeType won't be renamed
	 * 
	 * @param node is the Node that might be renamed
	 */
	public void renameNode(Node node) {
		if (!map.containsKey(node.getNodeType())) {
			if (node.getNodeType().equals("literal")) {
				renameLiteral(node);
			}
			return;
		}
		String newName = map.get(node.getNodeType());
		node.setNodeType(newName);
	}
	
	private void renameLiteral(Node node) {
		Node parent = node;
		while (!parent.getNodeType().equals("FieldDeclaration")) {	
			parent = parent.getParent();
			if (parent == null) {
				return;
			}
		}
		String value = null;
		for (Attribute attribute: parent.getAttributes()) {
			if (attribute.getAttributeKey().equals("Type")) {
				value = attribute.getAttributeValues().get(0).getValue().toString();
				if (map.containsKey(value)) {
					String newName = map.get(value);
					node.setNodeType(newName);
				}
				node.addAttribute(new AttributeImpl("Type", new StringValueImpl(value)));
				return;
			}
		}
		
	}
}
