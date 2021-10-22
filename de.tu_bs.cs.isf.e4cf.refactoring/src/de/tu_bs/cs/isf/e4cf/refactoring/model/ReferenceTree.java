package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.AddAttributeResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.AddAttributeValueResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.AddChildResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.DeleteAttributeResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.DeleteResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.EditAttributeKeyResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.EditAttributeValueResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.MoveResult;

public class ReferenceTree extends MultiSetTree {

	public void addChild(List<AddChildResult> addChildResults, Map<Node, Node> parentToChild) {

		for (AddChildResult addChildResult : addChildResults) {

			MultiSetNode multiSetNode = roots.get(0).getByNode(addChildResult.getParent());

			if (!multiSetNode.containsNode(addChildResult.getChild())) {
				multiSetNode.addChild(addChildResult.getChild(), addChildResult.getPosition(), parentToChild);
			}
		}
	}

	public void addAttribute(List<AddAttributeResult> addAttributeResults, Map<Node, Attribute> nodeToAttribute) {

		for (AddAttributeResult addAttributeResult : addAttributeResults) {

			MultiSetNode multiSetNode = roots.get(0).getByNode(addAttributeResult.getNode());

			if (multiSetNode != null) {
				multiSetNode.addAttribute(addAttributeResult.getAttribute(), nodeToAttribute);
			}
		}
	}

	public void delete(List<DeleteResult> deleteResults, Set<Node> deletedNodes) {

		for (DeleteResult deleteResult : deleteResults) {

			MultiSetNode multiSetNode = roots.get(0).getByNode(deleteResult.getNode());

			if (multiSetNode != null) {
				multiSetNode.delete(deletedNodes);
			}
		}
	}

	public void deleteAttribute(List<DeleteAttributeResult> deleteAttributeResults, Set<Attribute> deletedAttributes) {

		for (DeleteAttributeResult deleteAttributeResult : deleteAttributeResults) {

			MultiSetNode multiSetNode = roots.get(0).getByNode(deleteAttributeResult.getNode());

			if (multiSetNode != null) {
				multiSetNode.deleteAttribute(deleteAttributeResult.getAttribute(), deletedAttributes);
			}

		}

	}

	public void editAttributeKey(List<EditAttributeKeyResult> editAttributeKeyResults,
			Set<Attribute> editedAttributes) {
		for (EditAttributeKeyResult editAttributeKeyResult : editAttributeKeyResults) {

			MultiSetNode multiSetNode = roots.get(0).getByNode(editAttributeKeyResult.getNode());

			if (multiSetNode != null) {
				multiSetNode.editAttributeKey(editAttributeKeyResult.getAttribute(), editAttributeKeyResult.getKey(),
						editedAttributes);
			}

		}
	}

	public void addAttributeValue(List<AddAttributeValueResult> addAttributeValueResults,
			Map<Attribute, Value> attributeToValue) {
		for (AddAttributeValueResult addAttributeValueResult : addAttributeValueResults) {

			MultiSetNode multiSetNode = roots.get(0).getByNode(addAttributeValueResult.getNode());

			if (multiSetNode != null) {
				multiSetNode.addAttributeValue(addAttributeValueResult.getAttribute(),
						addAttributeValueResult.getValue(), attributeToValue);
			}

		}
	}

	public void editAttributeValue(List<EditAttributeValueResult> editAttributeValueResults,
			Map<Attribute, Value> attributeToValue) {
		for (EditAttributeValueResult editAttributeValueResult : editAttributeValueResults) {

			MultiSetNode multiSetNode = roots.get(0).getByNode(editAttributeValueResult.getNode());

			if (multiSetNode != null) {
				multiSetNode.editAttributeValue(editAttributeValueResult.getAttribute(),
						editAttributeValueResult.getValue(), attributeToValue);
			}

		}
	}
	
	
	public void move(List<MoveResult> moveResults) {
		
		for(MoveResult moveResult: moveResults) {
			MultiSetNode multiSetNode = roots.get(0).getByNode(moveResult.getNode());
			
			if(multiSetNode != null) {
				multiSetNode.move(moveResult.getPosition());
			}			
		}
		
	}
	
	

	public ReferenceTree(Granularity granularity) {
		super(granularity);
	}

	public static ReferenceTree buildReferenceTree(Tree tree,
			Map<String, List<Set<MultiSetNode>>> granularityToClusters) {

		ReferenceTree referenceTree = new ReferenceTree(null);

		MultiSetNode multiSetNode = MultiSetNode.create(tree.getRoot());
		referenceTree.roots.add(multiSetNode);
		multiSetNode.setRoot(true);

		return referenceTree;
	}

}
