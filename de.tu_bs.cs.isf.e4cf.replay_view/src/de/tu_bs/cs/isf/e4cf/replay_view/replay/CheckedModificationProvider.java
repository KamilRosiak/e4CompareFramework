package de.tu_bs.cs.isf.e4cf.replay_view.replay;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import org.eclipse.jface.viewers.CheckboxTreeViewer;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class CheckedModificationProvider implements ModificationProvider {

ReplayView view;
	
	public CheckedModificationProvider(ReplayView view) {
		this.view = view;
	}
	
	/**
	 * Retrieves all the checked {@link Modification} objects from the tree.
	 * Expands the {@link FeatureModelModificationSet} objects.
	 * 
	 * @return List of checked modifications  
	 */
	@Override
	public Queue<Modification> getModifications() {
		CheckboxTreeViewer commandsTableViewer = view.getModificationSetTreeViewer();
		List<?> list = Arrays.asList(commandsTableViewer.getCheckedElements());
		List<Modification> mods = new ArrayList<>(list.size());
		
		for (Object obj : list) {
			if (obj instanceof Modification) {
				mods.add((Modification) obj);
			} if (obj instanceof FeatureModelModificationSet) {
				commandsTableViewer.setExpandedState(obj, true);
			}
		}
		
		return new ArrayDeque<>(mods);
	}

}
