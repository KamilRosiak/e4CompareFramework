package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;

public abstract class AbstractNode implements Node {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 5776489857546412690L;
	private String nodeType;
	private String representation;
	private NodeType standardizedNodeType = NodeType.UNDEFINED;
	private List<Node> children;
	private transient Node parent;
	private List<Attribute> attributes;
	private VariabilityClass varClass = VariabilityClass.MANDATORY;
	private UUID uuid = UUID.randomUUID();
	private int startLine = -1;
	private int endLine = -1;

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
		if (parent == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isLeaf() {
		if (children.isEmpty()) {
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
	public void addAttribute(String key, @SuppressWarnings("rawtypes") Value value) {
		Optional<Attribute> attribute = attributes.stream().filter(e -> e.getAttributeKey().equals(key)).findAny();
		if (!attribute.isPresent()) {
			getAttributes().add(new AttributeImpl(key, value));
		} else {
			// adds this value as alternative for this attribute.
			attribute.get().addAttributeValue(value);
		}
	}

	@Override
	public void addAttribute(String key, @SuppressWarnings("rawtypes") List<Value> values) {
		Optional<Attribute> attribute = attributes.stream().filter(e -> e.getAttributeKey().equals(key)).findAny();
		if (!attribute.isPresent()) {
			getAttributes().add(new AttributeImpl(key, values));
		} else {
			// adds this value as alternative for this attribute.
			attribute.get().addAttributeValues(values);
		}
	}

	public void addAttribute(Attribute attr) {
		getAttributes().add(attr);
	}

	@Override
	public Attribute getAttributeForKey(String key) {
		return attributes.stream().filter(e -> e.getAttributeKey().equals(key)).findAny().get();
	}

	@Override
	public int getNumberOfChildren() {
		int size = 1;

		if (children.isEmpty()) {
			return size;
		} else {
			for (Node child : children) {
				size += child.getNumberOfChildren();
			}
			return size;
		}
	}

	@Override
	public List<Node> getNodesOfType(String nodeType) {
		List<Node> childrenList = new ArrayList<Node>();
		if (getNodeType().equals(nodeType)) {
			childrenList.add(this);
		}

		for (Node child : getChildren()) {
			childrenList.addAll(child.getNodesOfType(nodeType));
		}
		return childrenList;
	}

	@Override
	public List<String> getAllNodeTypes() {
		List<String> nodeTypes = new ArrayList<String>();
		nodeTypes.add(getNodeType());
		for (Node child : getChildren()) {
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

	@Override
	public String getNodeType() {
		return nodeType;
	}

	@Override
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

	@Override
	public void sortChildNodes() {
		// sort child artifacts if not empty
		if (!getChildren().isEmpty()) {
			getChildren().sort((a, b) -> {
				if (a.getStartLine() < b.getStartLine()) {
					return -1;
				}

				if (a.getStartLine() > b.getStartLine()) {
					return 1;
				}
				return 0;
			});
		}
	}

	@Override
	public void addChild(Node node, int position) {

		for (Node child : this.children) {
			if (child.getPosition() >= position) {
				child.setPosition(child.getPosition() + 1);
			}
		}

		node.setPosition(position);
		this.children.add(node);
		sortChildrenByPosition();

	}

	@Override
	public void updatePosition(int position) {

		parent.sortChildrenByPosition();
		int index = parent.getChildren().indexOf(this);

		if (position >= parent.getChildren().size()) {
			position = parent.getChildren().size() - 1;
		}

		int difference = this.getPosition() - position;

		if (difference > 0) {

			while (difference != 0) {
				Node child = parent.getChildren().get(index - 1);

				if (child instanceof Component) {

					Component component = (Component) child;

					Map<Integer, Configuration> mapping = component.getNodeComponentRelation().get(parent);

					for (Entry<Integer, Configuration> entry : mapping.entrySet()) {
						if (entry.getKey() == index - 1) {
							mapping.remove(entry.getKey());
							mapping.put(this.getPosition(), entry.getValue());
							this.setPosition(entry.getKey());
							break;
						}
					}

				} else {
					int childPosition = child.getPosition();
					child.setPosition(this.getPosition());
					this.setPosition(childPosition);

				}
				difference -= 1;

			}

		} else if (difference < 0) {

			while (difference != 0) {

				Node child = parent.getChildren().get(index + 1);
				if (child instanceof Component) {

					Component component = (Component) child;

					Map<Integer, Configuration> mapping = component.getNodeComponentRelation().get(parent);

					for (Entry<Integer, Configuration> entry : mapping.entrySet()) {
						if (entry.getKey() == index + 1) {
							mapping.remove(entry.getKey());
							mapping.put(this.getPosition(), entry.getValue());
							this.setPosition(entry.getKey());
							break;
						}
					}

				} else {

					int childPosition = child.getPosition();
					child.setPosition(this.getPosition());
					this.setPosition(childPosition);

				}

				difference += 1;
			}
		}
	}

	@Override
	public void sortChildrenByPosition() {
		Map<Integer, Node> treeMap = new TreeMap<Integer, Node>();
		for (Node child : this.getChildren()) {
			if (child instanceof Component) {

				Component component = (Component) child;

				Map<Integer, Configuration> mapping = component.getNodeComponentRelation().get(this);

				for (Entry<Integer, Configuration> entry : mapping.entrySet()) {
					treeMap.put(entry.getKey(), component);
				}

			} else {
				treeMap.put(child.getPosition(), child);
			}

		}
		this.setChildren(Lists.newArrayList(treeMap.values()));

	}

	@Override
	public void addChildWithParent(Node child) {
		child.setParent(this);
		this.children.add(child);
	}

	@Override
	public void addChildWithParent(Node child, int position) {
		child.setParent(this);

		addChild(child, position);
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

	/**
	 * @Override public String toString() { String nodeName = "NodeType:
	 *           "+getNodeType() +" \n"; for(Attribute attr : getAttributes()) {
	 *           nodeName += "Attrribute Key: "+ attr.getAttributeKey() + "\n";
	 *           for(String value : attr.getAttributeValues()) { nodeName += "
	 *           "+value +"\n"; } } return nodeName; }
	 **/

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return representation == null ? nodeType : representation;
	}

	@Override
	public void setRepresentation(String representation) {
		this.representation = representation;
	}

	@Override
	public void setStandardizedNodeType(NodeType type) {
		standardizedNodeType = type;
	}

	@Override
	public NodeType getStandardizedNodeType() {
		return standardizedNodeType;
	}

	@Override
	public int getPosition() {

		Attribute attribute = getAttributeForKey("Position");
		Value value = attribute.getAttributeValues().get(0);
		return Integer.parseInt((String) value.getValue());

	}

	@Override
	public void setPosition(int position) {

		Attribute attribute = getAttributeForKey("Position");
		if (attribute == null) {
			attribute = new AttributeImpl("" + position);
		}
		attribute.getAttributeValues().clear();
		attribute.addAttributeValue(new StringValueImpl("" + position));
	}

	@Override
	public void removeChild(Node child, int position) {
		for (Node childNode : this.children) {
			if (childNode.getPosition() >= position) {
				childNode.setPosition(childNode.getPosition() - 1);
			}
		}

		this.children.remove(child);
		sortChildrenByPosition();
	}

	@Override
	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	@Override
	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}

	@Override
	public int getStartLine() {
		return this.startLine;
	}

	@Override
	public int getEndLine() {
		return this.endLine;
	}
}
