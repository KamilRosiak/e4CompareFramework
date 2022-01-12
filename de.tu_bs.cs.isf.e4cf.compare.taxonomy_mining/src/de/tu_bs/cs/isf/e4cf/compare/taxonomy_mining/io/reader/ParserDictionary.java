package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.io.reader;

import java.util.Arrays;
import java.util.List;

public class ParserDictionary {

	public static List<String> CPP_NODE_TYPES = Arrays.asList("block", "break", "case", "continue", "default", "do",
			"empty_stmt", "expr_stmt", "for", "forever", "goto", "if_stmt", "label", "return", "switch", "while",
			"capture", "control", "else", "incr", "then", "extern", "function_decl", "function", "namespace", "lambda",
			"typedef", "using", "decl_stmt", "class_decl", "constructor_decl", "destructor", "destructor_decl",
			"enum_decl", "enum", "struct_decl", "union_decl", "template", "catch", "try", "throw", "ifdef", "macro",
			"region", "endregion", "ifndef", "include", "define", "unit", "comment");

	public static List<String> JAVA_NODE_TYPES = Arrays.asList("block", "break", "assert", "case", "continue", "do",
			"expr_stmt", "for", "if_stmt", "return", "switch", "while", "control", "then", "decl_stmt", "import",
			"package", "synchronized", "finally", "throw", "throws", "try", "expr_stmt", "class", "constructor", "do",
			"enum", "function", "interface_decl", "interface", "comment", "annotation", "implements");

	public static List<String> CSHARP_NODE_TYPES = Arrays.asList("block", "break", "case", "continue", "do",
			"expr_stmt", "for", "if_stmt", "return", "switch", "while", "then", "decl_stmt", "finally",
			"throw", "try", "expr_stmt", "class", "constructor", "do", "enum", "function", "interface", "comment",
			"attribute", "by", "checked", "constraint", "delegate", "destructor", "event", "equals", "finally",
			"foreach", "from", "function_decl", "in", "linq", "into", "join", "let", "lock", "modifier", "namespace",
			"on", "orderby", "select", "sizeof", "struct", "typeof", "unchecked", "using", "unsafe", "where", "init");
	
	
	public static List<String> BASE_TAXONOMY_NODE_TYPES = Arrays.asList("unit", "class", "function", "constructor",
			"decl_stmt", "expr_stmt", "if", "else", "for", "while", "do", "return");

}
