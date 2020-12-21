package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.JavaAttributesTypes;

/**
 * This class collects all attributes of a Node and converts them back to the JavaParser type.
 * 
 * @author Paulo Haas
 *
 */
public class JavaWriterAttributeCollector {
	private NodeList<AnnotationExpr> _annotation = new NodeList<AnnotationExpr>();
	private Statement _assignment = null;
	private boolean _asteriks = false;
	private NodeList<ClassOrInterfaceType> _bound = new NodeList<ClassOrInterfaceType>();
	private Expression _cast = null;
	private Expression _check = null;
	private int _children = 0;
	private String _class = new String();
	private Expression _comparison = null;
	private NodeList<Expression> _condition = new NodeList<Expression>();
	private boolean _default = false;
	private Expression _else = null;
	private Expression _expression = null;
	private String _identifier = new String();
	private Expression _initilization = null;
	private NodeList<ClassOrInterfaceType> _interface = new NodeList<ClassOrInterfaceType>();
	private boolean _isinterface = false;
	private Expression _iterator = null;
	private String _key = new String();
	private Expression _message = null;
	private NodeList<Modifier> _modifier = new NodeList<Modifier>();
	private String _name = new String();
	private String _package = new String();
	private Type _returnType = null;
	private Expression _scope = null;
	private Expression _selector = null;
	private Statement _statement = null;
	private boolean _static = false;
	private String _superclass = new String();
	private Expression _superexpr = null;
	private Expression _target = null;
	private Expression _then = null;
	private Type _throws = null;
	private Type _type = null;
	private TypeParameter _typeargument = null;
	private Expression _update = null;
	private Expression _value = null;

	/**
	 * Fills the attributes of the object.
	 * 
	 * @param n Node, whose attributes should be collected.
	 * @throws UnsupportedOperationException if an attribute has a key, that is not yet supported.
	 */
	public void collectAttributes(Node n) throws UnsupportedOperationException {
		for (Attribute attribute : n.getAttributes()) {
			String key = attribute.getAttributeKey();
			String singleVal = attribute.getAttributeValues().iterator().next();

			if (key.equals(JavaAttributesTypes.Annotation.name())) {
				attribute.getAttributeValues().forEach(val -> _annotation.add(StaticJavaParser.parseAnnotation(val)));
			} else if (key.equals(JavaAttributesTypes.Assignment.name())) {
				_assignment = StaticJavaParser.parseStatement(singleVal);
			} else if (key.equals(JavaAttributesTypes.Asterisks.name())) {
				_asteriks = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.Bound.name())) {
				attribute.getAttributeValues()
						.forEach(val -> _bound.add(StaticJavaParser.parseClassOrInterfaceType(val)));
			} else if (key.equals(JavaAttributesTypes.Cast.name())) {
				_cast = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Check.name())) {
				_check = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Children.name())) {
				_children = Integer.parseInt(singleVal);
			} else if (key.equals(JavaAttributesTypes.Class.name())) {
				_class = singleVal;
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
				_initilization = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Interface.name())) {
				attribute.getAttributeValues().forEach(val -> _interface.add(StaticJavaParser.parseClassOrInterfaceType(val)));
			} else if (key.equals(JavaAttributesTypes.IsInterface.name())) {
				_isinterface = Boolean.valueOf(singleVal);
			} else if (key.equals(JavaAttributesTypes.Iterator.name())) {
				_iterator = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Key.name())) {
				_key = singleVal;
			} else if (key.equals(JavaAttributesTypes.Message.name())) {
				_message = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Modifier.name())) {
				attribute.getAttributeValues()
						.forEach(val -> _modifier.add(new Modifier(Modifier.Keyword.valueOf(singleVal))));
			} else if (key.equals(JavaAttributesTypes.Name.name())) {
				_name = singleVal;
			} else if (key.equals(JavaAttributesTypes.Package.name())) {
				_package = singleVal;
			} else if (key.equals(JavaAttributesTypes.ReturnType.name())) {
				_returnType = StaticJavaParser.parseType(singleVal);
			} else if (key.equals(JavaAttributesTypes.Scope.name())) {
				_scope = StaticJavaParser.parseExpression(singleVal);
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
				_throws = StaticJavaParser.parseType(singleVal);
			} else if (key.equals(JavaAttributesTypes.Type.name())) {
				_type = StaticJavaParser.parseType(singleVal);
			} else if (key.equals(JavaAttributesTypes.TypeArgument.name())) {
				_typeargument = StaticJavaParser.parseTypeParameter(singleVal);
			} else if (key.equals(JavaAttributesTypes.Update.name())) {
				_update = StaticJavaParser.parseExpression(singleVal);
			} else if (key.equals(JavaAttributesTypes.Value.name())) {
				_value = StaticJavaParser.parseExpression(singleVal);
			} else {
				throw new UnsupportedOperationException(key + " has not been implemented.");
			}
		}
	}

	public NodeList<AnnotationExpr> getAnnotation() {
		return _annotation;
	}

	public Statement getAssignment() {
		return _assignment;
	}

	public boolean isAsteriks() {
		return _asteriks;
	}

	public NodeList<ClassOrInterfaceType> getBound() {
		return _bound;
	}

	public Expression getCast() {
		return _cast;
	}

	public Expression getCheck() {
		return _check;
	}

	public int getChildren() {
		return _children;
	}

	public String getClassAttribute() {
		return _class;
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

	public Expression getInitilization() {
		return _initilization;
	}

	public NodeList<ClassOrInterfaceType> getInterface() {
		return _interface;
	}

	public boolean isInterface() {
		return _isinterface;
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

	public String getPackage() {
		return _package;
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

	public Type getThrows() {
		return _throws;
	}

	public Type getType() {
		return _type;
	}

	public TypeParameter getTypeArgument() {
		return _typeargument;
	}

	public Expression getUpdate() {
		return _update;
	}

	public Expression getValue() {
		return _value;
	}
}