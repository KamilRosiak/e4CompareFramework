package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import java.util.Stack;

public class CommandManager {
	private static CommandManager instance = null;
	private Stack<UndoAction> queueStackNormal;

	static CommandManager getInstance() {
		if (instance != null)
			return instance;
		return new CommandManager();
	}

	public CommandManager() {
		queueStackNormal = new Stack<>();
	}

	public void execute(UndoAction action) {
		queueStackNormal.push(action);
	}

	public void undo() {
		queueStackNormal.pop().undo();
	}

	void clearNormal() {
		queueStackNormal.clear();
	}

	public Stack<UndoAction> getQueueStackNormal() {
		return queueStackNormal;
	}

	public void setQueueStackNormal(Stack<UndoAction> queueStackNormal) {
		this.queueStackNormal = queueStackNormal;
	}

}
