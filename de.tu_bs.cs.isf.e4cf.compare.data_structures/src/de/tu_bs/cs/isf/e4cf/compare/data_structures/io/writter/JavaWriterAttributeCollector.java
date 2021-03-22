package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaAttributesTypes;

/**
 * This class collects all attributes of a Node and converts them back to the
 * JavaParser type.
 * 
 * @author Paulo Haas
 *
 */
public class JavaWriterAttributeCollector {
	private NodeList<AnnotationExpr> _annotation = new NodeList<AnnotationExpr>();
	private boolean _asteriks = false;
	private NodeList<ClassOrInterfaceType> _bound = new NodeList<ClassOrInterfaceType>();
	private Expression _check = null;
	private int _children = 0;
	private String _comment = new String();
	private Expression _comparison = null;
	private NodeList<Expression> _condition = new NodeList<Expression>();
	private boolean _default = false;
	private Expression _else = null;
	private Expression _expression = null;
	private String _identifier = new String();
	private NodeList<Expression> _initilization = new NodeList<Expression>();
	private NodeList<ClassOrInterfaceType> _interface = new NodeList<ClassOrInterfaceType>();
	private boolean _isEnclosingParameters = false;
	private boolean _isEnum = false;
	private boolean _isinterface = false;
	private boolean _isThis = true;
	private Expression _iterator = null;
	private String _key = new String();
	private Expression _message = null;
	private NodeList<Modifier> _modifier = new NodeList<Modifier>();
	private String _name = new String();
	private String _operator = new String();
	private String _package = new String();
	private NodeList<Expression> _resource = new NodeList<Expression>();
	private Type _returnType = null;
	private Expression _scope = null;
	private Expression _selector = null;
	private Statement _statement = null;
	private boolean _static = false;
	private String _superclass = new String();
	private Expression _superexpr = null;
	private Expression _target = null;
	private Expression _then = null;
	private NodeList<ReferenceType> _throws = new NodeList<ReferenceType>();
	private Type _type = null;
	private TypeParameter _typeargument = null;
	private NodeList<Type> _typeParameterBound = new NodeList<Type>();
	private NodeList<ReferenceType> _unionType = new NodeList<ReferenceType>();
	private NodeList<Expression> _update = new NodeList<Expression>();
	private Expression _value = null;

