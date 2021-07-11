package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import java.io.File;
import java.nio.file.Paths;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.Granularity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;

public class GeneratorViewController {
	
	private GeneratorOptions options;
	@Inject private ServiceContainer services;
	
	public GeneratorOptions getOptions() {
		
		options = new GeneratorOptions();
		
		// Path
		options.outputRoot = Paths.get(textRootPath.getText());
		
		// Granularity
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
		
		// Options
		options.enableCrossover = checkboxCrossover.isSelected();
		options.enableSaveAll = checkboxSaveAll.isSelected();
		try {
			options.mutations = Integer.parseInt(textNumberMutations.getText());
		} catch (NumberFormatException e) {
			System.out.println("WARN! There was an issue parsing the mutation count, falling back to default value.");
			options.mutations = 1;
		}
		
		// Type Weights
		try {
			options.weightType1 = Integer.parseInt(textTypeWeight1.getText());
			options.weightType2 = Integer.parseInt(textTypeWeight2.getText());
			options.weightType3 = Integer.parseInt(textTypeWeight3.getText());
		} catch (NumberFormatException e) {
			System.out.println("WARN! There was an issue parsing the clone type weights, falling back to default values.");
			options.weightType1 = 50;
			options.weightType2 = 35;
			options.weightType3 = 15;
		}
		
		return options;
	}
	
	public void init() {
		toggleGroup();
		textRootPath.setText(services.workspaceFileSystem.getWorkspaceDirectory().getFile().toString() + "\\ 02 Trees");
	
		// restrict some text fields to numbers only
		restrictTextfieldToNumbers(textNumberMutations);
		restrictTextfieldToNumbers(textTypeWeight1);
		restrictTextfieldToNumbers(textTypeWeight2);
		restrictTextfieldToNumbers(textTypeWeight3);
		
	}
	
	private void restrictTextfieldToNumbers(TextField field) {
		field.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	field.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
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
	
	@FXML
	public CheckBox checkboxCrossover;
	
	@FXML 
	public CheckBox checkboxSaveAll;
	
	@FXML 
	public TextField textNumberMutations;
	
	@FXML
	public TextField textTypeWeight1;
	
	@FXML
	public TextField textTypeWeight2;
	
	@FXML
	public TextField textTypeWeight3;
	
}
