package de.tu_bs.cs.isf.e4cf.refactoring.controllers;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;
import de.tu_bs.cs.isf.e4cf.refactoring.views.SynchronizationView;

@Singleton
@Creatable
public class SynchronizationViewController extends Controller<SynchronizationView> {

	private Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations;

	public SynchronizationViewController() {
		super(new SynchronizationView());

	}

	@Override
	protected void initView() {
		view.getActionTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				TreeItem item = (TreeItem) event.item;
				ActionScope actionScope = (ActionScope) item.getData();
				List<SynchronizationScope> synchronizationScopes = actionsToSynchronizations.get(actionScope);

				view.createSynchronizationTree(synchronizationScopes);

			}
		});

		view.getSynchronizationTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				TreeItem item = (TreeItem) event.item;
				SynchronizationScope synchronizationScope = (SynchronizationScope) item.getData();

				view.getComponentTree().deselectAll();

				for (TreeItem treeItem : view.getComponentTree().getItems()) {

					view.markNodeRecursively(treeItem, synchronizationScope.getNode());
				}

				if (event.detail == SWT.CHECK) {

					boolean checked = item.getChecked();

					synchronizationScope.setSynchronize(checked);

					while (item.getParentItem() != null) {
						item = item.getParentItem();
					}
					view.checkTreeRecursively(item, checked);

				}
			}
		});

	}

	public void showView(Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations,
			Map<Component, List<ActionScope>> componentToActions) {

		this.actionsToSynchronizations = actionsToSynchronizations;

		view.showView(actionsToSynchronizations, componentToActions);

	}

}
