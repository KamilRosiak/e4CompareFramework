package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import org.eclipse.e4.ui.di.UIEventTopic;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.ComponentFeature;
import FeatureDiagram.ConfigurationFeature;

import FeatureDiagram.FeatureDiagramm;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FMEditorPaneController;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FMEditorPaneView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureDiagramSerialiazer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureInitializer;
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
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.EventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.SyntaxGroup;
import featureConfiguration.FeatureConfiguration;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

/**
 * This class represents the view of the MVC implementation it represents the
 * FeatureDiagram graphical.
 * 
 * @author {Kamil Rosiak, Alexander Schlie}
 *
 */
public class FMEditorView {
	private FMEditorPaneController editorPane;
	private ServiceContainer services;

	public FMEditorView(FMEditorPaneController editorPane, Consumer<javafx.scene.Node> uiConsumer) {
		this.editorPane = editorPane;
		uiConsumer.accept(this.editorPane.ui());
	}
	
	public FMEditorView(Tab tab, ServiceContainer services) {
		tab.setContent(this.editorPane.ui());
		this.services = services;
	}
	
	public void displayFeatureDiagram(FeatureDiagram diagram) {
		FXGraphicalFeature root = buildFXGrapicalDiagram(diagram.getRoot());
		editorPane.displayFeatureDiagram(root);
	}
	
