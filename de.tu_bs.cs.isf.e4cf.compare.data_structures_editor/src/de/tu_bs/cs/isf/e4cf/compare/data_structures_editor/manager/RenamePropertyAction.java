package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;

public class RenamePropertyAction implements UndoAction {
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
		System.out.println("UNDO");
		System.out.println(oldName);
		System.out.println(attr);
		attr.setAttributeKey(oldName);
	}
}
