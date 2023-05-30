package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class FXFeatureLowerConnector extends Arc {
	private double centerX;
	private double centerY; 
	private final double radiusX = 7d;
	private final double radiusY = 7d;
	
	public FXFeatureLowerConnector(FXGraphicalFeature fxFeature) {
		centerX = fxFeature.getTranslateX() + fxFeature.getWidth()/2;
		centerY = fxFeature.getTranslateY() + fxFeature.getHeight();
	}
	
	/**
	 * this method draws a unfilled half circle.
	 */
    public void drawAlternative() {
    	setCenterX(centerX);
    	setCenterY(centerY);
    	setRadiusX(radiusX);
    	setRadiusY(radiusY);
    	setLength(180);
    	setStartAngle(180);
    	setType(ArcType.OPEN);
    	setStroke(Color.BLACK);
    	setFill(Color.WHITE);
    	setStrokeWidth(1);
    }
	/**
	 * this method draws a black filled half circle.
	 */
    public void drawOr() {
    	setCenterX(centerX);
    	setCenterY(centerY);
    	setRadiusX(radiusX);
    	setRadiusY(radiusY);
    	setLength(180);
    	setStartAngle(180);
    	setType(ArcType.OPEN);
    	setStroke(Color.BLACK);
    	setFill(Color.BLACK);
    	setStrokeWidth(1);
    }
}
