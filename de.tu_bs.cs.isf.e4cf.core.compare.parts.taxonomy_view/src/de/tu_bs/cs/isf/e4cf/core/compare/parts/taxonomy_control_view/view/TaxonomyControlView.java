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
import org.eclipse.e4.core.services.events.IEventBroker;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures.TaxonomyControlSettings;
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
		
	private TaxonomyControlSettings taxonomySettings;
	
	private String taxonomyJSONTexts;
	
	@FXML
	private CheckBox languageCheckBoxJava;
	@FXML
	private CheckBox languageCheckBoxCplusplus;
	
	@FXML
	private RadioButton asymmetricRadioButton;
	@FXML
	private RadioButton symmetricRadioButton;
	
	@FXML
	private CheckBox directoryNameCheckbox;
	@FXML
	private CheckBox directorySizeCheckbox;
	@FXML
	private CheckBox directoryDateCreatedCheckbox;
	@FXML
	private CheckBox directoryDateModifiedCheckbox;
	@FXML
	private CheckBox directoryNonSourceCheckbox;
	@FXML
	private CheckBox directoryChildenCheckbox;
	@FXML
	private CheckBox directoryPathCheckbox;
	@FXML
	private CheckBox directoryChildSignatureCheckbox;
	
	@FXML
	private Button saveButton;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taxonomySettings = new TaxonomyControlSettings();
		initializeSourceLanguages();
		initializeAsymmetryMode();
		initializeDirectoryMetrics();
		initializeSaveButton();
	}
	
	private void initializeSourceLanguages() {
		languageCheckBoxJava.setSelected(true);
		languageCheckBoxCplusplus.setSelected(false);
	}
	
	private void initializeAsymmetryMode() {
		ToggleGroup group = new ToggleGroup();
		asymmetricRadioButton.setToggleGroup(group);
		symmetricRadioButton.setToggleGroup(group);
		asymmetricRadioButton.setSelected(taxonomySettings.getAsymmetryMode());
		
		services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, ""); 
		
		// Set Action/Event Handlers for Asymmetry Mode Radio Buttons
		asymmetricRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setAsymmetryMode(new_val); 
			    	  symmetricRadioButton.setSelected(!new_val);
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
		});
		
		// Set Action/Event Handlers for Symmetry Mode Radio Buttons
		symmetricRadioButton.selectedProperty().addListener(
			      (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    	  taxonomySettings.setAsymmetryMode(!new_val); 
			    	  asymmetricRadioButton.setSelected(!new_val);
			    	  sendTaxonomySettingsUIMessage(); // Update Subscribers
		});
		
		asymmetricRadioButton.setSelected(true);
		
	}
	
	private void initializeDirectoryMetrics() {
		directoryNameCheckbox.setSelected(true);
		directorySizeCheckbox.setSelected(true);
	}
	
	private void initializeSaveButton() {
//		saveButton.setDisable(true);
	}
	
	
	/**
	 * Updates subscribers on taxonomy settings
	*/
	private void sendTaxonomySettingsUIMessage() {
		// Send prepared graph to GraphView listener for display
		services.eventBroker.send(TaxonomyST.SAVE_TAXONOMY_EVENT, this.taxonomySettings); 
	}
	
	
	/**
	 * Method that handles save button action
	 */
	@FXML
	public void saveTaxonomy() {
		this.taxonomyJSONTexts = "Hi";
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
	
	
}
