package de.tu_bs.cs.isf.e4cf.replay_view.replay;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.eclipse.jface.viewers.TreeViewer;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class SelectedModificationProvider implements ModificationProvider {

	ReplayView view;
	
	public SelectedModificationProvider(ReplayView view) {
		this.view = view;
	}
	
	/**
	 * Retrieves all selected {@link FeatureModelModificationSet} objects from the tree.
	 * Selected {@link Modification} objects are not considered in the selection, as
	 * they might not create coherent replay.
	 * 
	 * @return
	 */
	@Override
	public Queue<Modification> getModifications() {
		TreeViewer tv = view.getModificationSetTreeViewer();
		List<?> selection = tv.getStructuredSelection().toList();
		List<Modification> mods = new ArrayList<>(selection.size());
		
		for (Object obj : selection) {
			if (obj instanceof FeatureModelModificationSet) {
				FeatureModelModificationSet modSet = (FeatureModelModificationSet) obj;
				mods.addAll(modSet.getModifications());
			}
		}
		
		return new ArrayDeque<>(mods);
	}
}
