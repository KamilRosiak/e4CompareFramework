package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.ComponentFeature;
import FeatureDiagram.ConfigurationFeature;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.DefaultFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleTextInputDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacemantConsts;
import featureConfiguration.FeatureConfiguration;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;


public class FXGraphicalFeature extends VBox  {
	public FXFeatureNameLabel featureNameLabel;
	public FXFeatureLowerConnector lowerConnector;
	public FXFeatureUpperConnector upperConnector;
	public DoubleProperty xPos, yPos;
	
	private IFeature feature = new DefaultFeature();
	private FXGraphicalFeature parentFxFeature;
	private List<FXGraphicalFeature> childFeatures = new ArrayList<>();;
	
	// TODO remove
	private ServiceContainer services;
	private FMEditorView view;
	
	public FXGraphicalFeature(IFeature feature) {
		this.feature = feature;
		createUI();
	}
	
	public FXGraphicalFeature(String name) {
		this(new Feature(name));
	}
	
	public void setVariability(Variability variability) {
		// TODO set the feature
		this.upperConnector.setVariability(variability);
	}
	
	public void setGroupVariability(GroupVariability groupVariability) {
		switch (groupVariability) {
		case AND:
			this.setGroupVariability_AND();
			break;
		case OR:
			this.setGroupVariability_OR();
			break;
		case XOR:
			this.setGroupVariability_ALTERNATIVE();
			break;		
		}
	}

//	public void addConfigLabel(String name) {
//		Label configLabel = new Label(name);
//		configLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//			if(event.getButton().equals(MouseButton.PRIMARY)) {
//				if(event.getClickCount() == 2) {
//					services.eventBroker.send(FDEventTable.SELECT_CONFIGURATION_EVENT, this);
//					event.consume();
//				}
//			}
//		});
//		configLabel.getStyleClass().add("componentFeature");
//		getChildren().add(configLabel);
//	}

	private void createUI() {
		setSpacing(0);
		initGraphicalFeature();
		addDrag();
		addSelectionListener();
		createContextMenu();
	}
	
