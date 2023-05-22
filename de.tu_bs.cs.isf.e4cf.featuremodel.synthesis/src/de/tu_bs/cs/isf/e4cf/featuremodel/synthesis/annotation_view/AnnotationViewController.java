package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.lang.model.SourceVersion;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.tree.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.FeatureLocator;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.FeatureOrganizer;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.SyntaxGroup;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.SynthesisConsts;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.Cluster.ChildSelectionModel;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.widgets.FeatureNameDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.widgets.WordCounter;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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
	private TableColumn<ClusterViewModel, Cluster.Variability> variabilityColumn;
	@FXML
	private TableColumn<ClusterViewModel, ChildSelectionModel> childSelectionColumn;
	@FXML
	private TableColumn<ClusterViewModel, String> childColumn;
	@FXML
	private ContextMenu annotationTableContextMenu;

	private ObservableList<ClusterViewModel> clusters = new SimpleListProperty<>();
	private FeatureLocator locator = new FeatureLocator();
	private MPLPlatform currentMpl;

	@Optional
	@Inject
	public void locateFeatures(@UIEventTopic(MPLEEditorConsts.LOCATE_FEATURES) MPLPlatform mpl) {
		List<SyntaxGroup> clusters = this.locator.locateFeatures(services, mpl);
		this.currentMpl = mpl;
		this.displayGroups(clusters);
	}

	@Optional
	@Inject
	public void updateMPL(@UIEventTopic(MPLEEditorConsts.ADD_VARIANT_TO_MPL) MPLPlatform mpl) {
		List<SyntaxGroup> groups = this.locator.updateMPL(mpl);
		List<Cluster> newClusters = groups.stream().map(Cluster::new).collect(Collectors.toList());
		List<Cluster> oldClusters = this.clusters.stream().map(ClusterViewModel::getCluster)
				.collect(Collectors.toList());
		updateClusters(oldClusters, newClusters);
		this.displayClusters(newClusters);
	}

	private void updateClusters(List<Cluster> oldClusters, List<Cluster> newClusters) {
		String[] variants = newClusters.get(0).getName().split(" ");
		String newVariantName = variants[variants.length - 1];
		Map<Cluster, List<Cluster>> clusterMap = new HashMap<>();
		for (Cluster c : oldClusters) {
			clusterMap.put(c, new ArrayList<>());
		}

		for (Cluster newC : newClusters) {
			for (Cluster oldC : oldClusters) {

				if (oldC.getSyntaxGroup().getUuids().containsAll(newC.getSyntaxGroup().getUuids())) { 
					// new cluster is subset of old cluster
					if (oldC.getSyntaxGroup().getConfigurations().size() < newC.getSyntaxGroup().getConfigurations()
							.size()) { // clusters are the same
						newC.setName(oldC.getName());
						newC.setRoot(oldC.isRoot());
						newC.setVariability(oldC.getVariability());
						newC.setChildSelection(oldC.getChildSelection());
						clusterMap.get(oldC).add(newC);
					} else { // remaining uuids not in new variant
						newC.setName(oldC.getName() + "\\" + newVariantName);
					}
					break;
				} else if (!Collections.disjoint(oldC.getSyntaxGroup().getUuids(), newC.getSyntaxGroup().getUuids())) {
					// other uuids in new cluster
					String newName = combineNames(oldC, newC);
					newC.setName(newName);
					System.out.println(newName);
					break;
				}
			}
		}

		// add children to new clusters
		for (Cluster oldC : oldClusters) {
			for (Cluster newC : clusterMap.get(oldC)) {
				for (Cluster oldChild : oldC.getChildren()) {
					clusterMap.get(oldChild).forEach(newC::addChild);
				}
			}
		}
	}

	private String combineNames(Cluster oldC, Cluster newC) {
		String oldName = oldC.getName();
		String clusters = newC.getName();
		for (Configuration config : oldC.getSyntaxGroup().getConfigurations()) {
			clusters = clusters.replaceAll("\\b" + config.getName() + "\\b", "");
		}
		clusters = clusters.trim();
		String combined = oldName;
		if (!clusters.isEmpty()) {
			combined += "\\" + clusters;
		}
		return combined;
	}

	/**
	 * Displays a list of SyntaxGroups in the annotation view
	 * 
	 * @param groups List of {@link SyntaxGroup}
	 */
	public void displayGroups(List<SyntaxGroup> groups) {
		List<Cluster> clusters = groups.stream().map(Cluster::new).collect(Collectors.toList());
		displayClusters(clusters);
	}

	public void displayClusters(List<Cluster> clusters) {
		services.partService.showPart(SynthesisConsts.BUNDLE_NAME);

		// display clusters in annotation view
		List<ClusterViewModel> viewModels = clusters.stream().map(ClusterViewModel::new).collect(Collectors.toList());
		this.clusters = FXCollections.observableList(viewModels);
		this.annotationTable.setItems(this.clusters);
		this.annotationTable.refresh();
		
		// calculate initial feature diagram proposal
		Tree<Cluster> hierarchy = FeatureOrganizer.createHierarchy(currentMpl, clusters);
		Feature root = toFeature(hierarchy.getRoot().value());
		FeatureDiagram diagram = new FeatureDiagram("Generated Feature Model", root);
		this.currentMpl.setFeatureModel(diagram);
		
		
		// display features in mpl editor
		services.eventBroker.send(MPLEEditorConsts.SHOW_MPL, this.currentMpl);
		// display diagram in feature model editor
		services.partService.showPart(FDStringTable.BUNDLE_NAME);
		services.eventBroker.post(FDEventTable.LOAD_FEATURE_DIAGRAM, diagram);
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
		selectedModel.setVariability(Cluster.Variability.MANDATORY);
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
	 * Collects all name label from current atomic sets
	 */
	@FXML
	private void proposeFeatureName(ActionEvent e) {
		e.consume();
		ClusterViewModel selectedModel = annotationTable.getSelectionModel().getSelectedItem();
		Map<String, Integer> words = new HashMap<>();
		Set<UUID> selectedIds = selectedModel.getCluster().getSyntaxGroup().getUuids();
		Set<Node> selectedNodes = currentMpl.getNodesForUUIDs(selectedIds);
		
		for (Node node : selectedNodes) {
			for (Attribute attr : node.getAttributes()) {
				for (Value<?> val : attr.getAttributeValues()) {
					Object value = val.getValue();
					if (value instanceof String) {
						String token = (String) value;
						String[] split = token.split("\\s");
						for (String word : split) {
							word = word.trim();
							word = word.replaceAll("\\p{Punct}", "");
							word = word.toLowerCase();
							words.put(word, words.getOrDefault(word, 0)+1);
						}
					}
				}
			}
		}
		
		
		Pattern nonWord = Pattern.compile("[^\\w]*|\\d"); 
		List<WordCounter> wordList = words.keySet().stream()
				.filter(word -> !SourceVersion.isKeyword(word))
				.filter(word -> {
					Matcher m = nonWord.matcher(word);
					return !m.matches();
				})
				.map(word -> new WordCounter(word, words.get(word)))
				.collect(Collectors.toList());
		annotationTableContextMenu.hide();
		new FeatureNameDialog(wordList, selectedModel);
		annotationTable.refresh();
	}

	/**
	 * Parses the annotations and builds the corresponding feature model
	 * 
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
		Feature feature = new Feature(cluster.getName());
		feature.setColor(cluster.getSyntaxGroup().getColor());
		feature.getConfigurations().addAll(cluster.getSyntaxGroup().getConfigurations());
		feature.getArtifactUUIDs().addAll(cluster.getSyntaxGroup().getUuids());
		switch (cluster.getVariability()) {
		case DEFAULT:
			feature.setVariability(Variability.DEFAULT);
			break;
		case MANDATORY:
			feature.setVariability(Variability.MANDATORY);
			break;
		case OPTIONAL:
			feature.setVariability(Variability.OPTIONAL);
			break;		
		}
		switch (cluster.getChildSelection()) {
		case ALTERNATIVE:
			feature.setGroupVariability(GroupVariability.ALTERNATIVE);
			break;
		case OR:
			feature.setGroupVariability(GroupVariability.OR);
			break;
		default:
			feature.setGroupVariability(GroupVariability.DEFAULT);
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

		variabilityColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Cluster.Variability.DEFAULT, 
				Cluster.Variability.MANDATORY, Cluster.Variability.OPTIONAL));
		variabilityColumn.setCellValueFactory(new PropertyValueFactory<>("variability"));

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
			// printDebug();
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
			// printDebug();
		});
		annotationTableContextMenu.setAutoHide(true);
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
