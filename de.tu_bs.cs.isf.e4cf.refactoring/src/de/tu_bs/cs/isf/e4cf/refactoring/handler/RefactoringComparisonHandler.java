package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.RefactoringViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.RefactoringEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringResult;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

public class RefactoringComparisonHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, RefactoringEngine refactoringEngine,
			RefactoringViewController refactoringViewController) {

		CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
				new MetricImpl("test"));

		Tree tree1 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0));
		Tree tree2 = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(1));

		List<RefactoringLayer> refactoringLayers = SynchronizationUtil.getRefactoringLayers();

		refactoringViewController.showView(refactoringLayers);
		if (refactoringViewController.isResult()) {
			RefactoringResult result1 = refactoringEngine.refactor(tree1, refactoringLayers);
			RefactoringResult result2 = refactoringEngine.refactor(tree2, refactoringLayers);

			List<Comparison<Node>> comparisons = new LinkedList<Comparison<Node>>();

			for (Component component1 : result1.getComponents()) {
				for (Component component2 : result2.getComponents()) {
					comparisons.add(compareEngine.compare(component1, component2));
				}
			}
			
			Node root = new NodeImpl();
			Tree tree = new TreeImpl("", root);

			for (Comparison<Node> comparison : comparisons) {
				if (comparison.getSimilarity() > 0.8) {
					Node mergedComponent = compareEngine.compareMerge(comparison.getLeftArtifact(), comparison.getRightArtifact());
					root.addChild(mergedComponent);
				}
			}

			services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, tree);
			services.workspaceFileSystem.initializeFileTree("");
		}

		

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 2;
	}

}
