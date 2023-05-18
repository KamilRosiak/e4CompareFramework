package de.tu_bs.cs.isf.e4cf.core.util.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
	
	public boolean hasProperty(Predicate<Node<T>> property) {
		if (property.test(this)) {
			return true;
		} else {
			for (Node<T> child : children) {
				if (child.hasProperty(property)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void visitDescendants(Consumer<Node<T>> visitor) {
		visitor.accept(this);
		for (Node<T> child : children) {
			child.visitDescendants(visitor);
		}
	}
	
	public int getDescendantDepth(T value, int depthCounter) {
		if (this.value.equals(value)) {
			return depthCounter;
		} else {
			int maxDepth = -1;
			for (Node<T> child : children) {
				int childDepth = child.getDescendantDepth(value, depthCounter + 1);
				if (childDepth > maxDepth) maxDepth = childDepth;
			}
			return maxDepth;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(children, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		return Objects.equals(children, other.children) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "Node [value=" + value + ", children=" + children + "]";
	}
	
	

}
