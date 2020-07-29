package de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.contribution;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.stringtable.FaMoFileTable;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.stringtable.FaMoStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;
import familyModel.FamilyModel;

public class FamFileExtension implements IProjectExplorerExtension {

	@Override
	public Image getIcon(RCPImageService imageService) {
		return imageService.getImage(FaMoStringTable.BUNDLE_NAME, FaMoFileTable.FV_ICON_16);
	}

	@Override
	public void execute(ServiceContainer container) {
		FileTreeElement selectedElement = container.rcpSelectionService.getCurrentSelectionFromExplorer();
		container.partService.showPart(FaMoStringTable.BUNDLE_NAME);
		FamilyModel faMo = (FamilyModel)EMFModelLoader.load(selectedElement.getAbsolutePath(), FaMoStringTable.FILE_ENDING_FAMO);
		container.eventBroker.send(E4CompareEventTable.SHOW_FAMILY_MODEL_EVENT, faMo);
	}	
}
