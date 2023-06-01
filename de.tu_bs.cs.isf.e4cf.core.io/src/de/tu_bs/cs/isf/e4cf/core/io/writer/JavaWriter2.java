package de.tu_bs.cs.isf.e4cf.core.io.writer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
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
import com.github.javaparser.ast.body.TypeDeclaration;
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
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
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
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithArguments;
import com.github.javaparser.ast.nodeTypes.NodeWithBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
import com.github.javaparser.ast.nodeTypes.NodeWithCondition;
import com.github.javaparser.ast.nodeTypes.NodeWithOptionalBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithParameters;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.nodeTypes.NodeWithTypeParameters;
import com.github.javaparser.ast.nodeTypes.NodeWithVariables;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.YieldStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.PrettyPrinterConfiguration.IndentType;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.io.interfaces.AbstractArtifactWriter;
import de.tu_bs.cs.isf.e4cf.core.io.reader.java_reader.JavaNodeTypes;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;

/** 
 * This class parses a node composite back into Github Javaparser Trees
 */
public class JavaWriter2 extends AbstractArtifactWriter{

	public final static String FILE_ENDING = "java";
	public final static String ALT_FILE_ENDING = "tree";
	public final static String NODE_TYPE_TREE = "JAVA";

	/**
	 * Initializes a new instance of class JavaWriter2.
	 */
	public JavaWriter2() {
		super(FILE_ENDING);
	}

	@Override
	public String getSuppotedNodeType() {
		return NODE_TYPE_TREE;
	}
	
	@Override
	public boolean isFileSupported(String fileExtension) {
		boolean isSupported = super.isFileSupported(fileExtension);
		if (!isSupported) {
			return fileExtension.equals(ALT_FILE_ENDING);
		}
		return isSupported;
	}

