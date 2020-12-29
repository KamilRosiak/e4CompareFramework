package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.Wizard;

/**
 * A wizard that handles drops from the system explorer to the project explorer,
 * if the drop contains a nested element (e.g. directory)
 */
public class DropWizard extends Wizard {

	private ImportDirectoryPage copyOptionsPage;
	private ChooseRecursiveCopyOptionPage recursiveCopyOptionPage;
	private IEclipseContext context;

	private DropElement dropElement;

	public DropWizard(IEclipseContext context, DropElement dropElement) {
		this.context = context;
		this.dropElement = dropElement;
	}

	@Override
	public void addPages() {
		this.copyOptionsPage = new ImportDirectoryPage("Import directory", context);
		this.recursiveCopyOptionPage = new ChooseRecursiveCopyOptionPage("Choose strategy", context);
		addPage(copyOptionsPage);
		addPage(recursiveCopyOptionPage);
	}

	@Override
	public boolean performFinish() {

		if (copyOptionsPage.copyWithoutContent()) {
			// perform copy of just the folder itself, with no content that is in it.
			copyRecursivly(0);
		} else if (!recursiveCopyOptionPage.copyRecursivly()) {
			// copy only first level children
			copyRecursivly(1);
		} else {
			// make full copy
			copyRecursivly(Integer.MAX_VALUE);
		}
		return true;
	}

	/**
	 * 
	 * @param depth indicates to which level the user want to copy the files. 0:
	 *              Create only folder without content 1: Copy only first level
	 *              children 2: Copy all children recursively
	 */
	private void copyRecursivly(int depth) {

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
