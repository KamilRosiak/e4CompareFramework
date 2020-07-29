package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.toolbar;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.FamilyModelViewController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SaveHandler implements EventHandler<MouseEvent> {
	
	protected FamilyModelViewController fmvController;
	
	public SaveHandler(FamilyModelViewController fmvController) {
		this.fmvController = fmvController;
	}
	
	@Override
	public void handle(MouseEvent event) {
		// check if all of the variants resources are set
		fmvController.saveFamilyModel();
	}

}
