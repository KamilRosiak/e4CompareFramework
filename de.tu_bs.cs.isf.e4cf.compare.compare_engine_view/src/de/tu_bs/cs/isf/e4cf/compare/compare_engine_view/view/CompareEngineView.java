package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.parts.TreeTable;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.collections.FXCollections;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class CompareEngineView {
	public static final String COMPARE_ENGINE_CSS_LOCATION ="css/compare_engine.css";
	private StackPane root;
	private Scene scene;
	private TreeTable table;
	
	public CompareEngineView(FXCanvas canvas, ServiceContainer services) {
		canvas.setScene(createScene(canvas));
	}
	
	private Scene createScene(FXCanvas canvas) {
        root = new StackPane();
        root.setStyle("-fx-background-color: white;"); 
        scene = new Scene(root);
        scene.getStylesheets().setAll(COMPARE_ENGINE_CSS_LOCATION);
        // creation all parts of the view by calling the specific methods 
        root.getChildren().addAll(createArtifactList(), createMatchingArea(), createComparisonArea());
		return scene;
	}

	private StackPane createArtifactList() {
		table = new TreeTable();
		StackPane pane = new StackPane();
		pane.getChildren().add(table);
		return pane;
	}
	
	private StackPane createMatchingArea() {
		StackPane pane = new StackPane();
		//ComboBox<AbstractMatcher> matcherSelection = new ComboBox<AbstractMatcher>();
		return pane;
	}
	
	private HBox createComparisonArea() {
		HBox pane = new HBox();
		pane.setStyle("-fx-background-color: blue;"); 
		Button compareButton = JavaFXBuilder.createButton("Compare", e -> {
			
		});
		pane.getChildren().add(compareButton);
		return pane;
	}

	public void showArtifacts(List<Tree> artifacts) {
		table.getItems().clear();
		table.setItems(FXCollections.observableArrayList(artifacts));
	}
}