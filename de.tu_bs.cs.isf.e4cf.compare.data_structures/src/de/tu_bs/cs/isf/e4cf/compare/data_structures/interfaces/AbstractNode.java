package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;

public abstract class AbstractNode implements Node {
	private static final long serialVersionUID = 5776489857546412690L;
	private String nodeType;
	private List<Node> children;
	private Node parent;
	private List<Attribute> attributes;
	private VariabilityClass varClass = VariabilityClass.MANDATORY;
	private UUID uuid = UUID.randomUUID();
	
	
	public AbstractNode() {
		initializeNode();
	}
	
	/**
	 * This method initializes all required objects.
	 */
	private void initializeNode() {
		setChildren(new ArrayList<Node>());
		setAttributes(new ArrayList<Attribute>());
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
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	@Override
	public void addAttribute(String key, String value) {
		Optional<Attribute> attribute = attributes.stream().filter(e -> e.getAttributeKey().equals(key)).findAny();
		if(!attribute.isPresent()) {
			getAttributes().add(new AttributeImpl(key,value));
		} else {
		    //adds this value as alternative for this attribute.
		    attribute.get().addAttributeValue(value);
		}
	}

	@Override
	public Attribute getAttributeForKey(String key) {
		return attributes.stream().findAny().filter(e -> e.getAttributeKey().equals(key)).get();
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
	
	@Override
	public List<String> getAllNodeTypes() {
	    List<String> nodeTypes = new ArrayList<String>();
	    nodeTypes.add(getNodeType());
	    for(Node child : getChildren()) {
		nodeTypes.addAll(child.getAllNodeTypes());
	    }
	    return nodeTypes;
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
	
	@Override
	public VariabilityClass getVariabilityClass() {
		return varClass;
	}

	@Override
	public void setVariabilityClass(VariabilityClass varClass) {
		this.varClass = varClass;
	}
	
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString() {
		String nodeName = "NodeType: "+getNodeType() +" \n";
		for(Attribute attr : getAttributes()) {
			nodeName += "Attrribute Key: "+ attr.getAttributeKey() + "\n";
			for(String value : attr.getAttributeValues()) {
				nodeName += "        "+value +"\n";
			}
		}
		return nodeName;
	}
	
	@Override
	public UUID getUUID() {
	    return uuid;
	}
	
	@Override
	public void setUUID(UUID uuid) {
	    this.uuid = uuid;
	}
}
