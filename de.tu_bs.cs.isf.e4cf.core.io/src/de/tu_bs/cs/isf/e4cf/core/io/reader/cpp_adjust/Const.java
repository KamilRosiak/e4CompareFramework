package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

/**
 * This class contains only constants used for the adjustment of cpp Node Types
 * 
 * @author David Bumm
 *
 */
public class Const {
	public static final String CPP = "JAVA"; //TODO change this to cpp
	public static final String IS_INTERFACE = "IsInterface";

	public static final String VALUE = "Value";

	
	public static final String C_UNIT = "CompilationUnit";
	public static final String NAME_BIG = "Name";
	public static final String NAME_SMALL = "name";
	public static final String M_DECL = "MethodDeclaration";
	public static final String R_TYPE = "ReturnType";
	public static final String TYPE_BIG = "Type";
	public static final String TYPE_SMALL = "type";
	public static final String EXPR = "expr";
	public static final String EXPR_STMT = "expr_stmt";
	public static final String INDEX = "index";
	public static final String LINE_COMMENT = "LineComment";
	public static final String COMMENT_BIG = "Comment";
	public static final String COMMENT_SMALL = "comment";
	public static final String SLASH_TWICE = "//";
	
	
	//Node Types
	public static final String BODY = "Body";
	public static final String CONTROL = "control";
	public static final String ENUM_DECLARATION = "EnumDeclaration";
	public static final String ENUM_DECL = "enum_decl";
	public static final String FIELD_DECL = "FieldDeclaration";
	public static final String ARGUMENT_BIG = "Argument";
	public static final String ARGUMENT_SMALL = "argument";
	public static final String RETURN_STMT = "ReturnStmt";
	public static final String ENUM_CONST_DECL= "EnumConstantDeclaration";
	public static final String INITIALIZATION = "Initialization";
	public static final String INIT = "init";
	public static final String VARIABLE_DECL = "VariableDeclarator";
	public static final String VARIABLE_DECL_EXPR = "VariableDeclarationExpr";
	public static final String ASSIGNMENT = "Assignment";
	public static final String ASSIGN = "ASSIGN";
	public static final String TARGET = "Target";
	public static final String OPERATOR_BIG = "Operator";
	public static final String OPERATOR_SMALL = "operator";
	public static final String NAME_EXPR = "NameExpr";
	public static final String ARR_ACCESS_EXPR = "ArrayAccessExpr";
	public static final String ARR_INIT_EXPR = "ArrayInitializerExpr";
	public static final String CONDITION_BIG = "Condition";
	public static final String CONDITION_SMALL = "condition";
	public static final String BINARY_EXPR = "BinaryExpr";
	public static final String UNARY_EXPR = "UnaryExpr";
	public static final String UPDATE = "Update";
	public static final String ELSE_BIG = "Else";
	public static final String ELSE_SMALL = "else";
	public static final String THEN_BIG = "Then";
	public static final String THEN_SMALL = "then";
	public static final String IF_STMT_BIG = "IfStmt";
	public static final String IF_STMT_SMALL = "if_stmt";
	public static final String METHOD_CALL = "MethodCallExpr";
	public static final String SWITCH_STMT = "SwitchStmt";
	public static final String SWITCH_ENTRY = "SwitchEntry";
	public static final String SELECTOR = "Selector";
	public static final String STMT_GRP = "STATEMENT_GROUP";
	public static final String CALL = "call";
	public static final String SPECIFIER = "Specifier";
	
	
	//illegal Strings
	public static final String T = "\t";
	public static final String EMPTY = "";
	public static final String HASHTAG = "#";
	public static final String LESS_OP = "<";
	public static final String GREATER_OP = ">";
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
	
	
	//operators
	public static final String LESS = "LESS";
	public static final String GREATER = "GREATER";
	public static final String GREATER_EQUALS ="GREATER_EQUALS";
	public static final String LESS_EQUALS = "LESS_EQUALS";
	public static final String DIVIDE = "DIVIDE";
	public static final String PLUS = "PLUS";
	public static final String MINUS = "MINUS";
	public static final String MULTIPLY = "MULTIPLY";
	public static final String GREATER_EQUALS_OP = ">=";
	public static final String LESS_EQUALS_OP = "<=";
	public static final String DIVIDE_OP = "/";
	public static final String PLUS_OP = "+";
	public static final String MINUS_OP = "-";
	public static final String MULTIPLY_OP = "*";
	public static final String PLUS_PLUS = "++";
	public static final String MINUS_MINUS = "--";
	public static final String POSTFIX_INCREMENT = "POSTFIX_INCREMENT";
	public static final String POSTFIX_DECREMENT = "POSTFIX_DECREMENT";
	public static final String PREFIX_INCREMENT = "PREFIX_INCREMENT";
	public static final String PREFIX_DECREMENT = "PREFIX_DECREMENT";
	public static final String MODIFIER = "modifier";



