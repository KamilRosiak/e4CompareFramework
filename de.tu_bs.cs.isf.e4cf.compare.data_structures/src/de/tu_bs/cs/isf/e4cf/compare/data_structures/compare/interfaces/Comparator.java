package de.tu_bs.cs.isf.e4cf.compare.data_structures.compare.interfaces;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.compare.elements.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public interface Comparator {
	
	public List<Comparison> compare(Tree firstArtifact, Tree secondArtifact);
}