package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FindOperation implements Initializable {
	@FXML
	public TextEditor textEditorViewController;
	@FXML
	private Button findButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField searchField;

	public void initDoFindAction() {
		ArrayList<Integer> findings = new ArrayList<Integer>();
		String input = textEditorViewController.getCurrentText();
		String lookingFor = "hallo";
		Pattern pattern = Pattern.compile("\\b" + lookingFor + "\\b");
		Matcher matcher = pattern.matcher(input); // Where input is a TextInput class
		while (matcher.find()) {
			findings.add(matcher.start());
		}
		int start = findings.get(1);
		textEditorViewController.getCurrentCodeArea().selectRange(start, start+lookingFor.length());
	}

	public void initCancelAction() {
		//beendet Suche
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createNewFindWindow();
	}

	private void createNewFindWindow() {

	}

}
