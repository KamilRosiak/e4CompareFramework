package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;

public interface SynchronizationCallback {

	public void handle(Map<ActionScope, List<ActionScope>> synchronizationScopes);
}
