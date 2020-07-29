package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.view;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.stringtable.FeatureModelConfigurationEvents;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.stringtable.FeatureModelConfigurationStrings;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class FeatureConfigurationExplorerExtension implements IProjectExplorerExtension {

	public FeatureConfigurationExplorerExtension() {
	}

	@Override
	public Image getIcon(RCPImageService imageService) {
		return null;
	}

	@Override
	public void execute(ServiceContainer container) {
		FileTreeElement selectedFileElement = container.rcpSelectionService.getCurrentSelectionFromExplorer();
		
		// check the validity of the file
		if (!selectedFileElement.exists() || selectedFileElement.isDirectory()) {
			return;
		}
		
		// send load event
		container.partService.showPart(FeatureModelConfigurationStrings.PART_NAME);
		container.eventBroker.send(FeatureModelConfigurationEvents.EVENT_LOAD_CONFIGURATION_FROM_FILE, selectedFileElement.getAbsolutePath());
	}

}
