
package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.NoSuchElementException;
import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.JavaNodeTypes;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;

/**
 * 
 * @author Serkan Acar
 * @author Pascal Blum
 * @author Paulo Haas
 * @author Hassan Smaoui
 *
 */
public class JavaWriterUtil {
	/**
	 * Converts a framework node and it's children to a JavaParser node
	 * 
	 * @param n Framework node
	 * @param p is the parent node of n's equivalent JavaParserNode. This can be
	 *          null.
	 * @return n as JavaParser node
	 * @throws UnsupportedOperationException if an attribute, node type etc. is not
	 *                                       supported.
	 */
	public static /* @ nullable @ */ com.github.javaparser.ast.Node visitWriter(Node n,
			/* @ nullable @ */ com.github.javaparser.ast.Node p) throws UnsupportedOperationException {
		com.github.javaparser.ast.Node jpNode = null;

		JavaWriterAttributeCollector attributes = new JavaWriterAttributeCollector();
		attributes.collectAttributes(n);

		if (n.getNodeType().equals(JavaWriter.NODE_TYPE_TREE)) {
			CompilationUnit obj = new CompilationUnit();
			jpNode = obj;
		} else if (n.getNodeType().equals(CompilationUnit.class.getSimpleName())) {
			ClassOrInterfaceDeclaration coid = new ClassOrInterfaceDeclaration();
			coid.setName(attributes.getName());
			coid.setModifiers(attributes.getModifier());

			if (!attributes.getSuperclass().isEmpty()) {
				coid.addExtendedType(attributes.getSuperclass());
			}
			coid.setImplementedTypes(attributes.getInterface());
			coid.setInterface(attributes.isInterface());

			if (p instanceof CompilationUnit) {
				CompilationUnit cu = (CompilationUnit) p;
				cu.addType(coid);

				if (!attributes.getPackage().isEmpty()) {
					cu.setPackageDeclaration(attributes.getPackage());
				}
			} else {
				throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName()
						+ ". Expected: " + CompilationUnit.class.getSimpleName());
			}
			jpNode = coid;
		} else if (n.getNodeType().equals(AnnotationDeclaration.class.getSimpleName())) {
			AnnotationDeclaration obj = new AnnotationDeclaration();
			obj.setModifiers(attributes.getModifier());
			obj.setName(attributes.getName());
			jpNode = obj;
		} else if (n.getNodeType().equals(AnnotationMemberDeclaration.class.getSimpleName())) {
			AnnotationMemberDeclaration obj = new AnnotationMemberDeclaration();
			obj.setModifiers(attributes.getModifier());
			obj.setType(attributes.getType());
			obj.setName(attributes.getName());
			obj.setDefaultValue(attributes.getValue());
			jpNode = obj;
		} else if (n.getNodeType().startsWith(JavaNodeTypes.Argument.name())) {
			if (p == null || attributes.getChildren() > 0) {
				// Do nothing, e.g. parent of concrete arg was arg
			} else if (attributes.getType() != null && attributes.getName() != null) {
				Parameter param = new Parameter(attributes.getType(), attributes.getName());
				param.setModifiers(attributes.getModifier());
				((NodeWithParameters) p).addParameter(param);
			} else if (!attributes.getName().isEmpty()) {
				((NodeWithArguments) p).addArgument(attributes.getName());
			} else if (attributes.getValue() != null) {
				((NodeWithArguments) p).addArgument(attributes.getValue());
			} else if (attributes.getChildren() == 0) {
				for (Node nChild : n.getChildren()) {
					com.github.javaparser.ast.Node jpChild = visitWriter(nChild, null);

					if (p instanceof NodeWithArguments && jpChild instanceof Expression) {
						((NodeWithArguments) p).addArgument((Expression) jpChild);
					} else {
						throw new UnsupportedOperationException("p is " + p.getClass().getSimpleName()
								+ " and jpChild is " + jpChild.getClass().getSimpleName());
					}
				}
			}
		} else if (n.getNodeType().equals(ArrayAccessExpr.class.getSimpleName())) {
			ArrayAccessExpr obj = new ArrayAccessExpr();
			obj.setIndex(attributes.getValue());
			obj.setName(StaticJavaParser.parseExpression(attributes.getName()));

			jpNode = obj;
		} else if (n.getNodeType().equals(ArrayCreationExpr.class.getSimpleName())) {
			ArrayCreationExpr obj = new ArrayCreationExpr();
			obj.setElementType(attributes.getType());
			jpNode = obj;
		} else if (n.getNodeType().equals(ArrayCreationLevel.class.getSimpleName())) {
			ArrayCreationLevel obj = new ArrayCreationLevel();
			jpNode = obj;
		} else if (n.getNodeType().equals(ArrayInitializerExpr.class.getSimpleName())) {
			ArrayInitializerExpr obj = new ArrayInitializerExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(ArrayType.class.getSimpleName())) {
			ArrayType obj = new ArrayType(attributes.getType(),
					attributes.getAnnotation().stream().toArray(AnnotationExpr[]::new));// TODO needs closer lookjpNode
																						// = obj;
		} else if (n.getNodeType().equals(AssertStmt.class.getSimpleName())) {
			AssertStmt obj = new AssertStmt();
			obj.setCheck(attributes.getCheck());
			obj.setMessage(attributes.getMessage());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(JavaNodeTypes.Assignment.name())) {
			AssignExpr obj = new AssignExpr();
			obj.setTarget(attributes.getTarget());
			obj.setValue(attributes.getValue());
			obj.setOperator(AssignExpr.Operator.valueOf(attributes.getOperator()));
			
			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}
			
