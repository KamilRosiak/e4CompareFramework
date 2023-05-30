package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import javafx.scene.shape.Circle;

public class FXFeatureUpperConnector extends Circle {
	
	public FXFeatureUpperConnector(IFeature feature) {
		setCenterX(100.0f);
		setCenterY(100.0f);
		setRadius(0.0f);
		this.setVariability(feature.getVariability());
	}
	
	public void setVariability(Variability variability) {
		this.getStyleClass().clear();
		switch (variability) {
		case MANDATORY:
			this.getStyleClass().add("upperFeatureConnectorMandatory");
			setRadius(5.0f);
			break;
		case OPTIONAL:
			this.getStyleClass().add("upperFeatureConnectorOptional");
			setRadius(5.0f);
			break;
		case DEFAULT:
			setRadius(0.0f);
			break;
		}
	}	
}
