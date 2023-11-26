package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.controller.EditorController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;

/**
 * Tab in the feature model editor view
 * 
 * @author Kamil Rosiak
 *
 */
public class FMEditorTab extends Tab {
	private final EditorController fmEditor;

	public FMEditorTab(String title, ServiceContainer services, EventHandler<Event> onClose) {
		super(title);
		this.setOnCloseRequest(onClose);

		this.fmEditor = new EditorController(services, this::setContent);
		this.setUserData(this.fmEditor);
	}

	public EditorController editor() {
		return this.fmEditor;
	}

}
