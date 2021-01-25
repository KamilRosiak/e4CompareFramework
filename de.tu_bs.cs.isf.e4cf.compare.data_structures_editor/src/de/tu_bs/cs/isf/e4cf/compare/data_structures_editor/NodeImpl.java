package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * 
 * @author Team05 Implementation of adapter class abstract node
 */
public class NodeImpl extends AbstractNode {

	private static final long serialVersionUID = 378278236426734L;

	public NodeImpl(String nodeType) {
		setNodeType(nodeType);
	}

	public NodeImpl(Node node) {
		setNodeType(node.getNodeType());
		setAttributes(node.getAttributes());
		setParent(node.getParent());
		setChildren(node.getChildren());
		setUUID(node.getUUID());
		setVariabilityClass(node.getVariabilityClass());
		initializeMandatoryAttributes(node);
	}

	public NodeImpl(String nodeType, Node parent) {
		this(nodeType);
		setParent(parent);
		parent.addChild(this);
	}

	public NodeImpl(String nodeString, Node parent, VariabilityClass varClass) {
		this(nodeString, parent);
		setVariabilityClass(varClass);
	}

	private void initializeMandatoryAttributes(Node node) {
		if (node.isRoot()) {
			node.addAttribute("name", "root");
		} else if (attributeExists(node, "TEXT")) {
			for (String string : node.getAttributeForKey("TEXT").getAttributeValues()) {
				node.addAttribute("name", string);
			}
		} else {
			node.addAttribute("name", node.getNodeType());
		}
		node.addAttribute("VariabilityClass", getVariabilityClass().toString());
	}

	public boolean attributeExists(Node node, String key) {
		for (Attribute attr : node.getAttributes()) {
			if (attr.getAttributeKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String s1 = "";
		if (this.getAttributes().isEmpty()) {
			return "empty";
		}
		for (Attribute attribute : this.getAttributes()) {
			if (attribute.getAttributeKey().toLowerCase().equals("name")) {
				return attribute.getAttributeValues().iterator().next();
			} // other way to get the last element than foreach
			for (String s : attribute.getAttributeValues()) {
				s1 = s;
			}
		}
		return s1;
	}

}
