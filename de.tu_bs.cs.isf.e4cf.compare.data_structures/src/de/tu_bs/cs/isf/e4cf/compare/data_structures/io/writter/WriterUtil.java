package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.JavaNodeTypes;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.*;

public class WriterUtil {
	public static com.github.javaparser.ast.Node visitWriter(Node n, com.github.javaparser.ast.Node p) throws UnsupportedOperationException {
		com.github.javaparser.ast.Node jpNode = null;

		JavaWriterAttributeCollector attributes = new JavaWriterAttributeCollector();
		attributes.collectAttributes(n);

		if (n.getNodeType().equals(JavaWriter.NODE_TYPE_TREE)) {
			jpNode = new CompilationUnit();
		} else if (n.getNodeType().equals(CompilationUnit.class.getSimpleName())) {
			ClassOrInterfaceDeclaration coid = new ClassOrInterfaceDeclaration();
			coid.setName(attributes.getName());
			coid.setModifiers(attributes.getModifier());
			coid.addExtendedType(attributes.getSuperclass());
			coid.setImplementedTypes(attributes.getInterface());
			coid.setInterface(attributes.isInterface());
			
			if (p instanceof CompilationUnit) {
				CompilationUnit cu = (CompilationUnit) p;
				cu.addType(coid);
				cu.setPackageDeclaration(attributes.getPackage());
			} else {
				throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName());
			}
			
		} else if (n.getNodeType().equals(AnnotationDeclaration.class.getSimpleName())) {
			jpNode = new AnnotationDeclaration(attributes.getModifier(), attributes.getName());
		} else if (n.getNodeType().equals(AnnotationMemberDeclaration.class.getSimpleName())) {
			jpNode = new AnnotationMemberDeclaration(attributes.getModifier(), attributes.getType(),
					attributes.getName(), attributes.getValue());
		} else if (n.getNodeType().equals(ArrayAccessExpr.class.getSimpleName())) {
			jpNode = new ArrayAccessExpr(); // TODO fill attributes
		} else if (n.getNodeType().equals(ArrayCreationExpr.class.getSimpleName())) {
			jpNode = new ArrayCreationExpr(attributes.getType()); // TODO needs closer look
		} else if (n.getNodeType().equals(ArrayCreationLevel.class.getSimpleName())) {
			jpNode = new ArrayCreationLevel(); // TODO needs closer look
		} else if (n.getNodeType().equals(ArrayInitializerExpr.class.getSimpleName())) {
			jpNode = new ArrayInitializerExpr();// TODO needs closer look
		} else if (n.getNodeType().equals(ArrayType.class.getSimpleName())) {
			jpNode = new ArrayType(attributes.getType(),
					attributes.getAnnotation().stream().toArray(AnnotationExpr[]::new));// TODO needs closer look
		} else if (n.getNodeType().equals(AssertStmt.class.getSimpleName())) {
			jpNode = new AssertStmt(attributes.getCheck(), attributes.getMessage());
		} else if (n.getNodeType() == JavaNodeTypes.Assignment.name()) {
			jpNode = new AssignExpr(attributes.getTarget(), attributes.getValue(), AssignExpr.Operator.ASSIGN);
		} else if (n.getNodeType().equals(BinaryExpr.class.getSimpleName())) {
			jpNode = new BinaryExpr(); // TODO fill attributes
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
			jpNode = new ConditionalExpr(attributes.getCondition().getFirst().get(), attributes.getThen(),
					attributes.getElse());
		} else if (n.getNodeType().equals(ConstructorDeclaration.class.getSimpleName())) {
			jpNode = new ConstructorDeclaration(attributes.getModifier(), attributes.getName())
					.setAnnotations(attributes.getAnnotation());
		} else if (n.getNodeType().equals(ContinueStmt.class.getSimpleName())) {
			jpNode = new ContinueStmt(attributes.getTarget().toString());
		} else if (n.getNodeType().equals(DoStmt.class.getSimpleName())) {
			jpNode = new DoStmt().setCondition(attributes.getCondition().getFirst().get());
		} else if (n.getNodeType().equals(DoubleLiteralExpr.class.getSimpleName())) {
			jpNode = new DoubleLiteralExpr(attributes.getValue().toString());
		} else if (n.getNodeType().equals(EmptyStmt.class.getSimpleName())) {
			jpNode = new EmptyStmt();
		} else if (n.getNodeType().equals(EnclosedExpr.class.getSimpleName())) {
			jpNode = new EnclosedExpr();
		} else if (n.getNodeType().equals(EnumConstantDeclaration.class.getSimpleName())) {
			jpNode = new EnumConstantDeclaration();
		} else if (n.getNodeType().equals(EnumDeclaration.class.getSimpleName())) {
			jpNode = new EnumDeclaration();
		} else if (n.getNodeType().equals(ExplicitConstructorInvocationStmt.class.getSimpleName())) {
			jpNode = new ExplicitConstructorInvocationStmt();
		} else if (n.getNodeType().equals(ExpressionStmt.class.getSimpleName())) {
			jpNode = new ExpressionStmt();
		} else if (n.getNodeType().equals(FieldAccessExpr.class.getSimpleName())) {
			jpNode = new FieldAccessExpr();
		} else if (n.getNodeType().equals(FieldDeclaration.class.getSimpleName())) {
			jpNode = new FieldDeclaration();
		} else if (n.getNodeType().equals(ForEachStmt.class.getSimpleName())) {
			jpNode = new ForEachStmt();
		} else if (n.getNodeType().equals(ForStmt.class.getSimpleName())) {
			jpNode = new ForStmt();
		} else if (n.getNodeType().equals(IfStmt.class.getSimpleName())) {
			jpNode = new IfStmt();
		} else if (n.getNodeType().equals(JavaNodeTypes.Import.name())) {
			if (p != null) {
				Optional<CompilationUnit> cuOpt = p.findCompilationUnit();
				if (cuOpt.isPresent() && !attributes.getName().isEmpty()) {
					cuOpt.get().addImport(new ImportDeclaration(attributes.getName(), attributes.isStatic(),
							attributes.isAsteriks()));
				}
			}
		} else if (n.getNodeType().equals(InitializerDeclaration.class.getSimpleName())) {
			jpNode = new InitializerDeclaration();
		} else if (n.getNodeType().equals(InstanceOfExpr.class.getSimpleName())) {
			jpNode = new InstanceOfExpr();
		} else if (n.getNodeType().equals(IntegerLiteralExpr.class.getSimpleName())) {
			jpNode = new IntegerLiteralExpr();
		} else if (n.getNodeType().equals(IntersectionType.class.getSimpleName())) {
			// TODO fill arguments jpNode = new IntersectionType();
		} else if (n.getNodeType().equals(LabeledStmt.class.getSimpleName())) {
			jpNode = new LabeledStmt();
		} else if (n.getNodeType().equals(LambdaExpr.class.getSimpleName())) {
			jpNode = new LambdaExpr();
		} else if (n.getNodeType().equals(LocalClassDeclarationStmt.class.getSimpleName())) {
			jpNode = new LocalClassDeclarationStmt();
		} else if (n.getNodeType().equals(LongLiteralExpr.class.getSimpleName())) {
			jpNode = new LongLiteralExpr();
		} else if (n.getNodeType().equals(MarkerAnnotationExpr.class.getSimpleName())) {
			jpNode = new MarkerAnnotationExpr();
		} else if (n.getNodeType().equals(MemberValuePair.class.getSimpleName())) {
			jpNode = new MemberValuePair();
		} else if (n.getNodeType().equals(MethodCallExpr.class.getSimpleName())) {
			jpNode = new MethodCallExpr();
		} else if (n.getNodeType().equals(MethodDeclaration.class.getSimpleName())) {
			jpNode = new MethodDeclaration();
		} else if (n.getNodeType().equals(MethodReferenceExpr.class.getSimpleName())) {
			jpNode = new MethodReferenceExpr();
		} else if (n.getNodeType().equals(NameExpr.class.getSimpleName())) {
			jpNode = new NameExpr();
		} else if (n.getNodeType().equals(NormalAnnotationExpr.class.getSimpleName())) {
			jpNode = new NormalAnnotationExpr();
		} else if (n.getNodeType().equals(NullLiteralExpr.class.getSimpleName())) {
			jpNode = new NullLiteralExpr();
		} else if (n.getNodeType().equals(ObjectCreationExpr.class.getSimpleName())) {
			jpNode = new ObjectCreationExpr();
		} else if (n.getNodeType().equals(Parameter.class.getSimpleName())) {
			jpNode = new Parameter();
		} else if (n.getNodeType().equals(PrimitiveType.class.getSimpleName())) {
			jpNode = new PrimitiveType();
		} else if (n.getNodeType().equals(ReceiverParameter.class.getSimpleName())) {
			jpNode = new ReceiverParameter();
		} else if (n.getNodeType().equals(ReturnStmt.class.getSimpleName())) {
			jpNode = new ReturnStmt();
		} else if (n.getNodeType().equals(SingleMemberAnnotationExpr.class.getSimpleName())) {
			jpNode = new SingleMemberAnnotationExpr();
		} else if (n.getNodeType().equals(StringLiteralExpr.class.getSimpleName())) {
			jpNode = new StringLiteralExpr();
		} else if (n.getNodeType().equals(SuperExpr.class.getSimpleName())) {
			jpNode = new SuperExpr();
		} else if (n.getNodeType().equals(SwitchEntry.class.getSimpleName())) {
			jpNode = new SwitchEntry();
		} else if (n.getNodeType().equals(SwitchExpr.class.getSimpleName())) {
			jpNode = new SwitchExpr();
		} else if (n.getNodeType().equals(SwitchStmt.class.getSimpleName())) {
			jpNode = new SwitchStmt();
		} else if (n.getNodeType().equals(SynchronizedStmt.class.getSimpleName())) {
			jpNode = new SynchronizedStmt();
		} else if (n.getNodeType().equals(TextBlockLiteralExpr.class.getSimpleName())) {
			jpNode = new TextBlockLiteralExpr();
		} else if (n.getNodeType().equals(ThisExpr.class.getSimpleName())) {
			jpNode = new ThisExpr();
		} else if (n.getNodeType().equals(ThrowStmt.class.getSimpleName())) {
			jpNode = new ThrowStmt();
		} else if (n.getNodeType().equals(TryStmt.class.getSimpleName())) {
			jpNode = new TryStmt();
		} else if (n.getNodeType().equals(TypeExpr.class.getSimpleName())) {
			jpNode = new TypeExpr();
		} else if (n.getNodeType().equals(TypeParameter.class.getSimpleName())) {
			jpNode = new TypeParameter();
		} else if (n.getNodeType().equals(UnaryExpr.class.getSimpleName())) {
			jpNode = new UnaryExpr();
		} else if (n.getNodeType().equals(UnionType.class.getSimpleName())) {
			jpNode = new UnionType();
		} else if (n.getNodeType().equals(UnknownType.class.getSimpleName())) {
			jpNode = new UnknownType();
		} else if (n.getNodeType().equals(UnparsableStmt.class.getSimpleName())) {
			jpNode = new UnparsableStmt();
		} else if (n.getNodeType().equals(VariableDeclarationExpr.class.getSimpleName())) {
			jpNode = new VariableDeclarationExpr();
		} else if (n.getNodeType().equals(VarType.class.getSimpleName())) {
			jpNode = new VarType();
		} else if (n.getNodeType().equals(VoidType.class.getSimpleName())) {
			jpNode = new VoidType();
		} else if (n.getNodeType().equals(WhileStmt.class.getSimpleName())) {
			jpNode = new WhileStmt();
		} else if (n.getNodeType().equals(WildcardType.class.getSimpleName())) {
			jpNode = new WildcardType();
		} else if (n.getNodeType().equals(YieldStmt.class.getSimpleName())) {
			jpNode = new YieldStmt();
		}

		if (p != null && jpNode != null) {
			jpNode.setParentNode(p);
		}

		// set jpNode as parent to all the nodes, that are generated from the
		// children
		for (Node nChild : n.getChildren()) {
			com.github.javaparser.ast.Node jpChild = visitWriter(nChild, Optional.ofNullable(jpNode).orElse(p));
		}

		return jpNode;
	}
}