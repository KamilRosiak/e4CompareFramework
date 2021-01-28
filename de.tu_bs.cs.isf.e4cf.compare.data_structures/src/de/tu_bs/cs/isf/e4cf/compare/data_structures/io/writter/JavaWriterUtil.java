
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
import com.github.javaparser.resolution.UnsolvedSymbolException;
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
	 * @return n as JavaParser node
	 * @throws UnsupportedOperationException if an attribute, node type etc. is not
	 *                                       supported.
	 */
	public com.github.javaparser.ast.Node visitWriter(Node n) throws UnsupportedOperationException {
		return visitWriter(n, null);
	}

	/**
	 * The recursive implementation of {@link JavaWriterUtil#visitWriter(Node)}.
	 * 
	 * @param n Framework node
	 * @param p is the parent node of n's equivalent JavaParserNode. This can be
	 *          null.
	 * @return n as JavaParser node
	 * @throws UnsupportedOperationException if an attribute, node type etc. is not
	 *                                       supported.
	 */
	private /* @ nullable @ */ com.github.javaparser.ast.Node visitWriter(Node n,
			/* @ nullable @ */ com.github.javaparser.ast.Node p) throws UnsupportedOperationException {
		// Declare a new node
		com.github.javaparser.ast.Node jpNode = null;

		// Collect the attributes from the given node
		JavaWriterAttributeCollector attributes = new JavaWriterAttributeCollector();
		attributes.collectAttributes(n);

		/*
		 * Create a new jp node based on e4cf node type and set specific values for the
		 * new node
		 */
		if (n.getNodeType().equals(JavaWriter.NODE_TYPE_TREE)) {
			jpNode = new CompilationUnit();
		} else if (isOfType(n, CompilationUnit.class)) {
			jpNode = createClassOrInterfaceDeclaration(attributes, p);
		} else if (isOfType(n, AnnotationDeclaration.class)) {
			jpNode = createAnnotationDeclaration(attributes, p);
		} else if (isOfType(n, AnnotationMemberDeclaration.class)) {
			jpNode = createAnnotationMemberDeclaration(attributes, p);
		} else if (isOfType(n, JavaNodeTypes.Argument)) {
			processArgument(attributes, p);
		} else if (isOfType(n, ArrayAccessExpr.class)) {
			ArrayAccessExpr obj = new ArrayAccessExpr();
			// Set the index of the accessed entry
			obj.setIndex(attributes.getValue());
			// Set the name of the array, which must be an expression
			obj.setName(StaticJavaParser.parseExpression(attributes.getName()));
			jpNode = obj;
		} else if (isOfType(n, ArrayCreationExpr.class)) {
			ArrayCreationExpr obj = new ArrayCreationExpr();
			// Set the type of the array creation expr
			obj.setElementType(attributes.getType());
			jpNode = obj;
		} else if (isOfType(n, ArrayCreationLevel.class)) {
			jpNode = new ArrayCreationLevel();
		} else if (isOfType(n, ArrayInitializerExpr.class)) {
			jpNode = new ArrayInitializerExpr();
		} else if (isOfType(n, ArrayType.class)) {
			// TODO check this
			/*
			 * ArrayType requires the annotations as an array, there the nodelist from
			 * attributes must be converted.
			 */
			jpNode = new ArrayType(attributes.getType(),
					attributes.getAnnotation().stream().toArray(AnnotationExpr[]::new));
		} else if (isOfType(n, AssertStmt.class)) {
			jpNode = createAssertStmt(attributes, p);
		} else if (isOfType(n, JavaNodeTypes.Assignment)) {
			jpNode = createAssignExpr(attributes, p);
		} else if (isOfType(n, BinaryExpr.class)) {
			jpNode = new BinaryExpr();
		} else if (isOfType(n, BlockComment.class)) {
			BlockComment obj = new BlockComment(attributes.getComment());
			p.addOrphanComment(obj);
			jpNode = obj;
		} else if (isOfType(n, BlockStmt.class) || isOfType(n, JavaNodeTypes.Body)) {
			jpNode = createBlockStmt(p);
		} else if (isOfType(n, BooleanLiteralExpr.class)) {
			BooleanLiteralExpr obj = new BooleanLiteralExpr();
			/*
			 * A boolean literal expr is either true or false. This value is saved in the
			 * attribute value, which is a parsed as an expression but needs to be converted
			 * to a boolean.
			 */
			obj.setValue(Boolean.valueOf(attributes.getValue().toString()));
			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Bound)) {
			processBound(attributes, p);
		} else if (isOfType(n, BreakStmt.class) || isOfType(n, JavaNodeTypes.Break)) {
			jpNode = createBreakStmt(attributes, p);
		} else if (isOfType(n, CastExpr.class)) {
			CastExpr obj = new CastExpr();
			// Set the desired type
			obj.setType(attributes.getType());
			jpNode = obj;
		} else if (isOfType(n, CatchClause.class)) {
			jpNode = createCatchClause(p);
		} else if (isOfType(n, CharLiteralExpr.class)) {
			CharLiteralExpr obj = new CharLiteralExpr();
			// Set the char of the expr
			obj.setValue(attributes.getValue().toString());
			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Class)) {
			jpNode = createClassOrInterfaceDeclaration(attributes, p);
		} else if (isOfType(n, ClassExpr.class)) {
			ClassExpr obj = new ClassExpr();
			// Set the class of the expr
			obj.setType(attributes.getType());
			jpNode = obj;
		} else if (isOfType(n, ConditionalExpr.class)) {
			jpNode = createConditionalExpr(attributes, p);
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
			jpNode = new EmptyStmt();
		} else if (isOfType(n, EnclosedExpr.class)) {
			EnclosedExpr obj = new EnclosedExpr();

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, EnumConstantDeclaration.class)) {
			jpNode = new EnumConstantDeclaration();
		} else if (isOfType(n, EnumDeclaration.class)) {
			jpNode = new EnumDeclaration();
		} else if (isOfType(n, ExplicitConstructorInvocationStmt.class)) {
			jpNode = new ExplicitConstructorInvocationStmt();
		} else if (isOfType(n, ExpressionStmt.class)) {
			jpNode = new ExpressionStmt();
		} else if (isOfType(n, FieldAccessExpr.class)) {
			jpNode = new FieldAccessExpr();
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

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, JavaNodeTypes.Import)) {
			if (p != null) {
				Optional<CompilationUnit> cuOpt = p.findCompilationUnit();
				if (cuOpt.isPresent() && !attributes.getName().isEmpty()) {
					cuOpt.get().addImport(new ImportDeclaration(attributes.getName(), attributes.isStatic(),
							attributes.isAsteriks()));
				}
			}
		} else if (isOfType(n, InitializerDeclaration.class)) {
			jpNode = new InitializerDeclaration();
		} else if (isOfType(n, InstanceOfExpr.class)) {
			jpNode = new InstanceOfExpr();
		} else if (isOfType(n, IntegerLiteralExpr.class)) {
			jpNode = new IntegerLiteralExpr();
		} else if (isOfType(n, JavaNodeTypes.Interface)) {
			jpNode = createClassOrInterfaceDeclaration(attributes, p);
		} else if (isOfType(n, IntersectionType.class)) {
			/*
			 * TODO fill arguments IntersectionType obj = new IntersectionType(); jpNode =
			 * obj;
			 */
		} else if (isOfType(n, LabeledStmt.class)) {
			jpNode = new LabeledStmt();
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
			jpNode = new LocalClassDeclarationStmt();
		} else if (isOfType(n, LongLiteralExpr.class)) {
			jpNode = new LongLiteralExpr();
		} else if (isOfType(n, MarkerAnnotationExpr.class)) {
			jpNode = new MarkerAnnotationExpr();
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
			jpNode = new MethodReferenceExpr();
		} else if (isOfType(n, NameExpr.class)) {
			jpNode = new NameExpr();
		} else if (isOfType(n, NormalAnnotationExpr.class)) {
			NormalAnnotationExpr obj = new NormalAnnotationExpr();
			obj.setName(attributes.getName());

			if (p instanceof NodeWithAnnotations) {
				((NodeWithAnnotations) p).addAnnotation(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, NullLiteralExpr.class)) {
			jpNode = new NullLiteralExpr();
		} else if (isOfType(n, ObjectCreationExpr.class)) {
			jpNode = new ObjectCreationExpr();
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
			jpNode = new PrimitiveType();
		} else if (isOfType(n, ReceiverParameter.class)) {
			jpNode = new ReceiverParameter();
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
			jpNode = new StringLiteralExpr();
		} else if (isOfType(n, SuperExpr.class)) {
			jpNode = new SuperExpr();
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
			jpNode = new SwitchExpr();
		} else if (isOfType(n, SwitchStmt.class)) {
			SwitchStmt obj = new SwitchStmt();
			obj.setSelector(attributes.getSelector());

			if (p instanceof NodeWithStatements) {
				((NodeWithStatements) p).addStatement(obj);
			}

			jpNode = obj;
		} else if (isOfType(n, SynchronizedStmt.class)) {
			jpNode = new SynchronizedStmt();
		} else if (isOfType(n, TextBlockLiteralExpr.class)) {
			jpNode = new TextBlockLiteralExpr();
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
			jpNode = new ThisExpr();
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
			jpNode = new TypeExpr();
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
			jpNode = new UnionType();
		} else if (isOfType(n, UnknownType.class)) {
			jpNode = new UnknownType();
		} else if (isOfType(n, UnparsableStmt.class)) {
			jpNode = new UnparsableStmt();
		} else if (isOfType(n, VariableDeclarationExpr.class)) {
			jpNode = new VariableDeclarationExpr();
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
			jpNode = new VarType();
		} else if (isOfType(n, VoidType.class)) {
			jpNode = new VoidType();
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
			jpNode = new WildcardType();
		} else if (isOfType(n, YieldStmt.class)) {
			jpNode = new YieldStmt();
		}

		// Set general attributes, which could apply to a set of node here.
		if (!(jpNode instanceof Comment) && !attributes.getComment().isEmpty()) {
			// Set the comment of a node
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

		/*
		 * Set the parent of the node. This often needs to be done more specific. This
		 * is done in the create functions.
		 */
		if (p != null && jpNode != null) {
			jpNode.setParentNode(p);

			/*
			 * An enclosed expr could appear anywhere, there fore the parent node is set in
			 * this place
			 */
			if (p instanceof EnclosedExpr && jpNode instanceof Expression) {
				((EnclosedExpr) p).setInner((Expression) jpNode);
			}
		}

		/*
		 * set jpNode as parent to all the nodes, that are generated from the children
		 */
		for (Node nChild : n.getChildren()) {
			com.github.javaparser.ast.Node jpChild = visitWriter(nChild, Optional.ofNullable(jpNode).orElse(p));
		}

		return jpNode;
	}

	/**
	 * Creates a new {@link ConditionalExpr} and sets its check, then and else
	 * statements. Afterwards the new node is added to its parent.
	 * 
	 * @param attributes Attributes of the node
	 * @param p          Parent of the node
	 * @return A new condition expr
	 * @throws UnsupportedOperationException Missing impl for type of p
	 */
	private ConditionalExpr createConditionalExpr(JavaWriterAttributeCollector attributes,
			com.github.javaparser.ast.Node p) throws UnsupportedOperationException {
		ConditionalExpr obj = new ConditionalExpr();
		// Set condition to first and only (should be)
		obj.setCondition(attributes.getCondition().getFirst().get());
		obj.setThenExpr(attributes.getThen());
		obj.setElseExpr(attributes.getElse());

		if (p instanceof NodeWithStatements) {
			((NodeWithStatements) p).addStatement(obj);
		} else {
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName());
		}

		return obj;
	}

	/**
	 * Creates a new {@link CatchClause} and adds it to the parent.
	 * 
	 * @param p {@link TryStmt} of the catch clause
	 * @return New catch clause
	 * @exception UnsupportedOperationException When p is not a {@link TryStmt}
	 */
	private CatchClause createCatchClause(com.github.javaparser.ast.Node p) throws UnsupportedOperationException {
		CatchClause obj = new CatchClause();

		if (p instanceof TryStmt) {
			NodeList<CatchClause> clauses = ((TryStmt) p).getCatchClauses();
			clauses.add(obj);
			((TryStmt) p).setCatchClauses(clauses);
		} else {
			// It should always be a try stmt
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName());
		}

		return obj;
	}

	/**
	 * Creates a new {@link BreakStmt} and sets its label properly. Then the
	 * statement is added to the parent.
	 * 
	 * @param attributes Attributes of the break stmt
	 * @param p          Parent of the break stmt
	 * @throws UnsupportedOperationException When there is missing an implementation
	 *                                       for specific types of p.
	 */
	private BreakStmt createBreakStmt(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p)
			throws UnsupportedOperationException {
		BreakStmt obj = new BreakStmt();
		/*
		 * If there is an target for the break stmt, then set it; otherwise remove the
		 * default "empty" label.
		 */
		if (attributes.getTarget() != null) {
			obj.setLabel(new SimpleName(attributes.getTarget().toString()));
		} else {
			obj.removeLabel();
		}

		/*
		 * Add the break stmt to the parent node or throw an exception if there is
		 * missing an implementation.
		 */
		if (p instanceof NodeWithStatements) {
			((NodeWithStatements) p).addStatement(obj);
		} else {
			// there is missing a case
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName());
		}

		return obj;
	}

	/**
	 * Adds a bound to a type parameter.
	 * 
	 * @param attributes Attributes of the bound
	 * @param p          Node with the bound
	 * @throws UnsupportedOperationException When p is not a {@link TypeParameter},
	 *                                       there is missing an implementation.
	 */
	private void processBound(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p)
			throws UnsupportedOperationException {
		if (p instanceof TypeParameter) {
			// Cast p to type parameter for easier reuse
			TypeParameter tp = (TypeParameter) p;
			// Get current bounds of p
			NodeList<ClassOrInterfaceType> getTypeBound = tp.getTypeBound();
			// Create a new bound
			ClassOrInterfaceType bound = new ClassOrInterfaceType();
			// Set the name
			bound.setName(attributes.getName());
			// Set the annotations
			bound.setAnnotations(attributes.getAnnotation());
			// Set the generics of the bound (bound of bound :-))
			bound.setTypeArguments(attributes.getTypeParameterBound());
			// Add the bound to the current list of bounds
			getTypeBound.add(bound);
			// Set the new bound list
			tp.setTypeBound(getTypeBound);
		} else {
			// there is missing a case
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName());
		}
	}

	/**
	 * Creates a new {@link BlockStmt} and adds it to the parent node.
	 * 
	 * @param p Parent of the block stmt
	 * @return New block stmt
	 * @throws UnsupportedOperationException When there is missing an implementation
	 *                                       for a specific parent.
	 */
	private BlockStmt createBlockStmt(com.github.javaparser.ast.Node p) throws UnsupportedOperationException {
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
		} else {
			// there is missing a case
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName());
		}
		return obj;
	}

	/**
	 * Creates a new {@link AssignExpr} and sets the target, value and operator of
	 * the expression. The new node is then added to the parent as a statement.
	 * 
	 * @param attributes Attributes of the framework node
	 * @param p          Parent node of the new node
	 * @exception UnsupportedOperationException If there is missing a implementation
	 *                                          for different types of p.
	 * @return New assign stmt
	 */
	private AssignExpr createAssignExpr(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p)
			throws UnsupportedOperationException {
		AssignExpr obj = new AssignExpr();
		obj.setTarget(attributes.getTarget());
		obj.setValue(attributes.getValue());
		obj.setOperator(AssignExpr.Operator.valueOf(attributes.getOperator()));

		if (p instanceof NodeWithStatements) {
			((NodeWithStatements) p).addStatement(obj);
		} else {
			// there is missing a case
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName()
					+ ". Expected: " + NodeWithStatements.class.getSimpleName());
		}

		return obj;
	}

	/**
	 * Generates a new instance of {@link AssertStmt}. This method sets the check
	 * and the message of the new node with values from attributes. The new node is
	 * added as a statement to the new node.
	 * 
	 * @param attributes Attributes of the framework node
	 * @param p          Parent node of the new node
	 * @exception UnsupportedOperationException If there is missing a implementation
	 *                                          for different types of p.
	 * @return New assert stmt
	 */
	private AssertStmt createAssertStmt(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p)
			throws UnsupportedOperationException {
		AssertStmt obj = new AssertStmt();
		// Set the check
		obj.setCheck(attributes.getCheck());
		// Set the message
		obj.setMessage(attributes.getMessage());

		/*
		 * Add node specifically as a child to parent, so it will rendered in the string
		 * output later.
		 */
		if (p instanceof NodeWithStatements) {
			// If parent is a node with statements
			((NodeWithStatements) p).addStatement(obj);
		} else {
			// there is missing a case
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName()
					+ ". Expected: " + NodeWithStatements.class.getSimpleName());
		}

		return obj;
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
	private boolean isOfType(Node compareFrameworkNode, Class javaParserClazz) {
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
	private boolean isOfType(Node compareFrameworkNode, JavaNodeTypes type) {
		return compareFrameworkNode.getNodeType().startsWith(type.name());
	}

	/**
	 * Creates a new {@link ClassOrInterfaceDeclaration}. The attributes for this
	 * node a retrieved from the parameter. At the end the new node is added to its
	 * parent node.
	 * 
	 * @param attributes Attributes of the new node
	 * @param p          Parent node of the new node
	 * @exception UnsupportedOperationException If type of p is not implemented.
	 * @return {@link ClassOrInterfaceDeclaration}
	 */
	private ClassOrInterfaceDeclaration createClassOrInterfaceDeclaration(JavaWriterAttributeCollector attributes,
			com.github.javaparser.ast.Node p) throws UnsupportedOperationException {
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
		} else if (p instanceof NodeWithMembers) {
			// Class in class or something
			((NodeWithMembers) p).addMember(coid);
		} else {
			// If p is not supported, throw exception, to signal missing impl
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName());
		}

		return coid;
	}

	/**
	 * Creates a new {@link AnnotationDeclaration}, sets it's attributes and adds
	 * the new node to the {@link CompilationUnit}.
	 * 
	 * @param attributes Attributes of the new node
	 * @param p          Parent node of the new node
	 * @return new annotation declaration
	 */
	private AnnotationDeclaration createAnnotationDeclaration(JavaWriterAttributeCollector attributes,
			com.github.javaparser.ast.Node p) {
		// Declare the compilation unit
		CompilationUnit cu;
		if (p instanceof CompilationUnit) {
			// Is the parent the compilation unit?
			cu = (CompilationUnit) p;
		} else {
			/*
			 * Otherwise find the compilation unit and get it. There is always a compilation
			 * unit.
			 */
			cu = p.findCompilationUnit().get();
		}
		/*
		 * Create a new intermediate array for the keywords. The constructor of
		 * annotation declaration requires an array, and cannot be used with a list
		 * returned by attributes.
		 */
		Modifier.Keyword[] keywords = new Modifier.Keyword[attributes.getModifier().size()];
		/*
		 * Add the annotation declaration to the compilation unit, with its respective
		 * attributes. This step creates a new annotation declaration.
		 */
		cu.addAnnotationDeclaration(attributes.getName(), attributes.getModifier().toArray(keywords));

		// return the newly created annotation declaration
		return cu.getAnnotationDeclarationByName(attributes.getName()).get();
	}

	/**
	 * Creates a new {@link AnnotationMemberDeclaration}, sets it's attributes and
	 * adds the new node to parental {@link AnnotationDeclaration}.
	 * 
	 * @param attributes Attributes of the annotation member decl
	 * @param p          Parental annotation decl
	 * @return New annotation member decl
	 */
	private AnnotationMemberDeclaration createAnnotationMemberDeclaration(JavaWriterAttributeCollector attributes,
			com.github.javaparser.ast.Node p) {
		// Create new annotation member decl
		AnnotationMemberDeclaration obj = new AnnotationMemberDeclaration();

		// Modifiers
		obj.setModifiers(attributes.getModifier());

		// Type
		obj.setType(attributes.getType());

		// Name
		obj.setName(attributes.getName());

		// DefaultValue
		obj.setDefaultValue(attributes.getValue());

		// Add to parent node
		((AnnotationDeclaration) p).addMember(obj);

		// Return annotation member declaration
		return obj;
	}

	private void processArgument(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p) {
		if (p == null || attributes.getChildren() > 0) {
			// Do nothing, e.g. parent of concrete arg was arg
		} else if (attributes.getType() != null && attributes.getName() != null) {
			// Parameter with type and name and eventually modifiers, e.g. method decl
			Parameter param = new Parameter(attributes.getType(), attributes.getName());
			param.setModifiers(attributes.getModifier());
			((NodeWithParameters) p).addParameter(param);
		} else if (!attributes.getName().isEmpty()) {
			// Argument without type but name, e.g. method call expr
			((NodeWithArguments) p).addArgument(attributes.getName());
		} else if (attributes.getValue() != null) {
			// Some arguments have no name but a value instead
			((NodeWithArguments) p).addArgument(attributes.getValue());
		} else if (attributes.getChildren() == 0) {
			// TODO check if this is dead code
			/*
			 * for (Node nChild : n.getChildren()) { com.github.javaparser.ast.Node jpChild
			 * = visitWriter(nChild, null);
			 * 
			 * if (p instanceof NodeWithArguments && jpChild instanceof Expression) {
			 * ((NodeWithArguments) p).addArgument((Expression) jpChild); } else { throw new
			 * UnsupportedOperationException("p is " + p.getClass().getSimpleName() +
			 * " and jpChild is " + jpChild.getClass().getSimpleName()); } }
			 */
		}
	}

}