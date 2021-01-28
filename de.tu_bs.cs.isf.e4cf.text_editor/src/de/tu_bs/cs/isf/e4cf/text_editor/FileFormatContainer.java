package de.tu_bs.cs.isf.e4cf.text_editor;

import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IFormatting;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IHighlighting;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IIndenting;

public class FileFormatContainer {
	private final IHighlighting highlighter;
	private final IIndenting indenter;
	private final IFormatting formatter;
	
	public FileFormatContainer(Object highlighter, Object indenter, Object formatter) {
		this.highlighter = (IHighlighting) highlighter;
		this.indenter = (IIndenting) indenter;
		this.formatter = (IFormatting) formatter;
		
	}

	public IFormatting getFormatter() {
		return formatter;
	}


	public IIndenting getIndenter() {
		return indenter;
	}

	public IHighlighting getHighlighter() {
		return highlighter;
	}

}