	//redundant Strings
	public static final String ENUM = "enum";
	public static final String FOR = "for";
	public static final String WHILE = "while";
	public static final String IF = "if";
	public static final String SWITCH = "switch";
	public static final String ELSE_IF = "else if";
	public static final String CASE = "case";
	public static final String BREAK_SMALL = "break";
	public static final String BREAK_BIG ="Break";
	public static final String RETURN = "return";
	public static final String DEFAULT_SMALL = "default";
	public static final String DEFAULT_BIG = "Default";
	
	//data types
	public static final String LITERAL = "literal";
	public static final String INT = "int";
	public static final String BOOLEAN = "boolean";
	public static final String BOOL = "bool";
	public static final String DOUBLE = "double";
	public static final String FLOAT = "float";
	public static final String CHAR = "char";
	public static final String LONG = "long";
	public static final String NULL = "null";
	public static final String STRING = "String";
	public static final String INT_LIT = "IntegerLiteralExpr";
	public static final String BOOLEAN_LIT = "BooleanLiteralExpr";
	public static final String DOUBLE_LIT = "DoubleLiteralExpr";
	public static final String FLOAT_LIT = "FloatLiteralExpr";
	public static final String STRING_LIT = "StringLiteralExpr";
	public static final String CHAR_LIT = "CharLiteralExpr";
	public static final String LONG_LIT = "LongLiteralExpr";
	public static final String NULL_LIT = "NullLiteralExpr";
	public static final String REGEX_INT = "\\d*";
	public static final String REGEX_DOUBLE = "\\d*\\.\\d*";
	public static final String REGEX_FLOAT = "\\d*\\.\\d*f";
	public static final String FALSE = "false";
	public static final String TRUE = "true";
	
	
	//renaming
	public static final String INCR = "incr";
	public static final String BLOCK_CONTENT = "block_content";
	public static final String ARG_LIST = "argument_list";
	public static final String THROW = "throw";
	public static final String THROW_STMT = "ThrowStmt";
	public static final String TRY = "try";
	public static final String TRY_STMT = "TryStmt";
	public static final String CATCH = "catch";
	public static final String CATCH_CLAUSE ="CatchClause";
	public static final String CONSTRUCTOR_DECL = "constructor_decl";
	public static final String CONSTRUCTOR_DECLARATION = "ConstructorDeclaration";
	public static final String DECL_STMT = "decl_stmt";
	public static final String LAMBDA = "lambda";
	public static final String LAMBDA_EXPR = "LambdaExpr";
	public static final String FUN_DECL = "function_decl";
	public static final String LABEL = "label";
	public static final String LABEL_STMT = "LabeledStmt";
	public static final String EMPTY_STMT_SMALL = "empty_stmt";
	public static final String EMPTY_STMT_BIG = "EmptyStmt";
	public static final String CONTINUTE_SMALL = "continue";
	public static final String CONTINUTE_BIG = "Continue";
	public static final String DO = "do";
	public static final String DO_STMT = "DoStmt";
	public static final String WHILE_STMT = "WhileStmt";
	public static final String FOR_STMT = "ForStmt";
	public static final String CONSTRUCTOR = "constructor";
	public static final String DECL = "decl";
	public static final String BLOCK = "block";
	public static final String PARAM = "parameter";
	public static final String PARAM_LIST = "parameter_list";
	public static final String FUNCTION = "function";
}
