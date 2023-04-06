package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import java.util.ArrayList;
import java.util.List;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorToolbar;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.DragHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.KeyTranslateHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.ResetHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.SelectionAreaHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.ZoomHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.themes.DefaultTheme;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation.AnimationMap;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacemantConsts;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgoFactory;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgorithm;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class FMEditorPaneView extends BorderPane {

	public interface FMEditorPaneMouseHandler {
		public EventHandler<MouseEvent> resetHandler();

		public EventHandler<MouseEvent> dragHandler();

		public EventHandler<MouseEvent> selectionAreaHandler();

		public EventHandler<MouseEvent> primaryMouseBtnHandler();

		public EventHandler<ScrollEvent> zoomHandler();
	}

	// javafx widgets
	public Pane rootPane;
	public Pane gesturePane;
	public Rectangle selectionRectangle;

	private FMEditorToolbar toolbar;
	private FMEditorPaneMouseHandler mouseEventHandler;

	// lists
	public List<FXGraphicalFeature> selectedFeatures;
	public List<FXGraphicalFeature> componentFeatureList;
	public AnimationMap labelBorderAnimationMap;

	// model
	public FeatureDiagramm currentModel;
	public FXGraphicalFeature currentFeature;


	private FXGraphicalFeature rootFeature;

	public FMEditorPaneView(FMEditorToolbar toolbar, FMEditorPaneMouseHandler mouseEventHandler) {
		this.toolbar = toolbar;
		this.mouseEventHandler = mouseEventHandler;
		
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
		this.setTop(this.toolbar);
		
		this.gesturePane = new Pane();
		this.gesturePane.setStyle("-fx-background-color: white;");
		this.setCenter(gesturePane);
		
		this.rootPane = new Pane();
		this.rootPane.setStyle("-fx-background-color: white;");
		this.gesturePane.getChildren().add(rootPane);
		
		// Mouse handler to zoom in and out of the rootPane
		ZoomHandler zoomHandler = new ZoomHandler(this.rootPane);
		gesturePane.addEventHandler(ScrollEvent.ANY, zoomHandler);
		// move rootPane with wasd or arrow keys
		this.addEventHandler(KeyEvent.ANY, new KeyTranslateHandler(this.rootPane, 10d));
		// move rootPane with mouse when control key is pressed
		DragHandler dragHandler = new DragHandler(this.rootPane);
		gesturePane.addEventHandler(MouseEvent.ANY, dragHandler);

		createSelectionRectangle(gesturePane);
		gesturePane.addEventHandler(MouseEvent.ANY, new SelectionAreaHandler(selectionRectangle, gesturePane, rootPane));
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
		placement.format(this.rootFeature);
		// Reset the translate offset so that large feature diagrams do not
		// disappear after formatting
		double centerX = gesturePane.getWidth() / 2 - rootPane.getWidth() / 2;
		double centerY = gesturePane.getHeight() / 4 - rootPane.getHeight() / 2;
		this.rootPane.setLayoutX(centerX);
		this.rootPane.setLayoutY(centerY);
		this.setRootFeature(this.rootFeature);
	}

	public void setRootFeature(FXGraphicalFeature root) {
		this.clear();
		this.rootFeature = root;
		this.rootPane.getChildren().add(root);
		addChangeListener(root);
		insertChildren(root);
	}

	/**
	 * This method clears the FeatureDiagramEditor
	 */
	private void clear() {
		this.rootPane.getChildren().clear();
	}

	private void addChangeListener(FXGraphicalFeature feature) {
		feature.addListener(o -> {
			feature.getChildFeatures().forEach(child -> remove(child));
			insertChildren(feature);
		});
	}

	public void insertFeatureBelow(FXGraphicalFeature parent, FXGraphicalFeature child) {
		// insert child
		this.rootPane.getChildren().add(child);
		addChangeListener(child);
		connectFeatures(parent, child);
		// TODO set x,y positions of features depending on size

		insertChildren(child);
	}

	private void insertChildren(FXGraphicalFeature feature) {
		for (FXGraphicalFeature child : feature.getChildFeatures()) {
			insertFeatureBelow(feature, child);
		}
	}

	private void connectFeatures(FXGraphicalFeature parent, FXGraphicalFeature child) {
		final Line line = new Line();
		// initial bind
		line.startXProperty().bind(parent.xPos.add(parent.getWidth() / 2));
		line.startYProperty().bind(parent.yPos.add(parent.getHeight() - parent.lowerConnector.getRadiusY()));
		line.endYProperty().bind(child.translateYProperty());
		line.endXProperty().bind(child.translateXProperty().add(child.widthProperty().doubleValue() / 2));

		// after update size
		parent.widthProperty().addListener(e -> {
			line.startXProperty().unbind();
			line.startXProperty().bind(parent.xPos.add(parent.getWidth() / 2));
		});

		parent.heightProperty().addListener(e -> {
			line.startYProperty().unbind();
			line.startYProperty().bind(parent.yPos.add(parent.getHeight() - parent.lowerConnector.getRadiusY()));
		});

		// if height changes bind with new height.
		child.heightProperty().addListener(e -> {
			line.endYProperty().unbind();
			line.endYProperty().bind(child.translateYProperty());
		});

		// if size changes bind with new width.
		child.widthProperty().addListener(e -> {
			line.endXProperty().unbind();
			line.endXProperty().bind(child.translateXProperty().add(child.widthProperty().doubleValue() / 2));
		});
		parent.childConnections.add(line);
		this.rootPane.getChildren().add(line);
	}

	public void remove(FXGraphicalFeature feature) {
		this.rootPane.getChildren().remove(feature);
		this.rootPane.getChildren().removeAll(feature.childConnections);
		feature.getChildFeatures().forEach(child -> remove(child));
	}

}