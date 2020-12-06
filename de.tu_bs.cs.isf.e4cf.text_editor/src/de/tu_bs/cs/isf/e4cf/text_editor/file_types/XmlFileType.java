package de.tu_bs.cs.isf.e4cf.text_editor.file_types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlFileType {
	
	/**
	 * All keywords that should be highlighted for this fileType
	 */
	public static final String[] KEYWORDS = new String[] {"xml", "encoding", "version"};
	
	/**
	 * Regular expressions for this file type
	 */
	private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	private static final String LESS_THAN_PATTERN = "<";
	private static final String MORE_THAN_PATTERN = ">";
	
	
    public static final Pattern PATTERN = Pattern.compile(
               "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
    		+ "|(?<LESSTHAN>" + LESS_THAN_PATTERN + ")"
            + "|(?<MORETHAN>" + MORE_THAN_PATTERN + ")"
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
		final String styleClass =
		        matcher.group("KEYWORD") != null ? "xml-keyword" :
		        matcher.group("LESSTHAN") != null ? "xml-less-than" :
		        matcher.group("MORETHAN") != null ? "xml-more-than" : 
		        null;
		return styleClass;
	}
}

