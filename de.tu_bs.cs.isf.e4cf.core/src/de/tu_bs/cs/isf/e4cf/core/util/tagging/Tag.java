package de.tu_bs.cs.isf.e4cf.core.util.tagging;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A tag identified by its name that has a color
 */
public class Tag {

	private String name;
	private String colorString;

	public Tag(String name, String colorString) {
		setName(name);
		setColorString(colorString);
	}

	public Tag(String name, Color color) {
		setName(name);
		setColor(color);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return Color.web(colorString);
	}

	public void setColor(Color color) {
		this.colorString = toHexString(color);
	}

	public String getColorString() {
		return colorString;
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;
	}

	/**
	 * Format a value as a hex string
	 * 
	 * @param value double
	 * @return hex string
	 */
	private String format(double value) {
		String in = Integer.toHexString((int) Math.round(value * 255));
		return in.length() == 1 ? "0" + in : in;
	}

	/**
	 * Build hex string representing a color
	 * 
	 * @param color to represent
	 * @return hex string for color
	 */
	private String toHexString(Color color) {
		return "#" + (format(color.getRed()) + format(color.getGreen()) + format(color.getBlue())
				+ format(color.getOpacity())).toUpperCase();
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}

		if (!(obj instanceof Tag)) {
			return false;
		}

		Tag tag = (Tag) obj;

		return (tag.name == this.name);
	}
	
	/**
	 * Generates a small circle node with a specific color
	 * 
	 * @return the circle
	 */
	public Circle getTagIcon() {

		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(1);
		dropShadow.setOffsetY(1);
		dropShadow.setRadius(2);
		dropShadow.setColor(Color.GRAY);

		Circle circle = new Circle(6, getColor());
		circle.setEffect(dropShadow);
		return circle;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
