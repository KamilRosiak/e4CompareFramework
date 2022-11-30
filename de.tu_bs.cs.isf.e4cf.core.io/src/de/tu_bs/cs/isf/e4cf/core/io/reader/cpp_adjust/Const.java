package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;

public class Const {
	public static final String CPP = "JAVA"; //TODO change this to cpp
	public static final String IS_INTERFACE = "IsInterface";

	public static final String VALUE = "Value";

	
	public static final String C_UNIT = "CompilationUnit";
	public static final String NAME = "Name";
	public static final String M_DECL = "MethodDeclaration";
	public static final String R_TYPE = "ReturnType";
	public static final String TYPE_BIG = "Type";
	public static final String TYPE = "type";
	public static final String EXPR = "expr";
	public static final String EXPR_STMT = "expr_stmt";
	public static final String INDEX = "index";
	public static final String LINE_COMMENT = "LineComment";
	public static final String COMMENT = "Comment";
	public static final String SLASH_TWICE = "//";
	
	//Node Types
	public static final String BODY = "Body";
	public static final String CONTROL = "control";
	public static final String ENUM_DECL = "EnumDeclaration";
	public static final String FIELD_DECL = "FieldDeclaration";
	public static final String ARGUMENT = "Argument";
	public static final String ARGUMENT_SMALL = "argument";
	public static final String RETURN_STMT = "ReturnStmt";
	public static final String ENUM_CONST_DECL= "EnumConstantDeclaration";
	public static final String INIT = "Initialization";
	public static final String VARIABLE_DECL = "VariableDeclarator";
	public static final String VARIABLE_DECL_EXPR = "VariableDeclarationExpr";
	public static final String ASSIGNMENT = "Assignment";
	public static final String ASSIGN = "ASSIGN";
	public static final String TARGET = "Target";
	public static final String OPERATOR_BIG = "Operator";
	public static final String OPERATOR = "operator";
	public static final String NAME_EXPR = "NameExpr";
	public static final String ARR_ACCESS_EXPR = "ArrayAccessExpr";
	public static final String ARR_INIT_EXPR = "ArrayInitializerExpr";
	public static final String CONDITION = "Condition";
	public static final String BINARY_EXPR = "BinaryExpr";
	public static final String UNARY_EXPR = "UnaryExpr";
	public static final String UPDATE = "Update";
	public static final String ELSE = "Else";
	public static final String THEN = "Then";
	public static final String IF_STMT_BIG = "IfStmt";
	public static final String IF_STMT_SMALL = "if_stmt";
	public static final String METHOD_CALL = "MethodCallExpr";
	
	//illegal Strings
	public static final String T = "\t";
	public static final String EMPTY = "";
	public static final String HASHTAG = "#";
	public static final String LESS_OP = "<";
	public static final String BIGGER_OP = ">";
	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String SPACE = " ";
	public static final String COLON = ":";
	public static final String SEMICOLON = ";";
	public static final String BRACKET_CURVED_L = "{";
	public static final String BRACKET_CURVED_R = "}";
	public static final String BRACKET_L = "(";
	public static final String BRACKET_R = ")";
	public static final String BRACKET_SQUARED = "[]";
	public static final String LINE_BREAK = "\n";
	public static final String EQ = "=";
	


	//redundant Strings
	public static final String ENUM = "enum";
	public static final String FOR = "for";
	public static final String WHILE = "while";
	public static final String IF = "if";
	public static final String SWITCH = "switch";
	public static final String ELSE_IF = "else if";
	public static final String CASE = "case";
	public static final String BREAK = "break";
	public static final String RETURN = "return";
	public static final String DEFAULT = "default";
	
	
	//data types
	public static final String LITERAL = "literal";
	public static final String INT = "int";
	public static final String BOOL = "boolean";
	public static final String DOUBLE = "double";
	public static final String FLOAT = "float";
	public static final String STRING = "String";
	public static final String INT_LIT = "IntegerLiteralExpr";
	public static final String BOOL_LIT = "BooleanLiteralExpr";
	public static final String DOUBLE_LIT = "DoubleLiteralExpr";
	public static final String FLOAT_LIT = "FloatLiteralExpr";
	public static final String STRING_LIT = "StringLiteralExpr";
	public static final String REGEX_INT = "\\d*";
	public static final String REGEX_DOUBLE = "\\d*\\.\\d*";
	public static final String REGEX_FLOAT = "\\d*\\.\\d*f";
	public static final String FALSE = "false";
	public static final String TRUE = "true";

	
}
