package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.core.util.emf.EMFUtil;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ArtefactFilter;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.FXTreeBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements.FXFamilyModelElement;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FXArtefactTree {
	
	private TreeView<FXFamilyModelElement> tree;
	private FXTreeBuilder familyTreeBuilder;
	private FXTreeBuilder artefactTreeBuilder;
	private ArtefactFilter artefactFilter;

	public FXArtefactTree(FXTreeBuilder FamilyTreeBuilder, FXTreeBuilder artefactTreeBuilder, ArtefactFilter artefactFilter) {
		this.familyTreeBuilder = FamilyTreeBuilder;
		this.artefactTreeBuilder = artefactTreeBuilder;
		this.artefactFilter = artefactFilter;
	}
	
	/**
	 * This method initializes this view and creates a TreeViewer from the given FamilyModel.
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

	/**
	 * Creates an item tree structure attached to a variant tree item.
	 * The tree structure is created by recursively processing the root artefact's contents.
	 * The contents of an EObject are the EObject instances within the containment relation. 
	 * 
	 * @param variantItem the item acting as root item for the created tree
	 * @param rootArtefact the EObject whose containment structure is used to create the tree
	 */
	public void createArtefactItemTree(TreeItem<FXFamilyModelElement> variantItem, EObject rootArtefact) {
		EMFUtil.applyOnContainmentStructure(variantItem, rootArtefact, (parent, eobject) -> {
			if (artefactFilter.apply(eobject)) {
				TreeItem<FXFamilyModelElement> artifactItem = artefactTreeBuilder.createTreeItem(eobject);
				parent.getChildren().add(artifactItem);				
				return artifactItem;
			} else { // skip current eobject based on filter
				return parent; 
			}
			
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
			createArtefactItemTree(varArtefactItem, artefact);
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
