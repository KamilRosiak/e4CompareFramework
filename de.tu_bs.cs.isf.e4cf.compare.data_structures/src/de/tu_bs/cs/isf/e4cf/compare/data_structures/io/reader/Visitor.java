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
 * Part of the custom visitor class. This class extends VoidVisitorAdapter
 * (https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/visitor/VoidVisitorAdapter.html).
 * 
 * @author Serkan Acar
 * @author Pascal Blum
 * @author Paulo Haas
 * @author Hassan Smaoui
 *
 */
public class Visitor extends VoidVisitorAdapter<Node> {
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/CompilationUnit.html
	 */
	@Override
	public void visit(CompilationUnit n, Node arg) {
		Node cu = VisitorUtil.Parent(n, arg);

		// Imports
		Node imports = VisitorUtil.Node(JavaNodeTypes.Import, cu);
		int importSize = n.getImports().size();
		VisitorUtil.AddAttribute(imports, JavaAttributesTypes.Children, String.valueOf(importSize));
		for (int i = 0; i < importSize; i++) {
			ImportDeclaration c = n.getImport(0);
			visit(c, imports);
			c.removeForced();
		}

		super.visit(n, cu);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/MethodDeclaration.html
	 */
	@Override
	public void visit(MethodDeclaration n, Node arg) {
		Node p = VisitorUtil.Parent(n, arg);
		
		// Throws
		for(int i = n.getThrownExceptions().size(); i > 0; i--) {
			ReferenceType referenceType = n.getThrownException(0);
			VisitorUtil.AddAttribute(p, JavaAttributesTypes.Throws, referenceType.asString());
			referenceType.removeForced();
		}
		
		// Arguments
		Node args = VisitorUtil.Node(JavaNodeTypes.Argument, p);
		int argList = n.getParameters().size();
		VisitorUtil.AddAttribute(args, JavaAttributesTypes.Children, String.valueOf(argList));
		for (int i = 0; i < argList; i++) {
			Parameter concreteParameter = n.getParameter(0);
			Node argNode = VisitorUtil.NodeSetElement(JavaNodeTypes.Argument, i, args);
			VisitorUtil.AddAttribute(argNode, JavaAttributesTypes.Type, concreteParameter.getTypeAsString());
			VisitorUtil.AddAttribute(argNode, JavaAttributesTypes.Name, concreteParameter.getNameAsString());
			concreteParameter.getModifiers().forEach(modif -> modif.accept(this, argNode));
			concreteParameter.removeForced();
		}

		super.visit(n, p);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/Modifier.html
	 */
	@Override
	public void visit(Modifier n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Modifier, n.getKeyword().name());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SimpleName.html
	 */
	@Override
	public void visit(SimpleName n, Node arg) {
		if (!(n.getParentNode().get() instanceof ClassOrInterfaceDeclaration)) {
			VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Name, n.toString());
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/ClassOrInterfaceDeclaration.html
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Node arg) {
		Node classOrInterfaceDeclarationNode = arg;
		if (!n.getParentNode().isPresent() || !(n.getParentNode().get() instanceof CompilationUnit)) {
			classOrInterfaceDeclarationNode = VisitorUtil.Node(n.isInterface() ? JavaNodeTypes.Interface : JavaNodeTypes.Class, arg);
		}
		
		// Class or Interface?
		VisitorUtil.AddAttribute(classOrInterfaceDeclarationNode, JavaAttributesTypes.IsInterface, String.valueOf(n.isInterface()));

		// Name
		SimpleName simpleName = n.getName();
		VisitorUtil.AddAttribute(classOrInterfaceDeclarationNode, JavaAttributesTypes.Name, simpleName.asString());
		// simpleName.removeForced(); // SimpleName is unremovable -> Solution cf.
		// visit(SimpleName,Node)

		// Superclass
		if (n.getExtendedTypes().size() > 0) {
			// Only a single class can be inherited!
			ClassOrInterfaceType superclass = n.getExtendedTypes(0);
			VisitorUtil.AddAttribute(classOrInterfaceDeclarationNode, JavaAttributesTypes.Superclass, superclass.getNameAsString());
			superclass.removeForced();
		}

		// Interfaces
		int interfaceSize = n.getImplementedTypes().size();
		for (int i = 0; i < interfaceSize; i++) {
			// Multiple classes can be implemented
			ClassOrInterfaceType implemented = n.getImplementedTypes(0);
			VisitorUtil.AddAttribute(classOrInterfaceDeclarationNode, JavaAttributesTypes.Interface, implemented.getNameAsString());
			implemented.removeForced();
		}
		super.visit(n, classOrInterfaceDeclarationNode);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/type/ClassOrInterfaceType.html
	 */
	@Override
	public void visit(ClassOrInterfaceType n, Node arg) {
		super.visit(n, arg);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/AnnotationDeclaration.html
	 */
	@Override
	public void visit(AnnotationDeclaration n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/AnnotationMemberDeclaration.html
	 */
	@Override
	public void visit(AnnotationMemberDeclaration n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ArrayAccessExpr.html
	 */
	@Override
	public void visit(ArrayAccessExpr n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ArrayCreationExpr.html
	 */
	@Override
	public void visit(ArrayCreationExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/ArrayCreationLevel.html
	 */
	@Override
	public void visit(ArrayCreationLevel n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ArrayInitializerExpr.html
	 */
	@Override
	public void visit(ArrayInitializerExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/type/ArrayType.html
	 */
	@Override
	public void visit(ArrayType n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Type, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/AssertStmt.html
	 */
	@Override
	public void visit(AssertStmt n, Node arg) {
		Node parent = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(parent, JavaAttributesTypes.Check, n.getCheck().toString());
		if(n.getMessage().isPresent()) {
			VisitorUtil.AddAttribute(parent, JavaAttributesTypes.Message, n.getMessage().get().toString());
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/AssignExpr.html
	 */
	@Override
	public void visit(AssignExpr n, Node arg) {
		Node assignment = VisitorUtil.Node(JavaNodeTypes.Assignment, arg);
		VisitorUtil.AddAttribute(assignment, JavaAttributesTypes.Target, n.getTarget().toString());
		VisitorUtil.AddAttribute(assignment, JavaAttributesTypes.Value, n.getValue().toString());
		VisitorUtil.AddAttribute(assignment, JavaAttributesTypes.Operator, n.getOperator().name());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/BinaryExpr.html
	 */
	@Override
	public void visit(BinaryExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/BlockStmt.html
	 */
	@Override
	public void visit(BlockStmt n, Node arg) {
		super.visit(n, VisitorUtil.Node(JavaNodeTypes.Body, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/BooleanLiteralExpr.html
	 */
	@Override
	public void visit(BooleanLiteralExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/BreakStmt.html
	 */
	@Override
	public void visit(BreakStmt n, Node arg) {
		Node breakNode = VisitorUtil.Node(JavaNodeTypes.Break, arg);
		if(n.getLabel().isPresent()) {
			VisitorUtil.AddAttribute(breakNode, JavaAttributesTypes.Target, n.getLabel().get().toString());
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/CastExpr.html
	 */
	@Override
	public void visit(CastExpr n, Node arg) {
		Node node = VisitorUtil.Node(JavaNodeTypes.Cast, arg);
		VisitorUtil.AddAttribute(node, JavaAttributesTypes.Type, n.getTypeAsString());
		n.getExpression().accept(this, node);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/CatchClause.html
	 */
	@Override
	public void visit(CatchClause n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/CharLiteralExpr.html
	 */
	@Override
	public void visit(CharLiteralExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ClassExpr.html
	 */
	@Override
	public void visit(ClassExpr n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ConditionalExpr.html
	 */
	@Override
	public void visit(ConditionalExpr n, Node arg) {
		Node p = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(p, JavaAttributesTypes.Condition, n.getCondition().toString());
		VisitorUtil.AddAttribute(p, JavaAttributesTypes.Then, n.getThenExpr().toString());
		VisitorUtil.AddAttribute(p, JavaAttributesTypes.Else, n.getElseExpr().toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/PackageDeclaration.html
	 */
	@Override
	public void visit(PackageDeclaration n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Package, n.getNameAsString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/ImportDeclaration.html
	 */
	@Override
	public void visit(ImportDeclaration n, Node arg) {
		Node leaf = VisitorUtil.Node(JavaNodeTypes.Import, arg);
		VisitorUtil.AddAttribute(leaf, JavaAttributesTypes.Asterisks, String.valueOf(n.isAsterisk()));
		VisitorUtil.AddAttribute(leaf, JavaAttributesTypes.Static, String.valueOf(n.isStatic()));
		VisitorUtil.AddAttribute(leaf, JavaAttributesTypes.Name, String.valueOf(n.getName()));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/FieldAccessExpr.html
	 */
	@Override
	public void visit(FieldAccessExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Scope, n.getScope().toString());
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Name, n.getNameAsString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/FieldDeclaration.html
	 */
	@Override
	public void visit(FieldDeclaration n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg)); // TODO super call?
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/LambdaExpr.html
	 */
	@Override
	public void visit(LambdaExpr n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/LocalClassDeclarationStmt.html
	 */
	@Override
	public void visit(LocalClassDeclarationStmt n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/LongLiteralExpr.html
	 */
	@Override
	public void visit(LongLiteralExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MarkerAnnotationExpr.html
	 */
	@Override
	public void visit(MarkerAnnotationExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Annotation, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MemberValuePair.html
	 */
	@Override
	public void visit(MemberValuePair n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Key, n.getNameAsString());
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Value, n.getValue().toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MethodCallExpr.html
	 */
	@Override
	public void visit(MethodCallExpr n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		
		// Scope
		if (n.getScope().isPresent()) {
			VisitorUtil.AddAttribute(c, JavaAttributesTypes.Scope, n.getScope().get().toString());
		}
		
		// TypeArguments
		if(n.getTypeArguments().isPresent()) {
			for(Type typeArgumentExpr : n.getTypeArguments().get()) {
				VisitorUtil.AddAttribute(c, JavaAttributesTypes.TypeArgument, typeArgumentExpr.toString());
			}
		}
		
		// Name
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Name, n.getNameAsString());
		
		// Arguments
		Node args = VisitorUtil.Node(JavaNodeTypes.Argument, c);
		int argSize = n.getArguments().size(); 
		VisitorUtil.AddAttribute(args, JavaAttributesTypes.Children, String.valueOf(argSize));
		for (int i = 0; i < argSize; i++) {
			Expression argumentExpr = n.getArgument(0);
			Node argNode = VisitorUtil.NodeSetElement(JavaNodeTypes.Argument, i, args);
			argumentExpr.accept(this, argNode);
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MethodReferenceExpr.html
	 */
	@Override
	public void visit(MethodReferenceExpr n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		
		// Identifier
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Identifier, n.getIdentifier());
		
		// Scope
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Scope, n.getScope().toString());
		
		// Type
		if (n.getTypeArguments().isPresent()) {
			n.getTypeArguments().get().forEach(typeArg -> VisitorUtil.AddAttribute(c, JavaAttributesTypes.Type, typeArg.toString()));
		}
		
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/Name.html
	 */
	@Override
	public void visit(Name n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Name, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NameExpr.html
	 */
	@Override
	public void visit(NameExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Name, n.getNameAsString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NormalAnnotationExpr.html
	 */
	@Override
	public void visit(NormalAnnotationExpr n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));		
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NullLiteralExpr.html
	 */
	@Override
	public void visit(NullLiteralExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ObjectCreationExpr.html
	 */
	@Override
	public void visit(ObjectCreationExpr n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);

		// Type
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Type, n.getTypeAsString());

		// Arguments
		Node arguments = VisitorUtil.Node(JavaNodeTypes.Argument, c);
		VisitorUtil.AddAttribute(arguments, JavaAttributesTypes.Children, String.valueOf(n.getArguments().size()));
		for (int i = 0; i < n.getArguments().size(); i++) {
			Node argNode = VisitorUtil.NodeSetElement(JavaNodeTypes.Argument, i, arguments);
			n.getArgument(i).accept(this, argNode);
		}

		// Anonymous Class Body
		if (n.getAnonymousClassBody().isPresent()) {
			Node anonClassBody = VisitorUtil.Node(JavaNodeTypes.Body, c);
			n.getAnonymousClassBody().get().forEach(decl -> decl.accept(this, anonClassBody));
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/Parameter.html
	 */
	@Override
	public void visit(Parameter n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Type, n.getTypeAsString());
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Name, n.getNameAsString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/type/PrimitiveType.html
	 */
	@Override
	public void visit(PrimitiveType n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Type, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/ReceiverParameter.html
	 */
	@Override
	public void visit(ReceiverParameter n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Type, n.getType().toString());
		super.visit(n, c);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/ReturnStmt.html
	 */
	@Override
	public void visit(ReturnStmt n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		super.visit(n, c);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SingleMemberAnnotationExpr.html
	 */
	@Override
	public void visit(SingleMemberAnnotationExpr n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Name, n.getNameAsString());
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Value, n.getMemberValue().toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/StringLiteralExpr.html
	 */
	@Override
	public void visit(StringLiteralExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SuperExpr.html
	 */
	@Override
	public void visit(SuperExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.SuperExpr, n.toString());
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SwitchEntry.html
	 */
	@Override
	public void visit(SwitchEntry n, Node arg) {
		Node parent = VisitorUtil.Parent(n, arg);
		
		// Label
		n.getLabels().forEach(label -> VisitorUtil.AddAttribute(parent, JavaAttributesTypes.Condition, label.toString()));
		if(n.getLabels().size() == 0) {
			VisitorUtil.AddAttribute(parent, JavaAttributesTypes.Default, String.valueOf(true));
		}
		
		// Type
		VisitorUtil.AddAttribute(parent, JavaAttributesTypes.Type, n.getType().name());
		
		// Statements
		n.getStatements().forEach(stmt -> stmt.accept(this, parent));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SwitchExpr.html
	 */
	@Override
	public void visit(SwitchExpr n, Node arg) {
		Node parent = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(parent, JavaAttributesTypes.Selector, n.getSelector().toString());
		n.getEntries().forEach(entry -> entry.accept(this, parent));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SwitchStmt.html
	 */
	@Override
	public void visit(SwitchStmt n, Node arg) {
		Node parent = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(parent, JavaAttributesTypes.Selector, n.getSelector().toString());
		n.getEntries().forEach(entry -> entry.accept(this, parent));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SynchronizedStmt.html
	 */
	@Override
	public void visit(SynchronizedStmt n, Node arg) {
		Node sync = VisitorUtil.Node(JavaNodeTypes.Synchronized, arg);
		VisitorUtil.AddAttribute(sync, JavaAttributesTypes.Expression, n.getExpression().toString());
		n.getBody().accept(this, sync);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/TextBlockLiteralExpr.html
	 */
	@Override
	public void visit(TextBlockLiteralExpr n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ThisExpr.html
	 */
	@Override
	public void visit(ThisExpr n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ThrowStmt.html
	 */
	@Override
	public void visit(ThrowStmt n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/TryStmt.html
	 */
	@Override
	public void visit(TryStmt n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/TypeExpr.html
	 */
	@Override
	public void visit(TypeExpr n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/TypeParameter.html
	 */
	@Override
	public void visit(TypeParameter n, Node arg) {
		Node p = VisitorUtil.Parent(n, arg);
		
		// Name
		VisitorUtil.AddAttribute(p, JavaAttributesTypes.Name, n.getNameAsString());
		
		// Annotations
		for(AnnotationExpr annotationExpr : n.getAnnotations()) {
			VisitorUtil.AddAttribute(p, JavaAttributesTypes.Annotation, annotationExpr.getNameAsString());
		}
		
		// Bounds
		int boundCounter = 0;
		for(ClassOrInterfaceType bound : n.getTypeBound()) {
			Node boundNode = VisitorUtil.NodeSetElement(JavaNodeTypes.Bound, boundCounter++, p);
			bound.accept(this, boundNode);
		}
		VisitorUtil.AddAttribute(p, JavaAttributesTypes.Bound, String.valueOf(n.getTypeBound().size()));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/UnaryExpr.html
	 */
	@Override
	public void visit(UnaryExpr n, Node arg) {
		Node unaryExprNode = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(unaryExprNode, JavaAttributesTypes.Name, n.getExpression().toString());
		VisitorUtil.AddAttribute(unaryExprNode, JavaAttributesTypes.Operator, n.getOperator().name());
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/UnionType.html
	 */
	@Override
	public void visit(UnionType n, Node arg) {
		Node unionTypeNode = VisitorUtil.Parent(n, arg);
		n.getElements().forEach(elem -> VisitorUtil.AddAttribute(unionTypeNode, JavaAttributesTypes.Type, elem.toString()));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/UnknownType.html
	 */
	@Override
	public void visit(UnknownType n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/UnparsableStmt.html
	 */
	@Override
	public void visit(UnparsableStmt n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/VariableDeclarationExpr.html
	 */
	@Override
	public void visit(VariableDeclarationExpr n, Node arg) {
		for(int childNodeCounter = 0; childNodeCounter < n.getChildNodes().size(); childNodeCounter++) {
			com.github.javaparser.ast.Node child = n.getChildNodes().get(childNodeCounter); 
			Node childNode = VisitorUtil.Parent(child, arg);
			n.getChildNodes().get(childNodeCounter).accept(this, childNode);
		}
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/index.html?com/github/javaparser/ast/expr/MemberValuePair.html
	 */
	@Override
	public void visit(VariableDeclarator n, Node arg) {
		// Type
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Type, n.getTypeAsString());
		
		// Name
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Name, n.getNameAsString());
		
		// Initializer
		if (n.getInitializer().isPresent()) {
			VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Initilization, n.getInitializer().get().toString());
		}
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/VarType.html
	 */
	@Override
	public void visit(VarType n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/VoidType.html
	 */
	@Override
	public void visit(VoidType n, Node arg) {
		JavaAttributesTypes type = JavaAttributesTypes.Value;
		if (n.getParentNode().get() instanceof MethodDeclaration) {
			type = JavaAttributesTypes.ReturnType;
		}
		VisitorUtil.AddAttribute(arg, type, n.toString());
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/WhileStmt.html
	 */
	@Override
	public void visit(WhileStmt n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/WildcardType.html
	 */
	@Override
	public void visit(WildcardType n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/YieldStmt.html
	 */
	@Override
	public void visit(YieldStmt n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/ConstructorDeclaration.html
	 * 
	 */
	@Override
	public void visit(ConstructorDeclaration n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/ContinueStmt.html
	 */
	@Override
	public void visit(ContinueStmt n, Node arg) {
		Node continueNode = VisitorUtil.Node(JavaNodeTypes.Continue, arg);
		if(n.getLabel().isPresent()) {
			VisitorUtil.AddAttribute(continueNode, JavaAttributesTypes.Target, n.getLabel().get().toString());
		}
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/DoStmt.html
	 */
	@Override
	public void visit(DoStmt n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));

	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/EmptyStmt.html
	 */
	@Override
	public void visit(EmptyStmt n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/DoubleLiteralExpr.html
	 */
	@Override
	public void visit(DoubleLiteralExpr n, Node arg) {
		VisitorUtil.Leaf(n, arg);

	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/EnclosedExpr.html
	 */

	@Override
	public void visit(EnclosedExpr n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/EnumConstantDeclaration.html
	 */

	@Override
	public void visit(EnumConstantDeclaration n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/EnumDeclaration.html
	 */
	@Override
	public void visit(EnumDeclaration n, Node arg) {

		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/ExplicitConstructorInvocationStmt.html
	 */

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		
		// TypeArguments
		if(n.getTypeArguments().isPresent()) {
			for(Type typeArgumentExpr : n.getTypeArguments().get()) {
				VisitorUtil.AddAttribute(c, JavaAttributesTypes.TypeArgument, typeArgumentExpr.toString());
			}
		}
		
		// Arguments
		Node args = VisitorUtil.Node(JavaNodeTypes.Argument, c);
		int argSize = n.getArguments().size(); 
		VisitorUtil.AddAttribute(args, JavaAttributesTypes.Children, String.valueOf(argSize));
		for (int i = 0; i < argSize; i++) {
			Expression argumentExpr = n.getArgument(0);
			Node argNode = VisitorUtil.NodeSetElement(JavaNodeTypes.Argument, i, args);
			argumentExpr.accept(this, argNode);
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/ForEachStmt.html
	 */
	@Override
	public void visit(ForEachStmt n, Node arg) {
		Node p = VisitorUtil.Parent(n, arg);
		
		// Iterator
		VisitorUtil.AddAttribute(p, JavaAttributesTypes.Iterator, n.getIterable().toString());
		
		// Initilization
		VisitorUtil.AddAttribute(p, JavaAttributesTypes.Initilization, n.getVariableDeclarator().getNameAsString());
		VisitorUtil.AddAttribute(p, JavaAttributesTypes.Type, n.getVariableDeclarator().getTypeAsString());
		
		// Block
		n.getBody().accept(this, p);

	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/ForStmt.html
	 */
	@Override
	public void visit(ForStmt n, Node arg) {
		Node p = VisitorUtil.Parent(n, arg);

		// Comparison
		if (n.getCompare().isPresent()) {
			VisitorUtil.AddAttribute(p, JavaAttributesTypes.Comparison, n.getCompare().get().toString());
		}
		n.removeCompare(); // rm bc visited

		// Initializations
		for (int i = 0; i < n.getInitialization().size(); i++) {
			Expression initExpr = n.getInitialization().get(0);
			VisitorUtil.AddAttribute(p, JavaAttributesTypes.Initilization, initExpr.toString());
			initExpr.removeForced();
		}

		// Updates
		for (int i = 0; i < n.getUpdate().size(); i++) {
			Expression updateExpr = n.getUpdate().get(0);
			VisitorUtil.AddAttribute(p, JavaAttributesTypes.Update, updateExpr.toString());
			updateExpr.removeForced();
		}

		// Leftovers
		super.visit(n, p);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/IfStmt.html
	 */
	@Override
	public void visit(IfStmt n, Node arg) {
		Node p = VisitorUtil.Parent(n, arg);
		this.visitIfStmt(n, p);
	}

	/**
	 * Util to generate multiple 'Then' cases
	 * 
	 * @param n   If Statement
	 * @param arg Parent Node
	 */
	private void visitIfStmt(IfStmt n, Node arg) {
		// Fall through
		Statement thenStmt = n.getThenStmt();
		Node thenNode = VisitorUtil.Node(JavaNodeTypes.Then, arg);
		VisitorUtil.AddAttribute(thenNode, JavaAttributesTypes.Condition, n.getCondition().toString());
		thenStmt.accept(this, thenNode);
		n.remove(thenStmt);

		// Block
		if (n.getElseStmt().isPresent()) {
			Statement elseStmt = n.getElseStmt().get();
			if(elseStmt instanceof IfStmt) {
				visitIfStmt((IfStmt) elseStmt, arg);
			} else {
				Node elseNode = VisitorUtil.Node(JavaNodeTypes.Else, arg);
				elseStmt.accept(this, elseNode);
			}
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/InitializerDeclaration.html
	 */
	@Override
	public void visit(InitializerDeclaration n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/InstanceOfExpr.html/
	 */
	@Override
	public void visit(InstanceOfExpr n, Node arg) {
		Node c = VisitorUtil.Parent(n, arg);
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Type, n.getType().toString());
		VisitorUtil.AddAttribute(c, JavaAttributesTypes.Expression, n.getExpression().toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/IntegerLiteralExpr.html
	 */
	@Override
	public void visit(IntegerLiteralExpr n, Node arg) {
		VisitorUtil.AddAttribute(arg, JavaAttributesTypes.Value, n.asNumber().toString());

	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/type/IntersectionType.html
	 */
	@Override
	public void visit(IntersectionType n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}
}