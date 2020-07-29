package de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements;

import FeatureDiagram.Feature;
import javafx.scene.shape.Circle;

public class FXFeatureUpperConnector extends Circle {
	
	public FXFeatureUpperConnector(Feature feature) {
		createControl(feature);
	}
	
	private void createControl(Feature feature) {
		
		setCenterX(100.0f);
		setCenterY(100.0f);
		setRadius(5.0f);

		if(feature.isMandatory()) {
			styleForMandatory();
		} else {
			styleForOptional();
		}
	}
	
	public void styleForMandatory() {
		getStyleClass().clear();
		getStyleClass().add("upperFeatureConnectorMandatory");
	}
	
	public void styleForOptional() {
		getStyleClass().clear();
		getStyleClass().add("upperFeatureConnectorOptional");
	}

	
}
