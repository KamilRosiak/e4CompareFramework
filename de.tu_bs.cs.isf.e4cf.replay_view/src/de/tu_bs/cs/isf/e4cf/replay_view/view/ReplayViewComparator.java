package de.tu_bs.cs.isf.e4cf.replay_view.view;

import java.util.Optional;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;

public class ReplayViewComparator extends ViewerComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof FeatureModelModificationSet && e2 instanceof FeatureModelModificationSet) {
			FeatureModelModificationSet modSet1 = (FeatureModelModificationSet) e1;
			FeatureModelModificationSet modSet2 = (FeatureModelModificationSet) e2;
			
			return Long.compare(getMinTimestamp(modSet1), getMinTimestamp(modSet2));
		} else if (e1 instanceof Modification && e2 instanceof Modification) {
			Modification mod1 = (Modification) e1;
			Modification mod2 = (Modification) e2;
			return Long.compare(mod1.getTimeStamp(), mod2.getTimeStamp());			
		} else {
			throw new RuntimeException("Objects must both be of the same type and either FeatureModelModificationSet or Modification.");		
		}
	}

	private long getMinTimestamp(FeatureModelModificationSet modSet) {
		Optional<Long> minTimestamp = modSet.getModifications().stream()
			.map(mod -> mod.getTimeStamp())
			.min((t1, t2) -> Long.compare(t1, t2));
		
		if (minTimestamp.isPresent()) {
			return minTimestamp.get();
		} else {
			return 0;
		}
	}
}
