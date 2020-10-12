package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.TreeUtil;

public class AbstractTree implements Tree {
    private static final long serialVersionUID = 7659681319811210012L;
    private Node root;
    private String treeName;
    private String artifactType;
	
	
	@Override
	public int getSize() {
		//temp starts with 1 which represents the root not itself 
		int temp = 1;
		for(Node node : root.getChildren()) {
			temp += node.getNumberOfChildren();
		}
		return temp;
	}
	
	@Override
	public int getNodeDepth(Node node) {
		return TreeUtil.getNodeDepth(node);
	}
	
	@Override
	public String getArtifactType() {
		return root.getNodeType();
	}
	@Override
	public List<Node> getNodesForType(String nodeType) {
		return TreeUtil.getNodesForType(root, nodeType);
	}
	
	@Override
	public List<Node> getLeaves() {
		return TreeUtil.getAllLeaveNodes(root);
	}
	
	@Override
	public List<Node> getInnerNodes() {
		return TreeUtil.getAllInnerNodes(root);
	}
	@Override
	public List<Node> getPath(Node startingNode, Node targetNode) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/***********************************************
	 * GETTER AND SETTER
	 */
	@Override
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		setArtifactType(root.getNodeType());
		this.root = root;
	}
	
	@Override
	public String getTreeName() {
		return this.treeName;
	}
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}
}
