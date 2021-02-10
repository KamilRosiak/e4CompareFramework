package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.Action;

/**
 * Implementation of UndoAction for RenameProperty
 * 
 * @author Team05
 *
 */

public class RenamePropertyAction implements Action {
	private String name;
	private String oldName;
	private AbstractAttribute attr;

	public RenamePropertyAction(String name, String oldName, AbstractAttribute attr) {
		this.name = name;
		this.oldName = oldName;
		this.attr = attr;
	}

	@Override
	public void undo() {
		attr.setAttributeKey(oldName);
	}
}
