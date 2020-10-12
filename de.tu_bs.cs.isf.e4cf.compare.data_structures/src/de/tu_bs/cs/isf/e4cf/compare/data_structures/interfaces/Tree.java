package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.io.Serializable;
import java.util.List;

public interface Tree extends Serializable {
	
	
	/**
	 * @return the root node of this tree.
	 */
	public Node getRoot();
	
	/**
	 * This method returns the number of nodes that are contained in this tree.
	 */
	public int getSize();

	/**
	 * @return the maximal height of this tree
	 */
	public int getNodeDepth(Node node);
	
	/**
	 * This method returns the tree name. Normally, it is the file name, class name, folder name.
	 */
	public String getTreeName();
	/**
	 * This method returns the artifactType of root node ,e.g, file , folder, class. 
	 * @return
	 */
	public String getArtifactType();
	
	/**
	 * This method returns all nodes of this tree that have the given type.
	 */
	public List<Node> getNodesForType(String nodeType);
	
	/**
	 * This method returns all leaf Nodes of this tree.
	 * @return a list of leaf Node.
	 */
	public List<Node> getLeaves();
	
	/**
	 * This method returns all non leaf Nodes of this tree.
	 * @return a list of all inner Nodes.
	 */
	public List<Node> getInnerNodes();
	
	/**
	 * This method returns a shortes path between the starting Node and the targetNode. The path is stored in a list ordered by steps. 
	 * Moreover, the first Node in this list is the startingNode and the last element is the targetNode.
	 */
	public List<Node> getPath(Node startingNode,Node targetNode);
	
	
	
}
