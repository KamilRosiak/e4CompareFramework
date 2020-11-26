package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

//serkan
public class Visitor3 extends VoidVisitorAdapter<Node>{
	@Override
	public void visit(CompilationUnit cu, Node n) {
		
		Node child = new NodeImpl("CompilationUnit");
		n.addChild(child);
		super.visit(cu, child);
		if (cu.getTypes() != null) {
			for (TypeDeclaration<?> tD : cu.getTypes()) {
				n.addAttribute("Type Declaration", tD.toString());
			}
		}
	}
	
	@Override
	public void visit(AnnotationDeclaration u, Node n) {
		n.getParent().addAttribute("AnnotationDeclaration", u.toString());
		if (u.getMembers() != null) {
			for (BodyDeclaration<?> member : u.getMembers()) {
				n.getParent().addAttribute("Member", member.toString());
			}
		}
	}
	
	@Override
	public void visit(AnnotationMemberDeclaration u, Node n) {
		Node child =  new NodeImpl(u.getNameAsString().toString());
		n.addChild(child);
		super.visit(u, child);
		n.addAttribute("Membertyp", u.getType().toString());
	}
	
	@Override
	public void visit(ArrayAccessExpr u, Node n) {
		Node child = new NodeImpl(u.getName().toString());
		n.addChild(child);
		super.visit(u, child);
		n.addAttribute("Expression", u.toString());
	}
	
	@Override
	public void visit(ArrayCreationExpr u, Node n) {
		n.addAttribute("Array Creation Expression Element Type", u.getElementType().toString());
		for (ArrayCreationLevel level : u.getLevels()) {
			n.getParent().addAttribute("Level", level.toString());
		}
	}
	
	@Override
	public void visit(ArrayCreationLevel u, Node n) {
		n.getParent().addAttribute("ArrayCreationLevel", u.toString());
	}
	
	@Override
	public void visit(ArrayInitializerExpr u, Node n) {
		if (u.getValues() != null) {
			for (Expression expr : u.getValues()) {
				n.addAttribute("Expression", expr.toString());
			}
		}
	}
	
	@Override
	public void visit(ArrayType u, Node n) {
		n.getParent().addAttribute("Array Type", u.getComponentType().toString());
	}
	
	@Override
	public void visit(AssertStmt u, Node n) {
		n.getParent().addAttribute("AssertStmt", u.getMessage().toString());
	}
	
	@Override
	public void visit(AssignExpr u, Node n) {
		n.getParent().addAttribute("AssignExpr", u.getValue().toString());
	}
	
	@Override
	public void visit(BinaryExpr u, Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
		n.addAttribute("Expression Left", u.getLeft().toString());
		n.addAttribute("Expression Right", u.getRight().toString());
		super.visit(u, child);
	}
	
	@Override
	public void visit(BlockStmt u, Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
		n.addAttribute("Block Statement", u.toString());
		if (u.getStatements() != null) {
			for (Statement s : u.getStatements()) {
				n.addAttribute("Statement", s.toString());
			}
		}
	}
	
	@Override
	public void visit(BooleanLiteralExpr u, Node n) {
		n.addAttribute("Boolean Literal Expression", u.toString());
	}
	
	@Override
	public void visit(BreakStmt u, Node n) {
		n.addAttribute("Break Statement", u.toString());
	}
	
	@Override
	public void visit(CastExpr u, Node n) {
		n.addAttribute("Typ", u.getType().toString());
		n.addAttribute("Expression", u.getExpression().toString());
	}
	
	@Override
	public void visit(CatchClause u, Node n) {
		n.addAttribute("Parameter", u.getParameter().toString());
		n.addAttribute("Body", u.getBody().toString());
	}
	
	@Override
	public void visit(CharLiteralExpr u, Node n) {
		n.addAttribute("Char Literal Expression", u.toString());	
	}
	
	@Override
	public void visit(ClassExpr u, Node n) {
		n.addAttribute("Class Expression Type", u.getType().toString());	
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
	
	@Override
	public void visit(ConditionalExpr u, Node n) {
		Node child = new NodeImpl(u.toString());
		n.addChild(child);
		super.visit(u, child);
		n.addAttribute("Condition", u.getCondition().toString());
		n.addAttribute("Then", u.getThenExpr().toString());
		n.addAttribute("Else", u.getElseExpr().toString());
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
