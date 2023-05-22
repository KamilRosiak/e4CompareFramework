package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorToolbar;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.DragHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.HotkeyHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.KeyTranslateHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.SelectionAreaHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.ZoomHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.themes.DefaultTheme;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation.AnimationMap;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacemantConsts;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgoFactory;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgorithm;
import javafx.collections.ListChangeListener;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public class FMEditorPaneView extends BorderPane {
	// javafx widgets
	public Pane rootPane;
	public Pane gesturePane;
	public Rectangle selectionRectangle;
	private FMEditorToolbar toolbar;

	// collections
	public List<FXGraphicalFeature> selectedFeatures;
	public List<FXGraphicalFeature> componentFeatureList;
	public AnimationMap labelBorderAnimationMap;

	// model
	public FeatureDiagramm currentModel;
	public FXGraphicalFeature currentFeature;
	private FXGraphicalFeature rootFeature;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public FMEditorPaneView(FMEditorToolbar toolbar) {
		this.toolbar = toolbar;
		this.labelBorderAnimationMap = new AnimationMap();
		this.selectedFeatures = new ArrayList<FXGraphicalFeature>();
		this.componentFeatureList = new ArrayList<FXGraphicalFeature>();
		this.rootFeature = new FXGraphicalFeature();
		setTheme();
		this.constructUI();
	}

	/**
	 * This method creates the Scene and adds all Pane and Listener to it.
	 */
	private void constructUI() {
		this.gesturePane = new Pane();
		this.gesturePane.setStyle("-fx-background-color: white;");
		this.setCenter(gesturePane);
		// add after gesturePane to be on top (z-index)
		this.setTop(this.toolbar);

		this.rootPane = new Pane();
		this.rootPane.setStyle("-fx-background-color: white;");
		this.gesturePane.getChildren().add(rootPane);

		gesturePane.widthProperty().addListener(l -> {
			formatDiagram();
		});
		gesturePane.heightProperty().addListener(l -> {
			formatDiagram();
		});

		// Mouse handler to zoom in and out of the rootPane
		ZoomHandler zoomHandler = new ZoomHandler(this.rootPane);
		gesturePane.addEventHandler(ScrollEvent.ANY, zoomHandler);
		// move rootPane with wasd or arrow keys
		this.addEventHandler(KeyEvent.ANY, new KeyTranslateHandler(this.rootPane, 10d));
		// move rootPane with mouse when control key is pressed
		DragHandler dragHandler = new DragHandler(this.rootPane, e -> e.isControlDown() && e.isPrimaryButtonDown());
		gesturePane.addEventHandler(MouseEvent.ANY, dragHandler);
		// toggle feature selection with a selection rectangle
		createSelectionRectangle(gesturePane);
		gesturePane.addEventHandler(MouseEvent.ANY, new SelectionAreaHandler(selectionRectangle, rootPane));
		setOnKeyPressed(new HotkeyHandler(this));
	}

	/**
	 * This method creates the selection rectangle.
	 */
	private void createSelectionRectangle(Pane parent) {
		this.selectionRectangle = new Rectangle(20, 20, Color.TRANSPARENT);
		parent.getChildren().add(this.selectionRectangle);
		this.selectionRectangle.setDisable(true);
		this.selectionRectangle.getStrokeDashArray().addAll(10d, 5d, 10d);
	}

	public void setTheme() {
		// set theme from preferences
		// TODO add parameter to select specific theme
		String cssLocation = PreferencesUtil
				.getValueWithDefault(FDStringTable.BUNDLE_NAME, FDStringTable.FME_THEME_KEY, DefaultTheme.DEFAULT_THEME)
				.getStringValue();
		this.getStylesheets().setAll(cssLocation);
	}

	public FeatureDiagramm currentModel() {
		return this.currentModel;
	}

	public void formatDiagram() {
		PlacementAlgorithm placement = PlacementAlgoFactory.getPlacementAlgorithm(PlacemantConsts.ABEGO_PLACEMENT);
		Pair<Double, Double> sizeXY = placement.format(this.rootFeature);

		double centerX = gesturePane.getWidth() / 2 - sizeXY.getKey() / 2;
		double centerY = gesturePane.getHeight() / 4;
		this.rootPane.setLayoutX(centerX);
		this.rootPane.setLayoutY(centerY);
	}

	public void setRootFeature(FXGraphicalFeature root) {
		this.clear();
		this.rootFeature = root;
		this.rootPane.getChildren().add(root);
		addChangeListener(root);
		insertChildren(root);
		root.setGroupVariability(root.getFeature().getGroupVariability());
	}

	/**
	 * This method clears the FeatureDiagramEditor
	 */
	private void clear() {
		this.rootPane.getChildren().clear();
	}

	private void addChangeListener(FXGraphicalFeature feature) {
		feature.addListener(l -> this.formatDiagram());

		FMEditorPaneView view = this;
		feature.getChildFeatures().addListener(new ListChangeListener<FXGraphicalFeature>() {
			@Override
			public void onChanged(Change<? extends FXGraphicalFeature> change) {
				while (change.next()) {
					if (change.wasAdded()) {
						change.getAddedSubList()
								.forEach(feature -> view.insertFeatureBelow(feature.getParentFxFeature(), feature));
					} else if (change.wasRemoved()) {
						change.getRemoved().forEach(view::remove);
					}
				}
				scheduler.schedule(view::formatDiagram, 5, TimeUnit.MILLISECONDS);
			}
		});
	}

	private void insertChildren(FXGraphicalFeature feature) {
		for (FXGraphicalFeature child : feature.getChildFeatures()) {
			insertFeatureBelow(feature, child);
		}
	}

	public void insertFeatureBelow(FXGraphicalFeature parent, FXGraphicalFeature child) {
		// insert child
		this.rootPane.getChildren().add(child);
		addChangeListener(child);
		connectFeatures(parent, child);
//		if (!child.getFeature().getGroupVariability().equals(GroupVariability.DEFAULT)) {
//			child.drawGroupVariability();
//		}
		insertChildren(child);
		child.setGroupVariability(child.getFeature().getGroupVariability());
	}

	private void connectFeatures(FXGraphicalFeature parent, FXGraphicalFeature child) {
		Line line = new Line();
		// initial bind
		line.startXProperty().bind(parent.layoutXProperty().add(parent.widthProperty().divide(2.0)));
		line.startYProperty().bind(parent.layoutYProperty()
				.add(parent.heightProperty().subtract(parent.lowerConnector.radiusYProperty())));
		line.endXProperty().bind(child.layoutXProperty().add(child.widthProperty().divide(2.0)));
		line.endYProperty().bind(child.layoutYProperty());
		
		parent.getChildFeatures().addListener((ListChangeListener<FXGraphicalFeature>) change -> {
			while(change.next()) {
				if (change.wasRemoved()) {
					for (FXGraphicalFeature removedChild : change.getRemoved()) {
						if (removedChild.equals(child)) {
							this.rootPane.getChildren().remove(line);
						}
					}
				}
			}
		});
		child.parentFeatureProperty.addListener((obs, oldVal, newVal) -> {
			if (!newVal.equals(parent)) {
				this.rootPane.getChildren().remove(line);
			}
		});

		parent.childConnections.put(child, line);
		this.rootPane.getChildren().add(line);
	}

	/**
	 * Removes a feature and its children recursively
	 * 
	 * @param feature FXGraphicalFeature to remove from the editor
	 */
	public void remove(FXGraphicalFeature feature) {
		this.rootPane.getChildren().remove(feature);
		this.rootPane.getChildren().removeAll(feature.childConnections.values());
		feature.getChildFeatures().forEach(child -> remove(child));
	}

}