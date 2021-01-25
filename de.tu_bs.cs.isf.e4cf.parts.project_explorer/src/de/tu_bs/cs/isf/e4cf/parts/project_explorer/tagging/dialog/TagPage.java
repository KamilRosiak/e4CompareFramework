package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.TagService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.TagViewController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.scene.Parent;

/**
 * A layout page that lets the user add tags to selected FileTreeElements.
 */
public class TagPage {

	private IEclipseContext context;

	private List<Tag> currentlySelectedTags = new ArrayList<Tag>();
	private List<Tag> sessionTags = new ArrayList<Tag>();

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
		currentlySelectedTags.addAll(initialSelectedTags);
		sessionTags.addAll(tagService.getAvailableTags());
	}

	public Parent createControl() {
		FXMLLoader<TagViewController> loader = new FXMLLoader<TagViewController>(context, StringTable.BUNDLE_NAME,
				FileTable.ADD_TAG_FXML);
		loader.getController().initViewControlls(sessionTags, currentlySelectedTags, context);

		return loader.getNode();
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
	 * Get a list of all tags that are valid in this specific session.
	 * 
	 * @return a list of tags.
	 */
	public List<Tag> getSessionTags() {
		return sessionTags;
	}
}
