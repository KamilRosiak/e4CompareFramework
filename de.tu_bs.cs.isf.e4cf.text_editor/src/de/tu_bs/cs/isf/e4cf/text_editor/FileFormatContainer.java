package de.tu_bs.cs.isf.e4cf.text_editor;

import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IFormatting;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IHighlighting;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IIndenting;

/**
 * Container that sets up indenting, formatting, and highlighting.
 * 
 * @author Lukas Cronauer
 *
 */
public class FileFormatContainer {
	private final IHighlighting highlighter;
	private final IIndenting indenter;
	private final IFormatting formatter;

	/**
	 * Constructor for the container element. Instances can vary by file type.
	 * 
	 * @param highlighter the given highlighter for this instance.
	 * @param indenter    the given indenter for this instance.
	 * @param formatter   the given formatter for this instance.
	 */
	public FileFormatContainer(Object highlighter, Object indenter, Object formatter) {
		this.highlighter = (IHighlighting) highlighter;
		this.indenter = (IIndenting) indenter;
		this.formatter = (IFormatting) formatter;

	}

	/**
	 * Provides this instance's formatter.
	 * 
	 * @return this instance's formatter
	 */
	public IFormatting getFormatter() {
		return formatter;
	}

	/**
	 * Provides this instance's indenter.
	 * 
	 * @return this instance's indenter
	 */
	public IIndenting getIndenter() {
		return indenter;
	}

	/**
	 * Provides this instance's highlighter
	 * 
	 * @return this instance's highlighter
	 */
	public IHighlighting getHighlighter() {
		return highlighter;
	}
}
