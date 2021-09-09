package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

public class StatsLogger {

	public int baseComponents;

	public int baseConfigurations;

	public int clusterSplits;

	public int clusterMerges;
	
	public void printStats() {
		System.out.println(" ----- Stats ---");
		System.out.println("Base components: " + baseComponents);
		System.out.println("Base configurations: " + baseConfigurations);

		System.out.println("Cluster splits: " + clusterSplits);
		System.out.println("Cluster merges: " + clusterMerges);
		System.out.println();

	}

}
