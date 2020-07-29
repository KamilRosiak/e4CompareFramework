package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.replay_view.replay.ModificationReplayer;
import de.tu_bs.cs.isf.e4cf.replay_view.replay.state.ReplayerState;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class PauseResumeCommandBehaviour implements SelectionListener {
	
	private ReplayView view;

	public PauseResumeCommandBehaviour(ReplayView view) {
		this.view = view;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		ModificationReplayer replayer = view.getController().getReplayer();
		if (replayer.isInState(ReplayerState.PAUSED)) {
			replayer.resumeReplay();
		} else {
			replayer.pauseReplay();
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
