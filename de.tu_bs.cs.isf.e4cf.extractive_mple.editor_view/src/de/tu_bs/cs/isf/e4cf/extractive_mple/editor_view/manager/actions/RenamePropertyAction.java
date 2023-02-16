package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;

/**
 * Implementation of UndoAction for RenameProperty
 * 
 * @author Team05
 *
 */

public class RenamePropertyAction extends AbstractTreeAction {
	private String oldName;
	private AbstractAttribute attr;

	public RenamePropertyAction(String name, String oldName, AbstractAttribute attr) {
		setName(name);
		this.oldName = oldName;
		this.attr = attr;
	}

	@Override
	public void undo() {
		attr.setAttributeKey(oldName);
	}

	@Override
	public void execute() {
	    // TODO Auto-generated method stub
	    
	}
}
