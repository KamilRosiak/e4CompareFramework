package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Delete;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Move;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Update;

public class ActionTreeBuilder {

	public void buildActionTree(List<ActionScope> actionScopes, Object item) {

		for (ActionScope actionScope : actionScopes) {
			Action action = actionScope.getAction();

			TreeItem mainTreeItem = null;

			if (item instanceof Tree) {
				mainTreeItem = new TreeItem((Tree) item, 0);
			} else if (item instanceof TreeItem) {
				mainTreeItem = new TreeItem((TreeItem) item, 0);
			}

			mainTreeItem.setText("Action type: " + action.getActionType());
			mainTreeItem.setData(actionScope);
			mainTreeItem.setChecked(actionScope.isApply());

			TreeItem subTreeItem1 = new TreeItem(mainTreeItem, 0);

			subTreeItem1.setData(actionScope);
			subTreeItem1.setChecked(actionScope.isApply());

			TreeItem subTreeItem2 = new TreeItem(mainTreeItem, 0);

			subTreeItem2.setData(actionScope);
			subTreeItem2.setChecked(actionScope.isApply());

			if (action instanceof Update) {

				Update update = (Update) action;

				subTreeItem1.setText("Node to update: " + update.getX().getNodeType());
				subTreeItem2.setText("Update with: " + update.getY().getNodeType());
				decorateWithAttributes(update.getX().getAttributes(), subTreeItem1, actionScope);
				decorateWithAttributes(update.getY().getAttributes(), subTreeItem2, actionScope);

			} else if (action instanceof Move) {

				Move move = (Move) action;

				TreeItem subTreeItem3 = new TreeItem(mainTreeItem, 0);
				subTreeItem3.setData(actionScope);
				subTreeItem3.setChecked(actionScope.isApply());

				subTreeItem1.setText("Node: " + move.getX().getNodeType());
				subTreeItem2.setText("New parent: " + move.getY().getNodeType());
				subTreeItem3.setText("Position: " + move.getPosition());

			} else if (action instanceof Insert) {

				Insert insert = (Insert) action;

				buildTreeRecursively(insert.getX(), actionScope, subTreeItem2);

				TreeItem subTreeItem3 = new TreeItem(mainTreeItem, 0);
				subTreeItem3.setData(actionScope);
				subTreeItem3.setChecked(actionScope.isApply());

				subTreeItem1.setText("Node: " + insert.getX().getNodeType());
				subTreeItem2.setText("Parent: " + insert.getY().getNodeType());
				subTreeItem3.setText("Position: " + insert.getPosition());

			}

			else if (action instanceof Delete) {

				Delete delete = (Delete) action;

				buildTreeRecursively(delete.getX().getParent(), actionScope, subTreeItem1);

				subTreeItem1.setText("Node: " + delete.getX().getNodeType());
				subTreeItem2.setText("Remove child from: " + delete.getX().getParent().getNodeType());

			}

		}

	}

	private void decorateWithAttributes(List<Attribute> attributes, TreeItem item, ActionScope scope) {
		for (Attribute attribute : attributes) {

			TreeItem attributeTreeItem = new TreeItem(item, 0);
			String valueString = "";
			for (int i = 0; i < attribute.getAttributeValues().size(); i++) {

				valueString += attribute.getAttributeValues().get(i).getValue().toString();
				if (i != attribute.getAttributeValues().size() - 1) {
					valueString += ",";
				}
			}

			attributeTreeItem.setText(attribute.getAttributeKey() + ":" + valueString);
			attributeTreeItem.setData(scope);
			attributeTreeItem.setChecked(scope.isApply());

		}
	}

	private void buildTreeRecursively(Node node, ActionScope scope, TreeItem item) {

		item.setText(node.getNodeType());
		item.setChecked(scope.isApply());
		item.setData(scope);

		for (Node child : node.getChildren()) {
			buildTreeRecursively(child, scope, new TreeItem(item, 0));
		}

	}

}
