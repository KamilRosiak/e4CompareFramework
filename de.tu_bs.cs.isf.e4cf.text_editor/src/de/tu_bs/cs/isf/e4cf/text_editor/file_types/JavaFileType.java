package de.tu_bs.cs.isf.e4cf.text_editor.file_types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaFileType {
	
	/**
	 * All keywords that should be highlighted for this fileType
	 */
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
	
	/**
	 * Regular expressions for this file type
	 */
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    
    public static final Pattern PATTERN = Pattern.compile(
               "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );
	
    /**
     * Returns the corresponding css class for a pattern token from this
     * file-types' pattern. Css classes have to be defined in a separate css-file
     * in the ui/view/style folder. Additionally the css-file has to be imported
     * into "main.css"
     * 
     * @param matcher 
     * @return css class for this pattern token
     */
	public static String getStyleClass(Matcher matcher) {
		String styleClass =
			matcher.group("KEYWORD") != null ? "java-keyword" :
			matcher.group("PAREN") != null ? "java-paren" :
			matcher.group("BRACE") != null ? "java-brace" :
			matcher.group("BRACKET") != null ? "java-bracket" :
			matcher.group("SEMICOLON") != null ? "java-semicolon" :
			matcher.group("STRING") != null ? "java-string" :
			matcher.group("COMMENT") != null ? "java-comment" :
			null;
		assert styleClass != null;
		return styleClass;
	}
}