	 /**
	  * initialize the  graphical feature. 
	  */
	private void initGraphicalFeature() {
		setAlignment(Pos.TOP_CENTER);
		this.upperConnector = new FXFeatureUpperConnector(feature);
		this.lowerConnector = new FXFeatureLowerConnector(this);
		this.featureNameLabel = new FXFeatureNameLabel(feature);
		getChildren().addAll(this.upperConnector, this.lowerConnector, this.featureNameLabel);
		
		this.setGroupVariability(feature.getGroupVariability());
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
//				for (ArtifactReference artifact : this.getFeature().getArtifactReferences()) {
//					System.out.println("Referenced Artifact for ClearName: "+artifact.getArtifactClearName());
//					for (String referencedID : artifact.getReferencedArtifactIDs()) {
//						System.out.println("-> "+referencedID);
//					}
//					System.out.println("\n");
//				}
			}
			event.consume();
		});
	}
	
	 /**
	  * Creating the context menu for the FMEditor.
	  */
	 private void createContextMenu() {
		 FXGraphicalFeatureContextMenu fmeContextMenu = new FXGraphicalFeatureContextMenu(services, this);
		 VBox box = this;
		 setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
	            @Override
	            public void handle(ContextMenuEvent event) {
	            	if(event.getSource() instanceof FXGraphicalFeature) {
	            		//view.setMousePosition(event.getSceneX(), event.getSceneY());
	            		//modifyMenuItemAbstractConcrete(fmeContextMenu, (FXGraphicalFeature)event.getSource());
	            		//modifyMenuItemFeatureVisibility(fmeContextMenu, (FXGraphicalFeature)event.getSource());
		            	fmeContextMenu.show(box, event.getScreenX(), event.getScreenY()); 
		            	event.consume();
	            	}
	            }
	            
	            // Change the menu entry to either change a feature to concrete or abstract
//				private void modifyMenuItemAbstractConcrete(FXGraphicalFeatureContextMenu fmeContextMenu, FXGraphicalFeature fxfeature) {
//	           		if (!fxfeature.getFeature().isAbstract()) {
//	           			for (MenuItem m : fmeContextMenu.getItems()) {
//	           				if (m.getText() ==	FDStringTable.FX_FEATURE_CM_MAKE_CONCRETE) {
//	           					m.setText(FDStringTable.FX_FEATURE_CM_MAKE_ABSTRACT);
//	           				}
//	           			}
//	           		} else {
//	           			for (MenuItem m : fmeContextMenu.getItems()) {
//	           				if (m.getText() == FDStringTable.FX_FEATURE_CM_MAKE_ABSTRACT) {
//	           					m.setText(FDStringTable.FX_FEATURE_CM_MAKE_CONCRETE);
//	           				}
//	           			}
//	           		}
//				}
//				
//				// Change the menu entry to either change a feature to hidden or visible
//				private void modifyMenuItemFeatureVisibility(FXGraphicalFeatureContextMenu fmeContextMenu, FXGraphicalFeature fxfeature) {
//	           		if (!fxfeature.getFeature().isHidden()) {
//	           			for (MenuItem m : fmeContextMenu.getItems()) {
//	           				if (m.getText() == FDStringTable.FX_FEATURE_CM_MAKE_SUBFEATURES_VISIBLE) {
//	           					m.setText(FDStringTable.FX_FEATURE_CM_CHANGE_SUBFEATURES_VISIBILITY);
//	           				}
//	           			}
//	           		} else {
//	           			for (MenuItem m : fmeContextMenu.getItems()) {
//	           				if (m.getText() == FDStringTable.FX_FEATURE_CM_CHANGE_SUBFEATURES_VISIBILITY) {
//	           					m.setText(FDStringTable.FX_FEATURE_CM_MAKE_SUBFEATURES_VISIBLE);
//	           				}
//	           			}
//	           		}
//				}
	        });
	 }
	
	public void setBackgroundColor(Color color) {
		int hue = (int) color.getHue();
		double colorSaturation = color.getSaturation();
		int saturation = (int) (100 * colorSaturation);
		double colorBrightness = color.getBrightness();
		int brightness = (int) (100 * colorBrightness);
		String css = "-fx-background-color: \r\n" + 
				"        #000,\r\n" + 
				"        hsb("+ hue + ", " + saturation + "%, " + brightness + "%);\r\n" + 
				"    -fx-background-insets: 0,1,1,2;\r\n" + 
				"    -fx-background-radius: 2,1,1,1;\r\n" + 
				"    -fx-padding: 2 5 2 5;\r\n" + 
				"    -fx-text-fill: white;\r\n" + 
				"    -fx-font-family: Monospaced;\r\n" + 
				"	-fx-font-size: 10px;\r\n" + 
				"	-fx-border-width: 1px;";
		featureNameLabel.setStyle(css);
	}
	
	public IFeature getFeature() {
		return feature;
	}
	
	public FMEditorView getView() {
		return view;
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
			fxGf.xPos.set(position);
			fxGf.yPos.set(getTranslateY() + getHeight() + PlacemantConsts.FEATURE_V_GAP_VALUE);
			position += step;
		}
	}
	
	public void setName(String name) {
		//feature.setName(name);
		featureNameLabel.setText(name);
		services.eventBroker.send(FDEventTable.LOGGER_RENAMED_FEATURE, feature);
	}
	
	public void setMandatory() {
		//feature.setMandatory(true);
		//upperConnector.styleForMandatory();
	}
	
	public void setOptional() {
		//feature.setMandatory(false);
		//upperConnector.styleForOptional();
	}
	
	void setGroupVariability_ALTERNATIVE() {
		
//		if (!feature.isAlternative()) {
//			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP, feature);
//			feature.setAlternative(true);
//			feature.setOr(false);
//			services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP, feature);			
//		}
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
	
	void setGroupVariability_OR() {
//		if (!feature.isOr()) {
//			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP, feature);			
//			feature.setAlternative(false);
//			feature.setOr(true);
//			services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP, feature);			
//		}
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
	
	void setGroupVariability_AND() {
//		if (feature.isOr() || feature.isAlternative()) {
//			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP, feature);
//			feature.setAlternative(false);
//			feature.setOr(false);
//			services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP, feature);
//		}
		if(getChildren().contains(lowerConnector)) {
			getChildren().remove(lowerConnector);
		}
		
		for(FXGraphicalFeature childFeature : childFeatures) {			
			childFeature.getChildren().add(0, childFeature.upperConnector);
		}
	}
	
	public void removeUpper() {
		getChildren().remove(upperConnector);
	}

	public FXGraphicalFeature getParentFxFeature() {
		return parentFxFeature;
	}

	public void setParentFxFeature(FXGraphicalFeature parentFxFeature) {
		this.parentFxFeature = parentFxFeature;
	}
	
	@Override
	public String toString() {
		return this.feature.getName()+"\tID: "+ this.feature.getUuid();
		
	}
}
