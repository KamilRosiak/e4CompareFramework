package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.FXTreeBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements.FXFamilyModelElement;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FXFamilyModelTree {

	private FXTreeBuilder familyTreeBuilder;
	private TreeView<FXFamilyModelElement> tree;

	public FXFamilyModelTree(FXTreeBuilder FamilyTreeBuilder) {
		this.familyTreeBuilder = FamilyTreeBuilder;
	}
	
	/**
	 * This method initializes this view and creates a TreeViewer out of the given FamilyModel.
	 */
	public void createFamilyModelTree(FamilyModel model) {
		TreeItem<FXFamilyModelElement> root = createFamilyModelItem(model);
		this.tree = new TreeView<FXFamilyModelElement>(root);
	}

	/**
	 * This model transforms a FamilyModel into his TreeItem representation.
	 */
	public TreeItem<FXFamilyModelElement> createFamilyModelItem(FamilyModel model) {
		TreeItem<FXFamilyModelElement> familyModelTreeItem = familyTreeBuilder.createTreeItem(model);
		for(VariationPoint varPoint : model.getVariationPoints()) {
			createVariationPoint(familyModelTreeItem, varPoint);
		}
		return familyModelTreeItem;
	}
	
	/**
	 * This method creates TreeItems that represents VariationPoint recursively.
	 */
	public void createVariationPoint(TreeItem<FXFamilyModelElement> parent, VariationPoint varPoint) {
		TreeItem<FXFamilyModelElement> varPointTreeItem = familyTreeBuilder.createTreeItem(varPoint);
		parent.getChildren().add(varPointTreeItem);
		for(VariationPoint childVarPoint : varPoint.getChildren()) {
			createVariationPoint(varPointTreeItem, childVarPoint);
		}
	}

	public TreeView<FXFamilyModelElement> getTree() {
		return tree;
	}

	public void setTree(TreeView<FXFamilyModelElement> tree) {
		this.tree = tree;
	}
}
