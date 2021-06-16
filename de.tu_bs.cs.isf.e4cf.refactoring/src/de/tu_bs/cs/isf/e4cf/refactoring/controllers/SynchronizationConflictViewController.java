package de.tu_bs.cs.isf.e4cf.refactoring.controllers;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;
import de.tu_bs.cs.isf.e4cf.refactoring.views.SynchronizationConflictView;

@Singleton
@Creatable
public class SynchronizationConflictViewController extends Controller<SynchronizationConflictView> {

	public SynchronizationConflictViewController() {
		super(new SynchronizationConflictView());

	}

	private Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts;

	@Override
	protected void initView() {
		view.getActionScopeTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				TreeItem item = (TreeItem) event.item;
				ActionScope actionScope = (ActionScope) item.getData();

				for (Entry<Set<ActionScope>, Set<SynchronizationScope>> entry : conflicts.entrySet()) {
					if (entry.getKey().contains(actionScope)) {
						view.createSynchronizationScopeTree(entry.getValue());
					}
				}

			}
		});

		view.getSynchronizationScopeTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				TreeItem item = (TreeItem) event.item;

				if (event.detail == SWT.CHECK) {

					SynchronizationScope synchronizationScope = (SynchronizationScope) item.getData();
					synchronizationScope.setSynchronize(item.getChecked());

					if (item.getChecked()) {
						for (TreeItem treeItem : view.getSynchronizationScopeTree().getItems()) {
							if (treeItem != item) {
								treeItem.setChecked(false);
								((SynchronizationScope) treeItem.getData()).setSynchronize(false);
							}
						}
					} else {
						view.getSynchronizationScopeTree().getItem(0).setChecked(true);
					}

				}
			}
		});

	}

	public void showView(Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts) {
		this.conflicts = conflicts;
		view.showView(conflicts);

	}

}
