package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.factory;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaAttributesTypes;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaNodeTypes;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaVisitor;

public class StatementNodeFactory implements IStatementNodeFactory {

	@Override
	public Node createStatementNode(Statement stmt, Node parent, JavaVisitor visitor) {
		if (stmt instanceof ForStmt) {
			return createForStmt((ForStmt) stmt, parent, visitor);
		}
		if (stmt instanceof IfStmt) {
			return createIfStmtNode((IfStmt) stmt, parent, visitor);
		}
		if (stmt instanceof ForEachStmt) {
			return createForEachStmtNode((ForEachStmt) stmt, parent, visitor);
		}
		return null;
	}

	/**
	 * This method creates a Node for a ForEachStmt statement.
	 */
	private Node createForEachStmtNode(ForEachStmt stmt, Node parent, JavaVisitor visitor) {
		Node p = new NodeImpl(NodeType.LOOP_COLLECTION_CONTROLLED, stmt.getClass().getSimpleName(), parent);
		// Add Iterator as attribute
		p.addAttribute(JavaAttributesTypes.Iterator.name(), new StringValueImpl(stmt.getIterable().toString()));
		// Add the initiliaze values
		p.addAttribute(JavaAttributesTypes.Initialization.name(), new StringValueImpl(stmt.getVariableDeclarator().getNameAsString()));
		p.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(stmt.getVariableDeclarator().getTypeAsString()));
		return p;
	}

	/**
	 * This method creates a Node for a ForStmt statement.
	 */
	private Node createForStmt(ForStmt forStmt, Node parent, JavaVisitor visitor) {
		Node forStmtNode = new NodeImpl(NodeType.LOOP_COUNT_CONTROLLED, forStmt.getClass().getSimpleName(), parent);

		// Comparison
		if (forStmt.getCompare().isPresent()) {
			forStmtNode.addAttribute(JavaAttributesTypes.Comparison.name(), new StringValueImpl(forStmt.getCompare().get().toString()));
		}
		forStmt.removeCompare(); // rm bc visited

		// Initializations
		int initializations = forStmt.getInitialization().size();
		for (int i = 0; i < initializations; i++) {
			Expression initExpr = forStmt.getInitialization().get(0);
			forStmtNode.addAttribute(JavaAttributesTypes.Initialization.name(), new StringValueImpl(initExpr.toString()));
			initExpr.removeForced();
		}

		// Updates
		int updates = forStmt.getUpdate().size();
		for (int i = 0; i < updates; i++) {
			Expression updateExpr = forStmt.getUpdate().get(0);
			forStmtNode.addAttribute(JavaAttributesTypes.Update.name(), new StringValueImpl(updateExpr.toString()));
			updateExpr.removeForced();
		}
		return forStmtNode;
	}

	/**
	 * This method creates a Node for a IfStmt statement.
	 */
	private Node createIfStmtNode(IfStmt ifStmt, Node arg, JavaVisitor visitor) {
		Node ifStmtNode = new NodeImpl(NodeType.IF, ifStmt.getClass().getSimpleName(), arg);
		ifStmtNode.addAttribute(JavaAttributesTypes.Condition.name(), new StringValueImpl(ifStmt.getCondition().toString()));

		// Fall through
		Statement thenStmt = ifStmt.getThenStmt();
		Node thenNode = new NodeImpl(NodeType.THEN, JavaNodeTypes.Then.name(), ifStmtNode);
		thenStmt.accept(visitor, thenNode);

		// Else
		if (ifStmt.getElseStmt().isPresent()) {
			Statement elseStmt = ifStmt.getElseStmt().get();
			Node elseNode = new NodeImpl(NodeType.ELSE, JavaNodeTypes.Else.name(), ifStmtNode);
			if (elseStmt instanceof IfStmt) {
				createIfStmtNode((IfStmt) elseStmt, elseNode, visitor);
			} else {
				elseStmt.accept(visitor, elseNode);
			}
		}
		return ifStmtNode;
	}

}
