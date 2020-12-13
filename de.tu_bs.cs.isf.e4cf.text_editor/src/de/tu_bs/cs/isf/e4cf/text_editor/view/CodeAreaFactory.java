package de.tu_bs.cs.isf.e4cf.text_editor.view;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

/**
 * Factory class for CodeArea with line numbers applied to it
 * 
 * @author Lukas Cronauer
 *
 */
public class CodeAreaFactory {

	/**
	 * Creates a new CodeArea and adds text to it, if any is given. Automatically
	 * applies line numbers to the codeArea
	 * 
	 * @param content contains any text that was parsed from an existing file, or an
	 *                empty String
	 * @return newly created CodeArea
	 * @author Lukas Cronauer
	 */
	public static CodeArea createCodeArea(String content) {
		CodeArea codeArea = new CodeArea(content);
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		return codeArea;
	}

}
