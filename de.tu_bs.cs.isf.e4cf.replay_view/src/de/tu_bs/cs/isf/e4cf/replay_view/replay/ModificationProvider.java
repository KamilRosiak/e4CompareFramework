package de.tu_bs.cs.isf.e4cf.replay_view.replay;

import java.util.Queue;

import FeatureDiagramModificationSet.Modification;

/**
 * Provides a list of modifications to clients.
 * 
 * @author Oliver Urbaniak
 * @see SelectedModificationProvider
 * @see CheckedModificationProvider
 *
 */
public interface ModificationProvider {

	public Queue<Modification> getModifications();
}
