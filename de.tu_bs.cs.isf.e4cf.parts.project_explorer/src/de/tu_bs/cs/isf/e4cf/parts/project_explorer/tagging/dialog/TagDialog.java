package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
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
public class TagDialog {

	private Alert alert;
	private TagPage tagPage;
	// indicates whether a file has been moved.
	private TagService tagService;
	private List<FileTreeElement> selectedElements;
	private List<Tag> avaiableTags = new ArrayList<Tag>();
	private ServiceContainer services;

	List<Tag> previouslySelected = new ArrayList<Tag>();

	public TagDialog(IEclipseContext context, TagService tagService, ServiceContainer services,
			List<FileTreeElement> selectedElements) {
		alert = new Alert(AlertType.NONE);
		alert.setTitle("Manage Tags");

		this.tagService = tagService;
		this.services = services;

		this.selectedElements = selectedElements;

		this.avaiableTags.addAll(tagService.getAvailableTags());

		previouslySelected = getInitialSelectedTags();

		final DialogPane pane = alert.getDialogPane();
		pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		this.tagPage = new TagPage(context, tagService, previouslySelected);
		pane.setContent(tagPage.createControl());

	}

	/** show the dialog and wait for the users input */
	public void open() {
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			// cancel the dialog without applying any changes made in this session
			System.out.println("cancel");
		} else if (result.get() == ButtonType.OK) {
			performFinish();
		}
	}

	public Alert getAlert() {
		return alert;
	}

	/**
	 * Apply the selected tags to all selected FileTreeElements and remove all tags
	 * that are not selected anymore.
	 */
	public void performFinish() {

		List<Tag> availableSessionTags = tagPage.getSessionTags();
		System.out.println("Session tags: " + availableSessionTags);

		// update the tags in the tagservice.

		/*
		 * for (Tag tag : avaiableTags) { if (availableSessionTags.contains(tag)) {
		 * tagService.delteAvailableTag(tag); } }
		 * 
		 * for (Tag tag : availableSessionTags) { if (!avaiableTags.contains(tag)) {
		 * tagService.addAvailableTag(tag); } else { // check if tag has updated Tag t =
		 * avaiableTags.get(avaiableTags.indexOf(tag)); if
		 * (!t.getColor().equals(tag.getColor())) { tagService.updateAvailableTag(tag,
		 * tag.getColor()); } } }
		 */
		tagService.getAvailableTags().clear();
		tagService.getAvailableTags().addAll(availableSessionTags);

		System.out.println("TagService avail tags after including session changes: " + tagService.getAvailableTags());

		List<Tag> selectedTags = tagPage.getSelectedTags();
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

		services.workspaceFileSystem.refresh();
	}

	// get all tags that are active on these elements.
	public List<Tag> getInitialSelectedTags() {
		HashSet<Tag> tags = new HashSet<Tag>();
		for (FileTreeElement element : selectedElements) {
			tags.addAll(tagService.getTags(element));
		}
		return new ArrayList<Tag>(tags);
	}
}
