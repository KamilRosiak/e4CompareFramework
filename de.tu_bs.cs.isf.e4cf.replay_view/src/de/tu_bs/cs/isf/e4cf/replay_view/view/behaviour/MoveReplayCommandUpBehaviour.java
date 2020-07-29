package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class MoveReplayCommandUpBehaviour implements SelectionListener {

	ReplayView view;
	
	public MoveReplayCommandUpBehaviour(ReplayView page) {
		this.view = page;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
//		TableUtil.moveSelectedItemsUp(view.getSelectedCommandsTable());
//		Arrays.asList(view.getSelectedCommandsTable().getColumns()).forEach(col -> col.pack());
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
