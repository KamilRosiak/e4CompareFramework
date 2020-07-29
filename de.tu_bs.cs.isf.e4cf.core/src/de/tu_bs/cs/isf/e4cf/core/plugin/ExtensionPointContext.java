package de.tu_bs.cs.isf.e4cf.core.plugin;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * Extension Point class for collecting all instances of {@link IConfigurationElement} for 
 * an extension point id.
 * 
 * @author Oliver Urbaniak
 */
public class ExtensionPointContext {

	private List<IConfigurationElement> configs;
	
	public ExtensionPointContext(String extensionPointId) {
		this.configs = Arrays.asList(Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointId));
	}
	
	/**
	 * Gets the instance of IConfigurationElement where the <b>attributes</b> value equals <b>value</b>
	 * 
	 * @param attribute the plugin attribute
	 * @param value the value to be matched with
	 * @return the element with <b>value</b> as the <b>attribute</b> value or null if no match is found
	 */
	public IConfigurationElement getConfigElementWhere(String attribute, String value) {
		for (IConfigurationElement c : configs) {
			String attr = c.getAttribute(attribute);
			if (attr.equals(value)) {
				return c;
			}
		}
		return null;
	}
	
	public List<IConfigurationElement> getConfigurationElements() {
		return configs;
	}

}