package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.TagListCell;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.TagViewController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.TagService;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;

/**
 * A layout page that lets the user add tags to selected FileTreeElements.
 */
public class AddTagPage {

	private IEclipseContext context;

	private FXMLLoader<TagViewController> loader;

	private List<Tag> selectedTags = new ArrayList<Tag>();

	private List<Tag> availableTags;

	private TagService tagService;

	private List<FileTreeElement> selectedElements;

	private AddTagDialog dialog;

	/**
	 * Creates a new page for the dialog that displays directory import options.
	 * 
	 * @param path
	 * @param context
	 */
	public AddTagPage(IEclipseContext context, AddTagDialog dialog, TagService tagService,
			List<FileTreeElement> selectedElements) {
		this.context = context;
		this.tagService = tagService;
		this.availableTags = tagService.getAvailableTags();
		this.selectedElements = selectedElements;
		this.dialog = dialog;
	}

	public Parent createControl() {
		loader = new FXMLLoader<TagViewController>(context, StringTable.BUNDLE_NAME, FileTable.ADD_TAG_FXML);

		loader.getController().listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		loader.getController().editTagsBtn.setOnAction(event -> {
			// open window in which user can 'CRUD' tags.
			dialog.getAlert().close();
			EditTagDialog dialog = new EditTagDialog(context, tagService, selectedElements);
			dialog.open();
		});

		loader.getController().listView.setCellFactory(listView -> new TagListCell(context, selectedTags));
		for (Tag tag : availableTags) {
			loader.getController().listView.getItems().add(tag);
		}

		selectedTags = findInitialSelection();
		for (Tag tag : findInitialSelection()) {
			loader.getController().listView.getSelectionModel().select(tag);
		}
		//

		return loader.getNode();
	}

	// get all tags that are active on these elements.
	public List<Tag> findInitialSelection() {
		List<Tag> tags = new ArrayList<Tag>();
		for (FileTreeElement element : selectedElements) {
			tags.addAll(tagService.getTags(element));
		}
		return tags;
	}

	/**
	 * Get the currently selected tags
	 * 
	 * @return the currently selected tags
	 */
	public List<Tag> getSelectedTags() {
		return loader.getController().listView.getSelectionModel().getSelectedItems();
	}
}
