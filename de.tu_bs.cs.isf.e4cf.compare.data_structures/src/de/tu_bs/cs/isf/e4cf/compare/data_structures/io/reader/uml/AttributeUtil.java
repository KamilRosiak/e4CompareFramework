package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.Package;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.BoolValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.IntegerValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ListValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

class AttributeUtil {

	static void setCommonAttributes(Node node, NamedElement element) {
		setNamedElementAttributes(node, element);
		if (node instanceof Classifier) {
			setClassifierAttributes(node, (Classifier) element);
		}
	}

	/**
	 * Sets the name, visibility, qualified name, namespace properties of the
	 * element as attributes of the node
	 * 
	 * @param node    The node to add the attributes to
	 * @param element The named element
	 */
	private static void setNamedElementAttributes(Node node, NamedElement element) {
		if (isNotEmpty(element.getName())) {
			node.addAttribute(UMLAttr.NAME.name(), new StringValueImpl(element.getName()));
			node.setRepresentation(element.getName());
		}
		if (element.getVisibility() != null && isNotEmpty(element.getVisibility().getLiteral())) {
			node.addAttribute(UMLAttr.VISIBILITY.name(), new StringValueImpl(element.getVisibility().getLiteral()));
		}
		if (isNotEmpty(element.getQualifiedName())) {
			node.addAttribute(UMLAttr.QUALIFIED_NAME.name(), new StringValueImpl(element.getQualifiedName()));
		}
		if (element.getNamespace() != null && isNotEmpty(element.getNamespace().getName())) {
			node.addAttribute(UMLAttr.NAMESPACE.name(), new StringValueImpl(element.getNamespace().getName()));
		}
	}

	private static void setClassifierAttributes(Node node, Classifier classifier) {
		node.addAttribute(UMLAttr.ABSTRACT.name(), new BoolValueImpl(classifier.isAbstract()));
		node.addAttribute(UMLAttr.FINAL.name(), new BoolValueImpl(classifier.isFinalSpecialization()));
		node.addAttribute(UMLAttr.LEAF.name(), new BoolValueImpl(classifier.isLeaf()));
	}

	static void setClassAttributes(Node node, org.eclipse.uml2.uml.Class clazz) {
		setCommonAttributes(node, clazz);
		setClassifierAttributes(node, clazz);
		node.addAttribute(UMLAttr.ACTIVE.name(), new BoolValueImpl(clazz.isActive()));
	}

	static void setAssociationAttributes(Node node, Association association) {
		setCommonAttributes(node, association);
		setClassifierAttributes(node, association);
		node.addAttribute(UMLAttr.DERIVED.name(), new BoolValueImpl(association.isDerived()));
	}

	static void setPropertyAttributes(Node node, Property property) {
		setCommonAttributes(node, property);
		node.addAttribute(UMLAttr.DERIVED.name(), new BoolValueImpl(property.isDerived()));
		node.addAttribute(UMLAttr.DERIVED_UNION.name(), new BoolValueImpl(property.isDerivedUnion()));
		node.addAttribute(UMLAttr.ID.name(), new BoolValueImpl(property.isID()));
		node.addAttribute(UMLAttr.LEAF.name(), new BoolValueImpl(property.isLeaf()));
		node.addAttribute(UMLAttr.ORDERED.name(), new BoolValueImpl(property.isOrdered()));
		node.addAttribute(UMLAttr.READ_ONLY.name(), new BoolValueImpl(property.isReadOnly()));
		node.addAttribute(UMLAttr.STATIC.name(), new BoolValueImpl(property.isStatic()));
		node.addAttribute(UMLAttr.UNIQUE.name(), new BoolValueImpl(property.isUnique()));
		
		node.addAttribute(UMLAttr.AGGREGATION.name(), new StringValueImpl(property.getAggregation().name()));
		node.addAttribute(UMLAttr.UPPER.name(), new IntegerValueImpl(property.getUpper()));
		node.addAttribute(UMLAttr.LOWER.name(), new IntegerValueImpl(property.getLower()));
		if (property.getType() != null)
			setTypeAttribute(node, property);
		
	}
	
