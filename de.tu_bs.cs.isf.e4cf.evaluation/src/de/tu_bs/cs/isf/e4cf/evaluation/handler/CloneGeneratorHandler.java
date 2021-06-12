
package de.tu_bs.cs.isf.e4cf.evaluation.handler;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator;

/**
 * This handler opens a given file in a tree view if a adapter exists
 * 
 *
 */
public class CloneGeneratorHandler {

	/**
	 * 
	 * 
	 * @param services
	 */
	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, CloneGenerator gen) {
		System.out.println("Hello Generator");
		
		// multi selection might be enabled by unifying selection list under a virtual directory root node later
		Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());
		gen.go(tree);
		
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
			return true;
		} else {
			return false;
		}
	}
	
}
