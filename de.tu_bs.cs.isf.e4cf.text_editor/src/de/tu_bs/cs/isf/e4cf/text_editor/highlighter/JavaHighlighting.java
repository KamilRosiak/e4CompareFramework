package de.tu_bs.cs.isf.e4cf.text_editor.highlighter;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

/**
 * Provides the highlighting computation for java-files.
 * To achieve that, this class contains the regular expressions
 * of all language elements that should be highlighted. These 
 * regular expressions are then used to compile a Pattern.
 *
 * @author Erwin Wijaya, Lukas Cronauer
 *
 */
public class JavaHighlighting {
	
	/**
	 * All words that should be specially highlighted for this fileType
	 */
	private static final String[] KEYWORDS = new String[] {
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
    private static final String ANNOTATION_PATTERN = "@"+"([^\n\s]*)";
    private static final String INSTANCE_PATTERN = "(?<=\\s)\\w+(?=\\s\\=\\snew\\s\\w+\\(\\)\\;)|\\w+(?=\\.\\w+\\(\\)\\;)";
    
    
    /**
     * Compiling the pattern to be checked with Matcher later
     */
    private static final Pattern PATTERN = Pattern.compile(
               "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
            + "|(?<PAREN>" + PAREN_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
            + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
            + "|(?<ANNOTATION>" + ANNOTATION_PATTERN + ")"
            + "|(?<INSTANCE>" + INSTANCE_PATTERN + ")"
    );
    
    /**
     * A Method to check if the current user input is a match with either of our pattern or our keyword for given fileType
     * after successfully check and find which word should be highlighted, it will then call the css
     * 
     * @param text content on codeArea that will be checked
     * @return the collection of word that will be highlighted
	 * @author Erwin Wijaya, Lukas Cronauer (from richttextfx-demo)
     */
	public static StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while(matcher.find()) {
			String styleClass = 
					matcher.group("KEYWORD") != null ? "java-keyword" :
					matcher.group("PAREN") != null ? "java-paren" :
					matcher.group("BRACE") != null ? "java-brace" :
					matcher.group("BRACKET") != null ? "java-bracket" :
					matcher.group("SEMICOLON") != null ? "java-semicolon" :
					matcher.group("STRING") != null ? "java-string" :
					matcher.group("COMMENT") != null ? "java-comment" :
					matcher.group("ANNOTATION") != null ? "java-annotation" :
					matcher.group("INSTANCE") != null ? "java-instance" :
					null;	
		    spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
		    spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
		    lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}
}
