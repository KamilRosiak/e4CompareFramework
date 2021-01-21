package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * A controller that manages the fxml for the cell in the listview
 */
public class TagListCellController {

	@FXML
	public HBox root;

	@FXML
	public Text tagName;

	@FXML
	public CheckBox selectChechbox;
}
