package de.tu_bs.cs.isf.e4cf.refactoring.model;

public class ActionScope {

	private Action action;

	private boolean apply;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public boolean isApply() {
		return apply;
	}

	public void setApply(boolean apply) {
		this.apply = apply;
	}

	public ActionScope(Action action, boolean apply) {
		super();
		this.action = action;
		this.apply = apply;
	}

	public ActionScope(Action action, boolean apply, MultiSetNode multiSetNode) {
		super();
		this.action = action;
		this.apply = apply;
		this.multiSetNode = multiSetNode;
	}

	private MultiSetNode multiSetNode;

	public MultiSetNode getMultiSetNode() {
		return multiSetNode;
	}

	public void setMultiSetNode(MultiSetNode multiSetNode) {
		this.multiSetNode = multiSetNode;
	}

}
