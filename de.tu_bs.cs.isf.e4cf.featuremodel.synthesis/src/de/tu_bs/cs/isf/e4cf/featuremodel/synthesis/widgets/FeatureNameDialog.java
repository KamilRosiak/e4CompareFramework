package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.widgets;

import java.awt.MouseInfo;
import java.io.IOException;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.ClusterViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
		try {
			initializeView();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * load fxml for feature name dialog
	 * 
	 * @throws IOException
	 */
	private void initializeView() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(FeatureNameDialog.class.getResource("FeatureNameDialog.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		fxmlLoader.load();
		stage = new Stage();
		stage.setTitle("WordCounter");
		// stage.getIcons().add(new Image(""));
		stage.setX(MouseInfo.getPointerInfo().getLocation().getX());
		stage.setY(MouseInfo.getPointerInfo().getLocation().getY());
		stage.setScene(new Scene(this));
		this.setOnKeyPressed(null);
		initialiazeTable();
		stage.showAndWait();
	}

	private void initialiazeTable() {
		labelCol.setCellValueFactory(new PropertyValueFactory<>("word"));
		countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
		wordTable.setOnMouseClicked(mouseEvent -> {
			//double click select name from table
			if (mouseEvent.getClickCount() == 2 && !wordTable.getSelectionModel().isEmpty()) {
				selectedCluster.setName(wordTable.getSelectionModel().getSelectedItem().word);
				stage.close();
			}
		});
	}

}
