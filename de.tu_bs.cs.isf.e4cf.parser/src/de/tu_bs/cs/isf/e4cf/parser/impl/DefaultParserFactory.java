package de.tu_bs.cs.isf.e4cf.parser.impl;

import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.CALLBACK_EXTENSION_POINT;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.DESCRIPTION_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.ID_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.INSTANCE_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.PARSER_EXTENSION_POINT;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.PARSER_PROCESS_EXTENSION_POINT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import de.tu_bs.cs.isf.e4cf.core.plugin.ExtensionPointContext;
import de.tu_bs.cs.isf.e4cf.parser.base.INodeCallback;
import de.tu_bs.cs.isf.e4cf.parser.base.IParser;
import de.tu_bs.cs.isf.e4cf.parser.base.IParserProcess;
import de.tu_bs.cs.isf.e4cf.parser.base.ParserType;
import de.tu_bs.cs.isf.e4cf.parser.base.helper.IParserFactory;

/**
 * Default implementation for the {@link IParserFactory} that is able to create instances
 * of all parser extensions.
 * 
 * @author Oliver Urbaniak
 */
public class DefaultParserFactory implements IParserFactory {
	private ExtensionPointContext _parserPluginContext;
	private ExtensionPointContext _callablePluginContext;
	private ExtensionPointContext _parserAppPluginContext;
	
	public DefaultParserFactory() {
		_parserPluginContext = new ExtensionPointContext(PARSER_EXTENSION_POINT);
		_callablePluginContext = new ExtensionPointContext(CALLBACK_EXTENSION_POINT);
		_parserAppPluginContext = new ExtensionPointContext(PARSER_PROCESS_EXTENSION_POINT);
	}
	
	private static <T> T createExtensionInstance(ExtensionPointContext pluginContext, String id) throws CoreException, NoSuchElementException {
		IConfigurationElement config = pluginContext.getConfigElementWhere(ID_ATTRIBUTE, id);
		T instance = createInstance(config);
		return instance;						
		
	}

	@SuppressWarnings("unchecked")
	private static <T> T createInstance(IConfigurationElement config) throws CoreException {
		return (T) config.createExecutableExtension(INSTANCE_ATTRIBUTE);
	}
	
	public static <T> List<T> createAllExtensionInstances(ExtensionPointContext pluginContext) throws CoreException {
		List<T> extenstionInstances = new ArrayList<T>();
		for (IConfigurationElement config : pluginContext.getConfigurationElements()) {
			extenstionInstances.add(createInstance(config));
		}
		return extenstionInstances;
	}
	
	@Override
	public IParser createParser(String parserId, Map<String, String> initData) throws CoreException, NoSuchElementException {
		IParser parser = createExtensionInstance(_parserPluginContext, parserId);
		parser.setInitializationData(initData != null ? initData : Collections.emptyMap());
		return parser;
	}
	
	@Override
	public String getParserDescription(String parserId) {
		IConfigurationElement config = _parserPluginContext.getConfigElementWhere(ID_ATTRIBUTE, parserId);
		return config.getAttribute(DESCRIPTION_ATTRIBUTE);
	}

	@Override
	public INodeCallback createNodeCallable(String callableId) throws NoSuchElementException, CoreException {	
		return createExtensionInstance(_callablePluginContext, callableId);
	}

	@Override
	public List<IParser> getAllParsers() throws CoreException {
		List<IParser> parsers = createAllExtensionInstances(_parserPluginContext);
		parsers.forEach(parser -> parser.setInitializationData(Collections.emptyMap()));
		return parsers;
	}

	@Override
	public IParserProcess createParserApplication(String parserAppId) throws NoSuchElementException, CoreException {
		IParserProcess parserApp = createExtensionInstance(_parserAppPluginContext, parserAppId);
		return parserApp;
	}
	
	@Override
	public List<IParserProcess> getAllParserApplicationsFor(ParserType parserType) throws CoreException {
		List<IParserProcess> typedParserApps = new ArrayList<>();
		List<IParserProcess> applications = createAllExtensionInstances(_parserAppPluginContext);
		applications.stream()
			.filter(parserApp -> parserApp.getType() == parserType)
			.forEach(parserApp -> typedParserApps.add(parserApp));
		return typedParserApps;
	}

	@Override
	public IParserProcess createDefaultParserApplicationFor(ParserType parserType) throws CoreException {
		IConfigurationElement config = _parserAppPluginContext.getConfigElementWhere("isDefault", "true");
		return createInstance(config);
	}

	@Override
	public String getParserAppDescription(String parserAppId) {
		IConfigurationElement config = _parserAppPluginContext.getConfigElementWhere(ID_ATTRIBUTE, parserAppId);
		return config.getAttribute(DESCRIPTION_ATTRIBUTE);
	}

	@Override
	public List<IParserProcess> getAllParserApplications() throws CoreException {
		List<IParserProcess> applications = createAllExtensionInstances(_parserAppPluginContext);
		return applications;
	}
}
