package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.effect.Lighting;

import com.github.javaparser.ast.visitor.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;

import java.util.Arrays;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;

/**
 * Custom visitor class extending {@link VoidVisitorAdapter}.
 * 
 * @see <a href=
 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/visitor/VoidVisitorAdapter.html">Javaparser
 *      Docs - VoidVisitorAdapter</a>
 * 
 * @author Serkan Acar
 * @author Pascal Blum
 * @author Paulo Haas
 * @author Hassan Smaoui
 *
 */
public class JavaVisitor extends VoidVisitorAdapter<Node> {
	/**
	 * Visits the compilation unit and creates a new node of type "CompilationUnit"
	 * ({@link CompilationUnit} as a string).
	 * <p>
	 * "CompilationUnit" node gets a child node for imports with type
	 * {@link JavaNodeTypes#Import} (as string), which has the concrete imports as
	 * child nodes. This node also has an attribute
	 * {@link JavaAttributesTypes#Children} containing the number of concrete
	 * imports (0 if none).
	 * <p>
	 * Each concrete import is visited (see
	 * {@link JavaVisitor#visit(ImportDeclaration, Node)} and removed afterwards.
	 * <p>
	 * Finally the remaining children of the compilation unit are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/CompilationUnit.html">Javaparser
	 *      Docs - CompilationUnit</a>
	 * @param n   Compilation Unit
	 * @param arg Parent framework node of the Compilation Unit's framework node.
	 */
	@Override
	public void visit(CompilationUnit n, Node arg) {
		Node cu = JavaVisitorUtil.createNode(n, arg);

		// Imports
		Node imports = JavaVisitorUtil.createNode(JavaNodeTypes.Import, cu);
		int importSize = n.getImports().size();
		JavaVisitorUtil.addAttribute(imports, JavaAttributesTypes.Children, String.valueOf(importSize));
		for (int i = 0; i < importSize; i++) {
			ImportDeclaration c = n.getImport(0);
			visit(c, imports);
			c.removeForced();
		}

		super.visit(n, cu);
	}

	/**
	 * Visits a method declaration and creates a new node of type
	 * "MethodDeclaration" ({@link MethodDeclaration} as string).
	 * <p>
	 * "MethodDeclaration" gets an attribute of type
	 * {@link JavaAttributesTypes#Throws} for every declared thrown exception. The
	 * thrown exception is removed from the method declaration afterwards.
	 * <p>
	 * "MethodDeclaration" gets a child node for the parameters with type
	 * {@link JavaNodeTypes#Argument} (as string), which has the concrete parameters
	 * as child nodes. This node also has an attribute
	 * {@link JavaAttributesTypes#Children} containing the number of concrete
	 * parameters (0 if none).
	 * <p>
	 * Each concrete parameter gets a distinct node of type
	 * {@link JavaNodeTypes#Argument} with the attributes types
	 * {@link JavaAttributesTypes#Type} containing the parameter's type and an
	 * attributes {@link JavaAttributesTypes#Name} containing the parameter's name.
	 * <p>
	 * Finally the remaining children of the method declaration are visited.
	 *
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/MethodDeclaration.html">Javaparser
	 *      Docs - MethodDeclaration</a>
	 * @param n   Method Declaration
	 * @param arg Parent framework node of the Method Declaration's framework node.
	 */
	@Override
	public void visit(MethodDeclaration n, Node arg) {
		Node p = JavaVisitorUtil.createNode(n, arg);

		// Throws
		for (int i = n.getThrownExceptions().size(); i > 0; i--) {
			ReferenceType referenceType = n.getThrownException(0);
			JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Throws, referenceType.asString());
			referenceType.removeForced();
		}

		// Arguments
		Node args = JavaVisitorUtil.createNode(JavaNodeTypes.Argument, p);
		int argList = n.getParameters().size();
		JavaVisitorUtil.addAttribute(args, JavaAttributesTypes.Children, String.valueOf(argList));
		for (int i = 0; i < argList; i++) {
			Parameter concreteParameter = n.getParameter(0);
			Node argNode = JavaVisitorUtil.createNodeWithIndex(JavaNodeTypes.Argument, i, args);
			JavaVisitorUtil.addAttribute(argNode, JavaAttributesTypes.Type, concreteParameter.getTypeAsString());
			JavaVisitorUtil.addAttribute(argNode, JavaAttributesTypes.Name, concreteParameter.getNameAsString());
			concreteParameter.getModifiers().forEach(modif -> modif.accept(this, argNode));
			concreteParameter.removeForced();
		}

