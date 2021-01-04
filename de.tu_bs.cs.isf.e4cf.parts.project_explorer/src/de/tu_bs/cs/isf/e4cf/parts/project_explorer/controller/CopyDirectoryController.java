package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * A controller for the JavaFX CopyDirectoryView
 */
public class CopyDirectoryController {

	@FXML
	public RadioButton copyContentRB;

	@FXML
	public RadioButton copyEmptyRB;

	@FXML
	public RadioButton copyShallowRB;

	public ToggleGroup toggleGroup = new ToggleGroup();

	public void toggleGroup() {

		copyContentRB.setToggleGroup(toggleGroup);
		copyEmptyRB.setToggleGroup(toggleGroup);
		copyShallowRB.setToggleGroup(toggleGroup);
		copyEmptyRB.setSelected(true);

	}

}
