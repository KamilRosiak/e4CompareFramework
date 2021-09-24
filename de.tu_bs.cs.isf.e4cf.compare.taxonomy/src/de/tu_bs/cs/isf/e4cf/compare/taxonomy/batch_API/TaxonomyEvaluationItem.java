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
	private String pathToVariants;
	private VariantTaxonomyNode groundTruthTaxonomy;
	private VariantTaxonomyNode computedTaxonomy;
	
	/**
	 * 
	 */
	public TaxonomyEvaluationItem(String _itemName, String _pathToVariants, VariantTaxonomyNode _groundTruthTaxonomy) {
		this.itemName = _itemName;
		this.pathToVariants = _pathToVariants;
		this.groundTruthTaxonomy = _groundTruthTaxonomy;
	}
	
	public String getItemName() {
		return this.itemName;
	}
	
	public String getPathToVariants() {
		return this.pathToVariants;
	}
	
	public VariantTaxonomyNode getGroundTruth() {
		return this.groundTruthTaxonomy;
	}
	
	public VariantTaxonomyNode getComputedTaxonomy() {
		return this.computedTaxonomy;
	}
	

}
