package de.tu_bs.cs.isf.e4cf.extractive_mple.structure;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.SerializationUtils;

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
	public Map<Node, List<Configuration>> componentRepository = new HashMap<Node, List<Configuration>>();
	public Node model;

	public void insertVariant(Node variant) {

	}

	public Node getVariant(Configuration config) {
		Node variant = (Node) SerializationUtils.clone(model);
		
		variant.getAttributes().iterator().forEachRemaining(attribute -> {
			if(config.getUUIDs().contains(attribute.getUuid()));
		});
		
		
		
		
		return variant;
	}

	public void removeConfiguration(Configuration config) {
		componentRepository.forEach((a, b) -> {
			if(b.contains(config)) {
				b.remove(config);
			}
		});
	}

}
