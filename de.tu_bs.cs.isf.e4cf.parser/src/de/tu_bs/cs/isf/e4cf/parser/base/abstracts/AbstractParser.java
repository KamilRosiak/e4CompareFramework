package de.tu_bs.cs.isf.e4cf.parser.base.abstracts;

import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.DESCRIPTION_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.ID_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.PARSER_EXTENSION_POINT;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;

import de.tu_bs.cs.isf.e4cf.core.plugin.ExtensionPointContext;
import de.tu_bs.cs.isf.e4cf.parser.base.INodeCallback;
import de.tu_bs.cs.isf.e4cf.parser.base.IParser;

/**
 * Default Parser implementation extracting the description from the extension and 
 * partially defining getters and setters. It also provides default Node Callback handling.
 *  
 * @author Oliver Urbaniak
 */
public abstract class AbstractParser implements IParser {

	protected List<INodeCallback> callbacks;
	protected String description;
	protected Path filePath;
	protected Map<String, String> initialData;
	 
	
	/**
	 * Sets an empty description
	 */
	protected AbstractParser() {
		this.callbacks = new ArrayList<>();
		this.description = "";
	}
	
	@Override
	public void setInitializationData(Map<String, String> initData) {
		this.initialData = initData;
	}
	
	/**
	 * Retrieves the description from the plugin
	 * 
	 * @param extensionId
	 */
	protected AbstractParser(String extensionId) {
		this.callbacks = new ArrayList<>();
		ExtensionPointContext pluginContext = new ExtensionPointContext(PARSER_EXTENSION_POINT);
		IConfigurationElement config = pluginContext.getConfigElementWhere(ID_ATTRIBUTE, extensionId);
		this.description = config.getAttribute(DESCRIPTION_ATTRIBUTE);
	}
	
	/**
	 * Convenience method that initiates post-processing of all node callbacks.
	 */
	protected void finish() {
		callbacks.forEach(nc -> nc.postProcessing());
	}
	
	@Override
	public void addNodeCallable(INodeCallback nodeCallable) {
		if (callbacks.stream().noneMatch(callback -> callback.getId().equals(nodeCallable.getId()))) {
			callbacks.add(nodeCallable);			
		}
	}

	@Override
	public void removeNodeCallable(INodeCallback nodeCallable) {
		callbacks.removeIf(nc -> nodeCallable == nc);
	}
	
	@Override
	public void removeAllNodeCallables() {
		callbacks.clear();	
	}

	@Override
	public List<INodeCallback> getNodeCallables() {
		return Collections.unmodifiableList(callbacks);
	}
	
    public void setFilePath(Path filePath) {
    	this.filePath = filePath;
    }
	
	public Path getFilePath() {
		return filePath;
	}
      	
	@Override
	public String getDescription() {
		return description;
	}
}

