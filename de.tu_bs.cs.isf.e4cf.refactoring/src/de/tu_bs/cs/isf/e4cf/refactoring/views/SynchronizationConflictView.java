package de.tu_bs.cs.isf.e4cf.refactoring.views;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ActionTreeBuilder;

public class SynchronizationConflictView extends View {

	private Label conflictsLabel;
	private Label taskLabel;

	private Tree conflictsTree;

	public Tree getConflictsTree() {
		return conflictsTree;
	}

	public void setConflictsTree(Tree conflictsTree) {
		this.conflictsTree = conflictsTree;
	}

	private ActionTreeBuilder actionTreeBuilder;

	public SynchronizationConflictView() {
		super(2, "Synchronization conflicts");

		actionTreeBuilder = new ActionTreeBuilder();

	}

	public void showView(Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts,
			Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations) {

		for (Entry<Set<ActionScope>, Set<SynchronizationScope>> entry : conflicts.entrySet()) {

			for (SynchronizationScope synchronizationScope : entry.getValue()) {
				synchronizationScope.setSynchronize(false);
			}
		}

		createActionScopeTree(conflicts.keySet());

		showView();
	}

	public void createActionScopeTree(Set<Set<ActionScope>> conflicts) {

		for (TreeItem item : conflictsTree.getItems()) {
			item.dispose();
		}

		int i = 0;
		for (Set<ActionScope> actionScopes : conflicts) {
			TreeItem item = new TreeItem(conflictsTree, 0);
			item.setText("Conflict " + i);
			item.setData(actionScopes.iterator().next());
			actionTreeBuilder.buildActionTree(Lists.newArrayList(actionScopes), item);

			checkTreeRecursively(item, false);

			i++;
		}

	}

	@Override
	public void setWidgets() {

		taskLabel = new Label(shell, 0);
		taskLabel.setText("Select one of the conflicting actions to be synchronized:");
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		taskLabel.setLayoutData(gridData);

		conflictsLabel = new Label(shell, 0);
		conflictsLabel.setText("Conflicts");
		
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		conflictsLabel.setLayoutData(gridData);

		conflictsTree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		conflictsTree.setLayoutData(gridData);

	}

}
