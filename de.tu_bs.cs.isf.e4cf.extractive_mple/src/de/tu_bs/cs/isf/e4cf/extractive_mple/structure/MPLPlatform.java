package de.tu_bs.cs.isf.e4cf.extractive_mple.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.SerializationUtils;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.CloneConfiguration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.NodeConfigurationUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.TreeUtil;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.extractive_mple.extensions.preferences.PlatformPreferences;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.extraction.ClusterEngine;

/**
 * This class represents the Platform for the multi-product-line engineering
 * 
 * @author Kamil Rosiak
 *
 */
public class MPLPlatform implements Serializable {
	private static final long serialVersionUID = 7052592590274822282L;
	public String name;
	public List<Configuration> configurations = new ArrayList<Configuration>();
	public Node model;
	public Matcher matcher = new SortingMatcher();
	public CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(matcher, new MetricImpl("MPLE"));
	public Configuration currrentConfiguration;
	private FeatureDiagram featureDiagram = null;
	private boolean isMulti = true;
	int configCount = 0;
	int componentCount = 0;

	public MPLPlatform() {

	}

	public MPLPlatform(CompareEngineHierarchical compareEngine, boolean isMulti) {
		this.isMulti = isMulti;
		this.compareEngine = compareEngine;
	}

	public Optional<FeatureDiagram> getFeatureModel() {
		if (this.featureDiagram != null) {
			return Optional.of(this.featureDiagram);
		} else {
			return Optional.empty();
		}
	}

	public void setFeatureModel(FeatureDiagram diagram) {
		this.featureDiagram = diagram;
	}

	public void insertVariants(List<Tree> variants) {
		try {
			variants.forEach(variant -> {
				insertVariant(variant);
				resetConfigurations();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void resetConfigurations() {
		configurations.forEach(config -> {
			config.getCloneConfigurations().forEach(cloneConfig -> {
				cloneConfig.isMerged = false;
			});
		});
	}

	public Configuration getNextConfig(Tree node) {
		ConfigurationImpl config = (ConfigurationImpl) NodeConfigurationUtil.generateConfiguration(node.getRoot(),
				node.getTreeName());
		return config;
	}

	/**
	 * Inserting a variant into the platform
	 */
	public void insertVariant(Tree variant) {
		try {
			if (model == null) {
				initializePlatform(variant);
				return;
			}

			if (variant.getRoot().getNodeType().equals(model.getNodeType())) {
				/**
				 * Intra clone refactoring detection clone artifacts within the variant and the
				 * creation of clone configurations
				 */
				List<CloneConfiguration> cloneConfigurations = new ArrayList<CloneConfiguration>();
				List<CloneConfiguration> fixedCloneConfigs = new ArrayList<CloneConfiguration>();
				if (isMulti) {
					cloneConfigurations = refactorComponents(variant.getRoot());
				}

				/**
				 * Inter clone refactoring detection clone artifacts between variants and the
				 * creation of clone configurations
				 */
				// compare variants with the platform clone model
				NodeComparison comparison = compareEngine.compare(model, variant.getRoot());
				// merge the new variant into the clone model
				model = comparison.mergeArtifacts(configurations, cloneConfigurations, fixedCloneConfigs);
				// generate the variant configuration for the merged variant
				Configuration variantConfig = getNextConfig(variant);
				variantConfig.getCloneConfigurations().addAll(cloneConfigurations);
				variantConfig.getCloneConfigurations().addAll(fixedCloneConfigs);
				configurations.add(variantConfig);
				// model.sortChildNodes();
			} else {
				System.out.println("root node has other type");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<CloneConfiguration> refactorComponents(Node node) {
		List<CloneConfiguration> componentConfigs = new ArrayList<CloneConfiguration>();
		try {
			// Get all nodes of the selected type
			List<Node> candidatNodes = node.getNodesOfType(PlatformPreferences.GRANULARITY_LEVEL.toString());
			candidatNodes = new ArrayList<>();
			Iterator<Node> candiateIterator = candidatNodes.iterator();

			// Only take artifacts with at least 20 nodes into account (Clone size)
			while (candiateIterator.hasNext()) {
				Node node2 = (Node) candiateIterator.next();
				if (node2.getAmountOfNodes(0) < 20) {
					candiateIterator.remove();
				}
			}

			// Initialize the cluster engine and run the process output is a list of sets
			// of nodes. Every set represents a clone cluster that has to be merged.
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
				CloneConfiguration firstConfig = NodeConfigurationUtil.createCloneConfiguration(mergeTarget,
						mergeTarget.getParent().getUUID());
				componentConfigs.add(firstConfig);

				for (Node clusterNode : clusterSet) {
					mergeTarget.setCloned(true);
					clusterNode.setCloned(true);

					NodeComparison nodeComparison = compareEngine.compare(mergeTarget, clusterNode);
					nodeComparison.mergeArtifacts(configurations, new ArrayList<CloneConfiguration>(),
							new ArrayList<CloneConfiguration>());

					clusterNode.getParent().getChildren().remove(clusterNode);

					if (!clusterNode.getParent().getChildren().contains(mergeTarget))
						clusterNode.getParent().getChildren().add(mergeTarget);

					componentConfigs.add(NodeConfigurationUtil.createCloneConfiguration(clusterNode,
							clusterNode.getParent().getUUID()));
				}
			}
		} catch (Exception e) {
			System.out.println("Multi Product Line Extraction not avalable in this version");
		}

		return componentConfigs;
	}

	/**
	 * Sets the first variant as root variant which serves as a starting point
	 */
	private void initializePlatform(Tree tree) {
		try {
			List<CloneConfiguration> componentConfigs = new ArrayList<CloneConfiguration>();
			if(isMulti) {
				componentConfigs = refactorComponents(tree.getRoot());
			}
			model = tree.getRoot();
			configurations = new ArrayList<Configuration>();
			ConfigurationImpl config = (ConfigurationImpl) getNextConfig(tree);
			config.getCloneConfigurations().addAll(componentConfigs);
			configurations.add(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertComponent(Node component) {

	}

	public Node getVariant(Configuration config) {
		Node rootNode = NodeConfigurationUtil.getNodeWithUUID(model, config.getRootUUID());
		Node variant = (Node) SerializationUtils.clone(rootNode);

		Iterator<Attribute> iterator = variant.getAttributes().iterator();

		iterator.forEachRemaining(attribute -> {
			if (!config.getUUIDs().contains(attribute.getUUID())) {
				iterator.remove();
			}
		});
		return variant;
	}

	public void removeConfiguration(Configuration config) {
		configurations.remove(config);
	}

	public Set<Node> getNodesForUUIDs(Set<UUID> uuids) {
		return TreeUtil.getNodesForCondition(model, node -> uuids.contains(node.getUUID()));
	}

}