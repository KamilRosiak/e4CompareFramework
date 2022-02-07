package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.ResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.AttributeDictionary;

public class FileComparator extends BaseComparator {

	private final float FILE_NAME_WEIGHT = 0.5f;
	private final float FILE_EXTENSION_WEIGHT = 0.25f;
	private final float FILE_SIZE_WEIGHT = 0.10f;

	public FileComparator() {
		super(NodeType.FILE.toString());
	}

	@Override
	public ResultElement<Node> compare(Node node1, Node node2) {

		float similarity = 0;

		if (node1 == null || node2 == null || !node1.getNodeType().equals(node2.getNodeType())) {
			return new TaxonomyResultElement(this, similarity);
		}

		float fileExtensionSimilarity = getFileExtensionSimilarity(node1, node2) * FILE_EXTENSION_WEIGHT;
		float fileNameSimilarity = getFileNameSimilarity(node1, node2) * FILE_NAME_WEIGHT;
		float fileSizeSimilarity = getFileSizeSimilarity(node1, node2) * FILE_SIZE_WEIGHT;

		similarity = fileExtensionSimilarity + fileNameSimilarity + fileSizeSimilarity;

		return new TaxonomyResultElement(this, similarity);
	}

	private float getFileExtensionSimilarity(Node node1, Node node2) {
		String fileExtension1 = (String) node1.getAttributeForKey(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY)
				.getAttributeValues().iterator().next().getValue();
		String fileExtension2 = (String) node2.getAttributeForKey(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY)
				.getAttributeValues().iterator().next().getValue();

		return getStringSimilarity(fileExtension1, fileExtension2);
	}

	private float getFileNameSimilarity(Node node1, Node node2) {
		String fileName1 = (String) node1.getAttributeForKey(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY)
				.getAttributeValues().iterator().next().getValue();
		String fileName2 = (String) node2.getAttributeForKey(AttributeDictionary.FILE_NAME_ATTRIBUTE_KEY)
				.getAttributeValues().iterator().next().getValue();

		return getStringSimilarity(fileName1, fileName2);
	}

	private float getFileSizeSimilarity(Node node1, Node node2) {
		float similarity = 0.0f;

		long fileSize1 = (long) node1.getAttributeForKey(AttributeDictionary.SIZE_ATTRIBUTE_KEY).getAttributeValues()
				.iterator().next().getValue();
		long fileSize2 = (long) node2.getAttributeForKey(AttributeDictionary.SIZE_ATTRIBUTE_KEY).getAttributeValues()
				.iterator().next().getValue();

		long maxValue = Math.max(fileSize1, fileSize2);
		long minValue = Math.min(fileSize1, fileSize2);

		if (maxValue > 0) {
			similarity = 1.0f - ((float) maxValue - (float) minValue) / (float) maxValue;
		}

		return similarity;
	}

}
