package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

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
	}

	public FXGraphicalFeature(String name) {
		this(new Feature(name));
	}

	public FXGraphicalFeature() {
		this(new Feature());
		setAbstract(false);
		this.feature.setComponent(false);
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
		getChildren().clear();
		getChildren().addAll(this.upperConnector, this.lowerConnector, this.featureNameLabel);
		// set selected background color if available
		if (feature.getColor().isPresent()) {
			System.out.println(feature.getColor().get());
			if (feature.getColor().get().toString().equals("0x000000ff")) {

			}
			this.setBackgroundColor(feature.getColor().get());
		} else if (feature instanceof StylableFeature) {
			((StylableFeature) feature).style(this);
		}
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
		getFeature().setAbstract(isAbstract);
		this.featureNameLabel.restyle();
	}

	@Optional
	@Inject
	public void updateFeature(@UIEventTopic(FDEventTable.UPDATE_FEATURE) IFeature feature) {
		if (this.feature.equals(feature)) {
			initGraphicalFeature();
		}
	}

}
