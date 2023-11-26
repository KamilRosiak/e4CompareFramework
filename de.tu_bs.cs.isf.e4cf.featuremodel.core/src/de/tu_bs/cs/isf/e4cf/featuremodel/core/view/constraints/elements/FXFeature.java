package de.tu_bs.cs.isf.e4cf.featuremodel.core.view.constraints.elements;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXFeature {
	private String featureName;
	private String featureDescription;
	private ImageView icon;
	private IFeature feature;
	
	public FXFeature(IFeature feature, String iconPath) {
		this.setFeature(feature);
		setFeatureName(feature.getName());
		setFeatureDescription(feature.getDescription());
		setImage(iconPath);
	}

	public IFeature getFeature() {
		return feature;
	}

	public void setFeature(IFeature feature) {
		this.feature = feature;
	}
	
	@Override
	public String toString() {
		return getFeatureName();
	}

	/**
	 * Icon
	 */
	public void setImage(String iconUrl) {
		icon =  new ImageView(new Image(iconUrl));
	}

    public ImageView getImage() {
        return icon;
    }
	
	/**
	 * Feature name
	 */
	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	
	/**
	 * Feature description
	 */
	
	public String getFeatureDescription() {
		return featureDescription;
	}

	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription;
	}
	
}
