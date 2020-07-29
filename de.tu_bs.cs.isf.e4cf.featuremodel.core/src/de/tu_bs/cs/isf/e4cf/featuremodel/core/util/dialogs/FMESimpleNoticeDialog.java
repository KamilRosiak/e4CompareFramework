package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FMESimpleNoticeDialog {
	private Alert alert;
	
	public FMESimpleNoticeDialog (String dialogTitle, String defaultText) {
		createDialog(dialogTitle,defaultText);
	}
	
	private void createDialog(String dialogTitle, String defaultText) {
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(dialogTitle);
		alert.setContentText(defaultText);
		alert.setHeaderText(null);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("icon/feature_model_editor_16.png")); // To add an icon
		alert.showAndWait();
	}
}
