package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.tu_bs.cs.isf.e4cf.core.util.tagging.Tag;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Controller for the TagView FXML
 */
public class TagViewController implements Initializable {

	@FXML
	public ListView<Tag> listView;

	@FXML
	public Text errorText;

	@FXML
	public HBox tag;

	@FXML
	public Button addBtn;

	@FXML
	public Button deleteBtn;

	@FXML
	public Button selectAllBtn;

	@FXML
	public Button deselectAllBtn;

	@FXML
	public TextField tagTextField;

	@FXML
	public ColorPicker colorPicker;

	@FXML
	public Button cancelBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cancelBtn.setVisible(false);
		deleteBtn.setDisable(true);
		colorPicker.setValue(Color.WHITE);

		errorText.setVisible(false);
		
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		listView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			int amountSelected = listView.getSelectionModel().getSelectedItems().size();
			if (amountSelected > 0) {
				deleteBtn.setDisable(false);
			} else {
				deleteBtn.setDisable(true);
			}
		});
	}
}
