package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import java.util.Set;

/**
 * Implementation of UndoAction for Edit and Add Value
 * 
 * @author Team05
 *
 */

public class ModifyValuesAction extends AbstractAction {
	private Set<String> oldAttributeValues;
	private Set<String> newAttributeValues;

	public ModifyValuesAction(String name, Set<String> oldAttributeValues, Set<String> newAttributeValues) {
		setName(name);
		this.oldAttributeValues = oldAttributeValues;
		this.newAttributeValues = newAttributeValues;
	}

	@Override
	public void undo() {
		newAttributeValues.clear();
		for (String value : oldAttributeValues) {
			newAttributeValues.add(value);
		}
	}

}
