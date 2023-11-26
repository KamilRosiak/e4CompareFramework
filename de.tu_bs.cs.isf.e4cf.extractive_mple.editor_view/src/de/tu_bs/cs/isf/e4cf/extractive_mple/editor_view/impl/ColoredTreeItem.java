package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
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
			if (color != null)
				if (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue() <= 128) {
					child.setStyle("-fx-text-fill:whitesmoke;");
				} else {
					child.setStyle("-fx-text-fill:black;");
				}
		});
	}

	@Optional
	@Inject
	public void updateFeature(@UIEventTopic(FDEventTable.UPDATE_FEATURE) Pair<Color, Color> colors) {
		if (colors.first.equals(color)) {
			this.color = colors.second;
		}
	}
}
