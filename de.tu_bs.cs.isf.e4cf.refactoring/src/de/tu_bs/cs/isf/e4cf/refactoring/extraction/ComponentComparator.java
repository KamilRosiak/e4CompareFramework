package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentComparison;

@Singleton
@Creatable
public class ComponentComparator {

	public final float COMPONENT_THRESHOLD = 0.9f;

	private CompareEngineHierarchical compareEngine;
	private SortingMatcher sortingMatcher;

	public ComponentComparator() {
		sortingMatcher = new SortingMatcher();
		compareEngine = new CompareEngineHierarchical(sortingMatcher, new MetricImpl("test"));

	}

	public List<ComponentComparison> compareComponents(List<Component> components1, List<Component> components2) {

		List<ComponentComparison> componentComparisonResults = new ArrayList<ComponentComparison>();
		List<Comparison<Node>> componentComparisons = matchComponents(components1, components2);

		for (Comparison<Node> componentComparison : componentComparisons) {

			if (componentComparison.getLeftArtifact() != null && componentComparison.getRightArtifact() != null
					&& componentComparison.getSimilarity() > COMPONENT_THRESHOLD) {
				// component match
				Component component1 = (Component) componentComparison.getLeftArtifact();
				Component component2 = (Component) componentComparison.getRightArtifact();

				List<Configuration> configurationsToAdd = new ArrayList<Configuration>();
				Map<Configuration, Configuration> configurationsToUpdate = new HashMap<Configuration, Configuration>();
				List<Configuration> configurationsToDelete = new ArrayList<Configuration>();

				List<Comparison<Node>> configurationComparisons = matchConfigurations(component1.getConfigurations(),
						component2.getConfigurations());

				for (Comparison<Node> configurationComparison : configurationComparisons) {

					if (configurationComparison.getLeftArtifact() != null
							&& configurationComparison.getRightArtifact() != null) {
						// configuration match: update configuration

						Configuration configuration1 = (Configuration) configurationComparison.getLeftArtifact();
						Configuration configuration2 = (Configuration) configurationComparison.getRightArtifact();

						configurationsToUpdate.put(configuration1, configuration2);

					} else if (configurationComparison.getLeftArtifact() != null
							&& configurationComparison.getRightArtifact() == null) {
						// no configuration match on right side: delete saved configuration
						Configuration configuration1 = (Configuration) configurationComparison.getLeftArtifact();

						configurationsToDelete.add(configuration1);

					} else {
						// no configuration match on left side: add configuration
						Configuration configuration2 = (Configuration) configurationComparison.getRightArtifact();

						configurationsToAdd.add(configuration2);

					}

				}
				ComponentComparison componentComparisonResult = new ComponentComparison(component1, component2,
						configurationsToUpdate, configurationsToAdd, configurationsToDelete);
				componentComparisonResults.add(componentComparisonResult);

			} else if (componentComparison.getLeftArtifact() == null) {
				Component component2 = (Component) componentComparison.getRightArtifact();

				ComponentComparison componentComparisonResult = new ComponentComparison(null, component2);
				componentComparisonResults.add(componentComparisonResult);

			} else if (componentComparison.getRightArtifact() == null) {

				Component component1 = (Component) componentComparison.getLeftArtifact();

				ComponentComparison componentComparisonResult = new ComponentComparison(component1, null);
				componentComparisonResults.add(componentComparisonResult);
			}

		}

		return componentComparisonResults;
	}

	private List<Comparison<Node>> matchComponents(List<Component> components1, List<Component> components2) {

		List<Comparison<Node>> componentComparisons = new ArrayList<Comparison<Node>>();

		for (Component component1 : components1) {
			for (Component component2 : components2) {
				componentComparisons.add(compareEngine.compare(component1, component2));
			}
		}

		sortingMatcher.calculateMatching(componentComparisons);

		return componentComparisons;
	}

	private List<Comparison<Node>> matchConfigurations(List<Configuration> configurations1,
			List<Configuration> configurations2) {

		List<Comparison<Node>> configurationsComparisons = new ArrayList<Comparison<Node>>();

		for (Configuration configuration1 : configurations1) {
			for (Configuration configuration2 : configurations2) {
				configurationsComparisons.add(compareEngine.compare(configuration1, configuration2));
			}
		}

		sortingMatcher.calculateMatching(configurationsComparisons);

		return configurationsComparisons;
	}

}
