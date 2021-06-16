package de.tu_bs.cs.isf.e4cf.refactoring.views;

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
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationTreeBuilder;

public class SynchronizationConflictView extends View {

	private Label actionScopeLabel;
	private Label synchronizationScopeLabel;

	private Tree actionScopeTree;
	private Tree synchronizationScopeTree;

	public Tree getActionScopeTree() {
		return actionScopeTree;
	}

	public void setActionScopeTree(Tree actionScopeTree) {
		this.actionScopeTree = actionScopeTree;
	}

	public Tree getSynchronizationScopeTree() {
		return synchronizationScopeTree;
	}

	public void setSynchronizationScopeTree(Tree synchronizationScopeTree) {
		this.synchronizationScopeTree = synchronizationScopeTree;
	}

	private ActionTreeBuilder actionTreeBuilder;
	private SynchronizationTreeBuilder synchronizationTreeBuilder;

	public SynchronizationConflictView() {
		super(2, "Synchronization conflicts");

		synchronizationTreeBuilder = new SynchronizationTreeBuilder();
		actionTreeBuilder = new ActionTreeBuilder();

	}

	public void showView(Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts) {

		for (Entry<Set<ActionScope>, Set<SynchronizationScope>> entry : conflicts.entrySet()) {

			for (SynchronizationScope synchronizationScope : entry.getValue()) {
				synchronizationScope.setSynchronize(false);
			}
		}

		createActionScopeTree(conflicts.keySet());

		showView();
	}

	public void createActionScopeTree(Set<Set<ActionScope>> conflicts) {

		for (TreeItem item : actionScopeTree.getItems()) {
			item.dispose();
		}

		int i = 0;
		for (Set<ActionScope> actionScopes : conflicts) {
			TreeItem item = new TreeItem(actionScopeTree, 0);
			item.setText("Conflict " + i);
			item.setData(actionScopes.iterator().next());
			actionTreeBuilder.buildActionTree(Lists.newArrayList(actionScopes), item);
			i++;
		}

	}

	public void createSynchronizationScopeTree(Set<SynchronizationScope> synchronizationScopes) {

		for (TreeItem item : synchronizationScopeTree.getItems()) {
			item.dispose();
		}
		synchronizationTreeBuilder.buildSynchronizationTree(Lists.newArrayList(synchronizationScopes),
				synchronizationScopeTree);

	}

	@Override
	public void setWidgets() {
		actionScopeLabel = new Label(shell, 0);
		actionScopeLabel.setText("Actions");

		synchronizationScopeLabel = new Label(shell, 0);
		synchronizationScopeLabel.setText("Synchronizations");

		actionScopeTree = new Tree(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		actionScopeTree.setLayoutData(new GridData(GridData.FILL_BOTH));

		synchronizationScopeTree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		synchronizationScopeTree.setLayoutData(new GridData(GridData.FILL_BOTH));

	}

}
