package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
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

	@Inject
	private ActionViewController actionViewController;

	public boolean showActionView(List<ConfigurationComparison> configurationComparisons) {
		actionViewController.showView(configurationComparisons);
		return actionViewController.isResult();
	}

	public void configureConfigurations(List<ConfigurationComparison> configurationComparisons) {

		// if there is a configuration whose change was not applied, add it as new
		// configurations
		for (ConfigurationComparison comparison : configurationComparisons) {

			for (ActionScope actionScope : comparison.getActionScopes()) {

				if (!actionScope.isApply()) {
					comparison.getComponentComparison().getAddedConfigurations().add(comparison.getConfiguration2());
					break;
				}
			}
		}
	}

	public void update(List<ConfigurationComparison> configurationComparisons,
			Map<Component, MultiSet> multiSets) {

		for (ConfigurationComparison configurationComparison : configurationComparisons) {
			//move actions on preceding unselected inserts should not be applied
			for (ActionScope actionScope1 : configurationComparison.getActionScopes()) {
				if (actionScope1.getAction() instanceof Insert && !actionScope1.isApply()) {
					Insert insert = (Insert) actionScope1.getAction();
					for (ActionScope actionScope2 : configurationComparison.getActionScopes()) {
						if (actionScope2.getAction() instanceof Move && actionScope2.isApply()) {
							Move move = (Move) actionScope2.getAction();
							if (move.getX() == insert.getX()) {
								actionScope2.setApply(false);
							}
						}

					}

				}

			}

			update(configurationComparison.getActionScopes(),
					multiSets.get(configurationComparison.getComponent1()));
		}

	}

	private void update(List<ActionScope> actionScopes, MultiSet multiSet) {

		actionScopes = sortActionScopes(actionScopes);

		List<Node> insertedNodes = new ArrayList<Node>();

		for (ActionScope actionScope : actionScopes) {
			Action action = actionScope.getAction();

			// always apply Insert actions
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
