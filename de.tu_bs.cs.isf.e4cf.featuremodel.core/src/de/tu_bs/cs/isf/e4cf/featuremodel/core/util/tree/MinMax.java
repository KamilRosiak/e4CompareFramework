package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree;

public class MinMax {
	public double min;
	public double max;
	
	public MinMax(double min, double max) {
		this.min = min;
		this.max = max;
	}
	
	/**
	 * returns the middle between min and max
	 */
	public double calculateMiddle() {
		double distance = max - min;
		return min + distance/2;
	}
}
