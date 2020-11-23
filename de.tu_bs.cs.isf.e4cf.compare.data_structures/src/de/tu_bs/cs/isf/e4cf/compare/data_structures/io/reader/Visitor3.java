package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

//serkan
public class Visitor3 extends VoidVisitorAdapter<Node>{
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
