package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.FileTable;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class ConfigurationDecorator implements NodeDecorator {

	@Override
	public boolean isSupportedTree(Tree tree) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> treeItem) {

		Node node = treeItem.getValue();
		
		if(node instanceof Configuration) {
			return treeItem;
		}

		if (node instanceof Component) {
			TreeItem<Node> parentTreeItem = treeItem.getParent();
			int position = parentTreeItem.getChildren().indexOf(treeItem);
			Node parentNode = parentTreeItem.getValue();
			Component component = (Component) node;
			Configuration configuration = component.getNodeComponentRelation().get(parentNode).get(position);

			for (TreeItem<Node> child : treeItem.getChildren()) {

				if (child.getValue() == configuration) {
					child.setGraphic(new ImageView(FileTable.FV_CONFIGURATION_16));
				}
			}

		}
		return treeItem;

	}

}
