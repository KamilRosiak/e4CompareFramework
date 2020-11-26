package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import com.github.javaparser.ast.visitor.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.*;

/**
 * Part of the custom visitor class. This class extends VoidVisitorAdapter
 * (https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/visitor/VoidVisitorAdapter.html).
 * 
 * @author Pascal Blum
 *
 */
public class Visitor0 extends VoidVisitorAdapter<Node> {
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/CompilationUnit.html
	 */
	@Override
	public void visit(CompilationUnit n, Node arg) {
		
		Node child = new NodeImpl("CompilationUnit");
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/MethodDeclaration.html
	 */
	@Override
	public void visit(MethodDeclaration n, Node arg) {
		Node child = new NodeImpl(n.getNameAsString());
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 *  https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/Modifier.html
	 */
	@Override
	public void visit(Modifier n, Node arg) {
		arg.getParent().addAttribute("Modifier", n.toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SimpleName.html
	 */
	@Override
	public void visit(SimpleName n, Node arg) {
		arg.getParent().addAttribute("SimpleName", n.toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/ClassOrInterfaceDeclaration.html
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration n, Node arg) {
		Node child = new NodeImpl(n.getNameAsString());
		arg.addChild(child);
		super.visit(n, child);
		arg.addAttribute("IsInterface", String.valueOf(n.isInterface()));
		int extendedTypeCtr = 0;
		for (ClassOrInterfaceType cit : n.getExtendedTypes()) {
			arg.addAttribute("Extended" + extendedTypeCtr, cit.getNameAsString());
		}
		int implementedTypeCtr = 0;
		for (ClassOrInterfaceType cit : n.getImplementedTypes()) {
			arg.addAttribute("Implemented" + implementedTypeCtr, cit.getNameAsString());
		}
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/AnnotationDeclaration.html
	 */
	@Override // check it
	public void visit(AnnotationDeclaration n, Node arg) {
		arg.getParent().addAttribute("AnnotationDeclaration", n.toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/AnnotationMemberDeclaration.html
	 */
	@Override // check it
	public void visit(AnnotationMemberDeclaration n, Node arg) {
		arg.getParent().addAttribute("AnnotationMemberDeclaration", n.toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ArrayAccessExpr.html
	 */
	@Override // needed?
	public void visit(ArrayAccessExpr n, Node arg) {
		Node child = new NodeImpl(n.getName().toString());
		arg.addChild(child);
		super.visit(n, child);
		arg.addAttribute("Expression", n.toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ArrayCreationExpr.html
	 */
	@Override
	public void visit(ArrayCreationExpr n, Node arg) {
		// 
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/PackageDeclaration.html
	 */
	@Override
	public void visit(PackageDeclaration n, Node arg) {
		arg.addAttribute("Package", n.toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/ImportDeclaration.html
	 */
	@Override
	public void visit(ImportDeclaration n, Node arg) {
		arg.addAttribute("Import", n.toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/FieldDeclaration.html
	 */
	@Override // super call?
	public void visit(FieldDeclaration n, Node arg) {
		arg.addAttribute("Declaration", n.toString());
	}
	
	//////////////////////////////////////////////////
		
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/LambdaExpr.html
	 */
	@Override
	public void visit(LambdaExpr n, Node arg) {
		//arg.addAttribute("Type", n.getClass().toString()); // for everything?
		Node child = new NodeImpl(n.toString());
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/LocalClassDeclarationStmt.html
	 */
	@Override
	public void visit(LocalClassDeclarationStmt n, Node arg) {
		Node child = new NodeImpl(n.toString());
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/LongLiteralExpr.html
	 */
	@Override
	public void visit(LongLiteralExpr n, Node arg) {
		arg.addAttribute("Value", n.getValue().toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MarkerAnnotationExpr.html
	 */
	@Override
	public void visit(MarkerAnnotationExpr n, Node arg) {
		// no attribute needed: it is always '@Override'
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MemberValuePair.html
	 */
	@Override
	public void visit(MemberValuePair n, Node arg) {
		arg.addAttribute("Name", n.getName().asString());
		arg.addAttribute("Value", n.getValue().toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MethodCallExpr.html
	 */
	@Override
	public void visit(MethodCallExpr n, Node arg) {
		arg.addAttribute("Name", n.getName().asString());
		arg.addAttribute("Scope", n.getScope().toString());
		int argCtr = 0;
		for (Expression ex : n.getArguments()) {
			arg.addAttribute("Attribute" + argCtr, ex.toString());
			argCtr++;
		}
		int typeCtr = 0;
		for (Type ty : n.getTypeArguments().get()) {
			arg.addAttribute("TypeArgument" + typeCtr, ty.toString());
			typeCtr++;
		}
		Node child = new NodeImpl(n.toString());
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/MethodReferenceExpr.html
	 */
	@Override
	public void visit(MethodReferenceExpr n, Node arg) {
		arg.addAttribute("Identifier", n.getIdentifier());
		arg.addAttribute("Scope", n.getScope().toString());
		
		int typeCtr = 0;
		for (Type ty : n.getTypeArguments().get()) {
			arg.addAttribute("TypeArgument" + typeCtr, ty.toString());
			typeCtr++;
		}
		
		Node child = new NodeImpl(n.toString());
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/Name.html
	 */
	@Override
	public void visit(Name n, Node arg) {
		arg.addAttribute("Name", n.asString());
	}	
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NameExpr.html
	 */
	@Override
	public void visit(NameExpr n, Node arg) {
		arg.addAttribute("Name", n.getNameAsString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NormalAnnotationExpr.html
	 */
	@Override
	public void visit(NormalAnnotationExpr n, Node arg) {
		arg.addAttribute("Expr", n.toString());
		int pairCtr = 0;
		for (MemberValuePair mvp : n.getPairs()) {
			arg.addAttribute("Pair" + pairCtr, mvp.toString());
			pairCtr++;
		}
		
		Node child = new NodeImpl(n.toString());
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/NullLiteralExpr.html
	 */
	@Override
	public void visit(NullLiteralExpr n, Node arg) {
		// no attribute needed: it is always 'null'
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/ObjectCreationExpr.html
	 */
	@Override
	public void visit(ObjectCreationExpr n, Node arg) {
		arg.addAttribute("Type", n.getType().toString());
		arg.addAttribute("Scope", n.getScope().toString());
		
		int typeCtr = 0;
		for (Type ty : n.getTypeArguments().get()) {
			arg.addAttribute("TypeArgument" + typeCtr, ty.toString());
			typeCtr++;
		}
		
		int argsCtr = 0;
		for (Expression ex : n.getArguments()) {
			arg.addAttribute("Argument" + argsCtr, ex.toString());
			argsCtr++;
		}
		
		Node child = new NodeImpl(n.toString());
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/Parameter.html
	 */
	@Override
	public void visit(Parameter n, Node arg) {
		arg.addAttribute("Name", n.getNameAsString());
		arg.addAttribute("Type", n.getTypeAsString());
		int annoCtr = 0;
		for (AnnotationExpr ae : n.getAnnotations()) {
			arg.addAttribute("Annotation" + annoCtr, ae.toString());
			annoCtr++;
		}
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/type/PrimitiveType.html
	 */
	@Override
	public void visit(PrimitiveType n, Node arg) {
		arg.addAttribute("Type", n.getElementType().toString());
		int annoCtr = 0;
		for (AnnotationExpr ae : n.getAnnotations()) {
			arg.addAttribute("Annotation" + annoCtr, ae.toString());
			annoCtr++;
		}
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/body/ReceiverParameter.html
	 */
	@Override
	public void visit(ReceiverParameter n, Node arg) {
		arg.addAttribute("Name", n.getNameAsString());
		arg.addAttribute("Type", n.getType().toString());
		int annoCtr = 0;
		for (AnnotationExpr ae : n.getAnnotations()) {
			arg.addAttribute("Annotation" + annoCtr, ae.toString());
			annoCtr++;
		}
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/stmt/ReturnStmt.html
	 */
	@Override
	public void visit(ReturnStmt n, Node arg) {
		arg.addAttribute("Expression", n.getExpression().toString());
		Node child = new NodeImpl(n.toString());
		arg.addChild(child);
		super.visit(n, child);
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SingleMemberAnnotationExpr.html
	 */
	@Override
	public void visit(SingleMemberAnnotationExpr n, Node arg) {
		arg.addAttribute("MemberValue", n.getMemberValue().toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/StringLiteralExpr.html
	 */
	@Override
	public void visit(StringLiteralExpr n, Node arg) {
		arg.addAttribute("Value", n.toString());
	}
	
	/**
	 * https://www.javadoc.io/static/com.github.javaparser/javaparser-core/3.17.0/com/github/javaparser/ast/expr/SuperExpr.html
	 */
	@Override
	public void visit(SuperExpr n, Node arg) {
		arg.addAttribute("TypeName", n.getTypeName().toString());
	}
}