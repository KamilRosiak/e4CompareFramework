package de.tu_bs.cs.isf.e4cf.replay_view.replay.state;

public enum ReplayerState {

	/**
	 * States: INACTIVE, ACTIVE, PAUSED
	 */
	INACTIVE {

		@Override
		public ReplayerState next(ReplayAction action) {
			switch (action) {
				case APPLY: 	return PLAYING;
				default:		return UNDEFINED;
			}
		}
		
	},
	PLAYING {

		@Override
		public ReplayerState next(ReplayAction action) {
			switch (action) {
				case CANCEL:	return INACTIVE;
				case PAUSE: 	return PAUSED;
				default:		return UNDEFINED;
			}
		}
		
	},
	PAUSED {

		@Override
		public ReplayerState next(ReplayAction action) {
			switch (action) {
				case CANCEL:	return INACTIVE;
				case RESUME: 	return PLAYING;
				case JUMP: 		return JUMPING;
				case PAUSE:		return PAUSED;
				default:		return UNDEFINED;
			}
		}
		
	},
	JUMPING {

		@Override
		public ReplayerState next(ReplayAction action) {
			switch (action) {
				case CANCEL:	return INACTIVE;
				case PAUSE: 	return PAUSED;
				default:		return UNDEFINED;
			}
		}
		
	},
	UNDEFINED {

		@Override
		public ReplayerState next(ReplayAction action) {
			return ERROR;
		}
		
	},
	ERROR {

		@Override
		public ReplayerState next(ReplayAction action) {
			return null;
		}
		
	};
	
	/**
	 * Actions: CANCEL, APPLY, RESUME, PAUSE, JUMP, ERROR 
	 */
	public static enum ReplayAction {CANCEL, APPLY, RESUME, PAUSE, JUMP, ERROR};
	
		
	public abstract ReplayerState next(ReplayAction action);
}
