package de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements;

import java.util.ArrayList;
import java.util.List;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.Feature;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleTextInputDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacemantConsts;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.FeatureModelEditorView;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class FXGraphicalFeature extends VBox  {
	private FXFeatureLowerConnector lowerConnector;
	private FXFeatureUpperConnector upperConnector;
	private Feature feature;
	private ServiceContainer services;
	private FeatureModelEditorView view;
	private List<FXGraphicalFeature> childFeatures;
	private DoubleProperty xPos, yPos;
	private Label featureNameLabel;
	private FXGraphicalFeature parentFxFeature;

	public FXGraphicalFeature(FeatureModelEditorView featureModelEditorView, Feature feature, ServiceContainer services) {
		this.feature = feature;
		this.view = featureModelEditorView;
		this.services = services;
		this.childFeatures = new ArrayList<FXGraphicalFeature>();
		createFeature();
	}

	private void createFeature() {
		setSpacing(0);
		initGraphicalFeature();
		addDrag();
		addSelectionListener();
		createTranslatePropertys();
		createContextMenu();
		bindHeight();
		bindWidth();
	}
		
	/**
	 * This method bind features x and y translate values to the FXGraphicalFeature.
	 */
	private void createTranslatePropertys() {
		xPos = new SimpleDoubleProperty(feature.getGraphicalfeature().getX());
		xPos.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				feature.getGraphicalfeature().setX(newValue.doubleValue());
			}
	      });
		
		yPos = new SimpleDoubleProperty(feature.getGraphicalfeature().getY());
		yPos.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				feature.getGraphicalfeature().setY(newValue.doubleValue());
			}
	      });
		translateXProperty().bind(xPos);
		translateYProperty().bind(yPos);
	}
	
	private void bindWidth() {
		this.widthProperty().addListener(e -> {
			feature.getGraphicalfeature().setWidth(widthProperty().get());
		});
	}
	
	
	private void bindHeight() {
		this.heightProperty().addListener(e -> {
			feature.getGraphicalfeature().setHeight(heightProperty().get());
		});
	}

	 /**
	  * Creating the context menu for the FMEditor.
	  */
	 private void createContextMenu() {
		 FXGraphicalFeatureContextMenu fmeContextMenu = new FXGraphicalFeatureContextMenu(services.eventBroker, this);
		 VBox box = this;
		 setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
	            @Override
	            public void handle(ContextMenuEvent event) {
	            	if(event.getSource() instanceof FXGraphicalFeature) {
	            		view.setMousePosition(event.getSceneX(), event.getSceneY());
	            		modifyMenuItemAbstractConcrete(fmeContextMenu, (FXGraphicalFeature)event.getSource());
	            		modifyMenuItemFeatureVisibility(fmeContextMenu, (FXGraphicalFeature)event.getSource());
		            	fmeContextMenu.show(box, event.getScreenX(), event.getScreenY()); 
		            	event.consume();
	            	}
	            }
	            
	            // Change the menu entry to either change a feature to concrete or abstract
				private void modifyMenuItemAbstractConcrete(FXGraphicalFeatureContextMenu fmeContextMenu, FXGraphicalFeature fxfeature) {
            		if (!fxfeature.getFeature().isAbstract()) {
            			for (MenuItem m : fmeContextMenu.getItems()) {
            				if (m.getText() ==	FDStringTable.FX_FEATURE_CM_MAKE_CONCRETE) {
            					m.setText(FDStringTable.FX_FEATURE_CM_MAKE_ABSTRACT);
            				}
            			}
            		} else {
            			for (MenuItem m : fmeContextMenu.getItems()) {
            				if (m.getText() == FDStringTable.FX_FEATURE_CM_MAKE_ABSTRACT) {
            					m.setText(FDStringTable.FX_FEATURE_CM_MAKE_CONCRETE);
            				}
            			}
            		}
				}
				
				// Change the menu entry to either change a feature to hidden or visible
				private void modifyMenuItemFeatureVisibility(FXGraphicalFeatureContextMenu fmeContextMenu, FXGraphicalFeature fxfeature) {
            		if (!fxfeature.getFeature().isHidden()) {
            			for (MenuItem m : fmeContextMenu.getItems()) {
            				if (m.getText() == FDStringTable.FX_FEATURE_CM_MAKE_SUBFEATURES_VISIBLE) {
            					m.setText(FDStringTable.FX_FEATURE_CM_CHANGE_SUBFEATURES_VISIBILITY);
            				}
            			}
            		} else {
            			for (MenuItem m : fmeContextMenu.getItems()) {
            				if (m.getText() == FDStringTable.FX_FEATURE_CM_CHANGE_SUBFEATURES_VISIBILITY) {
            					m.setText(FDStringTable.FX_FEATURE_CM_MAKE_SUBFEATURES_VISIBLE);
            				}
            			}
            		}
				}
	        });
	 }
	
	 /**
	  * initialize the  graphical feature. 
	  */
	private void initGraphicalFeature() {
        setAlignment(Pos.TOP_CENTER);
		upperConnector = new FXFeatureUpperConnector(feature);
		lowerConnector = new FXFeatureLowerConnector(this);
		getChildren().add(upperConnector);
		createNameLabel();
		
		setTranslateX(feature.getGraphicalfeature().getX());
		setTranslateY(feature.getGraphicalfeature().getY());
		
		if(feature.isOr()) {
			setGroupVariability_OR();
		} else if (feature.isAlternative()) {
			setGroupVariability_ALTERNATIVE();;
		} else {
			setGroupVariability_AND();	// FIXME validate
		}
	}
	
	/**
	 * This method creates the name label of an feature.
	 */
	private void createNameLabel() {
		featureNameLabel = new Label(feature.getName());
		featureNameLabel.getStyleClass().add("feature");
		//Opens a Renaming dialog on double click 
		featureNameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				if(event.getClickCount() == 2) {
					services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_RENAME, feature);
					FMESimpleTextInputDialog dialog = new FMESimpleTextInputDialog(FDStringTable.FD_DIALOG_MENU_RENAME_FEATURE,feature.getName());
					String newName = dialog.show(event.getSceneX(), event.getSceneY());
					if(!newName.equals(feature.getName())) {
						feature.setName(newName);
						featureNameLabel.setText(newName);
						event.consume();
						services.eventBroker.send(FDEventTable.LOGGER_RENAMED_FEATURE, feature);
					}
				}
			}
		});
		getChildren().add(featureNameLabel);
	}
	
	/**
	 * Adding a Drag event to graphical feature 
	 */
	private void addDrag() {
		setOnMouseDragged(event -> {
			if(!event.isShiftDown() && event.isPrimaryButtonDown()) {
				toFront();
				xPos.set(event.getX() + this.getTranslateX() - getWidth() / 2);
				yPos.set(event.getY() + this.getTranslateY() - getHeight() / 2);
			}
			event.consume();
		});
	}

	
	private void addSelectionListener() {
		setOnMousePressed(event -> {
			if(event.isShiftDown()) {
				toFront();
				services.eventBroker.send(FDEventTable.SET_SELECTED_FEATURE, this);	
				for (ArtifactReference artifact : this.getFeature().getArtifactReferences()) {
					System.out.println("Referenced Artifact for ClearName: "+artifact.getArtifactClearName());
					for (String referencedID : artifact.getReferencedArtifactIDs()) {
						System.out.println("-> "+referencedID);
					}
					System.out.println("\n");
				}
			}
			event.consume();
		});
	}
	
	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public List<FXGraphicalFeature> getChildFeatures() {
		return childFeatures;
	}
	
	public void addChildFeature(FXGraphicalFeature fxFeature) {
		childFeatures.add(fxFeature);	
	}
	
	public void addChildFeatureFormated(FXGraphicalFeature fxFeature) {
		addChildFeature(fxFeature);
		translateChildren();
	}
	
	/**
	 * replacing children.
	 */
	private void translateChildren() {
		double xSum = 0;
		for(FXGraphicalFeature fxGf : childFeatures) {
			xSum += fxGf.getWidth();
		}
		xSum += PlacemantConsts.FEATURE_DEFAUL_VALUE;
		xSum += childFeatures.size() * PlacemantConsts.FEATURE_H_GAP_VALUE;
		double position = (getTranslateX() + getWidth()/2) - xSum / 2;
		double step = xSum / childFeatures.size();
		for(FXGraphicalFeature fxGf : childFeatures) {
			fxGf.getXPos().set(position);
			fxGf.getYPos().set(getTranslateY() + getHeight() + PlacemantConsts.FEATURE_V_GAP_VALUE);
			position += step;
		}
	}
	
	public void rename(String name) {
		this.featureNameLabel.setText(name);
	}
	
	public void setMandatory() {
		feature.setMandatory(true);
		upperConnector.styleForMandatory();
	}
	
	public void setOptional() {
		feature.setMandatory(false);
		upperConnector.styleForOptional();
	}
	
	public void setGroupVariability_ALTERNATIVE() {
		
		if (!feature.isAlternative()) {
			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP, feature);
			feature.setAlternative(true);
			feature.setOr(false);
			services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP, feature);			
		}
		if (feature.isAlternative()) {
			if(getChildren().contains(lowerConnector)) {
				getChildren().remove(lowerConnector);
			}
			lowerConnector = new FXFeatureLowerConnector(this);
			lowerConnector.drawAlternative();
			for(FXGraphicalFeature childFeatures : childFeatures) {
				childFeatures.removeUpper();
			}
			getChildren().addAll(lowerConnector);
		}
	}
	
	public void setGroupVariability_OR() {
		if (!feature.isOr()) {
			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP, feature);			
			feature.setAlternative(false);
			feature.setOr(true);
			services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP, feature);			
		} 			
		if (feature.isOr()) {
			if(getChildren().contains(lowerConnector)) {
				getChildren().remove(lowerConnector);
			}
			lowerConnector = new FXFeatureLowerConnector(this);
			lowerConnector.drawOr();
			lowerConnector.toBack();
			for(FXGraphicalFeature childFeatures : childFeatures) {
				childFeatures.removeUpper();
			}
			getChildren().addAll(lowerConnector);
		}			
	}
	
	public void setGroupVariability_AND() {
		if (feature.isOr() || feature.isAlternative()) {
			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP, feature);
			feature.setAlternative(false);
			feature.setOr(false);
			services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP, feature);
		}
		if (!feature.isOr() && !feature.isAlternative()) {
			if(getChildren().contains(lowerConnector)) {
				getChildren().remove(lowerConnector);
			}
			
			for(FXGraphicalFeature childFeature : childFeatures) {			
				childFeature.getChildren().add(0, childFeature.upperConnector);
			}
		}
	}
	
	public FXFeatureLowerConnector getLowerConnector() {
		return lowerConnector;
	}
	
	public void removeUpper() {
		getChildren().remove(upperConnector);
	}
	
	public DoubleProperty getXPos() {
		return xPos;
	}

	public DoubleProperty getYPos() {
		return yPos;
	}

	public FXGraphicalFeature getParentFxFeature() {
		return parentFxFeature;
	}

	public void setParentFxFeature(FXGraphicalFeature parentFxFeature) {
		this.parentFxFeature = parentFxFeature;
	}
	
	public Label getFeatureNameLabel() {
		return featureNameLabel;
	}
	public FXFeatureUpperConnector getUpperConnector() {
		return upperConnector;
	}
	
	@Override
	public String toString() {
		return this.feature.getName()+"\tID: "+this.feature.getIdentifier();
		
	}
}
