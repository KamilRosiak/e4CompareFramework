package de.tu_bs.cs.isf.e4cf.refactoring.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.StatsLogger;

public class ExtractionComparisonHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, ExtractionPipeline pipeline) {

		StatsLogger.create();
		List<Tree> trees = new ArrayList<Tree>();
		for (FileTreeElement fileTreeElement : services.rcpSelectionService.getCurrentSelectionsFromExplorer()) {
			Tree tree = readerManager.readFile(fileTreeElement);
			pipeline.pipe(tree);
			trees.add(tree);
		}

		CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
				new MetricImpl("test"));

		Tree mergedTree = compareEngine.compare(trees);
		services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, mergedTree);

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() >= 2;
	}

}
