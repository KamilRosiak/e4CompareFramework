package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.toolbar;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.FamilyModelViewController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class LoadHandler implements EventHandler<MouseEvent> {

	private FamilyModelViewController controller;

	public LoadHandler(FamilyModelViewController controller) {
		this.controller = controller;
	}
	
	@Override
	public void handle(MouseEvent event) {
		controller.loadFamilyModel();
	}

	
}
