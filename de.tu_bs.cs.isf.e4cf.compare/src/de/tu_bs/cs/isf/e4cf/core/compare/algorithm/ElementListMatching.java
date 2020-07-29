package de.tu_bs.cs.isf.e4cf.core.compare.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author olive
 *
 * @param <T>
 */
public class ElementListMatching <T> {

	public static interface SimilarityComparator<T> {	
		public float compare(T element1, T element2);
	}

	private SimilarityComparator<T> comparator;
	
	public ElementListMatching(SimilarityComparator<T> comparator) {
		this.comparator = comparator;
	}
	
	
	public float computeTotalMatchingSimilarity(List<T> sourceList, List<T> targetList) {
		int sourceSize = sourceList.size();
		int targetSize = targetList.size();
				
		float[][] similarityMatrix = new float[sourceSize][targetSize];
		for (int i = 0; i < sourceSize; i++) {
			for (int j = 0; j < targetSize; j++) {
				similarityMatrix[i][j] = comparator.compare(sourceList.get(i), targetList.get(j)); 
			}
		}
		
		// iterate over all elements and for every iteration pick the best matching
		List<T> matchedSourceElements = new ArrayList<>();
		List<Float> bestMatchSimilarities = new ArrayList<>(sourceSize);
		while (matchedSourceElements.size() < sourceSize) {
			float maxCurrentSimilarity = -1.f;
			T element = null;
			for (int i = 0; i < sourceSize; i++) {
				for (int j = 0; j < targetSize; j++) {
					if (maxCurrentSimilarity < similarityMatrix[i][j]) { // choose higher similarity
						if (!matchedSourceElements.contains(sourceList.get(i))) { // only consider elements that have not been matched
							maxCurrentSimilarity = similarityMatrix[i][j];
							element = sourceList.get(i);
						}						
					}
				}
			}
			
			if (element != null) {
				matchedSourceElements.add(element);
				bestMatchSimilarities.add(maxCurrentSimilarity);
			} else {
				assert false : "No matching found. Not supposed to occur at this point";
			}
		}
		float accumulatedSimilarity = bestMatchSimilarities.stream().collect(Collectors.summingDouble(sim -> sim)).floatValue();
		float averageSimilarity = accumulatedSimilarity / (float) Math.max(sourceSize, targetSize);
		
		assert 0.f <= averageSimilarity && averageSimilarity <= 1.f : "average similarity value must be between 0.0 and 1.0 inclusive";
		return averageSimilarity;
	}
}
