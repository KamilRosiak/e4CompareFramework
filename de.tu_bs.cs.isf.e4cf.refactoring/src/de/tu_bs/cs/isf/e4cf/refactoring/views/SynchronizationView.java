package de.tu_bs.cs.isf.e4cf.refactoring.views;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ActionTreeBuilder;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ComponentTreeBuilder;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationTreeBuilder;


public class SynchronizationView extends View {

	private Tree actionTree;
	private Tree synchronizationTree;
	private Tree componentTree;

	private Label actionLabel;
	private Label scopeLabel;
	private Label componentLabel;
	private Label taskLabel;
	

	private Component selectedComponent;

	public Component getSelectedComponent() {
		return selectedComponent;
	}

	public void setSelectedComponent(Component selectedComponent) {
		this.selectedComponent = selectedComponent;
	}

	public Tree getActionTree() {
		return actionTree;
	}

	public void setActionTree(Tree actionTree) {
		this.actionTree = actionTree;
	}

	public Tree getSynchronizationTree() {
		return synchronizationTree;
	}

	public void setSynchronizationTree(Tree synchronizationTree) {
		this.synchronizationTree = synchronizationTree;
	}

	private Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations;
	private Map<Component, List<ActionScope>> componentToActions;

	private ComponentTreeBuilder componentTreeBuilder;
	private ActionTreeBuilder actionTreeBuilder;
	private SynchronizationTreeBuilder synchronizationTreeBuilder;

	public SynchronizationView() {
		super(3, "Synchronization application");
		componentTreeBuilder = new ComponentTreeBuilder();
		actionTreeBuilder = new ActionTreeBuilder();
		synchronizationTreeBuilder = new SynchronizationTreeBuilder();

	}

	public void showView(Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations,
			Map<Component, List<ActionScope>> componentToActions) {

		this.actionsToSynchronizations = actionsToSynchronizations;
		this.componentToActions = componentToActions;
		createActionTree(Lists.newArrayList(actionsToSynchronizations.keySet()));
		createComponentTree();

		showView();
	}

	public void closeView() {
		shell.close();
	}

	public Tree getComponentTree() {
		return componentTree;
	}

	public void setComponentTree(Tree componentTree) {
		this.componentTree = componentTree;
	}

	public void createComponentTree() {

		for (TreeItem item : componentTree.getItems()) {
			item.dispose();
		}

		componentTreeBuilder.buildComponentTree(componentToActions.keySet(), componentTree);

	}

	public void createActionTree(List<ActionScope> actionScopes) {

		for (TreeItem item : actionTree.getItems()) {
			item.dispose();
		}

		actionTreeBuilder.buildActionTree(actionScopes, actionTree);

		if (actionTree.getItemCount() > 0) {
			actionTree.select(actionTree.getItem(0));

			ActionScope actionScope = (ActionScope) actionTree.getItem(0).getData();

			createSynchronizationTree(actionsToSynchronizations.get(actionScope));
		}

	}

	public void createSynchronizationTree(List<SynchronizationScope> synchronizationScopes) {

		for (TreeItem item : synchronizationTree.getItems()) {
			item.dispose();
		}

		synchronizationTreeBuilder.buildSynchronizationTree(synchronizationScopes, synchronizationTree);

	}

	public void markNodeRecursively(TreeItem currentTreeItem, Node node) {

		for (TreeItem child : currentTreeItem.getItems()) {

			Object data = child.getData();
			if (data != null && data.equals(node)) {
				componentTree.select(child);
				return;
			}

			markNodeRecursively(child, node);
		}

	}

	@Override
	public void setWidgets() {
		
		taskLabel = new Label(shell, 0);
		taskLabel.setText("Choose synchronizations to be applied:");

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		taskLabel.setLayoutData(gridData);
		
		actionLabel = new Label(shell, 0);
		actionLabel.setText("Actions");

		scopeLabel = new Label(shell, 0);
		scopeLabel.setText("Synchronization scope");

		componentLabel = new Label(shell, 0);
		componentLabel.setText("Components");

		actionTree = new Tree(shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		actionTree.setLayoutData(new GridData(GridData.FILL_BOTH));

		synchronizationTree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		synchronizationTree.setLayoutData(new GridData(GridData.FILL_BOTH));

		componentTree = new Tree(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		componentTree.setLayoutData(new GridData(GridData.FILL_BOTH));

	}

}
