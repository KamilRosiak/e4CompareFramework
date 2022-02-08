package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Insert;

public class SynchronizationTreeBuilder {

	public void buildSynchronizationTree(List<ActionScope> synchronizationScopes, Tree tree) {

		for (ActionScope scope : synchronizationScopes) {
			if (scope.getAction() instanceof Insert) {
				Insert insert = (Insert) scope.getAction();
				buildTreeRecursively(insert.getY(), scope, new TreeItem(tree, 0));

			} else {

				buildTreeRecursively(scope.getAction().getX(), scope, new TreeItem(tree, 0));
			}

		}

	}

	private void buildTreeRecursively(Node node, ActionScope scope, TreeItem currentItem) {

		currentItem.setText(node.getNodeType());
		currentItem.setChecked(scope.isApply());
		currentItem.setData(scope);

		for (Node child : node.getChildren()) {
			buildTreeRecursively(child, scope, new TreeItem(currentItem, 0));
		}

	}

}
