package de.tu_bs.cs.isf.e4cf.text_editor.highlighter;

import java.util.Collection;
import java.util.Collections;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

/**
 * Definition of highlighting function for file types that should not get
 * highlighted
 * 
 * @author Lukas Cronauer
 *
 */
public class NoHighlighting {
	/**
	 * Does not highlight the text
	 * 
	 * @param text String of text that the highlighting should be applied to
	 * @return An empty StyleSpans-object
	 */
	public static StyleSpans<Collection<String>> computeHighlighting(String text) {
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		// add span with no highlighting to entire text as an empty StyleSpansBuilder throws an IllegalStateException
		spansBuilder.add(Collections.singleton("no-style"), text.length());
		return spansBuilder.create();
	}
}
