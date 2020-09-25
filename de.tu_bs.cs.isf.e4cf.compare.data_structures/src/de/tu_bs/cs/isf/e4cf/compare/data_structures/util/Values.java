package de.tu_bs.cs.isf.e4cf.compare.data_structures.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the storage for values that can be identified with their variant-number.
 * @author Kamil Rosiak
 *
 */
public class Values {
	private Map<String,String> values;
	
	/**
	 * Basic constructor for the creation of an values object.
	 */
	public Values() {
		setValues(new HashMap<String, String>());
	}
	
	/**
	 * This constructor initializes the data structurer and puts a initial value into it.
	 */
	public Values(String varaiant, String value) {
		this();
		values.put(varaiant, value);
	}
	
	public void putValue(String variant, String value) {
		values.put(variant, value);
	}
	
	public Map<String,String> getValues() {
		return values;
	}

	public void setValues(Map<String,String> values) {
		this.values = values;
	}
}
