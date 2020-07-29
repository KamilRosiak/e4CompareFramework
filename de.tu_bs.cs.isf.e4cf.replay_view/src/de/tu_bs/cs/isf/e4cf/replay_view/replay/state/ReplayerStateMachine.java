package de.tu_bs.cs.isf.e4cf.replay_view.replay.state;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.replay_view.replay.state.ReplayerState.ReplayAction;

public class ReplayerStateMachine {

	public static interface StateChangeListener {
		public void stateChanged(ReplayerState state);
	}
	
	protected ReplayerState prevState;
	protected ReplayerState state;

	protected List<StateChangeListener> stateListeners;
	
	public ReplayerStateMachine(ReplayerState state) {
		this.prevState = null;
		this.state = state;
		this.stateListeners = new ArrayList<>();
	}
	
	/**
	 * Advances the state machine to the next state according to the provided action.
	 * 
	 * @param action trigger for state change
	 * @return <tt>true</tt> if the state change does not result in an erroneous state
	 */
	synchronized public boolean advanceState(ReplayAction action) {
		prevState = state;
		state = state.next(action);
		stateListeners.forEach(listener -> listener.stateChanged(state));
		return state != ReplayerState.UNDEFINED || state != ReplayerState.ERROR;
	}
	
	public void addStateChangeListener(StateChangeListener listener) {
		if (!stateListeners.contains(listener)) {
			stateListeners.add(listener);
		}
	}
	
	public void removeStateChangedListener(StateChangeListener listener) {
		stateListeners.remove(listener);
	}
	
	public void removeAllListeners() {
		stateListeners.clear();
	}
	
	/**
	 * Compares the given state with the current state.
	 * 
	 * @param state
	 * @return <tt>true</tt> if the given state is equal to the current one.
	 */
	public boolean isInState(ReplayerState state) {
		return this.state == state;
	}
	
	/**
	 * Compares the given states with the current state.
	 * 
	 * @param state
	 * @return <tt>true</tt> if one of the given states matches the current one.
	 */
	public boolean isInAnyState(ReplayerState... states) {
		boolean result = false;
		for (int i = 0; i < states.length; i++) {
			ReplayerState state = states[i];
			result |= isInState(state);
		}
		return result;
		
	}
	
	/**
	 * Compares the given state with the previous active state.
	 * 
	 * @param state
	 * @return <tt>true</tt> if the given state is equal to the previous one.
	 */
	public boolean wasInState(ReplayerState state) {
		return this.prevState != null && prevState == state;
	}
	
	/**
	 * Reverts back to the previous state. This method can only be called once after a state transition.
	 * Subsequent calls will not change the state. If the active state is the initial one, this method has no effect.
	 */
	public void revertState() {
		if (prevState != null) {
			resetState(prevState);			
		}
	}
	
	/**
	 * Resets the current stat
	 * 
	 * @param state
	 */
	public void resetState(ReplayerState state) {
		this.state = state;
		stateListeners.forEach(listener -> listener.stateChanged(state));
	}
	
	public ReplayerState getState() {
		return this.state;
	}
}
