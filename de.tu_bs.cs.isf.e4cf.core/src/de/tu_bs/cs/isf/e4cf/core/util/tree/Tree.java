package de.tu_bs.cs.isf.e4cf.core.util.tree;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class Tree<T> implements Serializable {
	private static final long serialVersionUID = -2952558747881144092L;
	
	private Node<T> root;
	
	public Tree(Node<T> root) {
		this.root = root;
	}
	
	public Node<T> getRoot() {
		return root;
	}
	
	public void setRoot(Node<T> root) {
		this.root = root;
	}
	
	public boolean contains(Node<T> node) {
		return root.hasProperty(descendant -> descendant.equals(node));
	}
	
	public void visit(Consumer<Node<T>> visitor) {
		if (root == null) return;
		root.visitDescendants(visitor);
	}
	
	public Set<Node<T>> getAllNodes() {
		Set<Node<T>> allNodes = new HashSet<>();
		this.visit(allNodes::add);
		return allNodes;
	}
	
	public Optional<Node<T>> getNode(T value) {
		for (Node<T> node : getAllNodes()) {
			if (node.value().equals(value)) {
				return Optional.of(node);
			}
		}
		return Optional.empty();
	}
	
	public int getDepth(T value) {
		return root.getDescendantDepth(value, 0);
		
	}


}
