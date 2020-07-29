package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.view.components;

import java.util.List;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.LabelProvider;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;

/**
 * Label provider for Family Model ecore objects.
 * 
 * @author Oliver Urbaniak
 *
 */
public class DefaultFamilyModelLabelProvider implements LabelProvider {

	protected static final String INVALID_LABEL = "<FamilyModelLabelProvider: invalid label>";
	
	@Override
	public String getLabel(Object artefact) {
		if (artefact instanceof FamilyModel) {
			return ((FamilyModel) artefact).getName();
		} else if (artefact instanceof VariationPoint) {
			return ((VariationPoint) artefact).getName();
		} else if (artefact instanceof Variant) {
			return ((Variant) artefact).getIdentifier();
		} else if (artefact instanceof VariantArtefact) {
			String varArtefactName = ((VariantArtefact) artefact).getName();
			String originString = getOriginString((VariantArtefact) artefact);
			return varArtefactName+" --> "+originString;
		} else {
			return  INVALID_LABEL;
		}
	}

	public String getOriginString(VariantArtefact varArtefact) {
		List<Variant> origins = varArtefact.getOrigins();
		List<String> originNames = origins.stream()
				.map(variant -> variant.getIdentifier() != null ? variant.getIdentifier() : "<no label>")
				.collect(Collectors.toList());
		return String.join(", ", originNames);
	}
}
