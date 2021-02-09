package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import java.util.Stack;

/**
 * Manager for Undo
 * 
 * @author Team05
 *
 */

public class CommandManager {

	private Stack<UndoAction> queueStackNormal;

	public CommandManager() {
		queueStackNormal = new Stack<>();
	}

	/**
	 * Pushes an action on to the stack
	 * 
	 * @param action
	 */
	public void execute(UndoAction action) {
		queueStackNormal.push(action);
	}

	/**
	 * Invokes the action last pushed on to the stack
	 */
	public void undo() {
		queueStackNormal.pop().undo();
	}

}
