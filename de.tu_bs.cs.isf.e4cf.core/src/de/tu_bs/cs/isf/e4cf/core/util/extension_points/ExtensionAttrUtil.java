package de.tu_bs.cs.isf.e4cf.core.util.extension_points;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;


public class ExtensionAttrUtil {
	
	/**
	 * This method loads all attributes from an extension points and returns them as a list
	 */
	public static <K> List<K> getAttrsFromExtension(String ExtensionPoint, String attributeName) {
		IConfigurationElement[] configs = RCPContentProvider.getIConfigurationElements(ExtensionPoint);	
		List<K> objects = new ArrayList<K>();
		for(IConfigurationElement config : configs) {
			try {
				@SuppressWarnings("unchecked")
				K attr = (K) config.createExecutableExtension(attributeName);
				objects.add(attr);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return objects;
	}
}
