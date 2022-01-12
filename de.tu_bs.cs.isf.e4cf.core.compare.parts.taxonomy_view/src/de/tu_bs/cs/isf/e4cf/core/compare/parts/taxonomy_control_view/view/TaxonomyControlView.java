/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.view;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.TaxonomySettings;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.StringComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.TaxonomyConstruction;

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
	private RadioButton damerauLevenshteinComparatorRadioButton;

	@FXML
	private RadioButton simpleComparatorRadioButton;

	@FXML
	private RadioButton jaccardComparatorRadioButton;

	@FXML
	private RadioButton similarityModeRadioButton;

	@FXML
	private RadioButton reachabilityModeRadioButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ToggleGroup toggleGroup = new ToggleGroup();
		levenshteinComparatorRadioButton.setToggleGroup(toggleGroup);
		damerauLevenshteinComparatorRadioButton.setToggleGroup(toggleGroup);
		simpleComparatorRadioButton.setToggleGroup(toggleGroup);
		jaccardComparatorRadioButton.setToggleGroup(toggleGroup);

		toggleGroup = new ToggleGroup();
		similarityModeRadioButton.setToggleGroup(toggleGroup);
		reachabilityModeRadioButton.setToggleGroup(toggleGroup);

		levenshteinComparatorRadioButton
				.setSelected(TaxonomySettings.comparisonAlgorithm == StringComparison.LEVENSHTEIN);
		damerauLevenshteinComparatorRadioButton
				.setSelected(TaxonomySettings.comparisonAlgorithm == StringComparison.DAMERAULEVENSHTEIN);

		simpleComparatorRadioButton.setSelected(TaxonomySettings.comparisonAlgorithm == StringComparison.SIMPLE);

		similarityModeRadioButton.setSelected(TaxonomySettings.taxonomyConstruction == TaxonomyConstruction.SIMILARITY);
		reachabilityModeRadioButton
				.setSelected(TaxonomySettings.taxonomyConstruction == TaxonomyConstruction.REACHABILITY);

		jaccardComparatorRadioButton.setSelected(TaxonomySettings.comparisonAlgorithm == StringComparison.JACCARD);

		levenshteinComparatorRadioButton.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					TaxonomySettings.comparisonAlgorithm = StringComparison.LEVENSHTEIN;
				});

		damerauLevenshteinComparatorRadioButton.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					TaxonomySettings.comparisonAlgorithm = StringComparison.DAMERAULEVENSHTEIN;
				});

		simpleComparatorRadioButton.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					TaxonomySettings.comparisonAlgorithm = StringComparison.SIMPLE;
				});

		jaccardComparatorRadioButton.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					TaxonomySettings.comparisonAlgorithm = StringComparison.JACCARD;
				});

		reachabilityModeRadioButton.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					TaxonomySettings.taxonomyConstruction = TaxonomyConstruction.REACHABILITY;
				});

		similarityModeRadioButton.selectedProperty()
				.addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
					TaxonomySettings.taxonomyConstruction = TaxonomyConstruction.SIMILARITY;
				});

	}

}
