package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.persistence;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewEvents;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewFiles;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.stringtable.FamilyModelViewStrings;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class FamilyModelProjectExplorerExtension implements IProjectExplorerExtension {

	public FamilyModelProjectExplorerExtension() {
	}

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(FamilyModelViewStrings.BUNDLE_NAME, FamilyModelViewFiles.FV_ROOT_16);
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
			
			// send load event
			container.partService.showPart(FamilyModelViewStrings.PART_NAME);
			container.eventBroker.send(FamilyModelViewEvents.EVENT_LOAD_FAMILY_MODEL, selectedFileElement.getAbsolutePath());
		} else {
			RCPMessageProvider.errorMessage("Family Model View", "The double-click listener should only activate for a single file");
			// TODO: NOT POSSIBLE -> but for other handlers
			// TODO: load the family model and deduce the referenced resources from (the selected files OR the variant information)
		}
		
	}

}
