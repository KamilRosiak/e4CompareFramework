package de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging;

import javafx.scene.paint.Color;

public class Tag {

	private String name;
	private Color color;
	
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
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
