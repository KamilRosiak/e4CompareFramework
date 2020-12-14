package de.tu_bs.cs.isf.e4cf.text_editor.highlighter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

/**
 * Provides the highlighting computation for java-files. To achieve that, this
 * class contains the regular expressions of all language elements that should
 * be highlighted. These regular expressions are then used to compile a Pattern.
 *
 * @author Erwin Wijaya, Lukas Cronauer
 *
 */
public class JavaHighlighting {

	/**
	 * All words that should be specially highlighted for this fileType
	 */
	private static final String[] KEYWORDS = new String[] { "abstract", "assert", "boolean", "break", "byte", "case",
			"catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends",
			"final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "new", "package", "private", "protected", "public", "return", "short", "static",
			"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
			"volatile", "while" };

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
	private static final String ANNOTATION_PATTERN = "@" + "([^\n\s]*)";
	private static final String INSTANCE_PATTERN = "(?<=\\s)\\w+(?=\\s\\=\\snew\\s\\w+\\([A-Za-z1-9_,\\s]*\\)\\;)";
	private static final String INSTANCE_CALL_PATTERN = ".+(?=\\.\\w+\\([A-Za-z1-9_,\\s]*\\)\\;)";
	public static final String JAVA_CLASS_PATTERN = "(?<=\\s)\\w+(?=\\s\\w+\\s\\=\\snew)";
	public static final String JAVA_PARAMETER_PATTERN = "(?<=\\()[A-Za-z1-9_,\\s]+(?=\\);)";

