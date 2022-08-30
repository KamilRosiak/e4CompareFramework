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
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ComponentConfiguration;
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
	public Node mergeArtifacts(MergeContext context, List<Configuration> configs,
			List<ComponentConfiguration> components) {
		return mergeArtifacts(true, context, configs, components);
	}

	/**
	 * Sets the variability class of nodes and their children to optional
	 */
	public void setNodeOptionalWithChildren(Node node) {
		node.setVariabilityClass(ComparisonUtil.getClassForSimilarity(0f));
		node.getChildren().forEach(childNode -> {
			if (!childNode.isComponent()) {
				setNodeOptionalWithChildren(childNode);
			} else {
				childNode.setVariabilityClass(ComparisonUtil.getClassForSimilarity(0f));
			}
		});
	}

	public Node mergeArtifacts(boolean omitOptionalChildren, MergeContext context, List<Configuration> existingConfigs,
			List<ComponentConfiguration> componentConfigurations) {
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
				if (node.isComponent()) {
					for (Node childNode : parentNode.getChildren()) {
						if (childNode.getUUID().equals(node.getUUID())) {
							System.out.println("removed optional:" + node.getUUID());
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
							context, existingConfigs, componentConfigurations));
				}
			}
			return node;
		}

		// all artifacts which are equals
		if (getSimilarity() == ComparisonUtil.MANDATORY_VALUE) {
			// mandatory is a default value if the artifacts was optional in a previous
			// iteration it should stay as optional
			context.changedUUIDs.put(getRightArtifact().getUUID(), getLeftArtifact().getUUID());
			getRightArtifact().setUUID(getLeftArtifact().getUUID());// Set the UUID of the left artifact because it is
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
						context.changedUUIDs.put(rightAttr.getUUID(), leftAttr.getUUID());
						rightAttr.setUuid(leftAttr.getUUID());

						// propagate value ids
						rightAttr.getAttributeValues().forEach(rightValue -> {
							leftAttr.getAttributeValues().forEach(leftValue -> {
								if (rightValue.equals(leftValue)) {
									context.changedUUIDs.put(rightValue.getUUID(), leftValue.getUUID());
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

			// Process the Children as they were compared
			getRightArtifact().setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
			getRightArtifact().getChildren().clear();
			for (Comparison<Node> childComparision : getChildComparisons()) {
				getRightArtifact().addChildWithParent(((NodeComparison) childComparision)
						.mergeArtifacts(omitOptionalChildren, context, existingConfigs, componentConfigurations));
			}

			// Components have to be managed speratly

			if (getLeftArtifact().isComponent() && !getRightArtifact().isComponent()) {
				System.out.println("Mandatory Component left:" + getLeftArtifact().getUUID());

				System.out.println(getParentComparison().getLeftArtifact().getUUID());

				getRightArtifact().setComponent(true);
				ComponentConfiguration componentConfig = NodeConfigurationUtil
						.createComponentConfiguration(getRightArtifact(), getLeftArtifact().getUUID());
				componentConfigurations.add(componentConfig);

			} else if (!getLeftArtifact().isComponent() && getRightArtifact().isComponent()) {
				System.out.println("Mandatory Component  right:" + getLeftArtifact().getUUID());
				System.out.println(getParentComparison().getLeftArtifact().getUUID());

				getLeftArtifact().setComponent(true);
				ComponentConfiguration componentConfig = NodeConfigurationUtil
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

			if (getRightArtifact().isComponent()) {
				getLeftArtifact().setComponent(true);
			}

			return getLeftArtifact();
		} else {
			getLeftArtifact().setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
			ComponentConfiguration componentConfig = null;
			// left side no clone configurations
			if (!getLeftArtifact().isComponent() && getRightArtifact().isComponent()) {
				Node parentNode = getParentComparison().getLeftArtifact() != null
						? getParentComparison().getLeftArtifact()
						: getParentComparison().getRightArtifact();
				componentConfig = NodeConfigurationUtil.createComponentConfiguration(getLeftArtifact(),
						parentNode.getUUID());
			}

			if (getLeftArtifact().isComponent() && !getRightArtifact().isComponent()) {
				Node parentNode = getParentComparison().getLeftArtifact() != null
						? getParentComparison().getLeftArtifact()
						: getParentComparison().getRightArtifact();
				componentConfig = NodeConfigurationUtil.createComponentConfiguration(getRightArtifact(),
						parentNode.getUUID());
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
									context.changedUUIDs.put(rightValue.getUUID(), leftValue.getUUID());
									if (componentConfig != null && componentConfig.getConfiguration().getUUIDs()
											.contains(rightValue.getUUID())) {
										componentConfig.getConfiguration().getUUIDs().remove(rightValue.getUUID());
										componentConfig.getConfiguration().getUUIDs().add(leftValue.getUUID());
									}
									rightValue.setUUID(leftValue.getUUID());
								}
							}

							if (!leftAttr.containsValue(rightValue)) {
								leftAttr.addAttributeValue(rightValue);
							}
						}

						context.changedUUIDs.put(rightAttr.getUUID(), leftAttr.getUUID());
						if (componentConfig != null
								&& componentConfig.getConfiguration().getUUIDs().contains(rightAttr.getUUID())) {
							componentConfig.getConfiguration().getUUIDs().remove(rightAttr.getUUID());
							componentConfig.getConfiguration().getUUIDs().add(leftAttr.getUUID());
						}
						rightAttr.setUuid(leftAttr.getUUID());
						containedAttrs.add(rightAttr);
					}
				}
			}

			// remove all contained attrs from all others
			Set<Attribute> allAttrs = new HashSet<Attribute>(getRightArtifact().getAttributes());
			allAttrs.addAll(getLeftArtifact().getAttributes());
			allAttrs.removeAll(containedAttrs);
			// put all other attributes from right to left because it wasn't contained
			// before
			getLeftArtifact().getAttributes().clear();
			getLeftArtifact().getAttributes().addAll(allAttrs);

			context.changedUUIDs.put(getRightArtifact().getUUID(), getLeftArtifact().getUUID());
			getRightArtifact().setUUID(getLeftArtifact().getUUID());

			// process child comparisons recursively
			getLeftArtifact().getChildren().clear();
			for (Comparison<Node> childComparision : getChildComparisons()) {
				getLeftArtifact().addChildWithParent(((NodeComparison) childComparision)
						.mergeArtifacts(omitOptionalChildren, context, existingConfigs, componentConfigurations));
			}
			// add artifacts min line number
			getLeftArtifact()
					.setStartLine(Math.min(getLeftArtifact().getStartLine(), getRightArtifact().getStartLine()));
			getLeftArtifact().setEndLine(Math.min(getLeftArtifact().getEndLine(), getRightArtifact().getEndLine()));

			getLeftArtifact().sortChildNodes();

			// Components have to be managed speratly
			if (getLeftArtifact().isComponent() && !getRightArtifact().isComponent()) {
				Node parentNode = getParentComparison().getLeftArtifact() != null
						? getParentComparison().getLeftArtifact()
						: getParentComparison().getRightArtifact();
				System.out.println("Alternativ Component left:" + getLeftArtifact().getUUID() + " Parent: "
						+ parentNode.getUUID());

				componentConfigurations.add(componentConfig);
				// getRightArtifact().setComponent(true);
			} else if (!getLeftArtifact().isComponent() && getRightArtifact().isComponent()) {
				Node parentNode = getParentComparison().getLeftArtifact() != null
						? getParentComparison().getLeftArtifact()
						: getParentComparison().getRightArtifact();
				System.out.println("Alternativ Component right:" + getLeftArtifact().getUUID() + " Parent: "
						+ parentNode.getUUID());

				for (Configuration config : existingConfigs) {
					if (config.getUUIDs().contains(componentConfig.componentUUID)) {
						config.getComponentConfigurations().add(componentConfig);
						config.getUUIDs().removeAll(componentConfig.configuration.getUUIDs());
						// config.getUUIDs().remove(componentConfig.getComponentUUID());
						System.out.println("Contains parent:" + componentConfig.getParentUUID() + " component: "
								+ componentConfig.getComponentUUID());
					} else {
						System.out.println("Contains parent not:" + componentConfig.getParentUUID() + " component: "
								+ componentConfig.getComponentUUID());
					}
				}
				getLeftArtifact().setComponent(true);
			}

			return getLeftArtifact();
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