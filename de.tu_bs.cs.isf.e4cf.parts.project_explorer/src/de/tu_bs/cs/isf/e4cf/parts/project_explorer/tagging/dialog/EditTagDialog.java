package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.TagService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

/**
 * A dialog that display the editing page for editing tags.
 */
public class EditTagDialog {

	private Alert alert;
	private EditTagPage editTagPage;
	private IEclipseContext context;
	private TagService tagService;
	private List<FileTreeElement> selectedElements;

	public EditTagDialog(IEclipseContext context, TagService tagService, List<FileTreeElement> selectedElements) {

		this.context = context;
		this.tagService = tagService;
		this.selectedElements = selectedElements;
		alert = new Alert(AlertType.NONE);
		alert.setTitle("Edit Tags");

		final DialogPane pane = alert.getDialogPane();
		pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		this.editTagPage = new EditTagPage(context, tagService);
		pane.setContent(editTagPage.createControl());
	}

	/** show the dialog and wait for the users input */
	public void open() {
		// we ALWAYS want to go back to the initial AddTagDialog, because thats where
		// the user started.
		alert.showAndWait().ifPresent(response -> {
			this.performFinish();
		});
	}

	public void performFinish() {
		AddTagDialog dialog = new AddTagDialog(context, tagService, selectedElements);
		dialog.open();
	}

}
