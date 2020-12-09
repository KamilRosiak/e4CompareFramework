package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.templates.KeyValueWizard;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.templates.KeyValueWizard.NotFinishedException;

public class RenameHandler {

	private static final String FILE_NAME_LABEL = "New File Name";

	private KeyValueWizard _keyValueWizard;
	private WizardDialog _wizardDialog;

	@Execute
	public void execute(RCPSelectionService selectionService, RCPDialogService dialogService,
			RCPImageService imageService) {
		try {
			// setup wizard dialog
			ImageDescriptor pageImageDescriptor = imageService.getImageDescriptor(null, "icons/element24.png");
			_keyValueWizard = buildWizard(pageImageDescriptor, selectionService);
			_wizardDialog = dialogService.constructDialog("Rename File", new Point(400, 600), _keyValueWizard);
			_wizardDialog.open();
			String newFileName = getFileName(_keyValueWizard);

			FileTreeElement parentElement = selectionService.getCurrentSelectionFromExplorer();
			renameFile(parentElement, newFileName);
		} catch (NotFinishedException e) {
			postErrorMessage(e.getMessage());
		} catch (IOException e) {
			postErrorMessage(e.getMessage());
		} catch (NullPointerException e) {
			postErrorMessage(e.getMessage());
		}
	}

	private KeyValueWizard buildWizard(ImageDescriptor pageImageDescriptor, RCPSelectionService selectionService) {
		String wizardName = "Rename File";
		Map<String, String> keyValues = new HashMap<String, String>();

		String oldName = selectionService.getCurrentSelectionFromExplorer().getAbsolutePath();
		if (!selectionService.getCurrentSelectionFromExplorer().isDirectory()) {
			oldName = oldName.substring(oldName.lastIndexOf("\\") + 1);
		}

		keyValues.put(FILE_NAME_LABEL, oldName);
		KeyValueWizard pairWizard = new KeyValueWizard(wizardName, pageImageDescriptor, keyValues);
		return pairWizard;
	}

	private String getFileName(KeyValueWizard wizard) throws NotFinishedException {
		Map<String, String> values = wizard.getValues();
		String fileName = values.get(FILE_NAME_LABEL);
		if (fileName != null) {
			return fileName;
		} else {
			throw new NullPointerException("The file name could not be retrieved from wizard");
		}

	}

	private void renameFile(FileTreeElement parent, String newFileName) throws IOException {
		Path source = FileHandlingUtility.getPath(parent);
		Path target = source.getParent().resolve(newFileName);
		Files.move(source, target);
	}

	private void postErrorMessage(String errorMessage) {
		RCPMessageProvider.errorMessage("Rename File", errorMessage);
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selection = selectionService.getCurrentSelectionsFromExplorer();
		return selection != null && selection.size() == 1;
	}
}
