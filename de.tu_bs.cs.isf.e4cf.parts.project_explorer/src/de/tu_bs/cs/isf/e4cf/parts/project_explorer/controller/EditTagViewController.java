package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class EditTagViewController {

	@FXML
	public TextField tagTextField;

	@FXML
	public ColorPicker colorPicker;

	@FXML
	public ListView<Tag> listView;

	@FXML
	public Button addBtn;

	@FXML
	public Button deleteBtn;

	@FXML
	public Button updateBtn;
}
