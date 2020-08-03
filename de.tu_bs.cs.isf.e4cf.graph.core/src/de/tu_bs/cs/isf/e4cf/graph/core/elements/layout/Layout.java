package de.tu_bs.cs.isf.e4cf.graph.core.elements.layout;

public interface Layout<T> {
	
	public void init(T target);
	
	public void format();
}
