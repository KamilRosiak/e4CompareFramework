package de.tu_bs.cs.isf.e4cf.extractive_mple.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.NodeConfigurationUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.preferences.PlatformPreferences;

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
	public CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("MPLE"));
	
	public void insertVariants(List<Node> variants) {
		variants.forEach(variant -> {
			insertVariant(variant);
		});
	}

	public void insertVariant(Node variant) {

		if (model == null) {
			initializePlatform(variant);
			return;
		}

		if (variant.getNodeType().equals(model.getNodeType())) {
			// refactor components
			refactorComponents(variant);

			NodeComparison comparison = compareEngine.compare(model, variant);
			model = comparison.mergeArtifacts();
			
			
			
			
		} else {
			System.out.println("root node has other type");
			
		}
	}

	private void refactorComponents(Node node) {
		// get candidate nodes for the refactoring step
		List<Node> candidatNodes = node.getNodesOfType(PlatformPreferences.GRANULARITY_LEVEL.toString());
		//TODO: connect with the Ui 
	
		List<NodeComparison> comparisons = new ArrayList<NodeComparison>();
		
		//compare the upper triangle and create a similarity matrix
		for(int i= 0; i < candidatNodes.size();i++) {
			for(int j= 0; j < candidatNodes.size();i++) {
				comparisons.add(compareEngine.compare(candidatNodes.get(i), candidatNodes.get(j)));
			}
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
