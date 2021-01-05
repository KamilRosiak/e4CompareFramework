package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

/**
 * A wizard that lets the user create a new file.
 *
 */
public class NewFileWizard extends Wizard {

	private IEclipseContext context;
	private NewFilePage newFilePage;
	private Path targetPath;

	public NewFileWizard(IEclipseContext context, Path targetPath) {
		this.context = context;
		this.targetPath = targetPath;
	}

	@Override
	public void addPages() {
		this.newFilePage = new NewFilePage("New File", targetPath, context);
		addPage(newFilePage);
	}

	@Override
	public boolean canFinish() {
		return newFilePage.isFilenameValid();
	}

	@Override
	public boolean performFinish() {

		Path target = targetPath.resolve(newFilePage.getFilename());
		if (Files.exists(target)) {
			newFilePage.setFileAlreadyExistsHint(true);
			return false;
		} else {
			try {
				Files.createFile(target);
			} catch (FileAlreadyExistsException alreadyException) {
				alreadyException.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

}
