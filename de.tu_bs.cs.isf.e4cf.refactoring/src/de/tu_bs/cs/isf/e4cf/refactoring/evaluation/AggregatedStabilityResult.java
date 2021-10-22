package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

public class AggregatedStabilityResult {
	
	public int clusterSplits;
	
	public int clusterMerges;
	
	@Override
	public String toString() {
		return clusterSplits + "," + clusterMerges;
	}
}
