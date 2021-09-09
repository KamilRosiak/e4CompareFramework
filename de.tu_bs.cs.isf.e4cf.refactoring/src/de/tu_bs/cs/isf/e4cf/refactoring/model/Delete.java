package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class Delete extends Action {

	public Delete(Node x) {
		super(ActionType.DELETE, x);

	}

	private int savedPosition;

	@Override
	public void apply() {
		savedPosition = x.getPosition();
		x.getParent().getChildren().remove(x);
	}

	@Override
	public void undo() {
		x.getParent().addChildAtPosition(x, savedPosition);

	}
}
