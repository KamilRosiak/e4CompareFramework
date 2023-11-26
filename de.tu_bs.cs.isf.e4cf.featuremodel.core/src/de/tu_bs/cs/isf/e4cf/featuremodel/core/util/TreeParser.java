package de.tu_bs.cs.isf.e4cf.featuremodel.core.util;

import java.util.List;
import java.util.NoSuchElementException;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.impl.FeatureDiagramFactoryImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.FeatureDiagram;

/**
 * This class helps convert a Tree to a FeatureDiagram
 * 
 * @author {Eike Schmitz, Lukas Cronauer}
 *
 */
public class TreeParser {

	public static FeatureDiagramm parse(Tree tree) {
		FeatureDiagramm model = new FeatureDiagram();
		Feature root = createFeature(tree.getRoot());
		root.setAlternative(false);
		model.setRoot(root);
		
		for (Node node : tree.getRoot().getChildren()) {
			createFeatureModel(node, root);
		}
		
		return model;
	}
	
	private static void createFeatureModel(Node node, Feature parent) {
		Feature feature = createFeature(node, parent);
		for (Node n : node.getChildren()) {
			createFeatureModel(n, feature);
		}
	}
	
	private static Feature createFeature(Node node) {
		Feature feature = FeatureInitializer.createFeature(node.getNodeType(), !node.getVariabilityClass().equals(VariabilityClass.OPTIONAL));
		feature.setOr(hasOptionalChildren(node));
		if (!hasMandatoryChildren(node) && node.getVariabilityClass().equals(VariabilityClass.ALTERNATIVE)) {
			feature.setOr(false);
			feature.setAlternative(true);
		}
		
		
		ArtifactReference uuid = FeatureDiagramFactoryImpl.eINSTANCE.createArtifactReference();
		uuid.setArtifactClearName(node.getUUID().toString());
		feature.getArtifactReferences().add(uuid);	
		appendAttr(node, feature, "Type");
		appendAttr(node, feature, "Name");
		appendAttr(node, feature, "Condition");
		appendAttr(node, feature, "Iterator");
		appendAttr(node, feature, "Operator");
		insertValuesAsChildren(node, feature);
		
		return feature;
		
	}

	private static void insertValuesAsChildren(Node node, Feature feature) {
		try {
			Attribute valueAttr = node.getAttributeForKey("Value");
			for (Value<String> value : valueAttr.getAttributeValues()) {
				Feature child = FeatureInitializer.createFeature(value.getValue(), true);
				if (node.getVariabilityClass().equals(VariabilityClass.ALTERNATIVE)) {
					child.setMandatory(false);
				}
				feature.getChildren().add(child);
				child.setParent(feature);
			}
		} catch (NoSuchElementException e) {
		}
		if (feature.getChildren().size() > 1) {
			feature.setAlternative(true);
		}
	}

	private static void appendAttr(Node node, Feature feature, String property) {
		try {
			Attribute attr = node.getAttributeForKey(property);
			String append = "";
			for (Value<String> value : attr.getAttributeValues()) {
				append += " " + value.getValue();
				
			}
			feature.setName(feature.getName() + ":" + append);
		} catch (NoSuchElementException e) {
		}
	}
	
	private static Feature createFeature(Node node, Feature parent) {
		Feature feature = createFeature(node);
		feature.setParent(parent);
		parent.getChildren().add(feature);
		
		return feature;
		
	}
	
	private static boolean hasMandatoryChildren(Node node) {
		for (Node n : node.getChildren()) {
			if (n.getVariabilityClass().equals(VariabilityClass.MANDATORY)) {
				return true;
			}
			
		}
		return false;
	}
	
	private static boolean hasOptionalChildren(Node node) {
		for (Node n : node.getChildren()) {
			if (n.getVariabilityClass().equals(VariabilityClass.OPTIONAL)) {
				return true;
			}
			
		} 
		return false;
	}
	
}
