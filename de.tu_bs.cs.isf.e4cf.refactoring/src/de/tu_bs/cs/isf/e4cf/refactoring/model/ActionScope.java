package de.tu_bs.cs.isf.e4cf.refactoring.model;

public class ActionScope implements Comparable<ActionScope> {

	private Action action;

	private boolean apply;

	public boolean apply() {
		return apply;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setApply(boolean apply) {
		this.apply = apply;
	}

	public ActionScope(Action action, boolean apply) {
		super();
		this.action = action;
		this.apply = apply;
	}

	@Override
	public int compareTo(ActionScope o) {

		if (this.getAction().getActionType() == o.getAction().getActionType()) {
			return 0;
		}

		if (this.getAction().getActionType() == ActionType.MOVE) {
			return -1;
		}

		return 1;
	}

}