		super.visit(n, p);
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Modifier} with the modifiers
	 * name to the parent node.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/Modifier.html">Javaparser
	 *      Docs - Modifier</a>
	 * @param n   Modifier that is attributed to the parent
	 * @param arg Parent node getting the attribute
	 */
	@Override
	public void visit(Modifier n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Modifier, n.getKeyword().name());
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Name} with the name to the
	 * parent node.
	 * <p>
	 * If the parent is of type {@link ClassOrInterfaceDeclaration} then nothing is
	 * done, because {@link JavaVisitor#visit(ClassOrInterfaceDeclaration, Node)}
	 * defines special behavior for this case.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SimpleName.html">Javaparser
	 *      Docs - SimpleName</a>
	 * @param n   Name that is attributed to the parent
	 * @param arg Parent node getting the attribute
	 */
	@Override
	public void visit(SimpleName n, Node arg) {
		if (!(n.getParentNode().get() instanceof ClassOrInterfaceDeclaration)) {
			JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Name, n.toString());
		}
	}

	/**
	 * Visits a class or interface declaration and only creates a new node iff the
	 * declaration is not nested. The new node is either of type
	 * {@link JavaNodeTypes#Interface} or of type {@link JavaNodeTypes#Class}
	 * depending on {@link ClassOrInterfaceDeclaration#isInterface()}. The following
	 * attributes are added to the parent node otherwise.
	 * <p>
	 * The new node gets an attribute {@link JavaAttributesTypes#IsInterface} with
	 * the value of {@link ClassOrInterfaceDeclaration#isInterface()}.
	 * <p>
	 * The new node gets an attribute {@link JavaAttributesTypes#Name} with the name
	 * of the class/ interface.
	 * <p>
	 * The new node get an attribute {@link JavaAttributesTypes#Superclass} with the
	 * name of the class that is extended. The extended class is removed afterwards.
	 * If there is no class extended, this attribute is omitted.
	 * <p>
	 * The new node get an attribute {@link JavaAttributesTypes#Interface} with the
	 * name of the interface for every interface that is implemented. The
	 * implemented class is removed afterwards. If there is no class implemented,
	 * this attribute is omitted.
	 * <p>
	 * Finally the remaining children are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/ClassOrInterfaceDeclaration.html">Javaparser
	 *      Docs - ClassOrInterfaceDeclaration</a>
	 * @param n   Class Or Interface Declaration
	 * @param arg Parent framework node of the Class Or Interface Declaration's
	 *            framework node.
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Node arg) {
		Node classOrInterfaceDeclarationNode = arg;
		if (!n.getParentNode().isPresent() || !(n.getParentNode().get() instanceof CompilationUnit)) {
			classOrInterfaceDeclarationNode = JavaVisitorUtil
					.createNode(n.isInterface() ? JavaNodeTypes.Interface : JavaNodeTypes.Class, arg);
		}

		// Class or Interface?
		JavaVisitorUtil.addAttribute(classOrInterfaceDeclarationNode, JavaAttributesTypes.IsInterface,
				String.valueOf(n.isInterface()));

		// Name
		SimpleName simpleName = n.getName();
		JavaVisitorUtil.addAttribute(classOrInterfaceDeclarationNode, JavaAttributesTypes.Name, simpleName.asString());
		// simpleName.removeForced(); // SimpleName is unremovable -> Solution cf.
		// visit(SimpleName,Node)

		// Superclass
		if (n.getExtendedTypes().size() > 0) {
			// Only a single class can be inherited!
			ClassOrInterfaceType superclass = n.getExtendedTypes(0);
			JavaVisitorUtil.addAttribute(classOrInterfaceDeclarationNode, JavaAttributesTypes.Superclass,
					superclass.getNameAsString());
			superclass.removeForced();
		}

		// Interfaces
		int interfaceSize = n.getImplementedTypes().size();
		for (int i = 0; i < interfaceSize; i++) {
			// Multiple classes can be implemented
			ClassOrInterfaceType implemented = n.getImplementedTypes(0);
			JavaVisitorUtil.addAttribute(classOrInterfaceDeclarationNode, JavaAttributesTypes.Interface,
					implemented.getNameAsString());
			implemented.removeForced();
		}
		super.visit(n, classOrInterfaceDeclarationNode);
	}

	/**
	 * Visits the children of the class or interface type.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/ClassOrInterfaceType.html">JavaParser
	 *      Docs - ClassOrInterfaceType</a>
	 * @param n   ClassOrInterfaceType
	 * @param arg Parent framework node of the Class Or Interface Type's framework
	 *            node.
	 */
	@Override
	public void visit(ClassOrInterfaceType n, Node arg) {
		super.visit(n, arg);
	}

	/**
	 * Creates a new node for the annotation declaration and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/AnnotationDeclaration.html">JavaParser
	 *      Docs - AnnotationDeclaration</a>
	 * @param n   AnnotationDeclaration
	 * @param arg Parent framework node of the AnnotationDeclaration's framework
	 *            node.
	 */
	@Override
	public void visit(AnnotationDeclaration n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node for the annotation member declaration and visits it's
	 * children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/AnnotationMemberDeclaration.html">JavaParser
	 *      Docs - AnnotationMemberDeclaration</a>
	 * @param n   AnnotationMemberDeclaration
	 * @param arg Parent framework node of the AnnotationMemberDeclaration's
	 *            framework node.
	 */
	@Override
	public void visit(AnnotationMemberDeclaration n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node for the array access expression and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ArrayAccessExpr.html">JavaParser
	 *      Docs - ArrayAccessExpr</a>
	 * @param n   ArrayAccessExpr
	 * @param arg Parent framework node of the ArrayAccessExpr's framework node.
	 */
	@Override
	public void visit(ArrayAccessExpr n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the array creation expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ArrayCreationExpr.html">JavaParser
	 *      Docs - ArrayCreationExpr</a>
	 * @param n   ArrayCreationExpr
	 * @param arg Parent framework node of the ArrayCreationExpr's framework node.
	 */
	@Override
	public void visit(ArrayCreationExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * Creates a new node for the array creation level and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/ArrayCreationLevel.html">JavaParser
	 *      Docs - ArrayCreationLevel</a>
	 * @param n   ArrayCreationLevel
	 * @param arg Parent framework node of the ArrayCreationLevel's framework node.
	 */
	@Override
	public void visit(ArrayCreationLevel n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the array initializer expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ArrayInitializerExpr.html">JavaParser
	 *      Docs - ArrayInitializerExpr</a>
	 * @param n   ArrayInitializerExpr
	 * @param arg Parent framework node of the ArrayInitializerExpr's framework
	 *            node.
	 */
	@Override
	public void visit(ArrayInitializerExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Type} to the parent node with
	 * the array type as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/ArrayType.html">JavaParser
	 *      Docs - ArrayType</a>
	 * @param n   ArrayInitializerExpr
	 * @param arg Parent framework node of the ArrayType's framework node.
	 */
	@Override
	public void visit(ArrayType n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Type, n.toString());
	}

	/**
	 * Creates a new node for the assert statement of type {@link AssertStmt} as a
	 * string and adds the check as an attribute {@link JavaAttributesTypes#Check}.
	 * If a message is present, it is also added as an attribute
	 * {@link JavaAttributesTypes#Message}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/AssertStmt.html">JavaParser
	 *      Docs - AssertStmt</a>
	 * @param n   AssertStmt
	 * @param arg Parent framework node of the AssertStmt's framework node.
	 */
	@Override
	public void visit(AssertStmt n, Node arg) {
		Node parent = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(parent, JavaAttributesTypes.Check, n.getCheck().toString());
		if (n.getMessage().isPresent()) {
			JavaVisitorUtil.addAttribute(parent, JavaAttributesTypes.Message, n.getMessage().get().toString());
		}
	}

	/**
	 * Creates a new node for the assign expression of type
	 * {@link JavaNodeTypes#Assignment} as a string and adds the type as an
	 * attribute {@link JavaAttributesTypes#Type}, adds the target of assignment as
	 * an attributes {@link JavaAttributesTypes#Target} and adds the value of the
	 * assignment as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/AssignExpr.html">JavaParser
	 *      Docs - AssignExpr</a>
	 * @param n   AssignExpr
	 * @param arg Parent framework node of the AssignExpr's framework node.
	 */
	@Override
	public void visit(AssignExpr n, Node arg) {
		Node assignment = JavaVisitorUtil.createNode(JavaNodeTypes.Assignment, arg);
		JavaVisitorUtil.addAttribute(assignment, JavaAttributesTypes.Target, n.getTarget().toString());
		JavaVisitorUtil.addAttribute(assignment, JavaAttributesTypes.Value, n.getValue().toString());
		JavaVisitorUtil.addAttribute(assignment, JavaAttributesTypes.Operator, n.getOperator().name());
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the binary expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/BinaryExpr.html">JavaParser
	 *      Docs - BinaryExpr</a>
	 * @param n   BinaryExpr
	 * @param arg Parent framework node of the BinaryExpr's framework node.
	 */
	@Override
	public void visit(BinaryExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * Creates a new node for the block statement of type {@link JavaNodeTypes#Body}
	 * and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/BlockStmt.html">JavaParser
	 *      Docs - BlockStmt</a>
	 * @param n   BlockStmt
	 * @param arg Parent framework node of the BlockStmt's framework node.
	 */
	@Override
	public void visit(BlockStmt n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(JavaNodeTypes.Body, arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the boolean literal expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/BooleanLiteralExpr.html">JavaParser
	 *      Docs - BooleanLiteralExpr</a>
	 * @param n   BooleanLiteralExpr
	 * @param arg Parent framework node of the BooleanLiteralExpr's framework node.
	 */
	@Override
	public void visit(BooleanLiteralExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * Creates a new node for the break statement of type
	 * {@link JavaNodeTypes#Break} and adds an attribute
	 * {@link JavaAttributesTypes#Target} if a label is present.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/BreakStmt.html">JavaParser
	 *      Docs - BreakStmt</a>
	 * @param n   BreakStmt
	 * @param arg Parent framework node of the BreakStmt's framework node.
	 */
	@Override
	public void visit(BreakStmt n, Node arg) {
		Node breakNode = JavaVisitorUtil.createNode(JavaNodeTypes.Break, arg);
		if (n.getLabel().isPresent()) {
			JavaVisitorUtil.addAttribute(breakNode, JavaAttributesTypes.Target, n.getLabel().get().toString());
		}
	}

	/**
	 * Creates a new node for the cast expression of type {@link JavaNodeTypes#Cast}
	 * and adds an attribute {@link JavaAttributesTypes#Type} of the target type.
	 * Afterwards the expression of is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/CastExpr.html">JavaParser
	 *      Docs - CastExpr</a>
	 * @param n   CastExpr
	 * @param arg Parent framework node of the CastExpr's framework node.
	 */
	@Override
	public void visit(CastExpr n, Node arg) {
		Node node = JavaVisitorUtil.createNode(JavaNodeTypes.Cast, arg);
		JavaVisitorUtil.addAttribute(node, JavaAttributesTypes.Type, n.getTypeAsString());
		n.getExpression().accept(this, node);
	}

	/**
	 * Creates a new node for the catch clause of type {@link CatchClause} and
	 * visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/CatchClause.html">JavaParser
	 *      Docs - CatchClause</a>
	 * @param n   CatchClause
	 * @param arg Parent framework node of the CatchClause's framework node.
	 */
	@Override
	public void visit(CatchClause n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the char literal expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/CharLiteralExpr.html">JavaParser
	 *      Docs - CharLiteralExpr</a>
	 * @param n   CharLiteralExpr
	 * @param arg Parent framework node of the CharLiteralExpr's framework node.
	 */
	@Override
	public void visit(CharLiteralExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * creates a new node of type {@link ClassExpr} and adds the class expr as a
	 * string as attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ClassExpr.html">JavaParser
	 *      Docs - ClassExpr</a>
	 * @param n   ClassExpr
	 * @param arg Parent framework node of the ClassExpr's framework node.
	 */
	@Override
	public void visit(ClassExpr n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Creates a new node for the conditional expression of type
	 * {@link ConditionalExpr} and adds an attribute
	 * {@link JavaAttributesTypes#Condition} for the condition, adds an attribute
	 * {@link JavaAttributesTypes#Then} for the then expr and adds an attribute
	 * {@link JavaAttributesTypes#Else} for the else expr.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ConditionalExpr.html">JavaParser
	 *      Docs - ConditionalExpr</a>
	 * @param n   ConditionalExpr
	 * @param arg Parent framework node of the ConditionalExpr's framework node.
	 */
	@Override
	public void visit(ConditionalExpr n, Node arg) {
		Node p = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Condition, n.getCondition().toString());
		JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Then, n.getThenExpr().toString());
		JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Else, n.getElseExpr().toString());
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Package} to the parent node with
	 * the name of the package declaration.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/PackageDeclaration.html">JavaParser
	 *      Docs - PackageDeclaration</a>
	 * @param n   PackageDeclaration
	 * @param arg Parent framework node of the PackageDeclaration's framework node.
	 */
	@Override
	public void visit(PackageDeclaration n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Package, n.getNameAsString());
	}

	/**
	 * Creates a new node for the cast expression of type
	 * {@link JavaNodeTypes#Import} and adds an attribute
	 * {@link JavaAttributesTypes#Asterisks} indicating whetever the import
	 * declaration ends with an asteriks, adds an attribute
	 * {@link JavaAttributesTypes#Static} showing if the import declaration is
	 * static and adds an attribute {@link JavaAttributesTypes#Name} with the name
	 * of the import declaration.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/ImportDeclaration.html">JavaParser
	 *      Docs - ImportDeclaration</a>
	 * @param n   ImportDeclaration
	 * @param arg Parent framework node of the ImportDeclaration's framework node.
	 */
	@Override
	public void visit(ImportDeclaration n, Node arg) {
		Node leaf = JavaVisitorUtil.createNode(JavaNodeTypes.Import, arg);
		JavaVisitorUtil.addAttribute(leaf, JavaAttributesTypes.Asterisks, String.valueOf(n.isAsterisk()));
		JavaVisitorUtil.addAttribute(leaf, JavaAttributesTypes.Static, String.valueOf(n.isStatic()));
		JavaVisitorUtil.addAttribute(leaf, JavaAttributesTypes.Name, String.valueOf(n.getName()));
	}

	/**
	 * Adds the attributes {@link JavaAttributesTypes#Scope} and
	 * {@link JavaAttributesTypes#Name} to the parent node with the scope & name of
	 * the field access expr.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/FieldAccessExpr.html">JavaParser
	 *      Docs - FieldAccessExpr</a>
	 * @param n   FieldAccessExpr
	 * @param arg Parent framework node of the FieldAccessExpr's framework node.
	 */
	@Override
	public void visit(FieldAccessExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Scope, n.getScope().toString());
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Name, n.getNameAsString());
	}

	/**
	 * Creates a new node for the field declaration of type {@link FieldDeclaration}
	 * and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/FieldDeclaration.html">JavaParser
	 *      Docs - FieldDeclaration</a>
	 * @param n   FieldDeclaration
	 * @param arg Parent framework node of the FieldDeclaration's framework node.
	 */
	@Override
	public void visit(FieldDeclaration n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node for the lambda expression of type {@link LambdaExpr} and
	 * visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/LambdaExpr.html">JavaParser
	 *      Docs - LambdaExpr</a>
	 * @param n   LambdaExpr
	 * @param arg Parent framework node of the LambdaExpr's framework node.
	 */
	@Override
	public void visit(LambdaExpr n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node for the local class declaration stmt of type
	 * {@link LocalClassDeclarationStmt} and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/LocalClassDeclarationStmt.html">JavaParser
	 *      Docs - LocalClassDeclarationStmt</a>
	 * @param n   LocalClassDeclarationStmt
	 * @param arg Parent framework node of the LocalClassDeclarationStmt's framework
	 *            node.
	 */
	@Override
	public void visit(LocalClassDeclarationStmt n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Adds the attributes {@link JavaAttributesTypes#Value} to the parent node with
	 * the long literal expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/LongLiteralExpr.html">JavaParser
	 *      Docs - LongLiteralExpr</a>
	 * @param n   LongLiteralExpr
	 * @param arg Parent framework node of the LongLiteralExpr's framework node.
	 */
	@Override
	public void visit(LongLiteralExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * Adds the attributes {@link JavaAttributesTypes#Annotation} to the parent node
	 * with the marker annotation expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/MarkerAnnotationExpr.html">JavaParser
	 *      Docs - MarkerAnnotationExpr</a>
	 * @param n   MarkerAnnotationExpr
	 * @param arg Parent framework node of the MarkerAnnotationExpr's framework
	 *            node.
	 */
	@Override
	public void visit(MarkerAnnotationExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Annotation, n.getNameAsString());
	}

	/**
	 * Creates a new node for the member value pair of type {@link MemberValuePair}
	 * and adds an attribute {@link JavaAttributesTypes#Key} with the member value
	 * pair's name and {@link JavaAttributesTypes#Value} with member value pair's
	 * value.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/MemberValuePair.html">JavaParser
	 *      Docs - MemberValuePair</a>
	 * @param n   MemberValuePair
	 * @param arg Parent framework node of the MemberValuePair's framework node.
	 */
	@Override
	public void visit(MemberValuePair n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Key, n.getNameAsString());
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Value, n.getValue().toString());
	}

	/**
	 * Creates a new node for the method call expr of type {@link MethodCallExpr}
	 * and adds an attribute {@link JavaAttributesTypes#Scope} is the scope is
	 * present, otherwise this attribute is omitted. If type arguments are present,
	 * then for every type argument an attribute
	 * {@link JavaAttributesTypes#TypeArgument} is added. An attribute
	 * {@link JavaAttributesTypes#Name} containing the called methods name is always
	 * added.
	 * <p>
	 * For the arguments a new child node {@link JavaNodeTypes#Argument} is added to
	 * the created node with the attribute {@link JavaAttributesTypes#Children}
	 * which indicates how many arguments the method call expr has. Then for every
	 * argument a new node of type {@link JavaNodeTypes#Argument} with an index is
	 * created. The arguments are then visited with this new node as their parent.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/MethodCallExpr.html">JavaParser
	 *      Docs - MethodCallExpr</a>
	 * @param n   MethodCallExpr
	 * @param arg Parent framework node of the MethodCallExpr's framework node.
	 */
	@Override
	public void visit(MethodCallExpr n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);

		// Scope
		if (n.getScope().isPresent()) {
			JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Scope, n.getScope().get().toString());
		}

		// TypeArguments
		if (n.getTypeArguments().isPresent()) {
			for (Type typeArgumentExpr : n.getTypeArguments().get()) {
				JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.TypeArgument, typeArgumentExpr.toString());
			}
		}

		// Name
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Name, n.getNameAsString());

		// Arguments
		Node args = JavaVisitorUtil.createNode(JavaNodeTypes.Argument, c);
		int argSize = n.getArguments().size();
		JavaVisitorUtil.addAttribute(args, JavaAttributesTypes.Children, String.valueOf(argSize));
		for (int i = 0; i < argSize; i++) {
			Expression argumentExpr = n.getArgument(0);
			Node argNode = JavaVisitorUtil.createNodeWithIndex(JavaNodeTypes.Argument, i, args);
			argumentExpr.accept(this, argNode);
		}
	}

	/**
	 * Creates a new node for the method reference expr of type
	 * {@link MethodReferenceExpr}.
	 * <p>
	 * This node has an attribute {@link JavaAttributesTypes#Identifier} containing
	 * the identifier of the method reference expression and an attribute
	 * {@link JavaAttributesTypes#Scope} with the scope of the method reference
	 * expr.
	 * <p>
	 * If type arguments are present then for every type argument an attribute
	 * {@link JavaAttributesTypes#Type} is added.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/MethodReferenceExpr.html">JavaParser
	 *      Docs - MethodReferenceExpr</a>
	 * @param n   MethodReferenceExpr
	 * @param arg Parent framework node of the MethodReferenceExpr's framework node.
	 */
	@Override
	public void visit(MethodReferenceExpr n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);

		// Identifier
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Identifier, n.getIdentifier());

		// Scope
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Scope, n.getScope().toString());

		// Type
		if (n.getTypeArguments().isPresent()) {
			n.getTypeArguments().get()
					.forEach(typeArg -> JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Type, typeArg.toString()));
		}

	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Name} to the parent node with
	 * the name as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/Name.html">JavaParser
	 *      Docs - Name</a>
	 * @param n   Name
	 * @param arg Parent framework node of the Name's framework node.
	 */
	@Override
	public void visit(Name n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Name, n.toString());
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Name} to the parent node with
	 * the name expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/NameExpr.html">JavaParser
	 *      Docs - NameExpr</a>
	 * @param n   NameExpr
	 * @param arg Parent framework node of the NameExpr's framework node.
	 */
	@Override
	public void visit(NameExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Name, n.getNameAsString());
	}

	/**
	 * Creates a new node for the normal annotation expr of type
	 * {@link NormalAnnotationExpr} and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/NormalAnnotationExpr.html">JavaParser
	 *      Docs - NormalAnnotationExpr</a>
	 * @param n   NormalAnnotationExpr
	 * @param arg Parent framework node of the NormalAnnotationExpr's framework
	 *            node.
	 */
	@Override
	public void visit(NormalAnnotationExpr n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the null literal expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/NullLiteralExpr.html">JavaParser
	 *      Docs - NullLiteralExpr</a>
	 * @param n   NullLiteralExpr
	 * @param arg Parent framework node of the NullLiteralExpr's framework node.
	 */
	@Override
	public void visit(NullLiteralExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * Creates a new node for the object creation expr of type
	 * {@link ObjectCreationExpr}.
	 * <p>
	 * This node has an attribute {@link JavaAttributesTypes#Type} containing the
	 * type, which should be instantiated.
	 * <p>
	 * This node gets a child node {@link JavaNodeTypes#Argument} with the attribute
	 * {@link JavaAttributesTypes#Children} containing the number of children. For
	 * every argument a new node {@link JavaNodeTypes#Argument} with an index is
	 * added to the argument node. The arguments are then visited with the indexed
	 * nodes as their parent.
	 * <p>
	 * If an anonymoud class body is present an child node
	 * {@link JavaNodeTypes#Body} is added to the object creation node, which is the
	 * parent node for each statement of the anonymous class body.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ObjectCreationExpr.html">JavaParser
	 *      Docs - ObjectCreationExpr</a>
	 * @param n   ObjectCreationExpr
	 * @param arg Parent framework node of the ObjectCreationExpr's framework node.
	 */
	@Override
	public void visit(ObjectCreationExpr n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);

		// Type
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Type, n.getTypeAsString());

		// Arguments
		Node arguments = JavaVisitorUtil.createNode(JavaNodeTypes.Argument, c);
		JavaVisitorUtil.addAttribute(arguments, JavaAttributesTypes.Children, String.valueOf(n.getArguments().size()));
		for (int i = 0; i < n.getArguments().size(); i++) {
			Node argNode = JavaVisitorUtil.createNodeWithIndex(JavaNodeTypes.Argument, i, arguments);
			n.getArgument(i).accept(this, argNode);
		}

		// Anonymous Class Body
		if (n.getAnonymousClassBody().isPresent()) {
			Node anonClassBody = JavaVisitorUtil.createNode(JavaNodeTypes.Body, c);
			n.getAnonymousClassBody().get().forEach(decl -> decl.accept(this, anonClassBody));
		}
	}

	/**
	 * Creates a new node for the parameter of type {@link Parameter} and adds the
	 * parameter's type and name as attributes {@link JavaAttributesTypes#Type} and
	 * {@link JavaAttributesTypes#Name}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/Parameter.html">JavaParser
	 *      Docs - Parameter</a>
	 * @param n   Parameter
	 * @param arg Parent framework node of the Parameter's framework node.
	 */
	@Override
	public void visit(Parameter n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);
		if (!n.getTypeAsString().isEmpty()) {
			JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Type, n.getTypeAsString());
		}
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Name, n.getNameAsString());
	}

	/**
	 * Adds the primitive type as a string to the parent node as an attribute
	 * {@link JavaAttributesTypes#Type}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/PrimitiveType.html">JavaParser
	 *      Docs - PrimitiveType</a>
	 * @param n   PrimitiveType
	 * @param arg Parent framework node of the PrimitiveType's framework node.
	 */
	@Override
	public void visit(PrimitiveType n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Type, n.toString());
	}

	/**
	 * Creates a new node for the receiver parameter. The type of the created node
	 * is {@link ReceiverParameter} and gets an attribute
	 * {@link JavaAttributesTypes#Type} with the type of the receiver parameter.
	 * Afterwards all children of the receiver parameter are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/ReceiverParameter.html">JavaParser
	 *      Docs - ReceiverParameter</a>
	 * @param n   ReceiverParameter
	 * @param arg Parent framework node of the ReceiverParameter's framework node.
	 */
	@Override
	public void visit(ReceiverParameter n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Type, n.getType().toString());
		super.visit(n, c);
	}

	/**
	 * Creates a new node for the return statement with the node type
	 * {@link ReturnStmt} and visits all children of the return statement.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ReturnStmt.html">JavaParser
	 *      Docs - ReturnStmt</a>
	 * @param n   ReturnStmt
	 * @param arg Parent framework node of the ReturnStmt's framework node.
	 */
	@Override
	public void visit(ReturnStmt n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);
		super.visit(n, c);
	}

	/**
	 * Creates a new node for the single member annotation expression with the node
	 * type {@link SingleMemberAnnotationExpr} and adds it's name and member value
	 * as attributes {@link JavaAttributesTypes#Name} and
	 * {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SingleMemberAnnotationExpr.html">JavaParser
	 *      Docs - SingleMemberAnnotationExpr</a>
	 * @param n   SingleMemberAnnotationExpr
	 * @param arg Parent framework node of the SingleMemberAnnotationExpr's
	 *            framework node.
	 */
	@Override
	public void visit(SingleMemberAnnotationExpr n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Name, n.getNameAsString());
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Value, n.getMemberValue().toString());
	}

	/**
	 * Adds the string literal expression as a string to the parent node as an
	 * attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/StringLiteralExpr.html">JavaParser
	 *      Docs - StringLiteralExpr</a>
	 * @param n   StringLiteralExpr
	 * @param arg Parent framework node of the StringLiteralExpr's framework node.
	 */
	@Override
	public void visit(StringLiteralExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * Adds the super expression as a string to the parent node as an attribute
	 * {@link JavaAttributesTypes#SuperExpr}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SuperExpr.html">JavaParser
	 *      Docs - SuperExpr</a>
	 * @param n   SuperExpr
	 * @param arg Parent framework node of the SuperExpr's framework node.
	 */
	@Override
	public void visit(SuperExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.SuperExpr, n.toString());
	}

	/**
	 * Creates a new node for the switch entry of type {@link SwitchEntry}.
	 * <p>
	 * For each label this node gets an attribute
	 * {@link JavaAttributesTypes#Condition}. If no label is present, then the
	 * switch entry is the default entry and there gets an attribute
	 * {@link JavaAttributesTypes#Default} which always yields the value
	 * <code>true</code>.
	 * <p>
	 * The new node gets an attributes {@link JavaAttributesTypes#Type} which
	 * contains the type of the switch entry.
	 * <p>
	 * Finally every statement of the entry is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SwitchEntry.html">JavaParser
	 *      Docs - SwitchEntry</a>
	 * @param n   SwitchEntry
	 * @param arg Parent framework node of the SwitchEntry's framework node.
	 */
	@Override
	public void visit(SwitchEntry n, Node arg) {
		Node parent = JavaVisitorUtil.createNode(n, arg);

		// Label
		n.getLabels().forEach(
				label -> JavaVisitorUtil.addAttribute(parent, JavaAttributesTypes.Condition, label.toString()));
		if (n.getLabels().size() == 0) {
			JavaVisitorUtil.addAttribute(parent, JavaAttributesTypes.Default, String.valueOf(true));
		}

		// Type
		JavaVisitorUtil.addAttribute(parent, JavaAttributesTypes.Type, n.getType().name());

		// Statements
		n.getStatements().forEach(stmt -> stmt.accept(this, parent));
	}

	/**
	 * Creates a new node for the switch expression of type {@link SwitchExpr}.
	 * <p>
	 * This node gets an attribute {@link JavaAttributesTypes#Selector} which
	 * contains the selector.
	 * <p>
	 * Then each entry of the expression is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SwitchExpr.html">JavaParser
	 *      Docs - SwitchExpr</a>
	 * @param n   SwitchExpr
	 * @param arg Parent framework node of the SwitchExpr's framework node.
	 */
	@Override
	public void visit(SwitchExpr n, Node arg) {
		Node parent = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(parent, JavaAttributesTypes.Selector, n.getSelector().toString());
		n.getEntries().forEach(entry -> entry.accept(this, parent));
	}

	/**
	 * Creates a new node for the switch statement of type {@link SwitchStmt}.
	 * <p>
	 * This node gets an attribute {@link JavaAttributesTypes#Selector} which
	 * contains the selector.
	 * <p>
	 * Then each entry of the statement is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SwitchStmt.html">JavaParser
	 *      Docs - SwitchStmt</a>
	 * @param n   SwitchStmt
	 * @param arg Parent framework node of the SwitchStmt's framework node.
	 */
	@Override
	public void visit(SwitchStmt n, Node arg) {
		Node parent = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(parent, JavaAttributesTypes.Selector, n.getSelector().toString());
		n.getEntries().forEach(entry -> entry.accept(this, parent));
	}

	/**
	 * Creates a new node for the synchronized statement of type
	 * {@link JavaNodeTypes#Synchronized}.
	 * <p>
	 * This node gets an attribute {@link JavaAttributesTypes#Expression} which
	 * contains the expression of the statement.
	 * <p>
	 * Then the body of the synchronized statement is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SynchronizedStmt.html">JavaParser
	 *      Docs - SynchronizedStmt</a>
	 * @param n   SynchronizedStmt
	 * @param arg Parent framework node of the SynchronizedStmt's framework node.
	 */
	@Override
	public void visit(SynchronizedStmt n, Node arg) {
		Node sync = JavaVisitorUtil.createNode(JavaNodeTypes.Synchronized, arg);
		JavaVisitorUtil.addAttribute(sync, JavaAttributesTypes.Expression, n.getExpression().toString());
		n.getBody().accept(this, sync);
	}

	/**
	 * Creates a new node of type {@link TextLiteralExpr} and adds the text literal
	 * expr as a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/TextBlockLiteralExpr.html">JavaParser
	 *      Docs - TextBlockLiteralExpr</a>
	 * @param n   TextBlockLiteralExpr
	 * @param arg Parent framework node of the TextBlockLiteralExpr's framework
	 *            node.
	 */
	@Override
	public void visit(TextBlockLiteralExpr n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Creates a new node of type {@link ThisExpr} and adds the this expr as a
	 * string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ThisExpr.html">JavaParser
	 *      Docs - ThisExpr</a>
	 * @param n   ThisExpr
	 * @param arg Parent framework node of the ThisExpr's framework node.
	 */
	@Override
	public void visit(ThisExpr n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Creates a new node of type {@link ThrowStmt} and adds the throw statement as
	 * a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ThrowStmt.html">JavaParser
	 *      Docs - ThrowStmt</a>
	 * @param n   ThrowStmt
	 * @param arg Parent framework node of the ThrowStmt's framework node.
	 */
	@Override
	public void visit(ThrowStmt n, Node arg) {
		JavaVisitorUtil.addAttribute(JavaVisitorUtil.createNode(n, arg), JavaAttributesTypes.Statement, n.toString());
	}

	/**
	 * Creates a new node of type {@link TryStmt} and visits all children of the try
	 * stmt.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/TryStmt.html">JavaParser
	 *      Docs - TryStmt</a>
	 * @param n   TryStmt
	 * @param arg Parent framework node of the TryStmt's framework node.
	 */
	@Override
	public void visit(TryStmt n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node of type {@link TypeExpr} and adds the type expression as a
	 * string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/TypeExpr.html">JavaParser
	 *      Docs - TypeExpr</a>
	 * @param n   TypeExpr
	 * @param arg Parent framework node of the TypeExpr's framework node.
	 */
	@Override
	public void visit(TypeExpr n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Creates a new node of type {@link TypeParameter}.
	 * <p>
	 * The name of the {@link TypeParameter} is added as an attribute
	 * {@link JavaAttributesTypes#Name} to the new node.
	 * <p>
	 * For every annotation ({@link TypeParameter#getAnnotations()}) an attribute
	 * {@link JavaAttributesTypes#Annotation} with the annotation's name as a value
	 * is added.
	 * <p>
	 * The {@link TypeParameter}'s bounds ({@link TypeParameter#getTypeBound()} are
	 * added as a child node to the new node. The bound nodes are of type
	 * {@link JavaNodeTypes#Bound} with an index. The bounds are then visited with
	 * their respective node as the framework's parent node. Finally an attribute
	 * {@link JavaAttributesTypes#Bound} with the number bounds is added to the
	 * {@link TypeParameters}'s node.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/TypeParameter.html">JavaParser
	 *      Docs - TypeParameter</a>
	 * @param n   TypeParameter
	 * @param arg Parent framework node of the TypeParameter's framework node.
	 */
	@Override
	public void visit(TypeParameter n, Node arg) {
		Node p = JavaVisitorUtil.createNode(n, arg);

		// Name
		JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Name, n.getNameAsString());

		// Annotations
		for (AnnotationExpr annotationExpr : n.getAnnotations()) {
			JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Annotation, annotationExpr.getNameAsString());
		}

		// Bounds
		int boundCounter = 0;
		for (ClassOrInterfaceType bound : n.getTypeBound()) {
			Node boundNode = JavaVisitorUtil.createNodeWithIndex(JavaNodeTypes.Bound, boundCounter++, p);
			bound.accept(this, boundNode);
		}
		JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Bound, String.valueOf(n.getTypeBound().size()));
	}

	/**
	 * Creates a new node of type {@link UnaryExpr} and adds the unary expression as
	 * a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/UnaryExpr.html">JavaParser
	 *      Docs - UnaryExpr</a>
	 * @param n   UnaryExpr
	 * @param arg Parent framework node of the UnaryExpr's framework node.
	 */
	@Override
	public void visit(UnaryExpr n, Node arg) {
		Node unaryExprNode = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(unaryExprNode, JavaAttributesTypes.Name, n.getExpression().toString());
		JavaVisitorUtil.addAttribute(unaryExprNode, JavaAttributesTypes.Operator, n.getOperator().name());
	}

	/**
	 * Creates a new node with type {@link UnionType} and adds an attribute
	 * {@link JavaAttributesTypes#Type} for every element in
	 * {@link UnionType#getElements()}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/UnionType.html">JavaParser
	 *      Docs - UnionType</a>
	 * @param n   UnionType
	 * @param arg Parent framework node of the UnionType's framework node.
	 */
	@Override
	public void visit(UnionType n, Node arg) {
		Node unionTypeNode = JavaVisitorUtil.createNode(n, arg);
		n.getElements().forEach(
				elem -> JavaVisitorUtil.addAttribute(unionTypeNode, JavaAttributesTypes.Type, elem.toString()));
	}

	/**
	 * Creates a new node of type {@link UnknownType} and adds the unknown type as a
	 * string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/UnknownType.html">JavaParser
	 *      Docs - UnknownType</a>
	 * @param n   UnknownType
	 * @param arg Parent framework node of the UnknownType's framework node.
	 */
	@Override
	public void visit(UnknownType n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Creates a new node of type {@link UnparsableStmt} and adds the unparsable
	 * statement as a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/UnparsableStmt.html">JavaParser
	 *      Docs - UnparsableStmt</a>
	 * @param n   UnparsableStmt
	 * @param arg Parent framework node of the UnparsableStmt's framework node.
	 */
	@Override
	public void visit(UnparsableStmt n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * For every child node of the {@link VariableDeclarationExpr} a new node is
	 * created with the child's class as a type. Each child is then visited with
	 * it's new node as a parent.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/VariableDeclarationExpr.html">JavaParser
	 *      Docs - VariableDeclarationExpr</a>
	 * @param n   VariableDeclarationExpr
	 * @param arg Parent framework node of the VariableDeclarationExpr's framework
	 *            node.
	 */
	@Override
	public void visit(VariableDeclarationExpr n, Node arg) {
		for (int childNodeCounter = 0; childNodeCounter < n.getChildNodes().size(); childNodeCounter++) {
			com.github.javaparser.ast.Node child = n.getChildNodes().get(childNodeCounter);
			Node childNode = JavaVisitorUtil.createNode(child, arg);
			n.getChildNodes().get(childNodeCounter).accept(this, childNode);
		}
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Type} with value
	 * {@link VariableDeclarator#getType()}, an attribute
	 * {@link JavaAttributesTypes#Name} with value
	 * {@link VariableDeclarator#getName()} and an attribute
	 * {@link JavaAttributesTypes#Initilization} with value of
	 * {@link VariableDeclarator#getInitializer()} is the initializer is present to
	 * the parent node.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/VariableDeclarator.html">JavaParser
	 *      Docs - VariableDeclarator</a>
	 * @param n   VariableDeclarator
	 * @param arg Parent framework node of the VariableDeclarator's framework node.
	 */
	@Override
	public void visit(VariableDeclarator n, Node arg) {
		// Type
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Type, n.getTypeAsString());

		// Name
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Name, n.getNameAsString());

		// Initializer
		if (n.getInitializer().isPresent()) {
			JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Initilization, n.getInitializer().get().toString());
		}
	}

	/**
	 * Creates a new node of type {@link VarType} and adds the var type as a string
	 * as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/VarType.html">JavaParser
	 *      Docs - VarType</a>
	 * @param n   VarType
	 * @param arg Parent framework node of the VarType's framework node.
	 */
	@Override
	public void visit(VarType n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} with value
	 * {@link VoidType} as a string to the parent. If the parent is of the
	 * {@link VoidType} is {@link MethodDeclaration}, then the attribute type will
	 * be {@link JavaAttributesTypes#ReturnType}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/VoidType.html">JavaParser
	 *      Docs - VoidType</a>
	 * @param n   VoidType
	 * @param arg Parent framework node of the VoidType's framework node.
	 */
	@Override
	public void visit(VoidType n, Node arg) {
		JavaAttributesTypes type = JavaAttributesTypes.Value;
		if (n.getParentNode().get() instanceof MethodDeclaration) {
			type = JavaAttributesTypes.ReturnType;
		}
		JavaVisitorUtil.addAttribute(arg, type, n.toString());
	}

	/**
	 * Creates a new node of type {@link WhileStmt} and visits all children of the
	 * while statement.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/WhileStmt.html">JavaParser
	 *      Docs - WhileStmt</a>
	 * @param n   WhileStmt
	 * @param arg Parent framework node of the WhileStmt's framework node.
	 */
	@Override
	public void visit(WhileStmt n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node of type {@link WildcardType} and adds the wildcard type as
	 * a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/WildcardType.html">JavaParser
	 *      Docs - WildcardType</a>
	 * @param n   WildcardType
	 * @param arg Parent framework node of the WildcardType's framework node.
	 */
	@Override
	public void visit(WildcardType n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Creates a new node of type {@link YieldStmt} and visits all children of the
	 * yield statement.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/YieldStmt.html">JavaParser
	 *      Docs - YieldStmt</a>
	 * @param n   YieldStmt
	 * @param arg Parent framework node of the YieldStmt's framework node.
	 */
	@Override
	public void visit(YieldStmt n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node of type {@link ConstructorDeclaration} and visits all
	 * children of the constructor declaration.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/ConstructorDeclaration.html">JavaParser
	 *      Docs - ConstructorDeclaration</a>
	 * @param n   ConstructorDeclaration
	 * @param arg Parent framework node of the ConstructorDeclaration's framework
	 *            node.
	 */
	@Override
	public void visit(ConstructorDeclaration n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node of type {@link JavaNodeTypes#Continue} and adds an
	 * attribute {@link JavaAttributesTypes#Target} with value
	 * {@link ContinueStmt#getLabel()} is a label is present.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ContinueStmt.html">JavaParser
	 *      Docs - ContinueStmt</a>
	 * @param n   ContinueStmt
	 * @param arg Parent framework node of the ContinueStmt's framework node.
	 */
	@Override
	public void visit(ContinueStmt n, Node arg) {
		Node continueNode = JavaVisitorUtil.createNode(JavaNodeTypes.Continue, arg);
		if (n.getLabel().isPresent()) {
			JavaVisitorUtil.addAttribute(continueNode, JavaAttributesTypes.Target, n.getLabel().get().toString());
		}
	}

	/**
	 * Creates a new node of type {@link DoStmt} and visits all children of the do
	 * stmt.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/DoStmt.html">JavaParser
	 *      Docs - DoStmt</a>
	 * @param n   DoStmt
	 * @param arg Parent framework node of the DoStmt's framework node.
	 */
	@Override
	public void visit(DoStmt n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));

	}

	/**
	 * Creates a new node of type {@link EmptyStmt} and adds the empty statement as
	 * a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/EmptyStmt.html">JavaParser
	 *      Docs - EmptyStmt</a>
	 * @param n   EmptyStmt
	 * @param arg Parent framework node of the EmptyStmt's framework node.
	 */
	@Override
	public void visit(EmptyStmt n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Creates a new node of type {@link DoubleLiteralExpr} and adds the double
	 * literal expression as a string as an attribute
	 * {@link JavaAttributesTypes#Value}.
	 * 
	 * @see JavaVisitorUtil#createNodeWithValue(com.github.javaparser.ast.Node,
	 *      Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/DoubleLiteralExpr.html">JavaParser
	 *      Docs - DoubleLiteralExpr</a>
	 * @param n   DoubleLiteralExpr
	 * @param arg Parent framework node of the DoubleLiteralExpr's framework node.
	 */
	@Override
	public void visit(DoubleLiteralExpr n, Node arg) {
		JavaVisitorUtil.createNodeWithValue(n, arg);
	}

	/**
	 * Creates a new node of type {@link EnclosedExpr} and visits all children of
	 * the enclosed expression.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/EnclosedExpr.html">JavaParser
	 *      Docs - EnclosedExpr</a>
	 * @param n   EnclosedExpr
	 * @param arg Parent framework node of the EnclosedExpr's framework node.
	 */
	@Override
	public void visit(EnclosedExpr n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node of type {@link EnumConstantDeclaration} and visits all
	 * children of the enum constant declaration.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/EnumConstantDeclaration.html">JavaParser
	 *      Docs - EnumConstantDeclaration</a>
	 * @param n   EnumConstantDeclaration
	 * @param arg Parent framework node of the EnumConstantDeclaration's framework
	 *            node.
	 */
	@Override
	public void visit(EnumConstantDeclaration n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node of type {@link EnumDeclaration} and visits all children of
	 * the enum declaration.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/EnumDeclaration.html">JavaParser
	 *      Docs - EnumDeclaration</a>
	 * @param n   EnumDeclaration
	 * @param arg Parent framework node of the EnumDeclaration's framework node.
	 */
	@Override
	public void visit(EnumDeclaration n, Node arg) {

		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node of type {@link ExplicitConstructorInvocationStmt}.
	 * <p>
	 * If type arguments are present, then for every type argument an attribute is
	 * added to the new node. The attribute is of type
	 * {@link JavaAttributesTypes#TypeArgument} with the type argument as a string
	 * as value.
	 * <p>
	 * The new node gets a child node with type {@link JavaNodeTypes#Argument},
	 * which has an attribute {@link JavaAttributesTypes#Children} which indicates
	 * the number of arguments. The argument node gets an node of
	 * {@link JavaNodeTypes#Argument} with an index for every argument. The indexed
	 * argument node is the parent node for each argument, which are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ExplicitConstructorInvocationStmt.html">JavaParser
	 *      Docs - ExplicitConstructorInvocationStmt</a>
	 * @param n   ExplicitConstructorInvocationStmt
	 * @param arg Parent framework node of the ExplicitConstructorInvocationStmt's
	 *            framework node.
	 */
	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);

		// TypeArguments
		if (n.getTypeArguments().isPresent()) {
			for (Type typeArgumentExpr : n.getTypeArguments().get()) {
				JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.TypeArgument, typeArgumentExpr.toString());
			}
		}

		// Arguments
		Node args = JavaVisitorUtil.createNode(JavaNodeTypes.Argument, c);
		int argSize = n.getArguments().size();
		JavaVisitorUtil.addAttribute(args, JavaAttributesTypes.Children, String.valueOf(argSize));
		for (int i = 0; i < argSize; i++) {
			Expression argumentExpr = n.getArgument(0);
			Node argNode = JavaVisitorUtil.createNodeWithIndex(JavaNodeTypes.Argument, i, args);
			argumentExpr.accept(this, argNode);
		}
	}

	/**
	 * Creates a new node with type {@link ForEachStmt}.
	 * 
	 * Adds an attribute {@link JavaAttributesTypes#Iterator} with value
	 * {@link ForEachStmt#getIterable()} and an attribute
	 * {@link JavaAttributesTypes#Initilization} with value
	 * {@link ForEachStmt#getVariableDeclarator()} to new node.
	 * 
	 * Afterwards the body of the {@link ForEachStmt} is visited
	 * ({@link ForEachStmt#getBody()}).
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ForEachStmt.html">JavaParser
	 *      Docs - ForEachStmt</a>
	 * @param n   ForEachStmt
	 * @param arg Parent framework node of the ForEachStmt's framework node.
	 */
	@Override
	public void visit(ForEachStmt n, Node arg) {
		Node p = JavaVisitorUtil.createNode(n, arg);

		// Iterator
		JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Iterator, n.getIterable().toString());

		// Initilization
		JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Initilization, n.getVariableDeclarator().getNameAsString());
		JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Type, n.getVariableDeclarator().getTypeAsString());
		
		// Block
		n.getBody().accept(this, p);

	}

	/**
	 * A new node with type {@link ForStmt} is generated.
	 * <p>
	 * This node gets an attribute {@link JavaAttributesTypes#Comparison} if a
	 * comparison is present. This attribute contains the expression of
	 * {@link ForStmt#getCompare()} as a string.
	 * <p>
	 * For every initialization {@link ForStmt#getInitialization()} an attribute
	 * {@link JavaAttributesTypes#Initilization} with the initialization expression
	 * as a string is added. The initilization are removed from the {@link ForStmt}
	 * JavaParser node afterwards.
	 * <p>
	 * Analog to the initializations, the same thing is done with the updates
	 * ({@link ForStmt#getUpdate()}). These are added to the attribute
	 * {@link JavaAttributesTypes#Update}.
	 * <p>
	 * Finally the leftovers are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ForStmt.html">JavaParser
	 *      Docs - ForStmt</a>
	 * @param n   ForStmt
	 * @param arg Parent framework node of the ForStmt's framework node.
	 */
	@Override
	public void visit(ForStmt n, Node arg) {
		Node p = JavaVisitorUtil.createNode(n, arg);

		// Comparison
		if (n.getCompare().isPresent()) {
			JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Comparison, n.getCompare().get().toString());
		}
		n.removeCompare(); // rm bc visited

		// Initializations
		int initializations = n.getInitialization().size();
		for (int i = 0; i < initializations; i++) {
			Expression initExpr = n.getInitialization().get(0);
			JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Initilization, initExpr.toString());
			initExpr.removeForced();
		}

		// Updates
		int updates = n.getUpdate().size();
		for (int i = 0; i < updates; i++) {
			Expression updateExpr = n.getUpdate().get(0);
			JavaVisitorUtil.addAttribute(p, JavaAttributesTypes.Update, updateExpr.toString());
			updateExpr.removeForced();
		}

		// Leftovers
		super.visit(n, p);
	}

	/**
	 * A new node with type {@link IfStmt} is created. Then
	 * {@link JavaVisitor#visitIfStmt(IfStmt, Node)} is used to generate the
	 * children recursively.
	 * 
	 * @see JavaVisitor#visitIfStmt(IfStmt, Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/IfStmt.html">JavaParser
	 *      Docs - IfStmt</a>
	 * @param n   IfStmt
	 * @param arg Parent framework node of the IfStmt's framework node.
	 */
	@Override
	public void visit(IfStmt n, Node arg) {
		Node p = JavaVisitorUtil.createNode(n, arg);
		this.visitIfStmt(n, p);
	}

	/**
	 * The then statement is added as a child node {@link JavaNodeTypes#Then} to the
	 * parent. This node gets an attribute {@link JavaAttributesTypes#Condition}
	 * with the condition for the follow block. Then the then stmt visited with the
	 * new node as a parent and removed from the {@link IfStmt}.
	 * <p>
	 * If the else stmt (@link IfStmt#getElseStmt()) is an if stmt, then this method
	 * is called again, such that a new then node is created.
	 * <p>
	 * Otherwise, if the else stmt is not an if stmt, then a new node
	 * {@link JavaNodeTypes#Else} is created, which represents the parent node of
	 * the else stmt which is then visited.
	 * 
	 * @param n   If Statement
	 * @param arg Parent Node
	 */
	private void visitIfStmt(IfStmt n, Node arg) {
		// Fall through
		Statement thenStmt = n.getThenStmt();
		Node thenNode = JavaVisitorUtil.createNode(JavaNodeTypes.Then, arg);
		JavaVisitorUtil.addAttribute(thenNode, JavaAttributesTypes.Condition, n.getCondition().toString());
		thenStmt.accept(this, thenNode);
		n.remove(thenStmt);

		// Block
		if (n.getElseStmt().isPresent()) {
			Statement elseStmt = n.getElseStmt().get();
			if (elseStmt instanceof IfStmt) {
				visitIfStmt((IfStmt) elseStmt, arg);
			} else {
				Node elseNode = JavaVisitorUtil.createNode(JavaNodeTypes.Else, arg);
				elseStmt.accept(this, elseNode);
			}
		}
	}

	/**
	 * Creates a new node of type {@link InitializerDeclaration} and visits all
	 * children of the initializer declaration.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/decl/InitializerDeclaration.html">JavaParser
	 *      Docs - InitializerDeclaration</a>
	 * @param n   InitializerDeclaration
	 * @param arg Parent framework node of the InitializerDeclaration's framework
	 *            node.
	 */
	@Override
	public void visit(InitializerDeclaration n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}

	/**
	 * Creates a new node with type {@link InstanceOfExpr}.
	 * 
	 * Adds an attribute {@link JavaAttributesTypes#Type} with value
	 * {@link InstanceOfExpr#getType()} and adds an attribute
	 * {@link JavaAttributesTypes#Expression} with value
	 * {@link InstanceOfExpr#getExpression()}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/InstanceOfExpr.html">JavaParser
	 *      Docs - InstanceOfExpr</a>
	 * @param n   InstanceOfExpr
	 * @param arg Parent framework node of the InstanceOfExpr's framework node.
	 */
	@Override
	public void visit(InstanceOfExpr n, Node arg) {
		Node c = JavaVisitorUtil.createNode(n, arg);
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Type, n.getType().toString());
		JavaVisitorUtil.addAttribute(c, JavaAttributesTypes.Expression, n.getExpression().toString());
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} with value
	 * {@link IntegerLiteralExpr} as number to the parent node.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/IntegerLiteralExpr.html">JavaParser
	 *      Docs - IntegerLiteralExpr</a>
	 * @param n   IntegerLiteralExpr
	 * @param arg Parent framework node of the IntegerLiteralExpr's framework node.
	 */
	@Override
	public void visit(IntegerLiteralExpr n, Node arg) {
		JavaVisitorUtil.addAttribute(arg, JavaAttributesTypes.Value, n.asNumber().toString());

	}

	/**
	 * Creates a new node of type {@link IntersectionType} and visits all children
	 * of the intersection type.
	 * 
	 * @see JavaVisitorUtil#createNode(com.github.javaparser.ast.Node,Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/IntersectionType.html">JavaParser
	 *      Docs - IntersectionType</a>
	 * @param n   IntersectionType
	 * @param arg Parent framework node of the IntersectionType's framework node.
	 */
	@Override
	public void visit(IntersectionType n, Node arg) {
		super.visit(n, JavaVisitorUtil.createNode(n, arg));
	}
}