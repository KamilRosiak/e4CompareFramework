package de.tu_bs.cs.isf.e4cf.refactoring.util;

import java.util.List;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionType;

public class ActionTreeBuilder {

	public void buildActionTree(List<ActionScope> actionScopes, Object item) {

		for (ActionScope actionScope : actionScopes) {
			Action action = actionScope.getAction();

			TreeItem actionTreeItem = null;

			if (item instanceof Tree) {
				actionTreeItem = new TreeItem((Tree) item, 0);
			} else if (item instanceof TreeItem) {
				actionTreeItem = new TreeItem((TreeItem) item, 0);
			}

			actionTreeItem.setText("Action type: " + action.getActionType());
			actionTreeItem.setData(actionScope);
			actionTreeItem.setChecked(actionScope.apply());

			TreeItem affectedNodeTreeItem = new TreeItem(actionTreeItem, 0);
			affectedNodeTreeItem.setText("Affected node: " + action.getAffectedNode().getNodeType());
			affectedNodeTreeItem.setData(actionScope);
			affectedNodeTreeItem.setChecked(actionScope.apply());

			TreeItem actionNodeTreeItem = new TreeItem(actionTreeItem, 0);
			actionNodeTreeItem.setText("Action node: " + action.getActionNode().getNodeType());
			actionNodeTreeItem.setData(actionScope);
			actionNodeTreeItem.setChecked(actionScope.apply());

			if (action.getActionType() == ActionType.UPDATE) {

				decorateWithAttributes(action.getAffectedNode().getAttributes(), affectedNodeTreeItem, actionScope);
				decorateWithAttributes(action.getActionNode().getAttributes(), actionNodeTreeItem, actionScope);

			} else if (action.getActionType() == ActionType.MOVE) {
				affectedNodeTreeItem.setText(
						affectedNodeTreeItem.getText() + ", Position " + action.getAffectedNode().getPosition());
				actionNodeTreeItem
						.setText(actionNodeTreeItem.getText() + ", Position " + action.getActionNode().getPosition());
			} else if (action.getActionType() == ActionType.ADD) {
				buildTreeRecursively(action.getActionNode(), actionScope, actionNodeTreeItem);
			}

			else if (action.getActionType() == ActionType.DELETE) {
				buildTreeRecursively(action.getAffectedNode(), actionScope, affectedNodeTreeItem);

			}

		}

	}

	private void decorateWithAttributes(List<Attribute> attributes, TreeItem item, ActionScope scope) {
		for (Attribute attribute : attributes) {

			if (attribute.getAttributeKey() != "Position") {
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
				attributeTreeItem.setChecked(scope.apply());
			}

		}
	}

	private void buildTreeRecursively(Node node, ActionScope scope, TreeItem item) {

		item.setText(node.getNodeType());
		item.setChecked(scope.apply());
		item.setData(scope);

		for (Node child : node.getChildren()) {
			buildTreeRecursively(child, scope, new TreeItem(item, 0));
		}

	}

}
