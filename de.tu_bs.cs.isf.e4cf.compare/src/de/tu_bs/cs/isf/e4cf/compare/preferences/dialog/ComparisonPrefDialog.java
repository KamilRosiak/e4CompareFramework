/**
 * Copyright(c) 2022 Digital Mind
 * Copyright  : www.ditial-mind-solutions.de
 * Author     : Kamil Rosiak
 */

package de.tu_bs.cs.isf.e4cf.compare.preferences.dialog;

import java.awt.MouseInfo;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.preferences.ComparisonPrefs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Dialog for the selection of items
 * 
 * @author Kamil Rosiak
 *
 */
public class ComparisonPrefDialog extends BorderPane {
	private Stage stage;
	@FXML
	private TextField cloneSizeText, thresholdText, fileNameText;
	@FXML
	private ComboBox<String> typeCombo;

	public ComparisonPrefDialog(ComparisonPrefs prefs, Node root) {
		FXMLLoader fxmlLoader = new FXMLLoader(ComparisonPrefDialog.class.getResource("ComparisonPrefDialog.fxml"));
		try {
			fxmlLoader.setController(this);
			fxmlLoader.setRoot(this);
			fxmlLoader.load();
			stage = new Stage();
			stage.setTitle("Comparison Preferences");
			stage.getIcons().add(new Image("/icons/compare_engine_24.png"));
			stage.setX(MouseInfo.getPointerInfo().getLocation().getX());
			stage.setY(MouseInfo.getPointerInfo().getLocation().getY());
			typeCombo.getItems().addAll(root.getAllNodeTypes());
			typeCombo.getItems().sort((a, b) -> {
				return a.compareTo(b);
			});

			stage.setScene(new Scene(this));
			this.setOnKeyPressed(null);
			showPrefs(prefs);
			stage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void closeDialog() {
		stage.close();
	}

	private void showPrefs(ComparisonPrefs prefs) {
		thresholdText.setText(String.valueOf(prefs.getOptionalThreshold()));
		thresholdText.textProperty().addListener(e -> {
			try {
				prefs.setOptionalThreshold(Float.valueOf(thresholdText.getText().replace(",", ".")));
			} catch (Exception e2) {

			}
		});
		cloneSizeText.setText(String.valueOf(prefs.getCloneSize()));
		cloneSizeText.textProperty().addListener(e -> {
			try {
				prefs.setCloneSize(Integer.valueOf(cloneSizeText.getText().replace(",", ".")));
			} catch (Exception e2) {

			}
		});

		fileNameText.setText(prefs.getFileName());
		fileNameText.textProperty().addListener(e -> {
			try {
				prefs.setFileName(fileNameText.getText().replace(",", "."));
			} catch (Exception e2) {

			}
		});

		typeCombo.getSelectionModel().select(prefs.getGranularityLevel());
		typeCombo.getSelectionModel().selectedItemProperty().addListener(e -> {
			prefs.setGranularityLevel(typeCombo.getSelectionModel().getSelectedItem());
		});
	}

}
