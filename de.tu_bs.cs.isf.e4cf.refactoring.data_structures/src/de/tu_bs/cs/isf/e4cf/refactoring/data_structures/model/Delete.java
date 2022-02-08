package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class Delete extends Action {

	public Delete(Node x) {
		super(ActionType.DELETE, x);

	}

}