			jpNode = obj;
		} else if (n.getNodeType().equals(BinaryExpr.class.getSimpleName())) {
			BinaryExpr obj = new BinaryExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(BlockStmt.class.getSimpleName())
				|| n.getNodeType().equals(JavaNodeTypes.Body.name())) {
			BlockStmt obj = new BlockStmt();
			if (p instanceof NodeWithOptionalBlockStmt) {
				((NodeWithOptionalBlockStmt) p).setBody(obj);
			} else if (p instanceof NodeWithBlockStmt) {
				((NodeWithBlockStmt) p).setBody(obj);
			} else if (p instanceof NodeWithBody) {
				((NodeWithBody) p).setBody(obj);
			} else if (p instanceof TryStmt) {
				((TryStmt) p).setTryBlock(obj);
			} else if (p instanceof BlockStmt) {
				obj = (BlockStmt) p;
			} else if (p instanceof IfStmt) {
				((IfStmt) p).setThenStmt(obj);
			}
			jpNode = obj;
		} else if (n.getNodeType().equals(BooleanLiteralExpr.class.getSimpleName())) {
			BooleanLiteralExpr obj = new BooleanLiteralExpr();
			obj.setValue(Boolean.valueOf(attributes.getValue().toString()));
			jpNode = obj;
		} else if (n.getNodeType().equals(BreakStmt.class.getSimpleName())
				|| n.getNodeType().equals(JavaNodeTypes.Break.name())) {
			BreakStmt obj = new BreakStmt();
			if (attributes.getTarget() != null) {
				obj.setLabel(new SimpleName(attributes.getTarget().toString()));
			} else {
				obj.removeLabel();
			}

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(CastExpr.class.getSimpleName())) {
			CastExpr obj = new CastExpr();
			obj.setType(attributes.getType());
			jpNode = obj;
		} else if (n.getNodeType().equals(CatchClause.class.getSimpleName())) {
			CatchClause obj = new CatchClause();

			if (p instanceof TryStmt) {
				NodeList<CatchClause> clauses = ((TryStmt) p).getCatchClauses();
				clauses.add(obj);
				((TryStmt) p).setCatchClauses(clauses);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(CharLiteralExpr.class.getSimpleName())) {
			CharLiteralExpr obj = new CharLiteralExpr();
			obj.setValue(attributes.getValue().toString());
			jpNode = obj;
		} else if (n.getNodeType().equals(JavaNodeTypes.Class.name())) {
			ClassOrInterfaceDeclaration coid = new ClassOrInterfaceDeclaration();
			coid.setName(attributes.getName());
			coid.setModifiers(attributes.getModifier());

			if (!attributes.getSuperclass().isEmpty()) {
				coid.addExtendedType(attributes.getSuperclass());
			}
			coid.setImplementedTypes(attributes.getInterface());
			coid.setInterface(attributes.isInterface());

			if (p instanceof NodeWithMembers) {
				((NodeWithMembers) p).addMember(coid);
			}
					
			jpNode = coid;
		} else if (n.getNodeType().equals(ClassExpr.class.getSimpleName())) {
			ClassExpr obj = new ClassExpr();
			obj.setType(attributes.getType());
			jpNode = obj;
		} else if (n.getNodeType().equals(ConditionalExpr.class.getSimpleName())) {
			ConditionalExpr obj = new ConditionalExpr();
			obj.setCondition(attributes.getCondition().getFirst().get());
			obj.setThenExpr(attributes.getThen());
			obj.setElseExpr(attributes.getElse());
			
			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}
			
			jpNode = obj;
		} else if (n.getNodeType().equals(ConstructorDeclaration.class.getSimpleName())) {
			ConstructorDeclaration obj = new ConstructorDeclaration();
			obj.setModifiers(attributes.getModifier());
			obj.setName(attributes.getName());
			obj.setAnnotations(attributes.getAnnotation());
			jpNode = obj;
		} else if (n.getNodeType().equals(ContinueStmt.class.getSimpleName())) {
			ContinueStmt obj = new ContinueStmt();
			obj.setLabel(attributes.getTarget().toString());
			jpNode = obj;
		} else if (n.getNodeType().equals(DoStmt.class.getSimpleName())) {
			DoStmt obj = new DoStmt();
			obj.setCondition(attributes.getCondition().getFirst().get());
			jpNode = obj;
		} else if (n.getNodeType().equals(DoubleLiteralExpr.class.getSimpleName())) {
			DoubleLiteralExpr obj = new DoubleLiteralExpr();
			obj.setValue(attributes.getValue().toString());
			jpNode = obj;
		} else if (n.getNodeType().equals(JavaNodeTypes.Else.name())) {
			BlockStmt elseStmt = new BlockStmt();
			IfStmt ifStmt = (IfStmt) p;
			while (ifStmt.hasElseBranch()) {
				ifStmt = (IfStmt) ifStmt.getElseStmt().get();
			}
			IfStmt parentIfStmt = (IfStmt) ifStmt.getParentNode().get();
			parentIfStmt.removeElseStmt();
			parentIfStmt.setElseStmt(elseStmt);
			jpNode = elseStmt;
		} else if (n.getNodeType().equals(EmptyStmt.class.getSimpleName())) {
			EmptyStmt obj = new EmptyStmt();
			jpNode = obj;
		} else if (n.getNodeType().equals(EnclosedExpr.class.getSimpleName())) {
			EnclosedExpr obj = new EnclosedExpr();
			
			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}
			
			jpNode = obj;
		} else if (n.getNodeType().equals(EnumConstantDeclaration.class.getSimpleName())) {
			EnumConstantDeclaration obj = new EnumConstantDeclaration();
			jpNode = obj;
		} else if (n.getNodeType().equals(EnumDeclaration.class.getSimpleName())) {
			EnumDeclaration obj = new EnumDeclaration();
			jpNode = obj;
		} else if (n.getNodeType().equals(ExplicitConstructorInvocationStmt.class.getSimpleName())) {
			ExplicitConstructorInvocationStmt obj = new ExplicitConstructorInvocationStmt();
			jpNode = obj;
		} else if (n.getNodeType().equals(ExpressionStmt.class.getSimpleName())) {
			ExpressionStmt obj = new ExpressionStmt();
			jpNode = obj;
		} else if (n.getNodeType().equals(FieldAccessExpr.class.getSimpleName())) {
			FieldAccessExpr obj = new FieldAccessExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(FieldDeclaration.class.getSimpleName())) {
			FieldDeclaration fd = new FieldDeclaration();
			fd.setModifiers(attributes.getModifier());

			VariableDeclarator vd = new VariableDeclarator();
			vd.setType(attributes.getType());
			vd.setName(attributes.getName());
			if (!attributes.getInitilization().isEmpty()) {
				vd.setInitializer(attributes.getInitilization().getFirst().get());
			}

			fd.addVariable(vd);

			if (p instanceof TypeDeclaration) {
				TypeDeclaration nwm = (TypeDeclaration) p;
				nwm.addMember(fd);
			} else {
				throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName()
						+ ". Expected: " + TypeDeclaration.class.getSimpleName());
			}
			jpNode = fd;
		} else if (n.getNodeType().equals(ForEachStmt.class.getSimpleName())) {
			ForEachStmt obj = new ForEachStmt();
			obj.setIterable(attributes.getIterator());
			obj.setVariable(new VariableDeclarationExpr(attributes.getType(),
					attributes.getInitilization().getFirst().get().toString()));

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(ForStmt.class.getSimpleName())) {
			ForStmt obj = new ForStmt();
			obj.setInitialization(attributes.getInitilization());
			obj.setCompare(attributes.getComparison());
			obj.setUpdate(attributes.getUpdate());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(IfStmt.class.getSimpleName())) {
			IfStmt obj = new IfStmt();
			jpNode = obj;

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}
		} else if (n.getNodeType().equals(JavaNodeTypes.Import.name())) {
			if (p != null) {
				Optional<CompilationUnit> cuOpt = p.findCompilationUnit();
				if (cuOpt.isPresent() && !attributes.getName().isEmpty()) {
					cuOpt.get().addImport(new ImportDeclaration(attributes.getName(), attributes.isStatic(),
							attributes.isAsteriks()));
				}
			}
		} else if (n.getNodeType().equals(InitializerDeclaration.class.getSimpleName())) {
			InitializerDeclaration obj = new InitializerDeclaration();
			jpNode = obj;
		} else if (n.getNodeType().equals(InstanceOfExpr.class.getSimpleName())) {
			InstanceOfExpr obj = new InstanceOfExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(IntegerLiteralExpr.class.getSimpleName())) {
			IntegerLiteralExpr obj = new IntegerLiteralExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(IntersectionType.class.getSimpleName())) {
			/*
			 * TODO fill arguments IntersectionType obj = new IntersectionType(); jpNode =
			 * obj;
			 */
		} else if (n.getNodeType().equals(LabeledStmt.class.getSimpleName())) {
			LabeledStmt obj = new LabeledStmt();
			jpNode = obj;
		} else if (n.getNodeType().equals(LambdaExpr.class.getSimpleName())) {
			LambdaExpr obj = new LambdaExpr();
			
			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}
			
			jpNode = obj;
		} else if (n.getNodeType().equals(LocalClassDeclarationStmt.class.getSimpleName())) {
			LocalClassDeclarationStmt obj = new LocalClassDeclarationStmt();
			jpNode = obj;
		} else if (n.getNodeType().equals(LongLiteralExpr.class.getSimpleName())) {
			LongLiteralExpr obj = new LongLiteralExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(MarkerAnnotationExpr.class.getSimpleName())) {
			MarkerAnnotationExpr obj = new MarkerAnnotationExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(MemberValuePair.class.getSimpleName())) {
			MemberValuePair obj = new MemberValuePair();
			obj.setName(attributes.getKey());
			obj.setValue(attributes.getValue());
			
			if (p instanceof NormalAnnotationExpr) {
				NodeList<MemberValuePair> paris = ((NormalAnnotationExpr) p).getPairs();
				paris.add(obj);
				((NormalAnnotationExpr) p).setPairs(paris);
			}
			
			jpNode = obj;
		} else if (n.getNodeType().equals(MethodCallExpr.class.getSimpleName())) {
			MethodCallExpr obj = new MethodCallExpr();
			obj.setScope(attributes.getScope());
			obj.setName(attributes.getName());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			} else if (p instanceof LambdaExpr) {
				((LambdaExpr) p).setBody(new ExpressionStmt(obj));
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(MethodDeclaration.class.getSimpleName())) {
			MethodDeclaration obj = new MethodDeclaration();
			obj.setThrownExceptions(attributes.getThrows());
			obj.setName(attributes.getName());
			obj.setModifiers(attributes.getModifier());
			if (attributes.getReturnType() != null) {
				obj.setType(attributes.getReturnType());
			} else if (attributes.getType() != null) {
				obj.setType(attributes.getType());
			}
			if (attributes.getAnnotation().isNonEmpty()) {
				obj.setAnnotations(attributes.getAnnotation());
			}

			if (p instanceof NodeWithMembers) {
				((NodeWithMembers) p).addMember(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(MethodReferenceExpr.class.getSimpleName())) {
			MethodReferenceExpr obj = new MethodReferenceExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(NameExpr.class.getSimpleName())) {
			NameExpr obj = new NameExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(NormalAnnotationExpr.class.getSimpleName())) {
			NormalAnnotationExpr obj = new NormalAnnotationExpr();
			obj.setName(attributes.getName());
			
			if(p instanceof NodeWithAnnotations) {
				((NodeWithAnnotations) p).addAnnotation(obj);
			}
			
			jpNode = obj;
		} else if (n.getNodeType().equals(NullLiteralExpr.class.getSimpleName())) {
			NullLiteralExpr obj = new NullLiteralExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(ObjectCreationExpr.class.getSimpleName())) {
			ObjectCreationExpr obj = new ObjectCreationExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(Parameter.class.getSimpleName())) {
			Parameter obj = new Parameter();
			if (attributes.getType() != null) {
				obj.setType(attributes.getType());
			} else {
				obj.setType(new UnknownType());
			}
			obj.setName(attributes.getName());

			if (p instanceof NodeWithParameters) {
				((NodeWithParameters) p).addParameter(obj);
			} else if (p instanceof CatchClause) {
				((CatchClause) p).setParameter(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(PrimitiveType.class.getSimpleName())) {
			PrimitiveType obj = new PrimitiveType();
			jpNode = obj;
		} else if (n.getNodeType().equals(ReceiverParameter.class.getSimpleName())) {
			ReceiverParameter obj = new ReceiverParameter();
			jpNode = obj;
		} else if (n.getNodeType().equals(ReturnStmt.class.getSimpleName())) {
			ReturnStmt obj = new ReturnStmt(attributes.getValue());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			} else {
				throw new UnsupportedOperationException();
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(SingleMemberAnnotationExpr.class.getSimpleName())) {
			SingleMemberAnnotationExpr obj = new SingleMemberAnnotationExpr();
			obj.setName(attributes.getName());
			obj.setMemberValue(attributes.getValue());
			
			if (p instanceof NodeWithAnnotations) {
				((NodeWithAnnotations) p).addAnnotation(obj);
			}
			
			jpNode = obj;
		} else if (n.getNodeType().equals(StringLiteralExpr.class.getSimpleName())) {
			StringLiteralExpr obj = new StringLiteralExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(SuperExpr.class.getSimpleName())) {
			SuperExpr obj = new SuperExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(SwitchEntry.class.getSimpleName())) {
			SwitchEntry obj = new SwitchEntry();
			obj.setType(SwitchEntry.Type.valueOf(attributes.getType().toString()));
			if (!attributes.isDefault()) {
				obj.setLabels(attributes.getCondition());
			}

			if (p instanceof SwitchStmt) {
				NodeList<SwitchEntry> entries = ((SwitchStmt) p).getEntries();
				entries.add(obj);
				((SwitchStmt) p).setEntries(entries);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(SwitchExpr.class.getSimpleName())) {
			SwitchExpr obj = new SwitchExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(SwitchStmt.class.getSimpleName())) {
			SwitchStmt obj = new SwitchStmt();
			obj.setSelector(attributes.getSelector());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(SynchronizedStmt.class.getSimpleName())) {
			SynchronizedStmt obj = new SynchronizedStmt();
			jpNode = obj;
		} else if (n.getNodeType().equals(TextBlockLiteralExpr.class.getSimpleName())) {
			TextBlockLiteralExpr obj = new TextBlockLiteralExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(JavaNodeTypes.Then.name())) {
			IfStmt ifStmt = (IfStmt) p;
			while (ifStmt.hasElseBranch()) {
				ifStmt = (IfStmt) ifStmt.getElseStmt().get();
			}
			NodeList<Expression> conditions = attributes.getCondition();
			ifStmt.setCondition(conditions.get(0));
			IfStmt elseStmt = new IfStmt();
			elseStmt.setCondition(new BooleanLiteralExpr(true));
			elseStmt.setThenStmt(new EmptyStmt());
			ifStmt.setElseStmt(elseStmt);
			ifStmt.setThenStmt(new EmptyStmt());
			jpNode = ifStmt;
		} else if (n.getNodeType().equals(ThisExpr.class.getSimpleName())) {
			ThisExpr obj = new ThisExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(ThrowStmt.class.getSimpleName())) {
			ThrowStmt obj = attributes.getStatement().asThrowStmt();

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			} else {
				throw new UnsupportedOperationException();
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(TryStmt.class.getSimpleName())) {
			TryStmt obj = new TryStmt();

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(TypeExpr.class.getSimpleName())) {
			TypeExpr obj = new TypeExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(TypeParameter.class.getSimpleName())) {
			TypeParameter obj = new TypeParameter();
			jpNode = obj;
		} else if (n.getNodeType().equals(UnaryExpr.class.getSimpleName())) {
			UnaryExpr obj = new UnaryExpr();
			obj.setExpression(attributes.getName());
			obj.setOperator(UnaryExpr.Operator.valueOf(attributes.getOperator()));

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(UnionType.class.getSimpleName())) {
			UnionType obj = new UnionType();
			jpNode = obj;
		} else if (n.getNodeType().equals(UnknownType.class.getSimpleName())) {
			UnknownType obj = new UnknownType();
			jpNode = obj;
		} else if (n.getNodeType().equals(UnparsableStmt.class.getSimpleName())) {
			UnparsableStmt obj = new UnparsableStmt();
			jpNode = obj;
		} else if (n.getNodeType().equals(VariableDeclarationExpr.class.getSimpleName())) {
			VariableDeclarationExpr obj = new VariableDeclarationExpr();
			jpNode = obj;
		} else if (n.getNodeType().equals(VariableDeclarator.class.getSimpleName())) {
			VariableDeclarator obj = new VariableDeclarator();
			obj.setType(attributes.getType());
			obj.setName(attributes.getName());
			obj.setInitializer(attributes.getInitilization().getFirst().get());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(new VariableDeclarationExpr(obj));
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(VarType.class.getSimpleName())) {
			VarType obj = new VarType();
			jpNode = obj;
		} else if (n.getNodeType().equals(VoidType.class.getSimpleName())) {
			VoidType obj = new VoidType();
			jpNode = obj;
		} else if (n.getNodeType().equals(WhileStmt.class.getSimpleName())) {
			WhileStmt obj = new WhileStmt();
			obj.setCondition(attributes.getValue());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			} else {
				throw new UnsupportedOperationException();
			}

			jpNode = obj;
		} else if (n.getNodeType().equals(WildcardType.class.getSimpleName())) {
			WildcardType obj = new WildcardType();
			jpNode = obj;
		} else if (n.getNodeType().equals(YieldStmt.class.getSimpleName())) {
			YieldStmt obj = new YieldStmt();
			jpNode = obj;
		}

		if (p != null && jpNode != null) {
			jpNode.setParentNode(p);
			
			if (p instanceof EnclosedExpr && jpNode instanceof Expression) {
				((EnclosedExpr) p).setInner((Expression) jpNode);
			}
		}

		// set jpNode as parent to all the nodes, that are generated from the
		// children
		for (Node nChild : n.getChildren()) {
			com.github.javaparser.ast.Node jpChild = visitWriter(nChild, Optional.ofNullable(jpNode).orElse(p));
		}

		return jpNode;
	}
}