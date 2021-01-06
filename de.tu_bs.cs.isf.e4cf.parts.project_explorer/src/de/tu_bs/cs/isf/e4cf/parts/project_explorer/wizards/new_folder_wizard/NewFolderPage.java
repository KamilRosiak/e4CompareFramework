package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.new_folder_wizard;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.base_wizard.WizardPage;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Page to present a text input, a status label and an action button.
 *
 */
class NewFolderPage extends WizardPage {
	
	@Inject
	RCPSelectionService rcpSelectionService;
	
	private TextArea txtArea;
	private Label statusLabel;
	private Button createButton;
	
	private static final String FOLDER_NAME_REGEX = "[a-zA-Z0-9-_#]+";
//	private static final String EXTENDED_FOLDER_NAME_REGEX = "/(\\/?[a-zA-Z0-9-_#]+\\/{1})*[a-zA-Z0-9-_#]+";
	private Path target;

	public NewFolderPage(RCPSelectionService rcpSelectionService) {
		super("New Folder");
		this.rcpSelectionService = rcpSelectionService;
		FileTreeElement element = rcpSelectionService.getCurrentSelectionFromExplorer();
		String stringPath = element.getAbsolutePath();

		Stage stg = new Stage();
		stg.setTitle("Create new Folder in: " + stringPath);
		target = Paths.get(stringPath);

		nextButton.setDisable(true);

		createButton.setOnAction((event) -> {
			String folderName = txtArea.getText();
			if (element != null && element.isDirectory()) {
				if (folderName.matches(FOLDER_NAME_REGEX)) {
					File dir = new File(target.toAbsolutePath() + "/" + folderName);
					if (!dir.exists()) {
						dir.mkdir();
						statusLabel.setText("Folder (" + folderName + ") successfully created.");
						txtArea.setText("");
					} else {
						statusLabel.setText("Folder (" + folderName + ") already exists.");
					}
				} else {
					statusLabel.setText("Folder name (" + folderName + ") only allows numbers, characters, -, _, #.");
				}
			} else {
				statusLabel.setText("No valid target directory selection.");
			}
		});
	}

	@Override
	public Parent getContent() {
		txtArea = new TextArea();
		txtArea.setPrefWidth(260);
		txtArea.setPrefHeight(50);
		statusLabel = new Label();
		statusLabel.setMinWidth(260);
		createButton = createButton("Create a new Folder", 260);
		return new VBox(5, new Label("Propose a folder name:"), txtArea, statusLabel, createButton);
	}

}