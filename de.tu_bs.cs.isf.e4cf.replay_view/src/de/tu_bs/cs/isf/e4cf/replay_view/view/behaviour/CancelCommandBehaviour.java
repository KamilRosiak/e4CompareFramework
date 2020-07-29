package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.replay_view.replay.ModificationReplayer;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class CancelCommandBehaviour implements SelectionListener {

	private ReplayView view;

	public CancelCommandBehaviour(ReplayView view) {
		this.view = view;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		ModificationReplayer replayer = view.getController().getReplayer();
		if (replayer.cancelReplay()) {
			view.getPauseResumeCommandButton().setText(ReplayView.PAUSE_BUTTON_NAME);
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
