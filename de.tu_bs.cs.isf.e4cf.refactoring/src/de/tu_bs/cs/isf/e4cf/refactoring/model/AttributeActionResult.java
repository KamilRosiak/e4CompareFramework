package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AttributeActionResult extends ActionResult {

	private Map<MultiSetNode, MultiSetAttribute> affectedAttributes;

	public AttributeActionResult(Set<Node> affectedNodes, Set<MultiSetNode> affectedMultiSetNodes,
			Map<MultiSetNode, MultiSetAttribute> affectedAttributes) {
		super(affectedNodes, affectedMultiSetNodes);
		this.affectedAttributes = affectedAttributes;
	}

	public Map<MultiSetNode, MultiSetAttribute> getAffectedAttributes() {
		return affectedAttributes;
	}

	public void setAffectedAttributes(Map<MultiSetNode, MultiSetAttribute> affectedAttributes) {
		this.affectedAttributes = affectedAttributes;
	}

	

}
