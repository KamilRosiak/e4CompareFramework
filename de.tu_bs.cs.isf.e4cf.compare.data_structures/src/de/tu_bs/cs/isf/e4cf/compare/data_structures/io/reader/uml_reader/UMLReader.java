package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml_reader;

import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
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
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class UMLReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "uml" };
	private static final UMLPackage instance = UMLPackage.eINSTANCE;
	/**
	 * See the <a href="https://download.eclipse.org/modeling/mdt/uml2/javadoc/4.1.0/org/eclipse/uml2/uml/package-summary.html">
	 * Javadoc</a> for the eclipse UML Reader
	 */
	public UMLReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement fileTreeElement) {		
		Model umlModel = (Model) EMFModelLoader.load(fileTreeElement.getAbsolutePath(), "uml");
		Node rootNode = new NodeImpl(NodeType.MODEL);
		AttributeUtil.setCommonAttributes(rootNode, umlModel);
		
		umlModel.getPackagedElements().forEach(packagedElement -> {
			Node packagedNode = processElement(packagedElement);
			rootNode.addChild(packagedNode);
		});

		String fullFileName = fileTreeElement.getAbsolutePath() + fileTreeElement.getExtension();
		return new TreeImpl(fullFileName, rootNode);
	}

	private Node processModelElement(NamedElement element) {
		NodeImpl node = new NodeImpl("");
		
		if (element instanceof Class) {
			Class classElement = (Class) element;
			node.setStandardizedNodeType(NodeType.CLASS);
			AttributeUtil.setClassAttributes(node, classElement);
			System.out.println("class:" + classElement.getName());
			classElement.allOwnedElements().forEach(innerElement -> {
				if (innerElement instanceof Property) {
					Property property = (Property) innerElement;
					Node propertyNode = new NodeImpl(NodeType.PROPERTY);
					AttributeUtil.setPropertyAttributes(propertyNode, property);
					node.addChild(propertyNode);
//					System.out.println(
//							"    property: " + propertyNode.getAttributeForKey(UMLAttr.NAME.name()).getValue(0) + 
//							" type: " + propertyNode.getAttributeForKey(UMLAttr.TYPE.name()).getValue(0) +
//							" visibility: " + propertyNode.getAttributeForKey(UMLAttr.VISIBILITY.name()).getValue(0));
				} else if (innerElement instanceof Generalization) {
					Generalization generalization = (Generalization) innerElement;
					Node generalizationNode = new NodeImpl(NodeType.GENERALIZATION);
					node.addChild(generalizationNode);
//					System.out.println(
//							"    generalization: " + generalization.getGeneral().getName());
				} else if (innerElement instanceof Operation) {
					Operation operation = (Operation) innerElement;
					Node operationNode = new NodeImpl(NodeType.OPERATION);
					AttributeUtil.setOperationAttributes(node, operation);
					node.addChild(operationNode);
//					System.out.println(
//							"    operation: " + operation.getName() +
//							" visibility: " + operation.getVisibility().getLiteral());
				} else if (innerElement instanceof DataType) {
					DataType dataType = (DataType) innerElement;
					Node dataTypeNode = new NodeImpl(NodeType.DATA_TYPE);
					AttributeUtil.setDataTypeAttributes(node, dataType);
					node.addChild(dataTypeNode);
//					System.out.println(
//							"    dataType: " + dataType.getName() +
//							" visibility: " + dataType.getVisibility().getLiteral());
				} else if (innerElement instanceof LiteralString) {
					LiteralString literalString = (LiteralString) innerElement;
					Node literalStringNode = new NodeImpl(NodeType.LITERAL_STRING);
					AttributeUtil.setLiteralStringAttributes(node, literalString);
					node.addChild(literalStringNode);
//					System.out.println(
//							"    literal string: " + literalString.getName() +
//							" visibility: " + literalString.getVisibility().getLiteral());
				} else if (innerElement instanceof Parameter) {
					Parameter parameter = (Parameter) innerElement;
					Node parameterNode = new NodeImpl(NodeType.PARAMETER);
					AttributeUtil.setParameterAttributes(node, parameter);
					node.addChild(parameterNode);
//					System.out.println(
//							"    dataType: " + parameter.getName() +
//							" visibility: " + parameter.getVisibility().getLiteral());
				} else {
					System.out.println("Unchecked innerElement");
				}

			});
		} else if (element instanceof Package) {
			Package packageElement = (Package) element;
			node.setStandardizedNodeType(NodeType.PACKAGE);
			
//			System.out.println("package:" + packageElement.getName());
//			packageElement.allOwnedElements().forEach(innerElement -> {
//				System.out.println("   " + innerElement);
//			});
		} else if (element instanceof Enumeration) {
			Enumeration enumElement = (Enumeration) element;
			node.setStandardizedNodeType(NodeType.ENUM);
			
//			System.out.println("enumeration:" + enumElement.getName());
//			enumElement.allOwnedElements().forEach(innerElement -> {
//				System.out.println("   " + innerElement);
//			});
		} else if (element instanceof Association) {
			Association associationElement = (Association) element;
			node.setStandardizedNodeType(NodeType.ASSOCIATION);
			
//			System.out.println("association:" + associationElement.getName());
//			associationElement.allOwnedElements().forEach(innerElement -> {
//				System.out.println("   " + innerElement);
//			});
		} else if (element instanceof ElementImport) {
			ElementImport elementImportElement = (ElementImport) element;
			node.setStandardizedNodeType(NodeType.ELEMENT_IMPORT);
			

			//System.out.println("association:" + elementImportElement.getName());
//			elementImportElement.allOwnedElements().forEach(innerElement -> {
				//System.out.println("   " + innerElement);
//			});
		} else if (element instanceof DataType) {
			DataType dataType = (DataType) element;
			node.setStandardizedNodeType(NodeType.DATA_TYPE);
		} else {
			System.out.println("Element of unchecked type");
		}
		return node;
	}
	
	private Node processElement(NamedElement element) {
		NodeType type = getElementType(element);
		Node node = getTreeNode(type, element);
		
		element.getOwnedElements().forEach(ownedElement -> {
			if (ownedElement instanceof NamedElement) {
				node.addChild(processElement((NamedElement) ownedElement));
			} else if (ownedElement instanceof Generalization) {
				Generalization generalization = (Generalization) ownedElement;
				Node generalizationNode = new NodeImpl(NodeType.GENERALIZATION);
				AttributeUtil.setGeneralizationAttributes(generalizationNode, generalization);
				if (generalization.getOwnedElements().size() > 0) {
					System.out.println("element ignored");
				}
				node.addChild(generalizationNode);
			} else {
				System.out.println("Non named element encountered");
			}
		});
		
		return node;
	}
	
	private NodeType getElementType(NamedElement element) {
		if (element instanceof Model) {
			return NodeType.MODEL;
		} else if (element instanceof Package) {
			return NodeType.PACKAGE;
		} else if (element instanceof Enumeration) {
			return NodeType.ENUM;
		} else if (element instanceof Association) {
			return NodeType.ASSOCIATION;
		} else if (element instanceof ElementImport) {
			return NodeType.ELEMENT_IMPORT;
		} else if (element instanceof DataType) {
			return NodeType.DATA_TYPE;
		} else if (element instanceof Class) {
			return NodeType.CLASS;
		} else if (element instanceof Property) {
			return NodeType.PROPERTY;
		} else if (element instanceof Operation) {
			return NodeType.OPERATION;
		} else if (element instanceof LiteralString) {
			return NodeType.LITERAL_STRING;
		} else if (element instanceof LiteralInteger) {
			return NodeType.LITERAL_INTEGER;
		} else if (element instanceof LiteralBoolean) {
			return NodeType.LITERAL_BOOLEAN;
		} else if (element instanceof LiteralUnlimitedNatural) {
			return NodeType.LITERAL_UNLIMTED_NATURAL;
		} else if (element instanceof Parameter) {
			return NodeType.PARAMETER;
		} else if (element instanceof Interface) {
			return NodeType.INTERFACE_UML;
		} else if (element instanceof InterfaceRealization) {
			return NodeType.INTERFACE_REALIZATION;
		} else if (element instanceof EnumerationLiteral) {
			return NodeType.ENUMERATION_LITERAL;
		}
		return NodeType.UNDEFINED;
	}
	
	private Node getTreeNode(NodeType type, NamedElement element) {
			if (type == NodeType.MODEL) {
				Model model = (Model) element;
				Node modelNode = new NodeImpl(NodeType.MODEL);
				AttributeUtil.setCommonAttributes(modelNode, model);
				return modelNode;
			} else if (type == NodeType.PROPERTY) {
				Property property = (Property) element;
				Node propertyNode = new NodeImpl(NodeType.PROPERTY);
				AttributeUtil.setPropertyAttributes(propertyNode, property);
				return propertyNode;
			} else if (type == NodeType.GENERALIZATION) {
				Generalization generalization = (Generalization) element;
				Node generalizationNode = new NodeImpl(NodeType.GENERALIZATION);
				return generalizationNode;
			} else if (type == NodeType.OPERATION) {
				Operation operation = (Operation) element;
				Node operationNode = new NodeImpl(NodeType.OPERATION);
				AttributeUtil.setOperationAttributes(operationNode, operation);
				return operationNode;
			} else if (type == NodeType.DATA_TYPE) {
				DataType dataType = (DataType) element;
				Node dataTypeNode = new NodeImpl(NodeType.DATA_TYPE);
				AttributeUtil.setDataTypeAttributes(dataTypeNode, dataType);
				return dataTypeNode;
			} else if (type == NodeType.LITERAL_STRING) {
				LiteralString literalString = (LiteralString) element;
				Node literalStringNode = new NodeImpl(NodeType.LITERAL_STRING);
				AttributeUtil.setLiteralStringAttributes(literalStringNode, literalString);
				return literalStringNode;
			} else if (type == NodeType.LITERAL_INTEGER) {
				LiteralInteger literalInteger = (LiteralInteger) element;
				Node literalIntegerNode = new NodeImpl(NodeType.LITERAL_INTEGER);
				AttributeUtil.setLiteralIntegerAttributes(literalIntegerNode, literalInteger);
				return literalIntegerNode;
			} else if (type == NodeType.LITERAL_BOOLEAN) {
				LiteralBoolean literalBoolean = (LiteralBoolean) element;
				Node literalBooleanNode = new NodeImpl(NodeType.LITERAL_BOOLEAN);
				AttributeUtil.setLiteralBooleanAttributes(literalBooleanNode, literalBoolean);
				return literalBooleanNode;
			} else if (type == NodeType.LITERAL_UNLIMTED_NATURAL) {
				LiteralUnlimitedNatural literalUnlimitedNatural = (LiteralUnlimitedNatural) element;
				Node literalUnlimitedNaturalNode = new NodeImpl(NodeType.LITERAL_UNLIMTED_NATURAL);
				AttributeUtil.setLiteralUnlimitedNaturalAttributes(literalUnlimitedNaturalNode, literalUnlimitedNatural);
				return literalUnlimitedNaturalNode;
			} else if (type == NodeType.PARAMETER) {
				Parameter parameter = (Parameter) element;
				Node parameterNode = new NodeImpl(NodeType.PARAMETER);
				AttributeUtil.setParameterAttributes(parameterNode, parameter);
				return parameterNode;
			} else if (type == NodeType.PACKAGE) {
				Package packageElement = (Package) element;
				Node packageNode = new NodeImpl(NodeType.PACKAGE);
				AttributeUtil.setPackageElementAttributes(packageNode, packageElement);
				return packageNode;
			} else if (type == NodeType.ENUM) {
				Enumeration enumElement = (Enumeration) element;
				Node enumNode = new NodeImpl(NodeType.ENUM);
				AttributeUtil.setEnumerationAttributes(enumNode, enumElement);
				return enumNode;
			} else if (type == NodeType.ASSOCIATION) {
				Association associationElement = (Association) element;
				Node associationNode = new NodeImpl(NodeType.ASSOCIATION);
				AttributeUtil.setAssociationAttributes(associationNode, associationElement);
				return associationNode;
			} else if (type == NodeType.ELEMENT_IMPORT) {
				ElementImport importElement = (ElementImport) element;
				Node importNode = new NodeImpl(NodeType.ELEMENT_IMPORT);
				AttributeUtil.setElementImportAttributes(importNode, importElement);
				return importNode;
			} else if (type == NodeType.CLASS) {
				Class classElement = (Class) element;
				Node attributeNode = new NodeImpl(NodeType.CLASS);
				AttributeUtil.setClassAttributes(attributeNode, classElement);
				return attributeNode;
			} else if (type == NodeType.INTERFACE_UML) {
				Interface interfaceElement = (Interface) element;
				Node interfaceNode = new NodeImpl(NodeType.INTERFACE_UML);
				AttributeUtil.setInterfaceAttributes(interfaceNode, interfaceElement);
				return interfaceNode;
			} else if (type == NodeType.INTERFACE_REALIZATION) {
				InterfaceRealization interfaceRealization = (InterfaceRealization) element;
				Node interfaceRealizationNode = new NodeImpl(NodeType.INTERFACE_REALIZATION);
				AttributeUtil.setInterfaceRealizationAttributes(interfaceRealizationNode, interfaceRealization);
				return interfaceRealizationNode;
			} else if (type == NodeType.ENUMERATION_LITERAL) {
				EnumerationLiteral enumLiteralElement = (EnumerationLiteral) element;
				Node enumLiteralNode = new NodeImpl(NodeType.ENUMERATION_LITERAL);
				AttributeUtil.setEnumerationLiteralAttributes(enumLiteralNode, enumLiteralElement);
				return enumLiteralNode;
			} else if (type == NodeType.UNDEFINED) {
				return new NodeImpl(NodeType.UNDEFINED);
			} else {
				System.out.println("Unchecked innerElement"+ element.getLabel());
				return new NodeImpl("error");
			}
	}
}
