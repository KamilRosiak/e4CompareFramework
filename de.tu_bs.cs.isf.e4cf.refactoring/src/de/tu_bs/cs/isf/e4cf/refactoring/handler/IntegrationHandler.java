package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.CloneModel;

public class IntegrationHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, IntegrationPipeline integrationPipeline, ClusterEngine clusterEngine) {

		ClusterEngine.startProcess();

		Tree tree1 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0));
		CloneModel cloneModel1 = integrationPipeline.pipe(tree1);

		if (cloneModel1 != null) {
			for (int i = 1; i < services.rcpSelectionService.getCurrentSelectionsFromExplorer().size(); i++) {
				Tree tree2 = readerManager
						.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(i));
				CloneModel cloneModel2 = integrationPipeline.pipe(tree2, cloneModel1.getGranularities());

				cloneModel1.merge(cloneModel2);
			}

		
			Map<String, Object> event = new HashMap<String, Object>();
			event.put(DSEditorST.TREE, cloneModel1.getTree());
			event.put(DSEditorST.CLONE_MODEL, cloneModel1);
			services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, event);
		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() >= 1;
	}

}
