package de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class FXFeatureLowerConnector extends Arc {
	private double centerX;
	private double centerY; 
	private double radius;
	private final double radiusY = 4d;
	
	public FXFeatureLowerConnector(FXGraphicalFeature fxFeature) {
		centerX = fxFeature.getTranslateX() + fxFeature.getWidth()/2;
		centerY = fxFeature.getTranslateY() + fxFeature.getHeight();
		radius = fxFeature.getWidth()/2;
		addWidthListener(fxFeature);
	}
	

	private void addWidthListener(FXGraphicalFeature fxFeature) {
		//bind width value for repaint
		fxFeature.widthProperty().addListener(e-> {
			radius = fxFeature.getWidth()/2;
			if(fxFeature.getFeature().isOr()) {
				drawOr();
			} else if(fxFeature.getFeature().isAlternative()) {
				drawAlternative();
			}
			
		});
	}
	
	/**
	 * this method draws a unfilled half circle.
	 */
    public void drawAlternative() {
    	setCenterX(centerX);
    	setCenterY(centerY);
    	setRadiusX(radius-3);
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
    	setRadiusX(radius-3);
    	setRadiusY(radiusY);
    	setLength(180);
    	setStartAngle(180);
    	setType(ArcType.OPEN);
    	setStroke(Color.BLACK);
    	setFill(Color.BLACK);
    	setStrokeWidth(1);
    }
}
