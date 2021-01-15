package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.TagService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

/**
 * A dialog that display the page in which the user can add custom tags to
 * FileTreeElements.
 */
public class AddTagDialog {

	private Alert alert;
	private AddTagPage addTagPage;
	// indicates whether a file has been moved.
	private TagService tagService;
	private List<FileTreeElement> selectedElements;

	public AddTagDialog(IEclipseContext context, TagService tagService, List<FileTreeElement> selectedElements) {
		alert = new Alert(AlertType.NONE);
		alert.setTitle("Add Tags");

		this.tagService = tagService;

		this.selectedElements = selectedElements;

		final DialogPane pane = alert.getDialogPane();
		pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		this.addTagPage = new AddTagPage(context, this, tagService, selectedElements);
		pane.setContent(addTagPage.createControl());
	}

	/** show the dialog and wait for the users input */
	public void open() {
		alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
			this.performFinish();
		});
	}

	public Alert getAlert() {
		return alert;
	}

	/**
	 * Apply the selected tags to all selected FileTreeElements and remove all tags
	 * that are not selected anymore.
	 */
	public void performFinish() {

		List<Tag> previouslySelected = addTagPage.findInitialSelection();
		List<Tag> selectedTags = addTagPage.getSelectedTags();
		List<Tag> notIntersect = new ArrayList<Tag>();

		for (Tag tag : previouslySelected) {
			if (!selectedTags.contains(tag)) {
				notIntersect.add(tag);
			}
		}

		for (FileTreeElement element : selectedElements) {
			for (Tag tag : notIntersect) {
				tagService.deleteTag(element, tag);
			}
			for (Tag tag : selectedTags) {
				tagService.addTag(element, tag);
			}
		}
	}

}
