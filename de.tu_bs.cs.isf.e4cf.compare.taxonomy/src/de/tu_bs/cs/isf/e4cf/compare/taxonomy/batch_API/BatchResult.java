/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.batch_API;

/**
 * @author developer-olan
 *
 */
public class BatchResult {
	// TaxonomyEvaluationItem Details
	public String mode;
	public String itemName;
	public String pathToVariants;
	
	// Secondary Taxonomy performance Measures
	public float recallValue;
	public float precisionValue;
	public float accuracyValue;
	public float errorRateValue;

	public float falseAcceptanceFPRValue;
	public float missRateTNRValue;

	// Custom Taxonomy performance measures
	public float RVPA; // Root Variant Prediction Accuracy
	public float TDA; // Tree Depth Accuracy
	public float PCRA; // Parent-Child Relation Accuracy
	public float VPLA; // Variants Per Level Accuracy
	public float NDM; // Node Displacement Measure
	
	/**
	 * Class Constructor
	 */
	public BatchResult(String _mode, String _itemName,String _pathToVariants, float _recallValue, float _precisionValue,float _accuracyValue, float _errorRateValue) {
		// Ask the Lord for help when in need
		this.mode = _mode;
		this.itemName = _itemName;
		this.pathToVariants = _pathToVariants;
		this.recallValue = _recallValue;
		this.precisionValue = _recallValue;
		this.accuracyValue = _accuracyValue;
		this.errorRateValue = _errorRateValue;
	}
	
	public String getPropertyAsCSVString() {
		return this.mode+ "," + this.itemName + "," + this.pathToVariants + "," + this.recallValue + "," + this.precisionValue + "," + this.accuracyValue + "," + this.errorRateValue;
	}

}
