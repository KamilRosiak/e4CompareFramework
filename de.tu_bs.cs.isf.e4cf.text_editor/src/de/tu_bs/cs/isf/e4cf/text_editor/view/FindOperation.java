package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Contains the setup and functions to find specific terms in text.
 * 
 * @author Cedric Kapalla, Soeren Christmann
 */
public class FindOperation implements Initializable {
	public TextEditor textEditor;
	@FXML
	private Button findButton;
	@FXML
	private Button closeButton;
	@FXML
	private TextField searchField;

	private Scene scene;

	// term to be searched in the text
	private String findTerm = "";

	// list of starting points
	private ArrayList<Integer> findings = new ArrayList<Integer>();

	private int searchTextLength = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	/**
	 * Sets the scene for the find operation. 
	 * 
	 * @param scene The Scene of the Window
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Activated by pressing the Find-button. Checks the necessary Conditions for
	 * the search.Selects the next match and removes it from the list.
	 * 
	 * @author Cedric Kapalla, Soeren Christmann
	 * 
	 */
	public void searchSelectedWord() {
		String lookingFor = searchField.getText();

		if (lookingFor == "") {
			java.awt.Toolkit.getDefaultToolkit().beep();// Gibt Ton zurück
			return; // do nothing if nothing is in the search-field
		}
		if (findings.isEmpty()) {
			newFindOperation(lookingFor);
		}
		if (searchTextLength != textEditor.getCurrentText().length()) {
			newFindOperation(lookingFor);
		}
		if (!(findTerm.equals(lookingFor))) {
			newFindOperation(lookingFor);
		}
		int startPoint = findings.remove(0);
		textEditor.getCurrentCodeArea().selectRange(startPoint, startPoint + lookingFor.length());
	}

	/**
	 * Closes the popup window.
	 * 
	 * @author Soeren Christmann, Cedric Kapalla
	 */
	public void closeWindowAction() {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Sets an Editor for the finding operation.
	 * 
	 * @param editor is the main texteditor
	 * @author Soeren Christmann,Cedric Kapalla
	 */
	public void setTextEditor(TextEditor editor) {
		textEditor = editor;
	}

	/**
	 * Creates a arraylist of the startpositions of the searched words. If a new
	 * term is searched, the list is emptied.
	 * 
	 * @param lookingFor
	 * @author Soeren Christmann,Cedric Kapalla
	 */
	private void newFindOperation(String lookingFor) {
		findTerm = lookingFor; // set findTerm to be what is sought after now
		String input = textEditor.getCurrentText();
		findings.clear(); // empty the list if another term is searched
		searchTextLength = input.length();
		Pattern pattern = Pattern.compile("\\b" + lookingFor + "\\b");
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			// always adds starting value to the ArrayList
			findings.add(matcher.start());
		}
	}
}