	/**
	 * Fills the attributes of the object.
	 * 
	 * @param n Node, whose attributes should be collected.
	 * @throws UnsupportedOperationException if an attribute has a key, that is not
	 *                                       yet supported.
	 */
	public void collectAttributes(Node n) throws UnsupportedOperationException {
		for (Attribute attribute : n.getAttributes()) {
			final String key = attribute.getAttributeKey();
			final String singleVal = attribute.getAttributeValues().iterator().next();

			if (key.equals(JavaAttributesTypes.Annotation.name())) {
				for (String value : attribute.getAttributeValues()) {
					// Annotations start with an at-symbol
					if (!value.startsWith("@")) {
						value = "@" + value;
					}
					_annotation.add(StaticJavaParser.parseAnnotation(value));
				}
			} else if (key.equals(JavaAttributesTypes.Asterisks.name())) {
				_asteriks = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.Bound.name())) {
				/*
				 * Attribute Bound contains the number of bound children
				 * 
				 * attribute.getAttributeValues() .forEach(val ->
				 * _bound.add(StaticJavaParser.parseClassOrInterfaceType(val)));
				 */
			} else if (key.equals(JavaAttributesTypes.Check.name())) {
				_check = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Children.name())) {
				_children = Integer.parseInt(singleVal);
			} else if (key.equals(JavaAttributesTypes.Comment.name())) {
				_comment = singleVal;
			} else if (key.equals(JavaAttributesTypes.Comparison.name())) {
				_comparison = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Condition.name())) {
				attribute.getAttributeValues().forEach(val -> _condition.add(StaticJavaParser.parseExpression(val)));
			} else if (key.equals(JavaAttributesTypes.Default.name())) {
				_default = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.Else.name())) {
				_else = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Expression.name())) {
				_expression = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Identifier.name())) {
				_identifier = singleVal;
			} else if (key.equals(JavaAttributesTypes.Initilization.name())) {
				Expression expr;
				for (String val : attribute.getAttributeValues()) {
					if (val.startsWith("{") && val.endsWith("}")) {
						expr = StaticJavaParser.parseExpression("new " + getType().asString() + " " + val);
					} else {
						try {
							expr = StaticJavaParser.parseExpression(val);
						} catch (ParseProblemException ppe) {
							expr = StaticJavaParser.parseVariableDeclarationExpr(val);
						}
					}
					_initilization.add(expr);
				}
			} else if (key.equals(JavaAttributesTypes.Interface.name())) {
				attribute.getAttributeValues()
						.forEach(val -> _interface.add(StaticJavaParser.parseClassOrInterfaceType(val)));
			} else if (key.equals(JavaAttributesTypes.isEnclosingParameters.name())) {
				_isEnclosingParameters = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.IsEnum.name())) {
				_isEnum = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.IsInterface.name())) {
				_isinterface = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.IsThis.name())) {
				_isThis = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.Iterator.name())) {
				_iterator = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Key.name())) {
				_key = singleVal;
			} else if (key.equals(JavaAttributesTypes.Message.name())) {
				_message = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Modifer.name())) {
				attribute.getAttributeValues()
						.forEach(val -> _modifier.add(new Modifier(Modifier.Keyword.valueOf(val))));
			} else if (key.equals(JavaAttributesTypes.Name.name())) {
				_name = singleVal;
			} else if (key.equals(JavaAttributesTypes.Operator.name())) {
				_operator = singleVal;
			} else if (key.equals(JavaAttributesTypes.Package.name())) {
				_package = singleVal;
			} else if (key.equals(JavaAttributesTypes.Resource.name())) {
				attribute.getAttributeValues()
						.forEach(val -> _resource.add(StaticJavaParser.parseVariableDeclarationExpr(val)));
			} else if (key.equals(JavaAttributesTypes.ReturnType.name())) {
				_returnType = StaticJavaParser.parseType(singleVal);
			} else if (key.equals(JavaAttributesTypes.Scope.name())) {
				if (singleVal.equals("super")) {
					/*
					 * When the scope is "super" the JavaParser can't parse the expression (v3.18.0)
					 * so the superexpr is created manually.
					 */
					_scope = new SuperExpr();
				} else {
					_scope = StaticJavaParser.parseExpression(singleVal);
				}
			} else if (key.equals(JavaAttributesTypes.Selector.name())) {
				_selector = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Statement.name())) {
				_statement = StaticJavaParser.parseStatement(singleVal);
			} else if (key.equals(JavaAttributesTypes.Static.name())) {
				_static = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.Superclass.name())) {
				_superclass = singleVal;
			} else if (key.equals(JavaAttributesTypes.SuperExpr.name())) {
				_superexpr = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Target.name())) {
				_target = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Then.name())) {
				_then = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Throws.name())) {
				attribute.getAttributeValues()
						.forEach(val -> _throws.add(StaticJavaParser.parseClassOrInterfaceType(val)));
			} else if (key.equals(JavaAttributesTypes.Type.name())) {
				if (!singleVal.contains("|")) {
					_type = StaticJavaParser.parseType(singleVal);
				} else {
					for (String type : singleVal.split("\\|")) {
						_unionType.add(StaticJavaParser.parseClassOrInterfaceType(type));
					}
				}
			} else if (key.equals(JavaAttributesTypes.TypeArgument.name())) {
				_typeargument = StaticJavaParser.parseTypeParameter(singleVal);
			} else if (key.equals(JavaAttributesTypes.TypeParameterBound.name())) {
				attribute.getAttributeValues().forEach(val -> {
					if (val.equals("?")) {
						// Question mark type seems unparseable, so this is done manually
						_typeParameterBound.add(new ClassOrInterfaceType("?"));
					} else {
						_typeParameterBound.add(StaticJavaParser.parseClassOrInterfaceType(val));
					}
				});
			} else if (key.equals(JavaAttributesTypes.Update.name())) {
				attribute.getAttributeValues().forEach(val -> _update.add(StaticJavaParser.parseExpression(val)));
			} else if (key.equals(JavaAttributesTypes.Value.name())) {
				/*
				 * The value can be an array initializer expr, e.g. "{0, 1, 2}", which cannot be parsed. This
				 * needs some manual work.
				 */
				if (singleVal.startsWith("{") && singleVal.endsWith("}")) {
					NodeList<Expression> values = new NodeList<Expression>();
					for (String v : singleVal.substring(1,singleVal.length()-1).split(",")) {
						values.add(StaticJavaParser.parseExpression(v));
					}
					_value = new ArrayInitializerExpr(values);
				} else {
					_value = StaticJavaParser.parseExpression(singleVal);
				}
			} else {
				throw new UnsupportedOperationException(key + " has not been implemented.");
			}
		}
	}

	public NodeList<AnnotationExpr> getAnnotation() {
		return _annotation;
	}

	public boolean isAsteriks() {
		return _asteriks;
	}

	public NodeList<ClassOrInterfaceType> getBound() {
		return _bound;
	}

	public Expression getCheck() {
		return _check;
	}

	public int getChildren() {
		return _children;
	}

	public String getComment() {
		return _comment;
	}

	public Expression getComparison() {
		return _comparison;
	}

	public NodeList<Expression> getCondition() {
		return _condition;
	}

	public boolean isDefault() {
		return _default;
	}

	public Expression getElse() {
		return _else;
	}

	public Expression getExpression() {
		return _expression;
	}

	public String getIdentifier() {
		return _identifier;
	}

	public NodeList<Expression> getInitilization() {
		return _initilization;
	}

	public NodeList<ClassOrInterfaceType> getInterface() {
		return _interface;
	}

	public boolean isEnclosingParameters() {
		return _isEnclosingParameters;
	}

	public boolean isInterface() {
		return _isinterface;
	}

	public boolean isThis() {
		return _isThis;
	}

	public Expression getIterator() {
		return _iterator;
	}

	public String getKey() {
		return _key;
	}

	public Expression getMessage() {
		return _message;
	}

	public NodeList<Modifier> getModifier() {
		return _modifier;
	}

	public String getName() {
		return _name;
	}

	public String getOperator() {
		return _operator;
	}

	public String getPackage() {
		return _package;
	}

	public NodeList<Expression> getResource() {
		return _resource;
	}

	public Type getReturnType() {
		return _returnType;
	}

	public Expression getScope() {
		return _scope;
	}

	public Expression getSelector() {
		return _selector;
	}

	public Statement getStatement() {
		return _statement;
	}

	public boolean isStatic() {
		return _static;
	}

	public String getSuperclass() {
		return _superclass;
	}

	public Expression getSuperExpr() {
		return _superexpr;
	}

	public Expression getTarget() {
		return _target;
	}

	public Expression getThen() {
		return _then;
	}

	public NodeList<ReferenceType> getThrows() {
		return _throws;
	}

	public Type getType() {
		Type t = _type;
		if (t == null && !_unionType.isEmpty()) {
			t = new UnionType(_unionType);
		}
		return t;
	}

	public TypeParameter getTypeArgument() {
		return _typeargument;
	}

	public NodeList<Type> getTypeParameterBound() {
		return _typeParameterBound;
	}

	public NodeList<Expression> getUpdate() {
		return _update;
	}

	public Expression getValue() {
		return _value;
	}

	public boolean isEnum() {
		return _isEnum;
	}
}