package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.trasformation;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramFactory;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class FeatureModelTransformation {
	public FeatureDiagramFactory factory = FeatureDiagramFactory.eINSTANCE;

	public FeatureDiagramm trasformaGraphToFM(Tree tree) {
		FeatureDiagramm diagram = factory.createFeatureDiagramm();
		Feature rootFeature = factory.createFeature();
		diagram.setRoot(rootFeature);

		return diagram;
	}

	public Feature transformNodeRecursivly(Feature parent, Node node) {
		Feature feature = factory.createFeature();
		if (parent != null) {
			feature.setParent(parent);
			parent.getChildren().add(feature);
		}
		setVariabilityClass(node, feature);
		return feature;
	}

	public void setVariabilityClass(Node node, Feature feature) {
		switch (node.getVariabilityClass()) {
		case MANDATORY:
			feature.setMandatory(true);
			break;
		case ALTERNATIVE:
			feature.setAlternative(true);
			break;
		case OPTIONAL:
			feature.setMandatory(false);
			break;
		}
	}

}
