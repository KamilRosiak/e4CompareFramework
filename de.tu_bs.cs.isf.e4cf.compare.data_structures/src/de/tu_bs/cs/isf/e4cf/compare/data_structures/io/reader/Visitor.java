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
		Node imports = new NodeImpl(ImportDeclaration.class.getSimpleName(), cu);
		int importSize = n.getImports().size();
		imports.addAttribute(JavaNodeTypes.Children.name(), String.valueOf(importSize));
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
			p.addAttribute(JavaNodeTypes.Throws.name(), referenceType.asString());
			referenceType.removeForced();
		}
		
		// Arguments
		Node args = new NodeImpl(JavaNodeTypes.Argument.name(), p);
		int argList = n.getParameters().size();
		args.addAttribute(JavaNodeTypes.Children.name(), String.valueOf(argList));
		for (int i = 0; i < argList; i++) {
			Parameter concreteParameter = n.getParameter(0);
			Node argNode = new NodeImpl(JavaNodeTypes.Argument.name() + i, args);
			argNode.addAttribute(JavaNodeTypes.Type.name(), concreteParameter.getTypeAsString());
			argNode.addAttribute(JavaNodeTypes.Name.name(), concreteParameter.getNameAsString());
			concreteParameter.removeForced();
		}

		super.visit(n, p);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/Modifier.html
	 */
	@Override
	public void visit(Modifier n, Node arg) {
		arg.addAttribute(n.getClass().getSimpleName(), n.getKeyword().name());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SimpleName.html
	 */
	@Override
	public void visit(SimpleName n, Node arg) {
		if (!(n.getParentNode().get() instanceof ClassOrInterfaceDeclaration)) {
			arg.addAttribute(JavaNodeTypes.Name.name(), n.toString());
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/ClassOrInterfaceDeclaration.html
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Node arg) {
		Node classOrInterfaceDeclarationNode = arg;
		if (!n.getParentNode().isPresent() || !(n.getParentNode().get() instanceof CompilationUnit)) {
			classOrInterfaceDeclarationNode = new NodeImpl(n.isInterface() ? JavaNodeTypes.Interface.name() : JavaNodeTypes.Class.name(), arg);
		}
		
		// Class or Interface?
			classOrInterfaceDeclarationNode.addAttribute(JavaNodeTypes.isInterface.name(), String.valueOf(n.isInterface()));

		// Name
		SimpleName simpleName = n.getName();
		classOrInterfaceDeclarationNode.addAttribute(JavaNodeTypes.Name.name(), simpleName.asString());
		// simpleName.removeForced(); // SimpleName is unremovable -> Solution cf.
		// visit(SimpleName,Node)

		// Superclass
		if (n.getExtendedTypes().size() > 0) {
			// Only a single class can be inherited!
			ClassOrInterfaceType superclass = n.getExtendedTypes(0);
			classOrInterfaceDeclarationNode.addAttribute(JavaNodeTypes.Superclass.name(), superclass.getNameAsString());
			superclass.removeForced();
		}

		// Interfaces
		int interfaceSize = n.getImplementedTypes().size();
		for (int i = 0; i < interfaceSize; i++) {
			// Multiple classes can be implemented
			ClassOrInterfaceType implemented = n.getImplementedTypes(0);
			classOrInterfaceDeclarationNode.addAttribute(JavaNodeTypes.Interface.name(), implemented.getNameAsString());
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
		arg.addAttribute(JavaNodeTypes.Value.toString(), n.toString());
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
		arg.addAttribute(JavaNodeTypes.Value.name(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/type/ArrayType.html
	 */
	@Override
	public void visit(ArrayType n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Type.toString(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/AssertStmt.html
	 */
	@Override
	public void visit(AssertStmt n, Node arg) {
		Node parent = VisitorUtil.Parent(n, arg);
		parent.addAttribute(JavaNodeTypes.Check.name(), n.getCheck().toString());
		if(n.getMessage().isPresent()) {
			parent.addAttribute(JavaNodeTypes.Message.name(), n.getMessage().get().toString());
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/AssignExpr.html
	 */
	@Override
	public void visit(AssignExpr n, Node arg) {
		Node assignment = new NodeImpl(JavaNodeTypes.Assignment.name(), arg);
		Expression target = n.getTarget();
		Expression value = n.getValue();
		if (target.getChildNodes().size() <= 1) {
			// It is a simple target, e.g. 'x'
			assignment.addAttribute(JavaNodeTypes.Target.name(), target.toString());
		} else {
			target.accept(this, assignment);
		}
		if (value.getChildNodes().size() <= 1) {
			// It is a simple value, e.g. '1'
			assignment.addAttribute(JavaNodeTypes.Value.name(), value.toString());
		} else {
			value.accept(this, assignment);
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/BinaryExpr.html
	 */
	@Override
	public void visit(BinaryExpr n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Value.toString(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/BlockStmt.html
	 */
	@Override
	public void visit(BlockStmt n, Node arg) {
		super.visit(n, new NodeImpl(JavaNodeTypes.Body.name(), arg));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/BooleanLiteralExpr.html
	 */
	@Override
	public void visit(BooleanLiteralExpr n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Value.toString(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/BreakStmt.html
	 */
	@Override
	public void visit(BreakStmt n, Node arg) {
		Node breakNode = new NodeImpl(JavaNodeTypes.Break.name(), arg);
		if(n.getLabel().isPresent()) {
			breakNode.addAttribute(JavaNodeTypes.Target.name(), n.getLabel().get().toString());
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/CastExpr.html
	 */
	@Override
	public void visit(CastExpr n, Node arg) {
		Node node = new NodeImpl(JavaNodeTypes.Cast.name(), arg);
		node.addAttribute(JavaNodeTypes.Type.name(), n.getTypeAsString());
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
		arg.addAttribute(JavaNodeTypes.Value.toString(), n.toString());
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
		p.addAttribute(JavaNodeTypes.Condition.name(), n.getCondition().toString());
		p.addAttribute(JavaNodeTypes.Then.name(), n.getThenExpr().toString());
		p.addAttribute(JavaNodeTypes.Else.name(), n.getElseExpr().toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/PackageDeclaration.html
	 */
	@Override
	public void visit(PackageDeclaration n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Package.name(), n.getNameAsString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/ImportDeclaration.html
	 */
	@Override
	public void visit(ImportDeclaration n, Node arg) {
		Node leaf = new NodeImpl(JavaNodeTypes.Import.name(), arg);
		leaf.addAttribute(JavaNodeTypes.Asterisks.name(), String.valueOf(n.isAsterisk()));
		leaf.addAttribute(JavaNodeTypes.Static.name(), String.valueOf(n.isStatic()));
		leaf.addAttribute(JavaNodeTypes.Name.name(), String.valueOf(n.getName()));
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/FieldAccessExpr.html
	 */
	@Override
	public void visit(FieldAccessExpr n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Scope.name(), n.getScope().toString());
		arg.addAttribute(JavaNodeTypes.Name.name(), n.getNameAsString());
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
		arg.addAttribute(JavaNodeTypes.Value.name(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MarkerAnnotationExpr.html
	 */
	@Override
	public void visit(MarkerAnnotationExpr n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Annotation.toString(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MemberValuePair.html
	 */
	@Override
	public void visit(MemberValuePair n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaNodeTypes.Key.toString(), n.getNameAsString());
		c.addAttribute(JavaNodeTypes.Value.toString(), n.getValue().toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MethodCallExpr.html
	 */
	@Override
	public void visit(MethodCallExpr n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		
		// Scope
		if (n.getScope().isPresent()) {
			c.addAttribute(JavaNodeTypes.Scope.toString(), n.getScope().get().toString());
		}
		
		// TypeArguments
		if(n.getTypeArguments().isPresent()) {
			for(Type typeArgumentExpr : n.getTypeArguments().get()) {
				c.addAttribute(JavaNodeTypes.Type.name() + JavaNodeTypes.Argument.name(), typeArgumentExpr.toString());
			}
		}
		
		// Name
		c.addAttribute(JavaNodeTypes.Name.name(), n.getNameAsString());
		
		// Arguments
		Node args = new NodeImpl(JavaNodeTypes.Argument.name(), c);
		int argSize = n.getArguments().size(); 
		args.addAttribute(JavaNodeTypes.Children.name(), String.valueOf(argSize));
		for (int i = 0; i < argSize; i++) {
			Expression argumentExpr = n.getArgument(0);
			Node argNode = new NodeImpl(JavaNodeTypes.Argument.name() + i, args);
			argumentExpr.accept(this, argNode);
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MethodReferenceExpr.html
	 */
	@Override
	public void visit(MethodReferenceExpr n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		
		// Identifier
		c.addAttribute(JavaNodeTypes.Identifier.name(), n.getIdentifier());
		
		// Scope
		c.addAttribute(JavaNodeTypes.Scope.name(), n.getScope().toString());
		
		// Type
		if (n.getTypeArguments().isPresent()) {
			n.getTypeArguments().get().forEach(typeArg -> c.addAttribute(JavaNodeTypes.Type.name(), typeArg.toString()));
		}
		
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/Name.html
	 */
	@Override
	public void visit(Name n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Name.name(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NameExpr.html
	 */
	@Override
	public void visit(NameExpr n, Node arg) {
		arg.addAttribute("Name", n.getNameAsString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NormalAnnotationExpr.html
	 */
	@Override
	public void visit(NormalAnnotationExpr n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		super.visit(n, c);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NullLiteralExpr.html
	 */
	@Override
	public void visit(NullLiteralExpr n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Value.toString(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ObjectCreationExpr.html
	 */
	@Override
	public void visit(ObjectCreationExpr n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);

		// Type
		c.addAttribute(JavaNodeTypes.Type.name(), n.getTypeAsString());

		// Arguments
		Node arguments = new NodeImpl(JavaNodeTypes.Argument.name(), c);
		arguments.addAttribute(JavaNodeTypes.Children.name(), String.valueOf(n.getArguments().size()));
		for (int i = 0; i < n.getArguments().size(); i++) {
			Node argNode = new NodeImpl(JavaNodeTypes.Argument.name() + i, arguments);
			n.getArgument(i).accept(this, argNode);
		}

		// Anonymous Class Body
		if (n.getAnonymousClassBody().isPresent()) {
			Node anonClassBody = new NodeImpl(JavaNodeTypes.Body.name(), c);
			n.getAnonymousClassBody().get().forEach(decl -> decl.accept(this, anonClassBody));
		}
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/Parameter.html
	 */
	@Override
	public void visit(Parameter n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaNodeTypes.Type.toString(), n.getTypeAsString());
		c.addAttribute(JavaNodeTypes.Name.toString(), n.getNameAsString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/type/PrimitiveType.html
	 */
	@Override
	public void visit(PrimitiveType n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Type.name(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/ReceiverParameter.html
	 */
	@Override
	public void visit(ReceiverParameter n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaNodeTypes.Type.toString(), n.getType().toString());
		super.visit(n, c);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/ReturnStmt.html
	 */
	@Override
	public void visit(ReturnStmt n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		super.visit(n, c);
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SingleMemberAnnotationExpr.html
	 */
	@Override
	public void visit(SingleMemberAnnotationExpr n, Node arg) {
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		c.addAttribute("Name", n.getNameAsString()); // TODO aufloesen
		c.addAttribute(JavaNodeTypes.Value.toString(), n.getMemberValue().toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/StringLiteralExpr.html
	 */
	@Override
	public void visit(StringLiteralExpr n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Value.toString(), n.toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SuperExpr.html
	 */
	@Override
	public void visit(SuperExpr n, Node arg) {
		arg.addAttribute(n.getClass().getSimpleName(), n.toString());
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SwitchEntry.html
	 */
	@Override
	public void visit(SwitchEntry n, Node arg) {
		Node parent = VisitorUtil.Parent(n, arg);
		
		// Label
		n.getLabels().forEach(label -> parent.addAttribute(JavaNodeTypes.Condition.name(), label.toString()));
		if(n.getLabels().size() == 0) {
			parent.addAttribute(JavaNodeTypes.Default.name(), String.valueOf(true));
		}
		
		// Type
		parent.addAttribute(JavaNodeTypes.Type.name(), n.getType().name());
		
		// Statements
		n.getStatements().forEach(stmt -> stmt.accept(this, parent));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SwitchExpr.html
	 */
	@Override
	public void visit(SwitchExpr n, Node arg) {
		Node parent = VisitorUtil.Parent(n, arg);
		parent.addAttribute(JavaNodeTypes.Selector.name(), n.getSelector().toString());
		n.getEntries().forEach(entry -> entry.accept(this, parent));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SwitchStmt.html
	 */
	@Override
	public void visit(SwitchStmt n, Node arg) {
		Node parent = VisitorUtil.Parent(n, arg);
		parent.addAttribute(JavaNodeTypes.Selector.name(), n.getSelector().toString());
		n.getEntries().forEach(entry -> entry.accept(this, parent));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SynchronizedStmt.html
	 */
	@Override
	public void visit(SynchronizedStmt n, Node arg) {
		Node sync = new NodeImpl(JavaNodeTypes.Synchronized.name(), arg);
		sync.addAttribute(JavaNodeTypes.Expression.name(), n.getExpression().toString());
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
		p.addAttribute(JavaNodeTypes.Name.name(), n.getNameAsString());
		
		// Annotations
		for(AnnotationExpr annotationExpr : n.getAnnotations()) {
			p.addAttribute(JavaNodeTypes.Annotation.name(), annotationExpr.getNameAsString());
		}
		
		// Bounds
		int boundCounter = 0;
		for(ClassOrInterfaceType bound : n.getTypeBound()) {
			Node boundNode = new NodeImpl(JavaNodeTypes.Bound.name() + boundCounter++, p);
			bound.accept(this, boundNode);
		}
		p.addAttribute(JavaNodeTypes.Bound.name(), String.valueOf(n.getTypeBound().size()));
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/UnaryExpr.html
	 */
	@Override
	public void visit(UnaryExpr n, Node arg) {
		VisitorUtil.Leaf(n, arg);
	}

	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/UnionType.html
	 */
	@Override
	public void visit(UnionType n, Node arg) {
		Node unionTypeNode = new NodeImpl(n.getClass().getSimpleName(), arg);
		n.getElements().forEach(elem -> unionTypeNode.addAttribute(JavaNodeTypes.Type.name(), elem.toString()));
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
			Node childNode = new NodeImpl(child.getClass().getSimpleName(), arg);
			n.getChildNodes().get(childNodeCounter).accept(this, childNode);
		}
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/index.html?com/github/javaparser/ast/expr/MemberValuePair.html
	 */
	@Override
	public void visit(VariableDeclarator n, Node arg) {
		// Type
		arg.addAttribute(JavaNodeTypes.Type.name(), n.getTypeAsString());
		
		// Name
		arg.addAttribute(JavaNodeTypes.Name.name(), n.getNameAsString());
		
		// Initializer
		if (n.getInitializer().isPresent()) {
			arg.addAttribute(JavaNodeTypes.Initilization.name(), n.getInitializer().get().toString());
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
		JavaNodeTypes type = JavaNodeTypes.Value;
		if (n.getParentNode().get() instanceof MethodDeclaration) {
			type = JavaNodeTypes.ReturnType;
		}
		arg.addAttribute(type.name(), n.toString());
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
		Node continueNode = new NodeImpl(JavaNodeTypes.Continue.name(), arg);
		if(n.getLabel().isPresent()) {
			continueNode.addAttribute(JavaNodeTypes.Target.name(), n.getLabel().get().toString());
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
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		
		// TypeArguments
		if(n.getTypeArguments().isPresent()) {
			for(Type typeArgumentExpr : n.getTypeArguments().get()) {
				c.addAttribute(JavaNodeTypes.Type.name() + JavaNodeTypes.Argument.name(), typeArgumentExpr.toString());
			}
		}
		
		// Arguments
		Node args = new NodeImpl(JavaNodeTypes.Argument.name(), c);
		int argSize = n.getArguments().size(); 
		args.addAttribute(JavaNodeTypes.Children.name(), String.valueOf(argSize));
		for (int i = 0; i < argSize; i++) {
			Expression argumentExpr = n.getArgument(0);
			Node argNode = new NodeImpl(JavaNodeTypes.Argument.name() + i, args);
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
		p.addAttribute(JavaNodeTypes.Iterator.name(), n.getIterable().toString());
		
		// Initilization
		p.addAttribute(JavaNodeTypes.Initilization.name(), n.getVariableDeclarator().toString());
		
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
			p.addAttribute(JavaNodeTypes.Comparison.name(), n.getCompare().get().toString());
		}
		n.removeCompare(); // rm bc visited

		// Initializations
		for (int i = 0; i < n.getInitialization().size(); i++) {
			Expression initExpr = n.getInitialization().get(0);
			p.addAttribute(JavaNodeTypes.Initilization.name(), initExpr.toString());
			initExpr.removeForced();
		}

		// Updates
		for (int i = 0; i < n.getUpdate().size(); i++) {
			Expression updateExpr = n.getUpdate().get(0);
			p.addAttribute(JavaNodeTypes.Update.name(), updateExpr.toString());
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
		Node thenNode = new NodeImpl(JavaNodeTypes.Then.name(), arg);
		thenNode.addAttribute(JavaNodeTypes.Condition.name(), n.getCondition().toString());
		thenStmt.accept(this, thenNode);
		n.remove(thenStmt);

		// Block
		if (n.getElseStmt().isPresent()) {
			Statement elseStmt = n.getElseStmt().get();
			if(elseStmt instanceof IfStmt) {
				visitIfStmt((IfStmt) elseStmt, arg);
			} else {
				Node elseNode = new NodeImpl(JavaNodeTypes.Else.name(), arg);
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
		Node c = new NodeImpl(n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaNodeTypes.Type.toString(), n.getType().toString());
		c.addAttribute(JavaNodeTypes.Expression.toString(), n.getExpression().toString());
	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/IntegerLiteralExpr.html
	 */
	@Override
	public void visit(IntegerLiteralExpr n, Node arg) {
		arg.addAttribute(JavaNodeTypes.Value.toString(), n.asNumber().toString());

	}

	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/type/IntersectionType.html
	 */
	@Override
	public void visit(IntersectionType n, Node arg) {
		super.visit(n, VisitorUtil.Parent(n, arg));
	}
}