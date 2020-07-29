package de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Simple javafx toolbar which can be filled with different nodes.
 * 
 * @author Oliver Urbaniak
 *
 */
public class FXToolbar {

	private HBox hbox;
	private double width;

	/**
	 * 
	 * @param width the size of the toolbar 
	 * @param spacing the spacing of each element in the toolbar
	 */
	public FXToolbar(double width, double spacing) {
		this.hbox = new HBox(spacing);
		this.width = width;
	}
	
	public void addItem(Node node) {
		hbox.getChildren().add(node);
		//HBox.setHgrow(null, Priority.ALWAYS);
		node.prefHeight(width);
	}

	public void addItems(List<Node> nodes) {
		addItems(nodes.toArray(new Node[0]));
	}
	
	public void addItems(Node... nodes) {
		for (Node node : nodes) {
			addItem(node);
		}
	}
	
	public Node removeItem(Node node) {
		hbox.getChildren().remove(node);
		return node;
	}

	public HBox getHbox() {
		return hbox;
	}

	public void setHbox(HBox hbox) {
		this.hbox = hbox;
	}

	public double getWidth() {
		return width;
	}
	
	public void update() {
		
	}

	public void setWidth(double width) {
		this.width = width;
		hbox.getChildren().forEach(node -> node.prefHeight(width));
	}
}
