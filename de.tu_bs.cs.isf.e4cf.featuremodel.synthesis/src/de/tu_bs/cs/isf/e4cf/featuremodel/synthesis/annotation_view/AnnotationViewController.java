package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.EventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.SyntaxGroup;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.CheckBoxTableCellBuilder;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class AnnotationViewController implements Initializable {
	@Inject
	private ServiceContainer services;

	@FXML
	private TableView<Cluster> annotationTable;
	@FXML
	private TableColumn<Cluster, String> nameColumn;
	@FXML
	private TableColumn<Cluster, Boolean> mandatoryColumn;
	@FXML
	private TableColumn<Cluster, ChildSelectionModel> childSelectionColumn;
	@FXML
	private TableColumn<Cluster, String> childColumn;

	private ObservableList<Cluster> clusters = new SimpleListProperty<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.annotationTable.setItems(clusters);
		// initialize view
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		mandatoryColumn.setCellFactory(CheckBoxTableCell.forTableColumn(mandatoryColumn));
		mandatoryColumn.setCellValueFactory(new PropertyValueFactory<>("isMandatory"));

		childSelectionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(ChildSelectionModel.DEFAULT,
				ChildSelectionModel.ALTERNATIVE, ChildSelectionModel.OR));
		childSelectionColumn.setCellValueFactory(new PropertyValueFactory<>("childSelection"));
		
		childColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		childColumn.setCellValueFactory(e -> {
			String children = e.getValue().getChildren().stream()
					.map(Cluster::getName)
					.reduce("", (x, y) -> String.format("%s %s", x, y));
			return new SimpleStringProperty(children);
		});

	}

	@Optional
	@Inject
	public void setClusters(@UIEventTopic(EventTable.PUBLISH_SYNTAX_GROUPS) List<SyntaxGroup> groups) {
		List<Cluster> clusters = groups.stream().map(Cluster::new).collect(Collectors.toList());
		this.annotationTable.getItems().clear();
		this.annotationTable.setItems(FXCollections.observableList(clusters));
		this.annotationTable.refresh();
	}

}
