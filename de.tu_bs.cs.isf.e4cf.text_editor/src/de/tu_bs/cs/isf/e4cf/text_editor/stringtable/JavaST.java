package de.tu_bs.cs.isf.e4cf.text_editor.stringtable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaST {
	
	 public static final String[] KEYWORDS = new String[] {
	            "abstract", "assert", "boolean", "break", "byte",
	            "case", "catch", "char", "class", "const",
	            "continue", "default", "do", "double", "else",
	            "enum", "extends", "final", "finally", "float",
	            "for", "goto", "if", "implements", "import",
	            "instanceof", "int", "interface", "long", "native",
	            "new", "package", "private", "protected", "public",
	            "return", "short", "static", "strictfp", "super",
	            "switch", "synchronized", "this", "throw", "throws",
	            "transient", "try", "void", "volatile", "while"
	    };
	    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	    private static final String PAREN_PATTERN = "\\(|\\)";
	    private static final String BRACE_PATTERN = "\\{|\\}";
	    private static final String BRACKET_PATTERN = "\\[|\\]";
	    private static final String SEMICOLON_PATTERN = "\\;";
	    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	    public static final Pattern PATTERN = Pattern.compile(
	            "(?<JAVAKEYWORD>" + KEYWORD_PATTERN + ")"
	                    + "|(?<JAVAPAREN>" + PAREN_PATTERN + ")"
	                    + "|(?<JAVABRACE>" + BRACE_PATTERN + ")"
	                    + "|(?<JAVABRACKET>" + BRACKET_PATTERN + ")"
	                    + "|(?<JAVASEMICOLON>" + SEMICOLON_PATTERN + ")"
	                    + "|(?<JAVASTRING>" + STRING_PATTERN + ")"
	                    + "|(?<JAVACOMMENT>" + COMMENT_PATTERN + ")"
	    );

		public static String getStyleClass(String text) {
			Matcher matcher = PATTERN.matcher(text);
			String styleClass =
				matcher.group("JAVAKEYWORD") != null ? "java-keyword" :
				matcher.group("JAVAPAREN") != null ? "java-paren" :
				matcher.group("JAVABRACE") != null ? "java-brace" :
				matcher.group("JAVABRACKET") != null ? "java-bracket" :
				matcher.group("JAVASEMICOLON") != null ? "java-semicolon" :
				matcher.group("JAVASTRING") != null ? "java-string" :
				matcher.group("JAVACOMMENT") != null ? "java-comment" :
				null;
			assert styleClass != null;
			return styleClass;
		}
}
