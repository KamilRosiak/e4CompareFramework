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

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
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
	@SuppressWarnings("rawtypes")
	public void attachArguments(NodeWithArguments n, Node parent, JavaVisitor visitor) {
		Node args = new NodeImpl(NodeType.ARGUMENT, JavaNodeTypes.Argument.name(), parent);
		int argSize = n.getArguments().size();

		for (int i = 0; i < argSize; i++) {
			Expression argumentExpr = n.getArgument(i);
			Node argNode = new NodeImpl(NodeType.ARGUMENT, JavaNodeTypes.Argument.name() + i, args);
			argumentExpr.accept(visitor, argNode);
		}
		n.getArguments().clear();
		
	}

	public Node createCompilationUnitNode(CompilationUnit compilationUnit, Node parent, JavaVisitor visitor) {
		Node cu = new NodeImpl(NodeType.COMPILATION_UNIT, compilationUnit.getClass().getSimpleName(), parent);
		
		// Attach imports if available
		if(!compilationUnit.getImports().isEmpty()) {
			Node imports = new NodeImpl(NodeType.IMPORT, JavaNodeTypes.Imports.name(), cu);
			while(compilationUnit.getImports().iterator().hasNext()) {
				ImportDeclaration importDecl = compilationUnit.getImports().iterator().next();
				attachImportDecl(importDecl, imports, visitor);
				importDecl.removeForced();
			}
		}
		return cu;
	}

	public Node createClassOrInterfaceNode(ClassOrInterfaceDeclaration n, Node parent) {
		Node classOrInterfaceDeclarationNode = parent;
		if (!n.getParentNode().isPresent() || !(n.getParentNode().get() instanceof CompilationUnit)) {
			if (n.isInterface()) {
				classOrInterfaceDeclarationNode = new NodeImpl(NodeType.INTERFACE, JavaNodeTypes.Interface.name(), parent);
			} else {
				classOrInterfaceDeclarationNode = new NodeImpl(NodeType.CLASS, JavaNodeTypes.Class.name(), parent);
			}
		}

		// Class or Interface?
		classOrInterfaceDeclarationNode.addAttribute(JavaAttributesTypes.IsInterface.name(),
				new StringValueImpl(String.valueOf(n.isInterface())));

		// Superclass
		if (n.getExtendedTypes().size() > 0) {
			// Only a single class can be inherited!
			ClassOrInterfaceType superclass = n.getExtendedTypes(0);
			classOrInterfaceDeclarationNode.addAttribute(JavaAttributesTypes.Superclass.name(), new StringValueImpl(superclass.toString()));
			superclass.removeForced();
		}

		// Interfaces
		int interfaceSize = n.getImplementedTypes().size();
		for (int i = 0; i < interfaceSize; i++) {
			// Multiple classes can be implemented
			ClassOrInterfaceType implemented = n.getImplementedTypes(0);
			classOrInterfaceDeclarationNode.addAttribute(JavaAttributesTypes.Interface.name(), new StringValueImpl(implemented.toString()));
			implemented.removeForced();
		}
		return classOrInterfaceDeclarationNode;
	}

	/**
	 * Creates the leaf node for the import Declaration
	 */
	public void attachImportDecl(ImportDeclaration importDecl, Node parent, JavaVisitor visitor) {
		Node leaf = new NodeImpl(NodeType.IMPORT, JavaNodeTypes.Import.name(), parent);
		leaf.addAttribute(JavaAttributesTypes.Asterisks.name(), new StringValueImpl(String.valueOf(importDecl.isAsterisk())));
		leaf.addAttribute(JavaAttributesTypes.Static.name(), new StringValueImpl(String.valueOf(importDecl.isStatic())));
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
		Node p = new NodeImpl(NodeType.METHOD_DECLARATION, n.getClass().getSimpleName(), arg);

		// Add Throws as attributes
		for (int i = n.getThrownExceptions().size(); i > 0; i--) {
			ReferenceType referenceType = n.getThrownException(0);
			p.addAttribute(JavaAttributesTypes.Throws.name(), new StringValueImpl(referenceType.asString()));
			referenceType.removeForced();
		}

		// Add the parameter of the method declaration Parameter
		Node args = new NodeImpl(NodeType.ARGUMENT, JavaNodeTypes.Argument.name(), p);
		int argList = n.getParameters().size();
		for (int i = 0; i < argList; i++) {
			Parameter concreteParameter = n.getParameter(0);
			Node argNode = new NodeImpl(NodeType.ARGUMENT, JavaNodeTypes.Argument.name() + i, args);
			argNode.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(concreteParameter.getTypeAsString()));
			concreteParameter.getName().accept(visitor, argNode);
			concreteParameter.getModifiers().forEach(modif -> modif.accept(visitor, argNode));
			concreteParameter.removeForced();
		}
		// Add the returing type as attribute
		p.addAttribute(JavaAttributesTypes.ReturnType.name(), new StringValueImpl(n.getTypeAsString()));
		return p;
	}

	public IStatementNodeFactory getStatemenNodeFactory() {
		return statemenNodeFactory;
	}

	public void setStatemenNodeFactory(IStatementNodeFactory statemenNodeFactory) {
		this.statemenNodeFactory = statemenNodeFactory;
	}

}
