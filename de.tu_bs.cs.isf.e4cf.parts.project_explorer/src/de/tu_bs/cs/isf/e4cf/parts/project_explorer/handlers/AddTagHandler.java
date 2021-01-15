package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.TagService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog.AddTagDialog;

public class AddTagHandler implements IHandler {

	@Inject
	private TagService tagService;

	@Execute
	@Override
	public void execute(IEclipseContext context, ServiceContainer services, Shell shell) {
		List<FileTreeElement> selection = services.rcpSelectionService.getCurrentSelectionsFromExplorer();
		AddTagDialog dialog = new AddTagDialog(context, tagService, selection);
		dialog.open();
	}

	@Override
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selection = selectionService.getCurrentSelectionsFromExplorer();
		return selection != null && !selection.isEmpty();
	}

}
