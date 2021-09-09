package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;

public interface ActionCallback {

	public void handle(List<ActionScope> actionScopes);

}
