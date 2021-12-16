package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractTree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class TreeImpl extends AbstractTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1847459912345361053L;
	
	/**
	 * This constructor creates an empty tree.
	 * @param treeName the name of this tree
	 */
	public TreeImpl(String treeName) {
		setTreeName(treeName);
	}
	
	/**
	 * This constructor creates a named tree with the given root node.
	 */
	public TreeImpl(String treeName, Node root) {
		this(treeName);
		setRoot(root);
	}
	
	public TreeImpl(Tree first, Tree second, Node root) {
		this(first.getTreeName()+"_"+second.getTreeName(), root);
		setFileExtension(first.getFileExtension());
	}
	
	@Override
	public Tree cloneTree() {		
		Tree newTree = new TreeImpl(getTreeName(), getRoot().cloneNode());
		newTree.setFileExtension(getFileExtension());
		return newTree;		
	}
}
