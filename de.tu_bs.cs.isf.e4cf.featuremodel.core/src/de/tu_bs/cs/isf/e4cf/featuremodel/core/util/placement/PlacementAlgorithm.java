package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement;

import java.util.List;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;
import javafx.util.Pair;

public interface PlacementAlgorithm {
	
	void format(FeatureDiagramm diagram, List<FXGraphicalFeature> featureList);
	void format(FeatureDiagramm root);
	
	default Pair<Double, Double> format(FXGraphicalFeature root) {
		return new Pair<Double, Double>(0.0d, 0.0d);
		
	}
	
}
