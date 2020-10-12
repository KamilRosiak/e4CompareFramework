package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;


/**
 * This class contains utility methods for nodes {@link Node} and trees {@link Tree}
 * @author Kamil Rosiak
 *
 */
public class TreeUtil {

	/**
	 * This method returns all nodes with the given node type
	 */
	public static List<Node> getNodesForType(Node node, String nodeType) {
		List<Node> nodes = new ArrayList<Node>();
		// if the given node is of this type add them to the node list
		if(node.getNodeType().equals(nodeType)) {
			nodes.add(node);
		}
		
		// recursivly call of this method for all child nodes
		for(Node childNode : node.getChildren()) {
			nodes.addAll(getNodesForType(childNode, nodeType));
		}
		return nodes;
	}
	
	/**
	 * This method returns all leave nodes of the given node.A leaf node is a node without child nodes.
	 */
	public static List<Node> getAllLeaveNodes(Node node) {
		List<Node> nodes = new ArrayList<Node>();
		//if the node is a leaf add it to list
		if(node.getChildren().isEmpty()) {
			nodes.add(node);
		} else {
			//Recursively call of this method for all child nodes
			for(Node childNode : node.getChildren()) {
				nodes.addAll(getAllLeaveNodes(childNode));
			}
		}
		return nodes;
	}
	
	/**
	 * This method returns all inner nodes of the given node. A inner node is a node that has child nodes.
	 */
	public static List<Node> getAllInnerNodes(Node node) {
		List<Node> nodes = new ArrayList<Node>();
		//if the node has children it is not a leaf
		if(!node.getChildren().isEmpty()) {
			nodes.add(node);
			//Recursively call of this method for all child nodes
			for(Node childNode : node.getChildren()) {
				nodes.addAll(getAllLeaveNodes(childNode));
			}
		} 
		return nodes;
	}
	
	/**
	 * This method returns the depth of the given node.
	 */
	public static int getNodeDepth(Node node) {
		int depth = 1;
		if(!node.isRoot()) {
			depth += getNodeDepth(node.getParent());
		}
		return depth;
		
	}
}
