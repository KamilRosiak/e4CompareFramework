package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;

/**
 * Attribute keys given to a node by the {@link JavaVisitor}.
 * 
 * @author Serkan Acar
 * @author Pascal Blum
 * @author Paulo Haas
 * @author Hassan Smaoui
 *
 */
public enum JavaAttributesTypes {
	/**
	 * This attribute contains the name of an {@link AnnotationExpr}.
	 * 
	 * @see AnnotationExpr
	 */
	Annotation,
	/**
	 * This attribute shows whetever an an import ends with <code>*</code>.
	 * 
	 * @see ImportDeclaration
	 */
	Asterisks,
	/**
	 * This attributes contains the number of bounds of a type parameter
	 * 
	 * @see TypeParameter
	 */
	Bound,
	/**
	 * This attribute contains the check of an assert stmt.
	 * 
	 * @see AssertStmt
	 */
	Check,
	/**
	 * This attribute has meta information about the number of children of an
	 * aggregator node.
	 * <p>
	 * This is used for imports, arguments and parameters, as there can be many of
	 * these.
	 */
	Children,
	/**
	 * This attribute contains the content of a comment.
	 * 
	 * @see Comment
	 */
	Comment,
	/**
	 * This attribute contains the comparison of a for stmt.
	 * 
	 * @see ForStmt
	 */
	Comparison,
	/**
	 * This attribute contains a condition.
	 * <p>
	 * This condition could be of a if stmt, switch entry or a conditional expr.
	 * 
	 * @see IfStmt
	 * @see SwitchEntry
	 * @see ConditionalExpr
	 */
	Condition,
	/**
	 * This attribute is present when a switch entry is a default entry. It always
	 * contains <code>true</code>. This is just meta information as a default switch
	 * entry does have no condition and is always added last.
	 * 
	 * @see SwitchEntry
	 */
	Default,
	/**
	 * This attribute contains the else expr of a conditional expr.
	 * 
	 * @see ConditionalExpr
	 */
	Else,
	/**
	 * Any expression not detailedly specified.
	 * 
	 * @see Expression
	 */
	Expression,
	/**
	 * Contains the name of a referenced method, which is called identifier.
	 * 
	 * @see MethodReferenceExpr
	 */
	Identifier,
	/**
	 * An expr which initializes a for loop/ for each loop or a variable.
	 * 
	 * @see ForEachStmt
	 * @see ForStmt
	 * @see VariableDeclarator
	 */
	Initilization,
	/**
	 * This attribute lists the names of the interfaces implemented by a class.
	 * 
	 * @see ClassOrInterfaceDeclaration
	 */
	Interface,
	/**
	 * This attribute contains true if the parameters of an lambda expression are
	 * enclosed.
	 * 
	 * @see LambdaExpr
	 */
	isEnclosingParameters,
	/**
	 * This attribute contains true if the compilation unit is an enum.
	 */
	IsEnum,
	/**
	 * This attribute contains meta information about the type of an class or
	 * interface node.
	 * 
	 * @see ClassOrInterfaceDeclaration#isInterface()
	 */
	IsInterface,
	/**
	 * This attribute contains true if an {@link ExplicitConstructorInvocationStmt}
	 * refers to this. If this attribute contains false, then it refers to a super
	 * call.
	 */
	IsThis,
	/**
	 * An expr which contains the iterable of an for each stmt.
	 * 
	 * @see ForEachStmt
	 */
	Iterator,
	/**
	 * The key of a member value pair.
	 * 
	 * @see MemberValuePair
	 */
	Key,
	/**
	 * The message of an assert stmt when the check evaluates to false.
	 * 
	 * @see AssertStmt
	 */
	Message,
	/**
	 * A list of modifiers for the node.
	 * 
	 * @see Modifier
	 */
	Modifier,
	/**
	 * The name of a node
	 * 
	 * @see Name
	 * @see SimpleName
	 */
	Name,
	/**
	 * The operator used in an assignment or an unary expr.
	 * 
	 * @see AssignExpr
	 * @see UnaryExpr
	 */
	Operator,
	/**
	 * The package of a compilation unit
	 * 
	 * @see CompilationUnit
	 */
	Package,
	/**
	 * The return type of a method.
	 */
	ReturnType,
	/**
	 * The scope of a method or a field.
	 * 
	 * @see FieldAccessExpr
	 * @see MethodCallExpr
	 * @see MethodReferenceExpr
	 */
	Scope,
	/**
	 * The selector of a switch expr or stmt.
	 * 
	 * @see SwitchExpr
	 * @see SwitchStmt
	 */
	Selector,
	/**
	 * Any statement not detailedly specified.
	 * 
	 * @see Statement
	 */
	Statement,
	/**
	 * This attribute shows whetever an an import is static. Regarding the modifier
	 * <code>static</code> see {@link JavaAttributesTypes#Modifier}.
	 * 
	 * @see ImportDeclaration
	 */
	Static,
	/**
	 * Contains the class extended by a class.
	 * 
	 * @see ClassOrInterfaceDeclaration
	 */
	Superclass,
	/**
	 * Contains a super expr.
	 * 
	 * @see SuperExpr
	 */
	SuperExpr,
	/**
	 * Shows the target of a continue or break stmt or show the target of an
	 * assignment.
	 * 
	 * @see AssignExpr
	 * @see BreakStmt
	 * @see ContinueStmt
	 */
	Target,
	/**
	 * Contains the then expr of a conditional expr.
	 * 
	 * @see ConditionalExpr
	 */
	Then,
	/**
	 * Lists the names of exceptions which can be thrown by a method.
	 * 
	 * @see MethodDeclaration
	 */
	Throws,
	/**
	 * Any type not detailedly specified.
	 * 
	 * @see Type
	 */
	Type,
	/**
	 * A list of type arguments.
	 * 
	 * @see MethodCallExpr
	 * @see ExplicitConstructorInvocationStmt
	 */
	TypeArgument,
	/**
	 * A list of type arguments for a type parameter bound.
	 * 
	 * @see TypeParameter
	 */
	TypeParameterBound,
	/**
	 * The update expr of a for loop.
	 * 
	 * @see ForStmt
	 */
	Update,
	/**
	 * Contains a value for the node.
	 * 
	 * @see JavaVisitor
	 */
	Value,
}
