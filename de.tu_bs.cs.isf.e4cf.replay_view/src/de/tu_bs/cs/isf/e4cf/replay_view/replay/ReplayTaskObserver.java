package de.tu_bs.cs.isf.e4cf.replay_view.replay;

import FeatureDiagramModificationSet.Modification;

/**
 * Observer for modification events.
 * 
 * @author Oliver Urbaniak
 *
 */
public interface ReplayTaskObserver {

	public void onApplyModification(Modification mod);
	
	default public void onFinish() {
		
	}
}
