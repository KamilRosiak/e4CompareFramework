package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.List;
import java.util.Map;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ComponentLayerViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ActionManager;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ComponentComparator;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ComponentExtractor;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ComponentManager;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ConfigurationComparator;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.GranularityManager;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.SynchronizationManager;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ConfigurationComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSet;

public class SynchronizationHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager,
			ComponentLayerViewController componentLayerViewController, GranularityManager granularityManager,
			ClusterEngine clusterEngine, ComponentExtractor componentExtractor,
			ConfigurationComparator configurationComparator, ComponentComparator componentComparator,
			ComponentManager componentManager, ActionManager actionManager,
			SynchronizationManager synchronizationManager) {

		Tree tree1 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0));
		Tree tree2 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(1));

		Map<Tree, Map<ComponentLayer, List<Node>>> treeToLayers = granularityManager
				.extractNodesOfCertainGranularities(tree1, tree2);

		if (treeToLayers != null) {

			List<Component> components1 = componentExtractor
					.extractComponents(clusterEngine.detectClusters(treeToLayers.get(tree1)));
			List<Component> components2 = componentExtractor
					.extractComponents(clusterEngine.detectClusters(treeToLayers.get(tree2)));

			Map<Component, MultiSet> multiSets1 = MultiSet.generate(components1);

			List<ComponentComparison> componentComparisons = componentComparator.compareComponents(components1,
					components2);
			List<ConfigurationComparison> configurationComparisons = configurationComparator
					.compareConfigurations(componentComparisons);

			componentManager.removeConfigurations(componentComparisons, multiSets1);

			if (actionManager.showActionView(configurationComparisons)) {

				actionManager.applyActions(configurationComparisons, multiSets1);

				Map<ActionScope, List<ActionScope>> synchronizationScopes = synchronizationManager
						.determineSynchronizations(configurationComparisons, multiSets1);

				if (synchronizationManager.showSynchronizationView(configurationComparisons, synchronizationScopes)) {
					synchronizationManager.applySynchronizations(configurationComparisons, synchronizationScopes,
							multiSets1);

					componentManager.addConfigurations(componentComparisons, multiSets1);

					//TODO
					//refineComponents

					services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, tree1);
				}

			}

		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() >= 2;
	}
}
