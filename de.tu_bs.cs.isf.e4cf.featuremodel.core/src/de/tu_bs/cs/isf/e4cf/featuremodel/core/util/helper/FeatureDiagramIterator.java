package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;

public class FeatureDiagramIterator implements Iterator<Feature> {
	private Queue<Feature> elementQueue;
	
	public FeatureDiagramIterator(FeatureDiagramm diagram) {
		//fill queue:
		elementQueue = new LinkedList<Feature>();
		elementQueue.add(diagram.getRoot());
	}
	
	@Override
	public boolean hasNext() {
		return !elementQueue.isEmpty();
	}

	@Override
	public Feature next() {
		Feature head = elementQueue.poll();
		elementQueue.addAll(head.getChildren());
		return head;
	}
}
