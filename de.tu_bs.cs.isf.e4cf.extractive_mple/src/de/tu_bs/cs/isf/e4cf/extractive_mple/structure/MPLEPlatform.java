package de.tu_bs.cs.isf.e4cf.extractive_mple.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang.SerializationUtils;
import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.NodeConfigurationUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.extractive_mple.extensions.preferences.PlatformPreferences;

/**
 * This class represents the Platform for the multi-product-line engineering
 * 
 * @author Kamil Rosiak
 *
 */
public class MPLEPlatform implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7052592590274822282L;
	public String name;
	public List<Configuration> configurations = new ArrayList<Configuration>();
	public Node model;
	public Matcher matcher = new SortingMatcher();
	public CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(matcher, new MetricImpl("MPLE"));

	public void insertVariants(List<Node> variants) {
		variants.forEach(variant -> {
			insertVariant(variant);
		});
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
			refactorComponents(variant);
			NodeComparison comparison = compareEngine.compare(model, variant);
			comparison.updateSimilarity();
			matcher.calculateMatching(comparison);
			comparison.updateSimilarity();
			model = comparison.mergeArtifacts();
			// after merging all artifacts uuids are propagated to the right variant
			configurations.add(NodeConfigurationUtil.generateConfiguration(comparison.getRightArtifact()));
		} else {
			System.out.println("root node has other type");
		}
	}

	private void refactorComponents(Node node) {
		// get candidate nodes for the refactoring step
		List<Node> candidatNodes = node.getNodesOfType(PlatformPreferences.GRANULARITY_LEVEL.toString());
		System.out.println(candidatNodes.size());
		// TODO: connect with the Ui

		List<NodeComparison> comparisons = new ArrayList<NodeComparison>();

		// compare the upper triangle and create a similarity matrix
		for (int i = 0; i < candidatNodes.size(); i++) {
			for (int j = i; j < candidatNodes.size(); j++) {
				NodeComparison comparison = compareEngine.compare(candidatNodes.get(i), candidatNodes.get(j));
				comparison.updateSimilarity();
				matcher.calculateMatching(comparison);
				comparison.updateSimilarity();
				comparisons.add(comparison);
			}
		}
		// collect similar artifacts
		Iterator<NodeComparison> compariosnIterator = comparisons.iterator();
		while (compariosnIterator.hasNext()) {
			NodeComparison nodeComparison = (NodeComparison) compariosnIterator.next();
			if (nodeComparison.getSimilarity() < PlatformPreferences.COMPONENT_THRESHOLD
					&& !nodeComparison.getLeftArtifact().equals(nodeComparison.getRightArtifact())) {
				compariosnIterator.remove();
			}
		}
		comparisons.forEach(comparison -> {
			System.out.println(getAttributeValueFromNode(comparison.getLeftArtifact(), "Name") + " "
					+ getAttributeValueFromNode(comparison.getRightArtifact(), "Name") + " sim:"
					+ comparison.getSimilarity());
		});
	}

	private String getAttributeValueFromNode(Node n, String key) {
		try {
			return " " + n.getAttributeForKey(key).getAttributeValues().stream().map(v -> v.getValue().toString() + " ")
					.reduce("", String::concat);
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	/**
	 * Sets the first variant as root variant which serves as a starting point
	 */
	private void initializePlatform(Node variant) {
		model = variant;
		List<Configuration> configList = new ArrayList<Configuration>();
		configList.add(NodeConfigurationUtil.generateConfiguration(variant));
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
