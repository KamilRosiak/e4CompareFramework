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

public class TreeParser {

	public static FeatureDiagramm parse(Tree tree) {
		FeatureDiagramm model = new FeatureDiagram();
		Feature root = createFeature(tree.getRoot());
		root.setAlternative(false);
		model.setRoot(root);
		
		for (Node n : tree.getRoot().getChildren()) {
			createFeatureModel(n, root);
		}
		
		return model;
	}
	
	public static void createFeatureModel(Node node, Feature parent) {
		Feature feature = createFeature(node, parent);
		for (Node n : node.getChildren()) {
			createFeatureModel(n, feature);
		}
	}
	
	private static Feature createFeature(Node node) {
		Feature feature = FeatureInitializer.createFeature(node.getNodeType(), !node.getVariabilityClass().equals(VariabilityClass.OPTIONAL));
		feature.setAlternative(!hasMandatoryChildren(node));
		ArtifactReference uuid = FeatureDiagramFactoryImpl.eINSTANCE.createArtifactReference();
		uuid.setArtifactClearName(node.getUUID().toString());
		feature.getArtifactReferences().add(uuid);
		
		appendName(node, feature);
		insertValuesAsChildren(node, feature);
		
		return feature;
		
	}

	private static void insertValuesAsChildren(Node node, Feature feature) {
		try {
			Attribute valueAttr = node.getAttributeForKey("Value");
			for (Value<String> value : valueAttr.getAttributeValues()) {
				Feature child = FeatureInitializer.createFeature(value.getValue(), true);
				feature.getChildren().add(child);
				child.setParent(feature);
			}
		} catch (NoSuchElementException e) {
		}
		if (feature.getChildren().size() > 1) {
			feature.setAlternative(true);
		}
	}

	private static void appendName(Node node, Feature feature) {
		try {
			Attribute nameAttr = node.getAttributeForKey("Name");
			String name = "";
			for (Value<String> value : nameAttr.getAttributeValues()) {
				name += " " + value.getValue();
				
			}
			feature.setName(feature.getName() + ":" + name);
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
		return node.getChildren().isEmpty();
	}
	
}
