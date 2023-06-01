package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.io.Serializable;

import javafx.scene.paint.Color;

public class FeatureColor implements Serializable {
	private static final long serialVersionUID = 5331212479469080520L;
	public static final FeatureColor Unset = new FeatureColor(0, 0, 0, 1);
	
	private final double red;
	private final double blue;
	private final double green;
	private final double alpha;
	
	public FeatureColor(Color color) {
		this.red = color.getRed();
		this.blue = color.getBlue();
		this.green = color.getGreen();
		this.alpha = color.getOpacity();
	}
	
	private FeatureColor(double red, double blue, double green, double alpha) {
		this.red = red;
		this.blue = blue;
		this.green = green;
		this.alpha = alpha;
	}
	
	public Color get() {
		return new Color(red, green, blue, alpha);
	}	
}
 