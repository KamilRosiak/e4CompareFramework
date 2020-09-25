package de.tu_bs.cs.isf.e4cf.core.compare.algorithm;

public class CountAlgorithm {
	
	/**
	 * returns the quotient of sourceCount with targetCount (min/max) if both are 0 the returns 1 when source or target is 0 then it returns 0. 
	 */
	public static float countToSimilarity(int sourceCount, int targetCount) {
		
		if(sourceCount == 0 && targetCount == 0) {
			return 1f;
		}
		
		if(sourceCount == 0 || targetCount == 0) {
			return 0f;
		}
		
		return (float) Math.min(sourceCount, targetCount) / (float) Math.max(sourceCount, targetCount);
	}
}
