package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.parts;

import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;

public class MatcherArea extends StackPane {
	
	public MatcherArea() {
		createScene();
	}
	
	public void createScene() {
		ComboBox<Matcher> matcherSelection = new ComboBox<Matcher>();
		
		getChildren().add(matcherSelection);
	}

}
