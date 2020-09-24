package de.tu_bs.cs.isf.e4cf.compare.data_structures.date_structure.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.date_structure.util.Values;

public abstract class AbstractNode implements Node {
	private String nodeType;
	private List<Node> children;
	private Node parent;
	private Map<String,Values> values;
	
	public AbstractNode() {
		initializeNode();
	}
	
	/**
	 * This method initializes all required objects.
	 */
	private void initializeNode() {
		setChildren(new ArrayList<Node>());
		setValues(new HashMap<String, Values>());
	}
	
	@Override
	public boolean isRoot() {
		if(parent == null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isLeaf() {
		if(children.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Map<String, Values> getValues() {
		return values;
	}
	
	@Override
	public void addValue(String key, String variant, String value) {
		if(values.containsKey(key)) {
			values.get(key).putValue(variant, value);
		} else {
			values.put(key, new Values(variant,value));	
		}
	}
	
	@Override 
	public Values getValuesForKey(String key) {
		return values.get(key);
	}
	
	@Override
	public int getNumberOfChildren() {
		int size = 1;
		
		if(children.isEmpty()) {
			return size;
		} else {
			for(Node child : children) {
				size += child.getNumberOfChildren();
			}
			return size;
		}
	}
	
	/******************************************************
	 * GETTER AND SETTER 
	 ******************************************************/
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	@Override
	public List<Node> getChildren() {
		return children;
	}
	
	@Override
	public void addChild(Node child) {
		this.children.add(child);
	}
	
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public void setValues(Map<String,Values> values) {
		this.values = values;
	}

}
