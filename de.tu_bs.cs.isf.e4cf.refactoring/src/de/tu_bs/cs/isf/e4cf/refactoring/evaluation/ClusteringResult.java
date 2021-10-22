package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

public class ClusteringResult {

	private float averageIntraSimilarity;

	private float averageInterSimilarity;

	public int insufficientClusters;
	
	public int numberOfClusters;
	
	public float averageClusterSize;

	public int getInsufficientClusters() {
		return insufficientClusters;
	}

	public void setInsufficientClusters(int insufficientClusters) {
		this.insufficientClusters = insufficientClusters;
	}

	public int getNumberOfClusters() {
		return numberOfClusters;
	}

	public void setNumberOfClusters(int numberOfClusters) {
		this.numberOfClusters = numberOfClusters;
	}

	public float getAverageIntraSimilarity() {
		return averageIntraSimilarity;
	}

	public void setAverageIntraSimilarity(float averageIntraSimilarity) {
		this.averageIntraSimilarity = averageIntraSimilarity;
	}

	public float getAverageInterSimilarity() {
		return averageInterSimilarity;
	}

	public void setAverageInterSimilarity(float averageInterSimilarity) {
		this.averageInterSimilarity = averageInterSimilarity;
	}

	@Override
	public String toString() {
		return "" + averageIntraSimilarity + "," + averageInterSimilarity + "," + insufficientClusters + "," + numberOfClusters + "," + averageClusterSize;
	}
	
	

}
