package de.tu_bs.cs.isf.e4cf.evaluation.handler;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.evaluator.CloneEvaluator;

/**
 * This handler opens a dialog for the clone evaluator
 */
public class CloneEvaluationHandler {

	/**
	 * @param services
	 */
	@Execute
	public void execute(IEclipseContext context, CloneEvaluator evaluator) {
		System.out.println("Hello Evaluator");

		evaluator.evaluate();

		System.out.println("Goodbye");
	}
	
	@Evaluate
	public boolean isActive(ServiceContainer services) {
		if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1 &&
				services.rcpSelectionService.getCurrentSelectionsFromExplorer().get(0).isDirectory()) {
			
			FileTreeElement directoryElement = services.rcpSelectionService.getCurrentSelectionFromExplorer();
			directoryElement.getChildren();

			boolean foundVariantTree = false;
			boolean foundTree = false;
			boolean foundLog = false;
			
			for (FileTreeElement child : directoryElement.getChildren()) {
				String name = child.getFileName();
				
				// We need at least a log file and an original and one variant tree
				if (name.endsWith("0~1.tree")) {
					foundVariantTree = true;
				} else if (name.endsWith("0~0.tree")) {
					foundTree = true;
				} else if (name.endsWith(".log")) {
					foundLog = true;
				}
			}
			
			return foundLog && foundVariantTree && foundTree;
			
		} else {
			return false;
		}
	}
}
