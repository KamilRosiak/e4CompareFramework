package de.tu_bs.cs.isf.e4cf.parser.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class ParserNode {
	protected List<ParserNode> children = new ArrayList<ParserNode>();
	protected ParserNode parent = null;
	protected Map<String, Object> attributeMap = new HashMap<>();

	public ParserNode() {
	}
	
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	public List<ParserNode> getChildren() {
		return children;
	}

	public ParserNode setChildren(List<ParserNode> children) {
		this.children = children;
		return this;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public int getDepth() {
		return hasParent() ? 0 : parent.getDepth() + 1;
	}


	public ParserNode getParent() {
		return parent;
	}

	public ParserNode setParent(ParserNode parent) {
		this.parent = parent;
		if (parent != null) {
			parent.getChildren().add(this);
		}
		return this;
	}
	
	///////////////////////////
	// Utility Methods
	///////////////////////////
	
	public boolean sameParentAs(ParserNode other) {
		return getParent() == other.getParent();
	}
	
	public List<ParserNode> getSiblings() {
		List<ParserNode> siblings = new ArrayList<>();
		siblings.addAll(getParent().getChildren());
		siblings.removeIf(node -> (node == this));
		return siblings;
	}
	
	public boolean isDescendantOf(ParserNode n) {
		if (hasParent()) {
			return n == getParent() || isDescendantOf(getParent());
		} else {
			return false;
		}
	}
	
	/**
	 * Gets first child of the parameter node with the specified <i>attributeValue</i> for the given <i>attribute</i>.
	 * 
	 * @param attribute
	 * @return child node that fulfills the specification or null, if no child with that tag name exists
	 */
	public ParserNode getChildByAttribute(String attribute, String attributeValue) {
		for (ParserNode child : getChildren()) {
			if (child.get(attribute) != null && child.get(attribute).equals(attributeValue)) {
				return child;
			}
		}
		return null;
	}
	
	/**
	 * Executes <i>action</i> on the child node with the specified <i>attributeValue</i> for the given <i>attribute</i>.
	 * If the child node has not been found this function has no effect.
	 * It internally uses {@link #getChildByAttribute(String attribute, String attributeValue)}.
	 * 
	 * @param attribute
	 * @param attributeValue
	 * @param action
	 */
	public void consumeChild(String attribute, String attributeValue, Consumer<ParserNode> action) {
		ParserNode attributedChild = getChildByAttribute(attribute, attributeValue);
		if (attributedChild != null) {
			action.accept(attributedChild);
		}
	}

	/**
	 * Executes <i>action</i> on the attribute specified by <i>attribute</i>.
	 * If the calling ParserNode has no attribute with that name not found this function has no effect.
	 * 
	 * @param <T>
	 * @param attribute
	 * @param action
	 */
	public <T> void executeOnAttribute(String attribute, Consumer<T> action) {
		T attrValue = get(attribute);
		if (attrValue != null) {
			action.accept(attrValue);
		}
	}
	

	///////////////////////////
	// Map Methods
	///////////////////////////
	
	public <T> ParserNode put(String attribute, T value) {
		try {
			attributeMap.put(attribute, value);
		} catch (NullPointerException e) {
			throw new NullPointerException("inserting attribute or value of parser node attribute map is null.");
		}
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String attribute) {
		return (T) attributeMap.get(attribute);		
	}
	
	public ParserNode replace(Map<String, Object> attributeMap) {
		this.attributeMap = attributeMap;
		return this;
	}
}
