package de.tu_bs.cs.isf.e4cf.text_editor.view;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import de.tu_bs.cs.isf.e4cf.text_editor.highlighter.SyntaxHighlighter;
import de.tu_bs.cs.isf.e4cf.text_editor.indentation.JavaIndentation;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Extension of Tab class. This class adds a constructor containing the type of
 * a given file that is opened, as it is meant to hold different instances of a
 * text editor. It also has the function of highlighting its syntax, depending
 * on file extension (For now we have implemented highlighting for java and
 * xml).
 * 
 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
 * 
 */
public class EditorTab extends Tab {
	private String fileEnding;

	// Highlighter of this Tab's CodeArea
	private SyntaxHighlighter highlighter;

	/**
	 * Constructs a new Editor Tab.
	 * 
	 * @param text       The title of the Tab
	 * @param fileEnding The fileending of the file within the codearea
	 * @param content    The text withing the Codearea
	 */
	public EditorTab(String text, String fileEnding, String content) {
		this.fileEnding = fileEnding;
		CodeArea codeArea = CodeAreaFactory.createCodeArea(content);
		setIndentation(codeArea, fileEnding);
		setText(text);
		highlighter = new SyntaxHighlighter(fileEnding, codeArea);
		setContent(codeArea);
	}

	/**
	 * Activates the Indentation for the java-files.
	 * 
	 * @param codeArea   The codearea which should be indent.
	 * @param fileEnding The fileending which identifies the file as a java-file
	 */
	private void setIndentation(CodeArea codeArea, String fileEnding) {
		if (fileEnding.endsWith("java")) {
			new JavaIndentation(codeArea);
		}
	}

	/**
	 * Retrieves the file ending.
	 * 
	 * @return the current file ending
	 */
	public String getFileEnding() {
		return fileEnding;
	}
}