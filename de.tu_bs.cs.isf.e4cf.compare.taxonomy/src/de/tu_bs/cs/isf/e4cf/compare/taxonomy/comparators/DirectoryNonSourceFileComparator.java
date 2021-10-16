package de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators;

import java.util.List;
import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ArtifactFileDetails;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * 
 * Calculates similarity between two nodes by computing text similarity between attributes of nodes
 * 
 * @author developer-olan
 *
 */
public class DirectoryNonSourceFileComparator extends DirectoryNodeComparator {
	private String leftFolderNonSourceChildren = "";
	private String rightFolderNonSourceChildren = "";

	public DirectoryNonSourceFileComparator() {
	}
	
	public NodeResultElement compareWithDetail(List<ArtifactFileDetails> artifactComparisonList, Node leftFolder, Node rightFolder) {
		float similarity = 0.0f;
		// TODO: Get folder element tree and retrieve folderSize
		computePathToNode(leftFolder, true);
		computePathToNode(rightFolder, false);

		// Find the element related to the in the tree
		FileTreeElement foundLeftFileTree = FindFolderInFileTree(artifactComparisonList, getLeftVariantFolder() , getPathToLeftNode());
		FileTreeElement foundRightFileTree = FindFolderInFileTree(artifactComparisonList, getRightVariantFolder(), getPathToRightNode());

		if (foundLeftFileTree!= null && foundRightFileTree != null) {
			// Get Folder Sizes
			computeNonChildrenString(foundLeftFileTree, true);
			computeNonChildrenString(foundRightFileTree, false);
		}

		similarity = compareStringValues(leftFolderNonSourceChildren, rightFolderNonSourceChildren);
		return new NodeResultElement(this, similarity);
	}
	
	/**
	 * Returns the size of all contents of the folder
	 * @return
	 */
	public void computeNonChildrenString(FileTreeElement tree, boolean left) {
		if (tree.isDirectory()) {
			for (FileTreeElement child: tree.getChildren()) {
				if (!child.isDirectory()) {
					if (!child.getExtension().equals("java")) {
						if (left) {
							this.leftFolderNonSourceChildren += child.getFileName();	
						} else {
							this.rightFolderNonSourceChildren += child.getFileName();	
						}
					}
				}
			}
		}
	}
	
	public NodeResultElement compare(Tree variant, Node firstNode, Node secondNode) {
		NodeResultElement resultElement = compare(firstNode, secondNode);
		return resultElement;
	}
	
	@Override
	public NodeResultElement compare(Node firstNode, Node secondNode) {
		float similarity = 0.0f;
		return new NodeResultElement(this, similarity);
	}

}
