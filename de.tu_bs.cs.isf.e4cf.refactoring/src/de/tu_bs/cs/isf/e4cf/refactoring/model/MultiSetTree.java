package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;

public class MultiSetTree {

	protected List<MultiSetNode> roots;
	public static CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
			new MetricImpl("test"));

	public MultiSetTree() {
		roots = new ArrayList<MultiSetNode>();
	}

	public List<MultiSetNode> getRoots() {
		return roots;
	}

	public MultiSetNode getByNode(Node node) {
		for (MultiSetNode root : roots) {
			MultiSetNode target = root.getByNode(node);
			if (target != null) {
				return target;
			}
		}
		return null;
	}

	public MultiSetAttribute getByAttribute(Attribute attribute) {
		for (MultiSetNode root : roots) {
			MultiSetAttribute target = getByAttributeRecursively(root, attribute);
			if (target != null) {
				return target;
			}
		}
		return null;
	}

	private MultiSetAttribute getByAttributeRecursively(MultiSetNode multiSetNode, Attribute attribute) {

		if (multiSetNode.getAttribute(attribute) != null) {
			return multiSetNode.getAttribute(attribute);
		}

		for (MultiSetNode child : multiSetNode.getChildren()) {
			MultiSetAttribute multiSetAttribute = getByAttributeRecursively(child, attribute);
			if (multiSetAttribute != null) {
				return multiSetAttribute;
			}
		}
		return null;

	}

	protected Granularity granularity;

	public MultiSetTree(Granularity granularity) {
		this.roots = new ArrayList<MultiSetNode>();
		this.granularity = granularity;

	}

	public void integrate(Tree integrateIn, Tree tree) {
		Comparison<Node> comparison = compareEngine.compare(integrateIn.getRoot(), tree.getRoot());
		integrate(comparison);
	}

	public Granularity getGranularity() {
		return granularity;
	}

	private void integrate(Comparison<Node> comparison) {

		if (comparison.getLeftArtifact() != null && comparison.getRightArtifact() != null) {

			MultiSetNode multiSetNode = getByNode(comparison.getRightArtifact());

			if (multiSetNode != null) {
				multiSetNode.setNode(comparison.getLeftArtifact());

				NodeResultElement element = (NodeResultElement) comparison.getResultElements().get(0);

				for (Entry<String, Float> entry : element.getAttributeSimilarities().entrySet()) {

					String key = entry.getKey();
				
					Attribute attribute1 = comparison.getLeftArtifact().getAttributeForKey(key);
					Attribute attribute2 = comparison.getRightArtifact().getAttributeForKey(key);

					if (attribute1 != null && attribute2 != null
							&& attribute1.getAttributeKey().equals(attribute2.getAttributeKey())) {

						MultiSetAttribute multiSetAttribute2 = multiSetNode.getAttribute(attribute2);
						multiSetAttribute2.setAttribute(attribute1);

					}

				}
			}

			for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
				integrate(childComparison);
			}

		}

	}

	public static MultiSetTree create(Collection<Node> nodes, Granularity granularity) {

		MultiSetTree multiSetTree = new MultiSetTree(granularity);

		for (Node node : nodes) {
			MultiSetNode multiSetNode = MultiSetNode.create(node);
			multiSetTree.roots.add(multiSetNode);
			multiSetNode.setRoot(true);
		}

		for (MultiSetNode root1 : multiSetTree.roots) {
			for (MultiSetNode root2 : multiSetTree.roots) {
				if (!root1.equals(root2)) {
					Comparison<Node> comparison = compareEngine.compare(root1.getNode(), root2.getNode());
					createReferences(comparison, root1, root2);
				}
			}
		}

		return multiSetTree;
	}

	public List<MultiSetNode> getByGranularity(String granularity) {

		List<MultiSetNode> multiSetNodes = new ArrayList<MultiSetNode>();

		for (MultiSetNode multiSetNode : roots) {
			multiSetNodes.addAll(multiSetNode.getByGranularity(granularity));
		}
		return multiSetNodes;
	}

	protected static void createReferences(Comparison<Node> comparison, MultiSetNode multiSetNode1,
			MultiSetNode multiSetNode2) {

		if (comparison.getLeftArtifact() != null && comparison.getRightArtifact() != null) {

			MultiSetNode target1 = multiSetNode1.getByNode(comparison.getLeftArtifact());
			MultiSetNode target2 = multiSetNode2.getByNode(comparison.getRightArtifact());

			target1.addReference(target2);
			target2.addReference(target1);

			createAttributeReference(comparison, target1, target2);

			for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
				createReferences(childComparison, multiSetNode1, multiSetNode2);
			}

		}

	}

	private static void createAttributeReference(Comparison<Node> comparison, MultiSetNode target1,
			MultiSetNode target2) {
		NodeResultElement element = (NodeResultElement) comparison.getResultElements().get(0);

		for (Entry<String, Float> entry : element.getAttributeSimilarities().entrySet()) {
			String key = entry.getKey();			

			Attribute attribute1 = comparison.getLeftArtifact().getAttributeForKey(key);
			Attribute attribute2 = comparison.getRightArtifact().getAttributeForKey(key);

			if (attribute1 != null && attribute2 != null) {
				MultiSetAttribute multiSetAttribute1 = target1.getAttribute(attribute1);

				if (multiSetAttribute1 == null) {
					multiSetAttribute1 = target1.getAttribute(attribute1.getAttributeKey());
				}

				MultiSetAttribute multiSetAttribute2 = target2.getAttribute(attribute2);

				if (multiSetAttribute2 == null) {
					multiSetAttribute2 = target2.getAttribute(attribute2.getAttributeKey());
				}

				multiSetAttribute1.addReference(multiSetAttribute2);
				multiSetAttribute2.addReference(multiSetAttribute1);
			}

		}
	}

	public void removeRoot(MultiSetNode root) {
		this.roots.remove(root);
		removeRootRecursively(root);

	}

	public void removeRootRecursively(MultiSetNode node) {

		for (MultiSetNode reference : node.getReferences()) {
			reference.getReferences().remove(node);
		}

		for (MultiSetAttribute attribute : node.getAttributes()) {
			for (MultiSetAttribute reference : attribute.getReferences()) {
				reference.getReferences().remove(attribute);
			}
		}

		for (MultiSetNode child : node.getChildren()) {
			removeRootRecursively(child);
		}

	}

	public int getNumberOfRoots() {
		return roots.size();
	}

	public List<Tree> restoreTrees() {

		List<Tree> trees = new ArrayList<Tree>();
		for (MultiSetNode root : roots) {

			Node rootNode = root.restoreNode();
			trees.add(new TreeImpl(rootNode.getNodeType(), rootNode));

		}
		return trees;

	}

	public MultiSetTree removeRootAndCreateNewTree(MultiSetNode root) {

		MultiSetTree multiSetTree = new MultiSetTree(granularity);
		root.separate();

		multiSetTree.roots.add(root);
		this.roots.remove(root);
		return multiSetTree;

	}

	public void merge(MultiSetTree multiSetTree) {

		for (MultiSetNode root1 : roots) {

			for (MultiSetNode root2 : multiSetTree.roots) {

				Map<Node, MultiSetNode> mapping = new HashMap<Node, MultiSetNode>();

				Node nodeRoot1 = root1.restoreNodeWithMapping(mapping);
				Node nodeRoot2 = root2.restoreNodeWithMapping(mapping);

				Comparison<Node> comparison = compareEngine.compare(nodeRoot1, nodeRoot2);

				createReferences(comparison, mapping);
			}

		}

		this.roots.addAll(multiSetTree.roots);

	}

	protected void createReferences(Comparison<Node> comparison, Map<Node, MultiSetNode> mapping) {

		if (comparison.getLeftArtifact() != null && comparison.getRightArtifact() != null) {

			MultiSetNode target1 = mapping.get(comparison.getLeftArtifact());
			MultiSetNode target2 = mapping.get(comparison.getRightArtifact());

			target1.addReference(target2);
			target2.addReference(target1);

			createAttributeReference(comparison, target1, target2);

			for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
				createReferences(childComparison, mapping);
			}

		}

	}

	
}
