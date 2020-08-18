package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModel;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.FamilyModelFactory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariabilityCategory;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.Variant;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariantArtefact;
import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.model.FamilyModel.VariationPoint;

public class FamilyModelBuilder {

	private static FamilyModelFactory fmFactory = FamilyModelFactory.eINSTANCE;
	
	public static FamilyModel createFamilyModel(String name, VariationPoint[] variationPoints, Variant[] variants) {
		FamilyModel familyModel = fmFactory.createFamilyModel();
		familyModel.setName(name);
		familyModel.getVariationPoints().addAll(Arrays.asList(variationPoints));
		familyModel.getVariants().addAll(Arrays.asList(variants));
		return familyModel;
	}
	
	public static VariationPoint createVariationPoint(String name, VariabilityCategory variability, VariantArtefact[] variantArtefacts, VariationPoint[] children, VariationPoint parent) {
		VariationPoint variationPoint = fmFactory.createVariationPoint();
		variationPoint.setName(name);
		variationPoint.setVariabilityCategory(variability);
		variationPoint.getVariantArtefacts().addAll(Arrays.asList(variantArtefacts));
		variationPoint.getChildren().addAll(Arrays.asList(children));
		variationPoint.setParent(parent);
		return variationPoint;
	}
	
	public static VariantArtefact createVariantArtefact(String name, EObject[] artifacts, Variant[] origins) {
		VariantArtefact variantArtefact = fmFactory.createVariantArtefact();
		variantArtefact.setName(name);
		variantArtefact.getArtefacts().addAll(Arrays.asList(artifacts));
		variantArtefact.getOrigins().addAll(Arrays.asList(origins));
		return variantArtefact;
	}
	
	public static VariantArtefact createVariantArtefact(String name, EObject[] artifacts) {
		VariantArtefact variantArtefact = fmFactory.createVariantArtefact();
		variantArtefact.setName(name);
		variantArtefact.getArtefacts().addAll(Arrays.asList(artifacts));
		return variantArtefact;
	}
	
	public static Variant createVariant(String identifier, EObject instance) {
		Variant variant = fmFactory.createVariant();
		variant.setIdentifier(identifier);
		variant.setInstance(instance);
		return variant;
	}
}
