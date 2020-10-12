package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;

/**
 * This interfaces defines all methods that are needed to realize a node.
 * 
 * @author Kamil Rosiak
 *
 */
public interface Node extends Serializable {

	/**
	 * This method returns the type of the node, e.g., class, method, statement. This type has to be defined during the parsing process where.
	 * @return
	 */
	public String getNodeType();
	
	/**
	 * This method returns all values that are available for this node, e.g. an assignment has values for the left side and right side such as  a = b. 
	 * In the Values class you can store values that can be identified by their variant.
	 */
	public List<Attribute> getAttributes();
	
	/**
	 * This method adds value for a given key, a variant with a respective values.
	 */
	public void addAttribute(String key, String value);
	
	/**
	 * This method returns the value for the given key.
	 */
	public Attribute getAttributeForKey(String key);
	
	/**
	 * This method checks if the current node has no parent which means it is the root node in the data structure.
	 */
	public boolean isRoot();
	
	/**
	 * This method checks if the current node has no children which means that the current node is a leaf.
	 * @return
	 */
	public boolean isLeaf();
	
	/**
	 * Returns the parent Node of this Node if available else this node is the root node.
	 */
	public Node getParent();
	
	/**
	 * This method sets the parent Node
	 */
	public void setParent(Node parent);
	
	/**
	 * This method returns all child nodes of the current node.
	 * @return
	 */
	public List<Node> getChildren();
	
	/**
	 * This method returns all children nodes of the given type.
	 */
	public List<Node> getChildrenOfType(String nodeType);
	
	/**
	 * This method adds a child node to the current node and sets the parent.
	 */
	public void addChild(Node node);
	
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
	 * This method returns all node types that are contained in this node. 
	 * The node type of this node is included as well as the children node types and children's children node types.
	 * @return
	 */
	public List<String> getAllNodeTypes();
	
    	/**
    	 * This method returns the UUID of this node
    	 * @return
    	 */
	public UUID getUUID();
	/**
	 * This method sets the UUID of this node.
	 */
	public void setUUID(UUID uuid);
	
}
