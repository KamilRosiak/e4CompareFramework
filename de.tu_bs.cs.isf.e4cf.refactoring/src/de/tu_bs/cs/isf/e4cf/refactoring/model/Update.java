package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.List;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class Update extends Action {

	public Update(Node x, Node y) {
		super(ActionType.UPDATE, x);
		this.y = y;
	}

	private Node y;

	public Node getY() {
		return y;
	}

	public void setY(Node y) {
		this.y = y;
	}

	private String savedNodeType;
	private List<Attribute> savedAttributes;

	@Override
	public void apply() {
		savedNodeType = x.getNodeType();
		savedAttributes = x.cloneNode().getAttributes();

		x.setNodeType(y.getNodeType());
		List<Attribute> attributes = Lists.newArrayList(y.getAttributes());
		x.getAttributes().clear();
		for (Attribute attribute : attributes) {
			x.addAttribute(attribute.cloneAttribute());
		}
	}

	@Override
	public void undo() {
		x.setNodeType(savedNodeType);
		List<Attribute> attributes = Lists.newArrayList(savedAttributes);
		x.getAttributes().clear();
		for (Attribute attribute : attributes) {
			x.addAttribute(attribute.cloneAttribute());
		}

	}

}
