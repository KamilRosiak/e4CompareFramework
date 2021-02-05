package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import java.util.Stack;

public class CommandManager {

	private Stack<UndoAction> queueStackNormal;

	public CommandManager() {
		queueStackNormal = new Stack<>();
	}

	public void execute(UndoAction action) {
		queueStackNormal.push(action);
	}

	public void undo() {
		queueStackNormal.pop().undo();
	}

}
