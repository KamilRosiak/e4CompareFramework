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
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSet;

@Singleton
@Creatable
public class ComponentExtractor {

	public CloneModel extractComponents(Map<Granularity, List<Set<Node>>> layerToClusters) {

		CloneModel cloneModel = new CloneModel();
		for (Entry<Granularity, List<Set<Node>>> entry : layerToClusters.entrySet()) {
			extractComponents(entry.getValue(), entry.getKey().getLayer(), cloneModel);
		}
		return cloneModel;

	}

	private CloneModel extractComponents(List<Set<Node>> clusters, String layer, CloneModel cloneModel) {

		for (Set<Node> cluster : clusters) {

			// create base component
			Component component = new ComponentImpl();
			component.setLayer(layer);
			cloneModel.getComponents().add(component);

			for (Node clusterInstance1 : cluster) {
				// create base configuration
				Configuration configuration = new ConfigurationImpl();
				configuration.addChild(clusterInstance1);
				component.addChildWithParent(configuration);
			}

		}
		// generate multisets of all components
		Map<Component, MultiSet> multiSets = MultiSet.generate(cloneModel.getComponents());
		cloneModel.setMultiSets(multiSets);

		return cloneModel;
	}

}
