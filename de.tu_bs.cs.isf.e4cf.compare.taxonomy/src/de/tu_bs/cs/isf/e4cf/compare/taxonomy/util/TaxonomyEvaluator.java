/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;

/**
 * @author developer-olan
 *
 */
public class TaxonomyEvaluator {
	private VariantTaxonomyNode groundTruthTaxonomy;
	private VariantTaxonomyNode comparedTaxonomy;
	private int numberOfVariants;
	
	// Primary Taxonomy performance Measures
	private float truePositivesValue;
	private float trueNegativesValue;
	private float falsePositivesValue;
	private float falseNegativesValue;
	
	// Secondary Taxonomy performance Measures
	private float recallValue;
	private float precisionValue;
	private float accuracyValue;
	private float errorRateValue;
	
	private float falseAcceptanceFPRValue;
	private float missRateTNRValue;

	
	
	/**
	 * 
	 */
	public TaxonomyEvaluator(VariantTaxonomyNode _groundTruth) {
		this.groundTruthTaxonomy = _groundTruth;
	}
	
	
	public void calculatePrimaryMeasures() {
		
	}
	
	public void calculateTruePositivesValue() {
		
	}
	
	
	public void calculateSecondaryMeasures() {
		recallValue = truePositivesValue / (truePositivesValue + falseNegativesValue);
		precisionValue = truePositivesValue / (truePositivesValue + falsePositivesValue);
		accuracyValue = (truePositivesValue + trueNegativesValue) / (truePositivesValue + falsePositivesValue + trueNegativesValue + falseNegativesValue);
		errorRateValue =  1- accuracyValue;
		
		falseAcceptanceFPRValue = falsePositivesValue / (falsePositivesValue + trueNegativesValue);
		missRateTNRValue = falseNegativesValue / (truePositivesValue + falseNegativesValue);
	}
	
	public void setComparedTaxonomy(VariantTaxonomyNode _comparedTaxonomy) {
		this.comparedTaxonomy = _comparedTaxonomy;
	}
	
	
	public VariantTaxonomyNode getGroundtruthTaxonomy() {
		return this.groundTruthTaxonomy;
	}
	
	public VariantTaxonomyNode getComparedTaxonomy() {
		return this.comparedTaxonomy;
	}
	
	// Primary Measures' Getters
	public float getTruePositivesValue() {
		return this.truePositivesValue;
	}
	
	public float getTrueNegativesValue() {
		return this.trueNegativesValue;
	}
	
	public float getFalsePositivesValue() {
		return this.falsePositivesValue;
	}
	
	public float getFalseNegativesValue() {
		return this.falseNegativesValue;
	}
	
	
	// Secondary Measures' Getters
	public float getRecallValue() {
		return this.recallValue;
	}
	
	public float getPrecisionValue() {
		return this.precisionValue;
	}
	
	public float getAccuracyValue() {
		return this.accuracyValue;
	}
	
	public float getErrorRateValue() {
		return this.errorRateValue;
	}

	public float getfalseAcceptanceFPRValue() {
		return this.falseAcceptanceFPRValue;
	}
	
	public float getMissRateTNRValue() {
		return this.missRateTNRValue;
	}


}
