package de.tu_bs.cs.isf.e4cf.extractive_mple.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.SerializationUtils;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ComponentConfiguration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.NodeConfigurationUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.MergeContext;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.extractive_mple.extensions.preferences.PlatformPreferences;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.extraction.ClusterEngine;

/**
 * This class represents the Platform for the multi-product-line engineering
 * 
 * @author Kamil Rosiak
 *
 */
public class MPLPlatform implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7052592590274822282L;
	public String name;
	public List<Configuration> configurations = new ArrayList<Configuration>();
	public Node model;
	public Matcher matcher = new SortingMatcher();
	public CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(matcher, new MetricImpl("MPLE"));
	public Configuration currrentConfiguration;

	int configCount = 0;
	int componentCount = 0;

	public void insertVariants(List<Node> variants) {
		variants.forEach(variant -> {
			insertVariant(variant);
		});
	}

	public Configuration getNextConfig(Node node) {
		configCount++;
		ConfigurationImpl config = (ConfigurationImpl) NodeConfigurationUtil.generateConfiguration(node,
				"Variant Config " + configCount);
		return config;
	}

	/**
	 * Inserting a variant into the platform
	 */
	public void insertVariant(Node variant) {
		if (model == null) {
			initializePlatform(variant);
			return;
		}

		if (variant.getNodeType().equals(model.getNodeType())) {
			// refactor components
			List<ComponentConfiguration> componentConfigurations = refactorComponents(variant);
			NodeComparison comparison = compareEngine.compare(model, variant);
			MergeContext mergeContext = new MergeContext();
			model = comparison.mergeArtifacts(mergeContext, configurations, componentConfigurations);

			// Propagated changed uuid to component configuraitons
			componentConfigurations.forEach(componentConfig -> {
				if (mergeContext.changedUUIDs.containsKey(componentConfig.componentUUID)) {
					componentConfig.componentUUID = mergeContext.changedUUIDs.get(componentConfig.componentUUID);
				}
				if (mergeContext.changedUUIDs.containsKey(componentConfig.parentUUID)) {
					componentConfig.parentUUID = mergeContext.changedUUIDs.get(componentConfig.parentUUID);
				}

				List<UUID> removeList = new ArrayList<UUID>();
				List<UUID> addList = new ArrayList<UUID>();
				componentConfig.configuration.getUUIDs().forEach(e -> {
					if (mergeContext.changedUUIDs.containsKey(e)) {
						removeList.add(e);
						addList.add(mergeContext.changedUUIDs.get(e));
					}
				});

				componentConfig.configuration.getUUIDs().removeAll(removeList);
				componentConfig.configuration.getUUIDs().addAll(addList);
			});

			// after merging all artifacts uuids are propagated to the right variants and we
			// can generate the configuration
			Configuration variantConfig = getNextConfig(comparison.getRightArtifact());
			variantConfig.getComponentConfigurations().addAll(componentConfigurations);
			configurations.add(variantConfig);
			model.sortChildNodes();
		} else {
			System.out.println("root node has other type");
		}
	}

	private List<ComponentConfiguration> refactorComponents(Node node) {
		List<ComponentConfiguration> componentConfigs = new ArrayList<ComponentConfiguration>();
		// Get all nodes of the selected type

		List<Node> candidatNodes = node.getNodesOfType(PlatformPreferences.GRANULARITY_LEVEL.toString());
		Iterator<Node> candiateIterator = candidatNodes.iterator();
		/**
		 * while (candiateIterator.hasNext()) { Node node2 = (Node)
		 * candiateIterator.next(); if (node2.getAmountOfNodes(0) < 5) {
		 * candiateIterator.remove(); } }
		 **/
		// Initialize the cluster engine and run the process output ist a list of sets
		// of nodes. every set represents a clone cluster that have to be merged.
		ClusterEngine clusterEngine = new ClusterEngine();
		ClusterEngine.startProcess();
		List<Set<Node>> nodeCluster = clusterEngine.detectClusters(candidatNodes,
				clusterEngine.buildDistanceString(candidatNodes));

		// filter all sets which only contain one element
		Iterator<Set<Node>> iterator = nodeCluster.iterator();
		while (iterator.hasNext()) {
			Set<Node> set = iterator.next();
			if (set.size() <= 1) {
				iterator.remove();
			}
		}

		for (Set<Node> clusterSet : nodeCluster) {

			Node mergeTarget = clusterSet.iterator().next();
			clusterSet.remove(mergeTarget);

			// create configuration of the merge target component node.
			ComponentConfiguration firstConfig = NodeConfigurationUtil.createComponentConfiguration(mergeTarget,
					mergeTarget.getParent().getUUID());
			componentConfigs.add(firstConfig);

			for (Node clusterNode : clusterSet) {
				mergeTarget.setComponent(true);
				clusterNode.setComponent(true);

				NodeComparison nodeComparison = compareEngine.compare(mergeTarget, clusterNode);
				nodeComparison.updateSimilarity();
				MergeContext context = new MergeContext();
				nodeComparison.mergeArtifacts(context, configurations, new ArrayList<ComponentConfiguration>());

				clusterNode.getParent().getChildren().remove(clusterNode);

				if (!clusterNode.getParent().getChildren().contains(mergeTarget))
					clusterNode.getParent().getChildren().add(mergeTarget);
				componentConfigs.add(NodeConfigurationUtil.createComponentConfiguration(clusterNode,
						clusterNode.getParent().getUUID()));
			}

		}
		return componentConfigs;

	}

	/**
	 * Sets the first variant as root variant which serves as a starting point
	 */
	private void initializePlatform(Node variant) {
		List<ComponentConfiguration> componentConfigs = refactorComponents(variant);
		model = variant;
		configurations = new ArrayList<Configuration>();
		ConfigurationImpl config = (ConfigurationImpl) getNextConfig(variant);
		config.getComponentConfigurations().addAll(componentConfigs);
		configurations.add(config);
	}

	public void insertComponent(Node component) {

	}

	public Node getVariant(Configuration config) {
		Node rootNode = NodeConfigurationUtil.getNodeWithUUID(model, config.getRootUUID());
		Node variant = (Node) SerializationUtils.clone(rootNode);

		Iterator<Attribute> iterator = variant.getAttributes().iterator();

		iterator.forEachRemaining(attribute -> {
			if (!config.getUUIDs().contains(attribute.getUuid())) {
				iterator.remove();
			}
		});
		return variant;
	}

	public void removeConfiguration(Configuration config) {
		configurations.remove(config);
	}

}
