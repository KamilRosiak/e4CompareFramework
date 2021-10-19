package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

public enum ChangeActionType {

	ADD_CHILD_NODE(0), DELETE_NODE(1), ADD_ATTRIBUTE(2), DELETE_ATTRIBUTE(3), ADD_ATTRIBUTE_VALUE(4), EDIT_ATTRIBUTE_VALUE(5), EDIT_ATTRIBUTE_KEY(6);

	private final int value;

	private ChangeActionType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
