package de.tu_bs.cs.isf.e4cf.parser.base.abstracts;

import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.CALLBACK_EXTENSION_POINT;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.DESCRIPTION_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.ID_ATTRIBUTE;

import org.eclipse.core.runtime.IConfigurationElement;

import de.tu_bs.cs.isf.e4cf.core.plugin.ExtensionPointContext;
import de.tu_bs.cs.isf.e4cf.parser.base.INodeCallback;
import de.tu_bs.cs.isf.e4cf.parser.base.IParser;

/**
 * Default Node Callback implementation extracting the id and description from the extension and 
 * partially defining getters and setters.
 *  
 * @author Oliver Urbaniak
 */
public abstract class AbstractNodeCallback implements INodeCallback {
	private String description;
	private String id;
	private IParser parser;

	public AbstractNodeCallback(String extensionId) {
		ExtensionPointContext pluginContext = new ExtensionPointContext(CALLBACK_EXTENSION_POINT);
		IConfigurationElement config = pluginContext.getConfigElementWhere(ID_ATTRIBUTE, extensionId);
		id = config.getAttribute(ID_ATTRIBUTE);
		description = config.getAttribute(DESCRIPTION_ATTRIBUTE);
	}

	@Override
	public void setParser(IParser parser) {
		this.parser = parser;
	}

	@Override
	public IParser getParser() {
		return parser;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getId() {
		return id;
	}
}
