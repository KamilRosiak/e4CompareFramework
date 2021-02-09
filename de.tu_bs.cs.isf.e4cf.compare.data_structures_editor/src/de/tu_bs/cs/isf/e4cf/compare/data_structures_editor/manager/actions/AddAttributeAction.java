package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * Implementation of UndoAction for AddAttribute
 * 
 * @author Team05
 *
 */

public class AddAttributeAction extends AbstractAction {
    private List<Attribute> list;

    public AddAttributeAction(String name, Node treeItem, List<Attribute> list) {
	this.setName(name);
	this.setNode(treeItem);
	this.list = list;
    }

    @Override
    public void undo() {
	getNode().setAttributes(list);
    }

    public List<Attribute> getList() {
	return list;
    }

    public void setList(List<Attribute> list) {
	setList(list);
    }
}
