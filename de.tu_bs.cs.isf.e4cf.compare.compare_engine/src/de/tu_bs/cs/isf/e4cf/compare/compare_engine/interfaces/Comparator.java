package de.tu_bs.cs.isf.e4cf.compare.compare_engine.interfaces;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.compare_engine.elements.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.date_structure.interfaces.Tree;

public interface Comparator {
	
	public List<Comparison> compare(Tree firstArtifact, Tree secondArtifact);
}