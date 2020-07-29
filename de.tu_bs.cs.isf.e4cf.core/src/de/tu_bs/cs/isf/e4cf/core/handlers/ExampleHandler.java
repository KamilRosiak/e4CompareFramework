package de.tu_bs.cs.isf.e4cf.core.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

/**
 * Example implementation of an command 
 *  
 * @author {Kamil Rosiak}
 */
public class ExampleHandler {
	
		@Execute
		public void execute(){
		}
		
		@CanExecute
		public boolean canExecute() {
			return true;
		}
		
		
		/**
		 * Imperative Expression
		 */
		@Evaluate
		public boolean evaluate() {
			return true;
		}
}
