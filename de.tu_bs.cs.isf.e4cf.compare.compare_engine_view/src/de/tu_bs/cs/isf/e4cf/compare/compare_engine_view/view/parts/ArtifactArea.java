package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.parts;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import javafx.collections.FXCollections;
import javafx.scene.layout.StackPane;

public class ArtifactArea extends StackPane {
	private TreeTable table;
	
	public ArtifactArea() {
		createScene();
	}
	
	public void createScene() {
		table = new TreeTable();
		this.getChildren().add(table);
	}
	
	public List<Tree>  getSelectedArtifacts() {

		return null;
	}
	
	/**
	 * Shows the given artifacts in a TreeViewer
	 */
	public void showArtifacts(List<Tree> artifacts) {
		table.getItems().clear();
		table.setItems(FXCollections.observableArrayList(artifacts));
	}
	
	
}
 