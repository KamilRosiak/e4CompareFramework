package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml_reader;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Package;
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
		umlModel.getPackagedElements().forEach(packagedElement -> {
			System.out.println("\nmodel:" + packagedElement.getName());
			packagedElement.getModel().getMembers().forEach(member -> {
				Node root = new NodeImpl(packagedElement.getName());
				processModelElements(member, root);
			});
		});

		// System.out.println(model);
		return new TreeImpl(umlModel.getName());
	}

	private Node processModelElements(NamedElement element, Node parent) {
		if (element instanceof Class) {
			Class classElement = (Class) element;
			System.out.println("class:" + classElement.getName());
			classElement.allOwnedElements().forEach(innerElement -> {
				if (innerElement instanceof Property) {
					Property property = (Property) innerElement;
					System.out.println(
							"    property: " + property.getName() + 
							" type:" + property.getType().getName() +
							" visibility: " + property.getVisibility().getLiteral());
				}

			});
		} else if (element instanceof Package) {
			Package packageElement = (Package) element;
			System.out.println("package:" + packageElement.getName());
			packageElement.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		} else if (element instanceof Enumeration) {
			Enumeration enumElement = (Enumeration) element;
			System.out.println("enumaration:" + enumElement.getName());
			enumElement.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		} else if (element instanceof Association) {
			Association associationElement = (Association) element;
			System.out.println("association:" + associationElement.getName());
			associationElement.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		} else {
			System.out.println("Element of unchecked type");
		}
		return parent;
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
		}

		return node;
	}

}
