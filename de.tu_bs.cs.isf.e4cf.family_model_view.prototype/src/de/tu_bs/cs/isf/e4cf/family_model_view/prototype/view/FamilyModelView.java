package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.FXToolbar;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.ArtefactFilter;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.FamilyModelViewController;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.GenericFamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.FXArtefactTree;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components.FXFamilyModelTree;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements.FXFamilyModelElement;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.menu.ShowDetailsItem;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.toolbar.LoadHandler;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.toolbar.SaveHandler;
import javafx.embed.swt.FXCanvas;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FamilyModelView {
	
	private static final int TOOLBAR_CHILD_MARGIN = 10;
	private static final int TOOLBAR_WIDTH = 20;

	private static final double ARTEFACT_PANE_WIDTH_RATIO = 1.0 / 3.0;
	
	private FXCanvas fxCanvas;
	private Scene scene;
	private BorderPane familyModelPane;

	private FXFamilyModelTree familyModelTree;
	private FXArtefactTree artefactTree;
	
	private FXTreeBuilder familyTreeBuilder;
	private FXTreeBuilder artefactTreeBuilder;
	private ArtefactFilter artefactFilter;
	private GenericFamilyModel familyModel;
	
	private FamilyModelViewController fmvController;

	public FamilyModelView(Composite parent, FXTreeBuilder fmTreeBuilder, FXTreeBuilder artefactTreeBuilder, ArtefactFilter artefactFilter, FamilyModelViewController fmvController) {
		this.familyTreeBuilder = fmTreeBuilder;
		this.artefactTreeBuilder = artefactTreeBuilder;
		this.fmvController = fmvController;
		this.artefactFilter = artefactFilter;

		this.fxCanvas = new FXCanvas(parent, SWT.NONE);
		this.fxCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
	
	/**
	 * This method initialize the TreeViewer and shows them.
	 * @param canvas 
	 * @param model
	 */
	public void showFamilyModel(GenericFamilyModel familyModel) {
		this.familyModel = familyModel;
		familyModelPane = new BorderPane();
		
//        scene.getStylesheets().add("css/default_theme.css");
		
		// build the family model view
		VBox familyModelBox = new VBox(10);
		familyModelBox.setFillWidth(true);
		
		Label familyModelLabel = new Label("Family Model View");	
		familyModelLabel.setAlignment(Pos.CENTER);
		familyModelLabel.prefWidthProperty().bind(familyModelBox.widthProperty());
		
		familyModelTree = new FXFamilyModelTree(familyTreeBuilder);
		familyModelTree.createFamilyModelTree(familyModel.getInternalFamilyModel());
		
		familyModelBox.getChildren().addAll(familyModelLabel, familyModelTree.getTree());
		familyModelPane.setCenter(familyModelBox);
		
		BorderPane.setMargin(familyModelBox, new Insets(10, 5, 10, 5));
        
		// build the artefact view
		artefactTree = new FXArtefactTree(familyTreeBuilder, artefactTreeBuilder, artefactFilter);

		// set a fixed ratio between family model and artefact pane
        familyModelPane.widthProperty().addListener((observable, oldValue, newValue) -> {
        	changeArtefactPaneWidth(ARTEFACT_PANE_WIDTH_RATIO);
        });
        
        // add toolbar
        VBox toolbarBox = new VBox(10);
        
        FXToolbar toolbar = new FXToolbar(TOOLBAR_WIDTH, TOOLBAR_CHILD_MARGIN);
        Button saveFamilyModelButton = new Button("Save");
        saveFamilyModelButton.setOnMouseClicked(new SaveHandler(fmvController));
        Button loadFamilyModelButton = new Button("Load");
        loadFamilyModelButton.setOnMouseClicked(new LoadHandler(fmvController));
        
        Separator separator = new Separator(Orientation.VERTICAL);
        toolbar.addItems(saveFamilyModelButton, loadFamilyModelButton, separator);
        VBox.setMargin(toolbar.getHbox(), new Insets(0, 10, 0, 10));
        
        Separator horSeparator = new Separator(Orientation.HORIZONTAL);
        horSeparator.prefWidthProperty().bind(toolbarBox.widthProperty());
        
        toolbarBox.getChildren().addAll(toolbar.getHbox(), horSeparator);
        familyModelPane.setTop(toolbarBox);
		BorderPane.setMargin(toolbarBox, new Insets(10, 0 ,10, 0));
                
        // On item selection, show item in the artefact tree view
        familyModelTree.getTree().getSelectionModel().selectedItemProperty().addListener((obvservable, oldValue, newValue) -> {
        	if (newValue == null) {
        		familyModelPane.setRight(null);
        		return;
        	}
        	
        	FXFamilyModelElement element = newValue.getValue();
        	if (element.get() instanceof EObject) {
        		if (element.get() instanceof FamilyModel) {
        			artefactTree.createFamilyModelVariantTree((FamilyModel) element.get());
        		} else if (element.get() instanceof VariationPoint) {
        			artefactTree.createArtefactTree((VariationPoint) element.get());
        		} else {
        			throw new RuntimeException("The element shouldn't have types aside from FamilyModel and VariationPoint: "
        					+"type: \""+element.get().getClass().getSimpleName()+"\"");
        		}
        		showArtifactPane(artefactTree.getTree());
        		changeArtefactPaneWidth(ARTEFACT_PANE_WIDTH_RATIO);
        	}
        });
        
        // add context menu 
        ContextMenu cm = new ContextMenu(
        		new ShowDetailsItem(this, fmvController.getServices())
        );
        familyModelTree.getTree().setContextMenu(cm);
        
        scene = new Scene(familyModelPane);
        fxCanvas.setScene(scene);
        
        fxCanvas.layout();
	}

	/**
	 * Changes the artefact side view's width
	 * 
	 * @param ratioToView the view ratio between 0 and 1 exclusively
	 */
	private void changeArtefactPaneWidth(double ratioToView) {
		if (ratioToView <= 0 || ratioToView >= 1) {
			return;
		}
		
		if (familyModelPane.getRight() instanceof VBox) {
			VBox artefactBox = (VBox) familyModelPane.getRight();
			artefactBox.setPrefWidth(familyModelPane.getWidth() * ratioToView);	
		}			
	}
	
	public void showArtifactPane(TreeView<FXFamilyModelElement> tree) {
		if (tree != null && familyModelPane.getRight() != tree) {
			VBox artefactBox = new VBox(10);
			
			Label artefactPaneLabel = new Label("Artefact View");
			artefactPaneLabel.setAlignment(Pos.CENTER);
			artefactPaneLabel.prefWidthProperty().bind(artefactBox.widthProperty());
			
			artefactBox.getChildren().addAll(artefactPaneLabel, tree);
			familyModelPane.setRight(artefactBox);

			BorderPane.setMargin(artefactBox, new Insets(10));
		}
	}
	
	public GenericFamilyModel getFamilyModel() {
		return familyModel;
	}
	
	public BorderPane getRootNode() {
		return familyModelPane;
	}

	public FXFamilyModelTree getFamilyModelTree() {
		return familyModelTree;
	}

	public void setFamilyModelTree(FXFamilyModelTree familyModelTree) {
		this.familyModelTree = familyModelTree;
	}

	public FXArtefactTree getArtefactTree() {
		return artefactTree;
	}

	public void setArtefactTree(FXArtefactTree artefactTree) {
		this.artefactTree = artefactTree;
	}
}
