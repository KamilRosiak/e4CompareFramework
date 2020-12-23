package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * A controller for the JavaFX RecursiveCopyOptionsView
 */
public class RecursiveCopyOptionsController {
	@FXML
	public RadioButton recursiveRB;

	@FXML
	public RadioButton shallowRB;

	public ToggleGroup toggleGroup = new ToggleGroup();

	public void toggleGroup() {

		recursiveRB.setToggleGroup(toggleGroup);
		shallowRB.setToggleGroup(toggleGroup);

		recursiveRB.setSelected(true);

	}
}
