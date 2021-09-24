/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.ResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ArtifactFileDetails;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;

/**
 * @author developer-olan
 *
 */
public class DirectoryNodeComparator extends AbstractNodeComparator {
	private static final String DIRECTORY_NODE_TYPE_KEY = "Directory";
	private static final String DIRECTORY_NAME_ATTRIBUTE_KEY = "DIRECTORY_NAME"; 
	private String hostDirectory = "";


	private String leftVariantFolder = "";
	private String rightVariantFolder = "";
	private String pathToLeftNode = "";
	private String pathToRightNode = ""; 

	
	/**
	 * 
	 */
	public DirectoryNodeComparator() {
		super(WILDCARD);
	}
	
	/**
	 * Computes the directory path to a Node
	 * @param nodeFromVariant
	 * @param isLeftNode
	 */
	public void computePathToNode(Node nodeFromVariant, boolean isLeftNode) {
		if (nodeFromVariant.getNodeType().equals(DIRECTORY_NODE_TYPE_KEY)) {
			if (isLeftNode) {
				pathToLeftNode =  nodeFromVariant.getAttributeForKey(DIRECTORY_NAME_ATTRIBUTE_KEY).getAttributeValues().get(0).getValue().toString() + "\\" + pathToLeftNode;
				leftVariantFolder = nodeFromVariant.getAttributeForKey(DIRECTORY_NAME_ATTRIBUTE_KEY).getAttributeValues().get(0).getValue().toString();
			} else {
				pathToRightNode =  nodeFromVariant.getAttributeForKey(DIRECTORY_NAME_ATTRIBUTE_KEY).getAttributeValues().get(0).getValue().toString() + "\\" + pathToRightNode;
				rightVariantFolder = nodeFromVariant.getAttributeForKey(DIRECTORY_NAME_ATTRIBUTE_KEY).getAttributeValues().get(0).getValue().toString();
			}
		}
		
		if (nodeFromVariant.getParent() != null) {
			computePathToNode(nodeFromVariant.getParent(), isLeftNode);
		} 
	}
	
	/**
	 * Find FileTreeElement of folder path supplied 
	 * @param artifactComparisonList
	 * @param variantFolder
	 * @param folderPath
	 * @return
	 */
	public FileTreeElement FindFolderInFileTree(List<ArtifactFileDetails> artifactComparisonList, String variantFolder, String folderPath) {
		FileTreeElement foundFileTree = null;
		
		for (ArtifactFileDetails anArtifactComparison : artifactComparisonList) {
			if (anArtifactComparison.getFileTree().getFileName().equals(variantFolder)) {
				hostDirectory  = computePathToFileTreeRecursive(anArtifactComparison.getFileTree().getParent());
				foundFileTree = searchTreeRecursively(anArtifactComparison.getFileTree(), folderPath, hostDirectory);
				if (foundFileTree != null) {
					break;
				}
			}
		}
		
		return foundFileTree;
	}
	
	/**
	 * Search for FileTreeElement of searched folder in the Root FileTreeElement of the variant 
	 * @param variantFolder
	 * @param folderToFind
	 * @return
	 */
	public FileTreeElement searchTreeRecursively(FileTreeElement folderFileTree, String folderToFind, String containedInDirectory) {
		FileTreeElement foundFileTree = null;
		
		// Subtract Parent Path from Own Path
		String folderFileTreePath = getFolderFileTreePath(folderFileTree);
		folderFileTreePath = folderFileTreePath.replace(containedInDirectory, "");
		
		// Start with Parent Folder
		if (folderFileTree.isDirectory()) {
			if (folderFileTreePath.equals(folderToFind)) {
				return folderFileTree;
			}
		
			// Continue to Children of Parent Folder
			if (folderFileTree.getChildren().size() > 0) {
				for (FileTreeElement aChild : folderFileTree.getChildren()) {
					if (aChild.isDirectory()) {
						folderFileTreePath = getFolderFileTreePath(aChild);
						folderFileTreePath = folderFileTreePath.replace(containedInDirectory, "");
						if (folderFileTreePath.equals(folderToFind)) {
							foundFileTree = aChild;
							break;
						}
						
						if (aChild.getChildren().size() > 0) {
							for (FileTreeElement bChild: aChild.getChildren()) {
								searchTreeRecursively(bChild, folderToFind, containedInDirectory);
							}
						}
					}
				}
			}
		}
		
		return foundFileTree;
	}
	
	public String getFolderFileTreePath(FileTreeElement folderFileTree) {
		String cleanedPath = "";
		cleanedPath = computePathToFileTreeRecursive(folderFileTree);
		return cleanedPath;
	}
	
	/**
	 * Computes the directory path to a File Tree Element (Recursively)
	 * @param nodeFromVariant
	 * @param isLeftNode
	 */
	public String computePathToFileTreeRecursive(FileTreeElement folderFileTree) {
		String pathToFileTree = "";
		
		if (folderFileTree.isDirectory()) {
			pathToFileTree =  folderFileTree.getFileName() + "\\" + pathToFileTree;
		}
		if (folderFileTree.getParent() != null) {
			pathToFileTree =  computePathToFileTreeRecursive(folderFileTree.getParent()) + pathToFileTree;
		} 
		
		return pathToFileTree;
	}
	
	
	@Override
	public ResultElement<Node> compare(Node firstNode, Node secondNode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getLeftVariantFolder() {
		return this.leftVariantFolder;
	}
	
	public String getRightVariantFolder() {
		return this.rightVariantFolder;
	}
	
	public String getPathToLeftNode() {
		return this.pathToLeftNode;
	}
	
	public String getPathToRightNode() {
		return this.pathToRightNode;
	}

}
