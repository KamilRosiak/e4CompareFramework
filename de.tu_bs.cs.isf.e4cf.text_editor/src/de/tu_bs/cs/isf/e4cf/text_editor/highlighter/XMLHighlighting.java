package de.tu_bs.cs.isf.e4cf.text_editor.highlighter;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

/**
 * Provides the highlighting computation for xml-files. To achieve that, this
 * class contains the regular expressions of all language elements that should
 * be highlighted. These regular expressions are then used to compile a Pattern.
 *
 * @author Erwin Wijaya, Lukas Cronauer
 */
public class XMLHighlighting {

	/**
	 * Regular expressions for this file type
	 */
	private static final String ELEMENT_PATTERN = "(</?\\h*)(\\w+)([^<>]*)(\\h*/?>)";
	private static final String COMMENT_PATTERN = "<!--[^<>]+-->";
	private static final String ATTRIBUTE_PATTERN = "(\\w+\\h*)(=)(\\h*\"[^\"]+\")";

	// indices of regex group used to get the specified element
	private static final int GROUP_OPEN_BRACKET = 2;
	private static final int GROUP_ELEMENT_NAME = 3;
	private static final int GROUP_ATTRIBUTES_SECTION = 4;
	private static final int GROUP_CLOSE_BRACKET = 5;
	private static final int GROUP_ATTRIBUTE_NAME = 1;
	private static final int GROUP_EQUAL_SYMBOL = 2;
	private static final int GROUP_ATTRIBUTE_VALUE = 3;

	/**
	 * Compiling the pattern to be checked with Matcher later
	 */
	private static final Pattern XML_TAG = Pattern
			.compile("(?<ELEMENT>" + ELEMENT_PATTERN + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")");
	private static final Pattern ATTRIBUTES = Pattern.compile(ATTRIBUTE_PATTERN);

	/**
	 * Checks if the current user input is a match with either our pattern or our
	 * keyword for given a fileType after successfully checking and finding which
	 * word should be highlighted, the coresponding css-class will be applied to the
	 * specified region.
	 * 
	 * @param text content on codeArea that will be checked
	 * @return the collection of word that will be highlighted
	 *
	 * @author Lukas Cronauer (from richtextfx-demo)
	 */
	public static StyleSpans<Collection<String>> computeHighlighting(String text) {

		Matcher matcher = XML_TAG.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {

			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			if (matcher.group("COMMENT") != null) {
				spansBuilder.add(Collections.singleton("xml-comment"), matcher.end() - matcher.start());
			} else {
				if (matcher.group("ELEMENT") != null) {
					String attributesText = matcher.group(GROUP_ATTRIBUTES_SECTION);

					spansBuilder.add(Collections.singleton("xml-tagmark"),
							matcher.end(GROUP_OPEN_BRACKET) - matcher.start(GROUP_OPEN_BRACKET));
					spansBuilder.add(Collections.singleton("xml-anytag"),
							matcher.end(GROUP_ELEMENT_NAME) - matcher.end(GROUP_OPEN_BRACKET));

					if (!attributesText.isEmpty()) {

						lastKwEnd = 0;

						Matcher amatcher = ATTRIBUTES.matcher(attributesText);
						while (amatcher.find()) {
							spansBuilder.add(Collections.emptyList(), amatcher.start() - lastKwEnd);
							spansBuilder.add(Collections.singleton("xml-attribute"),
									amatcher.end(GROUP_ATTRIBUTE_NAME) - amatcher.start(GROUP_ATTRIBUTE_NAME));
							spansBuilder.add(Collections.singleton("xml-tagmark"),
									amatcher.end(GROUP_EQUAL_SYMBOL) - amatcher.end(GROUP_ATTRIBUTE_NAME));
							spansBuilder.add(Collections.singleton("xml-avalue"),
									amatcher.end(GROUP_ATTRIBUTE_VALUE) - amatcher.end(GROUP_EQUAL_SYMBOL));
							lastKwEnd = amatcher.end();
						}
						if (attributesText.length() > lastKwEnd)
							spansBuilder.add(Collections.emptyList(), attributesText.length() - lastKwEnd);
					}

					lastKwEnd = matcher.end(GROUP_ATTRIBUTES_SECTION);

					spansBuilder.add(Collections.singleton("xml-tagmark"),
							matcher.end(GROUP_CLOSE_BRACKET) - lastKwEnd);
				}
			}
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		try {
			return spansBuilder.create();
		} catch (IllegalStateException e) {
			// add style span with no effect to entire length of text when no spans were added
			spansBuilder.add(Collections.singleton("no-style"), text.length());
			return spansBuilder.create();
		}
	}
}
