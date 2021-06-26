package de.tu_bs.cs.isf.e4cf.evaluation.handler;

import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator;

/**
 * This handler opens a dialog for the clone evaluator
 */
public class CloneEvaluationHandler {

	/**
	 * @param services
	 */
	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, ReaderManager readerManager,
			CloneGenerator gen) {
		System.out.println("Hello Evaluator");

		Path workspaceRoot = services.workspaceFileSystem.getWorkspaceDirectory().getFile();

		FileTreeElement directoryElement = services.rcpSelectionService.getCurrentSelectionFromExplorer();
//		Path directory = Paths.get(services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath());
		directoryElement.getChildren();

		// read
		Tree originalTree = null;
		Tree modifiedTree = null;
		String logTreeFile;
		
		for (FileTreeElement child : directoryElement.getChildren()) {
			String name = child.getFileName();
			
			if (name.endsWith(".mod.tree")) {
				modifiedTree = readerManager.readFile(child);
			} else if (name.endsWith(".tree")) {
				originalTree = readerManager.readFile(child);
				break;
			} else if (name.endsWith(".log")) {
				// TODO read log
				logTreeFile = child.getFileName();
			}
		}

		SortingMatcher matcher = new SortingMatcher();
		Metric metric = new MetricImpl("Metric");

		CompareEngineHierarchical engine = new CompareEngineHierarchical(matcher, metric);
		Tree mergedTree = engine.compare(originalTree, modifiedTree);
		services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, mergedTree);
		// TODO evaluate
		// change -> 2 attribute values instead of one (?VARIABILITY CLASS (alternative))
		// change -> VARIABILITY CLASS (optional)
		
//		GeneratorDialog dialog = new GeneratorDialog(context, services.imageService);
//		Optional<GeneratorOptions> result = dialog.open();
//		result.ifPresent(options -> {
//			// multi selection might be enabled by unifying selection list under a virtual directory root node later
//			Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());
//			gen.go(tree, options);
//		});

		System.out.println("Goodbye");
	}

	/**
	 * This method checks if a artifact reader is available for this view
	 * 
	 * @param services
	 * @return
	 */
	@Evaluate
	public boolean isFileReaderAvailable(ServiceContainer services) {
		if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1) {
			return services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0).isDirectory();
			
			// TODO: check if expected files exist
		} else {
			return false;
		}
	}
}
