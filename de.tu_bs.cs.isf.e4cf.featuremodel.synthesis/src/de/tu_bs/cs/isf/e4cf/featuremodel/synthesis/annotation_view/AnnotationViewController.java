package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.ColoredFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.EventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.SyntaxGroup;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class AnnotationViewController implements Initializable {
	@Inject
	private ServiceContainer services;
	
	@FXML
	private TableView<ClusterViewModel> annotationTable;
	@FXML
	private TableColumn<ClusterViewModel, String> nameColumn;
	@FXML
	private TableColumn<ClusterViewModel, Boolean> mandatoryColumn;
	@FXML
	private TableColumn<ClusterViewModel, ChildSelectionModel> childSelectionColumn;
	@FXML
	private TableColumn<ClusterViewModel, String> childColumn;

	private ObservableList<ClusterViewModel> clusters = new SimpleListProperty<>();

	/**
	 * Displays a list of SyntaxGroups in the annotation view
	 * 
	 * @param groups List of {@link SyntaxGroup}
	 */
	@Optional
	@Inject
	public void displayClusters(@UIEventTopic(EventTable.PUBLISH_SYNTAX_GROUPS) List<SyntaxGroup> groups) {
		// convert syntaxGroups to viewModels
		List<ClusterViewModel> viewModels = groups.stream().map(Cluster::new).map(ClusterViewModel::new)
				.collect(Collectors.toList());
		this.clusters = FXCollections.observableList(viewModels);

		this.annotationTable.setItems(this.clusters);
		this.annotationTable.refresh();
	}

	/**
	 * ContextMenuItem handler to set the currently selected cluster as root
	 * 
	 * @param e ActionEvent from the MenuItem
	 */
	@FXML
	public void fxSetRoot(ActionEvent e) {
		e.consume();
		this.clusters.forEach(c -> c.setRoot(false));
		ClusterViewModel selectedModel = this.annotationTable.getSelectionModel().getSelectedItem();
		selectedModel.setRoot(true);
		selectedModel.setMandatory(true);
		annotationTable.refresh();
	}

	/**
	 * ContextMenuItem handler to add an abstract cluster to the annotation view
	 * 
	 * @param e ActionEvent from the MenuItem
	 */
	@FXML
	public void fxAddAbstractCluster(ActionEvent e) {
		e.consume();
		final String namePrefix = "Abstract";
		
		// find highest existing ordinal of an abstract cluster
		int maxOrdinal = this.clusters.stream().filter(model -> model.getCluster().isAbstract())
				.map(ClusterViewModel::getName).filter(name -> name.matches(String.format("%s\\d+", namePrefix)))
				.map(name -> name.substring(namePrefix.length())).mapToInt(Integer::valueOf).max().orElse(0);
		int ordinal = maxOrdinal + 1;

		Set<Configuration> configs = new HashSet<>();
		configs.add(new ConfigurationImpl(namePrefix + ordinal));
		Cluster abstractCluster = new Cluster(new SyntaxGroup(configs));
		ClusterViewModel model = new ClusterViewModel(abstractCluster);

		this.clusters.add(model);
		this.annotationTable.refresh();
	}
	
	
	/**
	 * Parses the annotations and builds the corresponding feature model
	 * @param e ActionEvent from the menu item
	 */
	@FXML
	public void synthesizeFeatureModel(ActionEvent e) throws InvalidAnnotationException {
		e.consume();
		// find root
		ClusterViewModel rootModel = this.clusters.stream().filter(ClusterViewModel::isRoot).findFirst().orElse(null);
		if (rootModel != null) {
			Feature rootFeature = toFeature(rootModel.getCluster());
			FeatureDiagram diagram = new FeatureDiagram("Synthesized Feature Model", rootFeature);
			// display the finished model in the editor
			services.eventBroker.post(FDEventTable.LOAD_FEATURE_DIAGRAM, diagram);
		} else {
			throw new InvalidAnnotationException("No cluster annotated as root");
		}
	}
	
	private Feature toFeature(Cluster cluster) {
		Feature feature = new ColoredFeature(cluster.getName(), cluster.getSyntaxGroup().getColor());
		if (cluster.isMandatory()) { 
			feature.setVariability(Variability.MANDATORY);
		} // feature is optional by default
		if (cluster.isAbstract()) {
			feature.setAbstract(true);
		}
		switch (cluster.getChildSelection()) {
		case ALTERNATIVE:
			feature.setGroupVariability(GroupVariability.ALTERNATIVE);
			break;
		case OR:
			feature.setGroupVariability(GroupVariability.OR);
			break;
		default:
			// feature groupVariability is DEFAULT by default
			break;
		}
		for (Cluster child : cluster.getChildren()) {
			Feature childFeature = toFeature(child);
			feature.addChild(childFeature);
		}
		return feature;
	}
	
	public class InvalidAnnotationException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;

		public InvalidAnnotationException(String string) {
			super(string);
		}
	};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.annotationTable.setItems(clusters);
		// initialize view
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		mandatoryColumn.setCellFactory(CheckBoxTableCell.forTableColumn(mandatoryColumn));
		mandatoryColumn.setCellValueFactory(new PropertyValueFactory<>("mandatory"));

		childSelectionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(ChildSelectionModel.DEFAULT,
				ChildSelectionModel.ALTERNATIVE, ChildSelectionModel.OR));
		childSelectionColumn.setCellValueFactory(new PropertyValueFactory<>("childSelectionModel"));

		childColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		childColumn.setCellValueFactory(new PropertyValueFactory<>("childrenDisplay"));

		// add special styling for certain clusters
		annotationTable.setRowFactory(table -> {
			return new TableRow<ClusterViewModel>() {
				@Override
				public void updateItem(ClusterViewModel model, boolean empty) {
					super.updateItem(model, empty);
					if (empty || model == null) {
						setText(null);
						setGraphic(null);
					} else {
						model.style(this);
					}
				}
			};
		});

		// update childrenDisplayProperties of parent clusters on name change
		nameColumn.setOnEditCommit(e -> {
			e.consume();
			Cluster updatedCluster = e.getRowValue().getCluster();
			for (ClusterViewModel model : this.clusters) {
				if (model.getCluster().isParentOf(updatedCluster)) {
					String wholeWordOldName = String.format("\\b%s\\b", e.getOldValue());
					String oldDisplay = model.childrenDisplayProperty().getValue();
					String newDisplay = oldDisplay.replaceAll(wholeWordOldName, e.getNewValue());
					model.childrenDisplayProperty().set(newDisplay);
				}
			}
			e.getRowValue().nameProperty().set(e.getNewValue());
			this.annotationTable.refresh();
			//printDebug();
		});

		// add and remove children to cluster on children list edit
		childColumn.setOnEditCommit(e -> {
			e.consume();
			String[] tokens = e.getNewValue().split("\\s+");
			List<Cluster> newChildren = new ArrayList<>();

			for (String t : tokens) {
				for (ClusterViewModel model : this.clusters) {
					if (model.getCluster().getName().equals(t)) {
						newChildren.add(model.getCluster());
						break;
					}
				}
			}

			e.getRowValue().setChildren(newChildren);
			this.annotationTable.refresh();
			//printDebug();
		});
	}

	/**
	 * Prints a list of all clusters and their properties in the annotation view
	 */
	public void printDebug() {
		System.out.println("");
		for (ClusterViewModel c : this.clusters) {
			System.out.println(c.toString());
		}
	}

}
