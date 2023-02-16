package de.tu_bs.cs.isf.e4cf.compare.matcher.util;

import java.io.Serializable;

import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;

public class ArtifactFactory implements Serializable {

	public static <K> Comparison<K> copyComparison(Comparison<K> source) {
		if (source instanceof NodeComparison) {
			NodeComparison nodeComp = (NodeComparison) source;
			return (Comparison<K>) new NodeComparison(nodeComp);
		}
		return source;

	}
}
