package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.SwitchExpr;
import com.github.javaparser.ast.expr.TextBlockLiteralExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithArguments;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.VoidVisitor;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.factory.JavaReaderUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.factory.NodeFactory;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.templates.AbstractJavaVisitor;

/**
 * Custom visitor class extending {@link VoidVisitor} creating a {@link Node}
 * for each {@link com.github.javaparser.ast.Node} visited.
 * 
 * @see <a href=
 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/visitor/VoidVisitorAdapter.html">Javaparser
 *      Docs - VoidVisitorAdapter</a>
 * 
 * @author Serkan Acar
 * @author Pascal Blum
 * @author Paulo Haas
 * @author Hassan Smaoui
 *
 */
public class JavaVisitor extends AbstractJavaVisitor {
	private NodeFactory factory;

	public JavaVisitor(NodeFactory factory) {
		this.setFactory(factory);
	}

	/**
	 * This method creates a child node {@link JavaNodeTypes#Argument} with the
	 * attribute {@link JavaAttributesTypes#Children} containing the number of
	 * arguments. The new node is added as a child to the parameter framework node.
	 * <p>
	 * For every argument a new node {@link JavaNodeTypes#Argument} with an index is
	 * added to the argument node. The arguments are then visited with the indexed
	 * nodes as their parent. After an argument is visited, it is removed, to not
	 * interfere with the later visiting.
	 * 
	 * @param args   Node with arguments
	 * @param parent Equivalent framework node of n
	 */
	private void visitArguments(@SuppressWarnings("rawtypes") NodeWithArguments args, Node parent) {
		this.factory.attachArguments(args, parent, this);
	}

	/**
	 * Visits the compilation unit and creates a new node of type "CompilationUnit"
	 * ({@link CompilationUnit} as a string).
	 * <p>
	 * "CompilationUnit" node gets a child node for imports with type
	 * {@link JavaNodeTypes#Import} (as string), which has the concrete imports as
	 * child nodes. This node also has an attribute
	 * {@link JavaAttributesTypes#Children} containing the number of concrete
	 * imports (0 if none).
	 * <p>
	 * Each concrete import is visited (see
	 * {@link JavaVisitor#visit(ImportDeclaration, Node)} and removed afterwards.
	 * <p>
	 * Finally the remaining children of the compilation unit are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/CompilationUnit.html">Javaparser
	 *      Docs - CompilationUnit</a>
	 * @param compilationUnit Compilation Unit
	 * @param parent          Parent framework node of the Compilation Unit's
	 *                        framework node.
	 */
	@Override
	public void visit(CompilationUnit compilationUnit, Node parent) {
		visitor(compilationUnit, this.factory.createCompilationUnitNode(compilationUnit, parent, this));
	}

