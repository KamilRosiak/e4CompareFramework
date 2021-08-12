package de.tu_bs.cs.isf.e4cf.featuremodel.core.util;

import java.util.LinkedList;
import java.util.List;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
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
		
		return feature;
		
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
