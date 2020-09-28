package de.tu_bs.cs.isf.e4cf.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;

/**
 * Helper class that provides current context.
 * @author {Kamil Rosiak}
 *
 */
public class RCPContentProvider {
   
    /**
     * This method returns the path of the current workspace location
     * @return path of the current workspace location
     */
	public static String getCurrentWorkspacePath() {
		Location instanceLocation = Platform.getInstanceLocation();
		return instanceLocation.getURL().getPath().substring(1);
	}
	
	
	public static IConfigurationElement[] getIConfigurationElements(String extensionPoint) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
	    IConfigurationElement[] configs;
	    configs = reg.getConfigurationElementsFor(extensionPoint);
	    return configs;
	}
	
	/**
	 * This method creates a list of object that are provides by a given extension point.
	 */
	public static <T>List<T> getInstanceFromBundle(String point, String extensionID) {
		List<T> objects = new ArrayList<T>();
	    for (IConfigurationElement config : getIConfigurationElements(point)) {
			try {
				objects.add((T)config.createExecutableExtension(extensionID));
			} catch (CoreException e) {
				e.printStackTrace();
				return null;
			}			
		}
		return objects;
	}

	
}
