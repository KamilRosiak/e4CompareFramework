package de.tu_bs.cs.isf.e4cf.compare.comparator;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.AbstractComparsion;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * A container for the storage of node comparisons
 * 
 * @author Kamil Rosiak
 *
 */
public class NodeComparison extends AbstractComparsion<Node> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1693676517573310162L;
	private List<AttrComparison> attributeComparions;

	public NodeComparison(Node leftArtifact, Node rightArtifact) {
		super(leftArtifact, rightArtifact);
		setAttributeComparions(new ArrayList<AttrComparison>());
	}

	public NodeComparison(Node leftArtifact, Node rightArtifact, float similarity) {
		this(leftArtifact, rightArtifact);
		setSimilarity(similarity);
	}

	/**
	 * Copy Constructor
	 * 
	 * @param comparison
	 */
	public NodeComparison(NodeComparison comparison) {
		this(comparison.getLeftElement(), comparison.getRightElement(), comparison.getSimilarityValue());
		setAttributeComparions(comparison.getAttributeComparions());
		copyComparison(comparison);
	}

	public List<AttrComparison> getAttributeComparions() {
		return attributeComparions;
	}

	public void setAttributeComparions(List<AttrComparison> attributeComparions) {
		this.attributeComparions = attributeComparions;
	}

	public void addAttributeComparion(AttrComparison attributeComparion) {
		this.attributeComparions.add(attributeComparion);
	}

	private float getAttributeSimilarity() {
		float similarity = 0f;
		for (AttrComparison attrComp : getAttributeComparions()) {
			similarity += attrComp.getSimilarityValue();
		}

		return similarity;
	}

	@Override
	public float updateSimilarity() {
		float childSim = getChildSimilarity();
		float attrSim = getAttributeComparions().isEmpty() ? 1f : getAttributeSimilarity();

		setSimilarity((childSim + attrSim) / 2);
		return getSimilarityValue();
	}

}
