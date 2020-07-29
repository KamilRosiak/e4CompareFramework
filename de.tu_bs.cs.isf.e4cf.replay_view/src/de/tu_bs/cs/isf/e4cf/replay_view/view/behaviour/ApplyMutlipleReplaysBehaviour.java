package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import de.tu_bs.cs.isf.e4cf.replay_view.replay.ModificationReplayer;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class ApplyMutlipleReplaysBehaviour implements SelectionListener {
	private ReplayView view;
	
	public ApplyMutlipleReplaysBehaviour(ReplayView view) {
		this.view = view;
	}
	
	@Override
	public void widgetSelected(SelectionEvent event) {
		ModificationReplayer replayer = view.getController().getReplayer();
		replayer.replayCheckedCommands();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		this.widgetSelected(e);
	}

}
