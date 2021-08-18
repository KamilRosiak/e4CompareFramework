package de.tu_bs.cs.isf.e4cf.refactoring.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations;

	@Override
	protected void initView() {
		view.getConflictsTree().addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				TreeItem item = (TreeItem) event.item;

				if (!item.getText().contains("Conflict")) {

					boolean checked = item.getChecked();
					ActionScope actionScope = (ActionScope) item.getData();

					if (event.detail == SWT.CHECK) {

						List<SynchronizationScope> synchronizationScopes = actionsToSynchronizations.get(actionScope);

						for (Entry<Set<ActionScope>, Set<SynchronizationScope>> entry : conflicts.entrySet()) {
							if (entry.getKey().contains(actionScope)) {

								for (SynchronizationScope synchronizationScope : entry.getValue()) {

									if (synchronizationScopes.contains(synchronizationScope)) {
										synchronizationScope.setSynchronize(checked);
									} else {
										synchronizationScope.setSynchronize(false);

									}

								}

							}
						}

						for (TreeItem treeItem : item.getParentItem().getItems()) {
							if (treeItem != item) {
								view.checkTreeRecursively(treeItem, false);

							} else {
								view.checkTreeRecursively(treeItem, true);
							}
						}

					}

				}

			}
		});

	}

	public void showView(Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts,
			Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations) {
		this.conflicts = conflicts;
		this.actionsToSynchronizations = actionsToSynchronizations;
		view.showView(conflicts, actionsToSynchronizations);

	}

}
