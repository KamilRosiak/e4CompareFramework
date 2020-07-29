package de.tu_bs.cs.isf.e4cf.replay_view.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.graphics.Image;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewStringTable;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class AddModificationSetDoubleClickHandler implements IProjectExplorerExtension {

	private static final String MOD_SET_EXTENSION = ReplayViewStringTable.MOD_SET_MODEL_EXT;

	public AddModificationSetDoubleClickHandler() {
		
	}

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(ReplayViewStringTable.BUNDLE_NAME, "icons/replay_32.png");
	}

	@Override
	public void execute(ServiceContainer container) {
		// aggregate modification sets by loading the selected file tree elements as an EObject
		List<FileTreeElement> fileElements = container.rcpSelectionService.getCurrentSelectionsFromExplorer();
		List<FeatureModelModificationSet> modSets = fileElements.stream()
			.filter(fileElement -> !fileElement.isDirectory() && fileElement.getExtension().equals(MOD_SET_EXTENSION))
			.map(fileElement -> (FeatureModelModificationSet) EMFModelLoader.load(fileElement.getAbsolutePath(), MOD_SET_EXTENSION))
			.collect(Collectors.toList());
		
		// send modification sets to replay view
		container.partService.showPart(ReplayViewStringTable.PART_NAME);
		container.eventBroker.send(ReplayViewStringTable.EVENT_ADD_MOD_SETS, modSets);
	}

}
