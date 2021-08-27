package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ActionViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ConfigurationComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Delete;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Move;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSet;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Update;

@Singleton
@Creatable
public class ActionManager {

	private ActionViewController actionViewController;

	public ActionManager() {
		actionViewController = new ActionViewController();

	}

	public boolean showActionView(List<ConfigurationComparison> configurationComparisons) {

		actionViewController.showView(configurationComparisons);

		return actionViewController.isResult();

	}

	public void configureConfigurations(List<ConfigurationComparison> configurationComparisons) {

		for (ConfigurationComparison comparison : configurationComparisons) {

			for (ActionScope actionScope : comparison.getActionScopes()) {

				if (!actionScope.isApply()) {
					comparison.getComponentComparison().getAddedConfigurations().add(comparison.getConfiguration2());
					break;
				}
			}
		}
	}

	public void applyActions(List<ConfigurationComparison> configurationComparisons,
			Map<Component, MultiSet> multiSets) {

		for (ConfigurationComparison configurationComparison : configurationComparisons) {
			applySelectedActions(configurationComparison.getActionScopes(),
					multiSets.get(configurationComparison.getComponent1()));
		}

	}

	private void applySelectedActions(List<ActionScope> actionScopes, MultiSet multiSet) {

		// apply actions: first perform insert actions even if they are not applied,
		// then
		// move actions and delete actions.
		// Remove not applied add actions in the end.
		actionScopes = sortActionScopes(actionScopes);

		List<Node> insertedNodes = new ArrayList<Node>();

		for (ActionScope actionScope : actionScopes) {
			Action action = actionScope.getAction();

			if (action instanceof Insert) {
				Insert insert = (Insert) action;
				insertedNodes.add(insert.getX());
				insert.apply();

			} else if (action instanceof Move) {

				if (actionScope.isApply()) {
					Move move = (Move) action;
					move.apply();
				}

			} else if (action instanceof Delete) {

				if (actionScope.isApply()) {

					Delete delete = (Delete) action;
					delete.apply();
				}
			} else if (action instanceof Update) {

				if (actionScope.isApply()) {
					Update update = (Update) action;
					update.apply();
				}
			}
		}

		// Remove unapplied insert actions
		for (ActionScope actionScope : actionScopes) {

			if (actionScope.getAction() instanceof Insert && !actionScope.isApply()) {
				Insert insert = (Insert) actionScope.getAction();
				insert.undo();
			} else if (actionScope.getAction() instanceof Insert && actionScope.isApply()) {
				Insert insert = (Insert) actionScope.getAction();
				multiSet.add(insert.getX());

			}
		}

	}

	private List<ActionScope> sortActionScopes(List<ActionScope> actionScopes) {

		// sort action scopes: INSERT, MOVE/UPDATE, DELETE
		List<ActionScope> sortedActionScopes = new ArrayList<ActionScope>();
		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction() instanceof Insert) {
				sortedActionScopes.add(actionScope);
			}
		}

		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction() instanceof Move || actionScope.getAction() instanceof Update) {
				sortedActionScopes.add(actionScope);
			}
		}

		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction() instanceof Delete) {
				sortedActionScopes.add(actionScope);
			}
		}
		return sortedActionScopes;

	}

}
