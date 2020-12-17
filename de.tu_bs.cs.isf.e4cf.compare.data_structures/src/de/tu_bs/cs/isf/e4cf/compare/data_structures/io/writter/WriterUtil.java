package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.JavaNodeTypes;

import com.github.javaparser.ast.visitor.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.*;
import com.github.javaparser.ast.Modifier.Keyword;

/**
 * 

 *
 */

public class WriterUtil {
	
	public static com.github.javaparser.ast.Node visitWriter(Node n) {
		com.github.javaparser.ast.Node jpNode = null;
		
		// set jpNode as parent to all the nodes, that are generated from the 
		// children
		for (com.github.javaparser.ast.Node jpN : visitWriter(n.getChildren())) {
			jpN.setParentNode(jpNode);
		}		
		
		// find all attributes
		String name = "";
		NodeList<Modifier> modifiers = new NodeList<Modifier>();
		
		// fill name
		for (Attribute a : n.getAttributes()) {
			if (a.getAttributeKey() == JavaNodeTypes.Name.name()) {
				List<String> tmpList = new ArrayList<>(a.getAttributeValues());
				name = tmpList.get(0); // to get the name, which is an attribute with one value
			}
		}
		
		// fill modifiers 
		for (Attribute a : n.getAttributes()) {
			if (a.getAttributeKey() == JavaNodeTypes.Modifier.name()) {
				for (String s : a.getAttributeValues()) {
					Modifier tmpModifier = new Modifier(Modifier.Keyword.valueOf(s.toUpperCase()));
					modifiers.add(tmpModifier);
				}
			}
		}
		
		if (n.getNodeType() == "CompilationUnit") {
			boolean isInterface = false;
			for (Attribute a : n.getAttributes()) {
				if (a.getAttributeKey() == JavaNodeTypes.isInterface.name()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					isInterface = Boolean.parseBoolean(tmpList.get(0));
				}
			}
			jpNode = new ClassOrInterfaceDeclaration(modifiers, isInterface, name);
			
		} else if (n.getNodeType() == "AnnotationDeclaration") {
			jpNode = new AnnotationDeclaration(modifiers, name);
			
		} else if (n.getNodeType() == "AnnotationMemberDeclaration") {
			AnnotationMemberDeclaration tmpAMD = new AnnotationMemberDeclaration();
			tmpAMD.setModifiers(modifiers);
			tmpAMD.setName(name);
			jpNode = tmpAMD;
			
		} else if (n.getNodeType() == "ArrayAccessExpr") {
			jpNode = new ArrayAccessExpr();	
			
		} else if (n.getNodeType() == "ArrayCreationExpr") {
			// need closer look
			
		} else if (n.getNodeType() == "ArrayCreationLevel") {
			jpNode = new ArrayCreationLevel();
			
		} else if (n.getNodeType() == "ArrayInitializerExpr") {
			// need closer look
			
		} else if (n.getNodeType() == "ArrayType") {
			// need closer look
			
		} else if (n.getNodeType() == "AssertStmt") {
			jpNode = new AssertStmt();

		} else if (n.getNodeType() == "Check") {
			/*for (Attribute a : n.getAttributes()) {
				if (a.getAttributeKey() == JavaNodeTypes.Check.name()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					//
				}
			}
			((AssertStmt) parentNode).setCheck())*/
			
		} else if (n.getNodeType() == "Message") {
			// to do
			
		} else if (n.getNodeType() == JavaNodeTypes.Assignment.name()) {
			// AssignExpr
			jpNode = new AssignExpr();
			
		} else if (n.getNodeType() == "BinaryExpr") {
			// need closer look
			
		} else if (n.getNodeType() == "BlockStmt") {
			jpNode = new BlockStmt();
			
		} else if (n.getNodeType() == "BooleanLiteralExpr") {
			// need closer look
			
		} else if (n.getNodeType() == "BreakStmt") {
			String target = "";
			for (Attribute a : n.getAttributes()) {
				if (a.getAttributeKey() == JavaNodeTypes.Target.name()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					target = tmpList.get(0); 
				}
			}
			//SimpleName tmpName = new SimpleName(target);
			jpNode = new BreakStmt(target);
			
		} else if (n.getNodeType() == "CastExpr") {
			String type = "";
			for (Attribute a : n.getAttributes()) {
				if (a.getAttributeKey() == JavaNodeTypes.Type.name()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					type = tmpList.get(0); 
				}
			}
			jpNode = new CastExpr();
			((CastExpr) jpNode).setType(type);
			
		} else if (n.getNodeType() == "CatchClause") {
			jpNode = new CatchClause();
			
		} else if (n.getNodeType() == "CharLiteralExpression") {
			// need closer look
			
		} else if (n.getNodeType() == "ClassExpr") {
			jpNode = new ClassExpr();
			
		} else if (n.getNodeType() == "ConditionalExpr") {
			Expression condition = null;
			Expression then = null;
			Expression else_ = null;
			for (Attribute a : n.getAttributes()) {
				if (a.getAttributeKey() == JavaNodeTypes.Condition.name()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					condition = StaticJavaParser.parseExpression(tmpList.get(0)); 
				}
				
				if (a.getAttributeKey() == JavaNodeTypes.Then.name()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					then = StaticJavaParser.parseExpression(tmpList.get(0)); 
				}
				
				if (a.getAttributeKey() == JavaNodeTypes.Else.name()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					else_ = StaticJavaParser.parseExpression(tmpList.get(0)); 
				}
			}
			jpNode = new ConditionalExpr(condition, then, else_);
			
		} else if (n.getNodeType() == "ConstructorDeclaration") {
			jpNode = new ConstructorDeclaration();
			
		} else if (n.getNodeType() == "ContinueStmt") {
			String target = "";
			for (Attribute a : n.getAttributes()) {
				if (a.getAttributeKey() == JavaNodeTypes.Target.name()) {
					List<String> tmpList = new ArrayList<>(a.getAttributeValues());
					target = tmpList.get(0); 
				}
			}
			jpNode = new ContinueStmt(target);
			
		} else if (n.getNodeType() == "DoStmt") {
			jpNode = new DoStmt();
			
		} else if (n.getNodeType() == "DoubleLiteralExpr") {
			// need closer look
			
		}
		
		
		
		// fill parameters
		for (Node nod0 : n.getChildren()) {
			if (nod0.getNodeType() == JavaNodeTypes.Argument.name()) {
				for (Node nod1 : nod0.getChildren()) {
					if (jpNode != null) {
						visitWriter(nod1).setParentNode(jpNode);
					}
				}
			}
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