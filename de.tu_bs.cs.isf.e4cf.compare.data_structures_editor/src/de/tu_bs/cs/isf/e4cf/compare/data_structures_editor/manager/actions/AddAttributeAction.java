package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

/**
 * Implementation of UndoAction for AddAttribute
 * 
 * @author Team05
 *
 */

public class AddAttributeAction extends AbstractTreeAction {
    private Attribute addedAttr;

    public AddAttributeAction(Node treeItem) {
	this.setNode(treeItem);
    }

    @Override
    public void undo() {
	getNode().getAttributes().remove(getAddedAttr());
    }

    public void setList(List<Attribute> list) {
	setList(list);
    }

    @Override
    public void execute() {
	String attrName = RCPMessageProvider.inputDialog("Attribute Name", "Enter Attribute Name");
	String attrValue = RCPMessageProvider.inputDialog("Attribute Value", "Enter Attribute Value");
	addedAttr = new AttributeImpl(attrName,new StringValueImpl(attrValue));
	getNode().getAttributes().add(addedAttr);
    }

    public Attribute getAddedAttr() {
        return addedAttr;
    }

    public void setAddedAttr(Attribute addedAttr) {
        this.addedAttr = addedAttr;
    }
}
