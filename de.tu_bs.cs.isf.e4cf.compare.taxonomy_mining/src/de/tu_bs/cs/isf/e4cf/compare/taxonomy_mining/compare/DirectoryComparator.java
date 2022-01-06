package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.ResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.AttributeDictionary;

public class DirectoryComparator extends BaseComparator {

	private final float DIRECTORY_NAME_WEIGHT = 0.5f;
	private final float SOURCE_CODE_FILE_WEIGHT = 0.25f;
	private final float NON_SOURCE_CODE_FILE_WEIGHT = 0.15f;
	private final float DIRECTORY_SIZE_WEIGHT = 0.10f;

	public DirectoryComparator() {
		super(NodeType.DIRECTORY.toString());
	}

	@Override
	public ResultElement<Node> compare(Node node1, Node node2) {

		if (node1 == null || node2 == null || !node1.getNodeType().equals(node2.getNodeType())) {
			return new TaxonomyResultElement(this, 0.0f);
		}

		float similarity = getDirectoryNameSimilarity(node1, node2) * DIRECTORY_NAME_WEIGHT
				+ getSourceCodeFileNameSimilarity(node1, node2) * SOURCE_CODE_FILE_WEIGHT
				+ getNonSourceCodeFileNameSimilarity(node1, node2) * NON_SOURCE_CODE_FILE_WEIGHT
				+ getDirectorySizeSimilarity(node1, node2) * DIRECTORY_SIZE_WEIGHT;

		return new TaxonomyResultElement(this, similarity);

	}

	private float getDirectoryNameSimilarity(Node node1, Node node2) {

		String directoryName1 = (String) node1.getAttributeForKey(AttributeDictionary.DIRECTORY_NAME_ATTRIBUTE_KEY)
				.getAttributeValues().iterator().next().getValue();
		String directoryName2 = (String) node2.getAttributeForKey(AttributeDictionary.DIRECTORY_NAME_ATTRIBUTE_KEY)
				.getAttributeValues().iterator().next().getValue();

		return getStringSimilarity(directoryName1, directoryName2);

	}

	private float getSourceCodeFileNameSimilarity(Node node1, Node node2) {

		List<Node> sourceFiles1 = new ArrayList<Node>();
		List<Node> sourceFiles2 = new ArrayList<Node>();

		for (Node child : node1.getChildren()) {
			if (!child.getNodeType().equals(NodeType.DIRECTORY.toString())) {
				sourceFiles1.add(child);
			}
		}
		for (Node child : node2.getChildren()) {
			if (!child.getNodeType().equals(NodeType.DIRECTORY.toString())) {
				sourceFiles2.add(child);
			}
		}

		return computeSimilarityOfFiles(sourceFiles1, sourceFiles2);

	}

	private float getDirectorySizeSimilarity(Node node1, Node node2) {
		float similarity = 0.0f;

		long directorySize1 = (long) node1.getAttributeForKey(AttributeDictionary.SIZE_ATTRIBUTE_KEY)
				.getAttributeValues().iterator().next().getValue();
		long directorySize2 = (long) node2.getAttributeForKey(AttributeDictionary.SIZE_ATTRIBUTE_KEY)
				.getAttributeValues().iterator().next().getValue();

		long maxValue = Math.max(directorySize1, directorySize2);
		long minValue = Math.min(directorySize1, directorySize2);

		if (maxValue > 0) {
			similarity = 1.0f - ((float) maxValue - (float) minValue) / (float) maxValue;
		}

		return similarity;
	}

	private float computeSimilarityOfFiles(List<Node> files1, List<Node> files2) {
		

		float totalSimilarity = 0;

		for (Node file1 : files1) {
			
			float minimumSimilarity = Float.MAX_VALUE;

			String fileName1 = (String) file1.getAttributeForKey(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY)
					.getAttributeValues().iterator().next().getValue();

			for (Node file2 : files2) {

				String fileName2 = (String) file2.getAttributeForKey(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY)
						.getAttributeValues().iterator().next().getValue();

				float similarity = getStringSimilarity(fileName1, fileName2);

				if (similarity < minimumSimilarity) {
					minimumSimilarity = similarity;
				}
			}
			totalSimilarity += minimumSimilarity;

		}

		return totalSimilarity / files1.size();

	}

	private float getNonSourceCodeFileNameSimilarity(Node node1, Node node2) {

		List<Node> nonSourceFiles1 = new ArrayList<Node>();
		List<Node> nonSourceFiles2 = new ArrayList<Node>();

		for (Node child : node1.getChildren()) {
			if (!child.getNodeType().equals(NodeType.FILE.toString())
					&& !child.getNodeType().equals(NodeType.DIRECTORY.toString())) {
				nonSourceFiles1.add(child);
			}
		}
		for (Node child : node2.getChildren()) {
			if (!child.getNodeType().equals(NodeType.FILE.toString())
					&& !child.getNodeType().equals(NodeType.DIRECTORY.toString())) {
				nonSourceFiles2.add(child);
			}
		}

		return computeSimilarityOfFiles(nonSourceFiles1, nonSourceFiles2);
	}

}
