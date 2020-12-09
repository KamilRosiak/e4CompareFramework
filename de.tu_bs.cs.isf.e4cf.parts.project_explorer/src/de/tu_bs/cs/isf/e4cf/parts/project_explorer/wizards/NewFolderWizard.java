package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jface.wizard.Wizard;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class NewFolderWizard extends Wizard {

	private Path _parentFolder;

	private NewFolderPage _page;

	public NewFolderWizard(Path parentPath, NewFolderPage page) {
		_parentFolder = parentPath;
		_page = page;
	}

	@Override
	public boolean canFinish() {
		return _page.isFolderNameSpecified();
	}

	@Override
	public boolean performFinish() {
		Path target = _parentFolder.resolve(_page.getFolderName());
		try {
			if (!Files.exists(target)) {
				Files.createDirectory(target);
			} else {
				RCPMessageProvider.informationMessage("New Folder", "Folder with the specified name already exists");
				return false;
			}
		} catch (IOException e) {
			RCPMessageProvider.errorMessage("New Folder", e.getMessage());
			return false;
		}

		return true;
	}

}
