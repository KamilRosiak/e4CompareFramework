package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import java.util.List;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class CheckAllBehaviour implements SelectionListener {

	private ReplayView view;
	
	public CheckAllBehaviour(ReplayView page) {
		this.view = page;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		List<FeatureModelModificationSet> modSets = view.getController().getModificationSets();
		CheckboxTreeViewer tv = view.getModificationSetTreeViewer();
		modSets.forEach(modSet -> tv.setSubtreeChecked(modSet, true));
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
