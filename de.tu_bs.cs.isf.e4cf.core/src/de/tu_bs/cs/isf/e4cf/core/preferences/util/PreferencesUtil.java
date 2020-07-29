package de.tu_bs.cs.isf.e4cf.core.preferences.util;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

import de.tu_bs.cs.isf.e4cf.core.preferences.util.key_value.KeyValueNode;

public class PreferencesUtil {
	
	public static void storeKeyValueNode(KeyValueNode keyValueNode) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(keyValueNode.getNode());
		prefs.put(keyValueNode.getKey(), keyValueNode.getStringValue());
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	public static KeyValueNode getValue(String node, String key) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(node);
		return new KeyValueNode(key, node , prefs.get(key, "null"));
	}
	
	public static KeyValueNode getValueWithDefault(String node, String key, String defaultVal) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(node);
		return new KeyValueNode(key, node , prefs.get(key, defaultVal));
	}
	
	public static String getValueAsString(String node, String key) {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(node);
		return  prefs.get(key, "null");
	}
}
