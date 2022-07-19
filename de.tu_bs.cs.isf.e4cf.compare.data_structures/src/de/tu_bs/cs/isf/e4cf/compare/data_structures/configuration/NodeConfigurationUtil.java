package de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration;

import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class NodeConfigurationUtil {

	/**
	 * Generates the configuration of the given node
	 */
	public static Configuration generateConfiguration(Node node, String name) {
		Configuration config = new ConfigurationImpl(name);
		if (!node.isComponent()) {
			addUUIDsRecurivly(node, config);
		}
		return config;
	}

	private static void addUUIDsRecurivly(Node node, Configuration config) {
		if (!node.isComponent()) {
			config.addUUID(node.getUUID());
			node.getAttributes().forEach(attr -> {
				config.addUUID(attr.getUuid());
				attr.getAttributeValues().forEach(value -> {
					config.addUUID(value.getUUID());
				});
			});
			node.getChildren().forEach(e -> {
				addUUIDsRecurivly(e, config);
			});
		}
	}

	/**
	 * Returns a node with the given UUID if available else null
	 */
	public static Node getNodeWithUUID(Node model, UUID nodeID) {
		if (model.getUUID().equals(nodeID)) {
			return model;
		} else {
			for (Node node : model.getChildren()) {
				return getNodeWithUUID(node, nodeID);
			}
			return null;
		}
	}

	public static ComponentConfiguration createComponentConfiguration(Node componentNode, UUID uuid) {
		ComponentConfiguration componentConfig = new ComponentConfiguration();
		componentConfig.parentUUID = uuid;
		componentConfig.componentUUID = componentNode.getUUID();
		Configuration config = new ConfigurationImpl("componentConfig");
		componentNode.getChildren().forEach(childNode -> {
			addUUIDsRecursivly(childNode, config);
		});
		componentConfig.configuration = config;
		return componentConfig;
	}

	/**
	 * Add UUIDs of nodes, attributes and attribute values recursively to the
	 * configuration
	 * 
	 * @param childNode
	 * @param config
	 */
	public static void addUUIDsRecursivly(Node childNode, Configuration config) {
		if (!childNode.isComponent()) {
			config.addUUID(childNode.getUUID());
			childNode.getAttributes().forEach(attr -> {
				config.addUUID(attr.getUuid());
				attr.getAttributeValues().forEach(attrValue -> {
					config.addUUID(attrValue.getUUID());
				});
			});

			childNode.getChildren().forEach(e -> {
				addUUIDsRecursivly(e, config);
			});
		} else {
			System.out.println("child is component");
		}
	}

}
