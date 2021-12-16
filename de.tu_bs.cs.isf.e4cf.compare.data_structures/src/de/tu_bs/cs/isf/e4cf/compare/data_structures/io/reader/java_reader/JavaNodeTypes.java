package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.type.TypeParameter;

/**
 * This file extends the nodes types and attributes for the generic data structure generated by {@link JavaVisitor}. 
 * 
 * @author Serkan Acar
 * @author Pascal Blum
 * @author Paulo Haas
 * @author Hassan Smaoui
 */
public enum JavaNodeTypes {
	/**
	 * Node type for arguments and parameters.
	 * Sometimes used as container.
	 * 
	 * @see Expression
	 * @see Parameter
	 */
	Argument,
	/**
	 * Node type for assignments 
	 * 
	 * @see AssignExpr
	 */
	Assignment,
	/**
	 * Node type for block comments
	 * 
	 * @see BlockComment
	 */
	BlockComment,
	/**
	 * Node type for a body or a block
	 */
	Body,
	/**
	 * Node type for a bound of a type parameter
	 * 
	 * @see TypeParameter
	 */
	Bound,
	/**
	 * Node type for a break statement
	 * 
	 * @see BreakStmt
	 */
	Break,
	/**
	 * Node type for a cast expr
	 * 
	 * @see CastExpr
	 */
	Cast,
	/**
	 * Node type for a class
	 * 
	 * @see ClassOrInterfaceDeclaration
	 */
	Class,
	/**
	 * Node type for a continue stmt
	 * 
	 * @see ContinueStmt
	 */
	Continue, 
	/**
	 * Node type for the last else branch of a if stmt
	 * 
	 * @see IfStmt
	 */
	Else,
	/**
	 * Node type for the finally block of a try stmt
	 * 
	 * @see TryStmt
	 */
	Finally,
	/**
	 * Node type for an import
	 * 
	 * @see ImportDeclaration
	 */
	Import, 
	/**
	 * Container node for multiple imports
	 */
	Imports,
	/**
	 * Node type for an interface
	 * 
	 * @see ClassOrInterfaceDeclaration
	 */
	Interface,
	/**
	 * Node for Javadoc comments
	 * 
	 * @see JavadocComment
	 */
	JavadocComment,
	/**
	 * Node for a inline comment
	 * 
	 * @see LineComment
	 */
	LineComment,
	/**
	 * Node for synchronized stmt
	 * 
	 * @see SynchronizedStmt
	 */
	Synchronized,
	/**
	 * Node types for <code>if</code> or <code>else if</code>.
	 * 
	 * @see IfStmt
	 */
	Then,
	AnnotationDeclaration,
	AnnotationMemberDeclaration,
	ArrayAccessExpr,
	ArrayCreationExpr,
	ArrayCreationLevel,
	ArrayInitializerExpr,
	AssertStmt,
	BinaryExpr,
	BooleanLiteralExpr,
	CatchClause,
	CharLiteralExpr,
	CompilationUnit,
	Condition,
	ConditionalExpr,
	ClassExpr,
	ConstructorDeclaration,
	DoStmt,
	DoubleLiteralExpr,
	EnclosedExpr,
	EnumConstantDeclaration,
	EnumDeclaration,
	EmptyStmt,
	ExplicitConstructorInvocationStmt,
	FieldAccessExpr,
	FieldDeclaration,
	ForEachStmt,
	ForStmt,
	IfStmt,
	InitializerDeclaration,
	Initialization, // Used in For-Statements
	InstanceOfExpr,
	IntegerLiteralExpr,
	IntersectionType,
	JAVA,
	LabeledStmt,
	LambdaExpr,
	LocalClassDeclarationStmt,
	LongLiteralExpr,
	MemberValuePair,
	MethodCallExpr,
	MethodDeclaration,
	MethodReferenceExpr,
	NameExpr,
	NormalAnnotationExpr,
	NullLiteralExpr,
	ObjectCreationExpr,
	Parameter,
	ReceiverParameter,
	ReturnStmt,
	SingleMemberAnnotationExpr,
	StringLiteralExpr,
	SuperExpr,
	SwitchEntry,
	SwitchStmt,
	ThrowStmt,
	TryStmt,
	ThisExpr,
	TypeParameter,
	UnaryExpr,
	UnionType,
	Update,
	VariableDeclarationExpr,
	VariableDeclarator,
	WhileStmt,
	WildcardType,
	YieldStmt,
	;
	
	public static JavaNodeTypes getFromClass(Class<?> c) {
		String simpleName = c.getSimpleName();
		for (JavaNodeTypes t : JavaNodeTypes.class.getEnumConstants()) {
			if (t.name().equals(simpleName))
				return t;
		}
		System.err.println("Tried to parse an unkown Class as JavaNodeType: " + simpleName);
		return null;
	}
	
	public static JavaNodeTypes fromString(String name) {
	    try {
	    	return valueOf(name);
	    } catch (IllegalArgumentException ex) {
	    	System.err.println("Tried to create unknown Java Node Type: " + name);
	    	return null;
	    }
	}

}
