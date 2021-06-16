package de.tu_bs.cs.isf.e4cf.refactoring.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ActionTreeBuilder;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ComponentTreeBuilder;

public class ActionView extends View {

	private Tree actionTree;
	private Tree componentTree;

	private Label actionLabel;
	private Label componentLabel;

	private Component selectedComponent;

	private Map<Component, List<ActionScope>> componentToActions;

	public Tree getActionTree() {
		return actionTree;
	}

	public void setActionTree(Tree actionTree) {
		this.actionTree = actionTree;
	}

	public Tree getComponentTree() {
		return componentTree;
	}

	public void setComponentTree(Tree componentTree) {
		this.componentTree = componentTree;
	}

	public Component getSelectedComponent() {
		return selectedComponent;
	}

	public void setSelectedComponent(Component selectedComponent) {
		this.selectedComponent = selectedComponent;
	}

	private ActionTreeBuilder actionTreeBuilder;
	private ComponentTreeBuilder componentTreeBuilder;

	public ActionView() {
		super(2, "Actions");

		actionTreeBuilder = new ActionTreeBuilder();
		componentTreeBuilder = new ComponentTreeBuilder();

	}

	public void showView(Map<Component, List<ActionScope>> componentToActions) {

		this.componentToActions = componentToActions;

		createComponentTree();

		List<ActionScope> allActionScopes = new ArrayList<ActionScope>();
		for (List<ActionScope> actionScopeList : componentToActions.values()) {
			allActionScopes.addAll(actionScopeList);
		}
		createActionTree(allActionScopes);

		showView();
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

			for (TreeItem treeItem : componentTree.getItems()) {
				markNodeRecursively(treeItem,
						((ActionScope) actionTree.getItem(0).getData()).getAction().getAffectedNode());
			}

		}

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

		componentLabel = new Label(shell, 0);
		componentLabel.setText("Components");

		actionLabel = new Label(shell, 0);
		actionLabel.setText("Actions");

		componentTree = new Tree(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		componentTree.setLayoutData(new GridData(GridData.FILL_BOTH));

		actionTree = new Tree(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		actionTree.setLayoutData(new GridData(GridData.FILL_BOTH));

	}

}
