package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.transformation;

import java.util.Map;

import de.tu_bs.cs.isf.e4cf.core.transform.Transformation;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;

/**
 * Specialized transformation for family models. The transformation provides the possibility to include a mapping 
 * from {@link VariationPoint}s to custom data. This can be used to preserve information about the source structure of the transformation.
 * 
 * @author Oliver Urbaniak
 *
 */
public interface FamilyModelTransformation extends Transformation<FamilyModel> {

	Map<VariationPoint, Object> getData();
}
