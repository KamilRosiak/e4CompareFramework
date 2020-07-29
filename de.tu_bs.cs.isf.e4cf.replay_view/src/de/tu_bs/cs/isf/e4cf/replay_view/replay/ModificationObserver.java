package de.tu_bs.cs.isf.e4cf.replay_view.replay;

import FeatureDiagramModificationSet.Modification;

/**
 * Observer for modification events.
 * 
 * @author Oliver Urbaniak
 *
 */
public interface ModificationObserver {

	public void onApplyModification(Modification mod);
}
