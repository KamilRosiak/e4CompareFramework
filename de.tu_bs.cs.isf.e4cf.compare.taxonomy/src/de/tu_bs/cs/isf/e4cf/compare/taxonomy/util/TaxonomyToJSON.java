/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import com.google.gson.Gson;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;

/**
 * @author developer-olan
 *
 */
public class TaxonomyToJSON {
	private Gson gsonConverter;
	
	public TaxonomyToJSON() {
		this.gsonConverter = new Gson();
	}
	
	public String convertToJSON(VariantTaxonomyNode taxonomyToConvert) {
		String JSONString = null;
		JSONString = gsonConverter.toJson(taxonomyToConvert.getVariantName());
		return JSONString;
	}
	public Gson getGsonConverter() {
		return gsonConverter;
	}
}
