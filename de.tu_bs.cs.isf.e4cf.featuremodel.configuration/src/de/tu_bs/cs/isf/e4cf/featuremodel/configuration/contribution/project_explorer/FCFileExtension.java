package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.contribution.project_explorer;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.stringtable.FeatureModelConfigurationEvents;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.stringtable.FeatureModelConfigurationFileTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.stringtable.FeatureModelConfigurationStrings;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDFileTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class FCFileExtension implements IProjectExplorerExtension {

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(FeatureModelConfigurationStrings.BUNDLE_NAME, FeatureModelConfigurationFileTable.FC_ICON);
	}

	@Override
	public void execute(ServiceContainer container) {
		container.partService.showPart(FeatureModelConfigurationStrings.BUNDLE_NAME);
		FileTreeElement target = container.rcpSelectionService.getCurrentSelectionFromExplorer();
		container.eventBroker.send(FeatureModelConfigurationEvents.EVENT_LOAD_CONFIGURATION_FROM_FILE, target.getAbsolutePath());
	}

}
