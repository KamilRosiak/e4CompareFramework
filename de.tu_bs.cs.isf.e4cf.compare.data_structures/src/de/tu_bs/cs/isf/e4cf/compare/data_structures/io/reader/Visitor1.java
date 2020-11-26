package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;

import com.github.javaparser.ast.expr.*;

import com.github.javaparser.ast.stmt.*;

import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

//Hassan
public class Visitor1 extends VoidVisitorAdapter<Node> {
	/**
	 * https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/ConstructorDeclaration.html

	 */
	@Override
	public void visit(ConstructorDeclaration u, Node n) {
		Node child = new NodeImpl(u.getClass().getSimpleName());
		n.addChild(child);
		super.visit(u, child);
	}
	 /**
	  * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/ContinueStmt.html
	  */
	@Override
	public void visit(ContinueStmt u, Node n) {
		n.addAttribute("Label", u.getLabel().toString());
	}
	
	/**
	 * 
	 */
	@Override
	public void visit(DoStmt u, Node n) {
		
		n.addAttribute("Condition", u.getCondition().toString());
		Node child = new NodeImpl(u.getClass().getSimpleName());
		n.addChild(child);
		super.visit(u, child);
	}
	
	@Override
	public void visit(EmptyStmt u, Node n) {
		//TODO
	}
	
	@Override
	public void visit(DoubleLiteralExpr u, Node n) {
		//TODO
		//n.addAttribute("value", );
	}
	
	@Override
	public void visit(EnclosedExpr u, Node n) {
		n.addAttribute("Expression", n.toString());
	}
	
	
	@Override
	public void visit(EnumConstantDeclaration u, Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
	} 
	
	@Override
	public void visit(EnumDeclaration u, Node n) {
		
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
	}
	
	@Override
	public void visit(ExplicitConstructorInvocationStmt u, Node n) {
		//TODO
	}
	
	
	@Override
	public void visit(ForEachStmt u, Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
	}
	
	@Override
	public void visit(ForStmt u, Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
	}
	
	@Override
	public void visit(IfStmt u, Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
	}
	
	@Override
	public void visit(InitializerDeclaration u,Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
	}
	
	@Override
	public void visit(InstanceOfExpr u, Node n) {
		n.addAttribute("expression", u.getExpression().toString());
		n.addAttribute("type", u.getType().toString());
	}
	
	@Override
	public void visit(IntegerLiteralExpr u, Node n) {
		n.addAttribute("expression", u.toString());
		n.addAttribute("value", u.asNumber().toString());
	}
	
	@Override
	public void visit(IntersectionType u, Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
	}
	
	
	@Override
	public void visit(CompilationUnit cu, Node n) {
		
		Node child = new NodeImpl("CompilationUnit");
		n.addChild(child);
		super.visit(cu, child);
	}
	
	@Override
	public void visit(MethodDeclaration md, Node n) {
		Node child = new NodeImpl(md.getNameAsString());
		n.addChild(child);
		super.visit(md, child);
	}
	
	@Override
	public void visit(Modifier mod, Node n) {
		n.getParent().addAttribute("Modifier", mod.toString());
	}
	
	@Override
	public void visit(SimpleName sn, Node n) {
		n.getParent().addAttribute("SimpleName", sn.toString());
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration ci, Node n) {
		Node child = new NodeImpl(ci.getNameAsString());
		n.addChild(child);
		super.visit(ci, child);
		n.addAttribute("IsInterface", String.valueOf(ci.isInterface()));
		int extendedTypeCtr = 0;
		for (ClassOrInterfaceType cit : ci.getExtendedTypes()) {
			n.addAttribute("Extended" + extendedTypeCtr, cit.getNameAsString());
		}
		int implementedTypeCtr = 0;
		for (ClassOrInterfaceType cit : ci.getImplementedTypes()) {
			n.addAttribute("Implemented" + implementedTypeCtr, cit.getNameAsString());
		}
	}

	@Override // check it
	public void visit(AnnotationDeclaration u, Node n) {
		n.getParent().addAttribute("AnnotationDeclaration", u.toString());
	}
	
	@Override // check it
	public void visit(AnnotationMemberDeclaration u, Node n) {
		n.getParent().addAttribute("AnnotationMemberDeclaration", u.toString());
	}
	
	@Override // needed?
	public void visit(ArrayAccessExpr u, Node n) {
		Node child = new NodeImpl(u.getName().toString());
		n.addChild(child);
		super.visit(u, child);
		n.addAttribute("Expression", u.toString());
	}
	
	@Override
	public void visit(ArrayCreationExpr u, Node n) {
		//
	}
	
	@Override
	public void visit(PackageDeclaration u, Node n) {
		n.addAttribute("Package", u.toString());
	}
	
	@Override
	public void visit(ImportDeclaration u, Node n) {
		n.addAttribute("Import", u.toString());
	}
	
	@Override // super call?
	public void visit(FieldDeclaration u, Node n) {
		n.addAttribute("Declaration", u.toString());
	}
}
