package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class UncheckBehaviour implements SelectionListener {
	
	private ReplayView view;
	
	public UncheckBehaviour(ReplayView view) {
		this.view = view;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		ITreeSelection selection = view.getModificationSetTreeViewer().getStructuredSelection();
		for (Object obj : selection.toList()) {
			view.getModificationSetTreeViewer().setSubtreeChecked(obj, false);
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
