package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.Values;

public abstract class AbstractNode implements Node {
	private static final long serialVersionUID = 5776489857546412690L;
	private String nodeType;
	private List<Node> children;
	private Node parent;
	private Map<String,Values> values;
	private VariabilityClass varClass;
	
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
	public Map<String, Values> getAttributes() {
		return values;
	}
	
	@Override
	public void addAttribute(String key, String variant, String value) {
		if(values.containsKey(key)) {
			values.get(key).putValue(variant, value);
		} else {
			values.put(key, new Values(variant,value));	
		}
	}
	
	@Override 
	public Values getAttributesForKey(String key) {
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
	
	@Override
	public List<Node> getChildrenOfType(String nodeType) {
		List<Node> childrenList = new ArrayList<Node>();
		for(Node child : getChildren()) {
			if(child.getNodeType().equals(nodeType)) {
				childrenList.add(child);
			}
		}
		return childrenList;
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
		child.setParent(this);
		this.children.add(child);
	}
	
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public void setValues(Map<String,Values> values) {
		this.values = values;
	}
	
	@Override
	public VariabilityClass getVariabilityClass() {
		return varClass;
	}

	@Override
	public void setVariabilityClass(VariabilityClass varClass) {
		this.varClass = varClass;
	}
	
	@Override
	public String toString() {
		String nodeName = "NodeType: "+getNodeType() +" \n";
		
		for(Entry<String, Values> entrySet : values.entrySet()) {
			nodeName += "Attrribute Key: "+ entrySet.getKey() + "\n";
			for(Entry<String,String> values : entrySet.getValue().getValues().entrySet()) {
				nodeName += "   "+values.getValue();
			}
		}

		return nodeName;
	}
}
