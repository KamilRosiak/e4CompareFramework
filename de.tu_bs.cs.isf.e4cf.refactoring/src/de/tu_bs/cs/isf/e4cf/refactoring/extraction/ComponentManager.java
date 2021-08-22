package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.sun.jndi.toolkit.ctx.ComponentContext;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSet;

@Singleton
@Creatable
public class ComponentManager {

	public void removeConfigurations(List<ComponentComparison> componentComparisons,
			Map<Component, MultiSet> multiSets) {
		for (ComponentComparison componentComparison : componentComparisons) {
			Component component = componentComparison.getComponent1();
			MultiSet multiSet = multiSets.get(component);

			for (Configuration configuration : componentComparison.getRemovedConfigurations()) {

				Map<Node, Map<Integer, Configuration>> nodeComponentRelation = component.getNodeComponentRelation();
				for (Entry<Node, Map<Integer, Configuration>> nodeComponentRelationEntry : nodeComponentRelation
						.entrySet()) {

					Node node = nodeComponentRelationEntry.getKey();
					for (Entry<Integer, Configuration> configurationPositionEntry : nodeComponentRelationEntry
							.getValue().entrySet()) {

						if (configurationPositionEntry.getValue() == configuration) {
							nodeComponentRelation.get(node).put(configurationPositionEntry.getKey(),
									component.getConfigurations().get(0));
						}

					}

				}

				component.getChildren().remove(configuration);
				multiSet.remove(configuration);
			}
		}
	}

	public void addConfigurations(List<ComponentComparison> componentComparisons, Map<Component, MultiSet> multiSets) {

		for (ComponentComparison componentComparison : componentComparisons) {
			Component component = componentComparison.getComponent1();
			MultiSet multiSet = multiSets.get(component);
			for (Configuration configuration : componentComparison.getAddedConfigurations()) {

				Component oldComponent = (Component) configuration.getParent();
				Map<Node, Map<Integer, Configuration>> oldNodeComponentRelation = oldComponent
						.getNodeComponentRelation();

				Map<Node, Map<Integer, Configuration>> newNodeComponentRelation = component.getNodeComponentRelation();

				for (Entry<Node, Map<Integer, Configuration>> oldNodeComponentRelationEntry : oldNodeComponentRelation
						.entrySet()) {

					Node node = oldNodeComponentRelationEntry.getKey();

					for (Entry<Integer, Configuration> configurationPositionEntry : oldNodeComponentRelationEntry
							.getValue().entrySet()) {

						if (configurationPositionEntry.getValue() == configuration) {

							if (!newNodeComponentRelation.containsKey(node)) {
								newNodeComponentRelation.put(node, new HashMap<Integer, Configuration>());
							}
							newNodeComponentRelation.get(node).put(configurationPositionEntry.getKey(),
									configurationPositionEntry.getValue());

						}

					}

				}

				component.addChildWithParent(configuration);
				multiSet.add(configuration);

			}

		}

	}

}