	static void setOperationAttributes(Node node, Operation operation) {
		setCommonAttributes(node, operation);
		if (operation.getConcurrency().getLiteral() != null && isNotEmpty(operation.getConcurrency().getLiteral())) {
			node.addAttribute(UMLAttr.CONCURRENCY.name(),
					new StringValueImpl(operation.getConcurrency().getLiteral()));
		}
		node.addAttribute(UMLAttr.ABSTRACT.name(), new BoolValueImpl(operation.isAbstract()));
		node.addAttribute(UMLAttr.LEAF.name(), new BoolValueImpl(operation.isLeaf()));
		node.addAttribute(UMLAttr.ORDERED.name(), new BoolValueImpl(operation.isOrdered()));
		node.addAttribute(UMLAttr.QUERY.name(), new BoolValueImpl(operation.isQuery()));
		node.addAttribute(UMLAttr.STATIC.name(), new BoolValueImpl(operation.isStatic()));
		node.addAttribute(UMLAttr.UNIQUE.name(), new BoolValueImpl(operation.isUnique()));
		
		node.addAttribute(UMLAttr.UPPER.name(), new IntegerValueImpl(operation.getUpper()));
		node.addAttribute(UMLAttr.LOWER.name(), new IntegerValueImpl(operation.getLower()));
		
		if (operation.getType() != null) {
			setTypeAttribute(node, operation);
		}
	}
	
	static void setElementImportAttributes(Node node, ElementImport elementImport) {
		if (elementImport.getVisibility() != null && isNotEmpty(elementImport.getVisibility().getLiteral())) {
			node.addAttribute(UMLAttr.VISIBILITY.name(), new StringValueImpl(elementImport.getVisibility().getLiteral()));
		}
		if (isNotEmpty(elementImport.getAlias())) {
			node.addAttribute(UMLAttr.ALIAS.name(), new StringValueImpl(elementImport.getAlias()));
		}
		if (isNotEmpty(elementImport.getImportedElement().getName())) {
			node.addAttribute(UMLAttr.IMPORTED_ELEMENT.name(), new StringValueImpl(elementImport.getImportedElement().getName()));
		}		
	}
	
	static void setDataTypeAttributes(Node node, DataType dataType) {
		setCommonAttributes(node, dataType);
		setClassifierAttributes(node, dataType);
	}
	
	static void setLiteralStringAttributes(Node node, LiteralString literalString) {
		setCommonAttributes(node, literalString);
		if (literalString.getValue() != null) {
			node.addAttribute(UMLAttr.VALUE.name(), new StringValueImpl(literalString.getValue()));
		}
	}
	
	static void setLiteralIntegerAttributes(Node node, LiteralInteger literalInteger) {
		setCommonAttributes(node, literalInteger);
		node.addAttribute(UMLAttr.VALUE.name(), new IntegerValueImpl(literalInteger.getValue()));
	}
	
	static void setLiteralBooleanAttributes(Node node, LiteralBoolean literalBoolean) {
		setCommonAttributes(node, literalBoolean);
		node.addAttribute(UMLAttr.VALUE.name(), new BoolValueImpl(literalBoolean.isValue()));
	}
	
	static void setLiteralUnlimitedNaturalAttributes(Node node, LiteralUnlimitedNatural literalUnlimitedNatural) {
		setCommonAttributes(node, literalUnlimitedNatural);
		node.addAttribute(UMLAttr.VALUE.name(), new IntegerValueImpl(literalUnlimitedNatural.getValue()));
		
	}
	
