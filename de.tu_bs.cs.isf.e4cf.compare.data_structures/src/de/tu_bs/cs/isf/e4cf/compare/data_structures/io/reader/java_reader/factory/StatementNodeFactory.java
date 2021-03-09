package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.factory;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
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
	if(stmt instanceof ForEachStmt) {
	    return createForEachStmtNode((ForEachStmt) stmt, parent,visitor);
	}
	return null;
    }


    /**
     * This method creates a Node for a ForEachStmt statement.
     */
    private Node createForEachStmtNode(ForEachStmt stmt, Node parent, JavaVisitor visitor) {
	Node p = new NodeImpl(stmt.getClass().getSimpleName(), parent);
	// Add Iterator ass attribute
	p.addAttribute(JavaAttributesTypes.Iterator.name(), stmt.getIterable().toString());
	// Add the initiliaze values 
	p.addAttribute(JavaAttributesTypes.Initilization.name(), stmt.getVariableDeclarator().getNameAsString());
	p.addAttribute(JavaAttributesTypes.Type.name(), stmt.getVariableDeclarator().getTypeAsString());
	return p;
    }
    
    /**
     * This method creates a Node for a ForStmt statement.
     */
    private Node createForStmt(ForStmt forStmt, Node parent, JavaVisitor visitor) {
	Node forStmtNode = new NodeImpl(forStmt.getClass().getSimpleName(), parent);

	// Comparison
	if (forStmt.getCompare().isPresent()) {
	    forStmtNode.addAttribute(JavaAttributesTypes.Comparison.name(), forStmt.getCompare().get().toString());
	}
	forStmt.removeCompare(); // rm bc visited

	// Initializations
	int initializations = forStmt.getInitialization().size();
	for (int i = 0; i < initializations; i++) {
	    Expression initExpr = forStmt.getInitialization().get(0);
	    forStmtNode.addAttribute(JavaAttributesTypes.Initilization.name(), initExpr.toString());
	    initExpr.removeForced();
	}

	// Updates
	int updates = forStmt.getUpdate().size();
	for (int i = 0; i < updates; i++) {
	    Expression updateExpr = forStmt.getUpdate().get(0);
	    forStmtNode.addAttribute(JavaAttributesTypes.Update.name(), updateExpr.toString());
	    updateExpr.removeForced();
	}
	return forStmtNode;
    }

    /**
     * This method creates a Node for a IfStmt statement.
     */
    private Node createIfStmtNode(IfStmt ifStmt, Node arg, JavaVisitor visitor) {
	Node ifStmtNode = new NodeImpl(ifStmt.getClass().getSimpleName(), arg);
	// Fall through
	Statement thenStmt = ifStmt.getThenStmt();
	Node thenNode = new NodeImpl(JavaNodeTypes.Then.name(), arg);
	thenNode.addAttribute(JavaAttributesTypes.Condition.name(), ifStmt.getCondition().toString());
	thenStmt.accept(visitor, thenNode);
	ifStmt.remove(thenStmt);

	// Block
	if (ifStmt.getElseStmt().isPresent()) {
	    Statement elseStmt = ifStmt.getElseStmt().get();
	    if (elseStmt instanceof IfStmt) {
		createIfStmtNode((IfStmt) elseStmt, arg, visitor);
	    } else {
		Node elseNode = new NodeImpl(JavaNodeTypes.Else.name(), arg);
		elseStmt.accept(visitor, elseNode);
	    }
	}
	return ifStmtNode;
    }

}
