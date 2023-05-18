package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.util.tree.Node;
import de.tu_bs.cs.isf.e4cf.core.util.tree.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.Cluster;

public class FeatureOrganizer {
	private MPLPlatform mpl;
	private List<Cluster> clusters;
	
	public FeatureOrganizer(MPLPlatform mpl, List<Cluster> clusters) {
		this.mpl = mpl;
		this.clusters = clusters;
	}
	
	public Tree<Cluster> createHierarchy() {
		return new Tree<>(new Node<>(clusters.get(0)));
	}

}