	/**
	 * Compiling the pattern to be checked with Matcher later
	 */
	private static final Pattern PATTERN = Pattern.compile("(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PAREN>"
			+ PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN + ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
			+ "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")" + "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>"
			+ COMMENT_PATTERN + ")" + "|(?<ANNOTATION>" + ANNOTATION_PATTERN + ")" + "|(?<INSTANCE>" + INSTANCE_PATTERN
			+ ")" + "|(?<PARAMETER>" + JAVA_PARAMETER_PATTERN + ")");

	/**
	 * A Method to check if the current user input is a match with either of our
	 * pattern or our keyword for given fileType after successfully check and find
	 * which word should be highlighted, it will then call the css
	 * 
	 * @param text content on codeArea that will be checked
	 * @return the collection of word that will be highlighted
	 * @author Erwin Wijaya, Lukas Cronauer (from richttextfx-demo)
	 */
	public static StyleSpans<Collection<String>> computeHighlighting(String text) {
		List<HighlightBlock> highlights = new ArrayList<>();
		// computing the errorHighlight if instance haven't been initialized
		highlights.addAll(errorInstanceHighlighting(text));
		// computing the Highlighting if it has different class name on initialization
		highlights.addAll(errorClassHighlighting(text));
		Matcher matcher = PATTERN.matcher(text);
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher.group("KEYWORD") != null ? "java-keyword"
					: matcher.group("PAREN") != null ? "java-paren"
							: matcher.group("BRACE") != null ? "java-brace"
									: matcher.group("BRACKET") != null ? "java-bracket"
											: matcher.group("SEMICOLON") != null ? "java-semicolon"
													: matcher.group("STRING") != null ? "java-string"
															: matcher.group("COMMENT") != null ? "java-comment"
																	: matcher.group("ANNOTATION") != null
																			? "java-annotation"
																			: matcher.group("INSTANCE") != null
																					? "java-instance"
																					: matcher.group("PARAMETER") != null
																							? "java-param"
																							: null;
			HighlightBlock highlight = new HighlightBlock(styleClass, matcher.start(), matcher.end());
			highlights.add(highlight);
		}
		// sort the list based on startIndex, highlight will then store the data
		// sequentially
		highlights = highlights.stream().sorted(new Comparator<HighlightBlock>() {
			@Override
			public int compare(HighlightBlock s1, HighlightBlock s2) {
				return Integer.compare(s1.getStartIndex(), s2.getStartIndex());
			}
		}).collect(Collectors.toList());
		for (int i = 0; i < highlights.size(); i++) {
			HighlightBlock firstHighlight = highlights.get(i);
			spansBuilder.add(Collections.singleton(firstHighlight.getCss()), firstHighlight.getLength());

			if (i < highlights.size() - 1) {
				HighlightBlock secondHighlight = highlights.get(i + 1);
				spansBuilder.add(Collections.emptyList(),
						secondHighlight.getStartIndex() - firstHighlight.getEndIndex());
			}
		}
		try {
			return spansBuilder.create();
		} catch (IllegalStateException e) {
			// add style span with no effect to entire length of text when no spans were added
			spansBuilder.add(Collections.singleton("no-style"), text.length());
			return spansBuilder.create();
		}
	}

	/**
	 * Adding a method to check if the user want to call instance If the instance
	 * has not been initialized then it will mark it with java-error css otherwise
	 * we highlight the name of instance
	 * 
	 * @param text content on codeArea that will be checked
	 * @return adding it to List of highlight
	 * @author Erwin Wijaya
	 */
	private static List<HighlightBlock> errorInstanceHighlighting(String text) {
		List<HighlightBlock> highlights = new ArrayList<>();
		Matcher newMatcher = Pattern.compile(INSTANCE_PATTERN).matcher(text);
		List<String> instances = new ArrayList<>();
		while (newMatcher.find()) {
			String instanceNew = newMatcher.group();
			instances.add(instanceNew);
		}

		Matcher callMatcher = Pattern.compile(INSTANCE_CALL_PATTERN).matcher(text);
		while (callMatcher.find()) {
			String instanceCall = callMatcher.group().trim();
			if (!instanceCall.contains(".")) {
				if (!instances.contains(instanceCall)) {
					HighlightBlock highlight = new HighlightBlock("java-error", callMatcher.start(), callMatcher.end());
					highlights.add(highlight);
				} else {
					if (!instanceCall.startsWith(".")) {
						HighlightBlock highlight = new HighlightBlock("java-instance", callMatcher.start(),
								callMatcher.end());
						highlights.add(highlight);
					}
				}
			}
		}
		return highlights;
	}

	/**
	 * Adding a method to check if both class on the initialization part same or not
	 * if the class name is not the same then it will be highlighted otherwise it
	 * will return as no error
	 * 
	 * @param text content on codeArea that will be checked
	 * @return adding it to List of highlight
	 * @author Erwin Wijaya
	 */
	private static List<HighlightBlock> errorClassHighlighting(String text) {
		List<HighlightBlock> highlights = new ArrayList<>();
		Matcher matcher = Pattern.compile(JAVA_CLASS_PATTERN).matcher(text);
		while (matcher.find()) {
			// First class
			String firstClassName = matcher.group();
			int firstClassStartIndex = matcher.start();
			int firstClassEndIndex = matcher.end();

			String secondClassInLinePattern = "(?<=" + firstClassName + ")\\s\\w+\\s\\=\\snew\\s\\w+";
			Matcher secondClassInLineMatcher = Pattern.compile(secondClassInLinePattern).matcher(text);
			if (secondClassInLineMatcher.find()) {
				String secondClassInLine = secondClassInLineMatcher.group();

				// Second class
				int substringIndex = secondClassInLine.lastIndexOf(" ") + 1;
				String secondClassName = secondClassInLine.substring(substringIndex);
				int secondClassStartIndex = matcher.end() + substringIndex;
				int secondClassEndIndex = matcher.end() + secondClassInLine.length();

				if (!firstClassName.equals(secondClassName)) {
					highlights.add(new HighlightBlock("java-error", firstClassStartIndex, firstClassEndIndex));
					highlights.add(new HighlightBlock("java-error", secondClassStartIndex, secondClassEndIndex));
				}
			}
		}
		return highlights;
	}
}
