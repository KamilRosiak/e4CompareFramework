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

/**
 * Contains the setup and functions to find specific terms in text.
 * 
 * @author Cedric Kapalla, Soeren Christmann
 */
public class FindOperation implements Initializable {
	@FXML
	public TextEditor textEditor;
	@FXML
	private Button findButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField searchField;

	private Scene scene;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Function dedicated to opening the Find-window.
	 * 
	 * @author
	 */
	public void open() {
		// currently non-functional
	}

	/**
	 * Activated by pressing the Find-button. Selects a single match and deviates
	 * between them.
	 * 
	 * @author Cedric Kapalla, Soeren Christmann
	 * 
	 */
	public void initDoFindAction() {
		ArrayList<Integer> findings = new ArrayList<Integer>();
		String input = textEditor.getCurrentText();
		String lookingFor = searchField.getText();
		Pattern pattern = Pattern.compile("\\b" + lookingFor + "\\b");
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			findings.add(matcher.start());
		}
		int start = findings.get(1);
		textEditor.getCurrentCodeArea().selectRange(start, start + lookingFor.length());
	}//WIP
	
	/**
	 * 
	 * 
	 * @author 
	 */
	public void initCancelAction() {
		// beendet Suche
	}
}
