package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.data;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util.FamilyModelBuilder;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;

public class FamilyModelDataGen extends FamilyModelBuilder {

	public FamilyModel createExampleGenericFamilyModel(EObject variant1, EObject variant2, EObject... artefacts) {
		assert (artefacts != null && artefacts.length >= 1);
		
		int artefactSize = artefacts.length;
		int index = 0;
		
		Variant[] variants = new Variant[] {
				createVariant("VARIANT 1", variant1),
				createVariant("VARIANT 2", variant2)
		};
		
		FamilyModel fm = createFamilyModel(
				"ExampleFamilyModel", 
				new VariationPoint[] {
						createVariationPoint(
								"VP1",
								VariabilityCategory.OPTIONAL,
								new VariantArtefact[] {
										createVariantArtefact(
												"VA1", 
												new EObject[] {artefacts[index++%artefactSize]}, 
												new Variant[] {variants[0]}) 
								},
								new VariationPoint[] {},
								null
						),
						createVariationPoint(
								"VP2",
								VariabilityCategory.ALTERNATIVE,
								new VariantArtefact[] {
										createVariantArtefact(
												"VA2", 
												new EObject[] {artefacts[index++%artefactSize]}, 
												new Variant[] {variants[1]}) 
								},
								new VariationPoint[] {},
								null
						),
						createVariationPoint(
								"VP3",
								VariabilityCategory.MANDATORY,
								new VariantArtefact[] {
										createVariantArtefact(
												"VA3", 
												new EObject[] {artefacts[index++%artefactSize]}, 
												new Variant[] {variants[0]}) 
								},
								new VariationPoint[] {},
								null
						)
				},
				variants
		);
		return fm;
	}
}
