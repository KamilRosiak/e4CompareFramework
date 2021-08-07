/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import com.google.gson.*;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;

/**
 * @author developer-olan
 *
 */
public class TaxonomyToJSON {
	public Gson gsonConverter;
	
	public TaxonomyToJSON() {
		this.gsonConverter = new Gson();
	}
	
	public String convertToJSON(VariantTaxonomyNode taxonomyToCOnvert) {
		String JSONString = null;
		JSONString = gsonConverter.toJson(taxonomyToCOnvert);
		return JSONString;
	}
	public Gson getGsonConverter() {
		return gsonConverter;
	}
}
