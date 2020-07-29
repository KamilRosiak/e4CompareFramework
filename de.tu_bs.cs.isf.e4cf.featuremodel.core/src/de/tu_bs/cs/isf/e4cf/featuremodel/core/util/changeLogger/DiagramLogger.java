package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger;

import java.util.List;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

public interface DiagramLogger {	
	void startLogging(FeatureDiagramm diagram, List<FXGraphicalFeature> featureList);
	void stopLogging();
	void exportLogs();
}
