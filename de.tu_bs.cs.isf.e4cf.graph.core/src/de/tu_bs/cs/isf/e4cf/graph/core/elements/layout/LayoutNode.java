package de.tu_bs.cs.isf.e4cf.graph.core.elements.layout;

import java.util.Set;

public interface LayoutNode<T> {

	Set<? extends LayoutNode<T>> getConnectedNodes();
	
	double getWidth();
	double getHeight();
	
	T get();
}
