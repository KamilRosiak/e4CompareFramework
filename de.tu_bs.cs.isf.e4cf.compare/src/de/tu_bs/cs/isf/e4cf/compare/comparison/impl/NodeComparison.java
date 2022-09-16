package de.tu_bs.cs.isf.e4cf.compare.comparison.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.CloneConfiguration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.NodeConfigurationUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.MergeContext;
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
	public Node mergeArtifacts(List<Configuration> configs, MergeContext context, List<CloneConfiguration> components,
			List<CloneConfiguration> fixedConfigs) {
		return mergeArtifacts(true, context, configs, components, fixedConfigs);
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
			}
		});
	}

	/**
	 * This method merges the contained nodes into a 150% model.
	 */
	public Node mergeArtifacts(boolean omitOptionalChildren, MergeContext context, List<Configuration> existingConfigs,
			List<CloneConfiguration> componentConfigurations, List<CloneConfiguration> fixedConfigs) {
		// if one of both artifact is null its means that we have an optional and can
		// keep the implementation below this artifacts.

		if (getLeftArtifact() == null || getRightArtifact() == null) {
			Node node = getLeftArtifact() == null ? getRightArtifact() : getLeftArtifact();
			node.setVariabilityClass(VariabilityClass.OPTIONAL);

			if (getParentComparison() != null) {
				Node parentNode = getParentComparison().getLeftArtifact() != null
						? getParentComparison().getLeftArtifact()
						: getParentComparison().getRightArtifact();
				// Check if parent node contains component
				if (node.isClone()) {
					for (Node childNode : parentNode.getChildren()) {
						if (childNode.getUUID().equals(node.getUUID())) {
							// mergeNodes(childNode, node);
							System.out.println("optional remove: " + node.getUUID());
							return null;
						}
					}
				}
			}

			if (omitOptionalChildren) {
				// Set all nodes to optional
				setNodeOptionalWithChildren(node);
			} else {
				// Process the Children as they were compared
				node.setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
				node.getChildren().clear();
				for (Comparison<Node> childComparision : getChildComparisons()) {
					node.addChildWithParent(((NodeComparison) childComparision).mergeArtifacts(omitOptionalChildren,
							context, existingConfigs, componentConfigurations, fixedConfigs));
				}
			}
			return node;
		}

		// all artifacts which are equals

		if (getSimilarity() == ComparisonUtil.MANDATORY_VALUE) {

			// propagate UUIDS
			context.changedUUIDs.put(getRightArtifact().getUUID(), getLeftArtifact().getUUID());
			replaceUUIDs(componentConfigurations, getRightArtifact().getUUID(), getLeftArtifact().getUUID());
			getRightArtifact().setUUID(getLeftArtifact().getUUID());

			// collect attributes that are not contained
			List<Attribute> otherAttrs = new ArrayList<Attribute>();
			otherAttrs.addAll(getLeftArtifact().getAttributes());

			// propagate artifact UUIDS
			getRightArtifact().getAttributes().forEach(rightAttr -> {
				getLeftArtifact().getAttributes().forEach(leftAttr -> {
					if (rightAttr.getAttributeKey().equals(leftAttr.getAttributeKey())) {
						// remove artifact from others because it is in both nodes contained
						otherAttrs.remove(leftAttr);
						// propagate attr UUID from the model to the merged node
						replaceUUIDs(componentConfigurations, rightAttr.getUUID(), leftAttr.getUUID());
						rightAttr.setUuid(leftAttr.getUUID());
						// propagate value ids
						rightAttr.getAttributeValues().forEach(rightValue -> {
							leftAttr.getAttributeValues().forEach(leftValue -> {
								if (rightValue.equals(leftValue)) {
									context.changedUUIDs.put(rightValue.getUUID(), getLeftArtifact().getUUID());
									replaceUUIDs(componentConfigurations, rightValue.getUUID(), leftValue.getUUID());
									rightValue.setUUID(leftValue.getUUID());
								}
							});
							if (!leftAttr.containsValue(rightValue)) {
								leftAttr.addAttributeValue(rightValue);
							}
						});
					}
				});
			});
			getRightArtifact().getAttributes().addAll(otherAttrs);

			List<Node> leftArtifacts = new ArrayList<Node>(getLeftArtifact().getChildren());
			List<Node> rightArtifacts = new ArrayList<Node>(getRightArtifact().getChildren());
			// Process the Children as they were compared
			getLeftArtifact().setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
			getLeftArtifact().getChildren().clear();
			for (Comparison<Node> childComparision : getChildComparisons()) {
				getLeftArtifact().addChildWithParent(((NodeComparison) childComparision).mergeArtifacts(
						omitOptionalChildren, context, existingConfigs, componentConfigurations, fixedConfigs));
				leftArtifacts.remove(childComparision.getLeftArtifact());
				rightArtifacts.remove(childComparision.getRightArtifact());
			}
			// Components have to be managed speratly
			if (getLeftArtifact().isClone() && !getRightArtifact().isClone()) {
				System.out.println("Mandatory Component left:" + getLeftArtifact().getUUID());

				System.out.println(getParentComparison().getLeftArtifact().getUUID());

				getRightArtifact().setCloned(true);
				CloneConfiguration componentConfig = NodeConfigurationUtil
						.createComponentConfiguration(getRightArtifact(), getLeftArtifact().getUUID());
				componentConfigurations.add(componentConfig);

			} else if (!getLeftArtifact().isClone() && getRightArtifact().isClone()) {
				System.out.println("Mandatory Component  right:" + getLeftArtifact().getUUID());
				System.out.println(getParentComparison().getLeftArtifact().getUUID());

				getLeftArtifact().setCloned(true);
				CloneConfiguration componentConfig = NodeConfigurationUtil
						.createComponentConfiguration(getLeftArtifact(), getLeftArtifact().getUUID());
				existingConfigs.forEach(config -> {
					if (config.getUUIDs().containsAll(componentConfig.configuration.getUUIDs())) {
						config.getComponentConfigurations().add(componentConfig);
						System.out.println("before:" + config.getUUIDs().size());
						config.getUUIDs().removeAll(componentConfig.configuration.getUUIDs());
						config.getUUIDs().remove(componentConfig.getComponentUUID());
						System.out.println("after:" + config.getUUIDs().size());
					}
				});
			}
			if (leftArtifacts.size() > 0) {
				leftArtifacts.forEach(e -> {
					if (!getLeftArtifact().getChildren().contains(e)) {
						getLeftArtifact().getChildren().add(e);
					}
				});
			}
			return getLeftArtifact();
		} else {
			getLeftArtifact().setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
			CloneConfiguration componentConfig = null;
			// left side no clone configurations we have to derive the configuration before
			// we merge artifacts in it
			if (!getLeftArtifact().isClone() && getRightArtifact().isClone()) {
				componentConfig = createCloneConfiguration(getLeftArtifact());
			}

			Set<Attribute> containedAttrs = new HashSet<Attribute>();
			// first merge attributes
			for (Attribute leftAttr : getLeftArtifact().getAttributes()) {
				for (Attribute rightAttr : getRightArtifact().getAttributes()) {
					// same attr type
					if (leftAttr.keyEquals(rightAttr)) {

						// propagate value ids
						for (Value rightValue : rightAttr.getAttributeValues()) {
							for (Value leftValue : leftAttr.getAttributeValues()) {
								if (rightValue.equals(leftValue)) {
									UUID leftID = leftValue.getUUID();
									UUID rightID = rightValue.getUUID();

									replaceUUIDs(componentConfigurations, rightID, leftID);
									if (componentConfig != null) {
										replaceUUID(componentConfig, rightID, leftID);
									}
									rightValue.setUUID(leftID);
								}
							}
							if (!leftAttr.containsValue(rightValue)) {
								leftAttr.addAttributeValue(rightValue);
							}
						}
						UUID leftID = leftAttr.getUUID();
						UUID rightID = rightAttr.getUUID();

						replaceUUIDs(componentConfigurations, rightID, leftID);

						if (componentConfig != null) {
							replaceUUID(componentConfig, rightID, leftID);
						}
						rightAttr.setUuid(leftID);
						containedAttrs.add(rightAttr);
					}
				}
			}

			// remove all contained attrs from all others
			Set<Attribute> notContainedRightAttrs = new HashSet<Attribute>(getRightArtifact().getAttributes());
			notContainedRightAttrs.removeAll(containedAttrs);
			getLeftArtifact().getAttributes().addAll(notContainedRightAttrs);

			UUID leftID = getLeftArtifact().getUUID();
			UUID rightID = getRightArtifact().getUUID();
			replaceUUIDs(componentConfigurations, rightID, leftID);
			if (componentConfig != null) {
				replaceUUID(componentConfig, rightID, leftID);
			}

			List<Node> leftArtifacts = new ArrayList<Node>(getLeftArtifact().getChildren());
			List<Node> rightArtifacts = new ArrayList<Node>(getRightArtifact().getChildren());
			// process child comparisons recursively
			getLeftArtifact().getChildren().clear();
			for (Comparison<Node> childComparision : getChildComparisons()) {
				getLeftArtifact().addChildWithParent(((NodeComparison) childComparision).mergeArtifacts(
						omitOptionalChildren, context, existingConfigs, componentConfigurations, fixedConfigs));
				leftArtifacts.remove(childComparision.getLeftArtifact());
				rightArtifacts.remove(childComparision.getRightArtifact());
			}

			getRightArtifact().setUUID(getLeftArtifact().getUUID());
			// add artifacts min line number
			getLeftArtifact()
					.setStartLine(Math.min(getLeftArtifact().getStartLine(), getRightArtifact().getStartLine()));
			getLeftArtifact().setEndLine(Math.min(getLeftArtifact().getEndLine(), getRightArtifact().getEndLine()));

			getLeftArtifact().sortChildNodes();

			// Components have to be managed speratly
			if (getLeftArtifact().isClone() && !getRightArtifact().isClone()) {
				Node parentNode = getParentComparison().getLeftArtifact() != null
						? getParentComparison().getLeftArtifact()
						: getParentComparison().getRightArtifact();
				System.out.println("Sim:" + getSimilarity() + " Alternativ Component left: "
						+ getLeftArtifact().getUUID() + " NoComponent Right:" + getRightArtifact().getUUID()
						+ " Parent: " + parentNode.getUUID());
				// we need a clone configuration for the right side
				//componentConfigurations.add(createCloneConfiguration(getRightArtifact()));
				fixedConfigs.add(createCloneConfiguration(getRightArtifact()));
				getRightArtifact().setCloned(true);

			} else if (!getLeftArtifact().isClone() && getRightArtifact().isClone()) {
				Node parentNode = getParentComparison().getLeftArtifact() != null
						? getParentComparison().getLeftArtifact()
						: getParentComparison().getRightArtifact();
				System.out.println("Alternativ Component right:" + getLeftArtifact().getUUID() + " Parent: "
						+ parentNode.getUUID());
				
				
				for (Configuration config : existingConfigs) {
					if (config.getUUIDs().contains(componentConfig.componentUUID)) {
						Set<UUID> variantConfigIDs = new HashSet<UUID>(config.getUUIDs());
						Set<UUID> cloneConfigIDs = new HashSet<UUID>(componentConfig.getConfiguration().getUUIDs());
						cloneConfigIDs.retainAll(variantConfigIDs);
						CloneConfiguration cloneConfig = new CloneConfiguration();
						cloneConfig.setComponentUUID(componentConfig.getComponentUUID());
						cloneConfig.setParentUUID(componentConfig.getParentUUID());
						cloneConfig.getConfiguration().getUUIDs().addAll(cloneConfigIDs);

						config.getComponentConfigurations().add(cloneConfig);
						config.getUUIDs().removeAll(cloneConfig.configuration.getUUIDs());
						config.getUUIDs().remove(cloneConfig.getComponentUUID());
					}

				}
				//at this point the configuration is closed and all replace action are not applied anymore
				List<CloneConfiguration> configsToRemove = new ArrayList<CloneConfiguration>();
				for (CloneConfiguration cloneConf : componentConfigurations) {
					if (cloneConf.getParentUUID().equals(componentConfig.getParentUUID())
							&& cloneConf.getComponentUUID().equals(componentConfig.getComponentUUID())) {
						fixedConfigs.add(cloneConf);
						configsToRemove.add(cloneConf);
					}
				}
				componentConfigurations.removeAll(configsToRemove);

				getLeftArtifact().setCloned(true);
			}

			if (leftArtifacts.size() > 0) {
				leftArtifacts.forEach(e -> {
					if (!getLeftArtifact().getChildren().contains(e)) {
						getLeftArtifact().getChildren().add(e);
					}
				});
			}

			return getLeftArtifact();
		}

	}

	private void mergeNodes(Node childNode, Node node) {
		List<Node> notContainedNodes = new ArrayList<Node>(node.getChildren());
		childNode.getChildren().forEach(child -> {
			node.getChildren().forEach(nodeChild -> {
				if (child.getUUID().equals(nodeChild.getUUID())) {
					notContainedNodes.remove(nodeChild);
					mergeNodes(child, nodeChild);
				}
			});
		});
		childNode.getChildren().addAll(notContainedNodes);

	}

	private CloneConfiguration createCloneConfiguration(Node clonedNode) {
		CloneConfiguration componentConfig;
		Node parentNode = getParentComparison().getLeftArtifact() != null ? getParentComparison().getLeftArtifact()
				: getParentComparison().getRightArtifact();
		componentConfig = NodeConfigurationUtil.createComponentConfiguration(clonedNode, parentNode.getUUID());
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

	public void replaceUUID(CloneConfiguration cloneConfiguration, UUID replace, UUID with) {
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