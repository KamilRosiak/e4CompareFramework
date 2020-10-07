package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.parts;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class CompareArea extends HBox {
	
	public CompareArea() {
		createScene();
	}
	
	public void createScene() {
		setStyle("-fx-background-color: blue;"); 
		Button compareButton = JavaFXBuilder.createButton("Compare", e -> {
			
		});
		getChildren().add(compareButton);
	}
	
}
