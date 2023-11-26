/**
 * Copyright(c) 2022 Digital Mind
 * Copyright  : www.ditial-mind-solutions.de
 * Author     : Kamil Rosiak
 */

package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.utilities.widgets.color_picker;

import java.awt.MouseInfo;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

/**
 * @author Kamil Rosiak
 *
 */
public class FeatureSelectionDialog extends DialogPane {
	private Stage stage;
	@FXML
	TableView<IFeature> featureTable;
	@FXML
	TableColumn<IFeature, String> nameCol;

	public FeatureSelectionDialog(Set<IFeature> features) {
		FXMLLoader fxmlLoader = new FXMLLoader(FeatureSelectionDialog.class.getResource("FeatureSelectionDialog.fxml"));
		try {
			fxmlLoader.setController(this);
			fxmlLoader.setRoot(this);
			fxmlLoader.load();
			stage = new Stage();
			stage.setTitle("Select Feature");
			stage.getIcons().add(new Image("/icons/color_24.png"));
			stage.setX(MouseInfo.getPointerInfo().getLocation().getX());
			stage.setY(MouseInfo.getPointerInfo().getLocation().getY());
			stage.setScene(new Scene(this));
			featureTable.getItems().addAll(features);
			nameCol.setCellValueFactory(e -> {
				return new SimpleStringProperty(e.getValue().getName());
			});

			featureTable.setOnMouseClicked(e -> {
				if (e.getClickCount() == 2) {
					stage.close();
				}
			});

			featureTable.setRowFactory(table -> {
				return new TableRow<IFeature>() {
					@Override
					public void updateItem(IFeature feature, boolean empty) {
						super.updateItem(feature, empty);
						if (empty || feature == null) {
							setText(null);
							setGraphic(null);
						} else {
							setBackground(new Background(new BackgroundFill(feature.getColor().get(), null, null)));
						}
					}
				};
			});

			this.setOnKeyPressed(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IFeature getSelectedFeature() {
		stage.showAndWait();
		IFeature feature = featureTable.getSelectionModel().getSelectedItem();
		return feature;
	}

}
