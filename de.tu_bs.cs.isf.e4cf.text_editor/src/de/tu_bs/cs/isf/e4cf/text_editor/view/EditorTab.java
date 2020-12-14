package de.tu_bs.cs.isf.e4cf.text_editor.view;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import de.tu_bs.cs.isf.e4cf.text_editor.highlighter.SyntaxHighlighter;
import javafx.scene.Node;
import javafx.scene.control.Tab;

/**
 * Extension of Tab class. This class adds a constructor containing the type of
 * a given file that is opened, as it is meant to hold different instances of a
 * text editor. It also has the function of highlighting its syntax, depending
 * on file extension (For now we have implemented highlighting for java and xml)
 * 
 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
 * 
 */
public class EditorTab extends Tab {
	private String fileEnding;
	
	// Highlighter of this Tab's CodeArea
	private SyntaxHighlighter highlighter;

	public EditorTab(String text, String fileEnding, String content) {
		this.fileEnding = fileEnding;
		CodeArea codeArea = CodeAreaFactory.createCodeArea(content);
		setText(text);
		highlighter = new SyntaxHighlighter(fileEnding, codeArea);
		setContent(codeArea);
	}

	public String getFileEnding() {
		return fileEnding;
	}
}