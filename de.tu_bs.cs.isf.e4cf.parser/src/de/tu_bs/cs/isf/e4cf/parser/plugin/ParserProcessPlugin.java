package de.tu_bs.cs.isf.e4cf.parser.plugin;

import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.INSTANCE_ATTRIBUTE;
import static de.tu_bs.cs.isf.e4cf.parser.plugin.ParserPluginStrings.PARSER_PROCESS_EXTENSION_POINT;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import de.tu_bs.cs.isf.e4cf.core.plugin.ExtensionPointContext;
import de.tu_bs.cs.isf.e4cf.parser.base.IParserProcess;

public class ParserProcessPlugin {
	
	private static ExtensionPointContext parserExtPoint = new ExtensionPointContext(PARSER_PROCESS_EXTENSION_POINT);
	private static Set<String> parseableExtensions = null;
	
	public static Set<String> getParseableExtensions() {
		if (parseableExtensions != null) {
			return parseableExtensions;
		} else {
			List<IConfigurationElement> configs = parserExtPoint.getConfigurationElements();
			parseableExtensions = new HashSet<>();
			for (IConfigurationElement config : configs) {
				try {
					IParserProcess parserProcess = (IParserProcess) config.createExecutableExtension(INSTANCE_ATTRIBUTE);
					parseableExtensions.add(parserProcess.getType().getExtension());
				} catch (CoreException e) {
					System.err.println("Parser process class could not be instantiated by the tag \""+INSTANCE_ATTRIBUTE+"\": "+e.getMessage());
				}
			}
			return parseableExtensions;			
		}
	}
}
