package de.tu_bs.cs.isf.e4cf.core.exchange.fide.util;

import static de.tu_bs.cs.isf.e4cf.core.exchange.fide.util.FeatureIDETag.ALT;
import static de.tu_bs.cs.isf.e4cf.core.exchange.fide.util.FeatureIDETag.AND;
import static de.tu_bs.cs.isf.e4cf.core.exchange.fide.util.FeatureIDETag.FEATURE;
import static de.tu_bs.cs.isf.e4cf.core.exchange.fide.util.FeatureIDETag.OR;

import org.eclipse.emf.common.util.EList;

import CrossTreeConstraints.AbstractConstraint;
import CrossTreeConstraints.Formula;
import CrossTreeConstraints.impl.CrossTreeConstraintsFactoryImpl;
import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.GraphicalFeature;
import FeatureDiagram.impl.FeatureDiagramFactoryImpl;
import FeatureModel.FeatureModell;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacemantConsts;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgoFactory;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgorithm;



/**
 * This class is used if one wants to convert a feature ide model (short fide) to a feature model diagram model (short fmd), the standard vat model type
 * 
 * @author Dibo Gonda (y0085182) / Patrick Suszek (y0082857) / Dzhakhar Akperov (y0087205)
 * @date 12.01.2020
 */
public class FeatureIDEConverter {
	
	private static int idCounter;

	/**
	 * Converts feature IDE model to family model
	 * @param fideModel
	 * @return
	 */
	public static FeatureDiagramm convertToFamilyModel(FeatureModell fideModel) {
		idCounter = 0;
		FeatureDiagramm familyModel = FeatureDiagramFactoryImpl.eINSTANCE.createFeatureDiagramm();
		Feature root = convertModelType(fideModel);
		familyModel.setRoot(root);
		//familyModel.set(convertConstraints(fideModel.getConstraints());
		return formatModel(familyModel);
	}

	/**
	 * Formats graphical positions
	 * @param familyModel
	 * @return familyModel (formatted)
	 */
	private static FeatureDiagramm formatModel(FeatureDiagramm familyModel) {
		FeatureDiagramm familyModelTemp = familyModel;
		PlacementAlgorithm formatter = PlacementAlgoFactory.getPlacementAlgorithm(PlacemantConsts.ABEGO_PLACEMENT);
		formatter.format(familyModelTemp);
		return familyModelTemp;
	}
	
	/**
	 * Converts Model Types recursively
	 * @param fideModel
	 * @return feature
	 */
	private static Feature convertModelType(FeatureModell fideModel) {
		if (fideModel.getStruct().getFeature() != null) {
			return convertFeature(fideModel.getStruct().getFeature(), FEATURE, null);
		} else if (fideModel.getStruct().getAnd() != null) {
			return convertFeature(fideModel.getStruct().getAnd(), AND, null);
		} else if (fideModel.getStruct().getOr() != null) {
			return convertFeature(fideModel.getStruct().getOr(), OR, null);
		} else if (fideModel.getStruct().getAlt() != null) {
			return convertFeature(fideModel.getStruct().getAlt(), ALT, null);
		} else {
			return null;
		}
	}
	
	
	/**
	 * Converts Feature of Feature Ide to Vat Feature
	 * @param fideFeature
	 * @param tag
	 * @param parent
	 * @return
	 */
	private static Feature convertFeature(FeatureModel.Feature fideFeature, FeatureIDETag tag, Feature parent) {
		Feature fmdFeature = FeatureDiagramFactoryImpl.eINSTANCE.createFeature();
		
		fmdFeature.setName(fideFeature.getName());
		fmdFeature.setDescription(fideFeature.getDescription());
		fmdFeature.setMandatory(fideFeature.isMandatory());
		fmdFeature.setParent(parent);
		fmdFeature.setIdentifier(idCounter);
		idCounter++;
		fmdFeature.setAbstract(fideFeature.isAbstract());
		fmdFeature.setHidden(fideFeature.isHidden());
	
		fmdFeature = setFeatureTag(fmdFeature, tag);
		fmdFeature.setGraphicalfeature(createGraphicalFeature(fmdFeature));
		addAllChildren(fmdFeature, fideFeature);
				
		return fmdFeature;
	}
	
	/**
	 * In fmd the standard feature is an and feature and the other types are displayed by flags in its data
	 * In fide the standard type of a feature with children is and, but the other types are other features and a feature with no children is a feature
	 * So we need to convert a feature type to a flag
	 * @param fmdFeature
	 * @param tag
	 * @return
	 */
	private static Feature setFeatureTag(Feature fmdFeature, FeatureIDETag tag) {
		Feature fmdFeatureTemp = fmdFeature;
		fmdFeatureTemp.setAlternative(tag == ALT);
		fmdFeatureTemp.setOr(tag == OR);
		
		return fmdFeatureTemp;
	}
	/**
	 * Creates graphical feature (sizes only)
	 * @param fmdFeature
	 * @return graphicalFeature with size only
	 * */
	private static GraphicalFeature createGraphicalFeature(Feature fmdFeature) {
		GraphicalFeature graphicalFeature = FeatureDiagramFactoryImpl.eINSTANCE.createGraphicalFeature();
		graphicalFeature.setHeight(GraphicalFeatureUtils.getHeight());
		graphicalFeature.setWidth(GraphicalFeatureUtils.getWidth(fmdFeature.getName()));
		
		return graphicalFeature;
	}
	
