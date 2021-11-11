/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import java.util.Arrays;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

/**
 * @author developer-olan
 *
 */
public class SourceNodeTraverser {

	private String nodeSourceCode;
	private static int sourceFileCount = 1;
	private static Node nodeParent;
	public static List<String> fileExtensions = Arrays.asList("CPP", "JAVA");
	
	public SourceNodeTraverser() {
	}
	
	public static String getAllSourceInVariant(Node variantNode) {	
		String variantSource = "";
		if (fileExtensions.contains(variantNode.getNodeType())) {
			variantSource += getNodeSourceRecursive(variantNode, true);
		} else if (variantNode.getNodeType().equals("Directory")) {
			
			if (!variantNode.getChildren().isEmpty()) {
				for (Node childNode : variantNode.getChildren()) {
					variantSource += getAllSourceInVariant(childNode);
				}
			}
		}
		
		return variantSource;
	}
	
	/**
	 * Finds and returns the Source Node Parent of a Node
	 * @param nodeToTransverse
	 * @return
	 */
	public static Node getNodeSourceParentRecursive(Node nodeToTransverse) {
		if (fileExtensions.contains(nodeToTransverse.getNodeType())) {
			nodeParent  = nodeToTransverse;
		} else if (nodeToTransverse.getParent() != null) {
			if (fileExtensions.contains(nodeToTransverse.getParent().getNodeType())) {
				nodeParent  = nodeToTransverse.getParent();
			} else {
				getNodeSourceParentRecursive(nodeToTransverse.getParent());
			}
		}
		
		return nodeParent;
	}
	
	/**
	 * Finds and returns SourceCode of a Node
	 * @param nodeToTransverse
	 * @return
	 */
	public static String getNodeSourceRecursive(Node nodeToTransverse, boolean recursive) {
		String nodeSourceCode = "";
		
		for (Attribute nodeAttribute : nodeToTransverse.getAttributes()) {
			List<Value> nodeSource = nodeAttribute.getAttributeValues();
			nodeSourceCode += joinAttributes(nodeSource);
		}
		
		if (recursive) {
			if (!nodeToTransverse.getChildren().isEmpty()) {
				for (Node childNode : nodeToTransverse.getChildren()) {
					nodeSourceCode += getNodeSourceRecursive(childNode, recursive);
				}
			}
		}
		
		return nodeSourceCode;
	}

	private static String joinAttributes(List<Value> attributeValues) {
		String attributesString = "";
		for (Value anAttributeValue : attributeValues) {
			attributesString += anAttributeValue.getValue().toString();
		}
		return attributesString;
	}

	public String getSourceCodeString() {
		return nodeSourceCode;
	}
	
	public static int getVariantSourceCount(Node variantTree) {
		sourceFileCount = 0;
		computeSubContents(variantTree);
		return sourceFileCount;
	}
	
	public static void computeSubContents(Node directoryItem) {
		if (fileExtensions.contains(directoryItem.getNodeType())) {
			sourceFileCount++;
		} else if (directoryItem.getNodeType().equals("Directory")) {
			directoryItem.getChildren().stream().forEach(e -> computeSubContents(e));
		}
		
	}
	
}