	/**
	 * Visits a method declaration and creates a new node of type
	 * "MethodDeclaration" ({@link MethodDeclaration} as string).
	 * <p>
	 * "MethodDeclaration" gets an attribute of type
	 * {@link JavaAttributesTypes#Throws} for every declared thrown exception. The
	 * thrown exception is removed from the method declaration afterwards.
	 * <p>
	 * "MethodDeclaration" gets a child node for the parameters with type
	 * {@link JavaNodeTypes#Argument} (as string), which has the concrete parameters
	 * as child nodes. This node also has an attribute
	 * {@link JavaAttributesTypes#Children} containing the number of concrete
	 * parameters (0 if none).
	 * <p>
	 * Each concrete parameter gets a distinct node of type
	 * {@link JavaNodeTypes#Argument} with the attributes types
	 * {@link JavaAttributesTypes#Type} containing the parameter's type and an
	 * attributes {@link JavaAttributesTypes#Name} containing the parameter's name.
	 * Then the each modifier of the parameter is visited.
	 * <p>
	 * The name of the return type of the method is written to an attribute
	 * {@link JavaAttributesTypes#ReturnType}.
	 * <p>
	 * Finally the remaining children, except of the return type, of the method
	 * declaration are visited.
	 *
	 * @see JavaVisitor#visit(Modifier, Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/MethodDeclaration.html">Javaparser
	 *      Docs - MethodDeclaration</a>
	 * @param methodDecl Method Declaration
	 * @param parent     Parent framework node of the Method Declaration's framework
	 *                   node.
	 */
	@Override
	public void visit(MethodDeclaration methodDecl, Node parent) {
		visitor(methodDecl, this.factory.createMethodDeclNode(methodDecl, parent, this), methodDecl.getType());
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Modifier} with the modifiers
	 * name to the parent node.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/Modifier.html">Javaparser
	 *      Docs - Modifier</a>
	 * @param n   Modifier that is attributed to the parent
	 * @param arg Parent node getting the attribute
	 */
	@Override
	public void visit(Modifier n, Node arg) {
		if (JavaReaderUtil.isAccsessModifier(n.getKeyword().name())) {
			arg.addAttribute(JavaAttributesTypes.AccessModifier.name(), new StringValueImpl(n.getKeyword().name()));
		} else {
			arg.addAttribute(n.getKeyword().name() + "_" + JavaAttributesTypes.Modifer.name(),
					new StringValueImpl(n.getKeyword().name()));
		}
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Name} with the name to the
	 * parent node.
	 * <p>
	 * If the parent is of type {@link ClassOrInterfaceDeclaration} then nothing is
	 * done, because {@link JavaVisitor#visit(ClassOrInterfaceDeclaration, Node)}
	 * defines special behavior for this case.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SimpleName.html">Javaparser
	 *      Docs - SimpleName</a>
	 * @param n   Name that is attributed to the parent
	 * @param arg Parent node getting the attribute
	 */
	@Override
	public void visit(SimpleName n, Node arg) {
		arg.addAttribute(JavaAttributesTypes.Name.name(), new StringValueImpl(n.toString()));
	}

	/**
	 * Visits a class or interface declaration and only creates a new node iff the
	 * declaration is not nested. The new node is either of type
	 * {@link JavaNodeTypes#Interface} or of type {@link JavaNodeTypes#Class}
	 * depending on {@link ClassOrInterfaceDeclaration#isInterface()}. The following
	 * attributes are added to the parent node otherwise.
	 * <p>
	 * The new node gets an attribute {@link JavaAttributesTypes#IsInterface} with
	 * the value of {@link ClassOrInterfaceDeclaration#isInterface()}.
	 * <p>
	 * The new node gets an attribute {@link JavaAttributesTypes#Name} with the name
	 * of the class/ interface.
	 * <p>
	 * The new node get an attribute {@link JavaAttributesTypes#Superclass} with the
	 * name of the class that is extended. The extended class is removed afterwards.
	 * If there is no class extended, this attribute is omitted.
	 * <p>
	 * The new node get an attribute {@link JavaAttributesTypes#Interface} with the
	 * name of the interface for every interface that is implemented. The
	 * implemented class is removed afterwards. If there is no class implemented,
	 * this attribute is omitted.
	 * <p>
	 * Finally the remaining children are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/ClassOrInterfaceDeclaration.html">Javaparser
	 *      Docs - ClassOrInterfaceDeclaration</a>
	 * @param classOrInterfaceDecl Class Or Interface Declaration
	 * @param parent               Parent framework node of the Class Or Interface
	 *                             Declaration's framework node.
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration classOrInterfaceDecl, Node parent) {
		visitor(classOrInterfaceDecl, this.factory.createClassOrInterfaceNode(classOrInterfaceDecl, parent));
	}

	/**
	 * Visits the children of the class or interface type.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/ClassOrInterfaceType.html">JavaParser
	 *      Docs - ClassOrInterfaceType</a>
	 * @param n   ClassOrInterfaceType
	 * @param arg Parent framework node of the Class Or Interface Type's framework
	 *            node.
	 */
	@Override
	public void visit(ClassOrInterfaceType n, Node arg) {
		visitor(n, arg);
	}

	/**
	 * Creates a new node for the annotation declaration and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/AnnotationDeclaration.html">JavaParser
	 *      Docs - AnnotationDeclaration</a>
	 * @param n   AnnotationDeclaration
	 * @param arg Parent framework node of the AnnotationDeclaration's framework
	 *            node.
	 */
	@Override
	public void visit(AnnotationDeclaration n, Node arg) {
		visitor(n, new NodeImpl(NodeType.ANNOTATION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node for the annotation member declaration and visits it's
	 * children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/AnnotationMemberDeclaration.html">JavaParser
	 *      Docs - AnnotationMemberDeclaration</a>
	 * @param n   AnnotationMemberDeclaration
	 * @param arg Parent framework node of the AnnotationMemberDeclaration's
	 *            framework node.
	 */
	@Override
	public void visit(AnnotationMemberDeclaration n, Node arg) {
		Node annotationMemberDeclaration = new NodeImpl(NodeType.ANNOTATION, n.getClass().getSimpleName(), arg);
		annotationMemberDeclaration.addAttribute(JavaAttributesTypes.Type.name(),
				new StringValueImpl(n.getTypeAsString()));
		visitor(n, annotationMemberDeclaration, n.getType());
	}

	/**
	 * Creates a new node for the array access expression and adds the index as an
	 * attribute {@link JavaAttributesTypes#Value}. Then all children except for the
	 * index are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ArrayAccessExpr.html">JavaParser
	 *      Docs - ArrayAccessExpr</a>
	 * @param n   ArrayAccessExpr
	 * @param arg Parent framework node of the ArrayAccessExpr's framework node.
	 */
	@Override
	public void visit(ArrayAccessExpr n, Node arg) {
		Node arrayAccessExprNode = new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg);
		// arrayAccessExprNode.addAttribute(JavaAttributesTypes.Value.name(), new StringValueImpl(n.getIndex().toString()));
		visitor(n, arrayAccessExprNode/*, n.getIndex()*/);
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the array creation expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ArrayCreationExpr.html">JavaParser
	 *      Docs - ArrayCreationExpr</a>
	 * @param n   ArrayCreationExpr
	 * @param arg Parent framework node of the ArrayCreationExpr's framework node.
	 */
	@Override
	public void visit(ArrayCreationExpr n, Node arg) {
		//arg.addAttribute(JavaAttributesTypes.Value.name(), new StringValueImpl(n.toString()));
		visitor(n, new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName()));
	}

	/**
	 * Creates a new node for the array creation level and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/ArrayCreationLevel.html">JavaParser
	 *      Docs - ArrayCreationLevel</a>
	 * @param n   ArrayCreationLevel
	 * @param arg Parent framework node of the ArrayCreationLevel's framework node.
	 */
	@Override
	public void visit(ArrayCreationLevel n, Node arg) {
		visitor(n, new NodeImpl(NodeType.BLOCK, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the array initializer expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ArrayInitializerExpr.html">JavaParser
	 *      Docs - ArrayInitializerExpr</a>
	 * @param n   ArrayInitializerExpr
	 * @param arg Parent framework node of the ArrayInitializerExpr's framework
	 *            node.
	 */
	@Override
	public void visit(ArrayInitializerExpr n, Node arg) {
		/*arg.addAttribute(JavaAttributesTypes.Value.name(), new StringValueImpl(n.toString())); */
		visitor(n, new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName()));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Type} to the parent node with
	 * the array type as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/ArrayType.html">JavaParser
	 *      Docs - ArrayType</a>
	 * @param n   ArrayInitializerExpr
	 * @param arg Parent framework node of the ArrayType's framework node.
	 */
	@Override
	public void visit(ArrayType n, Node arg) {
		arg.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(n.toString()));
	}

	/**
	 * Creates a new node for the assert statement of type {@link AssertStmt} as a
	 * string and adds the check as an attribute {@link JavaAttributesTypes#Check}.
	 * If a message is present, it is also added as an attribute
	 * {@link JavaAttributesTypes#Message}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/AssertStmt.html">JavaParser
	 *      Docs - AssertStmt</a>
	 * @param n   AssertStmt
	 * @param arg Parent framework node of the AssertStmt's framework node.
	 */
	@Override
	public void visit(AssertStmt n, Node arg) {
		Node assertion = new NodeImpl(NodeType.ASSERTION, n.getClass().getSimpleName(), arg);
		if (n.getMessage().isPresent()) {
			assertion.addAttribute(JavaAttributesTypes.Message.name(),
					new StringValueImpl(n.getMessage().get().toString()));
		}
		visitor(n, assertion);
	}

	/**
	 * Creates a new node for the assign expression of type
	 * {@link JavaNodeTypes#Assignment} as a string and adds the type as an
	 * attribute {@link JavaAttributesTypes#Type}, adds the target of assignment as
	 * an attributes {@link JavaAttributesTypes#Target} and adds the value of the
	 * assignment as an attribute {@link JavaAttributesTypes#Value}.
	 * <p>
	 * The attribute {@link JavaAttributesTypes#Operator} contains the assignment
	 * operation.
	 * 
	 * @see com.github.javaparser.ast.expr.AssignExpr.Operator
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/AssignExpr.html">JavaParser
	 *      Docs - AssignExpr</a>
	 * @param n   AssignExpr
	 * @param arg Parent framework node of the AssignExpr's framework node.
	 */
	@Override
	public void visit(AssignExpr n, Node arg) {
		Node assignment = new NodeImpl(NodeType.ASSIGNMENT, JavaNodeTypes.Assignment.name(), arg);
		assignment.addAttribute(JavaAttributesTypes.Target.name(), new StringValueImpl(n.getTarget().toString()));
		assignment.addAttribute(JavaAttributesTypes.Operator.name(), new StringValueImpl(n.getOperator().name()));
		visitor(n, assignment, n.getTarget());
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the binary expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/BinaryExpr.html">JavaParser
	 *      Docs - BinaryExpr</a>
	 * @param n   BinaryExpr
	 * @param arg Parent framework node of the BinaryExpr's framework node.
	 */
	@Override
	public void visit(BinaryExpr n, Node arg) {
		Node expr = new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg);
		expr.addAttribute(JavaAttributesTypes.Operator.name(), new StringValueImpl(n.getOperator().toString()));
		visitor(n, expr);
	}

	/**
	 * Creates a new node for the block statement of type {@link JavaNodeTypes#Body}
	 * and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/BlockStmt.html">JavaParser
	 *      Docs - BlockStmt</a>
	 * @param n   BlockStmt
	 * @param arg Parent framework node of the BlockStmt's framework node.
	 */
	@Override
	public void visit(BlockStmt n, Node arg) {
		visitor(n, new NodeImpl(NodeType.BLOCK, JavaNodeTypes.Body.name(), arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the boolean literal expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/BooleanLiteralExpr.html">JavaParser
	 *      Docs - BooleanLiteralExpr</a>
	 * @param n   BooleanLiteralExpr
	 * @param arg Parent framework node of the BooleanLiteralExpr's framework node.
	 */
	@Override
	public void visit(BooleanLiteralExpr n, Node arg) {
		createLiteralNode(n, arg, "boolean");
	}

	/**
	 * Creates a new node for the break statement of type
	 * {@link JavaNodeTypes#Break} and adds an attribute
	 * {@link JavaAttributesTypes#Target} if a label is present.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/BreakStmt.html">JavaParser
	 *      Docs - BreakStmt</a>
	 * @param n   BreakStmt
	 * @param arg Parent framework node of the BreakStmt's framework node.
	 */
	@Override
	public void visit(BreakStmt n, Node arg) {
		Node breakNode = new NodeImpl(NodeType.JUMP, JavaNodeTypes.Break.name(), arg);
		if (n.getLabel().isPresent()) {
			breakNode.addAttribute(JavaAttributesTypes.Target.name(), new StringValueImpl(n.getLabel().get().toString()));
		}
	}

	/**
	 * Creates a new node for the cast expression of type {@link JavaNodeTypes#Cast}
	 * and adds an attribute {@link JavaAttributesTypes#Type} of the target type.
	 * Afterwards the expression of is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/CastExpr.html">JavaParser
	 *      Docs - CastExpr</a>
	 * @param n   CastExpr
	 * @param arg Parent framework node of the CastExpr's framework node.
	 */
	@Override
	public void visit(CastExpr n, Node arg) {
		Node node = new NodeImpl(NodeType.CAST, JavaNodeTypes.Cast.name(), arg);
		node.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(n.getTypeAsString()));
		visitor(n, node);
	}

	/**
	 * Creates a new node for the catch clause of type {@link CatchClause} and
	 * visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/CatchClause.html">JavaParser
	 *      Docs - CatchClause</a>
	 * @param n   CatchClause
	 * @param arg Parent framework node of the CatchClause's framework node.
	 */
	@Override
	public void visit(CatchClause n, Node arg) {
		visitor(n, new NodeImpl(NodeType.CATCH, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the char literal expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/CharLiteralExpr.html">JavaParser
	 *      Docs - CharLiteralExpr</a>
	 * @param n   CharLiteralExpr
	 * @param arg Parent framework node of the CharLiteralExpr's framework node.
	 */
	@Override
	public void visit(CharLiteralExpr n, Node arg) {
		createLiteralNode(n, arg, "char");
	}

	/**
	 * creates a new node of type {@link ClassExpr} and adds the class expr as a
	 * string as attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ClassExpr.html">JavaParser
	 *      Docs - ClassExpr</a>
	 * @param n   ClassExpr
	 * @param arg Parent framework node of the ClassExpr's framework node.
	 */
	@Override
	public void visit(ClassExpr n, Node arg) {
		createNodeWithValue(n, arg, NodeType.CLASS);
	}

	/**
	 * Creates a new node for the conditional expression of type
	 * {@link ConditionalExpr} (aka Ternary) and adds an attribute
	 * {@link JavaAttributesTypes#Condition} for the condition, adds an attribute
	 * {@link JavaAttributesTypes#Then} for the then expr and adds an attribute
	 * {@link JavaAttributesTypes#Else} for the else expr.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ConditionalExpr.html">JavaParser
	 *      Docs - ConditionalExpr</a>
	 * @param n   ConditionalExpr
	 * @param arg Parent framework node of the ConditionalExpr's framework node.
	 */
	@Override
	public void visit(ConditionalExpr n, Node arg) {
		Node p = new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg);
		p.addAttribute(JavaAttributesTypes.Then.name(), new StringValueImpl(n.getThenExpr().toString()));
		p.addAttribute(JavaAttributesTypes.Else.name(), new StringValueImpl(n.getElseExpr().toString()));
		visitor(n, p);
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Package} to the parent node with
	 * the name of the package declaration.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/PackageDeclaration.html">JavaParser
	 *      Docs - PackageDeclaration</a>
	 * @param n   PackageDeclaration
	 * @param arg Parent framework node of the PackageDeclaration's framework node.
	 */
	@Override
	public void visit(PackageDeclaration n, Node arg) {
		arg.addAttribute(JavaAttributesTypes.Package.name(), new StringValueImpl(n.getNameAsString()));
	}

	/**
	 * Creates a new node for the cast expression of type
	 * {@link JavaNodeTypes#Import} and adds an attribute
	 * {@link JavaAttributesTypes#Asterisks} indicating whetever the import
	 * declaration ends with an asteriks, adds an attribute
	 * {@link JavaAttributesTypes#Static} showing if the import declaration is
	 * static and adds an attribute {@link JavaAttributesTypes#Name} with the name
	 * of the import declaration.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/ImportDeclaration.html">JavaParser
	 *      Docs - ImportDeclaration</a>
	 * @param importDecl ImportDeclaration
	 * @param parent     Parent framework node of the ImportDeclaration's framework
	 *                   node.
	 */
	@Override
	public void visit(ImportDeclaration importDecl, Node parent) {
		factory.attachImportDecl(importDecl, parent, this);
	}

	/**
	 * Adds the attributes {@link JavaAttributesTypes#Scope} and
	 * {@link JavaAttributesTypes#Name} to the parent node with the scope & name of
	 * the field access expr.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/FieldAccessExpr.html">JavaParser
	 *      Docs - FieldAccessExpr</a>
	 * @param n   FieldAccessExpr
	 * @param arg Parent framework node of the FieldAccessExpr's framework node.
	 */
	@Override
	public void visit(FieldAccessExpr n, Node arg) {
		Node accessNode = new NodeImpl(NodeType.REFERENCE, n.getClass().getSimpleName(), arg);
		accessNode.addAttribute(JavaAttributesTypes.Scope.name(), new StringValueImpl(n.getScope().toString()));
		visitor(n, accessNode);
	}

	/**
	 * Creates a new node for the field declaration of type {@link FieldDeclaration}
	 * and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/FieldDeclaration.html">JavaParser
	 *      Docs - FieldDeclaration</a>
	 * @param n   FieldDeclaration
	 * @param arg Parent framework node of the FieldDeclaration's framework node.
	 */
	@Override
	public void visit(FieldDeclaration n, Node arg) {
		visitor(n, new NodeImpl(NodeType.FIELD_DECLARATION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node for the lambda expression of type {@link LambdaExpr} and
	 * adds an attribute {@link JavaAttributesTypes#isEnclosingParameters}, which
	 * contains <code>true</code> when the parameter of the lambda expr are enclosed
	 * and <code>false</code> otherwise.
	 * <p>
	 * Then all children of the lambda expr are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/LambdaExpr.html">JavaParser
	 *      Docs - LambdaExpr</a>
	 * @param n   LambdaExpr
	 * @param arg Parent framework node of the LambdaExpr's framework node.
	 */
	@Override
	public void visit(LambdaExpr n, Node arg) {
		Node lambdaExprNode = new NodeImpl(NodeType.LAMBDA_EXPRESSION, n.getClass().getSimpleName(), arg);
		lambdaExprNode.addAttribute(JavaAttributesTypes.isEnclosingParameters.name(),
				new StringValueImpl(String.valueOf(n.isEnclosingParameters())));
		visitor(n, lambdaExprNode);
	}

	/**
	 * Creates a new node for the local class declaration stmt of type
	 * {@link LocalClassDeclarationStmt} and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/LocalClassDeclarationStmt.html">JavaParser
	 *      Docs - LocalClassDeclarationStmt</a>
	 * @param n   LocalClassDeclarationStmt
	 * @param arg Parent framework node of the LocalClassDeclarationStmt's framework
	 *            node.
	 */
	@Override
	public void visit(LocalClassDeclarationStmt n, Node arg) {
		visitor(n, new NodeImpl(NodeType.CLASS, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Adds the attributes {@link JavaAttributesTypes#Value} to the parent node with
	 * the long literal expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/LongLiteralExpr.html">JavaParser
	 *      Docs - LongLiteralExpr</a>
	 * @param n   LongLiteralExpr
	 * @param arg Parent framework node of the LongLiteralExpr's framework node.
	 */
	@Override
	public void visit(LongLiteralExpr n, Node arg) {
		createLiteralNode(n, arg, "long");
	}

	/**
	 * Adds the attributes {@link JavaAttributesTypes#Annotation} to the parent node
	 * with the marker annotation expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/MarkerAnnotationExpr.html">JavaParser
	 *      Docs - MarkerAnnotationExpr</a>
	 * @param n   MarkerAnnotationExpr
	 * @param arg Parent framework node of the MarkerAnnotationExpr's framework
	 *            node.
	 */
	@Override
	public void visit(MarkerAnnotationExpr n, Node arg) {
		arg.addAttribute(JavaAttributesTypes.Annotation.name(), new StringValueImpl(n.getNameAsString()));
	}

	/**
	 * Creates a new node for the member value pair of type {@link MemberValuePair}
	 * and adds an attribute {@link JavaAttributesTypes#Key} with the member value
	 * pair's name and {@link JavaAttributesTypes#Value} with member value pair's
	 * value.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/MemberValuePair.html">JavaParser
	 *      Docs - MemberValuePair</a>
	 * @param n   MemberValuePair
	 * @param arg Parent framework node of the MemberValuePair's framework node.
	 */
	@Override
	public void visit(MemberValuePair n, Node arg) {
		Node c = new NodeImpl(NodeType.ASSIGNMENT, n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaAttributesTypes.Key.name(), new StringValueImpl(n.getNameAsString()));
		c.addAttribute(JavaAttributesTypes.Value.name(), new StringValueImpl(n.getValue().toString()));
	}

	/**
	 * Creates a new node for the method call expr of type {@link MethodCallExpr}
	 * and adds an attribute {@link JavaAttributesTypes#Scope} is the scope is
	 * present, otherwise this attribute is omitted. If type arguments are present,
	 * then for every type argument an attribute
	 * {@link JavaAttributesTypes#TypeArgument} is added. An attribute
	 * {@link JavaAttributesTypes#Name} containing the called methods name is always
	 * added.
	 * <p>
	 * For the arguments a new child node {@link JavaNodeTypes#Argument} is added to
	 * the created node with the attribute {@link JavaAttributesTypes#Children}
	 * which indicates how many arguments the method call expr has. Then for every
	 * argument a new node of type {@link JavaNodeTypes#Argument} with an index is
	 * created. The arguments are then visited with this new node as their parent.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/MethodCallExpr.html">JavaParser
	 *      Docs - MethodCallExpr</a>
	 * @param n   MethodCallExpr
	 * @param arg Parent framework node of the MethodCallExpr's framework node.
	 */
	@Override
	public void visit(MethodCallExpr n, Node arg) {
		Node c = new NodeImpl(NodeType.METHOD_CALL, n.getClass().getSimpleName(), arg);

		// Scope
		if (n.getScope().isPresent()) {
			c.addAttribute(JavaAttributesTypes.Scope.name(), new StringValueImpl(n.getScope().get().toString()));
		}

		// TypeArguments
		if (n.getTypeArguments().isPresent()) {
			for (Type typeArgumentExpr : n.getTypeArguments().get()) {
				c.addAttribute(JavaAttributesTypes.TypeArgument.name(), new StringValueImpl(typeArgumentExpr.toString()));
			}
		}

		n.getName().accept(this, c);

		visitArguments(n, c);
	}

	/**
	 * Creates a new node for the method reference expr of type
	 * {@link MethodReferenceExpr}.
	 * <p>
	 * This node has an attribute {@link JavaAttributesTypes#Identifier} containing
	 * the identifier of the method reference expression and an attribute
	 * {@link JavaAttributesTypes#Scope} with the scope of the method reference
	 * expr.
	 * <p>
	 * If type arguments are present then for every type argument an attribute
	 * {@link JavaAttributesTypes#Type} is added.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/MethodReferenceExpr.html">JavaParser
	 *      Docs - MethodReferenceExpr</a>
	 * @param n   MethodReferenceExpr
	 * @param arg Parent framework node of the MethodReferenceExpr's framework node.
	 */
	@Override
	public void visit(MethodReferenceExpr n, Node arg) {
		Node c = new NodeImpl(NodeType.REFERENCE, n.getClass().getSimpleName(), arg);

		// Identifier
		c.addAttribute(JavaAttributesTypes.Identifier.name(), new StringValueImpl(n.getIdentifier()));

		// Scope
		c.addAttribute(JavaAttributesTypes.Scope.name(), new StringValueImpl(n.getScope().toString()));

		// Type
		if (n.getTypeArguments().isPresent()) {
			n.getTypeArguments().get()
					.forEach(typeArg -> c.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(typeArg.toString())));
		}

	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Name} to the parent node with
	 * the name as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/Name.html">JavaParser
	 *      Docs - Name</a>
	 * @param n   Name
	 * @param arg Parent framework node of the Name's framework node.
	 */
	@Override
	public void visit(Name n, Node arg) {
		arg.addAttribute(JavaAttributesTypes.Name.name(), new StringValueImpl(n.toString()));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Name} to the parent node with
	 * the name expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/NameExpr.html">JavaParser
	 *      Docs - NameExpr</a>
	 * @param n   NameExpr
	 * @param arg Parent framework node of the NameExpr's framework node.
	 */
	@Override
	public void visit(NameExpr n, Node arg) {
		visitor(n, new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node for the normal annotation expr of type
	 * {@link NormalAnnotationExpr} and visits it's children.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/NormalAnnotationExpr.html">JavaParser
	 *      Docs - NormalAnnotationExpr</a>
	 * @param n   NormalAnnotationExpr
	 * @param arg Parent framework node of the NormalAnnotationExpr's framework
	 *            node.
	 */
	@Override
	public void visit(NormalAnnotationExpr n, Node arg) {
		visitor(n, new NodeImpl(NodeType.ANNOTATION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} to the parent node with
	 * the null literal expr as a string.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/NullLiteralExpr.html">JavaParser
	 *      Docs - NullLiteralExpr</a>
	 * @param n   NullLiteralExpr
	 * @param arg Parent framework node of the NullLiteralExpr's framework node.
	 */
	@Override
	public void visit(NullLiteralExpr n, Node arg) {
		createLiteralNode(n, arg, "null");
	}

	/**
	 * Creates a new node for the object creation expr of type
	 * {@link ObjectCreationExpr}.
	 * <p>
	 * This node has an attribute {@link JavaAttributesTypes#Type} containing the
	 * type, which should be instantiated.
	 * <p>
	 * If the object creation expr has a scope, then the scope is added as an
	 * attribute {@link JavaAttributesTypes#Scope}.
	 * <p>
	 * This node gets a child node {@link JavaNodeTypes#Argument} with the attribute
	 * {@link JavaAttributesTypes#Children} containing the number of children. For
	 * every argument a new node {@link JavaNodeTypes#Argument} with an index is
	 * added to the argument node. The arguments are then visited with the indexed
	 * nodes as their parent. After an argument is visited, it is removed, to not
	 * interfere with the later visiting.
	 * <p>
	 * If an anonymous class body is present an child node, then every
	 * {@link BodyDeclaration} of the body is visited and added to the object
	 * creation node as a child node. After a body decl was visited, it is removed.
	 * <p>
	 * At the end the remaining child nodes of the object creation expr are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ObjectCreationExpr.html">JavaParser
	 *      Docs - ObjectCreationExpr</a>
	 * @param n   ObjectCreationExpr
	 * @param arg Parent framework node of the ObjectCreationExpr's framework node.
	 */
	@Override
	public void visit(ObjectCreationExpr n, Node arg) {
		Node c = new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg);

		// Type
		c.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(n.getTypeAsString()));

		// Scope
		n.getScope().ifPresent(scope -> c.addAttribute(JavaAttributesTypes.Scope.name(), new StringValueImpl(scope.toString())));

		visitArguments(n, c);

		// Anonymous Class Body
		if (n.getAnonymousClassBody().isPresent()) {
			NodeList<BodyDeclaration<?>> anonymousClassBodyList = n.getAnonymousClassBody().get();
			int anonymousClassBodySize = anonymousClassBodyList.size();
			for (int i = 0; i < anonymousClassBodySize; i++) {
				BodyDeclaration<?> anonClassBody = anonymousClassBodyList.get(0);
				anonClassBody.accept(this, c);
				anonClassBody.removeForced();
			}
		}

		visitor(n, c, n.getType(), n.getScope().orElse(null));
	}

	/**
	 * Creates a new node for the parameter of type {@link Parameter} and adds the
	 * parameter's type as an attribute {@link JavaAttributesTypes#Type}.
	 * <p>
	 * If the parameter is has no type, then the attribute is omitted.
	 * <p>
	 * Then visits all children except the type of the parameter.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/Parameter.html">JavaParser
	 *      Docs - Parameter</a>
	 * @param n   Parameter
	 * @param arg Parent framework node of the Parameter's framework node.
	 */
	@Override
	public void visit(Parameter n, Node arg) {
		Node c = new NodeImpl(NodeType.ARGUMENT, n.getClass().getSimpleName(), arg);
		if (!n.getTypeAsString().isEmpty()) {
			c.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(n.getTypeAsString()));
		}
		visitor(n, c, n.getType());
	}

	/**
	 * Adds the primitive type as a string to the parent node as an attribute
	 * {@link JavaAttributesTypes#Type}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/PrimitiveType.html">JavaParser
	 *      Docs - PrimitiveType</a>
	 * @param n   PrimitiveType
	 * @param arg Parent framework node of the PrimitiveType's framework node.
	 */
	@Override
	public void visit(PrimitiveType n, Node arg) {
		arg.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(n.toString()));
	}

	/**
	 * Creates a new node for the receiver parameter. The type of the created node
	 * is {@link ReceiverParameter} and gets an attribute
	 * {@link JavaAttributesTypes#Type} with the type of the receiver parameter.
	 * Afterwards all children of the receiver parameter are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/ReceiverParameter.html">JavaParser
	 *      Docs - ReceiverParameter</a>
	 * @param n   ReceiverParameter
	 * @param arg Parent framework node of the ReceiverParameter's framework node.
	 */
	@Override
	public void visit(ReceiverParameter n, Node arg) {
		Node c = new NodeImpl(NodeType.ARGUMENT, n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(n.getType().toString()));
		visitor(n, c);
	}

	/**
	 * Creates a new node for the return statement with the node type
	 * {@link ReturnStmt} and visits all children of the return statement.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ReturnStmt.html">JavaParser
	 *      Docs - ReturnStmt</a>
	 * @param n   ReturnStmt
	 * @param arg Parent framework node of the ReturnStmt's framework node.
	 */
	@Override
	public void visit(ReturnStmt n, Node arg) {
		Node c = new NodeImpl(NodeType.JUMP, n.getClass().getSimpleName(), arg);
		n.getExpression().ifPresent(expr -> visitor(n, c));
	}

	/**
	 * Creates a new node for the single member annotation expression with the node
	 * type {@link SingleMemberAnnotationExpr} and adds it's name and member value
	 * as attributes {@link JavaAttributesTypes#Name} and
	 * {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SingleMemberAnnotationExpr.html">JavaParser
	 *      Docs - SingleMemberAnnotationExpr</a>
	 * @param n   SingleMemberAnnotationExpr
	 * @param arg Parent framework node of the SingleMemberAnnotationExpr's
	 *            framework node.
	 */
	@Override
	public void visit(SingleMemberAnnotationExpr n, Node arg) {
		Node c = new NodeImpl(NodeType.ANNOTATION, n.getClass().getSimpleName(), arg);
		n.getName().accept(this, c);
		c.addAttribute(JavaAttributesTypes.Value.name(), new StringValueImpl(n.getMemberValue().toString()));
	}

	/**
	 * Adds the string literal expression as a string to the parent node as an
	 * attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/StringLiteralExpr.html">JavaParser
	 *      Docs - StringLiteralExpr</a>
	 * @param n   StringLiteralExpr
	 * @param arg Parent framework node of the StringLiteralExpr's framework node.
	 */
	@Override
	public void visit(StringLiteralExpr n, Node arg) {
		createLiteralNode(n, arg, "String");
	}

	/**
	 * Adds the super expression as a string to the parent node as an attribute
	 * {@link JavaAttributesTypes#SuperExpr}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SuperExpr.html">JavaParser
	 *      Docs - SuperExpr</a>
	 * @param n   SuperExpr
	 * @param arg Parent framework node of the SuperExpr's framework node.
	 */
	@Override
	public void visit(SuperExpr n, Node arg) {
		visitor(n, new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node for the switch entry of type {@link SwitchEntry}.
	 * <p>
	 * For each label this node gets an attribute
	 * {@link JavaAttributesTypes#Condition}. If no label is present, then the
	 * switch entry is the default entry and there gets an attribute
	 * {@link JavaAttributesTypes#Default} which always yields the value
	 * <code>true</code>.
	 * <p>
	 * The new node gets an attributes {@link JavaAttributesTypes#Type} which
	 * contains the type of the switch entry.
	 * <p>
	 * Finally every statement of the entry is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SwitchEntry.html">JavaParser
	 *      Docs - SwitchEntry</a>
	 * @param n   SwitchEntry
	 * @param arg Parent framework node of the SwitchEntry's framework node.
	 */
	@Override
	public void visit(SwitchEntry n, Node arg) {
		Node parent = new NodeImpl(NodeType.CASE, n.getClass().getSimpleName(), arg);

		// Label
		n.getLabels().forEach(label -> parent.addAttribute(JavaAttributesTypes.Condition.name(),new StringValueImpl(label.toString())));
		if (n.getLabels().size() == 0) {
			parent.addAttribute(JavaAttributesTypes.Default.name(), new StringValueImpl(String.valueOf(true)));
		}

		// Type
		parent.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(n.getType().name()));

		// Statements
		n.getStatements().forEach(stmt -> stmt.accept(this, parent));
	}

	/**
	 * Creates a new node for the switch expression of type {@link SwitchExpr}.
	 * <p>
	 * This node gets an attribute {@link JavaAttributesTypes#Selector} which
	 * contains the selector.
	 * <p>
	 * Then each entry of the expression is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/SwitchExpr.html">JavaParser
	 *      Docs - SwitchExpr</a>
	 * @param n   SwitchExpr
	 * @param arg Parent framework node of the SwitchExpr's framework node.
	 */
	@Override
	public void visit(SwitchExpr n, Node arg) {
		// NOTE: Switch Expressions are currently unsupported in javaparser
		Node parent = new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg);
		parent.addAttribute(JavaAttributesTypes.Selector.name(), new StringValueImpl(n.getSelector().toString()));
		n.getEntries().forEach(entry -> entry.accept(this, parent));
	}

	/**
	 * Creates a new node for the switch statement of type {@link SwitchStmt}.
	 * <p>
	 * This node gets an attribute {@link JavaAttributesTypes#Selector} which
	 * contains the selector.
	 * <p>
	 * Then each entry of the statement is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SwitchStmt.html">JavaParser
	 *      Docs - SwitchStmt</a>
	 * @param n   SwitchStmt
	 * @param arg Parent framework node of the SwitchStmt's framework node.
	 */
	@Override
	public void visit(SwitchStmt n, Node arg) {
		Node parent = new NodeImpl(NodeType.SWITCH, n.getClass().getSimpleName(), arg);
		parent.addAttribute(JavaAttributesTypes.Selector.name(), new StringValueImpl(n.getSelector().toString()));
		n.getEntries().forEach(entry -> entry.accept(this, parent));
	}

	/**
	 * Creates a new node for the synchronized statement of type
	 * {@link JavaNodeTypes#Synchronized}.
	 * <p>
	 * This node gets an attribute {@link JavaAttributesTypes#Expression} which
	 * contains the expression of the statement.
	 * <p>
	 * Then the body of the synchronized statement is visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/SynchronizedStmt.html">JavaParser
	 *      Docs - SynchronizedStmt</a>
	 * @param n   SynchronizedStmt
	 * @param arg Parent framework node of the SynchronizedStmt's framework node.
	 */
	@Override
	public void visit(SynchronizedStmt n, Node arg) {
		Node sync = new NodeImpl(NodeType.SYNCHRONIZE, JavaNodeTypes.Synchronized.name(), arg);
		sync.addAttribute(JavaAttributesTypes.Expression.name(), new StringValueImpl(n.getExpression().toString()));
		n.getBody().accept(this, sync);
	}

	/**
	 * Creates a new node of type {@link TextLiteralExpr} and adds the text literal
	 * expr as a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/TextBlockLiteralExpr.html">JavaParser
	 *      Docs - TextBlockLiteralExpr</a>
	 * @param n   TextBlockLiteralExpr
	 * @param arg Parent framework node of the TextBlockLiteralExpr's framework
	 *            node.
	 */
	@Override
	public void visit(TextBlockLiteralExpr n, Node arg) {
		createLiteralNode(n, arg, "String");
	}

	/**
	 * Creates a new node of type {@link ThisExpr} and adds the this expr as a
	 * string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/ThisExpr.html">JavaParser
	 *      Docs - ThisExpr</a>
	 * @param n   ThisExpr
	 * @param arg Parent framework node of the ThisExpr's framework node.
	 */
	@Override
	public void visit(ThisExpr n, Node arg) {
		createNodeWithValue(n, arg, NodeType.REFERENCE);
	}

	/**
	 * Creates a new node of type {@link ThrowStmt} and adds the throw statement as
	 * a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ThrowStmt.html">JavaParser
	 *      Docs - ThrowStmt</a>
	 * @param n   ThrowStmt
	 * @param arg Parent framework node of the ThrowStmt's framework node.
	 */
	@Override
	public void visit(ThrowStmt n, Node arg) {
		Node throwStmt = new NodeImpl(NodeType.THROW, n.getClass().getSimpleName(), arg);
		throwStmt.addAttribute(JavaAttributesTypes.Statement.name(), new StringValueImpl(n.toString()));
	}

	/**
	 * Creates a new node of type {@link TryStmt}.
	 * <p>
	 * If a finally block is present, then a node of type
	 * {@link JavaNodeTypes#Finally} is added to the try stmt. The finally block
	 * then accepts this visitor with the finally node as an argument. A finally
	 * block is always a {@link BlockStmt}.
	 * <p>
	 * For each present expression of the resources of the try stmt an attribute
	 * {@link JavaAttributesTypes#Resource} is added to the new node with the expr
	 * as a string as value. The expression is then removed from the resource, to
	 * not interfere with the later visiting.
	 * <p>
	 * Then the children of the try stmt are visited (except for the finally block).
	 * 
	 * @see JavaVisitor#visit(BlockStmt, Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/TryStmt.html">JavaParser
	 *      Docs - TryStmt</a>
	 * @param n   TryStmt
	 * @param arg Parent framework node of the TryStmt's framework node.
	 */
	@Override
	public void visit(TryStmt n, Node arg) {
		Node tryStmtNode = new NodeImpl(NodeType.TRY, n.getClass().getSimpleName(), arg);

		// Finally Block
		if (n.getFinallyBlock().isPresent()) {
			Node finallyBlockNode = new NodeImpl(NodeType.FINALLY, JavaNodeTypes.Finally.name(), tryStmtNode);
			n.getFinallyBlock().get().accept(this, finallyBlockNode);
		}

		// Resources
		int resourcesSize = n.getResources().size();
		for (int i = 0; i < resourcesSize; i++) {
			Expression expr = n.getResources().get(0);
			tryStmtNode.addAttribute(JavaAttributesTypes.Resource.name(), new StringValueImpl(expr.toString()));
			expr.removeForced();
		}

		visitor(n, tryStmtNode, n.getFinallyBlock().orElse(null));
	}

	/**
	 * Creates a new node of type {@link TypeExpr} and adds the type expression as a
	 * string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/TypeExpr.html">JavaParser
	 *      Docs - TypeExpr</a>
	 * @param n   TypeExpr
	 * @param arg Parent framework node of the TypeExpr's framework node.
	 */
	@Override
	public void visit(TypeExpr n, Node arg) {
		createNodeWithValue(n, arg, NodeType.REFERENCE);
	}

	/**
	 * Creates a new node of type {@link TypeParameter}.
	 * <p>
	 * The name of the {@link TypeParameter} is added as an attribute
	 * {@link JavaAttributesTypes#Name} to the new node.
	 * <p>
	 * For every annotation ({@link TypeParameter#getAnnotations()}) an attribute
	 * {@link JavaAttributesTypes#Annotation} with the annotation's name as a value
	 * is added.
	 * <p>
	 * The {@link TypeParameter}'s bounds ({@link TypeParameter#getTypeBound()} are
	 * added as a child node to the new node. The bound nodes are of type
	 * {@link JavaNodeTypes#Bound} with an index. The bounds are then visited with
	 * their respective node as the framework's parent node. Finally an attribute
	 * {@link JavaAttributesTypes#Bound} with the number bounds is added to the
	 * {@link TypeParameters}'s node.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/TypeParameter.html">JavaParser
	 *      Docs - TypeParameter</a>
	 * @param n   TypeParameter
	 * @param arg Parent framework node of the TypeParameter's framework node.
	 */
	@Override
	public void visit(TypeParameter n, Node arg) {
		Node p = new NodeImpl(NodeType.TEMPLATE, n.getClass().getSimpleName(), arg);

		// Name
		n.getName().accept(this, p);

		// Annotations
		for (AnnotationExpr annotationExpr : n.getAnnotations()) {
			p.addAttribute(JavaAttributesTypes.Annotation.name(), new StringValueImpl(annotationExpr.getNameAsString()));
		}

		// Bounds
		int boundCounter = 0;
		for (ClassOrInterfaceType bound : n.getTypeBound()) {
			Node boundNode = new NodeImpl(NodeType.ARGUMENT, JavaNodeTypes.Bound.name() + boundCounter++, p);
			bound.getAnnotations().forEach(ad -> ad.accept(this, boundNode));
			bound.getName().accept(this, boundNode);
			bound.getTypeArguments().ifPresent(args -> args.forEach(
					targ -> boundNode.addAttribute(JavaAttributesTypes.TypeParameterBound.name(), new StringValueImpl(targ.toString()))));
		}
		p.addAttribute(JavaAttributesTypes.Bound.name(), new StringValueImpl(String.valueOf(n.getTypeBound().size())));
	}

	/**
	 * Creates a new node of type {@link UnaryExpr} and adds the unary expression as
	 * a string as an attribute {@link JavaAttributesTypes#Value}.
	 * <p>
	 * In the attribute {@link JavaAttributesTypes#Operator} the name of the unary
	 * operation is saved.
	 * 
	 * @see com.github.javaparser.ast.expr.UnaryExpr.Operator
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/UnaryExpr.html">JavaParser
	 *      Docs - UnaryExpr</a>
	 * @param n   UnaryExpr
	 * @param arg Parent framework node of the UnaryExpr's framework node.
	 */
	@Override
	public void visit(UnaryExpr n, Node arg) {
		Node unaryExprNode = new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg);
		unaryExprNode.addAttribute(JavaAttributesTypes.Operator.name(), new StringValueImpl(n.getOperator().name()));
		visitor(n, unaryExprNode);
	}

	/**
	 * Creates a new node with type {@link UnionType} and adds an attribute
	 * {@link JavaAttributesTypes#Type} for every element in
	 * {@link UnionType#getElements()}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/UnionType.html">JavaParser
	 *      Docs - UnionType</a>
	 * @param n   UnionType
	 * @param arg Parent framework node of the UnionType's framework node.
	 */
	@Override
	public void visit(UnionType n, Node arg) {
		Node unionTypeNode = new NodeImpl(NodeType.UNION, n.getClass().getSimpleName(), arg);
		n.getElements().forEach(elem -> unionTypeNode.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(elem.toString())));
	}

	/**
	 * Creates a new node of type {@link UnknownType} and adds the unknown type as a
	 * string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/UnknownType.html">JavaParser
	 *      Docs - UnknownType</a>
	 * @param n   UnknownType
	 * @param arg Parent framework node of the UnknownType's framework node.
	 */
	@Override
	public void visit(UnknownType n, Node arg) {
		createNodeWithValue(n, arg, NodeType.UNDEFINED);
	}

	/**
	 * Creates a new node of type {@link UnparsableStmt} and adds the unparsable
	 * statement as a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/UnparsableStmt.html">JavaParser
	 *      Docs - UnparsableStmt</a>
	 * @param n   UnparsableStmt
	 * @param arg Parent framework node of the UnparsableStmt's framework node.
	 */
	@Override
	public void visit(UnparsableStmt n, Node arg) {
		createNodeWithValue(n, arg, NodeType.UNDEFINED);
	}

	/**
	 * Visits all children of the {@link VariableDeclarationExpr}.
	 * <p>
	 * If the parent of the variable decl expr is an {@link FieldDeclaration}, this
	 * method creates a new node, to provide a place for the attributes modifier.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/VariableDeclarationExpr.html">JavaParser
	 *      Docs - VariableDeclarationExpr</a>
	 * @param n   VariableDeclarationExpr
	 * @param arg Parent framework node of the VariableDeclarationExpr's framework
	 *            node.
	 */
	@Override
	public void visit(VariableDeclarationExpr n, Node arg) {
		Node varDeclExprNode = arg;
		if (!(n.getParentNode().get() instanceof FieldDeclaration)) {
			varDeclExprNode = new NodeImpl(NodeType.VARIABLE_DECLARATION, VariableDeclarationExpr.class.getSimpleName(), arg);
		}
		visitor(n, varDeclExprNode);
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Type} with value
	 * {@link VariableDeclarator#getType()}, an attribute
	 * {@link JavaAttributesTypes#Name} with value
	 * {@link VariableDeclarator#getName()} and an attribute
	 * {@link JavaAttributesTypes#Initialization} with value of
	 * {@link VariableDeclarator#getInitializer()} is the initializer is present to
	 * the parent node.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/VariableDeclarator.html">JavaParser
	 *      Docs - VariableDeclarator</a>
	 * @param n   VariableDeclarator
	 * @param arg Parent framework node of the VariableDeclarator's framework node.
	 */
	@Override
	public void visit(VariableDeclarator n, Node arg) {
		Node variableDeclaratorNode = new NodeImpl(NodeType.VARIABLE_DECLARATION, VariableDeclarator.class.getSimpleName(), arg);
		// Type
		variableDeclaratorNode.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(n.getTypeAsString()));

		visitor(n, variableDeclaratorNode, n.getType());
	}

	/**
	 * Creates a new node of type {@link VarType} and adds the var type as a string
	 * as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/VarType.html">JavaParser
	 *      Docs - VarType</a>
	 * @param n   VarType
	 * @param arg Parent framework node of the VarType's framework node.
	 */
	@Override
	public void visit(VarType n, Node arg) {
		createNodeWithValue(n, arg, NodeType.VARIABLE_DECLARATION);
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} with value
	 * {@link VoidType} as a string to the parent. If the parent is of the
	 * {@link VoidType} is {@link MethodDeclaration}, then the attribute type will
	 * be {@link JavaAttributesTypes#ReturnType}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/VoidType.html">JavaParser
	 *      Docs - VoidType</a>
	 * @param n   VoidType
	 * @param arg Parent framework node of the VoidType's framework node.
	 */
	@Override
	public void visit(VoidType n, Node arg) {
		arg.addAttribute(JavaAttributesTypes.Value.name(), new StringValueImpl(n.toString()));
	}

	/**
	 * Creates a new node of type {@link WhileStmt} and visits all children of the
	 * while statement.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/WhileStmt.html">JavaParser
	 *      Docs - WhileStmt</a>
	 * @param n   WhileStmt
	 * @param arg Parent framework node of the WhileStmt's framework node.
	 */
	@Override
	public void visit(WhileStmt n, Node arg) {
		Node whileStmtNode = new NodeImpl(NodeType.LOOP_WHILE, n.getClass().getSimpleName(), arg);
		whileStmtNode.addAttribute(JavaAttributesTypes.Condition.name(), new StringValueImpl(n.getCondition().toString()));
		visitor(n, whileStmtNode, n.getCondition());
	}

	/**
	 * Creates a new node of type {@link WildcardType} and adds the wildcard type as
	 * a string as an attribute {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/WildcardType.html">JavaParser
	 *      Docs - WildcardType</a>
	 * @param n   WildcardType
	 * @param arg Parent framework node of the WildcardType's framework node.
	 */
	@Override
	public void visit(WildcardType n, Node arg) {
		createNodeWithValue(n, arg, NodeType.TEMPLATE);
	}

	/**
	 * Creates a new node of type {@link YieldStmt} and visits all children of the
	 * yield statement.
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/YieldStmt.html">JavaParser
	 *      Docs - YieldStmt</a>
	 * @param n   YieldStmt
	 * @param arg Parent framework node of the YieldStmt's framework node.
	 */
	@Override
	public void visit(YieldStmt n, Node arg) {
		visitor(n, new NodeImpl(NodeType.JUMP, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node of type {@link ConstructorDeclaration} and visits all
	 * children of the constructor declaration.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/ConstructorDeclaration.html">JavaParser
	 *      Docs - ConstructorDeclaration</a>
	 * @param n   ConstructorDeclaration
	 * @param arg Parent framework node of the ConstructorDeclaration's framework
	 *            node.
	 */
	@Override
	public void visit(ConstructorDeclaration n, Node arg) {
		visitor(n, new NodeImpl(NodeType.CONSTRUCTION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node of type {@link JavaNodeTypes#Continue} and adds an
	 * attribute {@link JavaAttributesTypes#Target} with value
	 * {@link ContinueStmt#getLabel()} is a label is present.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ContinueStmt.html">JavaParser
	 *      Docs - ContinueStmt</a>
	 * @param n   ContinueStmt
	 * @param arg Parent framework node of the ContinueStmt's framework node.
	 */
	@Override
	public void visit(ContinueStmt n, Node arg) {
		Node continueNode = new NodeImpl(NodeType.JUMP, JavaNodeTypes.Continue.name(), arg);
		if (n.getLabel().isPresent()) {
			continueNode.addAttribute(JavaAttributesTypes.Target.name(), new StringValueImpl(n.getLabel().get().toString()));
		}
	}

	/**
	 * Creates a new node of type {@link DoStmt} and visits all children of the do
	 * stmt.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/DoStmt.html">JavaParser
	 *      Docs - DoStmt</a>
	 * @param n   DoStmt
	 * @param arg Parent framework node of the DoStmt's framework node.
	 */
	@Override
	public void visit(DoStmt n, Node arg) {
		Node doNode = new NodeImpl(NodeType.LOOP_DO, n.getClass().getSimpleName(), arg);
		doNode.addAttribute(JavaAttributesTypes.Condition.name(), new StringValueImpl(n.getCondition().toString()));
		visitor(n, doNode, n.getCondition());

	}

	/**
	 * Creates a new node of type {@link EmptyStmt}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/EmptyStmt.html">JavaParser
	 *      Docs - EmptyStmt</a>
	 * @param n   EmptyStmt
	 * @param arg Parent framework node of the EmptyStmt's framework node.
	 */
	@Override
	public void visit(EmptyStmt n, Node arg) {
		new NodeImpl(NodeType.BLOCK, EmptyStmt.class.getSimpleName(), arg);
	}

	/**
	 * Creates a new node of type {@link DoubleLiteralExpr} and adds the double
	 * literal expression as a string as an attribute
	 * {@link JavaAttributesTypes#Value}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/DoubleLiteralExpr.html">JavaParser
	 *      Docs - DoubleLiteralExpr</a>
	 * @param n   DoubleLiteralExpr
	 * @param arg Parent framework node of the DoubleLiteralExpr's framework node.
	 */
	@Override
	public void visit(DoubleLiteralExpr n, Node arg) {
		createLiteralNode(n, arg, "double");
	}

	/**
	 * Creates a new node of type {@link EnclosedExpr} and visits all children of
	 * the enclosed expression.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/EnclosedExpr.html">JavaParser
	 *      Docs - EnclosedExpr</a>
	 * @param n   EnclosedExpr
	 * @param arg Parent framework node of the EnclosedExpr's framework node.
	 */
	@Override
	public void visit(EnclosedExpr n, Node arg) {
		visitor(n, new NodeImpl(NodeType.EXPRESSION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node of type {@link EnumConstantDeclaration} and visits all
	 * children of the enum constant declaration.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/EnumConstantDeclaration.html">JavaParser
	 *      Docs - EnumConstantDeclaration</a>
	 * @param n   EnumConstantDeclaration
	 * @param arg Parent framework node of the EnumConstantDeclaration's framework
	 *            node.
	 */
	@Override
	public void visit(EnumConstantDeclaration n, Node arg) {
		visitor(n, new NodeImpl(NodeType.FIELD_DECLARATION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * If the parent node is the compilation unit an attribute
	 * {@link JavaAttributesTypes#IsEnum} with value <code>true</true> is added to
	 * the compilation unit. Otherwise, if the parent node is not the compilation
	 * unit, then a new node of type {@link EnumDeclaration} is created.
	 * <p>
	 * Then all children of the enum declaration are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/body/EnumDeclaration.html">JavaParser
	 *      Docs - EnumDeclaration</a>
	 * @param n   EnumDeclaration
	 * @param arg Parent framework node of the EnumDeclaration's framework node.
	 */
	@Override
	public void visit(EnumDeclaration n, Node arg) {
		Node enumNode = arg;
		if (!n.getParentNode().isPresent() || !(n.getParentNode().get() instanceof CompilationUnit)) {
			enumNode = new NodeImpl(NodeType.ENUM, n.getClass().getSimpleName(), arg);
		} else {
			enumNode.addAttribute(JavaAttributesTypes.IsEnum.name(), new StringValueImpl(String.valueOf(true)));
		}
		visitor(n, enumNode);
	}

	/**
	 * Creates a new node of type {@link ExplicitConstructorInvocationStmt}.
	 * <p>
	 * If type arguments are present, then for every type argument an attribute is
	 * added to the new node. The attribute is of type
	 * {@link JavaAttributesTypes#TypeArgument} with the type argument as a string
	 * as value.
	 * <p>
	 * The new node gets a child node with type {@link JavaNodeTypes#Argument},
	 * which has an attribute {@link JavaAttributesTypes#Children} which indicates
	 * the number of arguments. The argument node gets an node of
	 * {@link JavaNodeTypes#Argument} with an index for every argument. The indexed
	 * argument node is the parent node for each argument, which are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ExplicitConstructorInvocationStmt.html">JavaParser
	 *      Docs - ExplicitConstructorInvocationStmt</a>
	 * @param n   ExplicitConstructorInvocationStmt
	 * @param arg Parent framework node of the ExplicitConstructorInvocationStmt's
	 *            framework node.
	 */
	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Node arg) {
		Node c = new NodeImpl(NodeType.METHOD_CALL, n.getClass().getSimpleName(), arg);

		// This or super?
		c.addAttribute(JavaAttributesTypes.IsThis.name(), new StringValueImpl(String.valueOf(n.isThis())));

		// TypeArguments
		if (n.getTypeArguments().isPresent()) {
			for (Type typeArgumentExpr : n.getTypeArguments().get()) {
				c.addAttribute(JavaAttributesTypes.TypeArgument.name(), new StringValueImpl(typeArgumentExpr.toString()));
			}
		}

		visitArguments(n, c);
	}

	/**
	 * Creates a new node with type {@link ForEachStmt}.
	 * <p>
	 * Adds an attribute {@link JavaAttributesTypes#Iterator} with value
	 * {@link ForEachStmt#getIterable()}.
	 * <p>
	 * Another attribute {@link JavaAttributesTypes#Initialization} with the name of
	 * {@link ForEachStmt#getVariableDeclarator()} is added to the new node. The
	 * attribute {@link JavaAttributesTypes#Type} contains the type of
	 * {@link ForEachStmt#getVariableDeclarator()}.
	 * <p>
	 * Afterwards the body of the {@link ForEachStmt} is visited
	 * ({@link ForEachStmt#getBody()}).
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ForEachStmt.html">JavaParser
	 *      Docs - ForEachStmt</a>
	 * @param forEachStmt ForEachStmt
	 * @param parent      Parent framework node of the ForEachStmt's framework node.
	 */
	@Override
	public void visit(ForEachStmt forEachStmt, Node parent) {
		visitor(forEachStmt, this.factory.getStatemenNodeFactory().createStatementNode(forEachStmt, parent, this),
				forEachStmt.getIterable(), forEachStmt.getVariable());
	}

	/**
	 * A new node with type {@link ForStmt} is generated.
	 * <p>
	 * This node gets an attribute {@link JavaAttributesTypes#Comparison} if a
	 * comparison is present. This attribute contains the expression of
	 * {@link ForStmt#getCompare()} as a string.
	 * <p>
	 * For every initialization {@link ForStmt#getInitialization()} an attribute
	 * {@link JavaAttributesTypes#Initialization} with the initialization expression
	 * as a string is added. The initilization are removed from the {@link ForStmt}
	 * JavaParser node afterwards.
	 * <p>
	 * Analog to the initializations, the same thing is done with the updates
	 * ({@link ForStmt#getUpdate()}). These are added to the attribute
	 * {@link JavaAttributesTypes#Update}.
	 * <p>
	 * Finally the leftovers are visited.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/ForStmt.html">JavaParser
	 *      Docs - ForStmt</a>
	 * @param forStmt ForStmt
	 * @param parent  Parent framework node of the ForStmt's framework node.
	 */
	@Override
	public void visit(ForStmt forStmt, Node parent) {
		visitor(forStmt, this.getFactory().getStatemenNodeFactory().createStatementNode(forStmt, parent, this));
	}

	/**
	 * A new node with type {@link IfStmt} is created. Then
	 * {@link JavaVisitor#visitIfStmt(IfStmt, Node)} is used to generate the
	 * children recursively.
	 * 
	 * @see JavaVisitor#visitIfStmt(IfStmt, Node)
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/stmt/IfStmt.html">JavaParser
	 *      Docs - IfStmt</a>
	 * @param ifStmt IfStmt
	 * @param parent Parent framework node of the IfStmt's framework node.
	 */
	@Override
	public void visit(IfStmt ifStmt, Node parent) {
		this.factory.getStatemenNodeFactory().createStatementNode(ifStmt, parent, this);
	}

	/**
	 * Creates a new node of type {@link InitializerDeclaration} and visits all
	 * children of the initializer declaration.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/decl/InitializerDeclaration.html">JavaParser
	 *      Docs - InitializerDeclaration</a>
	 * @param n   InitializerDeclaration
	 * @param arg Parent framework node of the InitializerDeclaration's framework
	 *            node.
	 */
	@Override
	public void visit(InitializerDeclaration n, Node arg) {
		visitor(n, new NodeImpl(NodeType.BLOCK, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node with type {@link InstanceOfExpr}.
	 * 
	 * Adds an attribute {@link JavaAttributesTypes#Type} with value
	 * {@link InstanceOfExpr#getType()} and adds an attribute
	 * {@link JavaAttributesTypes#Expression} with value
	 * {@link InstanceOfExpr#getExpression()}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/InstanceOfExpr.html">JavaParser
	 *      Docs - InstanceOfExpr</a>
	 * @param n   InstanceOfExpr
	 * @param arg Parent framework node of the InstanceOfExpr's framework node.
	 */
	@Override
	public void visit(InstanceOfExpr n, Node arg) {
		Node c = new NodeImpl(NodeType.TYPE_CHECK, n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaAttributesTypes.Type.name(),new StringValueImpl( n.getType().toString()));
		c.addAttribute(JavaAttributesTypes.Expression.name(),new StringValueImpl( n.getExpression().toString()));
	}

	/**
	 * Adds an attribute {@link JavaAttributesTypes#Value} with value
	 * {@link IntegerLiteralExpr} as number to the parent node.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/expr/IntegerLiteralExpr.html">JavaParser
	 *      Docs - IntegerLiteralExpr</a>
	 * @param n   IntegerLiteralExpr
	 * @param arg Parent framework node of the IntegerLiteralExpr's framework node.
	 */
	@Override
	public void visit(IntegerLiteralExpr n, Node arg) {
		createLiteralNode(n, arg, "int");
	}

	/**
	 * Creates a new node of type {@link IntersectionType} and visits all children
	 * of the intersection type.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/type/IntersectionType.html">JavaParser
	 *      Docs - IntersectionType</a>
	 * @param n   IntersectionType
	 * @param arg Parent framework node of the IntersectionType's framework node.
	 */
	@Override
	public void visit(IntersectionType n, Node arg) {
		visitor(n, new NodeImpl(NodeType.INTERSECTION, n.getClass().getSimpleName(), arg));
	}

	/**
	 * Creates a new node of type {@link LineComment}.
	 * <p>
	 * The content of the comment ({@link Comment#getContent()} is saved in an
	 * attributes {@link JavaAttributesTypes#Comment}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/comments/LineComment.html">JavaParser
	 *      Docs - LineComment</a>
	 * @param n      LineComment
	 * @param parent Parent framework node of the LineComment's framework node.
	 */
	@Override
	public void visit(LineComment n, Node parent) {
		Node com = new NodeImpl(NodeType.COMMENT, JavaNodeTypes.LineComment.name(), parent);
		com.addAttribute(JavaAttributesTypes.Comment.name(), new StringValueImpl(n.getContent()));
	}

	/**
	 * Creates a new node of type {@link BlockComment}.
	 * <p>
	 * The content of the comment ({@link Comment#getContent()} is saved in an
	 * attributes {@link JavaAttributesTypes#Comment}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/comments/BlockComment.html">JavaParser
	 *      Docs - BlockComment</a>
	 * @param n   BlockComment
	 * @param arg Parent framework node of the BlockComment's framework node.
	 */
	@Override
	public void visit(BlockComment n, Node arg) {
		Node com = new NodeImpl(NodeType.COMMENT, JavaNodeTypes.BlockComment.name(), arg);
		com.addAttribute(JavaAttributesTypes.Comment.name(), new StringValueImpl(n.getContent()));
	}

	/**
	 * Creates a new node of type {@link JavadocComment}.
	 * <p>
	 * The content of the comment ({@link Comment#getContent()} is saved in an
	 * attributes {@link JavaAttributesTypes#Comment}.
	 * 
	 * @see <a href=
	 *      "https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/ast/comments/JavadocComment.html">JavaParser
	 *      Docs - JavadocComment</a>
	 * @param n   JavadocComment
	 * @param arg Parent framework node of the JavadocComment's framework node.
	 */
	@Override
	public void visit(JavadocComment n, Node arg) {
		Node com = new NodeImpl(NodeType.COMMENT, JavaNodeTypes.JavadocComment.name(), arg);
		com.addAttribute(JavaAttributesTypes.Comment.name(),new StringValueImpl( n.getContent()));
	}
	
	/**
	 * Creates a new node with attribute key "Value" (of type
	 * {@link JavaAttributesTypes} as string) and n converted to string as a value.
	 * <p>
	 * The new node has the simple name of the specific class of the JP Node as
	 * type.
	 * 
	 * @param n   JavaParser Node
	 * @param arg Parent Node
	 */
	private Node createNodeWithValue(com.github.javaparser.ast.Node n, Node arg, NodeType standardizedType) {
		Node c = new NodeImpl(standardizedType, n.getClass().getSimpleName(), arg);
		c.addAttribute(JavaAttributesTypes.Value.name(),new StringValueImpl( n.toString()));
		return c;
	}
	
	private Node createLiteralNode(com.github.javaparser.ast.Node n, Node arg, String dataType) {
		Node literalNode = new NodeImpl(NodeType.LITERAL, n.getClass().getSimpleName(), arg);
		literalNode.addAttribute(JavaAttributesTypes.Type.name(), new StringValueImpl(dataType));
		literalNode.addAttribute(JavaAttributesTypes.Value.name(), new StringValueImpl(n.toString()));
		return literalNode;
	}

	public NodeFactory getFactory() {
		return factory;
	}

	public void setFactory(NodeFactory factory) {
		this.factory = factory;
	}
}