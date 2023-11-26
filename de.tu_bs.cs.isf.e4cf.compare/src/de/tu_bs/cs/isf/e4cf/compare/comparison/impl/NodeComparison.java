package de.tu_bs.cs.isf.e4cf.compare.comparison.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.CloneConfiguration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.NodeConfigurationUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;

/**
 * The implementation for the comparison of artifacts of type Node.
 * 
 * @author NoLimit
 *
 */
public class NodeComparison extends AbstractComparsion<Node> {
	private static final long serialVersionUID = 7260025397506680712L;
	private NodeComparison parentComparison;

	public NodeComparison(Node leftArtifact, Node rightArtifact) {
		super(leftArtifact, rightArtifact);
	}

	public NodeComparison(Node leftArtifact, Node rightArtifact, float similarity) {
		this(leftArtifact, rightArtifact);
		setSimilarity(similarity);
	}

	public NodeComparison(Node leftArtifact, Node rightArtifact, float similarity, NodeComparison parent) {
		this(leftArtifact, rightArtifact, similarity);
		setParentComparison(parent);
	}

	public void setParentComparison(NodeComparison parent) {
		this.parentComparison = parent;

	}

	public NodeComparison(Node leftArtifact, Node rightArtifact, NodeComparison parent) {
		this(leftArtifact, rightArtifact);
		parent.addChildComparison(this);
	}

	/**
	 * Copy Constructor
	 */
	public NodeComparison(NodeComparison source) {
		this(source.getLeftArtifact(), source.getRightArtifact());
		getChildComparisons().addAll(source.getChildComparisons());
		getResultElements().addAll(source.getResultElements());
		setSimilarity(source.getSimilarity());
	}

	public Pair<Map<String, List<Comparison<Node>>>, Map<String, List<Comparison<Node>>>> findOptionalMatchings() {
		Map<String, List<Comparison<Node>>> leftOptionals = new HashMap<String, List<Comparison<Node>>>();
		Map<String, List<Comparison<Node>>> rightOptionals = new HashMap<String, List<Comparison<Node>>>();
		findOptionalMatchingsRecursivly(this, leftOptionals, rightOptionals);
		return new Pair<Map<String, List<Comparison<Node>>>, Map<String, List<Comparison<Node>>>>(leftOptionals,
				rightOptionals);
	}

	/**
	 * Visits every node comparison and stores all optional elements in two maps
	 * 
	 * @param nextNode
	 */
	private void findOptionalMatchingsRecursivly(Comparison<Node> nextNode,
			Map<String, List<Comparison<Node>>> leftOptionals, Map<String, List<Comparison<Node>>> rightOptionals) {
		// Collect Optional elements
		if (nextNode.getLeftArtifact() == null) {
			String nodeType = nextNode.getRightArtifact().getNodeType();
			if (!rightOptionals.containsKey(nodeType)) {
				rightOptionals.put(nodeType, new ArrayList<Comparison<Node>>());
			} else {
				rightOptionals.get(nodeType).add(nextNode);
			}
		} else if (nextNode.getRightArtifact() == null) {
			String nodeType = nextNode.getLeftArtifact().getNodeType();
			if (!leftOptionals.containsKey(nodeType)) {
				leftOptionals.put(nodeType, new ArrayList<Comparison<Node>>());
			} else {
				leftOptionals.get(nodeType).add(nextNode);
			}
		}
		// call recursively for each element
		nextNode.getChildComparisons().forEach(childNode -> {
			findOptionalMatchingsRecursivly(childNode, leftOptionals, rightOptionals);
		});
	}

	@Override
	public Node mergeArtifacts(List<Configuration> configs, List<CloneConfiguration> components,
			List<CloneConfiguration> fixedConfigs) {
		return mergeArtifacts(configs, components);
	}

	/**
	 * Sets the variability class of nodes and their children to optional
	 */
	public void setNodeOptionalWithChildren(Node node) {
		node.setVariabilityClass(ComparisonUtil.getClassForSimilarity(0f));
		node.getChildren().forEach(childNode -> {
			if (!childNode.isClone()) {
				setNodeOptionalWithChildren(childNode);
			} else {
				childNode.setVariabilityClass(ComparisonUtil.getClassForSimilarity(0f));
				setNodeOptionalWithChildren(childNode);
			}
		});
	}

