package de.tu_bs.cs.isf.e4cf.replay_view.view.behaviour;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;

import de.tu_bs.cs.isf.e4cf.replay_view.replay.ModificationReplayer;
import de.tu_bs.cs.isf.e4cf.replay_view.view.ReplayView;

public class ApplySingleReplayBehaviour implements IDoubleClickListener {
	private ReplayView view;
	
	public ApplySingleReplayBehaviour(ReplayView view) {
		this.view = view;
	}
	
	@Override
	public void doubleClick(DoubleClickEvent event) {
		ModificationReplayer replayer = view.getController().getReplayer();
		replayer.replaySelectedCommands();
	}

}
