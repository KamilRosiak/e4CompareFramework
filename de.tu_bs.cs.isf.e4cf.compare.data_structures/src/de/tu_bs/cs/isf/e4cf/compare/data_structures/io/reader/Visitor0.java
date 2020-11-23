package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import com.github.javaparser.ast.visitor.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.*;

public class Visitor0 extends VoidVisitorAdapter<Node> {
	@Override
	public void visit(CompilationUnit cu, Node n) {
<<<<<<< HEAD
		
		Node child = new NodeImpl("CompilationUnit");
		n.addChild(child);
=======
		Node child = new NodeImpl("CompilationUnit");
		child.setParent(n);
>>>>>>> branch 'feature/reader' of https://github.com/Hassan-smaoui/e4CompareFramework-team3.git
		super.visit(cu, child);
	}
	
	@Override
	public void visit(MethodDeclaration md, Node n) {
		Node child = new NodeImpl(md.getNameAsString());
<<<<<<< HEAD
		n.addChild(child);
=======
		child.setParent(n);
>>>>>>> branch 'feature/reader' of https://github.com/Hassan-smaoui/e4CompareFramework-team3.git
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
<<<<<<< HEAD
		n.addChild(child);
=======
		child.setParent(n);
>>>>>>> branch 'feature/reader' of https://github.com/Hassan-smaoui/e4CompareFramework-team3.git
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
<<<<<<< HEAD
		n.addChild(child);
=======
		child.setParent(n);
>>>>>>> branch 'feature/reader' of https://github.com/Hassan-smaoui/e4CompareFramework-team3.git
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
	
	//////////////////////////////////////////////////
		
}
