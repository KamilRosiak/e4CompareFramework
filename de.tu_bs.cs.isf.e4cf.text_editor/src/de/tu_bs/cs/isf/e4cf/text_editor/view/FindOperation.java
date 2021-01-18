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
	private Button cancelButton;
	@FXML
	private TextField searchField;

	private Scene scene;

	// term to be searched in the text
	private String findTerm = "";

	// list of starting points
	private ArrayList<Integer> findings = new ArrayList<Integer>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Activated by pressing the Find-button. Selects the next match and removes it
	 * from the list; if a new term is searched, the list is emptied.
	 * 
	 * @author Cedric Kapalla, Soeren Christmann
	 * 
	 */
	public void initDoFindAction() {
		String lookingFor = searchField.getText();

		if (lookingFor == "") {
			return; // do nothing if nothing is in the search-field
		}

		if (findTerm != lookingFor || findings.isEmpty()) {
			findTerm = lookingFor; // set findTerm to be what is sought after now
			String input = textEditor.getCurrentText();
			findings.clear(); //empty the list if another term is searched

			Pattern pattern = Pattern.compile("\\b" + lookingFor + "\\b");
			Matcher matcher = pattern.matcher(input);
			while (matcher.find()) {
				// always adds starting value to the ArrayList
				findings.add(matcher.start());
			}
		}

		int startPoint = findings.remove(0);
		textEditor.getCurrentCodeArea().selectRange(startPoint, startPoint + lookingFor.length());
	}

	/**
	 * Closes the popup window.
	 * 
	 * @author Soeren Christmann, Cedric Kapalla
	 */
	public void initCancelAction() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void setTextEditor(TextEditor editor) {
		textEditor = editor;
	}
}
