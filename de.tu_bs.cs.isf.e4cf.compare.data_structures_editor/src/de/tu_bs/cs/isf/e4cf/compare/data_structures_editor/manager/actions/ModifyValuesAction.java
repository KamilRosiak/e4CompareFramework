package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

/**
 * Implementation of UndoAction for Edit and Add Value
 * 
 * @author Team05
 *
 */

public class ModifyValuesAction extends AbstractTreeAction {
	private Set<Value> oldAttributeValues;
	private Set<Value> newAttributeValues;

	public ModifyValuesAction(String name, Set<Value> oldAttributeValues, Set<Value> newAttributeValues) {
		setName(name);
		this.oldAttributeValues = oldAttributeValues;
		this.newAttributeValues = newAttributeValues;
	}

	@Override
	public void undo() {
		newAttributeValues.clear();
		for (Value value : oldAttributeValues) {
			newAttributeValues.add(value);
		}
	}

	@Override
	public void execute() {
	    // TODO Auto-generated method stub
	    
	}

}
