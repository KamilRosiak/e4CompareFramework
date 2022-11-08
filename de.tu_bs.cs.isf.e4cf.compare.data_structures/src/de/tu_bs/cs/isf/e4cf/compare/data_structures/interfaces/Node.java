package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.List;
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
public interface Node {
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
	public void addChildWithParent(Node node, int position);

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
	 * 
	 * @return
	 */
	public List<String> getAllNodeTypes();

	/**
	 * This method returns the UUID of this node
	 * 
	 * @return
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
	 * 
	 * @return The iterator used for traversal
	 */
	public Iterable<Node> breadthFirstSearch();

	/**
	 * Traverse the Node composite in a depth first manner. Not safe for cycles
	 * 
	 * @return The iterator used for traversal
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
	 * 
	 * @return
	 */
	public Configuration createConfiguration();
	
	/**
	 * removes the node (and all its children) from the parent
	 * 
	 * @return the index of the previous position in the parent
	 */
	public int cut();
	
	/**
	 * removes the node from the parent. All the children will be added to the parent
	 * 
	 * @return the index of the previous position in the parent
	 */
	public int cutWithoutChildren();

}
