package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareFiles;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.matcher.util.MatcherUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class CompareEngineView implements Initializable {
    public static final int NAME_COLUMN_WIDTH_PERCENT = 60;
    public static final int TYPE_COLUMN_WIDTH_PERCENT = 40;

    @FXML
    private TableColumn<Tree, String> nameColumn;
    @FXML
    private TableColumn<Tree, String> typeColumn;
    @FXML
    private TableView<Tree> artifactTable;
    @FXML
    private ComboBox<Matcher> matcherCombo;

    @Inject
    ServiceContainer services;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	initButtons();
	initMatcherCombo();
    }

    private void initButtons() {

    }

    private void initMetricControll() {

    }


    /**
     * Add a list of artifacts to the list
     */
    @FXML
    public void addArtifacts() {
	List<FileTreeElement> parsedFiles = JavaFXBuilder
		.createFileChooser(RCPContentProvider.getCurrentWorkspacePath(), "Select More Artifacts").stream()
		.map(file -> new File(file.getAbsolutePath())).collect(Collectors.toList());
	artifactTable.getItems().addAll(ArtifactIOUtil.parseArtifacts(parsedFiles));
    }

    /**
     * Removes all selected artifacts from the artifact list.
     */
    @FXML
    public void removeArtifacts() {
	artifactTable.getItems().removeAll(artifactTable.getSelectionModel().getSelectedItem());
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
}
