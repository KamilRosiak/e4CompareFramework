package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;
import javafx.scene.paint.Color;

public class ColoredFeature extends Feature implements StylableFeature {
	private static final long serialVersionUID = 10453653767440955L;
	private Color color;

	public ColoredFeature(String name, Color color) {
		super(name);
		this.color = color;
	}

	@Override
	public void style(FXGraphicalFeature fx) {
		fx.setBackgroundColor(this.color);
		
	}

}