	/** Recursion to add the children
	 * Test if the spectated fide feature has children of that type and if yes
	 * tests if they really exist (because of some ecore stuff it can be not null and have no children of that type)
	 * Multiple if, because a feature can have children of different types
	 * 
	 * @param fmdFeature
	 * @param fideFeature
	 */
	private static void addAllChildren(Feature fmdFeature, FeatureModel.Feature fideFeature) {
		addChildren(fmdFeature, fideFeature.getFeature(), FEATURE);
		addChildren(fmdFeature, fideFeature.getAlt(), ALT);
		addChildren(fmdFeature, fideFeature.getOr(), OR);
		addChildren(fmdFeature, fideFeature.getAnd(), AND);
	}
	
	/**
	 * Adds children
	 * @param fmdFeature
	 * @param children
	 * @param tag
	 */
	private static void addChildren(Feature fmdFeature, EList<FeatureModel.Feature> children, FeatureIDETag tag) {
		if(children != null && children.size() > 0) {
			for (FeatureModel.Feature child : children) {
				fmdFeature.getChildren().add(convertFeature(child, tag, fmdFeature));
			}
		}
	}
	
	/**
	 * Converts the constraints of feature ide to vat
	 * @param fideConstraints
	 * @return
	 */
	private static EList<AbstractConstraint> convertConstraints(FeatureModel.Constraints fideConstraints) {
		EList<AbstractConstraint> fmdConstraint = CrossTreeConstraintsFactoryImpl.eINSTANCE.createFormula().getFormula();
		for (FeatureModel.Rule rule : fideConstraints.getRule()) {
			fmdConstraint.add(convertRule(rule));
		}
		return fmdConstraint;
	}
	
	
	/**
	 * Converts a feature ide rule to a vat formula
	 * @param fideRule
	 * @return
	 */
	private static Formula convertRule(FeatureModel.Rule fideRule) {
		//Get the operator type and convert it
		if (fideRule.getConj() != null && fideRule.getConj().size() > 0) {
			return convertOperator(fideRule.getConj(), FeatureModel.Operator.CONJ);
		} else if (fideRule.getDisj() != null && fideRule.getDisj().size() > 0) {
			return convertOperator(fideRule.getDisj(), FeatureModel.Operator.DISJ);
		} else if (fideRule.getIff() != null && fideRule.getIff().size() > 0) {
			return convertOperator(fideRule.getIff(), FeatureModel.Operator.IFF);
		} else if (fideRule.getImp() != null && fideRule.getImp().size() > 0) {
			return convertOperator(fideRule.getImp(), FeatureModel.Operator.IMP);
		} else if (fideRule.getNot() != null && fideRule.getNot().size() > 0) {
			return convertOperator(fideRule.getNot(), FeatureModel.Operator.NOT);
		}
		return null;
	}
	
	/**
	 * Converts a feture ide formular (opration) to a vat formular
	 * @param operator
	 * @param type
	 * @return
	 */
	private static Formula convertOperator(EList<FeatureModel.Opearation> operator, FeatureModel.Operator type) {
		Formula formular = CrossTreeConstraintsFactoryImpl.eINSTANCE.createFormula();
		if (operator == null || !operator.isEmpty())
		{
			return null;
		}
		switch (type) {
		case CONJ:
			formular.setOperator(CrossTreeConstraints.Operator.AND);
			break;
		case DISJ:
			formular.setOperator(CrossTreeConstraints.Operator.OR);
			break;
		case IFF:
			formular.setOperator(CrossTreeConstraints.Operator.EQUALS);
			break;
		case IMP:
			formular.setOperator(CrossTreeConstraints.Operator.IMPLIES);
			break;
		case NOT:
			formular.setIsNegated(true);
			break;
		default:
			return null;
		}
				
		//Convert operations in operation
		for (FeatureModel.Opearation operation : operator) {
			if (operation.getConj() != null && !operation.getConj().isEmpty()) {
				formular.getFormula().add(convertOperator(operation.getConj(), FeatureModel.Operator.CONJ));
			} else if (operation.getDisj() != null && !operation.getDisj().isEmpty()) {
				formular.getFormula().add(convertOperator(operation.getDisj(), FeatureModel.Operator.DISJ));
			} else if (operation.getIff() != null && !operation.getIff().isEmpty()) {
				formular.getFormula().add(convertOperator(operation.getConj(), FeatureModel.Operator.IFF));
			} else if (operation.getImp() != null && !operation.getImp().isEmpty()) {
				formular.getFormula().add(convertOperator(operation.getConj(), FeatureModel.Operator.IMP));
			} else if (operation.getConj() != null && !operation.getConj().isEmpty()) {
				formular.getFormula().add(convertOperator(operation.getNot(), FeatureModel.Operator.NOT));formular.setIsNegated(true);
			} else if (operation.getVar() != null && !operation.getVar().isEmpty()) {
				formular.setOperator(CrossTreeConstraints.Operator.NONE);
				for (String variable : operator.get(0).getVar()) {
					//literal not implemented
				}
			}
		}
		return formular;
	}
	
	
}
