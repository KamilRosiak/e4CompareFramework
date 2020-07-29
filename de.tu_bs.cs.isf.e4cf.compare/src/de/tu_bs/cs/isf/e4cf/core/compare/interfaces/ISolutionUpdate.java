package de.tu_bs.cs.isf.e4cf.core.compare.interfaces;

public interface ISolutionUpdate {
	
	/**
	 * This method updates the solution element and all his child's.
	 */
	public void updateSimilarity();
	
	/**
	 * this method check if is weighted or not and returns the weighted or unweighed similarity.
	 */
	public default float getSimilarity(float resultSimilarity , IWeighted attr , boolean isWeighted) {
		if(!isWeighted) {
			return resultSimilarity;
		} else {
			return resultSimilarity * (((IWeighted)attr).getWeight() / 100);
		}
	}
}
