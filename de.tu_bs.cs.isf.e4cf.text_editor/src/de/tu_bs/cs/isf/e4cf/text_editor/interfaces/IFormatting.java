package de.tu_bs.cs.isf.e4cf.text_editor.interfaces;

import org.fxmisc.richtext.CodeArea;

/**
 * Interface for custom file formatting
 * 
 * @author Lukas Cronauer
 *
 */
public interface IFormatting {
	
	/**
	 * Formats the text in the codeArea
	 * 
	 * @param codeArea CodeArea object an tab of the file format
	 */
	void format(CodeArea codeArea);

}
