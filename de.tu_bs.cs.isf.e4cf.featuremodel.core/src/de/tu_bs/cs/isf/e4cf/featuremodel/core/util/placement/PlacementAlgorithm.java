package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement;

import java.util.List;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;

public interface PlacementAlgorithm {
	
	void format(FeatureDiagramm diagram, List<FXGraphicalFeature> featureList);
	void format(FeatureDiagramm root);
	
	default void format(FXGraphicalFeature root) {
		
	}
	
}
