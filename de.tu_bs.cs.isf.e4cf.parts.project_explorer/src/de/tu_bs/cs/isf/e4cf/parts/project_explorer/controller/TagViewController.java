package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class TagViewController {
	@FXML
	public Button editTagsBtn;

	@FXML
	public ListView<Tag> listView;

}
