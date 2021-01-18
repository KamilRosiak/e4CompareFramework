package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.TagListCellController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.TagViewController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.TagService;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A layout page that lets the user add tags to selected FileTreeElements.
 */
public class TagPage {

	private IEclipseContext context;

	private TagViewController controller;

	private List<Tag> currentlySelectedTags = new ArrayList<Tag>();

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

			if (controller.addBtn.getText().equals("Add")) {
				Tag newTag = new Tag(controller.tagTextField.getText(), controller.colorPicker.getValue());
				tagService.addAvailableTag(newTag);
			} else {
				// TODO: update a given tag
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
					controller.root.getChildren().add(createTagIcon(item.getColor()));

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

	/**
	 * Generates a small circle node with a specific color
	 * 
	 * @param color the color of the circle
	 * @return the circle
	 */
	private Circle createTagIcon(Color color) {

		// TODO styling and layout
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(1);
		dropShadow.setOffsetY(1);
		dropShadow.setRadius(2);
		dropShadow.setColor(Color.GRAY);

		Circle circle = new Circle(6, color);
		circle.setEffect(dropShadow);
		return circle;
	}
}
