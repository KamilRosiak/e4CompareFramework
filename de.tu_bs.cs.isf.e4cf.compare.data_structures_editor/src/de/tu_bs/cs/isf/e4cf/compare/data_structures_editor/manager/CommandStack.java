package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import java.util.Stack;

/**
 * Stack for Actions which allows to undo actions.
 * 
 * @author Team05
 *
 */

public class CommandStack {

    private Stack<UndoAction> queueStackNormal;

    public CommandStack() {
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
