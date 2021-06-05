
package de.tu_bs.cs.isf.e4cf.evaluation.handler;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

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
	public void execute(ServiceContainer services) {
		System.out.println("Hello Generator");
	}
	
	
	/**
	 * This method checks if a artifact reader is available for this view
	 * 
	 * @param services
	 * @return
	 */
	@Evaluate
	public boolean isFileReaderAvailable(ServiceContainer services) {
		return true;
	}
	
}