	/**
	 * this function merges two node with its children
	 */
	public Node mergeArtifacts(List<Configuration> variantConfigurations,
			List<CloneConfiguration> cloneConfigurations) {
		/** Optional Case: Artifact is only in one variant **/
		if (getLeftArtifact() == null || getRightArtifact() == null) {
			Node node = getLeftArtifact() == null ? getRightArtifact() : getLeftArtifact();

			node.setVariabilityClass(VariabilityClass.OPTIONAL);
			// check if this node already exits and remove it if needed

			if (getParentComparison() != null) {
				Node parentNode = getParentComparison().getLeftArtifact() != null
						? getParentComparison().getLeftArtifact()
						: getParentComparison().getRightArtifact();
				// Check if parent node contains component
				if (node.isClone()) {
					for (Node childNode : parentNode.getChildren()) {
						if (childNode.getUUID().equals(node.getUUID())) {
							// System.out.println("optional remove: " + node.getUUID());
							return null;
						}
					}
				}
			}
			setNodeOptionalWithChildren(node);

			return node;
			/*********************************************************/
		} else {
			/******* Similar Nodes Case: Artifact are similar *******/

			if (getSimilarity() == 1.0) {
				getLeftArtifact().setVariabilityClass(VariabilityClass.MANDATORY);
			} else {
				getLeftArtifact().setVariabilityClass(VariabilityClass.ALTERNATIVE);
			}

			// left side no clone configurations we have to derive the configuration before
			// we merge artifacts in it
			CloneConfiguration cloneConfiguration = null;
			if (!getLeftArtifact().isClone() && getRightArtifact().isClone()) {
				cloneConfiguration = createCloneConfiguration(getLeftArtifact());
			}

			/** Propagate UUIDS **/
			propagateUUIDs(cloneConfigurations);

			/** Process Child Nodes **/
			List<Node> leftArtifacts = new ArrayList<Node>(getLeftArtifact().getChildren());
			getLeftArtifact().getChildren().clear();
			for (Comparison<Node> childComparison : getChildComparisons()) {
				NodeComparison nodeComparison = (NodeComparison) childComparison;
				getLeftArtifact()
						.addChildWithParent(nodeComparison.mergeArtifacts(variantConfigurations, cloneConfigurations));
				leftArtifacts.remove(nodeComparison.getLeftArtifact());
			}

			if (leftArtifacts.size() > 0) {
				Iterator<Node> nodeIterator = leftArtifacts.iterator();
				outter: while (nodeIterator.hasNext()) {
					try {
						Node node = nodeIterator.next();
						for (Node childNode : getLeftArtifact().getChildren()) {
							if (childNode.getUUID().equals(node.getUUID())) {
								nodeIterator.remove();
								continue outter;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				leftArtifacts.forEach(leftArtifact -> {
					getLeftArtifact().addChildWithParent(leftArtifact);
				});
			}
			/***********************/
			// At this point all common child nodes have the same identifier

			// add uncompared elements from a merge before

			/** Cases for the merge of clones **/
			if (getLeftArtifact().isClone() && !getRightArtifact().isClone()) {
				/** Left node is a clone right node is not a clone **/
				if (parentComparison != null)
					// System.out.println("Clone left:" + getLeftArtifact().getUUID() + " parent:"
					// + getParentComparison().getLeftArtifact().getUUID());

					cloneConfiguration = createCloneConfiguration(getRightArtifact());
				cloneConfiguration.isMerged = true;
				cloneConfigurations.add(cloneConfiguration);

			} else if (!getLeftArtifact().isClone() && getRightArtifact().isClone()) {
				/** Left node is not a clone right node is a clone **/
				// System.out.println("Clone right:" + getLeftArtifact().getUUID() + " parent:"
				// + getParentComparison().getLeftArtifact().getUUID());
				// iterate over all variant configurations and check if the UUIDs from the clone
				// config are contained
				if (getChildComparisons() != null) {
					lockConfigurations(cloneConfiguration.componentUUID,
							getParentComparison().getLeftArtifact().getUUID(), cloneConfigurations);
				}

				for (Configuration config : variantConfigurations) {
					if (config.getUUIDs().contains(cloneConfiguration.componentUUID)) {
						// Only use those UUIDs that are in the configuration at this evolution step
						Set<UUID> variantConfigIDs = new HashSet<UUID>(config.getUUIDs());
						Set<UUID> cloneConfigIDs = new HashSet<UUID>(cloneConfiguration.getConfiguration().getUUIDs());
						cloneConfigIDs.retainAll(variantConfigIDs);
						// copy configuration
						CloneConfiguration cloneConfig = new CloneConfiguration();
						cloneConfig.setComponentUUID(cloneConfiguration.getComponentUUID());
						cloneConfig.setParentUUID(cloneConfiguration.getParentUUID());
						cloneConfig.getConfiguration().getUUIDs().addAll(cloneConfigIDs);
						cloneConfig.isMerged = true;

						config.getCloneConfigurations().add(cloneConfig);
						config.getUUIDs().removeAll(cloneConfig.configuration.getUUIDs());
						config.getUUIDs().remove(cloneConfig.getComponentUUID());
					}
				}
				getLeftArtifact().setCloned(true);
			} else if (getLeftArtifact().isClone() && getRightArtifact().isClone()) {
				/** Both nodes are clones **/
				// System.out.println("Both clones:" + getLeftArtifact().getUUID());
				if (getParentComparison() != null) {
					lockConfigurations(getLeftArtifact().getUUID(), getParentComparison().getLeftArtifact().getUUID(),
							cloneConfigurations);
				}
			}
			return getLeftArtifact();
		}
	}

	/**
	 * This method merges two nodes by propagating all UUIDs from the left node to
	 * the right node and adding all remaining elements from the right artifact to
	 * the left one
	 * 
	 * @param cloneConfigurations
	 */
	private void propagateUUIDs(List<CloneConfiguration> cloneConfigurations) {
		// Propagate UUIDs
		replaceUUIDs(cloneConfigurations, getRightArtifact().getUUID(), getLeftArtifact().getUUID());
		getRightArtifact().setUUID(getLeftArtifact().getUUID());
		//
		List<Attribute> rightAttributes = new ArrayList<Attribute>(getRightArtifact().getAttributes());
		for (Attribute leftAttribute : getLeftArtifact().getAttributes()) {
			for (Attribute rightAttribute : getRightArtifact().getAttributes()) {
				if (leftAttribute.getAttributeKey().equals(rightAttribute.getAttributeKey())) {
					List<Value> rightValues = new ArrayList<Value>(rightAttribute.getAttributeValues());
					rightAttributes.remove(rightAttribute);
					// replace UUID
					replaceUUIDs(cloneConfigurations, rightAttribute.getUUID(), leftAttribute.getUUID());
					rightAttribute.setUuid(leftAttribute.getUUID());
					//
					for (Value leftValue : leftAttribute.getAttributeValues()) {
						for (Value rightValue : rightAttribute.getAttributeValues()) {
							if (leftValue.getValue().equals(rightValue.getValue())) {
								rightValues.remove(rightValue);
								// replace UUID
								replaceUUIDs(cloneConfigurations, rightValue.getUUID(), leftValue.getUUID());
								rightValue.setUUID(leftValue.getUUID());
								//
							}
						}
					}
					leftAttribute.addAttributeValues(rightValues);
				}
			}
		}
		// Add attributes to the left artifact that were not contained
		getLeftArtifact().getAttributes().addAll(rightAttributes);
	}

	/**
	 * Locks a configuration for the UUID propagation process
	 */
	private void lockConfigurations(UUID clone, UUID parent, List<CloneConfiguration> configs) {
		configs.forEach(cloneConfig -> {
			if (cloneConfig.getComponentUUID().equals(clone) && cloneConfig.getParentUUID().equals(parent)) {
				cloneConfig.isMerged = true;
			}
		});
	}

	/**
	 * This method creates a clone configuration for the given clone node.
	 */
	private CloneConfiguration createCloneConfiguration(Node clonedNode) {
		CloneConfiguration componentConfig;
		Node parentNode = getParentComparison().getLeftArtifact() != null ? getParentComparison().getLeftArtifact()
				: getParentComparison().getRightArtifact();
		componentConfig = NodeConfigurationUtil.createCloneConfiguration(clonedNode, parentNode.getUUID());
		return componentConfig;
	}

	/**
	 * This method checks if a clone configuration contains the replace UUID and
	 * replaces it with UUID.
	 */
	public void replaceUUIDs(List<CloneConfiguration> cloneConfigurations, UUID replace, UUID with) {
		for (CloneConfiguration cloneConfig : cloneConfigurations) {
			replaceUUID(cloneConfig, replace, with);
		}
	}

	/**
	 * Replaces all UUIDS in the given configuration
	 */
	public void replaceUUID(CloneConfiguration cloneConfiguration, UUID replace, UUID with) {
		if (!cloneConfiguration.isMerged) {
			// check if clone configuration changed
			if (cloneConfiguration.componentUUID.equals(replace)) {
				cloneConfiguration.setComponentUUID(with);
			}
			// check if clone configuration changed
			if (cloneConfiguration.parentUUID.equals(replace)) {
				cloneConfiguration.setParentUUID(with);
			}

			if (cloneConfiguration.getConfiguration().getUUIDs().contains(replace)) {
				cloneConfiguration.getConfiguration().getUUIDs().remove(replace);
				cloneConfiguration.getConfiguration().getUUIDs().add(with);
			}
		}
	}

	@Override
	public boolean areArtifactsOfSameType() {
		return (getLeftArtifact() != null && getRightArtifact() != null)
				? getLeftArtifact().getNodeType().equals(getRightArtifact().getNodeType())
				: false;
	}

	public NodeComparison getParentComparison() {
		return parentComparison;
	}

}