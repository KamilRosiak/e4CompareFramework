package de.tu_bs.cs.isf.e4cf.refactoring.util;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.MultiSetNode;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.MultiSetTree;

public class ComponentTreeBuilder {

	
	public void buildComponentTree(Iterable<MultiSetTree> components, Tree componentTree) {
		
		for (MultiSetTree component : components) {
			TreeItem componentTreeItem = new TreeItem(componentTree, 0);
			componentTreeItem.setText("Component");
			componentTreeItem.setData(component);

			for (MultiSetNode child : component.getRoots()) {
				buildTreeRecursively(child, component, new TreeItem(componentTreeItem, 0));
			}

		}
	}
	
	private void buildTreeRecursively(MultiSetNode node, MultiSetTree component, TreeItem currentItem) {

		currentItem.setText(node.getNode().getNodeType());
		currentItem.setData(node.getNode());

		for (MultiSetNode child : node.getChildren()) {
			buildTreeRecursively(child, component, new TreeItem(currentItem, 0));
		}

	}
}