	private FXGraphicalFeature buildFXGrapicalDiagram(IFeature feature) {
		FXGraphicalFeature fxRoot = new FXGraphicalFeature(feature);
		for (IFeature child : feature.getChildren()) {
			FXGraphicalFeature fxChild = buildFXGrapicalDiagram(child);
			fxRoot.addChildFeature(fxChild);
		}
		return fxRoot;
	}
	
//	
//
//	/**
//	 * This method loads a FeatureDiagram.
//	 */
//	public void loadFeatureDiagram(FeatureDiagramm model, boolean isSave) {
//		// Ask for saving before deleting
//		if (isSave) {
//			askToSave();
//		}
//		clearAll();
//
//		editorPane.currentModel = model;
//		FXGraphicalFeature fxRoot = addFeature(model.getRoot());
//		postProcessFeatureVisibility(fxRoot);
//		double xShift = this.getRootPane().getWidth() / 2 - model.getRoot().getGraphicalfeature().getX();
//		double yShift = this.getRootPane().getHeight() / 2 - model.getRoot().getGraphicalfeature().getY();
//		
//		for (Feature childFeature : model.getRoot().getChildren()) {
//			FXGraphicalFeature fxChild = addFeature(childFeature);
//			fxRoot.addChildFeature(fxChild);
//			fxChild.setParentFxFeature(fxRoot);
//			createLineToChildren(fxRoot, fxChild);
//		}
//		
//		// add all Feature to front so that no overlapping exists.
//		for (FXGraphicalFeature fxFeature : editorPane.featureList) {
//			fxFeature.getFeature().getGraphicalfeature().setX(fxFeature.getFeature().getGraphicalfeature().getX() + xShift);
//			fxFeature.getFeature().getGraphicalfeature().setY(fxFeature.getFeature().getGraphicalfeature().getY() + yShift);
//			fxFeature.toFront();
//			if (fxFeature.getFeature() instanceof ComponentFeature) {
//				fxFeature.getFeatureNameLabel().getStyleClass().add("componentFeature");
//				editorPane.componentFeatureList.add(fxFeature);
//			} else if (fxFeature.getFeature() instanceof ConfigurationFeature) {
//				fxFeature.getFeatureNameLabel().getStyleClass().add("componentFeature");
//			}
//		}
//		editorPane.rootPane.setTranslateX(xShift);
//		editorPane.rootPane.setTranslateY(yShift);
//	}
//
//	private void postProcessFeatureVisibility(FXGraphicalFeature fxRoot) {
//		for (FXGraphicalFeature fxChild : fxRoot.getChildFeatures()) {
//			if (fxChild.getFeature().isHidden()) {
//				postProcessRecursivelyHideSubfeatures(fxChild);
//			} else {
//				postProcessFeatureVisibility(fxChild);
//			}
//		}
//	}
//
//	public void postProcessRecursivelyHideSubfeatures(FXGraphicalFeature graphicalFeature) {
//		for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
//			changeFeatureVisibility(fxChild, true);
//			postProcessRecursivelyHideSubfeatures(fxChild);
//		}
//	}
//	
//	public FXGraphicalFeature addFeature(Feature feature) {
//		return addFeature(feature, null);
//	}
//
//	/**
//	 * This method adds a feature to the position of this graphicalFeature with his
//	 * children.
//	 */
//	public FXGraphicalFeature addFeature(Feature feature, Color background) {
//		FXGraphicalFeature fxGraFeature = createFXGraphicalFeature(feature, background);
//
//		editorPane.rootPane.getChildren().add(fxGraFeature);
//		editorPane.featureList.add(fxGraFeature);
//
//		return fxGraFeature;
//	}
//	
//	
	
//
//	/**
//	 * This method adds a feature to FeatureModelEditor to given coordinates.
//	 */
//	public FXGraphicalFeature addFeature(Feature feature, double xPos, double yPos) {
//		feature.getGraphicalfeature().setX(xPos);
//		feature.getGraphicalfeature().setY(yPos);
//		return addFeature(feature);
//	}
//
//	/**
//	 * This method adds a feature to the model editor at the current mouse position
//	 * .
//	 */
//	public FXGraphicalFeature addFeatureToMousePosition(Feature feature) {
//		feature.getGraphicalfeature().setX(editorPane.mouseX);
//		feature.getGraphicalfeature().setY(editorPane.mouseY);
//		return addFeature(feature);
//	}
//
//	public FXGraphicalFeature addFeatureBelow(Pair<String, FXGraphicalFeature> pair) {
//		String type = pair.getKey();
//		FXGraphicalFeature parent = pair.getValue();
//		double xPos = parent.getFeature().getGraphicalfeature().getX();
//		double yPos = parent.getFeature().getGraphicalfeature().getY() + parent.getHeight() * 2;
//		FXGraphicalFeature newFeature;
//		switch (type) {
//			case FDStringTable.FEATURE:
//				newFeature = createFeatureFX(parent, false, xPos, yPos);
//				break;
//			case FDStringTable.COMPONENTFEATURE:
//				newFeature = createComponentFeatureFX(parent, true, xPos, yPos);
//				break;
//			case FDStringTable.CONFIGURATIONFEATURE:
//				newFeature = createConfigurationFeatureFX(parent, false, xPos, yPos);
//				break;
//			default:
//				throw new IllegalArgumentException("Wrong type");
//		}
//		
//		return newFeature;
//	}
//
//	public FXGraphicalFeature addConfigurationBelow(Pair<FeatureConfiguration, FXGraphicalFeature> pair) {
//
//		FXGraphicalFeature fx = addFeatureBelow(new Pair<String, FXGraphicalFeature>(FDStringTable.CONFIGURATIONFEATURE, pair.getValue()));
//		ConfigurationFeature configFeature = (ConfigurationFeature) fx.getFeature();
//		configFeature.setConfigurationfeature(pair.getKey());
//		fx.setName(pair.getKey().getName());
//		
//		return fx;
//	}
//	
//	public FXGraphicalFeature createGraphicalFeatureBelow(FXGraphicalFeature parent, Feature newFeature) {
//		// add the new feature to model and set the parent feature
//		parent.getFeature().getChildren().add(newFeature);
//		newFeature.setParent(parent.getFeature());
//		
//		FXGraphicalFeature newGraFeature = new FXGraphicalFeature(this, newFeature, services);
//		// create graphical feature and set parent
//		newGraFeature.setParentFxFeature(parent);
//		parent.addChildFeatureFormated(newGraFeature);
//
//		// add graphical feature to scene
//		editorPane.rootPane.getChildren().addAll(newGraFeature);
//
//		// add feature to featureList
//		editorPane.featureList.add(newGraFeature);
//
//		// connect new feature with parent
//		createLineToChildren(parent, newGraFeature);
//
//		// set the current feature, necessary for feature split
//		setCurrentFeature(newGraFeature);
//		return newGraFeature;
//	}
//	
//	/**
//	 * This method adds a parent to given child and returns the new FXFeature.
//	 */
//	public FXGraphicalFeature addFeatureAbove(FXGraphicalFeature fxFeature) {
//		services.eventBroker.send(FDEventTable.LOGGER_ADD_FEATURE_ABOVE, fxFeature);
//
//		Feature feature = fxFeature.getFeature();
//
//		// If adding a feature above the current root feature
//		if (feature.getParent() == null) {
//			return addFeatureAboveRoot(fxFeature);
//		}
//		// else:
//
//		Feature parentFeature = feature.getParent();
//		FXGraphicalFeature fxParentFeature = fxFeature.getParentFxFeature();
//
//		// position under the chosen parent
//		double xPosChild = feature.getGraphicalfeature().getX();
//		double yPosChild = feature.getGraphicalfeature().getY() + fxFeature.getHeight() * 2;
//
//		double xPosParent = parentFeature.getGraphicalfeature().getX();
//		double yPosParent = parentFeature.getGraphicalfeature().getY()
//				+ parentFeature.getGraphicalfeature().getHeight() * 2;
//
//		// Set the position of the new feature to be in the middle of both the x- and
//		// the y-axis
//		// of the former parent and the current child
//		double xPosNewFeature = (Math.max(xPosChild, xPosParent) - Math.min(xPosChild, xPosParent)) / 2
//				+ Math.min(xPosChild, xPosParent);
//		double yPosNewFeature = (Math.max(yPosChild, yPosParent) - Math.min(yPosChild, yPosParent)) / 2
//				+ Math.min(yPosChild, yPosParent);
//
//		// create new feature and add above the child
//		
//		Feature newFeature = createNewFeature(FeatureInitializer.createFeature("NewFeature_" + editorPane.currentModel.getIdentifierIncrement(), false), xPosNewFeature, yPosNewFeature);
//
//		// Reset the parent-child relations
//
//		parentFeature.getChildren().remove(feature);
//		feature.setParent(newFeature);
//		newFeature.getChildren().add(feature);
//		newFeature.setParent(parentFeature);
//		parentFeature.getChildren().add(newFeature);
//
//		// create graphical feature and set parent
//		FXGraphicalFeature newGraFeature = new FXGraphicalFeature(this, newFeature, services);
//
//		// Reset the parent-child relations for the graphical FXfeatures
//		fxParentFeature.getChildFeatures().remove(fxFeature);
//		fxFeature.setParentFxFeature(newGraFeature);
//		newGraFeature.getChildFeatures().add(fxFeature);
//		newGraFeature.setParentFxFeature(fxParentFeature);
//		fxParentFeature.getChildFeatures().add(newGraFeature);
//		newGraFeature.minHeight(27.0);
//
//		newGraFeature.addChildFeatureFormated(fxFeature);
//		fxParentFeature.addChildFeatureFormated(newGraFeature);
//
//		// add graphical feature to scene
//		editorPane.rootPane.getChildren().addAll(newGraFeature);
//
//		// add feature to featureList
//		editorPane.featureList.add(newGraFeature);
//
//		// remove old lines
//		removeLine(fxFeature);
//
//		// connect new feature with parent
//		createLineToChildren(fxParentFeature, newGraFeature);
//		createLineToChildren(newGraFeature, fxFeature);
//
//		setCurrentFeature(newGraFeature);
//
//		return newGraFeature;
//	}
//
//	/**
//	 * Adds a new feature above the current root feature, and thus, replaces the
//	 * root with a new feature
//	 * 
//	 * @param fxfeature The current root feature
//	 * @return The new root feature, holding the former root feature "fxfeature" as
//	 *         a child
//	 */
//	private FXGraphicalFeature addFeatureAboveRoot(FXGraphicalFeature fxfeature) {
//		Feature formerRoot = fxfeature.getFeature();
//		double xPos = formerRoot.getGraphicalfeature().getX();
//		double yPos = formerRoot.getGraphicalfeature().getY() - 30;
//
//		// create new feature and add above the child
//		Feature newRoot = createNewFeature(FeatureInitializer.createFeature("NewFeature_" + editorPane.currentModel.getIdentifierIncrement(), true), xPos, yPos);
//		
//		// set parent-child relations
//		formerRoot.setParent(newRoot);
//		newRoot.getChildren().add(formerRoot);
//
//		// create graphical feature and set parent
//		FXGraphicalFeature newGraRoot = new FXGraphicalFeature(this, newRoot, services);
//		fxfeature.setParentFxFeature(newGraRoot);
//		newGraRoot.getChildFeatures().add(fxfeature);
//
//		newGraRoot.addChildFeatureFormated(fxfeature);
//
//		// add graphical feature to scene
//		editorPane.rootPane.getChildren().addAll(newGraRoot);
//
//		// add feature to featureList
//		editorPane.featureList.add(newGraRoot);
//
//		// connect new feature with parent
//		createLineToChildren(newGraRoot, fxfeature);
//
//		// finally, reset the root of the feature diagram
//		editorPane.currentModel.setRoot(newRoot);
//
//		// Notify the logger
//		services.eventBroker.send(FDEventTable.LOGGER_ADD_FEATURE, newGraRoot);
//
//		setCurrentFeature(newGraRoot);
//
//		return newGraRoot;
//	}
//
//	/**
//	 * Creating the connections between child and parent and binding property's.
//	 */
//	private void createLineToChildren(FXGraphicalFeature parent, FXGraphicalFeature child) {
//		final Line line = new Line();
//
//		// initial bind
//		line.startXProperty().bind(parent.getXPos().add(parent.getWidth() / 2));
//		line.startYProperty().bind(parent.getYPos().add(parent.getHeight() - parent.getLowerConnector().getRadiusY()));
//
//		line.endYProperty().bind(child.translateYProperty());
//		line.endXProperty().bind(child.translateXProperty().add(child.widthProperty().doubleValue() / 2));
//
//		// after update size
//		parent.widthProperty().addListener(e -> {
//			line.startXProperty().unbind();
//			line.startXProperty().bind(parent.getXPos().add(parent.getWidth() / 2));
//		});
//
//		parent.heightProperty().addListener(e -> {
//			line.startYProperty().unbind();
//			line.startYProperty()
//					.bind(parent.getYPos().add(parent.getHeight() - parent.getLowerConnector().getRadiusY()));
//		});
//
//		// if height changes bind with new height.
//		child.heightProperty().addListener(e -> {
//			line.endYProperty().unbind();
//			line.endYProperty().bind(child.translateYProperty());
//		});
//
//		// if size changes bind with new width.
//		child.widthProperty().addListener(e -> {
//			line.endXProperty().unbind();
//			line.endXProperty().bind(child.translateXProperty().add(child.widthProperty().doubleValue() / 2));
//		});
//
//		editorPane.featureLineMap.put(child, line);
//		editorPane.rootPane.getChildren().add(line);
//	}
//
//	/**
//	 * Removes a connection between a child feature and its' parent.
//	 */
//	private void removeLine(FXGraphicalFeature child) {
//
//		try {
//			Line line = editorPane.featureLineMap.get(child);
//			line.startXProperty().unbind();
//			line.startYProperty().unbind();
//			line.endXProperty().unbind();
//			line.endYProperty().unbind();
//			editorPane.rootPane.getChildren().remove(line);
//			editorPane.featureLineMap.remove(child);
//
//		} catch (Exception e) {
//			System.out.println("Error code 'x8y-11f': I was unable to remove line in Feature Model for feature: "
//					+ child.getFeature().getName());
//		}
//	}
//	
//	public Feature createNewFeature(Feature feature, double x, double y) {
//		editorPane.currentModel.setIdentifierIncrement(editorPane.currentModel.getIdentifierIncrement() + 1);
//		feature.setIdentifier(editorPane.currentModel.getIdentifierIncrement());
//		
//
//		/**
//		 * Set Feature to x and y position 
//		 */
//		feature.getGraphicalfeature().setX(x);
//		feature.getGraphicalfeature().setY(y);
//		
//		return feature;
//	}
//
//	public FXGraphicalFeature createFeatureFX(FXGraphicalFeature parent, boolean isRoot, double x, double y) {
//		Feature feature = FeatureInitializer.createFeature("NewFeature_" + editorPane.currentModel.getIdentifierIncrement(), isRoot);
//		createNewFeature(feature, x, y);
//		return createGraphicalFeatureBelow(parent, feature);
//	}
//
//	public FXGraphicalFeature createComponentFeatureFX(FXGraphicalFeature parent, boolean isRoot, double x, double y) {
//		ComponentFeature feature = FeatureInitializer.createComponentFeature("NewComponentFeature_" + editorPane.currentModel.getIdentifierIncrement());
//		createNewFeature(feature, x, y);
//		FXGraphicalFeature newGraFeature = createGraphicalFeatureBelow(parent, feature);
//		newGraFeature.getFeatureNameLabel().getStyleClass().addAll("componentFeature");
//		editorPane.componentFeatureList.add(newGraFeature);
//		return newGraFeature;
//		
//	}
//	public FXGraphicalFeature createConfigurationFeatureFX(FXGraphicalFeature parent, boolean isRoot, double x, double y) {
//		ConfigurationFeature feature = FeatureInitializer.createConfigurationFeature("NewConfigurationFeature_" + editorPane.currentModel.getIdentifierIncrement());
//		createNewFeature(feature, x, y);
//		FXGraphicalFeature newGraFeature = createGraphicalFeatureBelow(parent, feature);
//		newGraFeature.getFeatureNameLabel().getStyleClass().addAll("componentFeature");
//		return newGraFeature;
//	}
//
//	public Pane getRootPane() {
//		return editorPane.rootPane;
//	}
//
//	/**
//	 * returns a list that contains all FXGraphicalFeature.
//	 */
//	public List<FXGraphicalFeature> getFeatureList() {
//		return editorPane.featureList;
//	}
//	
//	public List<FXGraphicalFeature> getComponentFeatureList() {
//		return editorPane.componentFeatureList;
//	}
//	
//	public FeatureDiagramm getFeatureDiagram() {
//		return editorPane.currentModel;
//	}
//
//	/**
//	 * This method adds a FXGraphicalFeature to list.
//	 * 
//	 * @param graphicalFeature
//	 */
//	public void addFXGraphicalFeatureToList(FXGraphicalFeature graphicalFeature) {
//		this.editorPane.featureList.add(graphicalFeature);
//	}
//
//	/**
//	 * Remove feature from diagram.
//	 */
//	public void removeFeature(FXGraphicalFeature graphicalFeature, boolean showDialog, boolean triggeredByTrunkDelete,
//			boolean sendLoggerEvents) {
//
//		if (editorPane.currentModel.getRoot().equals(graphicalFeature.getFeature())) {
//			new FMESimpleNoticeDialog("Error", "Root feature can not be deleted");
//			return;
//		}
//
//		boolean decision = false;
//		if (showDialog) {
//			decision = new FMESimpleDecsionDialog("Remove Feature", "Are you sure").show();
//		}
//		if (decision || !showDialog) {
//			this.editorPane.rootPane.getChildren().remove(editorPane.featureLineMap.get(graphicalFeature));
//			this.editorPane.rootPane.getChildren().remove(graphicalFeature);
//			removeLine(graphicalFeature);
//			this.editorPane.featureList.remove(graphicalFeature);
//			if (graphicalFeature.getFeature() instanceof ComponentFeature) {
//				this.editorPane.componentFeatureList.remove(graphicalFeature);			
//				List<FXGraphicalFeature> temp = new ArrayList<FXGraphicalFeature>(graphicalFeature.getChildFeatures());
//				for(FXGraphicalFeature child: temp) {
//					removeFeature(child, false, false, false);
//				};
//			} else if (graphicalFeature.getFeature() instanceof ConfigurationFeature) {
//				((ComponentFeature) graphicalFeature.getParentFxFeature().getFeature()).getConfigurationfeature().remove((ConfigurationFeature) graphicalFeature.getFeature());
//			}
//				
//
//			if (sendLoggerEvents) {
//				services.eventBroker.send(FDEventTable.LOGGER_REMOVE_FEATURE, graphicalFeature);
//			}
//
//			if (graphicalFeature.getParentFxFeature() != null) {
//				Feature parent = graphicalFeature.getParentFxFeature().getFeature();
//				parent.getChildren().remove(graphicalFeature.getFeature());
//				graphicalFeature.getParentFxFeature().getChildFeatures().remove(graphicalFeature);
//
//				// remove from selected features if contained
//				editorPane.selectedFeatures.remove(graphicalFeature);
//
//				// only reset line if we do not delete the entire trunk anyways
//				if (!triggeredByTrunkDelete && !graphicalFeature.getChildFeatures().isEmpty()) {
//					for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
//						reconnectFeatures(graphicalFeature, fxChild);
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * todo comment
//	 * 
//	 * @param parent
//	 * @param child
//	 */
//	private void reconnectFeatures(FXGraphicalFeature parent, FXGraphicalFeature child) {
//		services.eventBroker.send(FDEventTable.LOGGER_REMOVED_LINE_TO_PARENT_FEATURE, parent);
//		child.setParentFxFeature(parent.getParentFxFeature());
//		parent.getParentFxFeature().getChildFeatures().add(child);
//		child.getFeature().setParent(parent.getParentFxFeature().getFeature());
//		parent.getParentFxFeature().getFeature().getChildren().add(child.getFeature());
//		removeLine(child);
//		createLineToChildren(child.getParentFxFeature(), child);
//		services.eventBroker.send(FDEventTable.LOGGER_RESET_LINE_TO_PARENT_FEATURE, child);
//	}
//
//	/*
//	 * Sets a feature to be Abstract or Concrete, depending on its current state
//	 */
//	public void setFeatureAbstract(FXGraphicalFeature graphicalFeature) {
//		Feature feature = graphicalFeature.getFeature();
//		if (feature.isAbstract()) {
//			feature.setAbstract(false);
//			graphicalFeature.getFeatureNameLabel().getStyleClass().remove("abstractFeature");
//		} else {
//			feature.setAbstract(true);
//			graphicalFeature.getFeatureNameLabel().getStyleClass().add("abstractFeature");
//		}
//	}
//
//	/**
//	 * TODO comment
//	 */
//	public void changeSubfeaturesVisibility(FXGraphicalFeature graphicalFeature) {
//		services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_SUBFEATURES_VISIBILITY,
//				graphicalFeature);
//		if (graphicalFeature.getFeature().isHidden()) {
//			graphicalFeature.getFeature().setHidden(false);
//			for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
//				recursivelyShowSubfeatures(fxChild);
//			}
//		} else {
//			graphicalFeature.getFeature().setHidden(true);
//			for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
//				recursivelyHideSubfeatures(fxChild);
//			}
//		}
//
//	}
//
//	/**
//	 * RECURSIVELY SHOWS SUBFEATURES
//	 */
//	public void recursivelyShowSubfeatures(FXGraphicalFeature graphicalFeature) {
//		if (!graphicalFeature.getFeature().isHidden()) {
//			for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
//				recursivelyShowSubfeatures(fxChild);
//			}
//		}
//		changeFeatureVisibility(graphicalFeature, false);
//	}
//
//	/**
//	 * RECURSIVELY HIDES SUBFEATURES
//	 */
//	public void recursivelyHideSubfeatures(FXGraphicalFeature graphicalFeature) {
//		if (!graphicalFeature.getFeature().isHidden()) {
//			for (FXGraphicalFeature fxChild : graphicalFeature.getChildFeatures()) {
//				recursivelyHideSubfeatures(fxChild);
//			}
//		}
//		if (graphicalFeature.isVisible()) {
//			changeFeatureVisibility(graphicalFeature, true);
//		}
//	}
//
//	/**
//	 * HIDES or SHOWS A FEATURE AND HENCE, SETS IT INVISIBLE
//	 */
//	public void changeFeatureVisibility(FXGraphicalFeature graphicalFeature, boolean hideThisFeautre) {
//		graphicalFeature.setVisible(!hideThisFeautre);
//		editorPane.featureLineMap.get(graphicalFeature).setVisible(!hideThisFeautre);
//		services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VISIBILITY, graphicalFeature);
//	}
//
//	/**
//	 * HIDES A FEATURE AND HENCE, SETS IT VISIBLE
//	 */
//	public void showFeature(FXGraphicalFeature graphicalFeature) {
//		Feature feature = graphicalFeature.getFeature();
//		feature.setHidden(false);
//		graphicalFeature.setVisible(true);
//		editorPane.featureLineMap.get(graphicalFeature).setVisible(true);
//	}
//
//	/**
//	 * Prints hidden status of subtree in breadth-first fashion
//	 * 
//	 * @param feature
//	 */
//	public void printHiddenStatusOfSubtree(Feature feature) {
//		System.out.println("Is feature " + feature.getName() + "hidden? " + feature.isHidden());
//		for (Feature childFeature : feature.getChildren()) {
//			printHiddenStatusOfSubtree(childFeature);
//		}
//	}
//
//	/**
//	 * Remove feature and its children below it from diagram.
//	 */
//	public void removeFeatureTrunk(FXGraphicalFeature graphicalFeature) {
//		boolean decision = new FMESimpleDecsionDialog("Remove Feature Trunk",
//				"This will remove the entire subtree of the selected feature! Do you want to proceed?").show();
//		if (decision) {
//			removeFeatureTrunkRecursively(graphicalFeature.getChildFeatures(), true);
//		}
//	}
//
//	/**
//	 * TODO comment
//	 * 
//	 * @param graphicalFeatures
//	 */
//	private void removeFeatureTrunkRecursively(List<FXGraphicalFeature> graphicalFeatures, boolean sendLoggerEvents) {
//
//		while (!graphicalFeatures.isEmpty()) {
//			FXGraphicalFeature fxChild = graphicalFeatures.get(0);
//			if (!fxChild.getChildFeatures().isEmpty()) {
//				removeFeatureTrunkRecursively(fxChild.getChildFeatures(), sendLoggerEvents);
//			} else {
//				removeFeature(fxChild, false, true, sendLoggerEvents);
//			}
//		}
//	}
//
//	/**
//	 * Recursively transfers all artifacts of an origin feature and its children to
//	 * one target feature.
//	 * 
//	 * @param origin The feature from which all artifacts are copied
//	 * @param target The feature to which all artifacts of the origin are copied to
//	 */
//	private void recusivelyTransferArtifacts(Feature origin, Feature target) {
//		for (Feature childOrigin : origin.getChildren()) {
//			recusivelyTransferArtifacts(childOrigin, target);
//		}
//		// transfer the artifacts from the origin to the target
//		target.getArtifactReferences().addAll(origin.getArtifactReferences());
//	}
//
//	public FeatureDiagramm getCurrentModel() {
//		return editorPane.currentModel;
//	}
//
//	public void setCurrentModel(FeatureDiagramm currentModel) {
//		this.editorPane.currentModel = currentModel;
//	}
//
//	public void logDiagramChanges(boolean startLogging) {
//		DiagramLogger logger;
//		if (startLogging) {
//			logger = DiagramLoggerFactory.getDiagramLogger(DiagramLoggerConsts.DIAGRAM_LOGGER_SIMPLE);
//			logger.startLogging(editorPane.currentModel, editorPane.featureList);
//		} else {
//			try {
//				logger = DiagramLoggerFactory.getCurrentLogger();
//				logger.stopLogging();
//			} catch (NullPointerException e) {
//				System.out.println("Problem encountered with the Logger");
//			}
//		}
//	}
//
//	/**
//	 * Triggers the replay of recorded feature modifications on the current feature
//	 * diagram
//	 * 
//	 * @param modificationSet A set of previously recorded modifications
//	 */
//	public void replayModificationSet(FeatureModelModificationSet modificationSet) {
//		ModificationSetReplayer replayer = new ModificationSetReplayer(modificationSet, editorPane.featureList, services);
//		replayer.replay(editorPane.currentModel);
//	}
//
//	public void setFeatureOptional(FXGraphicalFeature feature) {
//		feature.setOptional();
//		if (feature.getFeature() == editorPane.currentModel.getRoot()) {
//			services.eventBroker.send(FDEventTable.ROOT_FEATURE_OPTIONAL_EVENT, editorPane.currentModel.getUuid());
//		}
//	}
//
//	public void setFeatureMandatory(FXGraphicalFeature feature) {
//		feature.setMandatory();
//		if (feature.getFeature() == editorPane.currentModel.getRoot()) {
//			services.eventBroker.send(FDEventTable.ROOT_FEATURE_MANDATORY_EVENT, editorPane.currentModel.getUuid());
//		}
//	}
//
//	public void setFeatureAlternativeGroup(FXGraphicalFeature feature) {
//		feature.setGroupVariability_ALTERNATIVE();
//	}
//
//	public void setFeatureOrGroup(FXGraphicalFeature feature) {
//		feature.setGroupVariability_OR();
//	}
//
//	public void setFeatureAndGroup(FXGraphicalFeature feature) {
//		feature.setGroupVariability_AND();
//	}
//
//	public void renameCurrentFeature(String name) {
//		editorPane.currentFeature.setName(name);
//		if (!editorPane.currentFeature.getFeature().getArtifactReferences().isEmpty()) {
//			editorPane.currentFeature.getFeature().getArtifactReferences().get(0).setArtifactClearName(name);
//		}
//	}
//
//	public void setCurrentFeature(FXGraphicalFeature feature) {
//		editorPane.currentFeature = feature;
//	}
//
//	public FXGraphicalFeature getCurrentFeature() {
//		return editorPane.currentFeature;
//	}
//
//	public void setSelectedFeature(FXGraphicalFeature feature) {
//		// check for removed nodes
//		editorPane.labelBorderAnimationMap.refresh();
//
//		Label label = feature.getFeatureNameLabel();
//		if (editorPane.selectedFeatures.contains(feature)) {
//			editorPane.selectedFeatures.remove(feature);
//			label.getStyleClass().remove("featureIsPartOfSelection");
//			services.eventBroker.send(FDEventTable.LOGGER_REMOVE_FEATURE_FROM_SELECTION, feature);
//			editorPane.labelBorderAnimationMap.stopAnimation(label);
//		} else {
//			editorPane.selectedFeatures.add(feature);
//			label.getStyleClass().add("featureIsPartOfSelection");
//			services.eventBroker.send(FDEventTable.LOGGER_ADD_FEATURE_TO_SELECTION, feature);
//			editorPane.labelBorderAnimationMap.startAnimation(label, new DashedBorderAnimation(label, 10, 5, 2));
//		}
//	}
//
//	public void resetSelectedFeatures() {
//		for (FXGraphicalFeature feature : editorPane.selectedFeatures) {
//			feature.getFeatureNameLabel().getStyleClass().remove("featureIsPartOfSelection");
//		}
//		editorPane.selectedFeatures = new ArrayList<FXGraphicalFeature>();
//		services.eventBroker.send(FDEventTable.LOGGER_RESET_SELECTED_FEATURES, "");
//
//		// reset border animation map state
//		editorPane.labelBorderAnimationMap.stopAllAnimations();
//		editorPane.labelBorderAnimationMap.refresh();
//	}
//
//	public void fuseSelectedFeatures(FXGraphicalFeature feature) {
//		services.eventBroker.send(FDEventTable.LOGGER_GROUP_SELECTED_FEATURES_IN_FEATURE, feature);
//		if (FeatureModelEvaluator.isFeatureFusingPossible(editorPane.selectedFeatures, feature)) {
//			List<FXGraphicalFeature> subFXRootFeatures = FeatureModelEvaluator
//					.getRootNodesFromSelection(editorPane.selectedFeatures);
//			if (FeatureModelEvaluator.assessIntermediateFeatures(subFXRootFeatures)) {
//				if (FeatureModelEvaluator.assessParentChildRelation(subFXRootFeatures, feature)) {
//					for (FXGraphicalFeature subFXRoot : subFXRootFeatures) {
//						// transfer the artefacts first
//						recusivelyTransferArtifacts(subFXRoot.getFeature(), feature.getFeature());
//
//						// then remove the old features
//						removeFeatureTrunkRecursively(subFXRoot.getChildFeatures(), false);
//						removeFeature(subFXRoot, false, false, false);
//					}
//				}
//			}
//		}
//	}
//
//	public void moveSelectedFeaturesUnderFeature(FXGraphicalFeature selectedParentFXFeature) {
//		services.eventBroker.send(FDEventTable.LOGGER_MOVE_SELECTED_FEATURES_UNDER_FEATURE, selectedParentFXFeature);
//		if (FeatureModelEvaluator.isFeatureMovePossible(editorPane.selectedFeatures, selectedParentFXFeature)) {
//			List<FXGraphicalFeature> subFXRootFeatures = FeatureModelEvaluator
//					.getRootNodesFromSelection(editorPane.selectedFeatures);
//			if (FeatureModelEvaluator.assessIntermediateFeatures(subFXRootFeatures)) {
//				if (FeatureModelEvaluator.assessParentChildRelation(subFXRootFeatures, selectedParentFXFeature)) {
//					Feature selectedParentFeature = selectedParentFXFeature.getFeature();
//					for (FXGraphicalFeature subFXroot : subFXRootFeatures) {
//						Feature subRoot = subFXroot.getFeature();
//
//						subRoot.getParent().getChildren().remove(subRoot);
//						subRoot.setParent(selectedParentFeature);
//						selectedParentFeature.getChildren().add(subRoot);
//
//						subFXroot.getParentFxFeature().getChildFeatures().remove(subFXroot);
//						subFXroot.setParentFxFeature(selectedParentFXFeature);
//						selectedParentFXFeature.getChildFeatures().add(subFXroot);
//
//						// remove old line from subFeature to former parent
//						removeLine(subFXroot);
//
//						// connect new feature with parent
//						createLineToChildren(subFXroot.getParentFxFeature(), subFXroot);
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * Splits a feature an for each additional reference of the feature, creates a
//	 * new feature. Hence, a feature holding three references is split and produces
//	 * two additional features, one for each reference excluding the reference held
//	 * by the splitted feature itself.
//	 * 
//	 * @param feature A feature holding at least one reference and which is to be
//	 *                split
//	 */
//	public void splitFeature(FXGraphicalFeature feature) {
//		if (FeatureModelEvaluator.isFeatureSplitPossible(feature)) {
//			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_SPLIT, feature);
//
//			// only split if a feature contains at least two referenced artifacts
//			// only transfer references, which are not the "original" reference of that
//			// artifact
//
//			Iterator<ArtifactReference> iterator = feature.getFeature().getArtifactReferences().iterator();
//
//			int i = 0;
//			while (iterator.hasNext()) {
//				ArtifactReference artifactReference = iterator.next();
//				if (i > 0) {
//					services.eventBroker.send(FDEventTable.ADD_FEATURE_BELOW, feature.getParentFxFeature());
//					ArtifactReference currentArtRef = editorPane.currentFeature.getFeature().getArtifactReferences().get(0);
//					currentArtRef.getReferencedArtifactIDs().addAll(artifactReference.getReferencedArtifactIDs());
//					renameCurrentFeature(artifactReference.getArtifactClearName());
//
//					iterator.remove();
//				}
//				i++;
//			}
//		}
//	}
//
//	public void loadSyntaxGroups(@UIEventTopic(EventTable.PUBLISH_SYNTAX_GROUPS) List<SyntaxGroup> groups) {
//		clearAll();
//		
//		Iterator<SyntaxGroup> groupIterator = groups.iterator();
//		SyntaxGroup coreGroup = groupIterator.next();
//		Feature coreFeature = FeatureInitializer.createFeature("Core", true);
//		FXGraphicalFeature fxCore = addFeature(coreFeature, coreGroup.getColor());
//		
//		while (groupIterator.hasNext()) {
//			SyntaxGroup group = groupIterator.next();
//			Feature groupFeature = FeatureInitializer.createFeature(group.getNormalizedName(), false);
//			FXGraphicalFeature fxGroup = addFeature(groupFeature, group.getColor());
//			insertChild(coreFeature, groupFeature);
//			insertChild(fxCore, fxGroup);
//		}
//		
//		FeatureDiagramm diagram = new FeatureDiagram();
//		diagram.setRoot(coreFeature);
//		editorPane.currentModel = diagram;
//	}
//	
//	private void insertChild(Feature parent, Feature child) {
//		parent.getChildren().add(child);
//		child.setParent(parent);
//	}
//	
//	private void insertChild(FXGraphicalFeature parent, FXGraphicalFeature child) {
//		parent.addChildFeature(child);
//		child.setParentFxFeature(parent);
//		createLineToChildren(parent, child);
//	}

}
