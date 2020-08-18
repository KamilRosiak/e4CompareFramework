package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.core.util.emf.EMFUtil;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.FXTreeBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements.FXFamilyModelElement;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FXArtefactTree {
	
	private TreeView<FXFamilyModelElement> tree;
	private FXTreeBuilder familyTreeBuilder;
	private FXTreeBuilder artefactTreeBuilder;

	public FXArtefactTree(FXTreeBuilder FamilyTreeBuilder, FXTreeBuilder artefactTreeBuilder) {
		this.familyTreeBuilder = FamilyTreeBuilder;
		this.artefactTreeBuilder = artefactTreeBuilder;
	}
	
	/**
	 * This method initializes this view and creates a TreeViewer out of the given FamilyModel.
	 */
	public void createFamilyModelVariantTree(FamilyModel model) {
		TreeItem<FXFamilyModelElement> root = createFamilyModelItem(model);
		this.tree = new TreeView<FXFamilyModelElement>(root);
	}
		
	public TreeItem<FXFamilyModelElement> createFamilyModelItem(FamilyModel familyModel) {
		TreeItem<FXFamilyModelElement> familyModelItem = familyTreeBuilder.createTreeItem(familyModel);	
		
		for (Variant variant : familyModel.getVariants()) {
			TreeItem<FXFamilyModelElement> variantItem = createVariantItem(variant);
			familyModelItem.getChildren().add(variantItem);
		}
		
		return familyModelItem;
	}
	
	public TreeItem<FXFamilyModelElement> createVariantItem(Variant variant) {
		TreeItem<FXFamilyModelElement> variantItem = familyTreeBuilder.createTreeItem(variant);
		variant.getInstance();
		
		// create artefact sub tree structure on top of the variant item
		createArtefactItemTree(variantItem, variant.getInstance());
		return variantItem;
	}

	public void createArtefactItemTree(TreeItem<FXFamilyModelElement> variantItem, EObject rootArtefact) {
		EMFUtil.applyOnContainmentStructure(variantItem, rootArtefact, (parent, eobject) -> {
			TreeItem<FXFamilyModelElement> artifactItem = artefactTreeBuilder.createTreeItem(eobject);
			parent.getChildren().add(artifactItem);
			return artifactItem;
		});
	}
	
	public void createArtefactTree(VariationPoint varPoint) {
		TreeItem<FXFamilyModelElement> varPointItem = createVariationPointItem(varPoint);
		this.tree = new TreeView<FXFamilyModelElement>(varPointItem);
		
	}
	
	public TreeItem<FXFamilyModelElement> createVariationPointItem(VariationPoint varPoint) {		
		// set variation point as root
		TreeItem<FXFamilyModelElement> varPointItem = familyTreeBuilder.createTreeItem(varPoint);
		for (VariantArtefact varArtefact : varPoint.getVariantArtefacts()) {
			TreeItem<FXFamilyModelElement> varArtefactItem = createVariantArtefactItem(varArtefact);
			varPointItem.getChildren().add(varArtefactItem);
		}
		return varPointItem;
	}

	public TreeItem<FXFamilyModelElement> createVariantArtefactItem(VariantArtefact varArtefact) {
		TreeItem<FXFamilyModelElement> varArtefactItem = familyTreeBuilder.createTreeItem(varArtefact);
		for (EObject artefact : varArtefact.getArtefacts()) {
			TreeItem<FXFamilyModelElement> artefactItem = artefactTreeBuilder.createTreeItem(artefact);
			varArtefactItem.getChildren().add(artefactItem);
		}		
		return varArtefactItem;
	}
	
	public TreeView<FXFamilyModelElement> getTree() {
		return tree;
	}

	public void setTree(TreeView<FXFamilyModelElement> tree) {
		this.tree = tree;
	}
}
