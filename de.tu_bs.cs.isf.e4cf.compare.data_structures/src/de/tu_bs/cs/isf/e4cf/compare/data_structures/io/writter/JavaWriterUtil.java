
package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.util.NoSuchElementException;
import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.JavaNodeTypes;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
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
			jpNode = new CompilationUnit();
		} else if (isOfType(n, CompilationUnit.class)) {
			jpNode = createClassOrInterfaceDeclaration(attributes, p);
		} else if (isOfType(n, AnnotationDeclaration.class)) {
			CompilationUnit cu;
			if (p instanceof CompilationUnit) {
				cu = (CompilationUnit) p;
			} else {
				cu = p.findCompilationUnit().get();
			}
			Modifier.Keyword[] keywords = new Modifier.Keyword[attributes.getModifier().size()];
			cu.addAnnotationDeclaration(attributes.getName(), attributes.getModifier().toArray(keywords));

			jpNode = cu.getAnnotationDeclarationByName(attributes.getName()).get();
		} else if (isOfType(n, AnnotationMemberDeclaration.class)) {
			AnnotationMemberDeclaration obj = new AnnotationMemberDeclaration();
			obj.setModifiers(attributes.getModifier());
			obj.setType(attributes.getType());
			obj.setName(attributes.getName());
			obj.setDefaultValue(attributes.getValue());

			if (p instanceof AnnotationDeclaration) {
				((AnnotationDeclaration) p).addMember(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Argument)) {
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
		} else if (isOfType(n, ArrayAccessExpr.class)) {
			ArrayAccessExpr obj = new ArrayAccessExpr();
			obj.setIndex(attributes.getValue());
			obj.setName(StaticJavaParser.parseExpression(attributes.getName()));

			jpNode = obj;
		} else if (isOfType(n, ArrayCreationExpr.class)) {
			ArrayCreationExpr obj = new ArrayCreationExpr();
			obj.setElementType(attributes.getType());
			jpNode = obj;
		} else if (isOfType(n, ArrayCreationLevel.class)) {
			ArrayCreationLevel obj = new ArrayCreationLevel();
			jpNode = obj;
		} else if (isOfType(n, ArrayInitializerExpr.class)) {
			ArrayInitializerExpr obj = new ArrayInitializerExpr();
			jpNode = obj;
		} else if (isOfType(n, ArrayType.class)) {
			ArrayType obj = new ArrayType(attributes.getType(),
					attributes.getAnnotation().stream().toArray(AnnotationExpr[]::new));// TODO needs closer lookjpNode
																						// = obj;
		} else if (isOfType(n, AssertStmt.class)) {
			AssertStmt obj = new AssertStmt();
			obj.setCheck(attributes.getCheck());
			obj.setMessage(attributes.getMessage());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Assignment)) {
			AssignExpr obj = new AssignExpr();
			obj.setTarget(attributes.getTarget());
			obj.setValue(attributes.getValue());
			obj.setOperator(AssignExpr.Operator.valueOf(attributes.getOperator()));

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, BinaryExpr.class)) {
			BinaryExpr obj = new BinaryExpr();
			jpNode = obj;
		} else if (isOfType(n, BlockComment.class)) {
			BlockComment obj = new BlockComment(attributes.getComment());
			p.addOrphanComment(obj);
			jpNode = obj;
		} else if (isOfType(n, BlockStmt.class)
				|| isOfType(n, JavaNodeTypes.Body)) {
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
		} else if (isOfType(n, BooleanLiteralExpr.class)) {
			BooleanLiteralExpr obj = new BooleanLiteralExpr();
			obj.setValue(Boolean.valueOf(attributes.getValue().toString()));
			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Bound)) {
			if (p instanceof TypeParameter) {
				TypeParameter tp = (TypeParameter) p;
				NodeList<ClassOrInterfaceType> getTypeBound = tp.getTypeBound();
				ClassOrInterfaceType bound = new ClassOrInterfaceType();
				bound.setName(attributes.getName());
				bound.setAnnotations(attributes.getAnnotation());
				NodeList<Type> typeList = new NodeList<Type>();
				attributes.getTypeParameterBound().forEach(coid -> typeList.add(coid));
				bound.setTypeArguments(typeList);
				getTypeBound.add(bound);
				tp.setTypeBound(getTypeBound);
			}
		} else if (isOfType(n, BreakStmt.class)
				|| isOfType(n, JavaNodeTypes.Break)) {
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
		} else if (isOfType(n, CastExpr.class)) {
			CastExpr obj = new CastExpr();
			obj.setType(attributes.getType());
			jpNode = obj;
		} else if (isOfType(n, CatchClause.class)) {
			CatchClause obj = new CatchClause();

			if (p instanceof TryStmt) {
				NodeList<CatchClause> clauses = ((TryStmt) p).getCatchClauses();
				clauses.add(obj);
				((TryStmt) p).setCatchClauses(clauses);
			}

			jpNode = obj;
		} else if (isOfType(n, CharLiteralExpr.class)) {
			CharLiteralExpr obj = new CharLiteralExpr();
			obj.setValue(attributes.getValue().toString());
			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Class)) {
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
		} else if (isOfType(n, ClassExpr.class)) {
			ClassExpr obj = new ClassExpr();
			obj.setType(attributes.getType());
			jpNode = obj;
		} else if (isOfType(n, ConditionalExpr.class)) {
			ConditionalExpr obj = new ConditionalExpr();
			obj.setCondition(attributes.getCondition().getFirst().get());
			obj.setThenExpr(attributes.getThen());
			obj.setElseExpr(attributes.getElse());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, ConstructorDeclaration.class)) {
			ConstructorDeclaration obj = new ConstructorDeclaration();
			obj.setModifiers(attributes.getModifier());
			obj.setName(attributes.getName());
			obj.setAnnotations(attributes.getAnnotation());
			jpNode = obj;
		} else if (isOfType(n, ContinueStmt.class)) {
			ContinueStmt obj = new ContinueStmt();
			obj.setLabel(attributes.getTarget().toString());
			jpNode = obj;
		} else if (isOfType(n, DoStmt.class)) {
			DoStmt obj = new DoStmt();
			obj.setCondition(attributes.getCondition().getFirst().get());
			jpNode = obj;
		} else if (isOfType(n, DoubleLiteralExpr.class)) {
			DoubleLiteralExpr obj = new DoubleLiteralExpr();
			obj.setValue(attributes.getValue().toString());
			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Else)) {
			BlockStmt elseStmt = new BlockStmt();
			IfStmt ifStmt = (IfStmt) p;
			while (ifStmt.hasElseBranch()) {
				ifStmt = (IfStmt) ifStmt.getElseStmt().get();
			}
			IfStmt parentIfStmt = (IfStmt) ifStmt.getParentNode().get();
			parentIfStmt.removeElseStmt();
			parentIfStmt.setElseStmt(elseStmt);
			jpNode = elseStmt;
		} else if (isOfType(n, EmptyStmt.class)) {
			EmptyStmt obj = new EmptyStmt();
			jpNode = obj;
		} else if (isOfType(n, EnclosedExpr.class)) {
			EnclosedExpr obj = new EnclosedExpr();

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, EnumConstantDeclaration.class)) {
			EnumConstantDeclaration obj = new EnumConstantDeclaration();
			jpNode = obj;
		} else if (isOfType(n, EnumDeclaration.class)) {
			EnumDeclaration obj = new EnumDeclaration();
			jpNode = obj;
		} else if (isOfType(n, ExplicitConstructorInvocationStmt.class)) {
			ExplicitConstructorInvocationStmt obj = new ExplicitConstructorInvocationStmt();
			jpNode = obj;
		} else if (isOfType(n, ExpressionStmt.class)) {
			ExpressionStmt obj = new ExpressionStmt();
			jpNode = obj;
		} else if (isOfType(n, FieldAccessExpr.class)) {
			FieldAccessExpr obj = new FieldAccessExpr();
			jpNode = obj;
		} else if (isOfType(n, FieldDeclaration.class)) {
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
		} else if (isOfType(n, ForEachStmt.class)) {
			ForEachStmt obj = new ForEachStmt();
			obj.setIterable(attributes.getIterator());
			obj.setVariable(new VariableDeclarationExpr(attributes.getType(),
					attributes.getInitilization().getFirst().get().toString()));

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, ForStmt.class)) {
			ForStmt obj = new ForStmt();
			obj.setInitialization(attributes.getInitilization());
			obj.setCompare(attributes.getComparison());
			obj.setUpdate(attributes.getUpdate());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, IfStmt.class)) {
			IfStmt obj = new IfStmt();
			jpNode = obj;

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}
		} else if (isOfType(n, JavaNodeTypes.Import)) {
			if (p != null) {
				Optional<CompilationUnit> cuOpt = p.findCompilationUnit();
				if (cuOpt.isPresent() && !attributes.getName().isEmpty()) {
					cuOpt.get().addImport(new ImportDeclaration(attributes.getName(), attributes.isStatic(),
							attributes.isAsteriks()));
				}
			}
		} else if (isOfType(n, InitializerDeclaration.class)) {
			InitializerDeclaration obj = new InitializerDeclaration();
			jpNode = obj;
		} else if (isOfType(n, InstanceOfExpr.class)) {
			InstanceOfExpr obj = new InstanceOfExpr();
			jpNode = obj;
		} else if (isOfType(n, IntegerLiteralExpr.class)) {
			IntegerLiteralExpr obj = new IntegerLiteralExpr();
			jpNode = obj;
		} else if (isOfType(n, IntersectionType.class)) {
			/*
			 * TODO fill arguments IntersectionType obj = new IntersectionType(); jpNode =
			 * obj;
			 */
		} else if (isOfType(n, LabeledStmt.class)) {
			LabeledStmt obj = new LabeledStmt();
			jpNode = obj;
		} else if (isOfType(n, LambdaExpr.class)) {
			LambdaExpr obj = new LambdaExpr();

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, LineComment.class)) {
			LineComment obj = new LineComment(attributes.getComment());
			p.addOrphanComment(obj);
			jpNode = obj;
		} else if (isOfType(n, LocalClassDeclarationStmt.class)) {
			LocalClassDeclarationStmt obj = new LocalClassDeclarationStmt();
			jpNode = obj;
		} else if (isOfType(n, LongLiteralExpr.class)) {
			LongLiteralExpr obj = new LongLiteralExpr();
			jpNode = obj;
		} else if (isOfType(n, MarkerAnnotationExpr.class)) {
			MarkerAnnotationExpr obj = new MarkerAnnotationExpr();
			jpNode = obj;
		} else if (isOfType(n, MemberValuePair.class)) {
			MemberValuePair obj = new MemberValuePair();
			obj.setName(attributes.getKey());
			obj.setValue(attributes.getValue());

			if (p instanceof NormalAnnotationExpr) {
				NodeList<MemberValuePair> paris = ((NormalAnnotationExpr) p).getPairs();
				paris.add(obj);
				((NormalAnnotationExpr) p).setPairs(paris);
			}

			jpNode = obj;
		} else if (isOfType(n, MethodCallExpr.class)) {
			MethodCallExpr obj = new MethodCallExpr();
			obj.setScope(attributes.getScope());
			obj.setName(attributes.getName());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			} else if (p instanceof LambdaExpr) {
				((LambdaExpr) p).setBody(new ExpressionStmt(obj));
			}

			jpNode = obj;
		} else if (isOfType(n, MethodDeclaration.class)) {
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
		} else if (isOfType(n, MethodReferenceExpr.class)) {
			MethodReferenceExpr obj = new MethodReferenceExpr();
			jpNode = obj;
		} else if (isOfType(n, NameExpr.class)) {
			NameExpr obj = new NameExpr();
			jpNode = obj;
		} else if (isOfType(n, NormalAnnotationExpr.class)) {
			NormalAnnotationExpr obj = new NormalAnnotationExpr();
			obj.setName(attributes.getName());

			if (p instanceof NodeWithAnnotations) {
				((NodeWithAnnotations) p).addAnnotation(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, NullLiteralExpr.class)) {
			NullLiteralExpr obj = new NullLiteralExpr();
			jpNode = obj;
		} else if (isOfType(n, ObjectCreationExpr.class)) {
			ObjectCreationExpr obj = new ObjectCreationExpr();
			jpNode = obj;
		} else if (isOfType(n, Parameter.class)) {
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
		} else if (isOfType(n, PrimitiveType.class)) {
			PrimitiveType obj = new PrimitiveType();
			jpNode = obj;
		} else if (isOfType(n, ReceiverParameter.class)) {
			ReceiverParameter obj = new ReceiverParameter();
			jpNode = obj;
		} else if (isOfType(n, ReturnStmt.class)) {
			ReturnStmt obj = new ReturnStmt(attributes.getValue());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			} else {
				throw new UnsupportedOperationException();
			}

			jpNode = obj;
		} else if (isOfType(n, SingleMemberAnnotationExpr.class)) {
			SingleMemberAnnotationExpr obj = new SingleMemberAnnotationExpr();
			obj.setName(attributes.getName());
			obj.setMemberValue(attributes.getValue());

			if (p instanceof NodeWithAnnotations) {
				((NodeWithAnnotations) p).addAnnotation(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, StringLiteralExpr.class)) {
			StringLiteralExpr obj = new StringLiteralExpr();
			jpNode = obj;
		} else if (isOfType(n, SuperExpr.class)) {
			SuperExpr obj = new SuperExpr();
			jpNode = obj;
		} else if (isOfType(n, SwitchEntry.class)) {
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
		} else if (isOfType(n, SwitchExpr.class)) {
			SwitchExpr obj = new SwitchExpr();
			jpNode = obj;
		} else if (isOfType(n, SwitchStmt.class)) {
			SwitchStmt obj = new SwitchStmt();
			obj.setSelector(attributes.getSelector());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, SynchronizedStmt.class)) {
			SynchronizedStmt obj = new SynchronizedStmt();
			jpNode = obj;
		} else if (isOfType(n, TextBlockLiteralExpr.class)) {
			TextBlockLiteralExpr obj = new TextBlockLiteralExpr();
			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Then)) {
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
		} else if (isOfType(n, ThisExpr.class)) {
			ThisExpr obj = new ThisExpr();
			jpNode = obj;
		} else if (isOfType(n, ThrowStmt.class)) {
			ThrowStmt obj = attributes.getStatement().asThrowStmt();

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			} else {
				throw new UnsupportedOperationException();
			}

			jpNode = obj;
		} else if (isOfType(n, TryStmt.class)) {
			TryStmt obj = new TryStmt();

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, TypeExpr.class)) {
			TypeExpr obj = new TypeExpr();
			jpNode = obj;
		} else if (isOfType(n, TypeParameter.class)) {
			TypeParameter obj = new TypeParameter();
			obj.setName(attributes.getName());
			if (attributes.getAnnotation().isNonEmpty()) {
				obj.setAnnotations(attributes.getAnnotation());
			}

			if (p instanceof NodeWithTypeParameters) {
				((NodeWithTypeParameters) p).addTypeParameter(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, UnaryExpr.class)) {
			UnaryExpr obj = new UnaryExpr();
			obj.setExpression(attributes.getName());
			obj.setOperator(UnaryExpr.Operator.valueOf(attributes.getOperator()));

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, UnionType.class)) {
			UnionType obj = new UnionType();
			jpNode = obj;
		} else if (isOfType(n, UnknownType.class)) {
			UnknownType obj = new UnknownType();
			jpNode = obj;
		} else if (isOfType(n, UnparsableStmt.class)) {
			UnparsableStmt obj = new UnparsableStmt();
			jpNode = obj;
		} else if (isOfType(n, VariableDeclarationExpr.class)) {
			VariableDeclarationExpr obj = new VariableDeclarationExpr();
			jpNode = obj;
		} else if (isOfType(n, VariableDeclarator.class)) {
			VariableDeclarator obj = new VariableDeclarator();
			obj.setType(attributes.getType());
			obj.setName(attributes.getName());
			obj.setInitializer(attributes.getInitilization().getFirst().get());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(new VariableDeclarationExpr(obj));
			}

			jpNode = obj;
		} else if (isOfType(n, VarType.class)) {
			VarType obj = new VarType();
			jpNode = obj;
		} else if (isOfType(n, VoidType.class)) {
			VoidType obj = new VoidType();
			jpNode = obj;
		} else if (isOfType(n, WhileStmt.class)) {
			WhileStmt obj = new WhileStmt();
			obj.setCondition(attributes.getValue());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			} else {
				throw new UnsupportedOperationException();
			}

			jpNode = obj;
		} else if (isOfType(n, WildcardType.class)) {
			WildcardType obj = new WildcardType();
			jpNode = obj;
		} else if (isOfType(n, YieldStmt.class)) {
			YieldStmt obj = new YieldStmt();
			jpNode = obj;
		}

		if (!(jpNode instanceof Comment) && !attributes.getComment().isEmpty()) {
			String commentContent = attributes.getComment();
			Comment comment = null;
			if (commentContent.startsWith("/**")) {
				comment = new JavadocComment(commentContent);
			} else if (commentContent.startsWith("//")) {
				comment = new LineComment(commentContent);
			} else {
				comment = new BlockComment(commentContent);
			}
			jpNode.setComment(comment);
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

	/**
	 * Some {@link Node} have a type, that equals the simple name of a subclass of
	 * {@link com.github.javaparser.ast.Node}. This function compares whatever the
	 * node type of a e4cf node matches the class name.
	 * 
	 * @see isOfType
	 * @param compareFrameworkNode The e4cf node
	 * @param javaParserClazz      Class
	 * @return <code>true</code>, if {@link Node#getNodeType()} matches
	 *         {@link Class#getSimpleName()}.
	 */
	private static boolean isOfType(Node compareFrameworkNode, Class javaParserClazz) {
		return compareFrameworkNode.getNodeType().equals(javaParserClazz.getSimpleName());
	}

	/**
	 * Some {@link Node} have a type, that starts with (or equals) the name of an
	 * element of the enumeration {@link JavaNodeTypes}. This function compares
	 * whatever the node type of a e4cf node matches the name.
	 * 
	 * @param compareFrameworkNode e4cf Node
	 * @param type                 Type to be checked
	 * @return <code>true</code>, if {@link Node#getNodeType()} start with
	 *         {@link JavaNodeTypes#name()}, <code>false</code> otherwise.
	 */
	private static boolean isOfType(Node compareFrameworkNode, JavaNodeTypes type) {
		return compareFrameworkNode.getNodeType().startsWith(type.name());
	}

	/**
	 * Creates a new {@link ClassOrInterfaceDeclaration}. The attributes for this
	 * node a retrieved from the parameter.
	 * 
	 * @param attributes Attributes of the new node
	 * @param p          Parent node of the new node
	 * @return {@link ClassOrInterfaceDeclaration}
	 */
	private static ClassOrInterfaceDeclaration createClassOrInterfaceDeclaration(
			JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p) {
		ClassOrInterfaceDeclaration coid = new ClassOrInterfaceDeclaration();

		// Name
		coid.setName(attributes.getName());

		// Modifier
		coid.setModifiers(attributes.getModifier());

		// Extends a class?
		if (!attributes.getSuperclass().isEmpty()) {
			coid.addExtendedType(attributes.getSuperclass());
		}

		// Implemented types
		coid.setImplementedTypes(attributes.getInterface());

		// If node is an interface, set boolean to true
		coid.setInterface(attributes.isInterface());

		// Add new node to parent node
		if (p instanceof CompilationUnit) {
			CompilationUnit cu = (CompilationUnit) p;
			cu.addType(coid);

			// Set package of compilation unit
			if (!attributes.getPackage().isEmpty()) {
				cu.setPackageDeclaration(attributes.getPackage());
			}
		} else {
			// If p is not supported, throw exception
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName()
					+ ". Expected: " + CompilationUnit.class.getSimpleName());
		}

		return coid;
	}
}