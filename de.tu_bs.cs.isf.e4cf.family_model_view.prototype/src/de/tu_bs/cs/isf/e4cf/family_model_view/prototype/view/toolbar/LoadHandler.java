package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.toolbar;

import java.nio.file.Path;
import java.nio.file.Paths;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.FamilyModelViewController;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.elements.FXLoadResourceDialog;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelPackage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class LoadHandler implements EventHandler<MouseEvent> {

	private FamilyModelViewController controller;

	public LoadHandler(FamilyModelViewController controller) {
		this.controller = controller;
	}
	
	@Override
	public void handle(MouseEvent event) {
		// Query the user for the storage path for each object
		final int dialogWidth = 1024;
		final int rowHeight = 30;
		FXLoadResourceDialog dialog = new FXLoadResourceDialog("Family Model Resources", dialogWidth, rowHeight);
		dialog.open();
		
		// Retrieve the user selected resource
		String resPathString = dialog.getResourcePath();
		Path resPath = Paths.get(resPathString);
		String fmExtension = controller.getExtension(FamilyModelPackage.eINSTANCE.getFamilyModel());
		if (!resPathString.endsWith(fmExtension)) {
			RCPMessageProvider.errorMessage("Load Family Model", "The selected resource does not have a valid file extension.");
			return;
		}
		
		// Issue a load request
		controller.loadFamilyModel(resPathString);
	}

	
}
