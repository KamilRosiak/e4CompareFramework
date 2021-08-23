package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.List;
import java.util.Map;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
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
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ConfigurationComparator;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.GranularityManager;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.SynchronizationManager;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ConfigurationComparison;

public class SynchronizationHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager,
			ComponentLayerViewController componentLayerViewController, GranularityManager granularityManager,
			ClusterEngine clusterEngine, ComponentExtractor componentExtractor,
			ConfigurationComparator configurationComparator, ComponentComparator componentComparator,
			ActionManager actionManager, SynchronizationManager synchronizationManager) {

		Tree tree1 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0));
		Tree tree2 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(1));

		Map<Tree, Map<ComponentLayer, List<Node>>> treeToLayers = granularityManager
				.extractNodesOfCertainGranularities(tree1, tree2);

		if (treeToLayers != null) {

			CloneModel cloneModel1 = componentExtractor
					.extractComponents(clusterEngine.detectClusters(treeToLayers.get(tree1)));
			CloneModel cloneModel2 = componentExtractor
					.extractComponents(clusterEngine.detectClusters(treeToLayers.get(tree2)));

			List<ComponentComparison> componentComparisons = componentComparator
					.compareComponents(cloneModel1.getComponents(), cloneModel2.getComponents());
			List<ConfigurationComparison> configurationComparisons = configurationComparator
					.compareConfigurations(componentComparisons);

			cloneModel1.removeConfigurations(componentComparisons);

			if (actionManager.showActionView(configurationComparisons)) {

				actionManager.applyActions(configurationComparisons, cloneModel1.getMultiSets());

				Map<ActionScope, List<ActionScope>> synchronizationScopes = synchronizationManager
						.determineSynchronizations(configurationComparisons, cloneModel1.getMultiSets());

				if (synchronizationManager.showSynchronizationView(configurationComparisons, synchronizationScopes)) {
					synchronizationManager.applySynchronizations(configurationComparisons, synchronizationScopes,
							cloneModel1.getMultiSets());

					cloneModel1.addConfigurations(componentComparisons, cloneModel2);
					cloneModel1.replaceComponents(componentComparisons, cloneModel2);
					cloneModel1.addComponents(componentComparisons, cloneModel2);
					cloneModel1.removeComponents(componentComparisons);

					Node mergedRoot = new NodeImpl("");
					Tree mergedTree = new TreeImpl("", mergedRoot);
					mergedRoot.addChild(tree1.getRoot());
					mergedRoot.addChild(tree2.getRoot());

					clusterEngine.refineComponents(cloneModel1);
					// TODO
					// refineComponents

					services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, mergedTree);
				}

			}

		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() >= 2;
	}
}
