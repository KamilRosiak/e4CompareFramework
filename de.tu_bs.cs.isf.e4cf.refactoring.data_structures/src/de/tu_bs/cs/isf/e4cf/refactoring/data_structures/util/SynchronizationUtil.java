package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.MultiSetNode;

public class SynchronizationUtil {

	private static CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
			new MetricImpl("test"));

	public static Set<Granularity> getGranularities(Set<String> nodeTypes) {

		Set<Granularity> granularities = new HashSet<Granularity>();
		for (String nodeType : nodeTypes) {
			boolean refactor = nodeType.equals("MethodDeclaration");
			granularities.add(new Granularity(nodeType, refactor));
		}

		return granularities;
	}	

	public static int getPositionOfCommonPredecessor(Node parentLeft, Node parentRight, Node target,
			Comparison<Node> comparison) {

		int position = -1;
		for (int i = target.getPosition() - 1; i >= 0; i--) {

			Node currentNode = parentRight.getChildren().get(i);

			for (Comparison<Node> childComparison : comparison.getChildComparisons()) {

				if (childComparison.getRightArtifact() == currentNode) {

					if (childComparison.getLeftArtifact() != null) {
						position = childComparison.getLeftArtifact().getPosition();
						return position;
					}

				}

			}

		}

		return position;

	}

	public static int getPositionOfCommonPredecessor(Node parentLeft, Node parentRight, Node target) {

		Comparison<Node> comparison = compareEngine.compare(parentLeft, parentRight);
		return getPositionOfCommonPredecessor(parentLeft, parentRight, target, comparison);

	}

	public static List<Node> findLongestCommonSubsequence(List<Node> leftChildren, List<Node> rightChildren,
			List<Comparison<Node>> comparisons) {

		int m = leftChildren.size();
		int n = rightChildren.size();

		int[][] L = new int[m + 1][n + 1];

		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0) {
					L[i][j] = 0;
				} else if (hasPartner(leftChildren.get(i - 1), rightChildren.get(j - 1), comparisons)) {
					L[i][j] = L[i - 1][j - 1] + 1;
				} else {
					L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
				}
			}
		}

		int index = L[m][n];

		Node[] lcs = new Node[index];

		int i = m;
		int j = n;
		while (i > 0 && j > 0) {
			if (hasPartner(leftChildren.get(i - 1), rightChildren.get(j - 1), comparisons)) {
				lcs[index - 1] = leftChildren.get(i - 1);
				i--;
				j--;
				index--;
			} else if (L[i - 1][j] > L[i][j - 1]) {
				i--;
			} else {
				j--;
			}

		}

		List<Node> arrayList = new ArrayList<Node>();
		arrayList.addAll(Arrays.asList(lcs));
		return arrayList;
	}

	public static List<MultiSetNode> findLongestCommonSubsequence(List<MultiSetNode> leftChildren,
			List<MultiSetNode> rightChildren) {

		int m = leftChildren.size();
		int n = rightChildren.size();

		int[][] L = new int[m + 1][n + 1];

		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0) {
					L[i][j] = 0;
				} else if (hasPartner(leftChildren.get(i - 1), rightChildren.get(j - 1))) {
					L[i][j] = L[i - 1][j - 1] + 1;
				} else {
					L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
				}
			}
		}

		int index = L[m][n];

		MultiSetNode[] lcs = new MultiSetNode[index];

		int i = m;
		int j = n;
		while (i > 0 && j > 0) {
			if (hasPartner(leftChildren.get(i - 1), rightChildren.get(j - 1))) {
				lcs[index - 1] = leftChildren.get(i - 1);
				i--;
				j--;
				index--;
			} else if (L[i - 1][j] > L[i][j - 1]) {
				i--;
			} else {
				j--;
			}

		}

		List<MultiSetNode> arrayList = new ArrayList<MultiSetNode>();
		arrayList.addAll(Arrays.asList(lcs));
		return arrayList;
	}

	private static boolean hasPartner(MultiSetNode node1, MultiSetNode node2) {

		if (node1.getReferences().contains(node2) && node2.getReferences().contains(node1)) {
			return true;
		}

		return false;
	}
	
	
	private static boolean hasPartner(Node n1, Node n2, List<Comparison<Node>> comparisons) {

		for (Comparison<Node> comparison : comparisons) {
			if (comparison.getLeftArtifact() == n1 && comparison.getRightArtifact() == n2) {
				return true;
			}
		}

		return false;
	}

}
