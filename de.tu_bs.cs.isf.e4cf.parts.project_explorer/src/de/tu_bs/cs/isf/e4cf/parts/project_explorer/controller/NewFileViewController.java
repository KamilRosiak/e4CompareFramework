package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * ViewController for the NewFileView FXML Layout.
 *
 */
public class NewFileViewController {

	@FXML
	public TextField fileNameTextField;

	@FXML
	public Text notValidText;

	@FXML
	public Text alreadyExitsText;
	
	@FXML
	public Text fileTypeMissingText;

}
