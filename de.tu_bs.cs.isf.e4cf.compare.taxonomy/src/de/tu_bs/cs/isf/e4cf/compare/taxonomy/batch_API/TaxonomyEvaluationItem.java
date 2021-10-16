/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.batch_API;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;

/**
 * @author developer-olan
 *
 */
public class TaxonomyEvaluationItem {

	private String itemName;
	private String variantsDeveloper;
	private String problemClass;
	private String noOfVariants;
	private String pathToVariants;
	private VariantTaxonomyNode groundTruthTaxonomy;
	private VariantTaxonomyNode computedTaxonomy;
	
	/**
	 * Constructor 
	 */
	public TaxonomyEvaluationItem(String _itemName, String _pathToVariants, VariantTaxonomyNode _groundTruthTaxonomy) {
		this.itemName = _itemName;
		this.pathToVariants = _pathToVariants;
		this.groundTruthTaxonomy = _groundTruthTaxonomy;
	}
	
	/**
	 * Constructor 
	 */
	public TaxonomyEvaluationItem(String _itemName, String _variantsDeveloper, String _problemClass, String _noOfVariants, String _pathToVariants, VariantTaxonomyNode _groundTruthTaxonomy) {
		this.itemName = _itemName;
		this.variantsDeveloper = _variantsDeveloper;
		this.problemClass = _problemClass;
		this.noOfVariants = _noOfVariants;
		this.pathToVariants = _pathToVariants;
		this.groundTruthTaxonomy = _groundTruthTaxonomy;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	
	public String getPathToVariants() {
		return this.pathToVariants;
	}
	
	public String getVariantsDeveloper() {
		return this.variantsDeveloper;
	}
	
	public String getProblemClass() {
		return this.problemClass;
	}
	
	public String getNoOfVariants() {
		return this.noOfVariants;
	}
	
	public VariantTaxonomyNode getGroundTruth() {
		return this.groundTruthTaxonomy;
	}
	
	public VariantTaxonomyNode getComputedTaxonomy() {
		return this.computedTaxonomy;
	}
	

}
