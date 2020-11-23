package de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ProjectExplorerDagOverListener implements EventHandler<DragEvent> {

	@Override
	public void handle(DragEvent event) {
		final Dragboard db = event.getDragboard();
		if (db.hasFiles()) {
			event.acceptTransferModes(TransferMode.COPY);
		} else {
			event.consume();
		}
	}
}
