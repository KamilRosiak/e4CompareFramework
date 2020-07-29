package de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.elements;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.stringtable.FaMoStringTable;
import familyModel.FamilyModel;
import familyModel.VariabilityGroup;

public class FXFamilyModelElement {
	private Object element;
	
	public FXFamilyModelElement(Object element) {
		this.element = element;
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}
	
	@Override
	public String toString() {
		if(element instanceof FamilyModel) {
			return FaMoStringTable.FAMILY_MODEL;
		} else if(element instanceof VariabilityGroup) {
			VariabilityGroup group = (VariabilityGroup)element;
			return group.getGroupName()+" (" +group.getVariability().toString()+")";
		} else
			return element.toString();
		
		
	}
}
