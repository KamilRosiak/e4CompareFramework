package de.tu_bs.cs.isf.e4cf.refactoring.util;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ComponentTreeBuilder {

	
	public void buildComponentTree(Iterable<Component> components, Tree componentTree) {
		
		for (Component component : components) {
			TreeItem componentTreeItem = new TreeItem(componentTree, 0);
			componentTreeItem.setText(component.getNodeType());
			componentTreeItem.setData(component);

			for (Node child : component.getChildren()) {
				buildTreeRecursively(child, component, new TreeItem(componentTreeItem, 0));
			}

		}
	}
	
	private void buildTreeRecursively(Node node, Component component, TreeItem currentItem) {

		currentItem.setText(node.getNodeType());
		currentItem.setData(node);

		for (Node child : node.getChildren()) {
			buildTreeRecursively(child, component, new TreeItem(currentItem, 0));
		}

	}
}
