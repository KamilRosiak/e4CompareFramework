package de.tu_bs.cs.isf.e4cf.evaluation.evaluator

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger
import java.util.List
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.SOURCE

@Singleton
@Creatable
class CloneEvaluator {
	
	@Inject CloneLogger logger;
	@Inject ServiceContainer services;
	@Inject ReaderManager readerManager
	
	def evaluate() {
		val directoryElement = services.rcpSelectionService.getCurrentSelectionFromExplorer();
		directoryElement.getChildren();

		// read
		var Tree originalTree = null;
		var Tree modifiedTree = null;
		var List<String> logEntries = null;
		
		for (FileTreeElement child : directoryElement.getChildren()) {
			val name = child.getFileName();
			
			if (name.endsWith(".mod.tree")) {
				modifiedTree = readerManager.readFile(child);
			} else if (name.endsWith(".tree")) {
				originalTree = readerManager.readFile(child);
			} else if (name.endsWith(".log")) {
				logEntries = logger.read(child.getAbsolutePath());
				logger.readAttr(logEntries.get(0), SOURCE);
			}
		}

		val matcher = new SortingMatcher();
		val metric = new MetricImpl("Metric");

		val engine = new CompareEngineHierarchical(matcher, metric);
		var mergedTree = engine.compare(originalTree, modifiedTree);
		services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, mergedTree);
		// TODO evaluate
		// change -> 2 attribute values instead of one (?VARIABILITY CLASS (alternative))
		// change -> VARIABILITY CLASS (optional)
		
		// Get all nodes (optional / alternative)
		// -> xcheck in log for an (?) event
		
		// precision and recall
	}
	
}