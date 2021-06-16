package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.List;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.RefactoringViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.RefactoringEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringResult;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

public class RefactoringHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, RefactoringEngine engine,
			RefactoringViewController refactoringViewController) {

		Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());

		List<RefactoringLayer> refactoringLayers = SynchronizationUtil.getRefactoringLayers();

		refactoringViewController.showView(refactoringLayers);
		if (refactoringViewController.isResult()) {
			RefactoringResult result = engine.refactor(tree, refactoringLayers);

			services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, tree);
		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1;
	}
}
