package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.srcml_reader;

import java.util.Arrays;
import java.util.List;

public class ParserDictionary {
	
	public static List<String> LANGUAGE_DICTIONARY = Arrays.asList("class", "function", "constructor", "decl_stmt",
			"expr_stmt", "typedef", "if", "else", "for", "while", "do", "return", "macro");

	public static List<String> ATTRIBUTE_DICTIONARY = Arrays.asList("expr", "type", "name");

	public static List<String> ATTRIBUTE_KEY_DICTIONARY = Arrays.asList("name", "operator", "literal");

}
