package de.tu_bs.cs.isf.e4cf.core.parts.text_editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class TextEditorFactory {
	
	protected final static String TEXT_EDITOR_PLUGIN_ID = "de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.ITextEditor";
	protected final static String TEXT_EDITOR_INSTANCE_ELEMENT = "class";
			
	// TODO: private static Map<String, String/*markup-describing object*/> _extensionToMarkup;
	
	public TextEditorFactory() {
	}
	
	public static ITextEditor getEditorFor(String extension) {
		IConfigurationElement[] _configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(TEXT_EDITOR_PLUGIN_ID);
		if (_configElements == null) {
			RCPMessageProvider.errorMessage("Plug-In Error", "No configuration elements could be retrieved from Plug-In \"ITextEditor\"");
		}
		
		try {
			// search for an editor that supports extension
			for (IConfigurationElement config : _configElements ) {
				ITextEditor textEditor = (ITextEditor)config.createExecutableExtension(TEXT_EDITOR_INSTANCE_ELEMENT);
				if (textEditor.supportedExtensions().contains(extension)) {
					return textEditor;
				}
			}
			// otherwise just return default editor that handles all extensions
			for (IConfigurationElement config : _configElements) {
				ITextEditor textEditor = (ITextEditor)config.createExecutableExtension(TEXT_EDITOR_INSTANCE_ELEMENT);
				if (textEditor.isDefault() && textEditor.supportedExtensions().contains(ITextEditor.ALL_EXTENSIONS)) {
					return textEditor;
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ITextEditor createEditor(String id) {
		IConfigurationElement[] _configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(TEXT_EDITOR_PLUGIN_ID);
		if (_configElements == null) {
			RCPMessageProvider.errorMessage("Plug-In Error", "No configuration elements could be retrieved from Plug-In \"ITextEditor\"");
		}
		
		try {
			for (IConfigurationElement config : _configElements ) {
				ITextEditor textEditor = (ITextEditor)config.createExecutableExtension(TEXT_EDITOR_INSTANCE_ELEMENT);
				if (textEditor.getId().equals(id)) {
					return textEditor;
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String getMarkupForExtension(String extension) {
		return null;
	}
	
	
	
}
