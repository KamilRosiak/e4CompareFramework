package de.tu_bs.cs.isf.e4cf.core.util.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node<T> implements Serializable {
	private static final long serialVersionUID = 533534909323574578L;

	private T value;
	private List<Node<T>> children = new ArrayList<>();
	
	public Node(T value) {
		this.value = value;
	}
	
	public T value() {
		return value;
	}
	
	public void value(T value) {
		this.value = value;
	}
	
	public void addChild(Node<T> child) {
		this.children.add(child);
	}
	
	public boolean isChild(Node<T> node) {
		return children.contains(node);
	}
	
	public boolean removeChild(Node<T> child) {
		return children.remove(child);
	}

}
