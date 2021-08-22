package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ComponentImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;

@Singleton
@Creatable
public class ComponentExtractor {

	public List<Component> extractComponents(Map<ComponentLayer, List<Set<Node>>> layerToClusters) {

		List<Component> components = new ArrayList<Component>();
		for (Entry<ComponentLayer, List<Set<Node>>> entry : layerToClusters.entrySet()) {
			components.addAll(extractComponents(entry.getValue(), entry.getKey().getLayer()));
		}
		return components;

	}

	public void rebuildComponents(List<Component> oldComponents, Map<String, List<Set<Node>>> layerToClusters) {

		Map<String, Set<Component>> componentsByGranularities = new HashMap<String, Set<Component>>();
		for (Component component : oldComponents) {
			if (!componentsByGranularities.containsKey(component.getLayer())) {
				componentsByGranularities.put(component.getLayer(), new HashSet<Component>());
			}
			componentsByGranularities.get(component.getLayer()).add(component);
		}

		for (Entry<String, List<Set<Node>>> entry : layerToClusters.entrySet()) {
			Set<Component> currentComponents = componentsByGranularities.get(entry.getKey());

			for (Set<Node> cluster : entry.getValue()) {
				Component newComponent = new ComponentImpl();
				for (Node clusterInstance : cluster) {
					Configuration configuration = new ConfigurationImpl();
					configuration.addChild(clusterInstance);
					newComponent.addChildWithParent(configuration);
				}
			}

		}
		//replace component with new component, that has selected configuration

		// TODO

	}

	private List<Component> extractComponents(List<Set<Node>> clusters, String layer) {

		List<Component> components = new LinkedList<Component>();

		// filter clusters
		List<Set<Node>> filteredClusters = new ArrayList<Set<Node>>();
		for (Set<Node> cluster : clusters) {
			if (cluster.size() != 1) {
				filteredClusters.add(cluster);
			}

		}

		for (Set<Node> cluster : filteredClusters) {
			Component component = new ComponentImpl();
			component.setLayer(layer);
			component.setNodeComponentRelation(new HashMap<Node, Map<Integer, Configuration>>());

			for (Node clusterInstance1 : cluster) {

				Configuration configuration = new ConfigurationImpl();
				configuration.addChild(clusterInstance1);
				component.addChildWithParent(configuration);

				Node cloneParent = clusterInstance1.getParent();
				int position = cloneParent.getChildren().indexOf(clusterInstance1);

				if (!component.getNodeComponentRelation().containsKey(cloneParent)) {
					component.getNodeComponentRelation().put(cloneParent, new HashMap<Integer, Configuration>());
				}
				component.getNodeComponentRelation().get(cloneParent).put(position, configuration);

				// replace clone with component
				int index = cloneParent.getChildren().indexOf(clusterInstance1);
				cloneParent.getChildren().remove(index);
				cloneParent.getChildren().add(index, component);

			}

			components.add(component);
		}
		return components;
	}

}
