
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.new_folder_wizard.NewFolderWizard;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewFolderHandler {

	@Execute
//	public void execute (IEventBroker eventBroker, RCPSelectionService rcpSelectionService) {
	public void execute(RCPSelectionService rcpSelectionService) {
		Stage stage = new Stage();
		Path path = Paths.get(rcpSelectionService.getCurrentSelectionFromExplorer().getAbsolutePath());
		stage.setTitle("New Folder Wizard: " + path.getFileName());
//		Here the Icon has to be set
//		stage.getIcons().add(new Image(""));
		stage.setScene(new Scene(new NewFolderWizard(stage, rcpSelectionService), 300, 250));
		stage.show();
//		FileTreeElement element = rcpSelectionService.getCurrentSelectionFromExplorer();
//		String stringPath = element.getAbsolutePath();
//		File dir = new File(stringPath + "/" + FOLDER_PLACEHOLDER);
//		int i = 1;
//		while (dir.exists()) {
//			dir = new File(stringPath + "/" + FOLDER_PLACEHOLDER + i);
//			i++;
//		}
//		dir.mkdir();
//		eventBroker.send(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER, null);
//		Path nextTarget = dir.toPath();
//		List<FileTreeElement> children = element.getChildren();
//		for (FileTreeElement fileTreeElement: children) {
//			System.out.println(fileTreeElement.getAbsolutePath());
//		}
//		TreeItem<FileTreeElement> newValue = new TreeItem<FileTreeElement>(children.get(0));
//		StructuredSelection selection = new StructuredSelection(newValue);
//		_ss.setSelection(selection);
//		eventBroker.send(E4CEventTable.EVENT_RENAME_PROJECT_EXPLORER_ITEM, null);
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		FileTreeElement element = selectionService.getCurrentSelectionFromExplorer();
		return element == null || element.isDirectory();
	}
}