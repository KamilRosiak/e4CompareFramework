package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs;

import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.FeatureDiagram;
import featureConfiguration.FeatureConfiguration;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import javafx.util.Callback;

public class FMESetConfigurationDialog {
    private Dialog<FeatureConfiguration> dialog;
	
	public FMESetConfigurationDialog(String dialogTitle, FeatureDiagram fd) {
		createDialog(dialogTitle, fd);
	}
	
	public Dialog<FeatureConfiguration> getDialog() {
		return dialog;
	}

	private void createDialog(String dialogTitle, FeatureDiagram fd) {
		dialog = new Dialog<>();
		dialog.setTitle(dialogTitle);
		dialog.setResizable(false);
		
		ScrollPane configPane = new ScrollPane();
		
//		TableView<FeatureConfiguration> configTable = new TableView<>();
//		TableColumn<FeatureConfiguration, String> configCol = new TableColumn<FeatureConfiguration, String>("Name");
//		configCol.setCellValueFactory(new PropertyValueFactory<FeatureConfiguration, String>("name"));
//		configTable.getColumns().add(configCol);
//		configTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//		configTable.getItems().setAll(fd.getFeatureConfiguration());
		
		ListView<FeatureConfiguration> configList = new ListView<>();
		configList.setCellFactory(param -> new ListCell<FeatureConfiguration>() {
			@Override
			protected void updateItem(FeatureConfiguration item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName());
				}
			}
		});
		configList.getItems().setAll(fd.getFeatureConfiguration());
		configPane.setContent(configList);
		dialog.getDialogPane().setContent(configPane);
		
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
	}

    public FeatureConfiguration show(double x, double y) {
		Window dialogWindow = dialog.getDialogPane().getScene().getWindow();
		Scene scene = dialog.getDialogPane().getScene();
		dialogWindow.setX(x);
		dialogWindow.setY(y);
		
		dialog.setResultConverter(new Callback<ButtonType, FeatureConfiguration>() {

			@Override
			public FeatureConfiguration call(ButtonType button) {
				if (button == ButtonType.OK) {
					ScrollPane spane = (ScrollPane) dialog.getDialogPane().getContent();
					FeatureConfiguration fc = ((ListView<FeatureConfiguration>) spane.getContent()).getSelectionModel().getSelectedItem();
					return fc;
				}
				return null;
			}
			
		});
		Optional<FeatureConfiguration> result = dialog.showAndWait();
		if (result.isPresent()) {
			return dialog.getResult();
		}
		return null;
	}
    
}