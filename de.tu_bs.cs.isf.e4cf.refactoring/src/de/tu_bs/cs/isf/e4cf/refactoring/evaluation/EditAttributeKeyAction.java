package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class EditAttributeKeyAction extends ChangeAction {

	private Attribute attribute;
	
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public EditAttributeKeyAction(Node node, Attribute attribute, String key) {
		super(node, ChangeActionType.EDIT_ATTRIBUTE_KEY);
		this.attribute = attribute;
		this.key = key;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

}
