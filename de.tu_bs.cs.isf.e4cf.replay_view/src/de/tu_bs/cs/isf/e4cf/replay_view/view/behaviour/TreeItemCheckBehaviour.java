package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;

import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class TreeItemCheckBehaviour implements ICheckStateListener {

	public ReplayView view;
	
	public TreeItemCheckBehaviour(ReplayView view) {
		this.view = view;
	}

	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		view.getModificationSetTreeViewer().setSubtreeChecked(event.getElement(), event.getChecked());
	}

}
