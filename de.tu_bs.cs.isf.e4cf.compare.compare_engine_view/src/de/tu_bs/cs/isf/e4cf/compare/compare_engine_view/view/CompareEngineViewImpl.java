package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.parts.ArtifactArea;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.parts.CompareArea;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.parts.MatcherArea;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class CompareEngineViewImpl  {
	public static final String COMPARE_ENGINE_CSS_LOCATION ="css/compare_engine.css";
	private StackPane root;
	private Scene scene;
	private ArtifactArea artifactArea;
	
	public CompareEngineViewImpl(FXCanvas canvas, ServiceContainer services) {
		canvas.setScene(createScene());
	}
	
	private Scene createScene() {
        root = new StackPane();
        root.setStyle("-fx-background-color: white;"); 
        scene = new Scene(root);
        scene.getStylesheets().setAll(COMPARE_ENGINE_CSS_LOCATION);
        // creation all parts of the view by calling the specific methods 
    
        root.getChildren().addAll(createArtifactArea(), createMatchingArea(), createComparisonArea());
		return scene;
	}

	private StackPane createArtifactArea() {
		artifactArea = new ArtifactArea();
		return artifactArea;
	}
	
	private StackPane createMatchingArea() {
		return new MatcherArea();
	}
	
	private HBox createComparisonArea() {
		return new CompareArea();
	}
	
	public void showArtifacts(List<Tree> artifacts) {
		artifactArea.showArtifacts(artifacts);
	}
}