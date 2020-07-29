package de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.stringtable.FaMoFileTable;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.components.FamilyModelToolBar;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.elements.FXFamilyModelElement;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.menu.ShowDetailsItem;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.template.AbstractFamilyModelView;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import familyModel.FamilyModel;
import familyModel.FamilyModelFactory;
import familyModel.VariabilityCategory;
import familyModel.VariabilityGroup;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class FamilyModelView extends AbstractFamilyModelView {
	
	private FamilyModelFactory familyModelFactory = FamilyModelFactory.eINSTANCE;

	private BorderPane root;
	private Scene scene;
	private FXCanvas parent;

	private TreeView<FXFamilyModelElement> treeView;

	public FamilyModelView(FXCanvas parent, ServiceContainer services) {
		super(services);
		this.parent = parent;
		showFamilyModel(createModel());
	}
	
	/**
	 * This method initialize the TreeViewer and shows them.
	 */
	public void showFamilyModel(FamilyModel model) {
		root = new BorderPane();
        scene = new Scene(root);
        scene.getStylesheets().add("css/default_theme.css");
        root.setTop(new FamilyModelToolBar(services, this));
        treeView = createFamilyModelTree(model);
        root.setCenter(treeView);
        
        // add context menu 
        ContextMenu cm = new ContextMenu(
        		new ShowDetailsItem(this, services)
        );
        treeView.setContextMenu(cm);
        
        parent.setScene(scene);
	}
	
	/**
	 * Test method that creates a family model
	 * @return
	 */
	public FamilyModel createModel() {
		FamilyModel model = FamilyModelFactory.eINSTANCE.createFamilyModel();
		
		VariabilityGroup groupMandatory = FamilyModelFactory.eINSTANCE.createVariabilityGroup();
		groupMandatory.setVariability(VariabilityCategory.MANDATORY);
		groupMandatory.setGroupName("Artifact A");
		
		VariabilityGroup groupOption = familyModelFactory.createVariabilityGroup();
		groupOption.setVariability(VariabilityCategory.OPTIONAL);
		groupOption.setGroupName("Artifact B");
		
		VariabilityGroup groupAlternative = familyModelFactory.createVariabilityGroup();
		groupAlternative.setVariability(VariabilityCategory.ALTERNATIVE);
		groupAlternative.setGroupName("Collection of Artifacts");
		
		//sub groups
		VariabilityGroup subGroup1 = familyModelFactory.createVariabilityGroup();
		subGroup1.setVariability(VariabilityCategory.ALTERNATIVE);
		subGroup1.setGroupName("Artifact C");
		groupAlternative.getSubGroups().add(subGroup1);

		//sub groups
		VariabilityGroup subGroup2 = familyModelFactory.createVariabilityGroup();
		subGroup2.setVariability(VariabilityCategory.ALTERNATIVE);
		subGroup2.setGroupName("Artifact D");
		groupAlternative.getSubGroups().add(subGroup2);

	
		model.getVariabilyGroups().add(groupMandatory);
		model.getVariabilyGroups().add(groupOption);
		model.getVariabilyGroups().add(groupAlternative);

		return model;
	}
	
	
	/**
	 * This method initializes this view and creates a TreeViewer out of the given FamilyModel.
	 */
	private TreeView<FXFamilyModelElement> createFamilyModelTree(FamilyModel model) {
		//root item that contains all groups
		TreeItem<FXFamilyModelElement> root = createFamilyModel(model);
		this.tree = new TreeView<FXFamilyModelElement>(root);
		
		this.tree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> {
			services.eventBroker.send(E4CompareEventTable.SHOW_DETAIL_EVENT, newValue.getValue().getElement());
		});
		
		return this.tree;
	}
	
	/**
	 * This model transforms a FamilyModel into his TreeItem representation.
	 */
	private TreeItem<FXFamilyModelElement> createFamilyModel(FamilyModel model) {
		TreeItem<FXFamilyModelElement> root = createTreeItem(model, FaMoFileTable.FV_ROOT_16);
		for(VariabilityGroup group : model.getVariabilyGroups()) {
			TreeItem<FXFamilyModelElement> treeItem = createFamilyModelTreeItem(group);
			if(!group.getSubGroups().isEmpty()) {
				createGroupsRec(treeItem, group);
			}
			root.getChildren().add(treeItem);
		}
		return root;
	}
	
	/**
	 * This method creates TreeItems that represents VariabilityGroup recursively.
	 */
	private void createGroupsRec(TreeItem<FXFamilyModelElement> parent, VariabilityGroup group) {
		for(VariabilityGroup subGroup : group.getSubGroups()) {
			TreeItem<FXFamilyModelElement> treeItem = createFamilyModelTreeItem(subGroup);

			if(!group.getSubGroups().isEmpty()) {
				createGroupsRec(treeItem, subGroup);
			}
			parent.getChildren().add(treeItem);
		}
	}
	
	public BorderPane getRootPane() {
		return root;
	}
	
	public FamilyModel getFamilyModel() {
		if(tree.getRoot().getValue().getElement() instanceof FamilyModel) {
			return (FamilyModel) tree.getRoot().getValue().getElement();
		} else {
			return null;
		}
	}
}
