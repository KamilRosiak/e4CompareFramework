package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TreeItem;

import FeatureDiagramModificationSet.FeatureDiagramModificationSetFactory;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;
import de.tu_bs.cs.isf.e4cf.replay_view.util.ReplayViewStringTable;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;
import de.tu_bs.cs.isf.familymining.ppu_iec.rcp_e4.EMFModelLoader.impl.EMFModelLoader;

public class AddReplayCommandBehaviour implements SelectionListener {
	
	private static final String TARGET_EXTENSION = ReplayViewStringTable.MOD_SET_MODEL_EXT;
	
	private ReplayView view;
	
	public AddReplayCommandBehaviour(ReplayView page) {
		this.view = page;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		ServiceContainer serviceContainer = view.getController().getServiceContainer();
		RCPDialogService dialogService = serviceContainer.dialogService;
		List<String> selectedFiles = dialogService.getFilesFromFileSystem(serviceContainer.shell);
		List<FeatureModelModificationSet> modSets = selectedFiles.stream()
			.filter(selectedFile -> selectedFile.endsWith(TARGET_EXTENSION))
			.map(selectedFile -> retrieveModSet(selectedFile))
			.collect(Collectors.toList());
		view.getServiceContainer().eventBroker.send(ReplayViewStringTable.EVENT_ADD_MOD_SETS, modSets);
	}
	
	/**
	 * Load the {@link FeatureModelModificationSet} from the given file.
	 * 
	 * @param file
	 * @return
	 */
	protected FeatureModelModificationSet retrieveModSet(String file) {
		 EObject eobject = EMFModelLoader.load(file, TARGET_EXTENSION);
		 if (eobject != null && eobject instanceof FeatureModelModificationSet) {
			 return (FeatureModelModificationSet) eobject;
		 } else {
			 // issue a warning but return a processable instance
			System.out.println("[ReplayView]: The file \""+file+"\" is not a valid *."+TARGET_EXTENSION+" file.");
			return FeatureDiagramModificationSetFactory.eINSTANCE.createFeatureModelModificationSet();
		 } 
	}
	
	protected boolean isDuplicatedInList(Path selectedFile) {
		List<TreeItem> commandFileItems = Arrays.asList(view.getModificationSetTree().getItems());
		return commandFileItems.stream().anyMatch(commandFileItem -> {
				Modification mod = (Modification) commandFileItem.getData(ReplayViewStringTable.TABLE_ITEM_MODIFICATION_KEY);
				return false;
		});
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
