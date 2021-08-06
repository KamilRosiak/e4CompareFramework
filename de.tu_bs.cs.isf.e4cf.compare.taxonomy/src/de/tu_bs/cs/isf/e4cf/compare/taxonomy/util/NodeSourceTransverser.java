/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

/**
 * @author developer-olan
 *
 */
public class NodeSourceTransverser {

	private String nodeSourceCode;

	public void NodeSourceTransverser() {
	}
	
	public String getNodeSourceRecursive(Node nodeToTransverse) {
		String nodeSourceCode = "";
		
		for (Attribute nodeAttribute : nodeToTransverse.getAttributes()) {
			List<Value> nodeSource = nodeAttribute.getAttributeValues();
			nodeSourceCode += "" + joinAttributes(nodeSource);
		}

		if (!nodeToTransverse.getChildren().isEmpty()) {
			for (Node childNode : nodeToTransverse.getChildren()) {
				nodeSourceCode += "" + getNodeSourceRecursive(childNode);
			}
		}

		return nodeSourceCode;
	}
	

	public String getNodeSource(Node nodeToTransverse) {
		String nodeSourceCode = "";
		
		for (Attribute nodeAttribute : nodeToTransverse.getAttributes()) {
			List<Value> nodeSource = nodeAttribute.getAttributeValues();
			nodeSourceCode += "" + joinAttributes(nodeSource);
		}
		return nodeSourceCode;
	}

	private String joinAttributes(List<Value> attributeValues) {
		String attributesString = "";
		for (Value anAttributeValue : attributeValues) {
			attributesString += anAttributeValue.getValue().toString();
		}
		return attributesString;
	}

	public String getSourceCodeString() {
		return nodeSourceCode;
	}
}
