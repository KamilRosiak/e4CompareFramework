package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.StatsLogger;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;

public class SynchronizationHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, SynchronizationPipeline pipeline) {

		
		Tree tree1 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0));
		Tree tree2 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(1));

		CloneModel cloneModel = pipeline.pipe(tree1, tree2);

		for (int i = 2; i < services.rcpSelectionService.getCurrentSelectionsFromExplorer().size(); i++) {
			Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(i));
			cloneModel = pipeline.pipe(cloneModel, tree);
		}

		if (cloneModel != null) {
			services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, cloneModel.getTree());
		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() >= 2;
	}
}
