package de.tu_bs.cs.isf.e4cf.extractive_mple.extensions.workspace;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaReader;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewFiles;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class TreeExtension implements IProjectExplorerExtension {

	public TreeExtension() {
	}

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(FamilyModelViewStrings.BUNDLE_NAME, FamilyModelViewFiles.FV_ROOT_16);
	}

	@Override
	public void execute(ServiceContainer container) {
		try {
			List<FileTreeElement> selectedFileElements = container.rcpSelectionService
					.getCurrentSelectionsFromExplorer();
			if (selectedFileElements.size() == 1) {
				// check the validity of the file
				FileTreeElement selectedFileElement = selectedFileElements.get(0);
				if (!selectedFileElement.exists() || selectedFileElement.isDirectory()) {
					return;
				}

				// send load event
				container.partService.showPart(MPLEEditorConsts.TREE_VIEW_ID);
				JavaReader reader = new JavaReader();
				container.eventBroker.send(MPLEEditorConsts.SHOW_TREE, reader.readArtifact(selectedFileElement));
			} else {
				RCPMessageProvider.errorMessage("Java File", "Could not load multi product line");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
