package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ActionViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Delete;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Move;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSet;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Update;

@Singleton
@Creatable
public class ActionManager {

	private ActionViewController actionViewController;

	@Inject
	public ActionManager(ActionViewController actionViewController) {
		super();
		this.actionViewController = actionViewController;
	}

	public boolean showActionView(CloneModel cloneModel, List<ActionScope> actionScopes) {
		actionViewController.showView(cloneModel, actionScopes);
		return actionViewController.isResult();
	}

	public void update(List<ActionScope> actionScopes, CloneModel cloneModel) {

		// move actions on preceding unselected inserts should not be applied
		for (ActionScope actionScope1 : actionScopes) {
			if (actionScope1.getAction() instanceof Insert && !actionScope1.isApply()) {
				Insert insert = (Insert) actionScope1.getAction();
				for (ActionScope actionScope2 : actionScopes) {
					if (actionScope2.getAction() instanceof Move && actionScope2.isApply()) {
						Move move = (Move) actionScope2.getAction();
						if (move.getX() == insert.getX()) {
							actionScope2.setApply(false);
						}
					}

				}

			}

		}

		applyUpdates(actionScopes, cloneModel);

	}

	private void applyUpdates(List<ActionScope> actionScopes, CloneModel cloneModel) {

		actionScopes = sortActionScopes(actionScopes);

		for (ActionScope actionScope : actionScopes) {
			Action action = actionScope.getAction();

			// always apply Insert actions
			if (action instanceof Insert) {
				Insert insert = (Insert) action;
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

				Component component = actionScope.getComponent();
				MultiSet multiSet = cloneModel.getMultiSets().get(component);			

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
