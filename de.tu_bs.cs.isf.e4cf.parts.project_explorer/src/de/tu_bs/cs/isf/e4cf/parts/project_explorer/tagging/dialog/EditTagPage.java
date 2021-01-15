package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.TagListCell;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.EditTagViewController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.TagService;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.paint.Color;

/**
 * A page that lets the user edit all available tags. Includes CRUD operations.
 */
public class EditTagPage {

	private IEclipseContext context;
	private FXMLLoader<EditTagViewController> loader;
	private List<Tag> tags;
	private Color selectedColor = Color.WHITE;
	private TagService tagService;

	public EditTagPage(IEclipseContext context, TagService tagService) {
		this.context = context;
		this.tagService = tagService;
		this.tags = tagService.getAvailableTags();
	}

	public Parent createControl() {
		loader = new FXMLLoader<EditTagViewController>(context, StringTable.BUNDLE_NAME, FileTable.EDIT_TAG_FXML);

		// initial setup for buttons ( & colorpicker)
		loader.getController().addBtn.setDisable(true);
		loader.getController().deleteBtn.setDisable(true);
		loader.getController().updateBtn.setDisable(true);
		loader.getController().colorPicker.setValue(Color.WHITE);

		// setup support for selection in listview
		loader.getController().listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		loader.getController().listView.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			int amountSelected = loader.getController().listView.getSelectionModel().getSelectedItems().size();
			if (amountSelected > 0) {
				loader.getController().deleteBtn.setDisable(false);
				if (amountSelected == 1) {
					loader.getController().updateBtn.setDisable(false);
				} else {
					loader.getController().updateBtn.setDisable(true);
				}
			} else {
				loader.getController().deleteBtn.setDisable(true);
				loader.getController().updateBtn.setDisable(true);
			}
		});

		loader.getController().tagTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			loader.getController().addBtn.setDisable(!isTagNameValid(newValue));
		});

		loader.getController().colorPicker.setOnAction(event -> {
			selectedColor = loader.getController().colorPicker.getValue();
		});

		loader.getController().addBtn.setOnAction(event -> {
			// add new tag to TagService
			Tag newTag = new Tag(loader.getController().tagTextField.getText(), selectedColor);

			if (loader.getController().addBtn.getText().equals("Add")) {
				// add new tag
				tagService.addAvailableTag(newTag);
			} else {
				// update the tag
				loader.getController().addBtn.setText("Add");
			}

			loader.getController().tagTextField.setText("");
			loader.getController().colorPicker.setValue(Color.WHITE);
			updateList();
		});

		loader.getController().deleteBtn.setOnAction(event -> {
			// delete selected tag from taglist
			for (Tag tag : loader.getController().listView.getSelectionModel().getSelectedItems()) {
				// delete every item
				tagService.delteAvailableTag(tag);
			}
			updateList();
		});

		// TODO: can we ensure that tag names are unique
		// / not let the user create a tag with the same name
		loader.getController().updateBtn.setOnAction(event -> {
			Tag tag = loader.getController().listView.getSelectionModel().getSelectedItem();
			loader.getController().tagTextField.setText(tag.getName());
			loader.getController().colorPicker.setValue(tag.getColor());

			loader.getController().addBtn.setText("Update");
		});

		loader.getController().listView.setCellFactory(listView -> new TagListCell(context, tags));
		updateList();

		return loader.getNode();
	}

	// update the list with the new tags in it.
	private void updateList() {
		loader.getController().listView.getItems().clear();
		loader.getController().listView.getItems().addAll(tags);
	}

	private boolean isTagNameValid(String tagName) {
		return !(tagName.trim().equals(""));
	}
}
