package de.tu_bs.cs.isf.e4cf.featuremodel.core.view;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramFactory;
import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.GraphicalFeature;
import FeatureDiagram.impl.FeatureDiagramFactoryImpl;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.DragHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.KeyTranslateHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.PrimaryMouseButtonClickedHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.ResetHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.SelectionAreaHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.ZoomHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.themes.DefaultTheme;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureDiagramSerialiazer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation.AnimationMap;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation.DashedBorderAnimation;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.DiagramLogger;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.DiagramLoggerConsts;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.DiagramLoggerFactory;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.replayer.ModificationSetReplayer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleDecsionDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleNoticeDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper.FeatureModelEvaluator;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacemantConsts;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgoFactory;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgorithm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.toolbar.FeatureModelEditorToolbar;
import javafx.embed.swt.FXCanvas;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
/**
 * This class represents the view of the MVC implementation it represents the FeatureDiagram graphical.
 * @author {Kamil Rosiak, Alexander Schlie}
 *
 */
public class FeatureModelEditorView {
	final double SCALE_DELTA = 1.1;
	public double mouseX = 0;
	public double mouseY = 0;
	private ServiceContainer services;
	private Pane root;
	private ResetHandler resetHandler;
	private DragHandler dragHandler;
	private SelectionAreaHandler selectionHandler;
	private PrimaryMouseButtonClickedHandler primaryMouseClickedHandler;
	private ZoomHandler zoomHander;
	private Scene scene;
	private Rectangle selectionRectangle;
	private FeatureDiagramm currentModel;
	private FXGraphicalFeature currentFeature;
	private List<FXGraphicalFeature> selectedFeatures;

	
	
	private List<FXGraphicalFeature> featureList;
	private Map<FXGraphicalFeature,Line> featureLineMap;
	
	private AnimationMap labelBorderAnimationMap;
	
	public FeatureModelEditorView(FXCanvas canvas, ServiceContainer services) {
		this.services = services;
		canvas.setScene(createScene());	
	}
	
	/**
	 * This method creates the selection rectangle.
	 */
	private void createSelectionRectangle() {
		this.selectionRectangle = new Rectangle(20, 20, Color.TRANSPARENT);
        root.getChildren().add(selectionRectangle);
        selectionRectangle.setDisable(true);
        selectionRectangle.getStrokeDashArray().addAll(10d, 5d, 10d);
	}
	
	/**
	 * This method initiates the data structure of the FeatureDiagram Editor
	 */
	private void initDataStructure() {
		this.featureList = new ArrayList<FXGraphicalFeature>();
		this.featureLineMap = new HashMap<FXGraphicalFeature,Line>();
		this.labelBorderAnimationMap = new AnimationMap();
        this.selectedFeatures = new ArrayList<FXGraphicalFeature>();
	}
	
	/**
	 * This method creates the Scene and adds all Pane and Listener to it.
	 */
	private Scene createScene() {
		initDataStructure();
        root = new Pane();

		StackPane gesturePane = new StackPane(root);
		gesturePane.setStyle("-fx-background-color: white;");
        root.setStyle("-fx-background-color: white;"); 
        
        //Creating and adding the mouse handler that allows zooming in and out with the mouse wheel.
        zoomHander = new ZoomHandler(root);
        gesturePane.addEventHandler(ScrollEvent.ANY, zoomHander);

        scene = new Scene(gesturePane);
        
        //creating the selection rectangle
        createSelectionRectangle();
        
        //set theme from preferences 
        setTheme(PreferencesUtil.getValueWithDefault(FDStringTable.BUNDLE_NAME, FDStringTable.FME_THEME_KEY, DefaultTheme.DEFAULT_THEME).getStringValue());
        scene.addEventHandler(KeyEvent.ANY, new KeyTranslateHandler(root, 10d));
        
        
        primaryMouseClickedHandler = new PrimaryMouseButtonClickedHandler(services);
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, primaryMouseClickedHandler);
        
