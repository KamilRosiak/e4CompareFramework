package de.tu_bs.cs.isf.e4cf.text_editor.stringtable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XmlST {

	public static final String[] KEYWORDS = new String[] {"xml", "encoding", "version"};

	private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	private static final String LESS_THAN_PATTERN = "<";
	private static final String MORE_THAN_PATTERN = ">";
	
    public static final Pattern PATTERN = Pattern.compile(
            "(?<XMLKEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<XMLLESSTHAN>" + LESS_THAN_PATTERN + ")"
                    + "|(?<XMLMORETHAN>" + MORE_THAN_PATTERN + ")"
    );

    public static String getStyleClass(String text) {
		Matcher matcher = PATTERN.matcher(text);
		 final String styleClass =
		            matcher.group("XMLKEYWORD") != null ? "xml-keyword" :
		                    matcher.group("XMLLESSTHAN") != null ? "xml-less-than" :
		                            matcher.group("XMLMORETHAN") != null ? "xml-more-than" : null;
		return styleClass;
	}
}
