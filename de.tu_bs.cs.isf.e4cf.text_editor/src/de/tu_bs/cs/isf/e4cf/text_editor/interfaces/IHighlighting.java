package de.tu_bs.cs.isf.e4cf.text_editor.interfaces;

import java.util.Collection;

import org.fxmisc.richtext.model.StyleSpans;

public interface IHighlighting {
	
	StyleSpans<Collection<String>> computeHighlighting(String text);

}
