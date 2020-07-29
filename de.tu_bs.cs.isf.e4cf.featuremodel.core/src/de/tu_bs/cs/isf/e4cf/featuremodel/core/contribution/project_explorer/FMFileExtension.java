package de.tu_bs.cs.isf.e4cf.featuremodel.core.contribution.project_explorer;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDFileTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class FMFileExtension implements IProjectExplorerExtension {

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(FDStringTable.BUNDLE_NAME, FDFileTable.FM_ICON_16);
	}

	@Override
	public void execute(ServiceContainer container) {
		container.partService.showPart(FDStringTable.BUNDLE_NAME);
		FileTreeElement target = container.rcpSelectionService.getCurrentSelectionFromExplorer();
		container.eventBroker.send(FDEventTable.LOAD_FEATURE_DIAGRAM_FROM_FILE, target);
	}

}
