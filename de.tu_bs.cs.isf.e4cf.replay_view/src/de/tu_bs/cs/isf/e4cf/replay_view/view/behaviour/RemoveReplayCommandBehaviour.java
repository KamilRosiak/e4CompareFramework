package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class RemoveReplayCommandBehaviour implements SelectionListener {

	private ReplayView view;
	
	public RemoveReplayCommandBehaviour(ReplayView page) {
		this.view = page;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// get tree viewer selection
		TreeViewer tv = view.getModificationSetTreeViewer();
		List<?> selectedObjects = tv.getStructuredSelection().toList();
		
		// remove all selected objects from the viewer model
		for (Object obj : selectedObjects) {
			if (obj instanceof Modification) {
				view.getController().removeModification((Modification) obj);
			} else if (obj instanceof FeatureModelModificationSet) {
				view.getController().removeModificationSet((FeatureModelModificationSet) obj);
			}
		}
		
		tv.refresh();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
