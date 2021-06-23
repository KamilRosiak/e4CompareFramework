package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import java.io.File;
import java.nio.file.Paths;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.Granularity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;

public class GeneratorViewController {
	
	private GeneratorOptions options;
	@Inject private ServiceContainer services;
	
	public GeneratorOptions getOptions() {
		
		options = new GeneratorOptions();
		
		RadioButton selectedRadioButton = (RadioButton) granularityGroup.getSelectedToggle();
		String toggleGroupId = selectedRadioButton.getId();
		switch (toggleGroupId) {
		case "radioGranularityClass":
			options.granularity = Granularity.CLASS;
			break;
		case "radioGranularityMethod":
			options.granularity = Granularity.METHOD;
			break;
		default: // statement
			options.granularity = Granularity.STATEMENT;
			break;
		}
		
		options.outputRoot = Paths.get(textRootPath.getText());
		
		return options;
	}
	
	public void init() {
		toggleGroup();
		textRootPath.setText(services.workspaceFileSystem.getWorkspaceDirectory().getFile().toString() + "\\ 02 Trees");
	}
	
	// ------ RootPath Settings ------
	
	@FXML
	public TextField textRootPath;
	@FXML
	public Button btnBrowse;
	
	@FXML
	public void browseFiles() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(services.workspaceFileSystem.getWorkspaceDirectory().getFile().toFile());
		File selectedFile = directoryChooser.showDialog(null);
		textRootPath.setText(selectedFile.toString());
	}
	
	// ------ Granularity Settings ------
	
	@FXML
	public RadioButton radioGranularityClass;
	@FXML
	public RadioButton radioGranularityMethod;
	@FXML
	public RadioButton radioGranularityStatement;

	public ToggleGroup granularityGroup = new ToggleGroup();

	private void toggleGroup() {
		radioGranularityClass.setToggleGroup(granularityGroup);
		radioGranularityMethod.setToggleGroup(granularityGroup);
		radioGranularityStatement.setToggleGroup(granularityGroup);
		radioGranularityStatement.setSelected(true);
	}
	
	// ------ Generation Settings ------
	
	
}