	static void setParameterAttributes(Node node, Parameter parameter) {
		setCommonAttributes(node, parameter);
		if (parameter.getDirection().getLiteral() != null /*&& isNotEmpty(parameter.getDirection()).getLiteral()*/) {
			node.addAttribute(UMLAttr.DIRECTION.name(),
					new StringValueImpl(parameter.getDirection().getLiteral()));
		}
		if (parameter.getEffect().getLiteral() != null && isNotEmpty(parameter.getEffect().getLiteral())) {
			node.addAttribute(UMLAttr.EFFECT.name(),
					new StringValueImpl(parameter.getEffect().getLiteral()));
		}
		node.addAttribute(UMLAttr.EXCEPTION.name(), new BoolValueImpl(parameter.isException()));
		node.addAttribute(UMLAttr.ORDERED.name(), new BoolValueImpl(parameter.isOrdered()));
		node.addAttribute(UMLAttr.STREAM.name(), new BoolValueImpl(parameter.isStream()));
		node.addAttribute(UMLAttr.UNIQUE.name(), new BoolValueImpl(parameter.isUnique()));
		if (parameter.getType() != null) {
			setTypeAttribute(node, parameter);
		}
		
	}
	
	static void setInterfaceAttributes(Node node, Interface interfaze) {
		setCommonAttributes(node, interfaze);
		setClassifierAttributes(node, interfaze);
	}
	
	static void setPackageElementAttributes(Node node, Package pac) {
		setCommonAttributes(node, pac);
	}
	
	static void setEnumerationAttributes(Node node, Enumeration enumeration) {
		setCommonAttributes(node, enumeration);
		setClassifierAttributes(node, enumeration);
	}
	
	static void setEnumerationLiteralAttributes(Node node, EnumerationLiteral enumLiteral) {
		setCommonAttributes(node, enumLiteral);
	}
	
	public static void setGeneralizationAttributes(Node generalizationNode, Generalization generalization) {
		if (generalization.getGeneral() != null) {
			String general = generalization.getGeneral().toString();
			generalizationNode.addAttribute(UMLAttr.GENERAL.name(), new StringValueImpl(general));
		}
		generalizationNode.addAttribute(UMLAttr.SUBSTITUTABLE.name(), new BoolValueImpl(generalization.isSubstitutable()));
	}
	
	static void setInterfaceRealizationAttributes(Node node, InterfaceRealization interfaceRealization) {
		if (interfaceRealization.getClients() != null) {
			List<String> clientNames = interfaceRealization.getClients().stream()
					.map(NamedElement::getName)
					.collect(Collectors.toList());
			node.addAttribute(UMLAttr.CLIENT.name(), new ListValueImpl<>(clientNames));
		}
		if (interfaceRealization.getContract() != null) {
			String realization = interfaceRealization.getContract().toString();
			node.addAttribute(UMLAttr.CONTRACT.name(), new StringValueImpl(realization));
		}
		if (interfaceRealization.getSuppliers() != null) {
			List<String> supplierNames = interfaceRealization.getSuppliers().stream()
					.map(NamedElement::getName)
					.collect(Collectors.toList());
			node.addAttribute(UMLAttr.SUPPLIER.name(), new ListValueImpl<>(supplierNames));
		}
	}
	

	private static boolean isNotEmpty(String string) {
		return string != null && !string.isEmpty();
	}
	
	private static void setTypeAttribute(Node node, NamedElement element) {
		Type type;
		if (element instanceof TypedElement) {
			type = ((TypedElement) element).getType();
		} else if (element instanceof Operation) {
			type = ((Operation) element).getType();
		} else {
			return;
		}
		
		if (isNotEmpty(type.getName())) {
			node.addAttribute(UMLAttr.TYPE.name(), new StringValueImpl(type.getName()));
		} else { // primitive type
			Optional<String> typeName = retrievePrimitiveTypeName(type);
			if (typeName.isPresent()) {
				node.addAttribute(UMLAttr.TYPE.name(), new StringValueImpl(typeName.get()));
			}
		}
	}
	
