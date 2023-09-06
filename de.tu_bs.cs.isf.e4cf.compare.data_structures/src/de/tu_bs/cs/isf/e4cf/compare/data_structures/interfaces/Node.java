package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;

/**
 * This interfaces defines all methods that are needed to realize a node.
 * 
 * @author Kamil Rosiak
 * @author David Bumm
 *
 */
public interface Node extends Serializable {
	static final long serialVersionUID = 5776489857546412690L;

	/**
	 * Returns the start line of the respective artifact in its original artifact
	 * type.
	 */
	public int getStartLine();

	/**
	 * Returns the end line of the respective artifact in its original artifact
	 * type.
	 */
	public int getEndLine();

	/**
	 * Setter for the start line
	 */
	public void setStartLine(int startLine);

	/**
	 * Setter for the end line
	 */
	public void setEndLine(int endLine);

	/**
	 * This method returns the type of the node, e.g., class, method, statement.
	 * This type has to be defined during the parsing process where.
	 * 
	 * @return
	 */
	public String getNodeType();

	/**
	 * This method returns all values that are available for this node, e.g. an
	 * assignment has values for the left side and right side such as a = b. In the
	 * Values class you can store values that can be identified by their variant.
	 */
	public List<Attribute> getAttributes();

	/**
	 * This method returns all values that are available for this node, e.g. an
	 * assignment has values for the left side and right side such as a = b. In the
	 * Values class you can store values that can be identified by their variant.
	 */
	public void setAttributes(List<Attribute> list);

	/**
	 * This method adds value for a given key, a variant with a respective values.
	 */
	public void addAttribute(Attribute attr);

	/**
	 * This method adds value for a given key, a variant with a respective values.
	 */
	public void addAttribute(String key, @SuppressWarnings("rawtypes") Value value);

	/**
	 * This method adds values for a given key, a variant with a respective values.
	 */
	public void addAttribute(String attributeKey, @SuppressWarnings("rawtypes") List<Value> attributeValues);

	/**
	 * This method returns the value for the given key.
	 */
	public Attribute getAttributeForKey(String key);

	/**
	 * This method checks if the current node has no parent which means it is the
	 * root node in the data structure.
	 */
	public boolean isRoot();

	/**
	 * This method checks if the current node has no children which means that the
	 * current node is a leaf.
	 * 
	 * @return
	 */
	public boolean isLeaf();

	public void setCloned(boolean isComponent);

	public boolean isClone();

	/**
	 * Returns the parent Node of this Node if available else this node is the root
	 * node.
	 */
	public Node getParent();

	/**
	 * This method sets the parent Node
	 */
	public void setParent(Node parent);

	/**
	 * This method returns all child nodes of the current node.
	 * 
	 * @return
	 */
	public List<Node> getChildren();

	/**
	 * This method returns all children nodes of the given type.
	 */
	public List<Node> getNodesOfType(String nodeType);

	public void addChildWithParent(Node child);

	/**
	 * This method adds a child node to the current node at a given position and
	 * sets the parent.
	 */
	public void addChildWithPositionAndParent(Node node, int position);

	/**
	 * This method adds a child node to the current node and sets the parent.
	 */
	public void addChild(Node node);

	/**
	 * This method adds a child node to the current node at a given position and
	 * sets the parent.
	 */
	public void addChild(Node node, int position);

	/**
	 * Returns the total number of child nodes
	 */
	public int getNumberOfChildren();

	/**
	 * This method returns the variability class of this node.
	 */
	public VariabilityClass getVariabilityClass();

	/**
	 * This method sets the variability class of this node.
	 */
	public void setVariabilityClass(VariabilityClass varClass);

	/**
	 * This method returns all node types that are contained in this node. The node
	 * type of this node is included as well as the children node types and
	 * children's children node types.
	 */
	public Set<String> getAllNodeTypes();

	/**
	 * This method returns the UUID of this node
	 */
	public UUID getUUID();

	/**
	 * This method sets the UUID of this node.
	 */
	public void setUUID(UUID uuid);

	/**
	 * Sets a customizable, not necessarily consistently defined node type Use this
	 * method in parsers, when defining more precise types of node than available
	 * with standardized NodeTypes
	 */

	public void setNodeType(String nodeType);

	/**
	 * This method creates a deep copy of this node.
	 */
	public Node cloneNode();

	/**
	 * This method returns the position of this node.
	 * 
	 * @return
	 */
	public int getPosition();

	public void addChildAtPosition(Node child, int position);

	/**
	 * This method sets the position of this node.
	 */
	public void setPosition(int position);

	/**
	 * Sets the standardized node type of this node
	 */
	public void setStandardizedNodeType(NodeType type);

	/**
	 * Gets the standardized node type of this node
	 */
	public NodeType getStandardizedNodeType();

	/**
	 * Changes how the node displays itself when calling .toString()
	 */
	public void setRepresentation(String representation);

	/**
	 * Sorting all child elements using start and endline
	 */
	public void sortChildNodes();

	/**
	 * Traverse the Node composite in a breadth first manner. Not safe for cycles
	 */
	public Iterable<Node> breadthFirstSearch();

	/**
	 * Traverse the Node composite in a depth first manner. Not safe for cycles
	 */
	public Iterable<Node> depthFirstSearch();

	public void addNodeAfterwards(Node node);

	/**
	 * This method returns the number of optional elements within this comparison.
	 */
	public int numberOfOptionals();

	/**
	 * This method returns the number of alternative elements within this
	 * comparison.
	 */
	public int numberOfAlternatives();

	/**
	 * This method returns the number of mandatory elements within this comparison.
	 */
	public int numberOfMandatories();

	/**
	 * Returns the configuration of a single variant
	 */
	public Configuration createConfiguration();

	/**
	 * removes the node (and all its children) from the parent
	 * 
	 * @return the index of the previous position in the parent
	 */
	public int cut();

	/**
	 * removes the node from the parent. All the children will be added to the
	 * parent
	 * 
	 * @return the index of the previous position in the parent
	 */
	public int cutWithoutChildren();

	/**
	 * returns the String from the first Value to the corresponding Attribute. The
	 * index selects the specific Attribute. The first Attribute has index.
	 * 
	 * @param index
	 * @return the Value as String
	 */
	public String getValueAt(int index);

	/**
	 * This method sets the parent of this Node. Also it removes this node from the
	 * old parent and adds it to the new parent. This way no inconsistencies can
	 * occur when moving a node to a different parent.
	 * 
	 * @param parent is the new parent
	 */
	public void updateParent(Node parent);

	/**
	 * This method adds value for a given key and value.
	 * 
	 * @param attributeKey the name of the key
	 * @param value        the name of the value
	 */
	public void addAttribute(String attributeKey, String value);

	public int getAmountOfNodes(int startAmount);

	/**
	 * Returns the set of uuids of itself, its attributes, all of its reachable
	 * child-nodes and their attributes
	 */
	public Set<UUID> getAllUUIDS();

	/**
	 * return uuids of this node and its attributes.
	 */
	public Set<UUID> getUUIDsForNode();

	/**
	 * Recursively adds the uuid of itself, its attributes, its child-nodes and
	 * their attributes to the given set
	 */
	public void addAllUUIDS(Set<UUID> uuids);

	public Node getNodeByUUID(UUID key);

	public void removeElementsOfType(String string);

}
