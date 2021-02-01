package de.tu_bs.cs.isf.e4cf.text_editor.interfaces;

import org.fxmisc.richtext.CodeArea;

/**
 * Interface for custom file indentation
 * 
 * @author Lukas Cronauer
 *
 */
public interface IIndenting {
	
	/**
	 * Indents the content of the codeArea
	 * 
	 * @param codeArea CodeArea object of an tab of the file format
	 */
	void applyIndentation(CodeArea codeArea);

}
