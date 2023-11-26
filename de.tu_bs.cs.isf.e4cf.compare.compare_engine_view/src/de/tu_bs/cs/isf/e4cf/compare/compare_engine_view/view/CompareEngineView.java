package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareFiles;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.matcher.util.MatcherUtil;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;
import de.tu_bs.cs.isf.e4cf.compare.preferences.ComparisonPrefs;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class CompareEngineView implements Initializable {
	public static final int NAME_COLUMN_WIDTH_PERCENT = 60;
	public static final int TYPE_COLUMN_WIDTH_PERCENT = 40;

	public List<FileTreeElement> artifactFileTrees = new ArrayList<FileTreeElement>();

	@FXML
	private TableColumn<Tree, String> nameColumn;
	@FXML
	private TableColumn<Tree, String> typeColumn;
	@FXML
	private TableView<Tree> artifactTable;
	@FXML
	private ComboBox<Matcher> matcherCombo;

	@FXML
	private RadioButton twodimRadio, weightedRadio;
	@Inject
	ServiceContainer services;
	@Inject
	IEclipseContext context;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initButtons();
		initMatcherCombo();
	}

	private void initButtons() {

	}

	// TODO: impl
	private Metric getSelectedMetric() {
		return new MetricImpl("test");
	}

	@FXML
	private void selectMetric() {

	}

	@FXML
	public void compareArtifacts() {
		try {
			new Job("compare job") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					ComparisonPrefs pref = new ComparisonPrefs();

					List<Tree> variants = artifactTable.getItems();
					MPLPlatform platform = new MPLPlatform();
					// if spl compare is active
					if (!twodimRadio.isSelected()) {
						CompareEngineHierarchical engine = new CompareEngineHierarchical(getSelectedMatcher(),
								getSelectedMetric());
						platform = new MPLPlatform(engine, pref, false);
					}

					platform.insertVariants(variants, services);

					String path = services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//"
							+ "clone_model.mpl";
					// if spl compare is active
					if (!twodimRadio.isSelected()) {
						path = services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//"
								+ "clone_model.spl";
					}
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							services.partService.showPart(MPLEEditorConsts.TREE_VIEW_ID);
							services.partService.showPart(MPLEEditorConsts.PLATFORM_VIEW);
						}
					});

					services.eventBroker.send(MPLEEditorConsts.SHOW_MPL, platform);
					MPLEPlatformUtil.storePlatform(path, platform);
					return Status.OK_STATUS;
				}
			}.schedule();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a list of artifacts to the list
	 */
	@FXML
	public void addArtifacts() {
		try {
			List<FileTreeElement> selectedFiles = JavaFXBuilder
					.createFileChooser(RCPContentProvider.getCurrentWorkspacePath(), "Select More Artifacts").stream()
					.map(file -> new File(file.getAbsolutePath())).collect(Collectors.toList());
			collectParsedFilePaths(selectedFiles);

			artifactTable.getItems().addAll(ArtifactIOUtil.parseArtifacts(selectedFiles, context));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes all selected artifacts from the artifact list.
	 */
	@FXML
	public void removeArtifacts() {
		artifactTable.getItems().removeAll(artifactTable.getSelectionModel().getSelectedItem());
		removeFromArtifactFileDetailList();
	}

	/**
	 * This method loads all matcher from the Matcher extension point and puts them
	 * into the combobox.
	 */
	private void initMatcherCombo() {
		matcherCombo.getItems().addAll(FXCollections.observableArrayList(MatcherUtil.getAllMatcher()));
		matcherCombo.getSelectionModel().selectFirst();
		// ToolTip shows the description of the selected matcher
		Tooltip toolTip = new Tooltip(matcherCombo.getSelectionModel().getSelectedItem().getMatcherDescription());
		toolTip.setGraphic(new ImageView(services.imageService.getURL(CompareST.BUNDLE_NAME, CompareFiles.POP_UP_BG)));
		matcherCombo
				.setTooltip(new Tooltip(matcherCombo.getSelectionModel().getSelectedItem().getMatcherDescription()));
	}

	/**
	 * This method returns the selected matcher.
	 */
	public Matcher getSelectedMatcher() {
		return matcherCombo.getSelectionModel().getSelectedItem();
	}

	/**
	 * Subscribes to compare artifacts publisher messages from project explorer and
	 * adds file path all selected artifacts to artifact file path List for further
	 * processing
	 * 
	 * @param parsedFiles
	 */
	@Optional
	@Inject
	public void collectArtifactsFileDetails(
			@UIEventTopic(CompareST.LOAD_ARTIFACTS_PATH_EVENT) List<FileTreeElement> parsedFiles) {
		artifactFileTrees.clear(); // remove all items from previous selection
		collectParsedFilePaths(parsedFiles); // add newly parsed files
	}

	/**
	 * Shows artifacts in in the artifact table
	 * 
	 * @param artifacts
	 */
	@Optional
	@Inject
	public void showArtifacts(@UIEventTopic(CompareST.LOAD_ARTIFACTS_EVENET) List<Tree> artifacts) {
		services.partService.showPart(CompareST.BUNDLE_NAME);
		artifactTable.getItems().clear();
		artifactTable.getItems().addAll(artifacts);
	}

	/**
	 * Add a all selected artifacts to List
	 */
	public void collectParsedFilePaths(List<FileTreeElement> parsedFiles) {
		for (FileTreeElement parsedFile : parsedFiles) {
			artifactFileTrees.add(parsedFile);
		}
	}

	/**
	 * Removes item selected for removal in artifact Table from artifact file tree
	 * list
	 */
	public void removeFromArtifactFileDetailList() {
		Tree selectedArtifact = artifactTable.getSelectionModel().getSelectedItem();
		List<FileTreeElement> artifactsToRemove = new ArrayList<FileTreeElement>();
		for (FileTreeElement anArtifactDetail : artifactFileTrees) {
			if (anArtifactDetail.getFileName().equals(selectedArtifact.getTreeName())) {
				// anArtifactDetail.getAbsolutePath()
				artifactsToRemove.add(anArtifactDetail);
			}
		}
		artifactFileTrees.removeAll(artifactsToRemove);
	}
}