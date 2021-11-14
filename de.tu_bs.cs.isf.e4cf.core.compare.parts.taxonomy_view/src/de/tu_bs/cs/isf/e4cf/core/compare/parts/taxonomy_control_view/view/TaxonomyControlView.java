/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures.TaxonomySettings;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.string_table.TaxonomyST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

/**
 * @author developer-olan
 *
 */
public class TaxonomyControlView implements Initializable  {

	
	@Inject
	ServiceContainer services;
		
	private TaxonomySettings taxonomySettings;
	
	private String taxonomyJSONTexts;
	
	@FXML
	private RadioButton LevenshteinComparatorRadioButton;
	@FXML
	private RadioButton stringComparatorRadioButton;
	
	@FXML
	private CheckBox languageCheckBoxJava;
	@FXML
	private CheckBox languageCheckBoxCplusplus;
	
	@FXML
	private CheckBox directoryNameCheckbox;
	@FXML
	private CheckBox directorySizeCheckbox;
	@FXML
	private CheckBox directoryNonSourceCheckbox;
	@FXML
	private CheckBox directoryChildenCheckbox;
	
	@FXML
	private RadioButton sourceLevelRadioButton;
	@FXML
	private RadioButton dirLevelRadioButton;
	@FXML
	private RadioButton timeOnlyTaxonomyRadioButton;
	@FXML
	private RadioButton spaceMarcTaxonomyRadioButton;
	
