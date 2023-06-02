package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
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
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.tree.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.FeatureLocator;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.FeatureOrganizer;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.SyntaxGroup;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.SynthesisConsts;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.exceptions.InvalidAnnotationException;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.util.FeatureUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.widgets.FeatureNameDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.widgets.WordCounter;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AnnotationViewController implements Initializable {
	@Inject
	private ServiceContainer services;

	@FXML
	private TableView<ClusterViewModel> annotationTable;
	@FXML
	private TableColumn<ClusterViewModel, String> nameColumn;
	@FXML
	private TableColumn<ClusterViewModel, Variability> variabilityColumn;
	@FXML
	private TableColumn<ClusterViewModel, GroupVariability> childSelectionColumn;
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
		List<SyntaxGroup> syntaxGroups = this.locator.locateFeatures(services, mpl);
		this.currentMpl = mpl;
		List<Cluster> clusters = syntaxGroups.stream().map(Cluster::new).collect(Collectors.toList());

		services.partService.showPart(SynthesisConsts.BUNDLE_NAME);
		// calculate initial feature diagram proposal
		Tree<Cluster> hierarchy = FeatureOrganizer.createHierarchy(currentMpl, clusters);
		IFeature root = FeatureUtil.toFeature(hierarchy.getRoot().value());
		FeatureDiagram diagram = new FeatureDiagram("Generated Feature Model", root);
		this.currentMpl.setFeatureModel(diagram);
		String filename = services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//"
				+ this.currentMpl.fileName + ".mpl";
		MPLEPlatformUtil.storePlatform(filename, this.currentMpl);
		displayFeatures(diagram);
	}

	@Optional
	@Inject
	public void showFeatures(@UIEventTopic(MPLEEditorConsts.SHOW_FEATURES) MPLPlatform platform) {
		if (currentMpl == null || !currentMpl.equals(platform)) {
			this.currentMpl = platform;
			if (platform.getFeatureModel().isPresent()) {
				displayFeatures(platform.getFeatureModel().get());
			}
		}
	}

	@Optional
	@Inject
	public void updateMPL(@UIEventTopic(MPLEEditorConsts.ADD_VARIANT_TO_MPL) MPLPlatform newMpl) {
		FeatureDiagram newDiagram = this.locator.updateMPL(currentMpl, newMpl);
		this.currentMpl = newMpl;
		this.currentMpl.setFeatureModel(newDiagram);
		this.displayFeatures(newDiagram);
	}

	public void displayFeatures(FeatureDiagram featureDiagram) {
		this.currentMpl.setFeatureModel(featureDiagram);

		// display features in mpl editor
		services.eventBroker.send(MPLEEditorConsts.SHOW_MPL, this.currentMpl);
		// display diagram in feature model editor
		services.partService.showPart(FDStringTable.BUNDLE_NAME);
		services.eventBroker.post(FDEventTable.LOAD_FEATURE_DIAGRAM, featureDiagram);

		// display clusters in annotation view
		TreeSet<IFeature> allFeatures = new TreeSet<>((f1, f2) -> {
			int lengthDiff = f2.getName().length() - f1.getName().length();
			if (lengthDiff == 0) {
				lengthDiff = f2.getName().compareTo(f1.getName());
			}
			return lengthDiff;
		});
		allFeatures.addAll(featureDiagram.getAllFeatures());
		List<ClusterViewModel> viewModels = allFeatures.stream().map(ClusterViewModel::new)
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
		selectedModel.setVariability(Variability.MANDATORY);
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
		int maxOrdinal = this.clusters.stream().filter(model -> model.getFeature().isAbstract())
				.map(ClusterViewModel::getName).filter(name -> name.matches(String.format("%s\\d+", namePrefix)))
				.map(name -> name.substring(namePrefix.length())).mapToInt(Integer::valueOf).max().orElse(0);
		int ordinal = maxOrdinal + 1;

		Set<Configuration> configs = new HashSet<>();
		configs.add(new ConfigurationImpl(namePrefix + ordinal));
		Cluster abstractCluster = new Cluster(new SyntaxGroup(configs));
		IFeature abstractFeature = FeatureUtil.toFeature(abstractCluster);
		ClusterViewModel model = new ClusterViewModel(abstractFeature);

		this.clusters.add(model);
		this.annotationTable.refresh();
	}

	/**
	 * Collects all name label from current atomic sets
	 */
	@FXML
	private void proposeFeatureName(ActionEvent e) {
		e.consume();
		annotationTableContextMenu.hide();
		ClusterViewModel selectedModel = annotationTable.getSelectionModel().getSelectedItem();
		List<WordCounter> wordList = getInverseDocumentFrequency(selectedModel.getFeature());
		new FeatureNameDialog(wordList, selectedModel);
		annotationTable.refresh();
	}

	private List<WordCounter> getInverseDocumentFrequency(IFeature feature) {
		double documentCount = annotationTable.getItems().size();
		Map<String, List<IFeature>> wordDocCount = new HashMap<>();
		for (ClusterViewModel model : annotationTable.getItems()) {
			List<WordCounter> wordCounts = getWordFrequency(model.getFeature().getArtifactUUIDs());
			for (WordCounter wordCount : wordCounts) {
				List<IFeature> docs = wordDocCount.get(wordCount.word);
				if (docs != null) {
					docs.add(model.getFeature());
				} else {
					docs = new ArrayList<>();
					docs.add(model.getFeature());
					wordDocCount.put(wordCount.word, docs);
				}
			}
		}
		Map<String, Double> idf = new HashMap<>();
		wordDocCount.forEach((word, docs) -> idf.put(word, Math.log(documentCount / docs.size())));

		List<WordCounter> featureWords = getWordFrequency(feature.getArtifactUUIDs());
		featureWords.forEach(wc -> wc.count = idf.get(wc.word) * wc.count);
		return featureWords;
	}

	private List<WordCounter> getWordFrequency(Set<UUID> selectedIds) {
		Set<Node> selectedNodes = currentMpl.getNodesForUUIDs(selectedIds);
		Map<String, Integer> words = new HashMap<>();
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
							words.put(word, words.getOrDefault(word, 0) + 1);
						}
					}
				}
			}
		}
		if (words.size() > 0) {
			int maxCount = words.values().stream().max(Integer::compare).get();
			Map<String, Float> relativeWordFrequency = new HashMap<>();
			words.forEach((word, count) -> relativeWordFrequency.put(word, ((float) count) / maxCount));
			final Pattern nonWord = Pattern.compile("[^\\w]*|\\d");
			List<WordCounter> wordList = relativeWordFrequency.keySet().stream()
					.filter(word -> !SourceVersion.isKeyword(word)).filter(word -> {
						Matcher m = nonWord.matcher(word);
						return !m.matches();
					}).map(word -> new WordCounter(word, words.get(word))).collect(Collectors.toList());
			return wordList;
		} else {
			return new ArrayList<>();
		}

	}

	@FXML
	private void selectColor(ActionEvent e) {
		IFeature selectedFeature = annotationTable.getSelectionModel().getSelectedItem().getFeature();
		Color oldColor = selectedFeature.getColor().get();
		ColorPicker picker = new ColorPicker(selectedFeature.getColor().orElse(new Color(1, 1, 1, 1)));
		Scene scne = new Scene(picker);
		Stage stage = new Stage();
		stage.setTitle("Select Color");
		stage.setScene(scne);
		picker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedFeature.setColor(picker.getValue());
				services.eventBroker.send(FDEventTable.UPDATE_FEATURE, selectedFeature);
				services.eventBroker.send(FDEventTable.UPDATE_FEATURE,
						new Pair<Color, Color>(oldColor, picker.getValue()));
				annotationTable.refresh();
				stage.close();
			}
		});
		stage.showAndWait();
	}

	@FXML
	private void selectNext(ActionEvent e) {
		IFeature selectedFeature = annotationTable.getSelectionModel().getSelectedItem().getFeature();
		services.eventBroker.post(MPLEEditorConsts.SELECT_NEXT, selectedFeature);
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
			IFeature rootFeature = rootModel.getFeature();
			FeatureDiagram diagram = new FeatureDiagram("Synthesized Feature Model", rootFeature);
			// display the finished model in the editor
			services.eventBroker.post(FDEventTable.LOAD_FEATURE_DIAGRAM, diagram);
		} else {
			throw new InvalidAnnotationException("No cluster annotated as root");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.annotationTable.setItems(clusters);
		this.annotationTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// initialize view
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		variabilityColumn.setCellFactory(
				ComboBoxTableCell.forTableColumn(Variability.DEFAULT, Variability.MANDATORY, Variability.OPTIONAL));
		variabilityColumn.setCellValueFactory(new PropertyValueFactory<>("variability"));

		childSelectionColumn.setCellFactory(ComboBoxTableCell.forTableColumn(GroupVariability.DEFAULT,
				GroupVariability.ALTERNATIVE, GroupVariability.OR));
		childSelectionColumn.setCellValueFactory(new PropertyValueFactory<>("groupVariability"));

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
			IFeature updatedFeature = e.getRowValue().getFeature();
			for (ClusterViewModel model : this.clusters) {
				if (model.getFeature().getChildren().contains(updatedFeature)) {
					String wholeWordOldName = String.format("\\b%s\\b", e.getOldValue());
					String oldDisplay = model.childrenDisplayProperty().getValue();
					String newDisplay = oldDisplay.replaceAll(wholeWordOldName, e.getNewValue());
					model.childrenDisplayProperty().set(newDisplay);
				}
			}
			e.getRowValue().nameProperty().set(e.getNewValue());
			this.annotationTable.refresh();
			services.eventBroker.send(FDEventTable.UPDATE_FEATURE, updatedFeature);
			// printDebug();
		});

		// add and remove children to cluster on children list edit
		childColumn.setOnEditCommit(e -> {
			e.consume();
			String[] tokens = e.getNewValue().split("\\s+");
			List<IFeature> newChildren = new ArrayList<>();

			for (String t : tokens) {
				for (ClusterViewModel model : this.clusters) {
					if (model.getFeature().getName().equals(t)) {
						newChildren.add(model.getFeature());
						break;
					}
				}
			}

			e.getRowValue().setChildren(newChildren);
			this.annotationTable.refresh();
			// printDebug();
		});
		annotationTableContextMenu.setAutoHide(true);
		annotationTable.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if (e.isControlDown()) {
				if (e.getCode() == KeyCode.DOWN) {
					selectNext(new ActionEvent());
				}
			}
		});
	}

	@FXML
	private void mergeFeature() {
		try {
			List<ClusterViewModel> selection = new ArrayList<ClusterViewModel>(
					annotationTable.getSelectionModel().getSelectedItems());
			if (selection.size() > 1) {
				ClusterViewModel first = selection.get(0);
				selection.remove(first);

				selection.forEach(viewmodel -> {
					first.getFeature().getArtifactUUIDs().addAll(viewmodel.getFeature().getArtifactUUIDs());
					currentMpl.getFeatureModel().get().getAllFeatures().remove(viewmodel.getFeature());
					currentMpl.getFeatureModel().get().getAllFeatures().forEach(feature -> {
						feature.removeChild(viewmodel.getFeature());
					});

					this.clusters.remove(viewmodel);
				});

				annotationTable.getItems().removeAll(selection);
				annotationTable.refresh();

				services.eventBroker.send(MPLEEditorConsts.COLORS_CHANGED,
						currentMpl.getFeatureModel().get().getAllFeatures());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
