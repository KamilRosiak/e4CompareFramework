package de.tu_bs.cs.isf.e4cf.replay_view.replay.state;

import de.tu_bs.cs.isf.e4cf.replay_view.replay.state.ReplayerState.ReplayAction;

public class DebugReplayerStateMachine extends ReplayerStateMachine {

	public DebugReplayerStateMachine(ReplayerState state) {
		super(state);
	}
	
	@Override
	public synchronized boolean advanceState(ReplayAction action) {
		boolean result = super.advanceState(action);
		
		if (result) {
			System.out.println("[ReplayerState]: state advanced to \""+state+"\" due to action \""+action+"\".");
		} else {
			System.out.println("[ReplayerState]: state advanced to \""+state+"\" due to action \""+action+"\", prohibitted transition occured.");			
		}
		return result;
	}

	@Override
	public synchronized void revertState() {
		super.revertState();
		System.out.println("[ReplayerState]: state reverted to \""+state+"\".");
	}

	@Override
	public synchronized void resetState(ReplayerState state) {
		super.resetState(state);
		System.out.println("[ReplayerState]: state manually set to \""+state.toString()+"\".");
	}
}
