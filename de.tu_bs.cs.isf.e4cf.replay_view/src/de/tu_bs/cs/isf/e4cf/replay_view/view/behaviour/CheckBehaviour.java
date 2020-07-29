package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class CheckBehaviour implements SelectionListener {

	private ReplayView view;
	
	public CheckBehaviour(ReplayView page) {
		this.view = page;
	}
	
	
	@Override
	public void widgetSelected(SelectionEvent event) {
		ITreeSelection selection = view.getModificationSetTreeViewer().getStructuredSelection();
		for (Object obj : selection.toList()) {
			view.getModificationSetTreeViewer().setSubtreeChecked(obj, true);
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
