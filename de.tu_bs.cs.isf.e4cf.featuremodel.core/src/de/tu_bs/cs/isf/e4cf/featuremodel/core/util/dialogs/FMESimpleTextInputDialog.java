package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs;


import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FMESimpleTextInputDialog {
	private TextInputDialog textDialog;
	
	public FMESimpleTextInputDialog(String dialogTitle, String defaultText) {
		createDialog(dialogTitle, defaultText);
	}
	
	
	public void createDialog(String dialogTitle, String defaultText) {
		textDialog = new TextInputDialog(defaultText);
		textDialog.setContentText(null);
		textDialog.setHeaderText(null);
		textDialog.setTitle(dialogTitle);
		Stage stage = (Stage) textDialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("icon/feature_model_editor_16.png")); // To add an icon
	
	}

	public String show(Double x_pos, Double y_pos) {
		Window dialogWindow = textDialog.getDialogPane().getScene().getWindow();
		dialogWindow.setX(x_pos);
		dialogWindow.setY(y_pos);
		textDialog.showAndWait();
		return textDialog.getResult();
	}
	
}
