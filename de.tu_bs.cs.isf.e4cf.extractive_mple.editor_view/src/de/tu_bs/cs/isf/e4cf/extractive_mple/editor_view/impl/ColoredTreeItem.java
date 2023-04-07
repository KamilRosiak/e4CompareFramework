package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.control.TreeTableRow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class ColoredTreeItem extends StylableTreeItem {
	private Color color;
	
	public ColoredTreeItem(Node node, Color color) {
		super(node);
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void style(TreeTableRow<Node> row) {
		row.setBackground(new Background(new BackgroundFill(this.color, null, null)));
		row.getChildrenUnmodifiable().forEach(child -> {
			child.setStyle("-fx-text-fill:whitesmoke;");
		});
	}

}
