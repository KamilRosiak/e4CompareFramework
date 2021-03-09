package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

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
