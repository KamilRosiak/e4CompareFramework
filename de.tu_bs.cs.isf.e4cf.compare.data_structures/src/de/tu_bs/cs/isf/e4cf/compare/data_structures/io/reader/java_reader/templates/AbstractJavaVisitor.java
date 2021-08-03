package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.templates;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.PatternExpr;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsDirective;
import com.github.javaparser.ast.modules.ModuleOpensDirective;
import com.github.javaparser.ast.modules.ModuleProvidesDirective;
import com.github.javaparser.ast.modules.ModuleRequiresDirective;
import com.github.javaparser.ast.modules.ModuleUsesDirective;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaAttributesTypes;

public abstract class AbstractJavaVisitor implements VoidVisitor<Node> {

	/**
	 * Visits all children but specified exceptions of the node n.
	 * 
	 * @param n          JavaParser Node
	 * @param arg        e4cf Parent Node
	 * @param exceptions Nodes that should not be visited
	 */
	protected synchronized void visitor(com.github.javaparser.ast.Node n, Node arg, com.github.javaparser.ast.Node... exceptions) {
		// Comments are no child nodes. Therefore they are added explicitly.
		n.getComment().ifPresent(comment -> arg.addAttribute(JavaAttributesTypes.Comment.name(), new StringValueImpl(comment.getContent())));
		NodeList<com.github.javaparser.ast.Node> exceptionList = NodeList.nodeList(exceptions);
		for (com.github.javaparser.ast.Node childNode : n.getChildNodes()) {
			if (!exceptionList.contains(childNode)) {
				childNode.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(NodeList n, Node arg) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ExpressionStmt n, Node arg) {
		// TODO Auto-generated method stub
		visitor(n, arg);
	}

	@Override
	public void visit(LabeledStmt n, Node arg) {
		Node labeledStmt = new NodeImpl(NodeType.LABELED_STATEMENT, n.getClass().getSimpleName(), arg);
		labeledStmt.addAttribute(JavaAttributesTypes.Name.name(), new StringValueImpl(n.getLabel().asString()));
		visitor(n, labeledStmt);
	}

	@Override
	public void visit(ModuleDeclaration n, Node arg) {
		// TODO Auto-generated method stub
		visitor(n, arg);
	}

	@Override
	public void visit(ModuleRequiresDirective n, Node arg) {
		// TODO Auto-generated method stub
		visitor(n, arg);
	}

	@Override
	public void visit(ModuleExportsDirective n, Node arg) {
		// TODO Auto-generated method stub
		visitor(n, arg);
	}

	@Override
	public void visit(ModuleProvidesDirective n, Node arg) {
		// TODO Auto-generated method stub
		visitor(n, arg);
	}

	@Override
	public void visit(ModuleUsesDirective n, Node arg) {
		// TODO Auto-generated method stub
		visitor(n, arg);
	}

	@Override
	public void visit(ModuleOpensDirective n, Node arg) {
		// TODO Auto-generated method stub
		visitor(n, arg);
	}

	@Override
	public void visit(PatternExpr n, Node arg) {
		// TODO Auto-generated method stub
		visitor(n, arg);
	}
}
