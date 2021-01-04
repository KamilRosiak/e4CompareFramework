package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;

/**
 * A wizard that handles drops from the system explorer to the project explorer,
 * if the drop contains a nested element (e.g. directory)
 */
public class DropWizard extends Wizard {

	private ImportDirectoryPage copyOptionsPage;
	private DropElement dropElement;

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

		for (Path directoryPath : dropElement.getSources()) {
			try {
				Files.walk(directoryPath, depth).forEach(sourcePath -> {
					Path target = (dropElement.getTarget().resolve(directoryPath.getFileName()))
							.resolve(directoryPath.relativize(sourcePath));
					try {
						Files.copy(sourcePath, target);
					} catch (IOException e) {
						e.printStackTrace();
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

	/**
	 * An element that contains the target directory path and the source paths from
	 * a drag and drop operation. A DropElement can contain multiple sources.
	 */
	public static class DropElement {
		private Path[] sources;
		private Path target;

		/**
		 * Creates an drop element for a drag-and-drop operation.
		 * 
		 * @param target  the path from the target directory. This represents the 'root'
		 *                directory relative to the sources.
		 * @param sources an array of source paths, that will be copied to the target
		 *                directory.
		 */
		public DropElement(Path target, Path... sources) {
			this.sources = sources;
			this.target = target;
		}

		public Path[] getSources() {
			return sources;
		}

		public Path getTarget() {
			return target;
		}
	}
}
