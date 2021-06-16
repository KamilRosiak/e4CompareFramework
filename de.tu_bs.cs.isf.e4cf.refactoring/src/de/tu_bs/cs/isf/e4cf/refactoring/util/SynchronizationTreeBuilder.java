package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;

public class SynchronizationTreeBuilder {

	public void buildSynchronizationTree(List<SynchronizationScope> synchronizationScopes, Tree tree) {

		for (SynchronizationScope scope : synchronizationScopes) {
			buildTreeRecursively(scope.getNode(), scope, new TreeItem(tree, 0));

		}

	}

	private void buildTreeRecursively(Node node, SynchronizationScope scope, TreeItem currentItem) {

		currentItem.setText(node.getNodeType());
		currentItem.setChecked(scope.synchronize());
		currentItem.setData(scope);

		for (Node child : node.getChildren()) {
			buildTreeRecursively(child, scope, new TreeItem(currentItem, 0));
		}

	}

}
