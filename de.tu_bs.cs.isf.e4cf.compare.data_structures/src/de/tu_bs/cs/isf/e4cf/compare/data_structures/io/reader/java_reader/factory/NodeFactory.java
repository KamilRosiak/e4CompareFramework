package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.factory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.nodeTypes.NodeWithArguments;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaAttributesTypes;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaNodeTypes;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaVisitor;

/**
 * This class contains method for the transformation of java parser nodes into a
 * Node representation.
 * 
 * @author Kamil Rosiak
 *
 */
public class NodeFactory {
	private IStatementNodeFactory statemenNodeFactory;

	public NodeFactory(IStatementNodeFactory statemenNodeFactory) {
		this.setStatemenNodeFactory(statemenNodeFactory);
	}

	/**
	 * This method attaches arguments to a given parent node.
	 */
	public void attachArguments(NodeWithArguments n, Node parent, JavaVisitor visitor) {
		Node args = new NodeImpl(JavaNodeTypes.Argument.name(), parent);
		int argSize = n.getArguments().size();
		args.addAttribute(JavaAttributesTypes.Children.name(), String.valueOf(argSize));
		for (int i = 0; i < argSize; i++) {
			Expression argumentExpr = n.getArgument(0);
			Node argNode = new NodeImpl(JavaNodeTypes.Argument.name() + i, args);
			argumentExpr.accept(visitor, argNode);
			argumentExpr.removeForced();
		}
	}

	public Node createCompilationUnitNode(CompilationUnit compilationUnit, Node parent, JavaVisitor visitor) {
		Node cu = new NodeImpl(compilationUnit.getClass().getSimpleName(), parent);
		
		// Attach imports if available
		if(!compilationUnit.getImports().isEmpty()) {
			Node imports = new NodeImpl(JavaNodeTypes.Import.name(), cu);
			for(ImportDeclaration importDecl : compilationUnit.getImports()) {
				attachImportDecl(importDecl, imports, visitor);
				importDecl.removeForced();
			}
		}
		return cu;
	}

	public Node createClassOrInterfaceNode(ClassOrInterfaceDeclaration n, Node parent) {
		Node classOrInterfaceDeclarationNode = parent;
		if (!n.getParentNode().isPresent() || !(n.getParentNode().get() instanceof CompilationUnit)) {
			classOrInterfaceDeclarationNode = new NodeImpl(
					n.isInterface() ? JavaNodeTypes.Interface.name() : JavaNodeTypes.Class.name(), parent);
		}

		// Class or Interface?
		classOrInterfaceDeclarationNode.addAttribute(JavaAttributesTypes.IsInterface.name(),
				String.valueOf(n.isInterface()));

		// Superclass
		if (n.getExtendedTypes().size() > 0) {
			// Only a single class can be inherited!
			ClassOrInterfaceType superclass = n.getExtendedTypes(0);
			classOrInterfaceDeclarationNode.addAttribute(JavaAttributesTypes.Superclass.name(), superclass.toString());
			superclass.removeForced();
		}

		// Interfaces
		int interfaceSize = n.getImplementedTypes().size();
		for (int i = 0; i < interfaceSize; i++) {
			// Multiple classes can be implemented
			ClassOrInterfaceType implemented = n.getImplementedTypes(0);
			classOrInterfaceDeclarationNode.addAttribute(JavaAttributesTypes.Interface.name(), implemented.toString());
			implemented.removeForced();
		}
		return classOrInterfaceDeclarationNode;
	}

	/**
	 * Creates the leaf node for the import Declaration
	 */
	public void attachImportDecl(ImportDeclaration importDecl, Node parent, JavaVisitor visitor) {
		Node leaf = new NodeImpl(JavaNodeTypes.Import.name(), parent);
		leaf.addAttribute(JavaAttributesTypes.Asterisks.name(), String.valueOf(importDecl.isAsterisk()));
		leaf.addAttribute(JavaAttributesTypes.Static.name(), String.valueOf(importDecl.isStatic()));
		importDecl.getName().accept(visitor, leaf);
	}

	/**
	 * This method transforms a MethodDeclaration into a Node
	 * 
	 * @param n
	 * @param arg
	 * @param visitor
	 * @return
	 */
	public Node createMethodDeclNode(MethodDeclaration n, Node arg, JavaVisitor visitor) {
		Node p = new NodeImpl(n.getClass().getSimpleName(), arg);

		// Add Throws as attributes
		for (int i = n.getThrownExceptions().size(); i > 0; i--) {
			ReferenceType referenceType = n.getThrownException(0);
			p.addAttribute(JavaAttributesTypes.Throws.name(), referenceType.asString());
			referenceType.removeForced();
		}

		// Add the paramter of the method declariton Parameter
		Node args = new NodeImpl(JavaNodeTypes.Argument.name(), p);
		int argList = n.getParameters().size();
		args.addAttribute(JavaAttributesTypes.Children.name(), String.valueOf(argList));
		for (int i = 0; i < argList; i++) {
			Parameter concreteParameter = n.getParameter(0);
			Node argNode = new NodeImpl(JavaNodeTypes.Argument.name() + i, args);
			argNode.addAttribute(JavaAttributesTypes.Type.name(), concreteParameter.getTypeAsString());
			concreteParameter.getName().accept(visitor, argNode);
			concreteParameter.getModifiers().forEach(modif -> modif.accept(visitor, argNode));
			concreteParameter.removeForced();
		}
		// Add the returing type as attribute
		p.addAttribute(JavaAttributesTypes.ReturnType.name(), n.getTypeAsString());
		return p;
	}

	public IStatementNodeFactory getStatemenNodeFactory() {
		return statemenNodeFactory;
	}

	public void setStatemenNodeFactory(IStatementNodeFactory statemenNodeFactory) {
		this.statemenNodeFactory = statemenNodeFactory;
	}

}
