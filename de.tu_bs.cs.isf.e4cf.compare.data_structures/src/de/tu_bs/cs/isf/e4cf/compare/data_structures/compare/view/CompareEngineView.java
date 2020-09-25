package de.tu_bs.cs.isf.e4cf.compare.data_structures.compare.view;



import de.tu_bs.cs.isf.e4cf.compare.data_structures.compare.view.parts.TreeTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class CompareEngineView {
	public static final String COMPARE_ENGINE_CSS_LOCATION ="css/compare_engine.css";
	private Pane root;
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
        table = new TreeTable();
        root.getChildren().add(table);
		return scene;
	}
	



}
