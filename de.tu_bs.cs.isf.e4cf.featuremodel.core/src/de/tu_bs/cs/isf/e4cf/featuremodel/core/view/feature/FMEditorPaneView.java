package de.tu_bs.cs.isf.e4cf.featuremodel.core.view.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.DragHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.KeyTranslateHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.PrimaryMouseButtonClickedHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.ResetHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.SelectionAreaHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.ZoomHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.theme.themes.DefaultTheme;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.animation.AnimationMap;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.toolbar.FMEditorToolbar;
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
	public Rectangle selectionRectangle;
	
	private FMEditorToolbar toolbar;
	private FMEditorPaneMouseHandler mouseEventHandler;
	
	// lists
	public List<FXGraphicalFeature> selectedFeatures;
	public List<FXGraphicalFeature> featureList;
	public List<FXGraphicalFeature> componentFeatureList;
	
	// random stuff
	public Map<FXGraphicalFeature, Line> featureLineMap;
	public AnimationMap labelBorderAnimationMap;
	
	// model
	public FeatureDiagramm currentModel;
	public FXGraphicalFeature currentFeature;

	public FMEditorPaneView(FMEditorToolbar toolbar, FMEditorPaneMouseHandler mouseEventHandler) {
		this.toolbar = toolbar;
		this.mouseEventHandler = mouseEventHandler;
		
		this.constructUI();
	}
	
	/**
	 * This method creates the Scene and adds all Pane and Listener to it.
	 */
	private void constructUI() {
		initDataStructure();
		this.rootPane = new Pane();
		this.rootPane.setStyle("-fx-background-color: white;");
		

		Pane gesturePane = new Pane(this.rootPane);
		gesturePane.setStyle("-fx-background-color: white;");

		this.setCenter(gesturePane);
		toolbar.init(this);
		this.setTop(this.toolbar);

		// Creating and adding the mouse handler that allows zooming in and out with the
		// mouse wheel.
		ZoomHandler zoomHandler = new ZoomHandler(this.rootPane);
		gesturePane.addEventHandler(ScrollEvent.ANY, zoomHandler);

		// creating the selection rectangle
		createSelectionRectangle(gesturePane);

		// set theme from preferences
		setTheme(PreferencesUtil
				.getValueWithDefault(FDStringTable.BUNDLE_NAME, FDStringTable.FME_THEME_KEY, DefaultTheme.DEFAULT_THEME)
				.getStringValue());
		this.addEventHandler(KeyEvent.ANY, new KeyTranslateHandler(this.rootPane, 10d));

		gesturePane.addEventHandler(MouseEvent.MOUSE_PRESSED, this.mouseEventHandler.primaryMouseBtnHandler());

		((SelectionAreaHandler) this.mouseEventHandler.selectionAreaHandler()).init(gesturePane, this.rootPane, this.featureList, this.selectionRectangle,
				(PrimaryMouseButtonClickedHandler) this.mouseEventHandler.primaryMouseBtnHandler());
		gesturePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this.mouseEventHandler.selectionAreaHandler());

		DragHandler dragHandler = new DragHandler(this.rootPane);
		gesturePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);

		ResetHandler resetHandler = new ResetHandler(dragHandler, (SelectionAreaHandler) this.mouseEventHandler.selectionAreaHandler());
		gesturePane.addEventFilter(MouseEvent.MOUSE_RELEASED, this.mouseEventHandler.resetHandler());
		dragHandler.resetLastPosition();

		// translate root pane to keep root feature node centered, as long the pane
		// hasn't been moved before
		this.widthProperty().addListener((obs, oldVal, newVal) -> {
			this.rootPane.setTranslateX(this.rootPane.getTranslateX() + (newVal.doubleValue() - oldVal.doubleValue()) / 2);
		});
		this.heightProperty().addListener((obs, oldVal, newVal) -> {
			this.rootPane.setTranslateY(this.rootPane.getTranslateY() + (newVal.doubleValue() - oldVal.doubleValue()) / 2);
		});
		
		this.rootPane.setTranslateX(-30);
		this.rootPane.setTranslateY(-30);
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
	
	/**
	 * This method initiates the data structures of the FeatureDiagram Editor
	 */
	private void initDataStructure() {
		this.featureList = new ArrayList<FXGraphicalFeature>();
		this.featureLineMap = new HashMap<FXGraphicalFeature, Line>();
		this.labelBorderAnimationMap = new AnimationMap();
		this.selectedFeatures = new ArrayList<FXGraphicalFeature>();
		this.componentFeatureList = new ArrayList<FXGraphicalFeature>();
	}
	
	public void setTheme(String cssLocation) {
		this.getStylesheets().setAll(cssLocation);
	}
	
	public FeatureDiagramm currentModel() {
		return this.currentModel;
	}
}