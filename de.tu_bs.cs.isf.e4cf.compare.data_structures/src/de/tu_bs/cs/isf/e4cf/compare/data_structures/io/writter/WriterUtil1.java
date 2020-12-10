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
import com.github.javaparser.ast.Modifier.Keyword;

/**
 * 
 * @author Pascal Blum
 *
 */

public class WriterUtil {
	
	public static com.github.javaparser.ast.Node visitWriter(Node n) {
		com.github.javaparser.ast.Node jpNode = null;
		
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
			boolean isInterface;
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
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
		} else if (n.getNodeType() == ) {
			
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
		
		// set jpNode as parent to all the nodes, that are generated from the 
		// children
		for (com.github.javaparser.ast.Node jpN : visitWriter(n.getChildren())) {
			jpN.setParentNode(jpNode);
		}
		
		return jpNode;
	}
	
	public static NodeList<com.github.javaparser.ast.Node> visitWriter(List<Node> n) {
		NodeList<com.github.javaparser.ast.Node> nodeList = new NodeList<>();
		
		// go through all children and call visitWriter for every Node, 
		// 		then add the returned nodes to nodeList
		
		return nodeList;
	}
	
	
}