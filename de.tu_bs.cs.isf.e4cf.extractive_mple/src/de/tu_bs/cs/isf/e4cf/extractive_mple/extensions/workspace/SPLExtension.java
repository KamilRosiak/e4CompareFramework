package de.tu_bs.cs.isf.e4cf.extractive_mple.extensions.workspace;

import java.io.File;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewFiles;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class SPLExtension implements IProjectExplorerExtension {

	public SPLExtension() {
	}

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(MPLEEditorConsts.EMPLE_BUNDLE_NAME, FamilyModelViewFiles.FV_ROOT_16);
	}

	@Override
	public void execute(ServiceContainer container) {
		List<FileTreeElement> selectedFileElements = container.rcpSelectionService.getCurrentSelectionsFromExplorer();
		if (selectedFileElements.size() == 1) {
			// TODO: load the family model and deduce the other resources from the variant information
			
			// check the validity of the file
			FileTreeElement selectedFileElement = selectedFileElements.get(0);
			if (!selectedFileElement.exists() || selectedFileElement.isDirectory()) {
				return;
			}
			
			MPLPlatform platform = MPLEPlatformUtil.loadPlatform(new File(selectedFileElement.getAbsolutePath()));
			platform.location = selectedFileElement.getAbsolutePath();
			// send load event
			container.partService.showPart(MPLEEditorConsts.PLATFORM_VIEW);
			container.partService.showPart(MPLEEditorConsts.TREE_VIEW_ID);
			container.eventBroker.send(MPLEEditorConsts.SHOW_MPL, platform);
		} else {
			RCPMessageProvider.errorMessage("MPLE", "Could not load multi product line");
			// TODO: NOT POSSIBLE -> but for other handlers
			// TODO: load the family model and deduce the referenced resources from (the selected files OR the variant information)
		}
		
	}

}
