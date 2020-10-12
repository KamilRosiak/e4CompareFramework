package de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;

/**
 * This class represents a value pair to save persistently in the meta data.
 * @author {Kamil Rosiak}
 */
public class KeyValueNode {
	private String key;
	private String value;
	private String node;
	
	public KeyValueNode(String key, String node) {
		setKey(key);
		setNode(node);
		initValue();
	}
	
	public KeyValueNode(String key,String node , String value) {
		this(key,node);
		setValue(value);
	}
	
	public KeyValueNode(String key,String node , boolean value) {
		this(key,node);
		setValue(value);
	}
	
	public KeyValueNode(String key,String node , int value) {
		this(key,node);
		setValue(value);
	}
	
	private void initValue() {
		setValue(PreferencesUtil.getValueAsString(node, key));
	}
	
	public String getStringValue() {
		return value;
	}
	
	public boolean getBoolValue() {
		return Boolean.valueOf(value);
	}
	
	public float getFloatValue() {
		return Float.parseFloat(value);
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setValue(int value) {
		this.value = Integer.toString(value);
	}
	
	public void setValue(boolean value) {
		this.value = Boolean.toString(value);
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}	
}
