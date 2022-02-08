package de.tu_bs.cs.isf.e4cf.refactoring.controllers;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.views.SynchronizationView;

@Singleton
@Creatable
public class SynchronizationViewController extends Controller<SynchronizationView> {

	private Map<ActionScope, List<ActionScope>> actionsToSynchronizations;

	@Override
	protected void initView() {
		view.getActionTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				TreeItem item = (TreeItem) event.item;
				ActionScope actionScope = (ActionScope) item.getData();
				List<ActionScope> synchronizationScopes = actionsToSynchronizations.get(actionScope);

				view.createSynchronizationTree(synchronizationScopes);

			}
		});

		view.getSynchronizationTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				TreeItem item = (TreeItem) event.item;
				ActionScope synchronizationScope = (ActionScope) item.getData();

				view.getComponentTree().deselectAll();

				for (TreeItem treeItem : view.getComponentTree().getItems()) {

					if (synchronizationScope.getAction() instanceof Insert) {
						Insert insert = (Insert) synchronizationScope.getAction();
						view.markNodeRecursively(treeItem, insert.getY());

					} else {
						view.markNodeRecursively(treeItem, synchronizationScope.getAction().getX());
					}
				}
				if (event.detail == SWT.CHECK) {

					boolean checked = item.getChecked();

					synchronizationScope.setApply(checked);

					while (item.getParentItem() != null) {
						item = item.getParentItem();
					}
					view.checkTreeRecursively(item, checked);

				}
			}
		});

	}

	public void showView(Map<ActionScope, List<ActionScope>> actionsToSynchronizations, CloneModel cloneModel) {
		this.actionsToSynchronizations = actionsToSynchronizations;
		createView(new SynchronizationView());
		view.showView(actionsToSynchronizations, cloneModel);
	}

}
