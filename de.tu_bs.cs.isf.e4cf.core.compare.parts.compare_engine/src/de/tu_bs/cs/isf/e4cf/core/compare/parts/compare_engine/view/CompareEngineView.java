package de.tu_bs.cs.isf.e4cf.core.compare.parts.compare_engine.view;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class CompareEngineView {
	
	
	public CompareEngineView(FXCanvas parent, ServiceContainer services) {
		parent.setScene(createScene());
	}
	
	
	public Scene createScene() {
		StackPane pane = new StackPane();
		pane.setStyle("-fx-background-color: white;"); 
		
		Group modelGroup = new Group();
		
		Text text = new Text("Models");
		ComboBox comboBox = new ComboBox();
		modelGroup.getChildren().addAll(text,comboBox);
		pane.getChildren().addAll(modelGroup);
        Scene scene = new Scene(pane);
		
		
		return scene;	
	}
	
	
}
