package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.DropElement;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.DropElement.DropMode;

/**
 * A wizard that handles drops from the system explorer to the project explorer,
 * if the drop contains a nested element (e.g. directory)
 */
public class DropWizard extends Wizard {

	private ImportDirectoryPage copyOptionsPage;
	private DropElement dropElement;

	public DropWizard(IEclipseContext context, DropElement dropElement, RCPImageService imgService) {
		this.dropElement = dropElement;
		this.copyOptionsPage = new ImportDirectoryPage("Import directory", dropElement.getDropMode(),
				dropElement.getTarget(), context,
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

		for (Path directoryPath : dropElement.getSources()) {
			try {
				Files.walk(directoryPath, depth).forEach(sourcePath -> {
					Path target = (dropElement.getTarget().resolve(directoryPath.getFileName()))
							.resolve(directoryPath.relativize(sourcePath));
					try {
						Files.copy(sourcePath, target);
					} catch (FileAlreadyExistsException alreadyExc) {

					} catch (IOException e) {
						e.printStackTrace();
					}
				});

				if (copyOptionsPage.getDropMode() == DropMode.MOVE) {
					Files.walk(directoryPath).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean canFinish() {
		return true;
	}
}
