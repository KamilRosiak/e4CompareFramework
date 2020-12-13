package de.tu_bs.cs.isf.e4cf.text_editor.highlighter;

import java.util.Collection;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

public class NoHighlighting {
	public static StyleSpans<Collection<String>> computeHighlighting(String text) {
		return new StyleSpansBuilder<Collection<String>>().create();
	}
}
