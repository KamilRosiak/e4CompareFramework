package de.tu_bs.cs.isf.e4cf.parser.plugin;

public abstract class ParserPluginStrings {
	
	public static final String PARSER_EXTENSION_POINT = 
			"de.tu_bs.cs.isf.e4cf.parser.IParserInterface";
	public static final String CALLBACK_EXTENSION_POINT = 
			"de.tu_bs.cs.isf.e4cf.parser.INodeCallbackInterface";
	public static final String PARSER_PROCESS_EXTENSION_POINT = 
			"de.tu_bs.cs.isf.e4cf.parser.IParserProcessInterface";
	
	public static final String INSTANCE_ATTRIBUTE = "class";
	public static final String ID_ATTRIBUTE = "id";
	public static final String LABEL_ATTRIBUTE = "label";
	public static final String DESCRIPTION_ATTRIBUTE = "description";
	public static final String IS_DEFAULT_ATTRIBUTE = "isDefault";
}
