package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.JavaNodeTypes;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.*;

public class WriterUtil {
	
	public static com.github.javaparser.ast.Node visitWriter(Node n) {
		com.github.javaparser.ast.Node jpNode = null;
		
		// set jpNode as parent to all the nodes, that are generated from the 
		// children
		for (com.github.javaparser.ast.Node jpN : visitWriter(n.getChildren())) {
			jpN.setParentNode(jpNode);
		}		
		
		JavaWriterAttributeCollector attributes = new JavaWriterAttributeCollector();
		attributes.collectAttributes(n);
		
		if (n.getNodeType().equals(CompilationUnit.class.getSimpleName())) {
			jpNode = new ClassOrInterfaceDeclaration(attributes.getModifier(), attributes.isInterface(), attributes.getName());
		} else if (n.getNodeType().equals(AnnotationDeclaration.class.getSimpleName())) {
			jpNode = new AnnotationDeclaration(attributes.getModifier(), attributes.getName());
		} else if (n.getNodeType().equals(AnnotationMemberDeclaration.class.getSimpleName())) {
			jpNode = new AnnotationMemberDeclaration(attributes.getModifier(), attributes.getType(), attributes.getName(), attributes.getValue());
		} else if (n.getNodeType().equals(ArrayAccessExpr.class.getSimpleName())) {
			jpNode = new ArrayAccessExpr(); // TODO fill attributes	
		} else if (n.getNodeType().equals(ArrayCreationExpr.class.getSimpleName())) {
			jpNode = new ArrayCreationExpr(attributes.getType()); // TODO needs closer look
		} else if (n.getNodeType().equals(ArrayCreationLevel.class.getSimpleName())) {
			jpNode = new ArrayCreationLevel(); // TODO needs closer look
		} else if (n.getNodeType().equals(ArrayInitializerExpr.class.getSimpleName())) {
			jpNode = new ArrayInitializerExpr();// TODO needs closer look
		} else if (n.getNodeType().equals(ArrayType.class.getSimpleName())) {
			jpNode = new ArrayType(attributes.getType(), attributes.getAnnotation().stream().toArray(AnnotationExpr[]::new));// TODO needs closer look
		} else if (n.getNodeType().equals(AssertStmt.class.getSimpleName())) {
			jpNode = new AssertStmt(attributes.getCheck(), attributes.getMessage());
		} else if (n.getNodeType() == JavaNodeTypes.Assignment.name()) {
			jpNode = new AssignExpr(attributes.getTarget(), attributes.getValue(), AssignExpr.Operator.ASSIGN);
		} else if (n.getNodeType().equals(BinaryExpr.class.getSimpleName())) {
			jpNode = new BinaryExpr();	// TODO fill attributes
		} else if (n.getNodeType().equals(BlockStmt.class.getSimpleName())) {
			jpNode = new BlockStmt();
		} else if (n.getNodeType().equals(BooleanLiteralExpr.class.getSimpleName())) {
			jpNode = new BooleanLiteralExpr(Boolean.valueOf(attributes.getValue().toString()));
		} else if (n.getNodeType().equals(BreakStmt.class.getSimpleName())) {
			jpNode = new BreakStmt();
		} else if (n.getNodeType().equals(CastExpr.class.getSimpleName())) {
			jpNode = new CastExpr().setType(attributes.getType());
		} else if (n.getNodeType().equals(CatchClause.class.getSimpleName())) {
			jpNode = new CatchClause();
		} else if (n.getNodeType().equals(CharLiteralExpr.class.getSimpleName())) {
			jpNode = new CharLiteralExpr(attributes.getValue().toString());
		} else if (n.getNodeType().equals(ClassExpr.class.getSimpleName())) {
			jpNode = new ClassExpr(attributes.getType());
		} else if (n.getNodeType().equals(ConditionalExpr.class.getSimpleName())) {
			jpNode = new ConditionalExpr(attributes.getCondition().getFirst().get(), attributes.getThen(), attributes.getElse());
		} else if (n.getNodeType().equals(ConstructorDeclaration.class.getSimpleName())) {
			jpNode = new ConstructorDeclaration(attributes.getModifier(), attributes.getName()).setAnnotations(attributes.getAnnotation());
		} else if (n.getNodeType().equals(ContinueStmt.class.getSimpleName())) {
			jpNode = new ContinueStmt(attributes.getTarget().toString());
		} else if (n.getNodeType().equals(DoStmt.class.getSimpleName())) {
			jpNode = new DoStmt().setCondition(attributes.getCondition().getFirst().get());
		} else if (n.getNodeType().equals(DoubleLiteralExpr.class.getSimpleName())) {
			jpNode = new DoubleLiteralExpr(attributes.getValue().toString());
		}
		
		
		return jpNode;
	}
	
	public static NodeList<com.github.javaparser.ast.Node> visitWriter(List<Node> n) {
		NodeList<com.github.javaparser.ast.Node> nodeList = new NodeList<>();
		
		// go through all children and call visitWriter for every Node, 
		// 		then add the returned nodes to nodeList
		for (Node node : n) {
			nodeList.add(visitWriter(node));
		}
		
		return nodeList;
	}
	
	
}