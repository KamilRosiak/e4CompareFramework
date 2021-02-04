package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import java.util.Set;

public class PropertiesAction implements UndoAction {

	private String name;
	private Set<String> oldAttributeValues;
	private Set<String> newAttributeValues;

	public PropertiesAction(String name, Set<String> oldAttributeValues, Set<String> newAttributeValues) {
		this.name = name;
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
