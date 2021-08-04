/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.core.compare.algorithm.LevenstheinDistance;

/**
 * @author developer-olan
 *
 */
public class DirectoryNameComparator extends AbstractNodeComparator {
	private static final String DIRECTORY_NAME_ATTRIBUTE_KEY = "DIRECTORY_NAME";
	/**
	 * @param supportedType
	 */
	public DirectoryNameComparator() {
		super(WILDCARD);
	}

	@Override
	public NodeResultElement compare(Node firstNode, Node secondNode) {
		float similarity = 0.0f;
		similarity = compareDirectoryName(firstNode.getAttributeForKey(DIRECTORY_NAME_ATTRIBUTE_KEY), secondNode.getAttributeForKey("DIRECTORY_NAME"));
		System.out.println("Similarity: "+ similarity);
		return new NodeResultElement(this, similarity);
	}
	
	
	public Float compareDirectoryName(Attribute leftDirectoryNameAttribute, Attribute rightDirectoryNameAttribute) {
		float similarity = 0.0f;
		
		for (Value leftFolderName : leftDirectoryNameAttribute.getAttributeValues()) {
			for (Value rightFolderName : rightDirectoryNameAttribute.getAttributeValues()) {
				if (leftFolderName != null && rightFolderName != null) {
					System.out.println("Comparing folders: "+leftFolderName.getValue().toString()+", "+rightFolderName.getValue().toString());
					float levenstheinDistance= (float) LevenstheinDistance.computeLevenshteinDistance(leftFolderName.toString(), rightFolderName.toString());	
					int leftFolderNameValueLength = leftFolderName.getValue().toString().length();
					int rightFolderNameValueLength = rightFolderName.getValue().toString().length();
					int longerNameLength =  Math.max(leftFolderNameValueLength, rightFolderNameValueLength);
					similarity =  Math.abs(1 - (levenstheinDistance/longerNameLength));
				}
			}
		}
	
		return similarity;
	}

}
