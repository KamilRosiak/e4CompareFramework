package de.tu_bs.cs.isf.e4cf.compare.preferences;

import java.io.Serializable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;

/**
 * Preferences for the comparison process.
 * 
 * @author Kamil Rosiak
 *
 */
public class ComparisonPrefs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float optionalThreshold = 0.4f;
	private String granularityLevel = NodeType.COMPILATION_UNIT.toString();
	private int cloneSize = 40;
	private String fileName = "family_model";

	public ComparisonPrefs() {

	}

	public ComparisonPrefs(float threshold, String granularity, int cloneSize) {
		this.optionalThreshold = threshold;
		this.granularityLevel = granularity;
		this.setCloneSize(cloneSize);
	}

	public float getOptionalThreshold() {
		return optionalThreshold;
	}

	public void setOptionalThreshold(float optionalThreshold) {
		this.optionalThreshold = optionalThreshold;
	}

	public String getGranularityLevel() {
		return granularityLevel;
	}

	public void setGranularityLevel(String granularityLevel) {
		this.granularityLevel = granularityLevel;
	}

	public int getCloneSize() {
		return cloneSize;
	}

	public void setCloneSize(int cloneSize) {
		this.cloneSize = cloneSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
