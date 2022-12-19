package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml_reader;

import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
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
		Node rootNode = new NodeImpl(NodeType.MODEL);
		AttributeUtil.setCommonAttributes(rootNode, umlModel);
		
		umlModel.getPackagedElements().forEach(packagedElement -> {
			System.out.println("\nmodel:" + packagedElement.getName());
			
			Node packagedNode = new NodeImpl(NodeType.MODEL);
			AttributeUtil.setCommonAttributes(packagedNode, packagedElement);
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
			AttributeUtil.setClassAttributes(node, classElement);
			System.out.println("class:" + classElement.getName());
			classElement.allOwnedElements().forEach(innerElement -> {
				if (innerElement instanceof Property) {
					Property property = (Property) innerElement;
					Node propertyNode = new NodeImpl(NodeType.PROPERTY);
					AttributeUtil.setPropertyAttributes(propertyNode, property);
					node.addChild(propertyNode);
					System.out.println(
							"    property: " + propertyNode.getAttributeForKey(UMLAttr.NAME.name()).getValue(0) + 
							" type: " + propertyNode.getAttributeForKey(UMLAttr.TYPE.name()).getValue(0) +
							" visibility: " + propertyNode.getAttributeForKey(UMLAttr.VISIBILITY.name()).getValue(0));
				} else if (innerElement instanceof Generalization) {
					Generalization generalization = (Generalization) innerElement;
					Node generalizationNode = new NodeImpl(NodeType.GENERALIZATION);
					node.addChild(generalizationNode);
					System.out.println(
							"    generalization: " + generalization.getGeneral().getName());
				} else if (innerElement instanceof Operation) {
					Operation operation = (Operation) innerElement;
					Node operationNode = new NodeImpl(NodeType.OPERATION);
					AttributeUtil.setOperationAttributes(node, operation);
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
}
