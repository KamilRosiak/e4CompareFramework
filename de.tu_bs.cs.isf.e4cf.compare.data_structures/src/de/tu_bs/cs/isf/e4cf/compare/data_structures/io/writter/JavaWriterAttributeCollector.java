package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.UnionType;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
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
	private String _comment = new String();
	private NodeList<Expression> _condition = new NodeList<Expression>();
	private boolean _default = false;
	private Expression _expression = null;
	private String _identifier = new String();
	private NodeList<ClassOrInterfaceType> _interface = new NodeList<ClassOrInterfaceType>();
	private boolean _isEnclosingParameters = false;
	private boolean _isEnum = false;
	private boolean _isinterface = false;
	private boolean _isThis = true;
	private Expression _iterator = null;
	private String _key = new String();
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
	private Expression _target = null;
	private Expression _then = null;
	private NodeList<ReferenceType> _throws = new NodeList<ReferenceType>();
	private Type _type = null;
	private NodeList<Type> _typearguments = new NodeList<Type>();
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
			final StringValueImpl singleVal = (StringValueImpl) attribute.getAttributeValues().iterator().next();
			
			/* 
			 * Modifiers need to be taken care of beforehand, as they are not all listed individually in enum.
			 * E.g. "STATIC_modifier" should be taken care of in place of general modifiers
			 */
			if (key.contains(JavaAttributesTypes.Modifer.name())) {
				collectModifier(attribute);
				continue;
			}
			
			switch (JavaAttributesTypes.valueOf(key)) {
			case AccessModifier:
				collectModifier(attribute);
				break;
				
			case Annotation:
				for (Value<?> value : attribute.getAttributeValues()) {
					// Annotations start with an at-symbol
					if(value instanceof StringValueImpl) {
						StringValueImpl stringVal = (StringValueImpl)value;
						if (!stringVal.getValue().startsWith("@")) {
							stringVal.setValue( "@" + value.getValue());
						}
						_annotation.add(StaticJavaParser.parseAnnotation(stringVal.getValue()));
					}
				}
				break;
				
			case Asterisks:
				_asteriks = Boolean.valueOf(singleVal.getValue());
				break;
				
			case Bound:
				break;
				
			case Comment:
				_comment = singleVal.getValue();
				break;
				
			case Condition:
				attribute.getAttributeValues().forEach(val -> _condition.add(StaticJavaParser.parseExpression((String) val.getValue())));
				break;
				
			case Default:
				_default = Boolean.valueOf(singleVal.getValue());
				break;
				
			case Expression:
				_expression = StaticJavaParser.parseExpression(singleVal.getValue());
				break;
				
			case Identifier:
				_identifier = singleVal.getValue();
				break;
				
			case Interface:
				attribute.getAttributeValues().forEach(val -> 
					_interface.add(StaticJavaParser.parseClassOrInterfaceType((String) val.getValue())));
				break;
				
			case IsEnum:
				_isEnum = Boolean.valueOf(singleVal.getValue());
				break;
				
			case IsInterface:
				_isinterface = Boolean.valueOf(singleVal.getValue());
				break;
				
			case IsThis:
				_isThis = Boolean.valueOf(singleVal.getValue());
				break;
				
			case Iterator:
				_iterator = StaticJavaParser.parseExpression(singleVal.getValue());
				break;
				
			case Key:
				_key = singleVal.getValue();
				break;
				
			case Name:
				_name = singleVal.getValue();
				break;
				
			case NonAccessModifier:
				break;
				
			case Operator:
				_operator = singleVal.getValue();
				break;
				
			case Package:
				_package = singleVal.getValue();
				break;
				
			case Resource:
				attribute.getAttributeValues().forEach(val ->
					_resource.add(StaticJavaParser.parseVariableDeclarationExpr((String) val.getValue())));
				break;
				
			case ReturnType:
				_returnType = StaticJavaParser.parseType(singleVal.getValue());
				break;
				
			case Scope:
				try {
					// Try parsing as type to preserve Generics/Typing
					_scope = new TypeExpr(StaticJavaParser.parseType(singleVal.getValue()));
				} catch (ParseProblemException ppe) {
					// Fallback to any expression, if type expr did not match
					_scope = StaticJavaParser.parseExpression(singleVal.getValue());
				}
				break;
				
			case Selector:
				_selector = StaticJavaParser.parseExpression(singleVal.getValue());
				break;
				
			case Statement:
				_statement = StaticJavaParser.parseStatement(singleVal.getValue());
				break;
				
			case Static:
				_static = Boolean.valueOf(singleVal.getValue());
				break;
				
			case Superclass:
				_superclass = singleVal.getValue();
				break;
				
			case Target:
				_target = StaticJavaParser.parseExpression(singleVal.getValue());
				break;
				
			case Throws:
				attribute.getAttributeValues().forEach(val -> 
					_throws.add(StaticJavaParser.parseClassOrInterfaceType((String) val.getValue())));
				break;
				
			case Type:
				if (!singleVal.getValue().contains("|")) {
					if (singleVal.getValue() != "null") {
						_type = StaticJavaParser.parseType(singleVal.getValue());
					}
				} else {
					for (String type : singleVal.getValue().split("\\|")) {
						_unionType.add(StaticJavaParser.parseClassOrInterfaceType(type));
					}
				}
				break;
				
			case TypeArgument:
				// Might be multiple <A[],B...>
				attribute.getAttributeValues().forEach(v -> {
					Type t = StaticJavaParser.parseType((String) v.getValue());
					_typearguments.add(t);
					});
				break;
				
			case Value:
				if (singleVal.getValue().contains("?")) {
					// Don't parse it, instead: just append the extendedType here
					// IMO this shouldn't even be here but I can't find it in Reader
					String ext = singleVal.getValue().replaceFirst("\\? extends ", "");
					_typearguments.add(StaticJavaParser.parseType(ext));
				} else {
					_value = StaticJavaParser.parseExpression(singleVal.getValue());
				}
				break;
				
			case isEnclosingParameters:
				_isEnclosingParameters = Boolean.valueOf(singleVal.getValue());
				break;
				
			default:
				System.out.println(key);
				throw new UnsupportedOperationException(key + " has not been implemented.");
			}
			
		}
	}

	private void collectModifier(Attribute attribute) {
		attribute.getAttributeValues()
			.forEach(val -> _modifier.add(new Modifier(Modifier.Keyword.valueOf((String) val.getValue()))));
	}
	
	
	// ================================================================================================================
	// Getter Section
	// ================================================================================================================
	
	public NodeList<AnnotationExpr> getAnnotation() {
		return _annotation;
	}

	public boolean isAsteriks() {
		return _asteriks;
	}

	public NodeList<ClassOrInterfaceType> getBound() {
		return _bound;
	}

	public String getComment() {
		return _comment;
	}

	public NodeList<Expression> getCondition() {
		return _condition;
	}

	public boolean isDefault() {
		return _default;
	}

	public Expression getExpression() {
		return _expression;
	}

	public String getIdentifier() {
		return _identifier;
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

	public NodeList<Type> getTypeArguments() {
		return _typearguments;
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