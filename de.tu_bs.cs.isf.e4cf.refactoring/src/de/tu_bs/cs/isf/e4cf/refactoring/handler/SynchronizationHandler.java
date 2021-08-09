package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.List;

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
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ExtractionEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.SynchronizationEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ExtractionResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationResult;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

public class SynchronizationHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager,
			ComponentLayerViewController componentLayerViewController, ExtractionEngine extractionEngine,
			SynchronizationEngine synchronizationEngine) {

		
		
		Tree tree1 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0));
		Tree tree2 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(1));

		List<ComponentLayer> componentLayers = SynchronizationUtil
				.getComponentLayers(tree1.getRoot().getAllNodeTypes(), tree2.getRoot().getAllNodeTypes());

		componentLayerViewController.showView(componentLayers);
		if (componentLayerViewController.isResult()) {
			ExtractionResult extractionResult1 = extractionEngine.extractComponents(tree1, componentLayers);
			ExtractionResult extractionResult2 = extractionEngine.extractComponents(tree2, componentLayers);

			SynchronizationResult synchronizationResult = synchronizationEngine.synchronize(extractionResult1,
					extractionResult2);

			if (synchronizationResult != null) {
				Node root = new NodeImpl();
				Tree baseTree = new TreeImpl("", root);

				for (Tree tree : synchronizationResult.getTrees()) {
					root.addChild(tree.getRoot());

				}

				extractionEngine.analyzeComponents(synchronizationResult);
				services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, baseTree);
			}

		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() >= 2;
	}
}
