package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class MultiSetReferenceTree extends MultiSetTree {

	public MultiSetReferenceTree(Granularity granularity) {
		super(granularity);
		nodeMapping = new HashMap<MultiSetNode, MultiSetNode>();
		attributeMapping = new HashMap<MultiSetAttribute, MultiSetAttribute>();
	}

	private Map<MultiSetNode, MultiSetNode> nodeMapping;

	private Map<MultiSetAttribute, MultiSetAttribute> attributeMapping;

	public static MultiSetReferenceTree buildReferenceTree(Tree tree,
			Map<String, List<Set<MultiSetNode>>> granularityToClusters) {

		MultiSetReferenceTree multiSetTree = new MultiSetReferenceTree(null);

		MultiSetNode multiSetNode = MultiSetNode.build(tree.getRoot());
		multiSetTree.roots.add(multiSetNode);
		multiSetNode.setRoot(true);

		for (String granularity : granularityToClusters.keySet()) {

			for (Set<MultiSetNode> cluster : granularityToClusters.get(granularity)) {

				for (MultiSetNode node1 : cluster) {

					buildMapping(node1, multiSetTree);

				}

			}

		}

		return multiSetTree;
	}

	protected static void buildMapping(MultiSetNode partner1, MultiSetReferenceTree multiSetTree) {

		MultiSetNode partner2 = multiSetTree.getByNode(partner1.getNode()).get(0);

		multiSetTree.nodeMapping.put(partner1, partner2);

		for (MultiSetAttribute attribute1 : partner1.getAttributes()) {

			MultiSetAttribute attribute2 = partner2.getByAttribute(attribute1.getAttribute());

			multiSetTree.attributeMapping.put(attribute1, attribute2);
		}

		for (MultiSetNode child : partner1.getChildren()) {
			buildMapping(child, multiSetTree);
		}

	}

	public MultiSetNode getMapped(MultiSetNode multiSetNode) {
		return nodeMapping.get(multiSetNode);
	}

	public MultiSetAttribute getMapped(MultiSetAttribute multiSetAttribute) {
		return attributeMapping.get(multiSetAttribute);
	}

	public void addMap(MultiSetNode multiSetNode1, MultiSetNode multiSetNode2) {
		nodeMapping.put(multiSetNode1, multiSetNode2);
	}

	public void addMap(MultiSetAttribute multiSetAttribute1, MultiSetAttribute multiSetAttribute2) {
		attributeMapping.put(multiSetAttribute1, multiSetAttribute2);
	}

	public void deleteMap(MultiSetNode multiSetNode1) {
		nodeMapping.remove(multiSetNode1);
	}

	public void deleteMap(MultiSetAttribute multiSetAttribute1) {
		attributeMapping.remove(multiSetAttribute1);
	}

	public void deleteMapByTarget(MultiSetNode multiSetNode) {

		List<MultiSetNode> toRemove = new ArrayList<MultiSetNode>();
		for (Entry<MultiSetNode, MultiSetNode> entry : nodeMapping.entrySet()) {
			if (entry.getValue().equals(multiSetNode)) {
				toRemove.add(entry.getKey());
			}
		}

		for (MultiSetNode node : toRemove) {
			nodeMapping.remove(node);
			for (MultiSetAttribute attribute : node.getAttributes()) {
				deleteMapByTarget(attribute);
			}

		}

	}

	public void deleteMapByTarget(MultiSetAttribute multiSetAttribute) {

		List<MultiSetAttribute> toRemove = new ArrayList<MultiSetAttribute>();
		for (Entry<MultiSetAttribute, MultiSetAttribute> entry : attributeMapping.entrySet()) {
			if (entry.getValue().equals(multiSetAttribute)) {
				toRemove.add(entry.getKey());
			}
		}

		for (MultiSetAttribute node : toRemove) {
			attributeMapping.remove(node);
		}

	}

	public void addMapRecursively(MultiSetNode multiSetNode1, MultiSetNode multiSetNode2) {

		nodeMapping.put(multiSetNode1, multiSetNode2);

		for (MultiSetAttribute multiSetAttribute1 : multiSetNode1.getAttributes()) {

			for (MultiSetAttribute multiSetAttribute2 : multiSetNode2.getAttributes()) {

				if (multiSetAttribute1.getAttribute().equals(multiSetAttribute2.getAttribute())) {

					attributeMapping.put(multiSetAttribute1, multiSetAttribute2);

				}

			}

		}

		for (int i = 0; i < multiSetNode1.getChildren().size(); i++) {
			addMapRecursively(multiSetNode1.getChildren().get(i), multiSetNode2.getChildren().get(i));
		}

	}

	public Map<MultiSetNode, MultiSetNode> getNodeMapping() {
		return nodeMapping;
	}

	public void setNodeMapping(Map<MultiSetNode, MultiSetNode> nodeMapping) {
		this.nodeMapping = nodeMapping;
	}

	public Map<MultiSetAttribute, MultiSetAttribute> getAttributeMapping() {
		return attributeMapping;
	}

	public void setAttributeMapping(Map<MultiSetAttribute, MultiSetAttribute> attributeMapping) {
		this.attributeMapping = attributeMapping;
	}

}