	private static Optional<String> retrievePrimitiveTypeName(Type type) {		
		// retrieve type name from private eStorage field using reflection
		try {
			Field eStorageField = MinimalEObjectImpl.class.getDeclaredField("eStorage");
			eStorageField.setAccessible(true);
			Object eStorage = eStorageField.get(type);
			return Optional.of(eStorage.toString());
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	/* Superclass hierarchy
	 * [protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.DataTypeImpl.ownedAttributes, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.DataTypeImpl.ownedOperations, protected static final int[] org.eclipse.uml2.uml.internal.impl.DataTypeImpl.ATTRIBUTE_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.DataTypeImpl.OWNED_MEMBER_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.DataTypeImpl.FEATURE_ESUBSETS]
		[protected static final boolean org.eclipse.uml2.uml.internal.impl.ClassifierImpl.IS_LEAF_EDEFAULT, protected static final int org.eclipse.uml2.uml.internal.impl.ClassifierImpl.IS_LEAF_EFLAG, protected org.eclipse.uml2.uml.TemplateParameter org.eclipse.uml2.uml.internal.impl.ClassifierImpl.templateParameter, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ClassifierImpl.templateBindings, protected org.eclipse.uml2.uml.TemplateSignature org.eclipse.uml2.uml.internal.impl.ClassifierImpl.ownedTemplateSignature, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ClassifierImpl.collaborationUses, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ClassifierImpl.generalizations, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ClassifierImpl.powertypeExtents, protected static final boolean org.eclipse.uml2.uml.internal.impl.ClassifierImpl.IS_ABSTRACT_EDEFAULT, protected static final int org.eclipse.uml2.uml.internal.impl.ClassifierImpl.IS_ABSTRACT_EFLAG, protected static final boolean org.eclipse.uml2.uml.internal.impl.ClassifierImpl.IS_FINAL_SPECIALIZATION_EDEFAULT, protected static final int org.eclipse.uml2.uml.internal.impl.ClassifierImpl.IS_FINAL_SPECIALIZATION_EFLAG, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ClassifierImpl.ownedUseCases, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ClassifierImpl.useCases, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ClassifierImpl.redefinedClassifiers, protected org.eclipse.uml2.uml.CollaborationUse org.eclipse.uml2.uml.internal.impl.ClassifierImpl.representation, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ClassifierImpl.substitutions, protected static final int[] org.eclipse.uml2.uml.internal.impl.ClassifierImpl.REDEFINITION_CONTEXT_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.ClassifierImpl.REDEFINED_ELEMENT_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.ClassifierImpl.OWNED_ELEMENT_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.ClassifierImpl.FEATURE_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.ClassifierImpl.MEMBER_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.ClassifierImpl.OWNED_MEMBER_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.ClassifierImpl.COLLABORATION_USE_ESUBSETS]
		[protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.NamespaceImpl.ownedRules, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.NamespaceImpl.elementImports, protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.NamespaceImpl.packageImports, protected static final int[] org.eclipse.uml2.uml.internal.impl.NamespaceImpl.OWNED_MEMBER_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.NamespaceImpl.OWNED_ELEMENT_ESUBSETS, protected static final int[] org.eclipse.uml2.uml.internal.impl.NamespaceImpl.MEMBER_ESUBSETS]
		[protected static final java.lang.String org.eclipse.uml2.uml.internal.impl.NamedElementImpl.NAME_EDEFAULT, protected java.lang.String org.eclipse.uml2.uml.internal.impl.NamedElementImpl.name, protected static final int org.eclipse.uml2.uml.internal.impl.NamedElementImpl.NAME_ESETFLAG, protected org.eclipse.uml2.uml.StringExpression org.eclipse.uml2.uml.internal.impl.NamedElementImpl.nameExpression, protected static final java.lang.String org.eclipse.uml2.uml.internal.impl.NamedElementImpl.QUALIFIED_NAME_EDEFAULT, protected static final org.eclipse.uml2.uml.VisibilityKind org.eclipse.uml2.uml.internal.impl.NamedElementImpl.VISIBILITY_EDEFAULT, protected static final int org.eclipse.uml2.uml.internal.impl.NamedElementImpl.VISIBILITY_EFLAG_OFFSET, protected static final int org.eclipse.uml2.uml.internal.impl.NamedElementImpl.VISIBILITY_EFLAG_DEFAULT, protected static final org.eclipse.uml2.uml.VisibilityKind[] org.eclipse.uml2.uml.internal.impl.NamedElementImpl.VISIBILITY_EFLAG_VALUES, protected static final int org.eclipse.uml2.uml.internal.impl.NamedElementImpl.VISIBILITY_EFLAG, protected static final int org.eclipse.uml2.uml.internal.impl.NamedElementImpl.VISIBILITY_ESETFLAG, protected static final int[] org.eclipse.uml2.uml.internal.impl.NamedElementImpl.OWNED_ELEMENT_ESUBSETS]
		[protected org.eclipse.emf.common.util.EList org.eclipse.uml2.uml.internal.impl.ElementImpl.ownedComments, protected static final int[] org.eclipse.uml2.uml.internal.impl.ElementImpl.OWNED_ELEMENT_ESUBSETS, private static final java.lang.Class org.eclipse.uml2.uml.internal.impl.ElementImpl.CHANGE_DESCRIPTION_CLASS, private static final int org.eclipse.uml2.uml.internal.impl.ElementImpl.ADAPTING]
		[protected org.eclipse.emf.common.util.EList org.eclipse.emf.ecore.impl.EModelElementImpl.eAnnotations, protected int org.eclipse.emf.ecore.impl.EModelElementImpl.eFlags, protected static final int org.eclipse.emf.ecore.impl.EModelElementImpl.EFROZEN, protected static final int org.eclipse.emf.ecore.impl.EModelElementImpl.ELAST_EMODEL_ELEMENT_FLAG, static final boolean org.eclipse.emf.ecore.impl.EModelElementImpl.$assertionsDisabled]
		[protected org.eclipse.emf.ecore.InternalEObject org.eclipse.emf.ecore.impl.MinimalEObjectImpl$Container.eContainer]
		[private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.NO_DELIVER, private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.CONTAINER, private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.ADAPTER, private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.ADAPTER_LISTENER, private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.CLASS, private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.SETTING, private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.PROXY, private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.RESOURCE, private static final int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.FIELD_MASK, private int org.eclipse.emf.ecore.impl.MinimalEObjectImpl.eFlags, private java.lang.Object org.eclipse.emf.ecore.impl.MinimalEObjectImpl.eStorage]
		[private static final java.lang.String[] org.eclipse.emf.ecore.impl.BasicEObjectImpl.ESCAPE, protected static final int org.eclipse.emf.ecore.impl.BasicEObjectImpl.EVIRTUAL_SET, protected static final int org.eclipse.emf.ecore.impl.BasicEObjectImpl.EVIRTUAL_UNSET, protected static final int org.eclipse.emf.ecore.impl.BasicEObjectImpl.EVIRTUAL_GET, protected static final int org.eclipse.emf.ecore.impl.BasicEObjectImpl.EVIRTUAL_IS_SET, protected static final java.lang.Object org.eclipse.emf.ecore.impl.BasicEObjectImpl.EVIRTUAL_NO_VALUE, static final boolean org.eclipse.emf.ecore.impl.BasicEObjectImpl.$assertionsDisabled]
	 */
	/*
	 * boolean doLoop = false;
					Class<?> superClass = PrimitiveTypeImpl.class;
					while (doLoop && superClass != null) {
						Field[] fields = superClass.getDeclaredFields();
						System.out.println(Arrays.toString(fields));
						superClass = superClass.getSuperclass();
					}
	 */

}
