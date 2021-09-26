/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
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
		similarity = compareDirectoryName(firstNode.getAttributeForKey(DIRECTORY_NAME_ATTRIBUTE_KEY), secondNode.getAttributeForKey(DIRECTORY_NAME_ATTRIBUTE_KEY));
		return new NodeResultElement(this, similarity);
	}
	
	
	public Float compareDirectoryName(Attribute leftDirectoryNameAttribute, Attribute rightDirectoryNameAttribute) {
		float similarity = 0.0f;
		
		for (Value leftFolderName : leftDirectoryNameAttribute.getAttributeValues()) {
			for (Value rightFolderName : rightDirectoryNameAttribute.getAttributeValues()) {
				if (leftFolderName != null && rightFolderName != null) {
					int levDist = LevenstheinDistance.computeLevenshteinDistance(leftFolderName.getValue().toString(), rightFolderName.getValue().toString());
					int maxValue = Math.max(leftFolderName.getValue().toString().length(), rightFolderName.getValue().toString().length());
					similarity = ((float)maxValue - (float)levDist)/(float)maxValue;
				}
			}
		}
	
		return similarity;
	}

}
