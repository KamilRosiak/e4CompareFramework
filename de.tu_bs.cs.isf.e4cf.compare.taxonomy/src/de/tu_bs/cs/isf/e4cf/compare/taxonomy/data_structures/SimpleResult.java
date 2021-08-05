/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

/**
 * @author developer-olan
 *
 */
public class SimpleResult {
	private String leftNode;
	private String rightNode;
	private float similarity;
	
	public SimpleResult(String _leftNodeSignature, String _rightNodeSignature, float _similarity) {
		this.leftNode = _leftNodeSignature;
		this.rightNode = _rightNodeSignature;
		this.similarity = _similarity;
	}
	
	public String getLeftNode() {
		return leftNode;
	}
	
	public String getRightNode() {
		return rightNode;
	}
	
	public float getSimilarity() {
		return similarity;
	}
	
}
