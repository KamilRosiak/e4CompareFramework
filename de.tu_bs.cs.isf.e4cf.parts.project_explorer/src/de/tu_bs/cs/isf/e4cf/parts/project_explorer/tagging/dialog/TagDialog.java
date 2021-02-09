package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.TagService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/**
 * A dialog that display the page in which the user can add custom tags to
 * FileTreeElements.
 */
public class TagDialog {

	private Alert alert;
	private TagPage tagPage;
	// indicates whether a file has been moved.
	private TagService tagService;
	private List<FileTreeElement> selectedElements = new ArrayList<FileTreeElement>();
	private List<Tag> avaiableTags = new ArrayList<Tag>();
	private ServiceContainer services;
	private List<Tag> previouslySelected = new ArrayList<Tag>();
	private IEclipseContext context;

	public TagDialog(IEclipseContext context, ServiceContainer services, List<FileTreeElement> selectedElements) {
		this.context = context;
		this.services = services;
		this.tagService = services.tagService;
		this.selectedElements.addAll(selectedElements);
		this.avaiableTags.addAll(tagService.getAvailableTags());

		initAlert();
		initDialog();
	}

	private void initDialog() {
		final DialogPane pane = alert.getDialogPane();
		pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		final Stage stage = (Stage) pane.getScene().getWindow();
		stage.getIcons().add(services.imageService.getFXImage(null, FileTable.TAG_PNG).getImage());

		this.previouslySelected = getInitialSelectedTags();
		this.tagPage = new TagPage(context, tagService, previouslySelected);
		pane.setContent(tagPage.createControl());
	}

	private void initAlert() {
		alert = new Alert(AlertType.NONE);
		alert.setTitle(StringTable.TAG_DIALOG_TITLE);
	}

	/** show the dialog and wait for the users input */
	public void open() {
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			performFinish();
		}
	}

	/**
	 * Apply the selected tags to all selected FileTreeElements and remove all tags
	 * that are not selected anymore.
	 */
	public void performFinish() {
		applySessionChangesToTagService();

		List<Tag> selectedTags = tagPage.getSelectedTags();
		List<Tag> notIntersect = new ArrayList<Tag>();

		if (selectedElements != null) {
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
		services.workspaceFileSystem.refresh();
	}

	/**
	 * Apply changes made in this session to the available tags from the tag
	 * service.
	 */
	private void applySessionChangesToTagService() {
		List<Tag> availableSessionTags = tagPage.getSessionTags();

		for (Tag tag : avaiableTags) {
			if (!availableSessionTags.contains(tag)) {
				tagService.deleteAvailableTag(tag);
			}
		}

		for (Tag tag : availableSessionTags) {
			if (!avaiableTags.contains(tag)) {
				tagService.addAvailableTag(tag);
			} else {
				avaiableTags.get(avaiableTags.indexOf(tag));
				if (!tag.getColor().equals(tag.getColor())) {
					tagService.updateAvailableTag(tag, tag.getColor());
				}
			}
		}
	}

	// get all tags that are active on these elements.
	public List<Tag> getInitialSelectedTags() {
		HashSet<Tag> tags = new HashSet<Tag>();
		if (selectedElements != null) {
			for (FileTreeElement element : selectedElements) {
				tags.addAll(tagService.getTags(element));
			}
		}
		return new ArrayList<Tag>(tags);
	}
}
