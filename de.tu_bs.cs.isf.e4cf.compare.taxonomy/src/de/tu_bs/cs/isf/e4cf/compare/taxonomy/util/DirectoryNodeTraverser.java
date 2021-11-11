/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * @author developer-olan
 *
 */
public class DirectoryNodeTraverser {
	private static int subFoldersCount = 1;
	
	/**
	 *  
	 */
	public DirectoryNodeTraverser() {
		// TODO Auto-generated constructor stub
	}
	
	public static int getVariantSubdirectoryCount(Node variantTree) {
		subFoldersCount = 1;
		computeSubContents(variantTree);
		return subFoldersCount;
	}
	
	public static void computeSubContents(Node subFolder) {
		
		if (subFolder.getNodeType().equals("Directory")) {
			subFoldersCount++;
			subFolder.getChildren().stream().forEach(e -> computeSubContents(e));
		} 
	}
	

}