	@FXML
	private Button saveButton;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taxonomySettings = new TaxonomySettings();
		initializeBasicComparators();
		initializeSourceLanguages();
		initializeDirectoryMetrics();
		initializeLevelSelection();
		initializeTaxonomyType();
		initializeSaveButton();
		addAllTaxonomyUpdateListeners();
	}
	
	private void initializeSourceLanguages() {
		languageCheckBoxJava.setSelected(true);
		languageCheckBoxCplusplus.setSelected(false);
	}
	
	private void addAllTaxonomyUpdateListeners() {
		
		// Set Action/Event Handlers for source level Comparison 
		sourceLevelRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setSourceLevelComparison(new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
			    	  
		});
		
		// Set Action/Event Handlers for source level Comparison 
		dirLevelRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setSourceLevelComparison(!new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});  
		
		// Set Action/Event Handlers for Taxonomy Type 
		timeOnlyTaxonomyRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setTaxonomyTypeAsTree(new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
			    	  
		});
				
		// Set Action/Event Handlers for Taxonomy Type 
		spaceMarcTaxonomyRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setTaxonomyTypeAsTree(!new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});  
		
			
		// Set Action/Event Handlers for Levenshtein Comparator Mode Radio Buttons
		LevenshteinComparatorRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setLevenshteinMode(new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
			    	  
		});
		
		// Set Action/Event Handlers for Levenshtein Comparator Mode Radio Buttons
		stringComparatorRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setLevenshteinMode(!new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});
		
		
		directoryNameCheckbox.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setDirNameMetric(new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});
		
		directorySizeCheckbox.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setDirSizeMetric(new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});
		
		directoryNonSourceCheckbox.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setDirNumNonSourceMetric(new_val); 
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});
		
		directoryChildenCheckbox.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setDirNumSourceMetric(new_val);
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});
		
		languageCheckBoxJava.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setLanguageJava(new_val);
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});
		
		languageCheckBoxCplusplus.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setLanguageCplusplus(new_val);
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers	    	  
		});
		
		
	}
	
	private void initializeBasicComparators() {
		ToggleGroup comparatorGroup = new ToggleGroup();
		LevenshteinComparatorRadioButton.setToggleGroup(comparatorGroup);
		stringComparatorRadioButton.setToggleGroup(comparatorGroup);
		
		services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, ""); 
		
		// Set Action/Event Handlers for Levenshtein Comparator Mode Radio Buttons
		LevenshteinComparatorRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setLevenshteinMode(new_val); 
			    	  stringComparatorRadioButton.setSelected(!new_val);
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
		});
		
		// Set Action/Event Handlers for simple string Comparator Mode Radio Buttons
		stringComparatorRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setLevenshteinMode(!new_val); 
			    	  LevenshteinComparatorRadioButton.setSelected(!new_val);
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
		});
		
		LevenshteinComparatorRadioButton.setSelected(true);
		
	}
	
	private void initializeDirectoryMetrics() {
		directoryNameCheckbox.setSelected(true);
	}
	
	private void initializeLevelSelection() {
		ToggleGroup levelGroup = new ToggleGroup();
		sourceLevelRadioButton.setToggleGroup(levelGroup);
		dirLevelRadioButton.setToggleGroup(levelGroup);
		
		// Set Action/Event Handlers for Levenshtein Comparator Mode Radio Buttons
		sourceLevelRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  
			    	  taxonomySettings.setSourceLevelComparison(new_val); // Update setting in taxonomy settings object 
			    	  dirLevelRadioButton.setSelected(!new_val); // Update second option radio button
			    	  
			    	  // Update Directory Metric(s)
			    	  directoryNameCheckbox.setDisable(true);
			    	  directorySizeCheckbox.setDisable(true);
			    	  directoryNonSourceCheckbox.setDisable(true);
			    	  directoryChildenCheckbox.setDisable(true);
			    	  
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
		});
		
		// Set Action/Event Handlers for simple string Comparator Mode Radio Buttons
		dirLevelRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setSourceLevelComparison(!new_val); // Update setting in taxonomy settings object 
			    	  sourceLevelRadioButton.setSelected(!new_val); // Update second option radio button
			    	  
			    	  // Update Directory Metric(s)
			    	  directorySizeCheckbox.setDisable(false);
			    	  directoryNonSourceCheckbox.setDisable(false);
			    	  directoryChildenCheckbox.setDisable(false);
			    	  
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
		});
		
		sourceLevelRadioButton.setSelected(true);
		
	}
	
	private void initializeTaxonomyType() {
		ToggleGroup taxonomyTypeGroup = new ToggleGroup();
		timeOnlyTaxonomyRadioButton.setToggleGroup(taxonomyTypeGroup);
		spaceMarcTaxonomyRadioButton.setToggleGroup(taxonomyTypeGroup);
		
		spaceMarcTaxonomyRadioButton.setSelected(true);
	}
	
	private void initializeSaveButton() {
//		saveButton.setDisable(true);
	}
	
	
	/**
	 * Updates subscribers on taxonomy settings
	*/
	private void sendTaxonomySettingsUIMessage() {
		// Send prepared graph to GraphView listener for display
		services.eventBroker.send(TaxonomyST.SET_TAXONOMY_SETTINGS, this.taxonomySettings); 
	}
	
	
	/**
	 * Method that handles save button action
	 */
	@FXML
	public void saveTaxonomy() {
		//this.taxonomyJSONTexts = "Hi";
		if (this.taxonomyJSONTexts != null) {
			FileChooser fileChooser = new FileChooser();
			 
            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
 
            //Show save file dialog
            File file = fileChooser.showSaveDialog(null);
 
            if (file != null) {
                saveTextToFile(this.taxonomyJSONTexts.toString(), file);
            }
		}
	}
	
	private void saveTextToFile(String content, File file) {
	        try {
	            PrintWriter writer;
	            writer = new PrintWriter(file);
	            writer.println(content);
	            writer.close();
	        } catch (IOException ex) {
	            Logger.getLogger(TaxonomyControlView.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	
	
	/**
	 * Subscribes and sets computed JSON string ready for export
	 * 
	 * @param computedTaxonomyJSON
	 */
	
	  @Optional
	  @Inject 
	  public void updateTaxonomySettings(@UIEventTopic(TaxonomyST.SAVE_TAXONOMY_EVENT) String computedTaxonomyJSON) {
		  this.taxonomyJSONTexts = computedTaxonomyJSON; 
	  }
	 
	
	
}
