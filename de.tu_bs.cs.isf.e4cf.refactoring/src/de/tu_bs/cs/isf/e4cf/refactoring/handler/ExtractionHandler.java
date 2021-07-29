package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ComponentLayerViewController;

import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ExtractionEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ExtractionResult;

import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

public class ExtractionHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, ExtractionEngine engine,
			ComponentLayerViewController componentLayerViewController) {

		Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());

		Set<String> nodeTypes = new HashSet<String>();
		nodeTypes.addAll(tree.getRoot().getAllNodeTypes()); 
		
		List<ComponentLayer> componentLayers = SynchronizationUtil
				.getComponentLayers(nodeTypes);

		componentLayerViewController.showView(componentLayers);
		if (componentLayerViewController.isResult()) {
			ExtractionResult result = engine.extractComponents(tree, componentLayers);

			services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, tree);
		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1;
	}
}
