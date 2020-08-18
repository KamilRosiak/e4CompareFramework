package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;

public interface FamilyModelTransformation {

	public FamilyModel transform(Object object);
	
	public boolean canTransform(Object object);
}
