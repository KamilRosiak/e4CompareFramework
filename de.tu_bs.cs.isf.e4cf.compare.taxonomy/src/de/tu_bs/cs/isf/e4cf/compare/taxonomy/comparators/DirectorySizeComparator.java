/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ArtifactFileDetails;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * @author developer-olan
 *
 */
public class DirectorySizeComparator extends DirectoryNodeComparator {
	private long leftFolderBytes = 0;
	private long rightFolderBytes = 0;
	
	/**
	 * @param supportedType
	 */
	public DirectorySizeComparator() {
		super();
	}
	
	public NodeResultElement compareWithDetail(List<ArtifactFileDetails> artifactComparisonList, Node leftFolder, Node rightFolder) {
		// TODO: Get folder element tree and retrieve folderSize
		computePathToNode(leftFolder, true);
		computePathToNode(rightFolder, false);
		
		// Find the element related to the in the tree
		FileTreeElement foundLeftFileTree = FindFolderInFileTree(artifactComparisonList, getLeftVariantFolder() , getPathToLeftNode());
		FileTreeElement foundRightFileTree = FindFolderInFileTree(artifactComparisonList, getRightVariantFolder(), getPathToRightNode());

		if (foundLeftFileTree!= null && foundRightFileTree != null) {
			// Get Folder Sizes
			computeSizeInBytes(foundLeftFileTree, true);
			computeSizeInBytes(foundRightFileTree, false);
		}
		
		
		// Compare folder sizes and Return similarity value
		float similarity = compareDirectorySizes(leftFolderBytes, rightFolderBytes);
		
		return new NodeResultElement(this, similarity);
	}

	
	/**
	 * Returns the size of all contents of the folder
	 * @return
	 */
	public void computeSizeInBytes(FileTreeElement tree, boolean left) {
		if (tree.isDirectory()) {
			tree.getChildren().stream().forEach(e -> computeSizeInBytes(e, left));
		} else {
			if (left) {
				this.leftFolderBytes += tree.getSize();	
			} else {
				this.rightFolderBytes += tree.getSize();	
			}
		}
	}
	
	
	
	@Override
	public NodeResultElement compare(Node firstFolder, Node secondFolder) {
		float similarity = 0.0f;
		return new NodeResultElement(this, similarity);
	}
	
	
	public Float compareDirectorySizes(long leftFolderByteSize, long rightFolderByteSize) {
		float similarity = 0.0f;
		
		int maxValue = Math.max((int)leftFolderByteSize, (int)rightFolderByteSize);
		int minValue = Math.min((int)leftFolderByteSize, (int)rightFolderByteSize);
		
		if (maxValue > 0) {
			similarity = 1.0f - ((float) maxValue - (float) minValue) / (float) maxValue;
		}
		
		return similarity;
	}

}
