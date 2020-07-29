package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper;

import javafx.scene.Node;

/**
 * Utility for style manipulations for JavaFX Nodes.
 * 
 * @see javafx.scene.Node
 * @author Oliver Urbaniak
 */
public class StyleUtil {
	
	/**
	 * Adds a property to the Node's style.
	 * If the property already exists the value is overwritten.
	 * 
	 * @param node
	 * @param property
	 * @param value
	 */
	static public void addProperty(Node node, String property, String value) {
		String style = node.getStyle();
		
		int propertyStartIndex = style.indexOf(property);
		boolean propertyExists = propertyStartIndex >= 0;
		if (propertyExists) {
			// overwrite property
			int valueStartIndex = style.indexOf(":", propertyStartIndex)+1;
			int valueEndIndex = style.indexOf(";", valueStartIndex);
			String oldValue = style.substring(valueStartIndex, valueEndIndex);
			node.setStyle(style.replace(oldValue, value));
		} else {
			String entry = property+": "+value+";"; 
			node.setStyle(style.concat(entry));
		}		
	}
	
	/**
	 * Removes the property from the node if available.
	 * 
	 * @param node
	 * @param property
	 */
	static public void removeProperty(Node node, String property) {
		String style = node.getStyle();
		
		int propertyStartIndex = style.indexOf(property);
		boolean propertyExists = propertyStartIndex >= 0;
		if (propertyExists) {
			int valueEndIndex = style.indexOf(";", propertyStartIndex)+1;
			String oldValue = style.substring(propertyStartIndex, valueEndIndex);
			node.setStyle(style.replace(oldValue, ""));
		}
	}
}
