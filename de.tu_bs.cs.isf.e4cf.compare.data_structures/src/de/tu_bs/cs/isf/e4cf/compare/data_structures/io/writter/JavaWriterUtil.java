
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
			jpNode = new AnnotationMemberDeclaration(attributes.getModifier(), attributes.getAnnotation(),
					attributes.getType(), new SimpleName(attributes.getName()), attributes.getValue());
		} else if (isOfType(n, JavaNodeTypes.Argument)) {
			processArgument(attributes, p);
		} else if (isOfType(n, ArrayAccessExpr.class)) {
			jpNode = new ArrayAccessExpr(StaticJavaParser.parseExpression(attributes.getName()), attributes.getValue());
		} else if (isOfType(n, ArrayCreationExpr.class)) {
			jpNode = new ArrayCreationExpr(attributes.getType());
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
			jpNode = new AssertStmt(attributes.getCheck(), attributes.getMessage());
		} else if (isOfType(n, JavaNodeTypes.Assignment)) {
			jpNode = new AssignExpr(attributes.getTarget(), attributes.getValue(),
					AssignExpr.Operator.valueOf(attributes.getOperator()));
		} else if (isOfType(n, BinaryExpr.class)) {
			jpNode = new BinaryExpr();
		} else if (isOfType(n, BlockComment.class)) {
			jpNode = new BlockComment(attributes.getComment());
		} else if (isOfType(n, BlockStmt.class) || isOfType(n, JavaNodeTypes.Body)) {
			if (p instanceof BlockStmt) {
				jpNode = p;
			} else {
				jpNode = new BlockStmt();
			}
		} else if (isOfType(n, BooleanLiteralExpr.class)) {
			jpNode = new BooleanLiteralExpr(Boolean.valueOf(attributes.getValue().toString()));
		} else if (isOfType(n, JavaNodeTypes.Bound)) {
			processBound(attributes, p);
		} else if (isOfType(n, BreakStmt.class) || isOfType(n, JavaNodeTypes.Break)) {
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
			jpNode = obj;
		} else if (isOfType(n, CastExpr.class)) {
			jpNode = new CastExpr().setType(attributes.getType());
		} else if (isOfType(n, CatchClause.class)) {
			jpNode = new CatchClause();
		} else if (isOfType(n, CharLiteralExpr.class)) {
			jpNode = new CharLiteralExpr(attributes.getValue().toString());
		} else if (isOfType(n, JavaNodeTypes.Class)) {
			jpNode = createClassOrInterfaceDeclaration(attributes, p);
		} else if (isOfType(n, ClassExpr.class)) {
			jpNode = new ClassExpr(attributes.getType());
		} else if (isOfType(n, ConditionalExpr.class)) {
			jpNode = new ConditionalExpr(attributes.getCondition().getFirst().get(), attributes.getThen(),
					attributes.getElse());
		} else if (isOfType(n, ConstructorDeclaration.class)) {
			jpNode = new ConstructorDeclaration().setModifiers(attributes.getModifier()).setName(attributes.getName())
					.setAnnotations(attributes.getAnnotation());
		} else if (isOfType(n, ContinueStmt.class)) {
			ContinueStmt obj = new ContinueStmt();
			/*
			 * If there is an optional label for the continue stmt, then set it; otherwise
			 * remove the default "empty" label.
			 */
			if (attributes.getTarget() != null) {
				obj.setLabel(new SimpleName(attributes.getTarget().toString()));
			} else {
				obj.removeLabel();
			}
			jpNode = obj;
		} else if (isOfType(n, DoStmt.class)) {
			jpNode = new DoStmt().setCondition(attributes.getCondition().getFirst().get());
		} else if (isOfType(n, DoubleLiteralExpr.class)) {
			jpNode = new DoubleLiteralExpr(attributes.getValue().toString());
		} else if (isOfType(n, JavaNodeTypes.Else)) {
			BlockStmt elseStmt = new BlockStmt();
			/*
			 * In the JavaParser if-elif-else branches are nested; in the e4cf framework the
			 * if-elif-else branches are flat. To reconstruct the depth, the else branches
			 * of the superior if stmt must be traversed to the bottom. p is always the
			 * uppermost/ first if case.
			 */
			IfStmt ifStmt = (IfStmt) p;
			while (ifStmt.hasElseBranch()) {
				ifStmt = (IfStmt) ifStmt.getElseStmt().get();
			}
			ifStmt.setElseStmt(elseStmt);
			p = null;
			jpNode = elseStmt;
		} else if (isOfType(n, EmptyStmt.class)) {
			jpNode = new EmptyStmt();
		} else if (isOfType(n, EnclosedExpr.class)) {
			/*
			 * Only add the enclosed expr to the parent, the inner expr could contain
			 * anything and is therefore set in the child.
			 */
			jpNode = new EnclosedExpr();
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
			FieldDeclaration fd = new FieldDeclaration().setModifiers(attributes.getModifier());
			VariableDeclarator vd = new VariableDeclarator().setType(attributes.getType())
					.setName(attributes.getName());
			if (!attributes.getInitilization().isEmpty()) {
				vd.setInitializer(attributes.getInitilization().getFirst().get());
			}

			fd.addVariable(vd);
			jpNode = fd;
		} else if (isOfType(n, ForEachStmt.class)) {
			jpNode = new ForEachStmt().setIterable(attributes.getIterator()).setVariable(new VariableDeclarationExpr(
					attributes.getType(), attributes.getInitilization().getFirst().get().toString()));
		} else if (isOfType(n, ForStmt.class)) {
			jpNode = new ForStmt().setInitialization(attributes.getInitilization())
					.setCompare(attributes.getComparison()).setUpdate(attributes.getUpdate());
		} else if (isOfType(n, IfStmt.class)) {
			// Just add the first if stmt, the logic is in THEN and ELSE.
			jpNode = new IfStmt();
		} else if (isOfType(n, JavaNodeTypes.Import)) {
			processImport(attributes, p);
		} else if (isOfType(n, InitializerDeclaration.class)) {
			jpNode = new InitializerDeclaration();
		} else if (isOfType(n, InstanceOfExpr.class)) {
			jpNode = new InstanceOfExpr();
		} else if (isOfType(n, IntegerLiteralExpr.class)) {
			jpNode = new IntegerLiteralExpr();
		} else if (isOfType(n, JavaNodeTypes.Interface)) {
			jpNode = createClassOrInterfaceDeclaration(attributes, p);
		} else if (isOfType(n, IntersectionType.class)) {
			// TODO fill arguments
			// IntersectionType obj = new IntersectionType();
			// jpNode = obj;
		} else if (isOfType(n, LabeledStmt.class)) {
			jpNode = new LabeledStmt();
		} else if (isOfType(n, LambdaExpr.class)) {
			// Just add a lambda expr, the logic is done in the children.
			jpNode = new LambdaExpr();
		} else if (isOfType(n, LineComment.class)) {
			jpNode = new LineComment(attributes.getComment());
		} else if (isOfType(n, LocalClassDeclarationStmt.class)) {
			jpNode = new LocalClassDeclarationStmt();
		} else if (isOfType(n, LongLiteralExpr.class)) {
			jpNode = new LongLiteralExpr();
		} else if (isOfType(n, MarkerAnnotationExpr.class)) {
			jpNode = new MarkerAnnotationExpr();
		} else if (isOfType(n, MemberValuePair.class)) {
			jpNode = new MemberValuePair(attributes.getKey(), attributes.getValue());
		} else if (isOfType(n, MethodCallExpr.class)) {
			jpNode = new MethodCallExpr(attributes.getScope(), attributes.getName());
		} else if (isOfType(n, MethodDeclaration.class)) {
			jpNode = new MethodDeclaration(attributes.getModifier(), attributes.getName(), attributes.getReturnType(),
					new NodeList<Parameter>()).setThrownExceptions(attributes.getThrows())
							.setAnnotations(attributes.getAnnotation());
		} else if (isOfType(n, MethodReferenceExpr.class)) {
			jpNode = new MethodReferenceExpr();
		} else if (isOfType(n, NameExpr.class)) {
			jpNode = new NameExpr();
		} else if (isOfType(n, NormalAnnotationExpr.class)) {
			// Create a new normal annotation expr, set its name and add it to p.
			jpNode = new NormalAnnotationExpr(new Name(attributes.getName()), new NodeList<MemberValuePair>());
		} else if (isOfType(n, NullLiteralExpr.class)) {
			jpNode = new NullLiteralExpr();
		} else if (isOfType(n, ObjectCreationExpr.class)) {
			jpNode = new ObjectCreationExpr();
		} else if (isOfType(n, Parameter.class)) {
			Parameter obj = new Parameter(new UnknownType(), attributes.getName());
			if (attributes.getType() != null) {
				obj.setType(attributes.getType());
			}
			jpNode = obj;
		} else if (isOfType(n, PrimitiveType.class)) {
			jpNode = new PrimitiveType();
		} else if (isOfType(n, ReceiverParameter.class)) {
			jpNode = new ReceiverParameter();
		} else if (isOfType(n, ReturnStmt.class)) {
			jpNode = new ReturnStmt(attributes.getValue());
		} else if (isOfType(n, SingleMemberAnnotationExpr.class)) {
			jpNode = new SingleMemberAnnotationExpr(new Name(attributes.getName()), attributes.getValue());
		} else if (isOfType(n, StringLiteralExpr.class)) {
			jpNode = new StringLiteralExpr();
		} else if (isOfType(n, SuperExpr.class)) {
			jpNode = new SuperExpr();
		} else if (isOfType(n, SwitchEntry.class)) {
			SwitchEntry obj = new SwitchEntry().setType(SwitchEntry.Type.valueOf(attributes.getType().toString()));
			if (!attributes.isDefault()) {
				obj.setLabels(attributes.getCondition());
			}
			jpNode = obj;
		} else if (isOfType(n, SwitchExpr.class)) {
			jpNode = new SwitchExpr();
		} else if (isOfType(n, SwitchStmt.class)) {
			jpNode = new SwitchStmt(attributes.getSelector(), new NodeList<SwitchEntry>());
		} else if (isOfType(n, SynchronizedStmt.class)) {
			jpNode = new SynchronizedStmt();
		} else if (isOfType(n, TextBlockLiteralExpr.class)) {
			jpNode = new TextBlockLiteralExpr();
		} else if (isOfType(n, JavaNodeTypes.Then)) {
			IfStmt ifStmt = (IfStmt) p;
			int counter = 0;
			while (ifStmt.hasElseBranch()) {
				ifStmt = (IfStmt) ifStmt.getElseStmt().get();
				counter++;
			}
			NodeList<Expression> conditions = attributes.getCondition();
			if (counter != 0 || !ifStmt.getCondition().equals(new BooleanLiteralExpr(false))) {
				ifStmt.setElseStmt(new IfStmt());
				ifStmt = (IfStmt) ifStmt.getElseStmt().get();
			}
			ifStmt.setCondition(conditions.getFirst().get());
			jpNode = ifStmt;
		} else if (isOfType(n, ThisExpr.class)) {
			jpNode = new ThisExpr();
		} else if (isOfType(n, ThrowStmt.class)) {
			jpNode = attributes.getStatement().asThrowStmt();
		} else if (isOfType(n, TryStmt.class)) {
			jpNode = new TryStmt();
		} else if (isOfType(n, TypeExpr.class)) {
			jpNode = new TypeExpr();
		} else if (isOfType(n, TypeParameter.class)) {
			jpNode = new TypeParameter(attributes.getName()).setAnnotations(attributes.getAnnotation());
		} else if (isOfType(n, UnaryExpr.class)) {
			jpNode = new UnaryExpr(attributes.getExpression(), UnaryExpr.Operator.valueOf(attributes.getOperator()));
		} else if (isOfType(n, UnionType.class)) {
			jpNode = new UnionType();
		} else if (isOfType(n, UnknownType.class)) {
			jpNode = new UnknownType();
		} else if (isOfType(n, UnparsableStmt.class)) {
			jpNode = new UnparsableStmt();
		} else if (isOfType(n, VariableDeclarationExpr.class)) {
			jpNode = new VariableDeclarationExpr();
		} else if (isOfType(n, VariableDeclarator.class)) {
			VariableDeclarator obj = new VariableDeclarator(attributes.getType(), attributes.getName());
			if (attributes.getInitilization().isNonEmpty()) {
				obj.setInitializer(attributes.getInitilization().getFirst().get());
			}
			jpNode = obj;
		} else if (isOfType(n, VarType.class)) {
			jpNode = new VarType();
		} else if (isOfType(n, VoidType.class)) {
			jpNode = new VoidType();
		} else if (isOfType(n, WhileStmt.class)) {
			jpNode = new WhileStmt().setCondition(attributes.getValue());
		} else if (isOfType(n, WildcardType.class)) {
			jpNode = new WildcardType();
		} else if (isOfType(n, YieldStmt.class)) {
			jpNode = new YieldStmt();
		}

		// Set general attributes, which could apply to a set of node types here.
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

		setParent(p, jpNode);

		/*
		 * set jpNode as parent to all the nodes, that are generated from the children
		 */
		for (Node nChild : n.getChildren()) {
			com.github.javaparser.ast.Node jpChild = visitWriter(nChild, Optional.ofNullable(jpNode).orElse(p));
		}

		return jpNode;
	}

	private void setParent(com.github.javaparser.ast.Node p, com.github.javaparser.ast.Node jpNode)
			throws UnsupportedOperationException {
		if (p instanceof com.github.javaparser.ast.Node && jpNode instanceof com.github.javaparser.ast.Node
				&& !p.equals(jpNode)) {
			jpNode.setParentNode(p);

			if (p instanceof CompilationUnit) {
				// If p instanceof CompilationUnit -> handled in e4cf Node CompilationUnit If
			} else if (p instanceof TypeDeclaration && jpNode instanceof BodyDeclaration) {
				((TypeDeclaration) p).addMember((BodyDeclaration) jpNode);
			} else if (p instanceof NodeWithAnnotations && jpNode instanceof AnnotationExpr) {
				((NodeWithAnnotations) p).addAnnotation((AnnotationExpr) jpNode);
			} else if (p instanceof SwitchStmt && jpNode instanceof SwitchEntry) {
				NodeList<SwitchEntry> entries = ((SwitchStmt) p).getEntries();
				entries.add((SwitchEntry) jpNode);
				((SwitchStmt) p).setEntries(entries);
			} else if (p instanceof NodeWithTypeParameters && jpNode instanceof TypeParameter) {
				((NodeWithTypeParameters) p).addTypeParameter((TypeParameter) jpNode);
			} else if (jpNode instanceof Comment) {
				p.addOrphanComment((Comment) jpNode);
			} else if (p instanceof CatchClause && jpNode instanceof Parameter) {
				((CatchClause) p).setParameter((Parameter) jpNode);
			} else if (p instanceof NodeWithBody && jpNode instanceof Statement) {
				((NodeWithBody) p).setBody((Statement) jpNode);
			} else if (p instanceof NodeWithBody && jpNode instanceof Expression) {
				((NodeWithBody) p).setBody(new ExpressionStmt((Expression) jpNode));
			} else if (p instanceof EnclosedExpr && jpNode instanceof Expression) {
				((EnclosedExpr) p).setInner((Expression) jpNode);
			} else if (p instanceof NodeWithStatements && jpNode instanceof VariableDeclarator) {
				((NodeWithStatements) p).addStatement(new VariableDeclarationExpr((VariableDeclarator) jpNode));
			} else if (p instanceof NodeWithStatements && jpNode instanceof Statement) {
				((NodeWithStatements) p).addStatement((Statement) jpNode);
			} else if (p instanceof NodeWithStatements && jpNode instanceof Expression) {
				((NodeWithStatements) p).addStatement((Expression) jpNode);
			} else if (p instanceof NodeWithParameters && jpNode instanceof Parameter) {
				((NodeWithParameters) p).addParameter((Parameter) jpNode);
			} else if (p instanceof NodeWithMembers && jpNode instanceof MethodDeclaration) {
				((NodeWithMembers) p).addMember((MethodDeclaration) jpNode);
			} else if (p instanceof NormalAnnotationExpr && jpNode instanceof MemberValuePair) {
				NodeList<MemberValuePair> paris = ((NormalAnnotationExpr) p).getPairs();
				paris.add((MemberValuePair) jpNode);
				((NormalAnnotationExpr) p).setPairs(paris);
			} else if (p instanceof TryStmt && jpNode instanceof CatchClause) {
				NodeList<CatchClause> clauses = ((TryStmt) p).getCatchClauses();
				clauses.add((CatchClause) jpNode);
				((TryStmt) p).setCatchClauses(clauses);
			} else if (p instanceof NodeWithOptionalBlockStmt && jpNode instanceof BlockStmt) {
				((NodeWithOptionalBlockStmt) p).setBody((BlockStmt) jpNode);
			} else if (p instanceof NodeWithBlockStmt && jpNode instanceof BlockStmt) {
				((NodeWithBlockStmt) p).setBody((BlockStmt) jpNode);
			} else if (p instanceof TryStmt && jpNode instanceof BlockStmt) {
				((TryStmt) p).setTryBlock((BlockStmt) jpNode);
			} else if (p instanceof IfStmt && jpNode instanceof IfStmt) {
				// IfStmt logic is in then and else
			} else if (p instanceof IfStmt && jpNode instanceof Statement) {
				((IfStmt) p).setThenStmt((Statement) jpNode);
			} else if (p instanceof IfStmt && jpNode instanceof Expression) {
				((IfStmt) p).setThenStmt(new ExpressionStmt((Expression) jpNode));
			}

			else {
				throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName()
						+ ". Child node is of type " + jpNode.getClass().getSimpleName());
			}

		}
	}

	/**
	 * Adds a new {@link ImportDeclaration} to the {@link CompilationUnit}
	 * 
	 * @param attributes Attributes of the import decl
	 * @param p          any node of the tree
	 */
	private void processImport(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p) {
		/*
		 * If p is not the compilation unit, then find the compilation unit. It is not
		 * checked whetever there is a compilation unit, bc. there should always be a
		 * compilation unit.
		 */
		if (p != null) {
			CompilationUnit cuOpt = p.findCompilationUnit().get();
			if (!attributes.getName().isEmpty()) {
				/*
				 * If name is not empty, then given attributes can construct a valid import
				 * declaration. If the name is empty, it was the import node with meta info
				 * number of children.
				 */
				cuOpt.addImport(
						new ImportDeclaration(attributes.getName(), attributes.isStatic(), attributes.isAsteriks()));
			}
		}
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
			// Add the bound to the current list of bounds
			getTypeBound.add(new ClassOrInterfaceType().setName(attributes.getName())
					.setAnnotations(attributes.getAnnotation()).setTypeArguments(attributes.getTypeParameterBound()));
			// Set the new bound list
			tp.setTypeBound(getTypeBound);
		} else {
			// there is missing a case
			throw new UnsupportedOperationException("Parent node is of type " + p.getClass().getSimpleName());
		}
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
		ClassOrInterfaceDeclaration coid = new ClassOrInterfaceDeclaration(attributes.getModifier(),
				attributes.isInterface(), attributes.getName());
		// Extends a class?
		if (!attributes.getSuperclass().isEmpty()) {
			coid.addExtendedType(attributes.getSuperclass());
		}
		// Implemented types
		coid.setImplementedTypes(attributes.getInterface());
		// Add new node to parent node
		if (p instanceof CompilationUnit) {
			CompilationUnit cu = (CompilationUnit) p;
			cu.addType(coid);

			// Set package of compilation unit
			if (!attributes.getPackage().isEmpty()) {
				cu.setPackageDeclaration(attributes.getPackage());
			}
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

	private void processArgument(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p) {
		if (p == null || attributes.getChildren() > 0) {
			// Do nothing, e.g. parent of concrete arg was arg
		} else if (attributes.getType() != null && attributes.getName() != null) {
			// Parameter with type and name and eventually modifiers, e.g. method decl
			((NodeWithParameters) p).addParameter(
					new Parameter(attributes.getType(), attributes.getName()).setModifiers(attributes.getModifier()));
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