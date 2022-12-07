package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml_reader;

import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.BoolValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class UMLReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "uml" };


	/**
	 * See the <a href="https://download.eclipse.org/modeling/mdt/uml2/javadoc/4.1.0/org/eclipse/uml2/uml/package-summary.html">
	 * Javadoc</a> for the eclipse UML Reader
	 */
	public UMLReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement fileTreeElement) {
		@SuppressWarnings("unused") // if the variable is removed the method throws an exception and does not work
		UMLPackage instance = UMLPackage.eINSTANCE;
		
		Model umlModel = (Model) EMFModelLoader.load(fileTreeElement.getAbsolutePath(), "uml");
		Node rootNode = new NodeImpl(umlModel.getName());
		umlModel.getPackagedElements().forEach(packagedElement -> {
			System.out.println("\nmodel:" + packagedElement.getName());
			
			Node packagedNode = new NodeImpl(packagedElement.getName());
			packagedElement.getModel().getMembers().forEach(member -> {
				packagedNode.addChild(processModelElement(member));
			});
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
			System.out.println("class:" + classElement.getName());
			classElement.allOwnedElements().forEach(innerElement -> {
				if (innerElement instanceof Property) {
					Property property = (Property) innerElement;
					Node propertyNode = new NodeImpl(property.getName());
					node.addChild(propertyNode);
					System.out.println(
							"    property: " + property.getName() + 
							" type:" + property.getType().getName() +
							" visibility: " + property.getVisibility().getLiteral());
				} else if (innerElement instanceof Generalization) {
					Generalization generalization = (Generalization) innerElement;
					Node generalizationNode = new NodeImpl(NodeType.GENERALIZATION);
					node.addChild(generalizationNode);
					System.out.println(
							"    generalization: " + generalization.getGeneral().getName());
				} else if (innerElement instanceof Operation) {
					Operation operation = (Operation) innerElement;
					Node operationNode = new NodeImpl(NodeType.OPERATION);
					node.addChild(operationNode);
					System.out.println(
							"    operation: " + operation.getName() +
							" visibility: " + operation.getVisibility().getLiteral());
				}

			});
		} else if (element instanceof Package) {
			Package packageElement = (Package) element;
			node.setStandardizedNodeType(NodeType.PACKAGE);
			System.out.println("package:" + packageElement.getName());
			packageElement.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		} else if (element instanceof Enumeration) {
			Enumeration enumElement = (Enumeration) element;
			node.setStandardizedNodeType(NodeType.ENUM);
			System.out.println("enumaration:" + enumElement.getName());
			enumElement.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		} else if (element instanceof Association) {
			Association associationElement = (Association) element;
			node.setStandardizedNodeType(NodeType.ASSOCIATION);
			System.out.println("association:" + associationElement.getName());
			associationElement.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		} else {
			System.out.println("Element of unchecked type");
		}
		return node;
	}
	
	private void setCommonAttributes(NodeImpl node, NamedElement element) {
		setNamedElementAttributes(node, element);
		if (node instanceof Classifier) {
			setClassifierAttributes(node, (Classifier) element);
		}
	}
	
	private void setNamedElementAttributes(NodeImpl node, NamedElement element) {
		if (isNotEmpty(element.getName())) {
			node.addAttribute(ElementAttr.NAME.toString(), new StringValueImpl(element.getName()));
		}
		if (element.getVisibility() != null && isNotEmpty(element.getVisibility().getLiteral())) {
			node.addAttribute(ElementAttr.VISIBILITY.toString(), new StringValueImpl(element.getVisibility().getLiteral()));
		}
		if (isNotEmpty(element.getQualifiedName())) {
			node.addAttribute(ElementAttr.QUALIFIED_NAME.toString(), new StringValueImpl(element.getQualifiedName()));
		}
		if (element.getNamespace() != null && isNotEmpty(element.getNamespace().getName())) {
			node.addAttribute(ElementAttr.NAMESPACE.toString(), new StringValueImpl(element.getNamespace().getName()));
		}
	}
	
	private void setClassifierAttributes(NodeImpl node, Classifier classifier) {
		if (classifier.isAbstract()) {
			node.addAttribute(ElementAttr.ABSTRACT.toString(), new BoolValueImpl(classifier.isAbstract()));
		}
		if (classifier.isFinalSpecialization()) {
			node.addAttribute(ElementAttr.FINAL.toString(), new BoolValueImpl(classifier.isFinalSpecialization()));
		}
		if (classifier.isLeaf()) {
			node.addAttribute(ElementAttr.LEAF.toString(), new BoolValueImpl(classifier.isLeaf()) );
		}
	}
	
	private void setClassAttributes(NodeImpl node, Class clazz) {
		if (clazz.isActive()) {
			node.addAttribute(ElementAttr.ACTIVE.toString(), new BoolValueImpl(clazz.isActive()));
		}
	}
	
	private void setAssociationAttributes(NodeImpl node, Association association) {
		if (association.isDerived()) {
			node.addAttribute(ElementAttr.DERIVED.toString(), new BoolValueImpl(association.isDerived()));
		}
	}
	
	private void setPropertyAttributes(NodeImpl node, Property property) {
		if (property.isDerived()) {
			node.addAttribute(ElementAttr.DERIVED.toString(), new BoolValueImpl(property.isDerived()));
		}
		if (property.isDerivedUnion()) {
			node.addAttribute(ElementAttr.DERIVED_UNION.toString(), new BoolValueImpl(property.isDerivedUnion()) );
		}
		if (property.isID()) {
			node.addAttribute(ElementAttr.ID.toString(), new BoolValueImpl(property.isID()) );
		}
		if (property.isLeaf()) {
			node.addAttribute(ElementAttr.LEAF.toString(), new BoolValueImpl(property.isLeaf()));
		}
		if (property.isOrdered()) {
			node.addAttribute(ElementAttr.ORDERED.toString(), new BoolValueImpl(property.isOrdered()));
		}
		if (property.isReadOnly()) {
			node.addAttribute(ElementAttr.READ_ONLY.toString(), new BoolValueImpl(property.isReadOnly()));
		}
		if (property.isStatic()) {
			node.addAttribute(ElementAttr.STATIC.toString(), new BoolValueImpl(property.isStatic()));
		}
		if (property.isUnique()) {
			node.addAttribute(ElementAttr.UNIQUE.toString(), new BoolValueImpl(property.isUnique()));
		}
	}
	
	private static boolean isNotEmpty(String string) {
		return string != null && !string.isEmpty();
	}
	
	enum ElementAttr {
		// NamedElement
		NAME("NAME"),
		VISIBILITY("VISIBILITY"),
		QUALIFIED_NAME("QUALIFIED_NAME"),
		NAMESPACE("NAMESPACE"),
		// Classifier
		ABSTRACT("ABSTRACT"),
		FINAL("FINAL"),
		LEAF("LEAF"),
		// Class
		ACTIVE("ACTIVE"),
		// Association
		DERIVED("DERIVED"),
		//Property
		AGGREGATION("AGGREGATION"),
		DERIVED_UNION("DERIVED_UNION"),
		ID("ID"),
		ORDERED("ORDERED"),
		READ_ONLY("READ_ONLY"),
		STATIC("STATIC"),
		UNIQUE("UNIQUE"),
		TYPE("TYPE"),
		//OPERATION
		QUERY("QUERY"),
		CONCURRENCY("CONCURRENCY")
		;
		
		private String title;
		
		ElementAttr(String title) {
			this.title = title;
		}
		
		@Override
		public String toString() {
			return title;
		}
	}

	private static Node createNodeForNamedElement(NamedElement element) {
		Node node = null;
		if (element instanceof Class) {
			node = new NodeImpl(NodeType.CLASS);
		} else if (element instanceof Property) {
			node = new NodeImpl(NodeType.PROPERTY);
		} else if (element instanceof Package) {
			node = new NodeImpl(NodeType.PACKAGE);
		} else if (element instanceof Enumeration) {
			node = new NodeImpl(NodeType.ENUM);
		} else if (element instanceof Association) {
			node = new NodeImpl(NodeType.ASSOCIATION);
		} else if (element instanceof Operation) {
			node = new NodeImpl(NodeType.OPERATION);
		}

		return node;
	}

}
