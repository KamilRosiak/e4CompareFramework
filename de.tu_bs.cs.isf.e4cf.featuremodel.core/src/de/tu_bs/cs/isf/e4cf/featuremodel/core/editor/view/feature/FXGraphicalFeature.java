package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.StylableFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.ChangeList;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation.DashedBorderAnimation;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation.INodeAnimator;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class FXGraphicalFeature extends VBox implements Observable {
	public FXFeatureNameLabel featureNameLabel;
	public FXFeatureUpperConnector upperConnector;
	public FXFeatureLowerConnector lowerConnector;
	public Map<FXGraphicalFeature, Line> childConnections = new HashMap<>();
	public DoubleProperty xPos = new SimpleDoubleProperty(), yPos = new SimpleDoubleProperty();

	private IFeature feature = new Feature();
	public ObjectProperty<FXGraphicalFeature> parentFeatureProperty = new SimpleObjectProperty<>();
	private INodeAnimator animator;

	private ObservableList<FXGraphicalFeature> childFeatures = new ChangeList<>();
	private List<InvalidationListener> listeners = new ArrayList<>();

	// TODO remove
	private ServiceContainer services;
	private FMEditorView view;

	public FXGraphicalFeature(IFeature feature) {
		this.feature = feature;
		createUI();
		if (feature instanceof StylableFeature) {
			((StylableFeature) feature).style(this);
		}
	}

	public FXGraphicalFeature(String name) {
		this(new Feature(name));
	}

	public FXGraphicalFeature() {
		this(new Feature());
	}

	public void setVariability(Variability variability) {
		if (variability.equals(Variability.DEFAULT)) {
			this.removeUpper();
		} else {
			this.upperConnector.setVariability(variability);
			this.getChildren().add(0, upperConnector);
		}
		this.getFeature().setVariability(variability);
	}

	public void setGroupVariability(GroupVariability groupVariability) {
		switch (groupVariability) {
		case DEFAULT:
			this.setGroupVariability_Default();
			break;
		case OR:
			this.setGroupVariability_OR();
			break;
		case ALTERNATIVE:
			this.setGroupVariability_ALTERNATIVE();
			break;
		}
		this.getFeature().setGroupVariability(groupVariability);
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

		xPos.bind(this.layoutXProperty());
		yPos.bind(this.layoutYProperty());

		this.widthProperty().addListener(l -> this.invalidate());
		this.heightProperty().addListener(l -> this.invalidate());
	}

	/**
	 * initialize the graphical feature.
	 */
	private void initGraphicalFeature() {
		setAlignment(Pos.TOP_CENTER);
		this.upperConnector = new FXFeatureUpperConnector(feature);
		this.lowerConnector = new FXFeatureLowerConnector(this);
		this.featureNameLabel = new FXFeatureNameLabel(this);
		getChildren().addAll(this.upperConnector, this.lowerConnector, this.featureNameLabel);
	}

	/**
	 * Adding a Drag event to graphical feature
	 */
	private void addDrag() {
		setOnMouseDragged(event -> {
			if (!event.isShiftDown() && event.isPrimaryButtonDown()) {
				toFront();
				double x = event.getX() + this.getLayoutX() - getWidth() / 2;
				double y = event.getY() + this.getLayoutY() - getHeight() / 2;
				relocate(x, y);
			}
			event.consume();
		});
	}

	private void addSelectionListener() {
		setOnMousePressed(event -> {
			if (event.isShiftDown()) {
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
		VBox currentGraFeature = this;
		setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			@Override
			public void handle(ContextMenuEvent event) {
				if (event.getSource() instanceof FXGraphicalFeature) {
					event.consume();
					fmeContextMenu.show(currentGraFeature, event.getScreenX(), event.getScreenY());
				}
			}
		});
	}

	public void setBackgroundColor(Color color) {
		int hue = (int) color.getHue();
		double colorSaturation = color.getSaturation();
		int saturation = (int) (100 * colorSaturation);
		double colorBrightness = color.getBrightness();
		int brightness = (int) (100 * colorBrightness);
		String css = "-fx-background-color: \r\n" + "        #000,\r\n" + "        hsb(" + hue + ", " + saturation
				+ "%, " + brightness + "%);\r\n" + "    -fx-background-insets: 0,1,1,2;\r\n"
				+ "    -fx-background-radius: 2,1,1,1;\r\n" + "    -fx-padding: 2 5 2 5;\r\n"
				+ "    -fx-text-fill: white;\r\n" + "    -fx-font-family: Monospaced;\r\n"
				+ "	-fx-font-size: 10px;\r\n" + "	-fx-border-width: 1px;";
		featureNameLabel.setStyle(css);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		this.listeners.remove(listener);
	}

	private void invalidate() {
		this.listeners.forEach(l -> l.invalidated(this));
	}

	public IFeature getFeature() {
		return feature;
	}

	public FMEditorView getView() {
		return view;
	}

	public void setFeature(IFeature feature) {
		this.feature = feature;
	}

	public ObservableList<FXGraphicalFeature> getChildFeatures() {
		return childFeatures;
	}

	public void addChildFeature(FXGraphicalFeature fxFeature) {
		fxFeature.setParentFxFeature(this);
		this.childFeatures.add(fxFeature);
		feature.getChildren().add(fxFeature.getFeature());
	}
	
	public boolean removeChildFeature(FXGraphicalFeature fxFeature) {
		boolean removed = this.childFeatures.remove(fxFeature);
		if (removed) {
			this.getFeature().removeChild(fxFeature.getFeature());
		}
		return removed;
	}

	public void setName(String name) {
		// feature.setName(name);
		featureNameLabel.setText(name);
		feature.setName(name);
		services.eventBroker.send(FDEventTable.LOGGER_RENAMED_FEATURE, feature);
	}
	
	void drawGroupVariability() {
		switch (feature.getGroupVariability()) {
		case ALTERNATIVE:
			this.lowerConnector.drawAlternative();
			break;
		case DEFAULT:
			break;
		case OR:
			this.lowerConnector.drawOr();
			break;
		}
	}

	void setGroupVariability_ALTERNATIVE() {

//		if (!feature.isAlternative()) {
//			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP, feature);
//			feature.setAlternative(true);
//			feature.setOr(false);
//			services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP, feature);			
//		}
		if (getChildren().contains(lowerConnector)) {
			getChildren().remove(lowerConnector);
		}
		lowerConnector = new FXFeatureLowerConnector(this);
		lowerConnector.drawAlternative();
		for (FXGraphicalFeature childFeatures : childFeatures) {
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
		if (getChildren().contains(lowerConnector)) {
			getChildren().remove(lowerConnector);
		}
		lowerConnector = new FXFeatureLowerConnector(this);
		lowerConnector.drawOr();
		lowerConnector.toBack();
		for (FXGraphicalFeature childFeatures : childFeatures) {
			childFeatures.removeUpper();
		}
		getChildren().addAll(lowerConnector);
	}

	void setGroupVariability_Default() {
//		if (feature.isOr() || feature.isAlternative()) {
//			services.eventBroker.send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP, feature);
//			feature.setAlternative(false);
//			feature.setOr(false);
//			services.eventBroker.send(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP, feature);
//		}
		if (getChildren().contains(lowerConnector)) {
			getChildren().remove(lowerConnector);
		}
		lowerConnector = new FXFeatureLowerConnector(this);
		for (FXGraphicalFeature childFeature : childFeatures) {
			if (!childFeature.getChildren().contains(childFeature.upperConnector)) {
				childFeature.getChildren().add(0, childFeature.upperConnector);
			}
		}
	}

	public void removeUpper() {
		getChildren().remove(upperConnector);
	}

	public FXGraphicalFeature getParentFxFeature() {
		return parentFeatureProperty.get();
	}

	public void setParentFxFeature(FXGraphicalFeature parentFxFeature) {
		this.parentFeatureProperty.set(parentFxFeature);
		this.getFeature().setParent(parentFxFeature.getFeature());
	}

	public void setPosition(double x, double y) {
		this.layoutXProperty().set(x);
		this.layoutYProperty().set(y);
	}

	@Override
	public String toString() {
		return this.feature.getName() + "\tID: " + this.feature.getUuid();
	}

	public void toggleSelected() {
		if (this.animator != null) {
			featureNameLabel.getStyleClass().remove("featureIsPartOfSelection");
			this.animator.stop();
			this.animator = null;
		} else {
			featureNameLabel.getStyleClass().add("featureIsPartOfSelection");
			this.animator = new DashedBorderAnimation(featureNameLabel, 10, 5, 2);
			this.animator.start();
		}
	}

	public void setAbstract(boolean isAbstract) {
		this.feature.setAbstract(isAbstract);
		this.featureNameLabel.restyle();
	}

}
