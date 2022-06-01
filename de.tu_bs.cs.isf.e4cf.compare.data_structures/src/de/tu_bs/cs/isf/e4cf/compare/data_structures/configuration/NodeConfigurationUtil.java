package de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class NodeConfigurationUtil {

	/**
	 * Generates the configuration of the given node
	 */
	public static Configuration generateConfiguration(Node node) {
		Configuration config = new ConfigurationImpl("");
		addUUIDsToConfiguration(config, node);
		return config;
	}

	/**
	 * Iterates recursively over all children and adds all UUIDs to the
	 * configuration
	 */
	public static void addUUIDsToConfiguration(Configuration config, Node node) {
		config.addUUID(node.getUUID());
		node.getAttributes().forEach(attribute -> {
			config.addUUID(attribute.getUuid());
			attribute.getAttributeValues().forEach(value -> {
				config.addUUID(value.getUUID());
			});
		});
		node.getChildren().forEach(childNode -> {
			addUUIDsToConfiguration(config, childNode);
		});
	}
	


}
