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
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ComponentExtractor;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.GranularityManager;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSet;

public class ExtractionHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, GranularityManager granularityManager,
			ClusterEngine clusterEngine, ComponentExtractor componentExtractor) {

		Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());

		Map<ComponentLayer, List<Node>> layerToNodes = granularityManager.extractNodesOfCertainGranularities(tree);
		if (layerToNodes != null) {

			CloneModel cloneModel = componentExtractor
					.extractComponents(clusterEngine.detectClusters(layerToNodes));
		

			services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, tree);
		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1;
	}
}
