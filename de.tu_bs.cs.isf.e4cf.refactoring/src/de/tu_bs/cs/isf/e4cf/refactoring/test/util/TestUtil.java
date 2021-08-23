package de.tu_bs.cs.isf.e4cf.refactoring.test.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.ArtifactReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaReader;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.DefaultTreeBuilder;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.TreeBuilder;

public class TestUtil {

	public static Tree assembleTree(Tree tree) {

		removePositionAttribute(tree.getRoot());
		return tree;
	}

	private static void removePositionAttribute(Node node) {

		List<Attribute> newAttributes = new ArrayList<Attribute>();
		for (Attribute attribute : node.getAttributes()) {

			if (!attribute.getAttributeKey().equals("Position")) {
				newAttributes.add(attribute);
			}
		}
		node.setAttributes(newAttributes);
		for (Node child : node.getChildren()) {
			removePositionAttribute(child);
		}

	}

	public static Tree readFile(String relativePath) {

		String path = new java.io.File(relativePath).getAbsolutePath();
		TreeBuilder treeBuilder = new DefaultTreeBuilder(1);
		Directory fte = new Directory(path);
		try {
			treeBuilder.buildTree(fte);
		} catch (IOException e) {

			e.printStackTrace();
		}

		Tree tree = new TreeImpl(fte.getFileName(), readFileRecursivly(null, fte));
		if (fte.isDirectory()) {
			tree.setFileExtension("DIRECTORY");
		} else {
			tree.setFileExtension(fte.getExtension());
		}

		return tree;
	}

	private static Node readFileRecursivly(Node parentNode, FileTreeElement fte) {
		if (fte.isDirectory()) {
			Node nextNode = new NodeImpl("Directory");
			nextNode.addAttribute("DIRECTORY_NAME", new StringValueImpl(fte.getFileName()));

			fte.getChildren().stream()
					.forEach(childFte -> nextNode.addChildWithParent(readFileRecursivly(nextNode, childFte)));
			return nextNode;
		} else {
			ArtifactReader reader = new JavaReader();
			return reader.readArtifact(fte).getRoot();
		}
	}

}
