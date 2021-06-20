package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ActionViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.RefactoringViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.SynchronizationConflictViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.SynchronizationViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.RefactoringEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.SynchronizationEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

public class RefactoringSynchronizationHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, RefactoringEngine refactoringEngine,
			SynchronizationEngine synchronizationEngine, SynchronizationViewController synchronizationViewController,
			ActionViewController actionViewController, RefactoringViewController refactoringViewController,
			SynchronizationConflictViewController synchronizationConflictViewController) {

		Tree first = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0));
		Tree second = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(1));

		List<RefactoringLayer> refactoringLayers = SynchronizationUtil
				.getRefactoringLayers(first.getRoot().getAllNodeTypes(), second.getRoot().getAllNodeTypes());

		boolean skipView = false;

		refactoringViewController.showView(refactoringLayers);
		if (refactoringViewController.isResult()) {
			RefactoringResult firstResult = refactoringEngine.refactor(first, refactoringLayers, false);
			RefactoringResult secondResult = refactoringEngine.refactor(second, refactoringLayers, false);

			Map<Component, List<ActionScope>> componentToActions = synchronizationEngine.analyzeActions(firstResult,
					secondResult);

			skipView = componentToActions.values().stream().allMatch(x -> x.isEmpty());

			if (!skipView) {
				actionViewController.showView(componentToActions);
			}

			if (actionViewController.isResult() || skipView) {

				Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations = synchronizationEngine
						.analyzeSynchronizations(firstResult, secondResult, componentToActions);

				skipView = actionsToSynchronizations.values().stream().allMatch(x -> x.isEmpty());

				if (!skipView) {
					synchronizationViewController.showView(actionsToSynchronizations, componentToActions);
				}

				if (synchronizationViewController.isResult() || skipView) {

					Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts = synchronizationEngine
							.scanForSynchronizationConflicts(componentToActions, actionsToSynchronizations);

					if (conflicts.size() != 0) {
						synchronizationConflictViewController.showView(conflicts, actionsToSynchronizations);

						if (!synchronizationConflictViewController.isResult()) {
							return;
						}

					}

					SynchronizationResult synchronizationResult = synchronizationEngine.synchronize(firstResult,
							secondResult, componentToActions, actionsToSynchronizations, true);

					Node baseRoot = new NodeImpl("");
					Tree baseTree = new TreeImpl("", baseRoot);

					for (Tree tree : synchronizationResult.getTrees()) {
						baseRoot.addChild(tree.getRoot());
					}
					services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, baseTree);

				}
			}
		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() >= 2;
	}
}
