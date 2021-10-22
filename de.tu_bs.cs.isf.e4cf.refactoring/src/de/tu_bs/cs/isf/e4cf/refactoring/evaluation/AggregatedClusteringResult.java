package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

public class AggregatedClusteringResult {

	public int models;

	public int sufficientClustering;

	public int insufficientClustering;

	public int numberOfClusters;

	public int numberOfConfigurations;

	public float totalIntraSimilarity;

	public float totalInterSimilarity;

	public float getAverageIntraSimilarity() {
		return totalIntraSimilarity / models;
	}

	public float getAverageInterSimilarity() {
		return totalInterSimilarity / models;
	}

	public float getAverageClusterSize() {

		return numberOfConfigurations / numberOfClusters;
	}

	@Override
	public String toString() {
		return sufficientClustering + "," + insufficientClustering + "," + numberOfClusters + ","
				+ getAverageClusterSize() + "," + getAverageInterSimilarity() + "," + getAverageIntraSimilarity();
	}
	
	

}
