
package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import java.lang.Thread.State;
import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.JavaNodeTypes;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.*;
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
		 * new node. Take a look at JavaVisitor for more information about the
		 * conversion.
		 */
		if (n.getNodeType().equals(JavaWriter.NODE_TYPE_TREE)) {
			jpNode = new CompilationUnit();
		} else if (isOfType(n, CompilationUnit.class)) {
			// Add package to CU created previously
			if (p instanceof CompilationUnit && !attributes.getPackage().isEmpty()) {
				((CompilationUnit) p).setPackageDeclaration(attributes.getPackage());
			}

			/*
			 * When the compilation unit is class or interface create a new class or
			 * interface declaration. Otherwise it is an enum declaration.
			 */
			if (!attributes.isEnum()) {
				jpNode = createClassOrInterfaceDeclaration(attributes, p);
			} else {
				jpNode = new EnumDeclaration(attributes.getModifier(), attributes.getName());
			}
		} else if (isOfType(n, AnnotationDeclaration.class)) {
			jpNode = createAnnotationDeclaration(attributes, p);
		} else if (isOfType(n, AnnotationMemberDeclaration.class)) {
			jpNode = new AnnotationMemberDeclaration(attributes.getModifier(), attributes.getAnnotation(),
					attributes.getType(), new SimpleName(attributes.getName()), attributes.getValue());
		} else if (isOfType(n, JavaNodeTypes.Argument)) {
			processArgument(attributes, p, n);
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
				/*
				 * Some conversions might create block stmts themselves but have block stmts as
				 * a child. An example for this behavior is JavaNodeTypes.Else. This check
				 * prevents duplicate block stmts.
				 */
				jpNode = p;
			} else {
				// Otherwise create a new block stmt
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
			jpNode = new ConstructorDeclaration(attributes.getName()).setModifiers(attributes.getModifier())
					.setAnnotations(attributes.getAnnotation());
		} else if (isOfType(n, ContinueStmt.class) || isOfType(n, JavaNodeTypes.Continue)) {
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
			ifStmt.setElseStmt(new BlockStmt());
			p = null;
			jpNode = ifStmt.getElseStmt().get();
		} else if (isOfType(n, EmptyStmt.class)) {
			jpNode = new EmptyStmt();
		} else if (isOfType(n, EnclosedExpr.class)) {
			/*
			 * Only add the enclosed expr to the parent, the inner expr could contain
			 * anything and is therefore set in the child.
			 */
			jpNode = new EnclosedExpr();
		} else if (isOfType(n, EnumConstantDeclaration.class)) {
			jpNode = new EnumConstantDeclaration(attributes.getName());
		} else if (isOfType(n, EnumDeclaration.class)) {
			jpNode = new EnumDeclaration(attributes.getModifier(), attributes.getName());
		} else if (isOfType(n, ExplicitConstructorInvocationStmt.class)) {
			jpNode = new ExplicitConstructorInvocationStmt().setThis(attributes.isThis());
		} else if (isOfType(n, ExpressionStmt.class)) {
			jpNode = new ExpressionStmt();
		} else if (isOfType(n, FieldAccessExpr.class)) {
			jpNode = new FieldAccessExpr();
		} else if (isOfType(n, FieldDeclaration.class)) {
			jpNode = new FieldDeclaration().setModifiers(attributes.getModifier());
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
			// jpNode = new IntersectionType();
		} else if (isOfType(n, LabeledStmt.class)) {
			jpNode = new LabeledStmt().setLabel(new SimpleName(attributes.getName()));
		} else if (isOfType(n, LambdaExpr.class)) {
			/*
			 * In some cases, e.g. binary expr, the lambda expr might contain the attribute
			 * value. In this case the attribute contains the body of the lambda expr.
			 */
			jpNode = new LambdaExpr().setEnclosingParameters(attributes.isEnclosingParameters());
			if (attributes.getValue() != null) {
				((LambdaExpr) jpNode).setBody(new ExpressionStmt(attributes.getValue()));
			}
		} else if (isOfType(n, LineComment.class)) {
			jpNode = new LineComment(attributes.getComment());
		} else if (isOfType(n, LocalClassDeclarationStmt.class)) {
			jpNode = new LocalClassDeclarationStmt();
		} else if (isOfType(n, LongLiteralExpr.class)) {
			jpNode = new LongLiteralExpr();
		} else if (isOfType(n, MarkerAnnotationExpr.class)) {
			jpNode = attributes.getAnnotation().getFirst().get().asMarkerAnnotationExpr();
		} else if (isOfType(n, MemberValuePair.class)) {
			jpNode = new MemberValuePair(attributes.getKey(), attributes.getValue());
		} else if (isOfType(n, MethodCallExpr.class)) {
			jpNode = new MethodCallExpr(attributes.getScope(), attributes.getName());
		} else if (isOfType(n, MethodDeclaration.class)) {
			/*
			 * The bodies of the method declaration are removed by default in this step. If
			 * a method is not abstract the body will be defined in the child nodes
			 * otherwise it stays empty.
			 */
			jpNode = new MethodDeclaration(attributes.getModifier(), attributes.getName(), attributes.getReturnType(),
					new NodeList<Parameter>()).setThrownExceptions(attributes.getThrows())
							.setAnnotations(attributes.getAnnotation()).removeBody();
		} else if (isOfType(n, MethodReferenceExpr.class)) {
			jpNode = new MethodReferenceExpr();
		} else if (isOfType(n, NameExpr.class)) {
			jpNode = new NameExpr();
		} else if (isOfType(n, NormalAnnotationExpr.class)) {
			jpNode = new NormalAnnotationExpr(new Name(attributes.getName()), new NodeList<MemberValuePair>());
		} else if (isOfType(n, NullLiteralExpr.class)) {
			jpNode = new NullLiteralExpr();
		} else if (isOfType(n, ObjectCreationExpr.class)) {
			// Creates a new object creation expr, with scope, type and empty arguments list
			jpNode = new ObjectCreationExpr(attributes.getScope(), attributes.getType().asClassOrInterfaceType(),
					new NodeList<Expression>());
		} else if (isOfType(n, Parameter.class)) {
			Parameter obj = new Parameter(new UnknownType(), attributes.getName()).setModifiers(attributes.getModifier());
			if (attributes.getType() != null) {
				// Replace unknown type if there is a more specific type for the parameter.
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
				// If the switch entry is not "default:" (but "case xxx:") add a condition
				obj.setLabels(attributes.getCondition());
			}
			jpNode = obj;
		} else if (isOfType(n, SwitchExpr.class)) {
			jpNode = new SwitchExpr();
		} else if (isOfType(n, SwitchStmt.class)) {
			jpNode = new SwitchStmt(attributes.getSelector(), new NodeList<SwitchEntry>());
		} else if (isOfType(n, SynchronizedStmt.class) || isOfType(n, JavaNodeTypes.Synchronized)) {
			jpNode = new SynchronizedStmt().setExpression(attributes.getExpression());
		} else if (isOfType(n, TextBlockLiteralExpr.class)) {
			jpNode = new TextBlockLiteralExpr();
		} else if (isOfType(n, JavaNodeTypes.Then)) {
			IfStmt ifStmt = (IfStmt) p; // Parent of then is always if
			int counter = 0; // Flag for has else branch
			while (ifStmt.hasElseBranch()) {
				/*
				 * p is the top most if stmt. Traverse the else branches down to the bottom, to
				 * convert the flat structure back to the deep structure. Also set the flag
				 * "counter".
				 */
				ifStmt = (IfStmt) ifStmt.getElseStmt().get();
				counter++;
			}
			if (counter != 0 || !ifStmt.getCondition().equals(new BooleanLiteralExpr(false))) {
				/*
				 * If we went down the branches (condition 1) or the if stmt if freshly created,
				 * then it's condition is "false" (condition 2). (Condition 2) assumes that
				 * "if(false)" never occurs in a real serious program, as it is just dead code.
				 * If any condition is true, create a new if stmt in the else branch.
				 */
				ifStmt.setElseStmt(new IfStmt());
				ifStmt = (IfStmt) ifStmt.getElseStmt().get();
			}
			ifStmt.setCondition(attributes.getCondition().getFirst().get());
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
			jpNode = new VariableDeclarationExpr().setModifiers(attributes.getModifier()).setAnnotations(attributes.getAnnotation());
		} else if (isOfType(n, VariableDeclarator.class)) {
			VariableDeclarator obj = new VariableDeclarator(attributes.getType(), attributes.getName());
			/*
			 * Variable declarators don't need an initialiations (e.g. they are just
			 * declared). So check if there's any, if so add it.
			 */
			attributes.getInitilization().getFirst().ifPresent(init -> obj.setInitializer(init));
			jpNode = obj;
		} else if (isOfType(n, VarType.class)) {
			jpNode = new VarType();
		} else if (isOfType(n, VoidType.class)) {
			jpNode = new VoidType();
		} else if (isOfType(n, WhileStmt.class)) {
			jpNode = new WhileStmt().setCondition(attributes.getCondition().getFirst().get());
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

		for (Node nChild : n.getChildren()) {
			/*
			 * Visit all children. In nodes with only meta information, jpNode stays null.
			 * When that happens, use p as argument again, so the child nodes of the meta
			 * info node can be directly added to the original parent.
			 */
			com.github.javaparser.ast.Node jpChild = visitWriter(nChild, Optional.ofNullable(jpNode).orElse(p));
		}

		return jpNode;
	}

	/**
	 * This method adds the child to the children of the parent node. The types of
	 * the nodes are also considered to make the changes effective.
	 * 
	 * @param parentNode Parent Node
	 * @param childNode  Child Node
	 * @throws UnsupportedOperationException Implementations of special combinations
	 *                                       of node types might be missing. When
	 *                                       this happens an exception is thrown,
	 *                                       with the specific combination. The
	 *                                       missing implementation must be
	 *                                       implemented, otherwise the results of
	 *                                       the conversion could be unexspected.
	 */
	private void setParent(com.github.javaparser.ast.Node parentNode, com.github.javaparser.ast.Node childNode)
			throws UnsupportedOperationException {
		if (parentNode instanceof com.github.javaparser.ast.Node && childNode instanceof com.github.javaparser.ast.Node
				&& !parentNode.equals(childNode)) {
			/*
			 * Just set the parent of the child node generally. This doesn't seem to affect
			 * anything.
			 */
			childNode.setParentNode(parentNode);

			/*
			 * The real magic is done here. Check their types and add the child
			 * specifically.
			 */
			if (childNode instanceof AnnotationDeclaration) {
				// see createAnnotationDeclaration
			} else if (parentNode instanceof CompilationUnit && childNode instanceof TypeDeclaration) {
				((CompilationUnit) parentNode).addType((TypeDeclaration) childNode);
			} else if (parentNode instanceof EnumDeclaration && childNode instanceof EnumConstantDeclaration) {
				((EnumDeclaration) parentNode).addEntry((EnumConstantDeclaration) childNode);
			} else if (parentNode instanceof SynchronizedStmt && childNode instanceof BlockStmt) {
				((SynchronizedStmt) parentNode).setBody((BlockStmt) childNode);
			} else if (parentNode instanceof LabeledStmt && childNode instanceof Statement) {
				((LabeledStmt) parentNode).setStatement((Statement) childNode);
			} else if (parentNode instanceof NodeWithVariables && childNode instanceof VariableDeclarator) {
				((NodeWithVariables) parentNode).addVariable((VariableDeclarator) childNode);
			} else if (parentNode instanceof LabeledStmt && childNode instanceof Expression) {
				((LabeledStmt) parentNode).setStatement(new ExpressionStmt((Expression) childNode));
			} else if (parentNode instanceof LambdaExpr && childNode instanceof Statement) {
				((LambdaExpr) parentNode).setBody((Statement) childNode);
			} else if (parentNode instanceof LambdaExpr && childNode instanceof Expression) {
				((LambdaExpr) parentNode).setBody(new ExpressionStmt((Expression) childNode));
			} else if (parentNode instanceof TypeDeclaration && childNode instanceof BodyDeclaration) {
				((TypeDeclaration) parentNode).addMember((BodyDeclaration) childNode);
			} else if (parentNode instanceof NodeWithAnnotations && childNode instanceof AnnotationExpr) {
				((NodeWithAnnotations) parentNode).addAnnotation((AnnotationExpr) childNode);
			} else if (parentNode instanceof SwitchStmt && childNode instanceof SwitchEntry) {
				NodeList<SwitchEntry> entries = ((SwitchStmt) parentNode).getEntries();
				entries.add((SwitchEntry) childNode);
				((SwitchStmt) parentNode).setEntries(entries);
			} else if (parentNode instanceof NodeWithTypeParameters && childNode instanceof TypeParameter) {
				((NodeWithTypeParameters) parentNode).addTypeParameter((TypeParameter) childNode);
			} else if (childNode instanceof Comment) {
				parentNode.addOrphanComment((Comment) childNode);
			} else if (parentNode instanceof CatchClause && childNode instanceof Parameter) {
				((CatchClause) parentNode).setParameter((Parameter) childNode);
			} else if (parentNode instanceof NodeWithBody && childNode instanceof Statement) {
				((NodeWithBody) parentNode).setBody((Statement) childNode);
			} else if (parentNode instanceof NodeWithBody && childNode instanceof Expression) {
				((NodeWithBody) parentNode).setBody(new ExpressionStmt((Expression) childNode));
			} else if (parentNode instanceof EnclosedExpr && childNode instanceof Expression) {
				((EnclosedExpr) parentNode).setInner((Expression) childNode);
			} else if (parentNode instanceof NodeWithStatements && childNode instanceof VariableDeclarator) {
				((NodeWithStatements) parentNode)
						.addStatement(new VariableDeclarationExpr((VariableDeclarator) childNode));
			} else if (parentNode instanceof NodeWithStatements && childNode instanceof Statement) {
				((NodeWithStatements) parentNode).addStatement((Statement) childNode);
			} else if (parentNode instanceof NodeWithStatements && childNode instanceof Expression) {
				((NodeWithStatements) parentNode).addStatement((Expression) childNode);
			} else if (parentNode instanceof NodeWithParameters && childNode instanceof Parameter) {
				((NodeWithParameters) parentNode).addParameter((Parameter) childNode);
			} else if (parentNode instanceof NodeWithMembers && childNode instanceof MethodDeclaration) {
				((NodeWithMembers) parentNode).addMember((MethodDeclaration) childNode);
			} else if (parentNode instanceof NormalAnnotationExpr && childNode instanceof MemberValuePair) {
				NodeList<MemberValuePair> paris = ((NormalAnnotationExpr) parentNode).getPairs();
				paris.add((MemberValuePair) childNode);
				((NormalAnnotationExpr) parentNode).setPairs(paris);
			} else if (parentNode instanceof TryStmt && childNode instanceof CatchClause) {
				NodeList<CatchClause> clauses = ((TryStmt) parentNode).getCatchClauses();
				clauses.add((CatchClause) childNode);
				((TryStmt) parentNode).setCatchClauses(clauses);
			} else if (parentNode instanceof NodeWithOptionalBlockStmt && childNode instanceof BlockStmt) {
				((NodeWithOptionalBlockStmt) parentNode).setBody((BlockStmt) childNode);
			} else if (parentNode instanceof NodeWithBlockStmt && childNode instanceof BlockStmt) {
				((NodeWithBlockStmt) parentNode).setBody((BlockStmt) childNode);
			} else if (parentNode instanceof TryStmt && childNode instanceof BlockStmt) {
				((TryStmt) parentNode).setTryBlock((BlockStmt) childNode);
			} else if (parentNode instanceof IfStmt && childNode instanceof IfStmt) {
				// IfStmt logic is done in then and else
			} else if (parentNode instanceof IfStmt && childNode instanceof Statement) {
				((IfStmt) parentNode).setThenStmt((Statement) childNode);
			} else if (parentNode instanceof IfStmt && childNode instanceof Expression) {
				((IfStmt) parentNode).setThenStmt(new ExpressionStmt((Expression) childNode));
			} else if (parentNode instanceof NodeWithArguments && childNode instanceof Expression) {
				// see processArgument
			}

			else {
				throw new UnsupportedOperationException(
						"Parent node is of type " + parentNode.getClass().getSimpleName() + ". Child node is of type "
								+ childNode.getClass().getSimpleName());
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
				 * If name is not empty, then the given attributes can construct a valid import
				 * declaration. If the name is empty, it was the import node with meta info
				 * about the number of children.
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
			// There doesn't seem to be a better way for this
			TypeParameter tp = (TypeParameter) p; // Cast for easier reuse
			NodeList<ClassOrInterfaceType> getTypeBound = tp.getTypeBound(); // Get current bounds
			ClassOrInterfaceType bound = new ClassOrInterfaceType().setName(attributes.getName())
					.setAnnotations(attributes.getAnnotation());
			if (attributes.getTypeParameterBound().size() > 0) {
				bound.setTypeArguments(attributes.getTypeParameterBound());
			}
			getTypeBound.add(bound); // Add bound
			tp.setTypeBound(getTypeBound); // Save changes
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
		if (!attributes.getSuperclass().isEmpty()) {
			// coid extends another class
			coid.addExtendedType(attributes.getSuperclass());
		}
		// coid implements other class
		coid.setImplementedTypes(attributes.getInterface());
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
		CompilationUnit cu;
		if (p instanceof CompilationUnit) {
			// Parent is compilation unit
			cu = (CompilationUnit) p;
		} else {
			/*
			 * Otherwise find the compilation unit and get it. There should always be a
			 * compilation unit.
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
		 * attributes. This step creates a new annotation declaration and also returns
		 * it.
		 */
		return cu.addAnnotationDeclaration(attributes.getName(), attributes.getModifier().toArray(keywords));
	}

	/**
	 * This method processes arguments and adds them to the parent.
	 * 
	 * @param attributes Attributes of the argument
	 * @param p          Parent node, which owns the argument
	 * @param n          Argument node
	 */
	private void processArgument(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p, Node n) {
		if (p == null || attributes.getChildren() > 0) {
			// Do nothing, e.g. parent of concrete arg was arg with meta info
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
		} else {
			for (Node childNode : n.getChildren()) {
				((NodeWithArguments) p).addArgument((Expression) visitWriter(childNode));
			}
		}
	}
}