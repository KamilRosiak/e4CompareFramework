package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;

public class FeatureDiagramIterator implements Iterator<IFeature> {
	private Queue<IFeature> elementQueue;

	public FeatureDiagramIterator(FeatureDiagram diagram) {
		// fill queue:
		elementQueue = new LinkedList<IFeature>();
		elementQueue.add(diagram.getRoot());
	}

	@Override
	public boolean hasNext() {
		return !elementQueue.isEmpty();
	}

	@Override
	public IFeature next() {
		IFeature head = elementQueue.poll();
		elementQueue.addAll(head.getChildren());
		return head;
	}
}
