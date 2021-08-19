package de.tu_bs.cs.isf.e4cf.text_editor.interfaces;

import java.util.Collection;

import org.fxmisc.richtext.model.StyleSpans;

/**
 * Interface for custom file highlighting
 * 
 * @author Lukas Cronauer
 *
 */
public interface IHighlighting {
	
	/**
	 * Builds an StyleSpans object with highlighted segments in it
	 * 
	 * @param text Content of the text field to be highlighted
	 * @return StyleSpans object containing segments with a style applied to them
	 */
	StyleSpans<Collection<String>> computeHighlighting(String text);

}
