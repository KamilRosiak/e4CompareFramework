package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.StatsLogger;

public class ExtractionHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, ExtractionPipeline pipeline) {
		
		Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());
		pipeline.pipe(tree);
		services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, tree);

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1;
	}
}
