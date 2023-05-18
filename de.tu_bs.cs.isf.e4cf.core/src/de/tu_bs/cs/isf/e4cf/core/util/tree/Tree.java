package de.tu_bs.cs.isf.e4cf.core.util.tree;

import java.io.Serializable;
import java.util.function.Consumer;

public class Tree<T> implements Serializable {
	private static final long serialVersionUID = -2952558747881144092L;
	
	private Node<T> root;
	
	public Tree(Node<T> root) {
		this.root = root;
	}
	
	public Node<T> root() {
		return root;
	}
	
	public void root(Node<T> root) {
		this.root = root;
	}
	
	public boolean contains(Node<T> node) {
		return false;
		// TODO 
	}
	
	public void visit(Consumer<Node<T>> nodeVisitor) {
		// TODO
	}
	

}
