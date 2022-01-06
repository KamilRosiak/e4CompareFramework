/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.view;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.TaxonomySettings;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * @author developer-olan
 *
 */
public class TaxonomyControlView implements Initializable {

	@FXML
	private RadioButton levenshteinComparatorRadioButton;
	@FXML
	private RadioButton stringComparatorRadioButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ToggleGroup toggleGroup = new ToggleGroup();
		levenshteinComparatorRadioButton.setToggleGroup(toggleGroup);
		stringComparatorRadioButton.setToggleGroup(toggleGroup);
		levenshteinComparatorRadioButton.setSelected(TaxonomySettings.useLevenshteinComparison);
		stringComparatorRadioButton.setSelected(!TaxonomySettings.useLevenshteinComparison);

		levenshteinComparatorRadioButton.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					TaxonomySettings.useLevenshteinComparison = true;
				});

		stringComparatorRadioButton.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					TaxonomySettings.useLevenshteinComparison = false;
				});
		
		
		
	}

}
