package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.DropElement;

/**
 * A wizard that handles drops from the system explorer to the project explorer,
 * if the drop contains a nested element (e.g. directory)
 */
public class DropWizard extends Wizard {

	private ImportDirectoryPage copyOptionsPage;
	private DropElement dropElement;

	boolean didFileMove = true;

	public DropWizard(IEclipseContext context, DropElement dropElement, RCPImageService imgService) {
		this.dropElement = dropElement;
		this.copyOptionsPage = new ImportDirectoryPage("Import directory", dropElement.getTarget(), context,
				imgService.getImageDescriptor(null, "icons/Explorer_View/items/folder24.png"));

		addPage(copyOptionsPage);
	}

	@Override
	public boolean performFinish() {

		switch (copyOptionsPage.getCopyStrategy()) {
		case EMPTY:
			copyRecursively(0);
			break;
		case RECURSIVE:
			copyRecursively(Integer.MAX_VALUE);
			break;
		case SHALLOW:
			copyRecursively(1);
			break;
		default:
			copyRecursively(0);
			break;
		}
		return true;
	}

	/**
	 * 
	 * @param depth indicates to which level the user want to copy the files. 0:
	 *              Create only folder without content 1: Copy only first level
	 *              children 2: Copy all children recursively
	 */
	private void copyRecursively(int depth) {

		for (Path directory : dropElement.getSources()) {
			didFileMove = true;
			try {
				Files.walk(directory, depth).forEach(sourcePath -> {
					// if parent didn't move then children don't need to move either.
					if (!didFileMove) {
						return;
					}
					// construct new target path
					Path target = (dropElement.getTarget().resolve(directory.getFileName()))
							.resolve(directory.relativize(sourcePath));
					if (target.equals(sourcePath)) {
						// no need to copy
						didFileMove = false;
					} else {
						try {
							Files.copy(sourcePath, target);
						} catch (FileAlreadyExistsException fae) {
							if (didFileMove) {
								RCPMessageProvider.errorMessage("Directory already exsits.",
										"A Directory with the name " + directory.getFileName() + " exists.");
							}
							didFileMove = false;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean canFinish() {
		return true;
	}

	public enum DropMode {
		COPY, MOVE;
	}
}
