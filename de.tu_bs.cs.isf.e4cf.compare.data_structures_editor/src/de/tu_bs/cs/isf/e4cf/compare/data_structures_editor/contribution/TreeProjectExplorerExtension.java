package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.contribution;

import java.util.List;
import org.eclipse.swt.graphics.Image;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.handler.OpenInTreeViewHandler;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class TreeProjectExplorerExtension implements IProjectExplorerExtension {

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(DSEditorST.BUNDLE_NAME, "icons/tree_view_icon.png");
	}

	@Override
	public void execute(ServiceContainer container) {
		List<FileTreeElement> selectedFileElements = container.rcpSelectionService.getCurrentSelectionsFromExplorer();
		if (selectedFileElements.size() == 1) {
			
			// check the validity of the file
			FileTreeElement selectedFileElement = selectedFileElements.get(0);
			if (!selectedFileElement.exists() || selectedFileElement.isDirectory()) {
				return;
			}
			
			new OpenInTreeViewHandler().execute(container, new ReaderManager());
		} else {
			RCPMessageProvider.errorMessage("DS Editor", "The double-click listener should only activate for a single file");
		}
		
	}

}
