package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * Encapsulates an attribute and its owner.
 * 
 * @author Team05
 *
 */
public class NodeAttributePair {
    private Node owner;
    private Attribute attribute;

    public NodeAttributePair(Node owner, Attribute attribute) {
	this.owner = owner;
	this.attribute = attribute;
    }

    public Node getOwner() {
	return this.owner;
    }

    public void setOwner(Node owner) {
        this.owner = owner;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
	return this.attribute;
    }

}
