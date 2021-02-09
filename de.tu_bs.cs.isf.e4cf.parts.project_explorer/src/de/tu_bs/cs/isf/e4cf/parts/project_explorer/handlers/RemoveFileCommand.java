
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.TagService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IHandler;

public class RemoveFileCommand implements IHandler {
	private @Inject TagService tagService;

	private static final String DIALOG_MESSAGE = "The procedure will delete all the contents from the hard disk.\n"
			+ "Are you sure you want to delete the following files?";

	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, Shell shell) {
		// get current selection
		List<FileTreeElement> selection = services.rcpSelectionService.getCurrentSelectionsFromExplorer();
		String filePathList = generateFilePathList(selection);

		// display warning if user really wants to delete selected files (show files)
		boolean shouldDelete = RCPMessageProvider.questionMessage("Delete File(s)",
				DIALOG_MESSAGE + "\n\n" + filePathList);
		if (shouldDelete) {
			delete(selection);
		}
	}

	private String generateFilePathList(List<FileTreeElement> selection) {
		StringBuilder sb = new StringBuilder();
		for (FileTreeElement element : selection) {
			sb.append("- " + element.getRelativePath() + "\n");
		}
		String filePathList = sb.toString();
		return filePathList;
	}

	private void delete(List<FileTreeElement> selection) {
		for (FileTreeElement element : selection) {
			try {
				FileHandlingUtility.delete(FileHandlingUtility.getPath(element));
				tagService.clearTags(element);
			} catch (IOException e) {
				RCPMessageProvider.errorMessage("Delete File", "Some files could not be deleted");
				e.printStackTrace();
			}
		}
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selection = selectionService.getCurrentSelectionsFromExplorer();
		return selection != null && !selection.isEmpty();

	}
}
