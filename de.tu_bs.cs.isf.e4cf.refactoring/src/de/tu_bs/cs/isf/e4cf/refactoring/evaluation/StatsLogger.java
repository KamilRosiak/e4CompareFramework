package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

public class StatsLogger {

	private static StatsLogger instance;

	public int baseComponents;

	public int baseConfigurations;

	public int clusterSplits;

	public int clusterMerges;

	public static StatsLogger getInstance() {
		return instance;
	}

	public static StatsLogger create() {
		instance = new StatsLogger();
		return instance;
	}

	public void printStats() {
		System.out.println(" ----- Stats ---");
		System.out.println("Base components: " + baseComponents);
		System.out.println("Base configurations: " + baseConfigurations);

		System.out.println("Cluster splits: " + clusterSplits);
		System.out.println("Cluster merges: " + clusterMerges);
		System.out.println();

	}

}
