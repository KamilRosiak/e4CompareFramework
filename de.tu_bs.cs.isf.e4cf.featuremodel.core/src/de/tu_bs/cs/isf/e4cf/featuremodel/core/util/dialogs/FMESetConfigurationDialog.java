package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.FeatureDiagram;
import featureConfiguration.FeatureConfiguration;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;

public class FMESetConfigurationDialog {
    private Dialog<FeatureConfiguration> dialog;
	
	public FMESetConfigurationDialog(String dialogTitle, FeatureDiagram fd) {
		createDialog(dialogTitle, fd);
	}

	private void createDialog(String dialogTitle, FeatureDiagram fd) {
		dialog = new Dialog<>();
		dialog.setTitle(dialogTitle);
		dialog.setResizable(true);
		
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
	}

    public FeatureConfiguration show(double x, double y) {
		Window dialogWindow = dialog.getDialogPane().getScene().getWindow();
		Scene scene = dialog.getDialogPane().getScene();
		dialogWindow.setX(x);
		dialogWindow.setY(y);
		dialog.showAndWait();
		return dialog.getResult();
	}
    
}