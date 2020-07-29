 
package de.tu_bs.cs.isf.e4cf.replay_view;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewStringTable;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class AddModificationSetsMenuHandler {
	
	private static final String MOD_SET_EXTENSION = ReplayViewStringTable.MOD_SET_MODEL_EXT;
	
	@Execute
	public void execute(ServiceContainer serviceContainer) {
		// aggregate modification sets by loading the selected file tree elements as an EObject
		List<FileTreeElement> fileElements = serviceContainer.rcpSelectionService.getCurrentSelectionsFromExplorer();
		List<FeatureModelModificationSet> modSets = fileElements.stream()
				.filter(element -> !element.isDirectory() && element.getExtension().equals(ReplayViewStringTable.MOD_SET_MODEL_EXT))
				.map(element -> (FeatureModelModificationSet) EMFModelLoader.load(element.getAbsolutePath(), MOD_SET_EXTENSION))
				.collect(Collectors.toList());
		
		// add the modification sets to the replay view
		serviceContainer.partService.showPart(ReplayViewStringTable.PART_NAME);
		serviceContainer.eventBroker.send(ReplayViewStringTable.EVENT_ADD_MOD_SETS, modSets);		
	}
	
	
	@CanExecute
	public boolean canExecute(ServiceContainer serviceContainer) {
		List<FileTreeElement> fileElements = serviceContainer.rcpSelectionService.getCurrentSelectionsFromExplorer();
		return fileElements.stream()
				.filter(element -> !element.isDirectory())
				.anyMatch(element -> element.getExtension().equals(ReplayViewStringTable.MOD_SET_MODEL_EXT));
	}
		
}