	@Override
	public void writeArtifact(Tree tree, String path) {
		if (tree.getArtifactType().equals(NODE_TYPE_TREE)) {
			PrettyPrinterConfiguration ppc = new PrettyPrinterConfiguration();
			ppc.setIndentType(IndentType.TABS); // Default is spaces
			ppc.setIndentSize(1); // Only one tab to indent
			try {
				com.github.javaparser.ast.Node jpRoot = parseE4Tree(null, tree.getRoot());
				FileStreamUtil.writeTextToFile(path + "." + FILE_ENDING, jpRoot.toString(ppc));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Traverse the e4 Compare Node Tree and build up javaparser nodes along the way
	 * @param jParent The Github Javaparser Parent to append newly created nodes to
	 * @param e4Node The e4Compare Node that generates a JPNode in this pass
	 * @return JavaParser Subtree
	 */
	public com.github.javaparser.ast.Node parseE4Tree(com.github.javaparser.ast.Node jpParent, Node e4Node) {
		
		com.github.javaparser.ast.Node jpNode = null;
		
		// this flag disables the recursive descent of the e4Node's children if set to false
		boolean doDescentFlag = true;
		
		// Collect the attributes from the given node
		JavaWriterAttributeCollector attributes = new JavaWriterAttributeCollector();
		attributes.collectAttributes(e4Node);
		
		// TODO: Multifiles / Directories
		
		// Switch through the different NodeTypes that we may find and create according JavaParser nodes
		switch (JavaNodeTypes.fromString(e4Node.getNodeType()))
		{
		case AnnotationDeclaration:
			jpNode = createAnnotationDeclaration(attributes, jpParent);
			break;
		case AnnotationMemberDeclaration:
			jpNode = new AnnotationMemberDeclaration(attributes.getModifier(), attributes.getAnnotation(),
					attributes.getType(), new SimpleName(attributes.getName()), attributes.getValue());
			break;
		case Argument:
			doDescentFlag = processArgument(attributes, jpParent, e4Node);
			break;
		case ArrayAccessExpr:
			jpNode = processArrayAccess(e4Node);
			doDescentFlag = false;
			break;
		case ArrayCreationExpr:
			if (attributes.getType() != null) {
				jpNode = new ArrayCreationExpr(attributes.getType());
			} else {
				// Probably Initialized as Object Array, derive from Name field
				jpNode = new ArrayCreationExpr(
						StaticJavaParser.parseClassOrInterfaceType(attributes.getName()));
			}
			break;
		case ArrayCreationLevel:
			jpNode = new ArrayCreationLevel();
			break;
		case ArrayInitializerExpr:
			jpNode = new ArrayInitializerExpr();
			break;
		case AssertStmt:
			jpNode = new AssertStmt();
			break;
		case Assignment:
			AssignExpr assEx = new AssignExpr();
			assEx.setTarget(attributes.getTarget());
			assEx.setOperator(AssignExpr.Operator.valueOf(attributes.getOperator()));
			// Value is done in Expression
			jpNode = assEx;
			break;
		case BinaryExpr:
			BinaryExpr expr = new BinaryExpr();
			expr.setOperator(BinaryExpr.Operator.valueOf(attributes.getOperator()));
			// We need to intervene into the parsing process here to determine left and right expressions
			Node left = e4Node.getChildren().get(0);
			Node right = e4Node.getChildren().get(1);
			expr.setLeft((Expression)parseE4Tree(expr, left));
			expr.setRight((Expression)parseE4Tree(expr, right));
			jpNode = expr;
			doDescentFlag = false;
			break;
		case BlockComment:
			jpNode = new BlockComment(attributes.getComment());
			break;
		case Body:
			jpNode = new BlockStmt();
			break;
		case BooleanLiteralExpr:
			jpNode = new BooleanLiteralExpr(Boolean.valueOf(attributes.getValue().toString()));
			break;
		case Bound:
			processBound(attributes, jpParent);
			break;
		case Break:
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
			break;
		case Cast:
			jpNode = new CastExpr().setType(attributes.getType());
			break;
		case CatchClause:
			jpNode = new CatchClause();
			break;
		case CharLiteralExpr:
			jpNode = attributes.getValue().toCharLiteralExpr().get();
			break;
		case Class:
			// TODO ClassExpr?
			jpNode = createClassOrInterfaceDeclaration(attributes, jpParent);
			break;
		case CompilationUnit:
			// Add package to CU created previously
			if (jpParent instanceof CompilationUnit && !attributes.getPackage().isEmpty()) {
				((CompilationUnit) jpParent).setPackageDeclaration(attributes.getPackage());
			}
			/*
			 * When the compilation unit is class or interface create a new class or
			 * interface declaration. Otherwise it is an enum declaration. The name
			 * attribute is empty, if the compilation unit only consists of a package
			 * declaration.
			 */
			if (!attributes.getName().isEmpty()) {
				if (!attributes.isEnum()) {
					jpNode = createClassOrInterfaceDeclaration(attributes, jpParent);
				} else {
					jpNode = new EnumDeclaration(attributes.getModifier(), attributes.getName());
				}
			}
			break;
		case Condition:
			// Attach Expression below to parent above
			Expression condition = (Expression)parseE4Tree(null, e4Node.getChildren().get(0));
			if (jpParent instanceof ForStmt) {
				((ForStmt)jpParent).setCompare(condition);
			} else if (jpParent instanceof AssertStmt) {
				((AssertStmt)jpParent).setCheck(condition);
			} else {
				((NodeWithCondition<?>) jpParent).setCondition(condition);
			}
			doDescentFlag = false;
			break;
		case ConditionalExpr:
			jpNode = new ConditionalExpr();
			break;
		case ConstructorDeclaration:
			jpNode = new ConstructorDeclaration(attributes.getName()).setModifiers(attributes.getModifier())
				.setAnnotations(attributes.getAnnotation());
			break;
		case Continue:
			ContinueStmt cStmt = new ContinueStmt();
			// If there is an optional label for the continue stmt, then set it; otherwise remove the default "empty" label.
			if (attributes.getTarget() != null) {
				cStmt.setLabel(new SimpleName(attributes.getTarget().toString()));
			} else {
				cStmt.removeLabel();
			}
			jpNode = cStmt;
			break;
		case DoStmt:
			jpNode = new DoStmt();
			break;
		case DoubleLiteralExpr:
			jpNode = new DoubleLiteralExpr(attributes.getValue().toString());
			break;
		case Else:
			// Else should always have some form of statement/expression as child, right? And also just 1
			// Parse the statements below then and attach them accordingly
			if (jpParent instanceof IfStmt) {
				((IfStmt)jpParent).setElseStmt((Statement) parseE4Tree(null, e4Node.getChildren().get(0)));
			} else if (jpParent instanceof ConditionalExpr) {
				((ConditionalExpr)jpParent).setElseExpr((Expression) parseE4Tree(null, e4Node.getChildren().get(0)));
			} else {
				throw new UnsupportedOperationException("Can't add 'else' to parent: " + jpParent.getClass().getSimpleName());
			}
			doDescentFlag = false;
			break;
		case EmptyStmt:
			jpNode = new EmptyStmt();
			break;
		case EnclosedExpr:
			// Parentheses that contain another expression (set later)
			jpNode = new EnclosedExpr();
			break;
		case EnumConstantDeclaration:
			jpNode = new EnumConstantDeclaration(attributes.getName());
			break;
		case EnumDeclaration:
			jpNode = new EnumDeclaration(attributes.getModifier(), attributes.getName());
			break;
		case ExplicitConstructorInvocationStmt:
			jpNode = new ExplicitConstructorInvocationStmt().setThis(attributes.isThis());
			break;
		case FieldAccessExpr:
			jpNode = new FieldAccessExpr(attributes.getScope(), attributes.getName());
			break;
		case FieldDeclaration:
			jpNode = new FieldDeclaration()
				.setModifiers(attributes.getModifier())
				.setAnnotations(attributes.getAnnotation());
			break;
		case Finally:
			/*
			 * If the types are correct (parent if try stmt and finally if really block,
			 * then set finally block. Set parent and node to (virtually) null, so rest of
			 * the method (setParent and visiting) is not done.
			 */
			if (jpParent instanceof TryStmt && e4Node.getChildren().get(0).getNodeType().equals(JavaNodeTypes.Body.name())) {
				jpNode = parseE4Tree(null, e4Node.getChildren().get(0));
				((TryStmt) jpParent).setFinallyBlock((BlockStmt) jpNode);
				jpParent = null;
				e4Node = new NodeImpl(NodeType.UNDEFINED);
			}
			break;
		case ForEachStmt:
			jpNode = new ForEachStmt().setIterable(attributes.getIterator());
			//.setVariable(new VariableDeclarationExpr(	attributes.getType(), attributes.getInitilization().getFirst().get().toString()));
			break;
		case ForStmt:
			// Set compare to "true" as it is javas default in case no condition node exists
			jpNode = new ForStmt().setCompare(StaticJavaParser.parseExpression("true"));
			// Initialization and Update have their own cases.
			break;
		case IfStmt:
			// Just add the first if stmt, the logic is in THEN and ELSE and CONDITION
			jpNode = new IfStmt();
			break;
		case Import:
			processImport(attributes, jpParent);
			break;
		case Imports:
			// container, ignore
			break;
		case Initialization:
			// A Container to discriminate expressions in for-loops
			for (Node e4Child : e4Node.getChildren()) {
				Expression initExpr = (Expression) parseE4Tree(null, e4Child);
				initExpr.setParentNode(jpParent);
				// For vs ForEach
				if (jpParent instanceof ForStmt) {
					((ForStmt)jpParent).getInitialization().add(initExpr);
				} else {
					((ForEachStmt)jpParent).setVariable((VariableDeclarationExpr) initExpr);
				}
			}
			doDescentFlag = false;
			break;
		case InitializerDeclaration:
			jpNode = new InitializerDeclaration();
			break;
		case InstanceOfExpr:
			jpNode = new InstanceOfExpr()
						.setExpression(attributes.getExpression())
						.setType(attributes.getType().toString());
			break;
		case IntegerLiteralExpr:
			jpNode = new IntegerLiteralExpr(attributes.getValue().toString());
			break;
		case Interface:
			jpNode = createClassOrInterfaceDeclaration(attributes, jpParent);
			break;
		case IntersectionType:
			// Looks fine
			break;
		case JAVA:
			// e4 root node is wrapping a compilation unit 
			jpNode = new CompilationUnit();
			break;
		case LabeledStmt:
			jpNode = new LabeledStmt().setLabel(new SimpleName(attributes.getName()));
			break;
		case LambdaExpr:
			/*
			 * In some cases, e.g. binary expr, the lambda expr might contain the attribute
			 * value. In this case the attribute contains the body of the lambda expr.
			 */
			jpNode = new LambdaExpr().setEnclosingParameters(attributes.isEnclosingParameters());
			break;
		case LineComment:
			jpNode = new LineComment(attributes.getComment());
			break;
		case LocalClassDeclarationStmt:
			jpNode = new LocalClassDeclarationStmt();
			break;
		case LongLiteralExpr:
			jpNode = new LongLiteralExpr(attributes.getValue().toString());
			break;
		case MemberValuePair:
			jpNode = new MemberValuePair(attributes.getKey(), attributes.getValue());
			break;
		case MethodCallExpr:
			jpNode = new MethodCallExpr(attributes.getScope(), attributes.getName());
			if (attributes.getTypeArguments().isNonEmpty()) {
				((MethodCallExpr)jpNode).setTypeArguments(attributes.getTypeArguments());
			}			
			break;
		case MethodDeclaration:
			/*
			 * The bodies of the method declaration are removed by default in this step. If
			 * a method is not abstract the body will be defined in the child nodes
			 * otherwise it stays empty.
			 */
			jpNode = new MethodDeclaration(attributes.getModifier(), attributes.getName(), attributes.getReturnType(),
					new NodeList<Parameter>()).setThrownExceptions(attributes.getThrows())
							.setAnnotations(attributes.getAnnotation()).removeBody();
			break;
		case MethodReferenceExpr:
			MethodReferenceExpr methRef = new MethodReferenceExpr();
			methRef.setScope(attributes.getScope()); 
			methRef.setIdentifier(attributes.getIdentifier());
			if (attributes.getType() != null) {
				methRef.setTypeArguments(attributes.getType());
			}
			jpNode = methRef;
			break;
		case NameExpr:
			jpNode = new NameExpr(attributes.getName());
			break;
		case NormalAnnotationExpr:
			jpNode = new NormalAnnotationExpr(new Name(attributes.getName()), new NodeList<MemberValuePair>());
			break;
		case NullLiteralExpr:
			jpNode = new NullLiteralExpr();
			break;
		case ObjectCreationExpr:
			// Creates a new object creation expr, with scope, type and empty arguments list
			jpNode = new ObjectCreationExpr(attributes.getScope(),attributes.getType().asClassOrInterfaceType(),new NodeList<Expression>());
			break;
		case Parameter:
			Parameter param = new Parameter(new UnknownType(), attributes.getName()).setModifiers(attributes.getModifier());
			if (attributes.getType() != null) {
				// Replace unknown type if there is a more specific type for the parameter.
				param.setType(attributes.getType());
			}
			jpNode = param;
			break;
		case ReceiverParameter:
			jpNode = new ReceiverParameter();
			break;
		case ReturnStmt:
			jpNode = new ReturnStmt();
			break;
		case SingleMemberAnnotationExpr:
			jpNode = new SingleMemberAnnotationExpr(new Name(attributes.getName()), attributes.getValue());
			break;
		case StringLiteralExpr:
			jpNode = attributes.getValue().toStringLiteralExpr().get();
			break;
		case SuperExpr:
			jpNode = new SuperExpr();
			break;
		case SwitchEntry:
			SwitchEntry se = new SwitchEntry().setType(SwitchEntry.Type.valueOf(attributes.getType().toString()));
			if (!attributes.isDefault()) {
				// If the switch entry is not "default:" (but "case xxx:") add a condition
				se.setLabels(attributes.getCondition());
			}
			jpNode = se;
			break;
		case SwitchStmt:
			jpNode = new SwitchStmt(attributes.getSelector(), new NodeList<SwitchEntry>());
			break;
		case Synchronized:
			jpNode = new SynchronizedStmt().setExpression(attributes.getExpression());
			break;
		case Then:
			// Then should always have some form of statement/expression as child, right? And also just 1
			// Parse the statements below then and attach them accordingly
			if (jpParent instanceof IfStmt) {
				((IfStmt)jpParent).setThenStmt((Statement) parseE4Tree(null, e4Node.getChildren().get(0)));
			} else if (jpParent instanceof ConditionalExpr) {
				((ConditionalExpr)jpParent).setThenExpr((Expression) parseE4Tree(null, e4Node.getChildren().get(0)));
			} else {
				throw new UnsupportedOperationException("Can't add 'then' to parent: " + jpParent.getClass().getSimpleName());
			}
			doDescentFlag = false;
			break;
		case ThrowStmt:
			jpNode = attributes.getStatement().asThrowStmt();
			break;
		case TryStmt:
			jpNode = new TryStmt().setResources(attributes.getResource());
			break;
		case TypeParameter:
			jpNode = new TypeParameter(attributes.getName()).setAnnotations(attributes.getAnnotation());
			break;
		case UnaryExpr:
			jpNode = new UnaryExpr();
			((UnaryExpr)jpNode).setOperator(UnaryExpr.Operator.valueOf(attributes.getOperator()));
			break;
		case UnionType:
			jpNode = new UnionType();
			break;
		case Update:
			// A Container to discriminate expressions in for-loops
			for (Node e4Child : e4Node.getChildren()) {
				Expression updExpr = (Expression) parseE4Tree(null, e4Child);
				updExpr.setParentNode(jpParent);
				((ForStmt)jpParent).getUpdate().add(updExpr);
			}
			doDescentFlag = false;
			break;
		case VariableDeclarationExpr:
			jpNode = new VariableDeclarationExpr().setModifiers(attributes.getModifier()).setAnnotations(attributes.getAnnotation());
			break;
		case VariableDeclarator:
			VariableDeclarator vd = new VariableDeclarator(attributes.getType(), attributes.getName());
			jpNode = vd;
			break;
		case WhileStmt:
			jpNode = new WhileStmt();
			break;
		case YieldStmt:
			jpNode = new YieldStmt();
			break;
		default:
			// Switch Expression Broken in GitHub Javaparser
			// Unlisted Types may be ignored because they are only logical containers in e4cf
			//System.out.println("Ignored NodeType: " + e4Node.getNodeType());
			break;
		}
		
		// Reintegration of different comments that appeared as attributes on e4 Nodes
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
		
		// Integrate JavaParser node at correct field
		setParent(jpParent, jpNode);
		
		if (doDescentFlag) {
			for (Node e4Child : e4Node.getChildren()) {
				/*
				 * Visit all children. In nodes with only meta information, jpNode stays null.
				 * When that happens, use jpParent as argument again, so the child nodes of the meta
				 * info node can be directly added to the original parent.
				 */
				parseE4Tree(Optional.ofNullable(jpNode).orElse(jpParent), e4Child);
			}
		}
		
		return jpNode;
	}

	/**
	 * This method adds the child to the children of the parent node. The types of
	 * the nodes are also considered to make the changes effective.
	 * 
	 * @param p Parent Node
	 * @param c  Child Node
	 */
	@SuppressWarnings("rawtypes")
	private void setParent(com.github.javaparser.ast.Node p, com.github.javaparser.ast.Node c) {
		
		// No null values
		if (p == null || c == null) {
			// Ignore
			return;
		}
		
		/* Self-References may occur, see ArrayInitializerExpr
		if (p.equals(c)) {
			throw new UnsupportedOperationException("Can not attach a node to itself: " + p.toString());
		}
		*/
		
		// Set general parent, does not seems to affect anything
		c.setParentNode(p);
		
		// Real Magic now: Add Types according to their class types
		if (c instanceof AnnotationDeclaration) 
		{
			// see createAnnotationDeclaration
		}
		else if (p instanceof CompilationUnit && c instanceof TypeDeclaration) 
		{
			((CompilationUnit) p).addType((TypeDeclaration) c);
		}
		else if (p instanceof EnumDeclaration && c instanceof EnumConstantDeclaration) 
		{
			((EnumDeclaration) p).addEntry((EnumConstantDeclaration) c);
		}
		else if (p instanceof LabeledStmt && c instanceof Statement) 
		{
			((LabeledStmt) p).setStatement((Statement) c);
		}
		else if (p instanceof NodeWithVariables && c instanceof VariableDeclarator) 
		{
			((NodeWithVariables) p).addVariable((VariableDeclarator) c);
		}
		else if (p instanceof LabeledStmt && c instanceof Expression) 
		{
			((LabeledStmt) p).setStatement(new ExpressionStmt((Expression) c));
		}
		else if (p instanceof LambdaExpr && c instanceof Statement) 
		{
			((LambdaExpr) p).setBody((Statement) c);
		}
		else if (p instanceof TypeDeclaration && c instanceof BodyDeclaration) 
		{
			((TypeDeclaration<?>) p).addMember((BodyDeclaration) c);
		}
		else if (p instanceof NodeWithAnnotations && c instanceof AnnotationExpr) 
		{
			((NodeWithAnnotations) p).addAnnotation((AnnotationExpr) c);
		}
		else if (p instanceof SwitchStmt && c instanceof SwitchEntry) 
		{
			NodeList<SwitchEntry> entries = ((SwitchStmt) p).getEntries();
			entries.add((SwitchEntry) c);
			((SwitchStmt) p).setEntries(entries);
		}
		else if (p instanceof NodeWithTypeParameters && c instanceof TypeParameter) 
		{
			((NodeWithTypeParameters) p).addTypeParameter((TypeParameter) c);
		}
		else if (c instanceof Comment) 
		{
			p.addOrphanComment((Comment) c);
		}
		else if (p instanceof CatchClause && c instanceof Parameter) 
		{
			((CatchClause) p).setParameter((Parameter) c);
		}
		else if (p instanceof NodeWithBody && c instanceof Statement) 
		{
			((NodeWithBody) p).setBody((Statement) c);
		}
		else if (p instanceof NodeWithBody && c instanceof Expression) 
		{
			((NodeWithBody) p).setBody(new ExpressionStmt((Expression) c));
		}
		else if (p instanceof EnclosedExpr && c instanceof Expression) 
		{
			((EnclosedExpr) p).setInner((Expression) c);
		}
		else if (p instanceof UnaryExpr && c instanceof Expression) 
		{
			((UnaryExpr) p).setExpression((Expression) c);
		}
		else if (p instanceof NodeWithStatements && c instanceof Statement) 
		{
			((NodeWithStatements) p).addStatement((Statement) c);
		}
		else if (p instanceof NodeWithStatements && c instanceof Expression) 
		{
			((NodeWithStatements) p).addStatement((Expression) c);
		}
		else if (p instanceof NodeWithParameters && c instanceof Parameter) 
		{
			((NodeWithParameters) p).addParameter((Parameter) c);
		}
		else if (p instanceof NormalAnnotationExpr && c instanceof MemberValuePair) 
		{
			NodeList<MemberValuePair> paris = ((NormalAnnotationExpr) p).getPairs();
			paris.add((MemberValuePair) c);
			((NormalAnnotationExpr) p).setPairs(paris);
		}
		else if (p instanceof TryStmt && c instanceof CatchClause) 
		{
			NodeList<CatchClause> clauses = ((TryStmt) p).getCatchClauses();
			clauses.add((CatchClause) c);
			((TryStmt) p).setCatchClauses(clauses);
		}
		else if (p instanceof NodeWithOptionalBlockStmt && c instanceof BlockStmt) 
		{
			((NodeWithOptionalBlockStmt) p).setBody((BlockStmt) c);
		}
		else if (p instanceof NodeWithBlockStmt && c instanceof BlockStmt) 
		{
			((NodeWithBlockStmt) p).setBody((BlockStmt) c);
		}
		else if (p instanceof TryStmt && c instanceof BlockStmt) 
		{
			((TryStmt) p).setTryBlock((BlockStmt) c);
		}
		else if (p instanceof IfStmt && c instanceof IfStmt) 
		{
			// IfStmt logic is done in then andelse
		}
		else if (p instanceof NodeWithArguments && c instanceof Expression) 
		{
			// This occurs with method calls
			((NodeWithArguments)p).addArgument((Expression)c);
		}
		else if (p instanceof VariableDeclarator && c instanceof Expression) 
		{
			((VariableDeclarator) p).setInitializer((Expression) c);
		}
		else if (p instanceof AssignExpr && c instanceof Expression) {
			((AssignExpr) p).setValue((Expression) c);
		}
		else if (p instanceof BinaryExpr) {
			// Already taken care of during the creation to determine left-right nodes
		}
		else if (p instanceof ArrayCreationExpr && c instanceof ArrayCreationLevel) 
		{
			((ArrayCreationExpr)p).getLevels().add((ArrayCreationLevel) c);
		}
		else if (p instanceof ArrayCreationLevel && c instanceof Expression) 
		{
			((ArrayCreationLevel)p).setDimension((Expression) c);
		}
		else if (p instanceof ArrayInitializerExpr && c instanceof Expression) 
		{
			((ArrayInitializerExpr)p).getValues().add((Expression) c);
		}
		else if (p instanceof ReturnStmt && c instanceof Expression) 
		{
			((ReturnStmt)p).setExpression((Expression) c);
		}
		else if (p instanceof CastExpr && c instanceof Expression) 
		{
			((CastExpr)p).setExpression((Expression) c);
		}
		else if (p instanceof AssertStmt && c instanceof Expression) 
		{
			((AssertStmt)p).setMessage((Expression) c);
		}
		else if (p instanceof LocalClassDeclarationStmt && c instanceof ClassOrInterfaceDeclaration) 
		{
			((LocalClassDeclarationStmt)p).setClassDeclaration((ClassOrInterfaceDeclaration) c);
		}
		else if (p instanceof AnnotationMemberDeclaration && c instanceof Expression) 
		{
			((AnnotationMemberDeclaration)p).setDefaultValue((Expression) c);
		}
		else 
		{
			throw new UnsupportedOperationException(
					"Parent node is of type " + p.getClass().getSimpleName() + ". Child node is of type "
							+ c.getClass().getSimpleName());
		}
		
		
	}
	
	// ====================================================
	// Utility Node Creation
	// ====================================================
	
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
	private AnnotationDeclaration createAnnotationDeclaration(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p) {
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
		List<Modifier.Keyword> keywordlist = attributes.getModifier().stream()
			.map(key -> key.getKeyword())
			.collect(Collectors.toList());
		Modifier.Keyword[] keywords = keywordlist.toArray(new Modifier.Keyword[keywordlist.size()]);
		
		/*
		 * Add the annotation declaration to the compilation unit, with its respective
		 * attributes. This step creates a new annotation declaration and also returns
		 * it.
		 */
		return cu.addAnnotationDeclaration(attributes.getName(), keywords);
	}
	
	/**
	 * This method processes arguments and adds them to the parent.
	 * 
	 * @param attributes Attributes of the argument
	 * @param p          Parent node, which owns the argument
	 * @param n          Argument node
	 * @return whether the descent is to be continued or this method takes over
	 */
	private boolean processArgument(JavaWriterAttributeCollector attributes, com.github.javaparser.ast.Node p, Node n) {
		// Arguments may be containers of other arguments (calls), containers of expressions (calls) or have type/name (declaration)
		if (attributes.getType() != null && attributes.getName() != null) {
			// Parameter with type and name and eventually modifiers, e.g. method decl
			Parameter argument = new Parameter(attributes.getType(), attributes.getName())
												.setModifiers(attributes.getModifier())
												.setAnnotations(attributes.getAnnotation());
			((NodeWithParameters<?>) p).addParameter(argument);
			// For Annotations with Members, we need to traverse from this node onward
			n.getChildren().forEach(c -> parseE4Tree(argument, c));
			
		} else {
			if (n.getParent().getStandardizedNodeType() == NodeType.METHOD_DECLARATION) {
				// just a container, do nothing and descent further
				return true;
			} else {
				// Method Call -> Collect actual arguments and descent from there
				List<Node> expressions = n.getChildren().stream()
						.map(Node::getChildren)
						.flatMap(Collection::stream)
						.collect(Collectors.toList());
				for (Node expr : expressions) {
					parseE4Tree(p, expr);
				}
				return false;
			}
		}
		return false;
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
	 * The way of structuring arrays differs from e4CF to JavaParser so we need to process the access
	 * manually and set index and expressions correctly
	 * @param n begin of an array access expression
	 * @return JavaParser ArrayAccessExpression containing the whole subtree
	 */
	private com.github.javaparser.ast.Node processArrayAccess(Node n) {
		JavaWriterAttributeCollector attributes = new JavaWriterAttributeCollector();
		attributes.collectAttributes(n);
		
		ArrayAccessExpr access = new ArrayAccessExpr();
		access.setIndex(attributes.getValue());
		
		// Tree is basically a list from now on
		Node c = n;
		ArrayAccessExpr subAccess = access;
		while (!c.getChildren().isEmpty()) {
			c = c.getChildren().get(0);
			attributes.collectAttributes(c);
			if (c.getNodeType() == JavaNodeTypes.NameExpr.name()) {
				// Leaf node, array name
				subAccess.setName(new NameExpr(attributes.getName()));
			} else if (c.getNodeType() == JavaNodeTypes.ArrayAccessExpr.name()) {
				// Another Access Level
				ArrayAccessExpr nextAccess = new ArrayAccessExpr();
				subAccess.setName(nextAccess);
				nextAccess.setIndex(attributes.getValue());
				subAccess = nextAccess;
			} else {
				// Other kinds of expressions, methodCallExpr...
				subAccess.setName((Expression) parseE4Tree(null, c));
				// Stop descending
				break;
			}
		}
		
		return access;
	}
	
}