        selectionHandler = new SelectionAreaHandler(root, featureList, selectionRectangle, primaryMouseClickedHandler, services);
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, selectionHandler);

        dragHandler = new DragHandler(root);
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);

        resetHandler = new ResetHandler(dragHandler, selectionHandler);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, resetHandler);
        dragHandler.resetLastPosition();

        gesturePane.setAlignment(Pos.TOP_CENTER);
        gesturePane.getChildren().add(new FeatureModelEditorToolbar(services, this));
        
        //creating an empty feature diagram
		createNewFeatureDiagram();
		
        return scene;
	}
	
	public void setTheme(String cssLocation) {
		scene.getStylesheets().setAll(cssLocation);
	}
	
	/**
	 * This method replaces all elements of the current loaded feature diagram
	 */
	public void formatDiagram(boolean askToSave) {
		PlacementAlgorithm placement = PlacementAlgoFactory.getPlacementAlgorithm(PlacemantConsts.ABEGO_PLACEMENT);
		placement.format(currentModel);
		// Reset the translate offset so that large feature diagrams do not
		// disappear after formatting
		root.setTranslateX(0d);
		root.setTranslateY(0d);
		loadFeatureDiagram(currentModel, askToSave);
	}
		
	/**
	 * This method creates a new FeatureDiagram and adds the root to FeatureDiagram.
	 */
	public void createNewFeatureDiagram() {
		clearAll();
		currentModel = FeatureDiagramFactoryImpl.eINSTANCE.createFeatureDiagramm();
		Feature root = createFeature("Root", true);
		root.getGraphicalfeature().setX(this.root.getWidth()/2);
		root.getGraphicalfeature().setY(this.root.getMaxWidth()/2);
		currentModel.setRoot(root);
		addFeature(root ,this.getRootPane().getWidth()/2,50d);
	}
	
	/**
	 * This method loads a FeatureDiagram.
	 */
	public void loadFeatureDiagram(FeatureDiagramm model, boolean isSave) {
		//Ask for saving before deleting
		if(isSave) {
			askToSave();
		}
		clearAll();
		
		currentModel = model;
		FXGraphicalFeature fxRoot = addFeature(model.getRoot());
		postProcessFeatureVisibility(fxRoot);
		//add all Feature to front so that no overlapping exists.
		for(FXGraphicalFeature fxFeature : featureList) {
			fxFeature.toFront();
		}
		
		root.setTranslateX(0d);
		root.setTranslateY(0d);
	}
	
	private void postProcessFeatureVisibility(FXGraphicalFeature fxRoot) {
		for (FXGraphicalFeature fxChild : fxRoot.getChildFeatures()) {
			if (fxChild.getFeature().isHidden()) {
				postProcessRecursivelyHideSubfeatures(fxChild);
			} else {
				postProcessFeatureVisibility(fxChild);
			}
		}
	}

	public void postProcessRecursivelyHideSubfeatures(FXGraphicalFeature graphicalFeature) {
		for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
			changeFeatureVisibility(fxChild, true);			
			postProcessRecursivelyHideSubfeatures(fxChild);				
		}			
	}
	
	/**
	 * This method creates a dialog and checks if the user would like to save the current diagram.
	 */
	public void askToSave() {
		boolean isCurrentModel = RCPMessageProvider.questionMessage("FeatureDiagramEditor", "Would you like to save the current Feature Model");
		if(isCurrentModel) {
			saveFeatureDiagram();
		}
	}
	
	/**
	 * This method clears the FeatureDiagramEditor
	 */
	public void clearAll() {
		root.getChildren().clear();
		root.getChildren().add(selectionRectangle);
		featureLineMap.clear();
		featureList.clear();
	}
	
	/**
	 * This method saves the current featureModel to into workspace.
	 */
	public void saveFeatureDiagram() {
		FileTreeElement root = services.workspaceFileSystem.getWorkspaceDirectory();
		Path rootPath = FileHandlingUtility.getPath(root);
		Path projectPath = rootPath.resolve("");
		FeatureDiagramSerialiazer.save(currentModel, projectPath.resolve(E4CStringTable.FEATURE_MODEL_DIRECTORY).toUri() + currentModel.getRoot().getName()+FDStringTable.FD_FILE_ENDING);
	}
	
	 /**
	  * This method sets the current mouse position (relative to the scene).
	  */
	 public void setMousePosition(double x, double y) {
		 this.mouseX = x;
		 this.mouseY = y;
	 }

	 /**
	  * This method adds a feature to the position of this graphicalFeature with his children.
	  */
	 public FXGraphicalFeature addFeature(Feature feature) {
		 // log feature added
	 	services.eventBroker.send(FDEventTable.LOGGER_ADD_FEATURE, feature);
	 	
	 	if(feature.getGraphicalfeature() != null) {
		 	feature.getGraphicalfeature().setX(feature.getGraphicalfeature().getX());
		 	feature.getGraphicalfeature().setY(feature.getGraphicalfeature().getY());
	 	}
	 	FXGraphicalFeature fxGraFeature = new FXGraphicalFeature(this,feature, services);
	 	
	 	if (feature.isAbstract()) {
	 		fxGraFeature.getFeatureNameLabel().getStyleClass().add("abstractFeature");
	 	}
	 	
		root.getChildren().add(fxGraFeature); 
	    featureList.add(fxGraFeature);
	    
	    for(Feature childFeature : feature.getChildren()) {
	    	FXGraphicalFeature fxChild = addFeature(childFeature);
	    	fxGraFeature.addChildFeature(fxChild);
	    	fxChild.setParentFxFeature(fxGraFeature);
	    	createLineToChildren(fxGraFeature, fxChild);
	    	
	    }	
	    
	    if (fxGraFeature.getFeature().isAlternative()) {
	    	fxGraFeature.setGroupVariability_ALTERNATIVE();
	    } else if (fxGraFeature.getFeature().isOr()) {
	    	fxGraFeature.setGroupVariability_OR();
	    }
	    
		return fxGraFeature;
	 }
	 /**
	  * This method adds a feature to FeatureModelEditor to given coordinates.
	  */
	 public FXGraphicalFeature addFeature(Feature feature, double xPos, double yPos) {
		 	feature.getGraphicalfeature().setX(xPos);
		 	feature.getGraphicalfeature().setY(yPos);
		 	return addFeature(feature);
	 } 
	 
	 /**
	  * This method adds a feature to the model editor at the current mouse position .
	  */
	 public FXGraphicalFeature addFeatureToMousePosition(Feature feature) {
		 	feature.getGraphicalfeature().setX(mouseX);
		 	feature.getGraphicalfeature().setY(mouseY);
			return addFeature(feature);
	 } 
	 
	 /**
	  * This method adds a child to given parent and returns the new FXFeature.
	  */
	 public FXGraphicalFeature addFeatureBelow(FXGraphicalFeature parent) {
		 services.eventBroker.send(FDEventTable.LOGGER_ADD_FEATURE_BELOW, parent);
		 //create new feature and add under the parent 
		 double xPos = parent.getFeature().getGraphicalfeature().getX();
		 double yPos = parent.getFeature().getGraphicalfeature().getY() + parent.getHeight()*2;
		 Feature newFeature = createFeatureWithPosition("NewFeature_"+currentModel.getIdentifierIncrement(), false, xPos, yPos);
		 //add the new feature to model and set the parent feature
		 parent.getFeature().getChildren().add(newFeature);
		 newFeature.setParent(parent.getFeature());
		 
		 //create graphical feature and set parent
		 FXGraphicalFeature newGraFeature = new FXGraphicalFeature(this, newFeature, services);
		 newGraFeature.setParentFxFeature(parent);
		 parent.addChildFeatureFormated(newGraFeature);
		 
		 //add graphical feature to scene
		 root.getChildren().addAll(newGraFeature);
		 
		 //add feature to featureList
		 featureList.add(newGraFeature);
		 
		 //connect new feature with parent
		 createLineToChildren(parent, newGraFeature);

		 // set the current feature, necessary for feature split
		 setCurrentFeature(newGraFeature);
		 
		 return newGraFeature;
	 }
	 
	 /**
	  * This method adds a parent to given child and returns the new FXFeature.
	  */
	 public FXGraphicalFeature addFeatureAbove(FXGraphicalFeature fxFeature) {
		 services.eventBroker.send(FDEventTable.LOGGER_ADD_FEATURE_ABOVE, fxFeature);
		 
		 Feature feature = fxFeature.getFeature();
		
		 // If adding a feature above the current root feature
		 if (feature.getParent() == null) {
			 return  addFeatureAboveRoot(fxFeature);
		 }
		 // else:
		 
		 Feature parentFeature = feature.getParent();
		 FXGraphicalFeature fxParentFeature = fxFeature.getParentFxFeature();
		 
		 
		 //position under the chosen parent
		 double xPosChild = feature.getGraphicalfeature().getX();
		 double yPosChild = feature.getGraphicalfeature().getY() + fxFeature.getHeight()*2;

		 double xPosParent = parentFeature.getGraphicalfeature().getX();
		 double yPosParent = parentFeature.getGraphicalfeature().getY() + parentFeature.getGraphicalfeature().getHeight()*2;
		 
		 // Set the position of the new feature to be in the middle of both the x- and the y-axis
		 // of the former parent and the current child
		 double xPosNewFeature = (Math.max(xPosChild, xPosParent) - Math.min(xPosChild, xPosParent)) / 2 + Math.min(xPosChild, xPosParent);
		 double yPosNewFeature = (Math.max(yPosChild, yPosParent) - Math.min(yPosChild, yPosParent)) / 2 + Math.min(yPosChild, yPosParent);
		 
		 
		 //create new feature and add above the child
		 Feature newFeature = createFeatureWithPosition("NewFeature", false, xPosNewFeature, yPosNewFeature);

		 // Reset the parent-child relations
		 
		 parentFeature.getChildren().remove(feature);
		 feature.setParent(newFeature);
		 newFeature.getChildren().add(feature);
		 newFeature.setParent(parentFeature);		
		 parentFeature.getChildren().add(newFeature);
		 
		 //create graphical feature and set parent
		 FXGraphicalFeature newGraFeature = new FXGraphicalFeature(this, newFeature, services);
		 
		 // Reset the parent-child relations for the graphical FXfeatures
		 fxParentFeature.getChildFeatures().remove(fxFeature);
		 fxFeature.setParentFxFeature(newGraFeature);
		 newGraFeature.getChildFeatures().add(fxFeature);
		 newGraFeature.setParentFxFeature(fxParentFeature);
		 fxParentFeature.getChildFeatures().add(newGraFeature);
		 newGraFeature.minHeight(27.0);
		 
		 newGraFeature.addChildFeatureFormated(fxFeature);
		 fxParentFeature.addChildFeatureFormated(newGraFeature);			
		 
		 
		 //add graphical feature to scene
		 root.getChildren().addAll(newGraFeature);
		 
		 //add feature to featureList
		 featureList.add(newGraFeature);
		 
		 // remove old lines
		 removeLine(fxFeature);
		 
		 //connect new feature with parent
		 createLineToChildren(fxParentFeature, newGraFeature);			
		 createLineToChildren(newGraFeature, fxFeature);

		 setCurrentFeature(newGraFeature);
	
		 return newGraFeature;
	 }
	 
	 /**
	  * Adds a new feature above the current root feature, and thus, replaces the root with a new feature
	  * @param fxfeature The current root feature
	  * @return The new root feature, holding the former root feature "fxfeature" as a child
	  */
	 private FXGraphicalFeature addFeatureAboveRoot(FXGraphicalFeature fxfeature) {
		 Feature formerRoot = fxfeature.getFeature();
		 double xPos = formerRoot.getGraphicalfeature().getX();
		 double yPos = formerRoot.getGraphicalfeature().getY()-30;
		 
		 //create new feature and add above the child
		 Feature newRoot = createFeature("NewFeature", true);
		 newRoot.getGraphicalfeature().setX(xPos);
		 newRoot.getGraphicalfeature().setY(yPos);
		 
		 // set parent-child relations
		 formerRoot.setParent(newRoot);
		 newRoot.getChildren().add(formerRoot);
		 
		 //create graphical feature and set parent
		 FXGraphicalFeature newGraRoot = new FXGraphicalFeature(this, newRoot, services);
		 fxfeature.setParentFxFeature(newGraRoot);
		 newGraRoot.getChildFeatures().add(fxfeature);
		 
		 newGraRoot.addChildFeatureFormated(fxfeature);
		 
		 //add graphical feature to scene
		 root.getChildren().addAll(newGraRoot);
		 
		 //add feature to featureList
		 featureList.add(newGraRoot);
		 
		 //connect new feature with parent
		 createLineToChildren(newGraRoot, fxfeature);	
		 
		 // finally, reset the root of the feature diagram
		 currentModel.setRoot(newRoot);
		 
		 // Notify the logger
		 services.eventBroker.send(FDEventTable.LOGGER_ADD_FEATURE, newGraRoot);
		 
		 setCurrentFeature(newGraRoot);
		 
		return newGraRoot;
	}

	/**
	  * Creating the connections between child and parent and binding property's.
	  */
	 private void createLineToChildren(FXGraphicalFeature parent, FXGraphicalFeature child) {
		 final Line line = new Line();
		 
		 //initial bind 
		 line.startXProperty().bind(parent.getXPos().add(parent.getWidth()/2));
		 line.startYProperty().bind(parent.getYPos().add(parent.getHeight() - parent.getLowerConnector().getRadiusY()));
		 
		 line.endYProperty().bind(child.translateYProperty());
		 line.endXProperty().bind(child.translateXProperty().add(child.widthProperty().doubleValue()/2));
		 
		 //after update size
		 parent.widthProperty().addListener(e -> {
			 line.startXProperty().unbind();
			 line.startXProperty().bind(parent.getXPos().add(parent.getWidth()/2));
		 });
		 
		 parent.heightProperty().addListener(e -> {
			 line.startYProperty().unbind();
			 line.startYProperty().bind(parent.getYPos().add(parent.getHeight() - parent.getLowerConnector().getRadiusY()));
		 });
		 
		 //if height changes bind with new height.
		 child.heightProperty().addListener(e-> {
			 line.endYProperty().unbind();
			 line.endYProperty().bind(child.translateYProperty());
		 });
		 
		 //if size changes bind with new width.
		 child.widthProperty().addListener(e -> {
			 line.endXProperty().unbind();
			 line.endXProperty().bind(child.translateXProperty().add(child.widthProperty().doubleValue()/2));
		 });
	
		 featureLineMap.put(child, line);
		 root.getChildren().add(line);	 
	 }
	 
	 /**
	  * Removes a connection between a child feature and its' parent.
	  */
	 private void removeLine(FXGraphicalFeature child) {
		 
		 try {
			 Line line = featureLineMap.get(child);
			 line.startXProperty().unbind();
			 line.startYProperty().unbind();
			 line.endXProperty().unbind();
			 line.endYProperty().unbind();
			 root.getChildren().remove(line);
			 featureLineMap.remove(child);
			 
		 } catch (Exception e) {
			 System.out.println("Error code 'x8y-11f': I was unable to remove line in Feature Model for feature: "+child.getFeature().getName());
		 } 
	 }
	 
	 
	 /**
	  * This method creates a new feature. With default name
	  */
	 public Feature createFeature(String featureName, boolean isRoot) {
     	Feature feature = FeatureDiagramFactoryImpl.eINSTANCE.createFeature();
     	feature.setName(featureName);
     	feature.setMandatory(isRoot ? true : false);
     	feature.setAlternative(false);
     	feature.setOr(false);
     	feature.setAbstract(false);
     	currentModel.setIdentifierIncrement(currentModel.getIdentifierIncrement() + 1);
     	feature.setIdentifier(currentModel.getIdentifierIncrement());
     	GraphicalFeature graphicalFeature = FeatureDiagramFactory.eINSTANCE.createGraphicalFeature();
     	feature.setGraphicalfeature(graphicalFeature);
     	
     	ArtifactReference artifactReference = FeatureDiagramFactoryImpl.eINSTANCE.createArtifactReference();
     	artifactReference.setArtifactClearName(feature.getName());
     	feature.getArtifactReferences().add(artifactReference);
     	
     	return feature;
	 }
	 
	 /**
	  * Creates a feature with given name and sets the x and y position
	  */
	 public Feature createFeatureWithPosition(String featureName, boolean isRoot, double x, double y) {
		 Feature feature = createFeature(featureName, isRoot);
		 feature.getGraphicalfeature().setX(x);
		 feature.getGraphicalfeature().setX(y);
		 return feature;
	 }
	 
	public Pane getRootPane() {
		return root;
	}
	
	/**
	 * returns a list that contains all FXGraphicalFeature.
	 */
	public List<FXGraphicalFeature> getFeatureList() {
		return featureList;
	}
	
	/**
	 * This method adds a FXGraphicalFeature to list.
	 * @param graphicalFeature
	 */
	public void addFXGraphicalFeatureToList(FXGraphicalFeature graphicalFeature) {
		this.featureList.add(graphicalFeature);
	}
	
	/**
	 * Remove feature from diagram.
	 */
	public void removeFeature(FXGraphicalFeature graphicalFeature, boolean showDialog, boolean triggeredByTrunkDelete, boolean sendLoggerEvents) {
		
		if (currentModel.getRoot().equals(graphicalFeature.getFeature())){
			new FMESimpleNoticeDialog("Error", "Root feature can not be deleted");
			return;
		}
		
		boolean decision = false;
		if (showDialog) {
			decision = new FMESimpleDecsionDialog("Remove Feature", "Are you sure").show();		
		} 
		if (decision || !showDialog) {
			this.root.getChildren().remove(featureLineMap.get(graphicalFeature));
			this.root.getChildren().remove(graphicalFeature);
			removeLine(graphicalFeature);
			this.featureList.remove(graphicalFeature);
			
			if (sendLoggerEvents) {
				services.eventBroker.send(FDEventTable.LOGGER_REMOVE_FEATURE, graphicalFeature);				
			}
			
			if (graphicalFeature.getParentFxFeature() != null) {
				Feature parent = graphicalFeature.getParentFxFeature().getFeature();
				parent.getChildren().remove(graphicalFeature.getFeature());
				graphicalFeature.getParentFxFeature().getChildFeatures().remove(graphicalFeature);
				
				// remove from selected features if contained
				selectedFeatures.remove(graphicalFeature);
				
				// only reset line if we do not delete the entire trunk anyways
				if (!triggeredByTrunkDelete && !graphicalFeature.getChildFeatures().isEmpty()) {
					for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
						reconnectFeatures(graphicalFeature, fxChild);
					}				
				}
			}
		}
	}
	
	/**
	 * todo comment
	 * @param parent
	 * @param child
	 */
	private void reconnectFeatures(FXGraphicalFeature parent, FXGraphicalFeature child) {
		services.eventBroker.send(FDEventTable.LOGGER_REMOVED_LINE_TO_PARENT_FEATURE, parent);
		child.setParentFxFeature(parent.getParentFxFeature());	
		parent.getParentFxFeature().getChildFeatures().add(child);
		child.getFeature().setParent(parent.getParentFxFeature().getFeature());
		parent.getParentFxFeature().getFeature().getChildren().add(child.getFeature());
		removeLine(child);
		createLineToChildren(child.getParentFxFeature(), child);
		services.eventBroker.send(FDEventTable.LOGGER_RESET_LINE_TO_PARENT_FEATURE, child);
	}
	
	/*
	 * Sets a feature to be Abstract or Concrete, depending on its current state
	 */
	public void setFeatureAbstract(FXGraphicalFeature graphicalFeature) {
		Feature feature = graphicalFeature.getFeature();
		if (feature.isAbstract()) {
			feature.setAbstract(false);
			graphicalFeature.getFeatureNameLabel().getStyleClass().remove("abstractFeature");
		} else {
			feature.setAbstract(true);
			graphicalFeature.getFeatureNameLabel().getStyleClass().add("abstractFeature");
		}
	}
	
	/**
	 * TODO comment
	 */
	public void changeSubfeaturesVisibility(FXGraphicalFeature graphicalFeature) {
		services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_SUBFEATURES_VISIBILITY, graphicalFeature);
		if (graphicalFeature.getFeature().isHidden()) {
			graphicalFeature.getFeature().setHidden(false);
			for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
				recursivelyShowSubfeatures(fxChild);
			}
		} else {
			graphicalFeature.getFeature().setHidden(true);
			for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
				recursivelyHideSubfeatures(fxChild);
			}
		}
		
	}

	/**
	 * RECURSIVELY SHOWS SUBFEATURES
	 */
	public void recursivelyShowSubfeatures(FXGraphicalFeature graphicalFeature) {
		if (!graphicalFeature.getFeature().isHidden()) {
			for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
				recursivelyShowSubfeatures(fxChild);
			}	
		}		
		changeFeatureVisibility(graphicalFeature, false);
	}
	
	
	/**
	 * RECURSIVELY HIDES SUBFEATURES
	 */
	public void recursivelyHideSubfeatures(FXGraphicalFeature graphicalFeature) {
		if (!graphicalFeature.getFeature().isHidden()) {
			for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
				recursivelyHideSubfeatures(fxChild);				
			}			
		}
		if (graphicalFeature.isVisible()) {
			changeFeatureVisibility(graphicalFeature, true);						
		}
	}
	
	/**
	 * HIDES or SHOWS A FEATURE AND HENCE, SETS IT INVISIBLE
	 */
	public void changeFeatureVisibility(FXGraphicalFeature graphicalFeature, boolean hideThisFeautre) {
		graphicalFeature.setVisible(!hideThisFeautre);
		featureLineMap.get(graphicalFeature).setVisible(!hideThisFeautre);		
    	services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VISIBILITY, graphicalFeature);
	}
	
	/**
	 * HIDES A FEATURE AND HENCE, SETS IT VISIBLE
	 */
	public void showFeature(FXGraphicalFeature graphicalFeature) {
		Feature feature = graphicalFeature.getFeature();
		feature.setHidden(false);
		graphicalFeature.setVisible(true);
		featureLineMap.get(graphicalFeature).setVisible(true);
	}
	
	/**
	 * Prints hidden status of subtree in breadth-first fashion
	 * 
	 * @param feature
	 */
	public void printHiddenStatusOfSubtree(Feature feature) {
		System.out.println("Is feature "+feature.getName()+"hidden? "+feature.isHidden());
		for (Feature childFeature : feature.getChildren()) {
			printHiddenStatusOfSubtree(childFeature);
		}
	}
	
	/**
	 * Remove feature and its children below it from diagram.
	 */
	public void removeFeatureTrunk(FXGraphicalFeature graphicalFeature) {
    	boolean decision = new FMESimpleDecsionDialog("Remove Feature Trunk", "This will remove the entire subtree of the selected feature! Do you want to proceed?").show();
    	if(decision) {
    		removeFeatureTrunkRecursively(graphicalFeature.getChildFeatures(), true);    			
    	}
	}
	
	/**
	 * TODO comment
	 * @param graphicalFeatures
	 */
	private void removeFeatureTrunkRecursively (List<FXGraphicalFeature> graphicalFeatures, boolean sendLoggerEvents) {
		
		while (!graphicalFeatures.isEmpty()) {
			FXGraphicalFeature fxChild = graphicalFeatures.get(0);
			if(!fxChild.getChildFeatures().isEmpty()) {
				removeFeatureTrunkRecursively(fxChild.getChildFeatures(), sendLoggerEvents);					
			} else {
				removeFeature(fxChild, false, true, sendLoggerEvents);
			}
		}
	}
	
	/**
	 * Recursively transfers all artifacts of an origin feature and its children to one target feature. 
	 * @param origin The feature from which all artifacts are copied
	 * @param target The feature to which all artifacts of the origin are copied to
	 */
	private void recusivelyTransferArtifacts(Feature origin, Feature target) {
		for (Feature childOrigin : origin.getChildren()) {
			recusivelyTransferArtifacts(childOrigin, target);
		} 
		// transfer the artifacts from the origin to the target
		target.getArtifactReferences().addAll(origin.getArtifactReferences());
	}
	
	public FeatureDiagramm getCurrentModel() {
		return currentModel;
	}

	public void setCurrentModel(FeatureDiagramm currentModel) {
		this.currentModel = currentModel;
	}
	
	public void logDiagramChanges(boolean startLogging) {
		DiagramLogger logger;
		if (startLogging) {	
			logger = DiagramLoggerFactory.getDiagramLogger(DiagramLoggerConsts.DIAGRAM_LOGGER_SIMPLE);
			logger.startLogging(currentModel, featureList);
		} else {
			try {
				logger = DiagramLoggerFactory.getCurrentLogger();
				logger.stopLogging();
			} catch (NullPointerException e) {
				System.out.println("Problem encountered with the Logger");
			}
		}
	}
	
	/**
	 * Triggers the replay of recorded feature modifications on the current feature diagram
	 * @param modificationSet A set of previously recorded modifications
	 */
	public void replayModificationSet(FeatureModelModificationSet modificationSet) {
		ModificationSetReplayer replayer = new ModificationSetReplayer(modificationSet, featureList, services);
		replayer.replay(currentModel);
	}

	public void setFeatureOptional(FXGraphicalFeature feature) {
		feature.setOptional();
	}

	public void setFeatureMandatory(FXGraphicalFeature feature) {
		feature.setMandatory();
	}
	
	public void setFeatureAlternativeGroup(FXGraphicalFeature feature) {
		feature.setGroupVariability_ALTERNATIVE();
	}
	
	public void setFeatureOrGroup(FXGraphicalFeature feature) {
		feature.setGroupVariability_OR();
	}
	
	public void setFeatureAndGroup(FXGraphicalFeature feature) {
		feature.setGroupVariability_AND();
	}

	public void renameCurrentFeature(String name) {
		currentFeature.getFeature().setName(name);
		currentFeature.rename(name);
		if (!currentFeature.getFeature().getArtifactReferences().isEmpty()) {
			currentFeature.getFeature().getArtifactReferences().get(0).setArtifactClearName(name);			
		}
	}

	public void setCurrentFeature(FXGraphicalFeature feature) {
		currentFeature = feature;
	}
	
	public FXGraphicalFeature getCurrentFeature() {
		return currentFeature;
	}
		
	public void setSelectedFeature(FXGraphicalFeature feature) {
		// check for removed nodes
		labelBorderAnimationMap.refresh();
		
		Label label = feature.getFeatureNameLabel();
		if (selectedFeatures.contains(feature)) {
			selectedFeatures.remove(feature);
			label.getStyleClass().remove("featureIsPartOfSelection");
			services.eventBroker.send(FDEventTable.LOGGER_REMOVE_FEATURE_FROM_SELECTION, feature);
			labelBorderAnimationMap.stopAnimation(label);
		} else {
			selectedFeatures.add(feature);
			label.getStyleClass().add("featureIsPartOfSelection");
			services.eventBroker.send(FDEventTable.LOGGER_ADD_FEATURE_TO_SELECTION, feature);
			labelBorderAnimationMap.startAnimation(label, new DashedBorderAnimation(label, 10, 5, 2));
		}
	}

	public void resetSelectedFeatures() {
		for (FXGraphicalFeature feature : selectedFeatures) {
			feature.getFeatureNameLabel().getStyleClass().remove("featureIsPartOfSelection");
		}
		selectedFeatures = new ArrayList<FXGraphicalFeature>();
		services.eventBroker.send(FDEventTable.LOGGER_RESET_SELECTED_FEATURES, "");
		
		// reset border animation map state
		labelBorderAnimationMap.stopAllAnimations();
		labelBorderAnimationMap.refresh();
	}

	public void fuseSelectedFeatures(FXGraphicalFeature feature) {
		services.eventBroker.send(FDEventTable.LOGGER_GROUP_SELECTED_FEATURES_IN_FEATURE, feature);
		if (FeatureModelEvaluator.isFeatureFusingPossible(selectedFeatures, feature)){
			List<FXGraphicalFeature> subFXRootFeatures = FeatureModelEvaluator.getRootNodesFromSelection(selectedFeatures);	
			if (FeatureModelEvaluator.assessIntermediateFeatures(subFXRootFeatures)) {
				if (FeatureModelEvaluator.assessParentChildRelation(subFXRootFeatures, feature)) {
					for (FXGraphicalFeature subFXRoot : subFXRootFeatures) {
						// transfer the artefacts first
						recusivelyTransferArtifacts(subFXRoot.getFeature(), feature.getFeature());
						
						// then remove the old features
						removeFeatureTrunkRecursively(subFXRoot.getChildFeatures(), false);
						removeFeature(subFXRoot, false, false, false);
					}
				}
			}
		} 
	}

	public void moveSelectedFeaturesUnderFeature(FXGraphicalFeature selectedParentFXFeature) {
		services.eventBroker.send(FDEventTable.LOGGER_MOVE_SELECTED_FEATURES_UNDER_FEATURE, selectedParentFXFeature);
		if (FeatureModelEvaluator.isFeatureMovePossible(selectedFeatures, selectedParentFXFeature)) {
			List<FXGraphicalFeature> subFXRootFeatures = FeatureModelEvaluator.getRootNodesFromSelection(selectedFeatures);	
			if (FeatureModelEvaluator.assessIntermediateFeatures(subFXRootFeatures)) {
				if (FeatureModelEvaluator.assessParentChildRelation(subFXRootFeatures, selectedParentFXFeature)) {
					Feature selectedParentFeature = selectedParentFXFeature.getFeature();
					for (FXGraphicalFeature subFXroot : subFXRootFeatures) {
						Feature subRoot = subFXroot.getFeature();
						
						subRoot.getParent().getChildren().remove(subRoot);
						subRoot.setParent(selectedParentFeature);
						selectedParentFeature.getChildren().add(subRoot);
						
						subFXroot.getParentFxFeature().getChildFeatures().remove(subFXroot);
						subFXroot.setParentFxFeature(selectedParentFXFeature);
						selectedParentFXFeature.getChildFeatures().add(subFXroot);
						
						// remove old line from subFeature to former parent
						removeLine(subFXroot);
						
						//connect new feature with parent
						createLineToChildren(subFXroot.getParentFxFeature(), subFXroot);						
					}
				}
			}
		}
	}

	/**
	 * Splits a feature an for each additional reference of the feature, creates a new feature. 
	 * Hence, a feature holding three references is split and produces two additional features, one
	 * for each reference excluding the reference held by the splitted feature itself.
	 * @param feature A feature holding at least one reference and which is to be split
	 */
	public void splitFeature(FXGraphicalFeature feature) {
		if (FeatureModelEvaluator.isFeatureSplitPossible(feature)) {
			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_SPLIT, feature);
			
			// only split if a feature contains at least two referenced artifacts
			// only transfer references, which are not the "original" reference of that artifact
			
			Iterator<ArtifactReference> iterator = feature.getFeature().getArtifactReferences().iterator();
			
			int i = 0;
			while(iterator.hasNext()) {
				ArtifactReference artifactReference = iterator.next();
				if (i > 0) {
					services.eventBroker.send(FDEventTable.ADD_FEATURE_BELOW, feature.getParentFxFeature());
					ArtifactReference currentArtRef = currentFeature.getFeature().getArtifactReferences().get(0);
					currentArtRef.getReferencedArtifactIDs().addAll(artifactReference.getReferencedArtifactIDs());
					renameCurrentFeature(artifactReference.getArtifactClearName());
					
					iterator.remove();
				}
				i++;
			}
		} 
	}
}
