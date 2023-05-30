package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.paint.Color;



public class ColorPicker {
	
	public static List<Color> generatePalette(int size) {
		List<Color> palette = new ArrayList<>(size);
		double hueFraction = 360.0 / size;
		for (int i = 0; i < size; i++) {
			double hue = hueFraction * i;
			palette.add(Color.hsb(hue, 1.0d, 0.77));
		}
		// shuffle to reduce likelihood of similar colors near each other
		Collections.shuffle(palette);
		return palette;
	}

}
