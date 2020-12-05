package de.tu_bs.cs.isf.e4cf.text_editor.view;

import javafx.scene.Node;
import javafx.scene.control.Tab;

/**
 * Extension of Tab class. This class adds a constructor containing the type of
 * a given file that is opened, as it is meant to hold different instances of a
 * text editor.
 * 
 * @author Cedric Kapalla, Soeren Christmann
 * 
 */
public class EditorTab extends Tab {
	private String fileEnding;

	public EditorTab(String text, String fileEnding, Node content) {
		setText(text);
		this.fileEnding = fileEnding;
		setContent(content);
	}

	public String getFileEnding() {
		return fileEnding;
	}
}