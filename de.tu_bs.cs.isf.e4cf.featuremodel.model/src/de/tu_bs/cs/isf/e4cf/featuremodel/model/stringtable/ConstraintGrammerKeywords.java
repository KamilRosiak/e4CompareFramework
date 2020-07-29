package de.tu_bs.cs.isf.e4cf.featuremodel.model.stringtable;

public class ConstraintGrammerKeywords {
	public static final String NOT_OP ="NOT";
	public static final String AND_OP ="AND";
	public static final String OR_OP ="OR";
	public static final String EQUALS_OP ="EQUALS";
	public static final String IMPL_OP ="IMPLIES";
	public static final String LEFT_BRACKET ="(";
	public static final String RIGHT_BRACKET =")";
	
	
	public static final String[] KEYWORDS = new String[] {
	          NOT_OP, AND_OP , OR_OP, EQUALS_OP, IMPL_OP, LEFT_BRACKET, RIGHT_BRACKET
	};
	

	public static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	
	public static final String PAREN_PATTERN = "\\(|\\)";
}
