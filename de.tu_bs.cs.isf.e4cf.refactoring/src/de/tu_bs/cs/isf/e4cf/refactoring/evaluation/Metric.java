package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

public class Metric {

	private float type2AttributeChangeDegree;

	private int variantChangeDegree;

	private float synchronizationDegree;

	private String granularity;

	public Metric(float type2AttributeChangeDegree, int variantChangeDegree, float synchronizationDegree,
			String granularity) {
		super();
		this.type2AttributeChangeDegree = type2AttributeChangeDegree;
		this.variantChangeDegree = variantChangeDegree;
		this.synchronizationDegree = synchronizationDegree;
		this.granularity = granularity;
	}

	public float getSynchronizationDegree() {
		return synchronizationDegree;
	}

	public void setSynchronizationDegree(float synchronizationDegree) {
		this.synchronizationDegree = synchronizationDegree;
	}

	public String getGranularity() {
		return granularity;
	}

	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	public float getType2AttributeChangeDegree() {
		return type2AttributeChangeDegree;
	}

	public void setType2AttributeChangeDegree(float type2AttributeChangeDegree) {
		this.type2AttributeChangeDegree = type2AttributeChangeDegree;
	}

	public int getVariantChangeDegree() {
		return variantChangeDegree;
	}

	public void setVariantChangeDegree(int variantChangeDegree) {
		this.variantChangeDegree = variantChangeDegree;
	}

}
