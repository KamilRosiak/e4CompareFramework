package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.widgets;

import java.awt.MouseInfo;
import java.io.IOException;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.ClusterViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FeatureNameDialog extends BorderPane {
	private Stage stage;

	@FXML
	TableView<WordCounter> wordTable;
	@FXML
	TableColumn<WordCounter, String> labelCol, countCol;
	private ClusterViewModel selectedCluster;

	public FeatureNameDialog(List<WordCounter> wordList, ClusterViewModel model) {
		this.selectedCluster = model;
		try {
			wordList.sort((w1, w2) -> -Double.compare(w1.count, w2.count));
			initializeView(wordList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	/**
	 * load fxml for feature name dialog
	 * 
	 * @throws IOException
	 */
	private void initializeView(List<WordCounter> wordList) throws IOException {		
		FXMLLoader fxmlLoader = new FXMLLoader(FeatureNameDialog.class.getResource("FeatureNameDialog.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		fxmlLoader.load();
		
		initializeTable();
		wordTable.getItems().addAll(wordList);
		wordTable.refresh();
		
		stage = new Stage();
		stage.setTitle("WordCounter");
		// stage.getIcons().add(new Image(""));
		stage.setX(MouseInfo.getPointerInfo().getLocation().getX());
		stage.setY(MouseInfo.getPointerInfo().getLocation().getY());
		Scene scene = new Scene(this);
		stage.setScene(scene);
		this.setOnKeyPressed(null);
		stage.showAndWait();
	}

	private void initializeTable() {
		labelCol.setCellFactory(TextFieldTableCell.forTableColumn());
		labelCol.setCellValueFactory(wc -> new SimpleStringProperty(wc.getValue().word));
		countCol.setCellFactory(TextFieldTableCell.forTableColumn());
		countCol.setCellValueFactory(wc -> new SimpleStringProperty(String.valueOf(wc.getValue().count)));
		wordTable.setOnMouseClicked(mouseEvent -> {
			//double click select name from table
			if (mouseEvent.getClickCount() == 2 && !wordTable.getSelectionModel().isEmpty()) {
				selectedCluster.setName(wordTable.getSelectionModel().getSelectedItem().word);
				stage.close();
			}
		});
	}

}
