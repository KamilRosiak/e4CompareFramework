package de.tu_bs.cs.isf.e4cf.replay_view.view;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import FeatureDiagramModificationSet.FeatureModelModificationSet;

public class ReplayContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		List<FeatureModelModificationSet> fmModSet = (List<FeatureModelModificationSet>) inputElement;
		return fmModSet.toArray();
	}

	@Override
	public Object[] getChildren(Object parent) {
		if (parent instanceof FeatureModelModificationSet) {
			return ((FeatureModelModificationSet) parent).getModifications().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof FeatureModelModificationSet) {
			return !((FeatureModelModificationSet) element).getModifications().isEmpty();
		}
		return false;
	}

}
