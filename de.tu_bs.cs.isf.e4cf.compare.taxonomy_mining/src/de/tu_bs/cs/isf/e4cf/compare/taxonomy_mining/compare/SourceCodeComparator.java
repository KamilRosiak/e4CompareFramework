package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.ResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.AttributeDictionary;

public class SourceCodeComparator extends BaseComparator {

	private final float SOURCE_CODE_WEIGHT = 0.7f;
	private final float NESTING_WEIGHT = 0.25f;
	private final float POSITION_WEIGHT = 0.05f;

	public SourceCodeComparator() {
		super(WILDCARD);
	}

	@Override
	public ResultElement<Node> compare(Node node1, Node node2) {
		float similarity = 0;

		if (getTypeSimilarity(node1, node2) == 0) {
			return new TaxonomyResultElement(this, 0);
		}

		float sourceCodeSimilarity = getSourceCodeSimilarity(node1, node2) * SOURCE_CODE_WEIGHT;
		float nestingSimilarity = getNestingSimilarity(node1, node2) * NESTING_WEIGHT;
		float positionSimilarity = getPositionSimilarity(node1, node2) * POSITION_WEIGHT;

		similarity = sourceCodeSimilarity + nestingSimilarity + positionSimilarity;

		return new TaxonomyResultElement(this, similarity);
	}

	private int getTypeSimilarity(Node node1, Node node2) {

		if (node1 == null || node2 == null) {
			return 0;
		}

		return node1.getNodeType().equals(node2.getNodeType()) ? 1 : 0;

	}

	private float getSourceCodeSimilarity(Node node1, Node node2) {

		if (node1 == null || node2 == null) {
			return 0f;
		}

		Attribute sourceCodeAttribute1 = node1.getAttributeForKey(AttributeDictionary.SOURCE_CODE_ATTRIBUTE_KEY);
		List<Value> sourceCodeValues1 = sourceCodeAttribute1.getAttributeValues();
		Attribute sourceCodeAttribute2 = node2.getAttributeForKey(AttributeDictionary.SOURCE_CODE_ATTRIBUTE_KEY);
		List<Value> sourceCodeValues2 = sourceCodeAttribute2.getAttributeValues();

		String sourceCode1 = "";
		for (Value value : sourceCodeValues1) {
			sourceCode1 += value.getValue();
		}
		String sourceCode2 = "";
		for (Value value : sourceCodeValues2) {
			sourceCode2 += value.getValue();
		}

		return getStringSimilarity(sourceCode1, sourceCode2);

	}

	private float getNestingSimilarity(Node node1, Node node2) {

		List<Node> nestingFragments1 = getNestingFragments(node1);
		List<Node> nestingFragments2 = getNestingFragments(node2);

		int maxSize = nestingFragments1.size() > nestingFragments2.size() ? nestingFragments1.size()
				: nestingFragments2.size();

		float sum = 0;
		for (int i = 0; i < maxSize; i++) {

			Node nestingNode1 = i >= nestingFragments1.size() ? null : nestingFragments1.get(i);
			Node nestingNode2 = i >= nestingFragments2.size() ? null : nestingFragments2.get(i);

			if (nestingNode1 == null || nestingNode2 == null) {
				sum += 0;
			} else {
				if (nestingNode1.getNodeType().equals(NodeType.DIRECTORY.toString())
						|| nestingNode2.getNodeType().equals(NodeType.DIRECTORY.toString())) {
					sum += getTypeSimilarity(nestingNode1, nestingNode2);
				} else {

					sum += (getTypeSimilarity(nestingNode1, nestingNode2)
							* getSourceCodeSimilarity(nestingNode1, nestingNode2));

				}
			}

		}

		return sum / maxSize;

	}

	private float getPositionSimilarity(Node node1, Node node2) {

		Attribute positionAttribute1 = node1.getAttributeForKey(AttributeDictionary.POSITION_ATTRIBUTE_KEY);
		Value<Integer> positionValue1 = positionAttribute1.getAttributeValues().get(0);
		Attribute positionAttribute2 = node2.getAttributeForKey(AttributeDictionary.POSITION_ATTRIBUTE_KEY);
		Value<Integer> positionValue2 = positionAttribute2.getAttributeValues().get(0);

		List<Node> nestingFragments1 = getNestingFragments(node1);
		List<Node> nestingFragments2 = getNestingFragments(node2);

		int nestingSize = Math.max(nestingFragments1.size(), nestingFragments2.size());
		int positionSize = Math.max(positionValue1.getValue(), positionValue2.getValue());

		float nestingDifference = (float) Math.abs(nestingFragments1.size() - nestingFragments2.size())
				/ (nestingSize == 0 ? 1 : nestingSize);
		float positionDifference = (float) Math.abs(positionValue1.getValue() - positionValue2.getValue())
				/ (positionSize == 0 ? 1 : positionSize);

		return (nestingDifference + positionDifference) / 2;

	}

	private List<Node> getNestingFragments(Node node) {
		List<Node> nestingFragments = new ArrayList<Node>();
		getNestingFragmentsRecursively(node, nestingFragments);
		Collections.reverse(nestingFragments);
		return nestingFragments;

	}

	private void getNestingFragmentsRecursively(Node node, List<Node> nestingFragments) {

		nestingFragments.add(node);

		if (node.getParent() != null) {
			getNestingFragmentsRecursively(node.getParent(), nestingFragments);
		}

	}

}
