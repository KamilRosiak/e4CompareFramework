package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class EvaluatorViewController {

	private EvaluatorOptions options;

	public EvaluatorOptions getOptions() {
		options = new EvaluatorOptions();
		
		// Selection
		options.doIntraEvaluation = checkIntra.isSelected();
		options.doInterEvaluation = checkInter.isSelected();
		options.doTaxonomyEvaluation= checkTaxonomy.isSelected();
		
		// Options
		options.isLogVerbose = checkVerbose.isSelected();
		
		return options;
	}

	public void init() { }
	
	// ------ Evaluation Selection ------
	@FXML
	public CheckBox checkIntra;
	@FXML
	public CheckBox checkInter;
	@FXML
	public CheckBox checkTaxonomy;
	
	// ------ Evaluation Settings ------
	@FXML
	public CheckBox checkVerbose;

}
