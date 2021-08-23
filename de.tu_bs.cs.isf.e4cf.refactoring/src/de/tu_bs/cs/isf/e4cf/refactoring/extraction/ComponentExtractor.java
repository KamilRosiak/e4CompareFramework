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
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSet;

@Singleton
@Creatable
public class ComponentExtractor {

	public CloneModel extractComponents(Map<ComponentLayer, List<Set<Node>>> layerToClusters) {

		CloneModel cloneModel = new CloneModel();

		for (Entry<ComponentLayer, List<Set<Node>>> entry : layerToClusters.entrySet()) {
			extractComponents(entry.getValue(), entry.getKey().getLayer(), cloneModel);
		}
		return cloneModel;

	}

	private CloneModel extractComponents(List<Set<Node>> clusters, String layer, CloneModel cloneModel) {

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
			cloneModel.getComponentInstances().put(component, new HashSet<Component>());

			for (Node clusterInstance1 : cluster) {
				Component componentInstance = new ComponentImpl();
				componentInstance.setLayer(layer);

				for (Node clusterInstance2 : cluster) {
					Configuration configuration = new ConfigurationImpl();
					configuration.addChild(clusterInstance2);
					componentInstance.addChildWithParent(configuration);

					if (clusterInstance1 == clusterInstance2) {
						componentInstance.setSelectedConfiguration(configuration);
					}

				}

				Configuration configuration = new ConfigurationImpl();
				configuration.addChild(clusterInstance1);
				component.addChildWithParent(configuration);

				Node cloneParent = clusterInstance1.getParent();

				// replace clone with component
				int index = cloneParent.getChildren().indexOf(clusterInstance1);
				cloneParent.getChildren().remove(index);
				cloneParent.getChildren().add(index, componentInstance);
				componentInstance.setParent(cloneParent);

				cloneModel.getComponentInstances().get(component).add(componentInstance);

			}

		}
		Map<Component, MultiSet> multiSets = MultiSet.generate(cloneModel.getComponentInstances().keySet());
		cloneModel.setMultiSets(multiSets);

		return cloneModel;
	}

}
