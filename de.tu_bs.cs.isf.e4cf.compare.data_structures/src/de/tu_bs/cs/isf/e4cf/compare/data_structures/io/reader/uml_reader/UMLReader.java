package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.uml_reader;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.AssociationImpl;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.EnumerationImpl;
import org.eclipse.uml2.uml.internal.impl.PackageImpl;
import org.eclipse.uml2.uml.internal.impl.PropertyImpl;
import org.eclipse.uml2.uml.internal.impl.UMLPackageImpl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ModelUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.emf.EMFUtil;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class UMLReader extends AbstractArtifactReader {
	public final static String[] SUPPORTED_FILE_ENDINGS = { "uml" };

	public UMLReader() {
		super(SUPPORTED_FILE_ENDINGS);
	}

	@Override
	public Tree readArtifact(FileTreeElement element) {
		UMLPackage umlPackag = UMLPackage.eINSTANCE;
		Model model = (Model) EMFModelLoader.load(element.getAbsolutePath(), "uml");
		model.getPackagedElements().forEach(packaagleElement -> {
			packaagleElement.getModel().getMembers().forEach(member -> {
				Node root = new NodeImpl("UML");
				processModelElements(member, root);
			});
		});

		// System.out.println(model);
		return new TreeImpl("UML Tree");
	}

	public Node processModelElements(NamedElement element, Node parent) {

		if (element instanceof ClassImpl) {
			ClassImpl classImpl = (ClassImpl) element;
			System.out.println("class:" + classImpl.getLabel());

			classImpl.allOwnedElements().forEach(innerElement -> {
				if (innerElement instanceof PropertyImpl) {
					PropertyImpl property = (PropertyImpl) innerElement;
					System.out.println("    property: " + property.getLabel() + " type:" + property.getType().getLabel() +" visibility: " + property.getVisibility().getLiteral());

				}

			});
		} else if (element instanceof PackageImpl) {
			PackageImpl classImpl = (PackageImpl) element;
			System.out.println("package:" + classImpl.getLabel());
			classImpl.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		} else if (element instanceof EnumerationImpl) {
			EnumerationImpl classImpl = (EnumerationImpl) element;
			System.out.println("enumaration:" + classImpl.getLabel());
			classImpl.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		} else if (element instanceof AssociationImpl) {
			AssociationImpl classImpl = (AssociationImpl) element;
			System.out.println("association:" + classImpl.getLabel());
			classImpl.allOwnedElements().forEach(innerElement -> {
				System.out.println("   " + innerElement);
			});
		}
		return parent;
	}

	public static Node createNodeForNamedElement(NamedElement element) {
		Node node = null;
		if (element instanceof ClassImpl) {
			node = new NodeImpl(NodeType.CLASS);
			
			node = new NodeImpl(NodeType.PROPERTY);
		} else if (element instanceof PackageImpl) {
			node = new NodeImpl(NodeType.PACKAGE);
		} else if (element instanceof EnumerationImpl) {
			node = new NodeImpl(NodeType.ENUM);
		} else if (element instanceof AssociationImpl) {
			node = new NodeImpl(NodeType.ASSOCIATION);
		}

		return node;
	}

}
