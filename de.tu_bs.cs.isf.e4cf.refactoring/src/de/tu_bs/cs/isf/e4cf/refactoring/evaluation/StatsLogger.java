package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

public class StatsLogger {

	public int components;

	public int configurations;

	public int clusterSplits;

	public int clusterMerges;
	
	public float averageIntraSimilarity;
	
	public float averageInterSimilarity;
	
	public long duration;
	
	public void printStats() {
		System.out.println(" ----- Stats ---");
		System.out.println("Components: " + components);
		System.out.println("Configurations: " + configurations);
		System.out.println("Cluster splits: " + clusterSplits);
		System.out.println("Cluster merges: " + clusterMerges);
		System.out.println("Average intra similarity: " + averageIntraSimilarity);
		System.out.println("Average inter similarity: " + averageInterSimilarity);
		System.out.println("Duration: " + duration);
		System.out.println();

	}

}
