package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.manager;

/**
 * Interface for Undo
 * 
 * @author Team05
 *
 */

public interface Action {
    	/**
    	 * This method executes a given action 
    	 */
    	public void execute();
    	/**
    	 * This method undoes the executed action
    	 */
	public void undo();
}
