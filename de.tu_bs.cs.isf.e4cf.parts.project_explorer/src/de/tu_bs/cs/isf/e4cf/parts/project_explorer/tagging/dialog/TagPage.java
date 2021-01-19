package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.TagService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.TagListCellController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.TagViewController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * A layout page that lets the user add tags to selected FileTreeElements.
 */
public class TagPage {

	private IEclipseContext context;

	private TagViewController controller;

	private List<Tag> currentlySelectedTags = new ArrayList<Tag>();

	private Tag tagToUpdate;

	private TagService tagService;

	/**
	 * Lets the user add new tags, delete tags, update color of tags
	 * 
	 * @param context             the app context
	 * @param tagService          the tag service to provide all tags and functions
	 *                            to delete and update
	 * @param initialSelectedTags all initially selected tags.
	 */
	public TagPage(IEclipseContext context, TagService tagService, List<Tag> initialSelectedTags) {
		this.context = context;
		this.tagService = tagService;
		currentlySelectedTags.addAll(initialSelectedTags);
	}

	public Parent createControl() {
		FXMLLoader<TagViewController> loader = new FXMLLoader<TagViewController>(context, StringTable.BUNDLE_NAME,
				FileTable.ADD_TAG_FXML);
		controller = loader.getController();

		// setup addBtn functionality
		controller.addBtn.setOnAction(event -> {

			// check if user wants to update or add a tag
			if (controller.addBtn.getText().equals("Add")) {
				Tag newTag = new Tag(controller.tagTextField.getText(), controller.colorPicker.getValue());
				tagService.addAvailableTag(newTag);
			} else {
				tagService.updateAvailableTag(tagToUpdate, controller.colorPicker.getValue());
			}

			showTagUpdateUI(false);
			controller.tagTextField.setText("");
			controller.colorPicker.setValue(Color.WHITE);
			updateList();
		});

		controller.cancelBtn.setOnAction(event -> {
			controller.tagTextField.setText("");
			controller.colorPicker.setValue(Color.WHITE);
			showTagUpdateUI(false);
		});

		// setup deleteBtn functionality
		controller.deleteBtn.setOnAction(event -> {
			for (Tag tag : controller.listView.getSelectionModel().getSelectedItems()) {
				tagService.delteAvailableTag(tag);
				currentlySelectedTags.remove(tag);
			}
			updateList();
		});
		
		controller.selectAllBtn.setOnAction(event -> {
			currentlySelectedTags.clear();
			currentlySelectedTags.addAll(controller.listView.getItems());
			updateList();
		});
		
		controller.deselectAllBtn.setOnAction(event -> {
			currentlySelectedTags.clear();
			updateList();
		});

		controller.listView.getItems().addAll(tagService.getAvailableTags());

		controller.listView.setCellFactory(listView -> new ListCell<Tag>() {

			@Override
			protected void updateItem(Tag item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					FXMLLoader<TagListCellController> loader = new FXMLLoader<TagListCellController>(context,
							StringTable.BUNDLE_NAME, FileTable.TAG_LIST_CELL);

					TagListCellController controller = loader.getController();

					controller.tagName.setText(item.getName());
					controller.root.getChildren().add(item.getTagIcon());

					controller.selectChechbox.setSelected(currentlySelectedTags.contains(item));

					controller.selectChechbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
						if (newValue) {
							currentlySelectedTags.add(item);
						} else {
							currentlySelectedTags.remove(item);
						}
					});
					setGraphic(loader.getNode());
				}

				// update when user double clicks the specific tag.
				setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent click) {
						if (!empty && click.getClickCount() == 2) {
							TagPage.this.controller.tagTextField.setText(item.getName());
							TagPage.this.controller.colorPicker.setValue(item.getColor());
							tagToUpdate = item;
							showTagUpdateUI(true);
						}
					}
				});
			}
		});

		return loader.getNode();
	}

	/**
	 * Toggle the UI elements to represent either the add or update tag
	 * functionality.
	 * 
	 * @param update true if the UI should represent update, else false.
	 */
	private void showTagUpdateUI(boolean update) {
		controller.addBtn.setText(update ? "Update" : "Add");
		// user is not supposed to update the name of the tag.
		controller.tagTextField.setDisable(update);
		controller.cancelBtn.setVisible(update);
	}

	// update the list with the new tags in it.
	private void updateList() {
		controller.listView.getItems().clear();

		controller.listView.getItems().addAll(tagService.getAvailableTags());
	}

	/**
	 * Get the currently selected tags
	 * 
	 * @return the currently selected tags
	 */
	public List<Tag> getSelectedTags() {
		return currentlySelectedTags;
	}
}
