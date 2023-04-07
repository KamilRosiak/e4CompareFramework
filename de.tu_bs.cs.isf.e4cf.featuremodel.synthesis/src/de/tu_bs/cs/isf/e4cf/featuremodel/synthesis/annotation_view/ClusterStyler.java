package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class ClusterStyler {
	
	enum Style {
		ROOT,
		ABSTRACT,
		COLOREDTABLE;
	}
	
	public static void style(Labeled ui, ClusterViewModel model) {
		setBackgroundColor(ui, model);
		manageStyle(model.isRoot(), ui, Style.ROOT);
		manageStyle(model.getCluster().isAbstract(), ui, Style.ABSTRACT);
		
	}
	
	private static void manageStyle(boolean prop, Node ui, Style style) {
		String styleName = style.name().toLowerCase();
		if (prop) {
			if (!ui.getStyleClass().contains(styleName)) {
				ui.getStyleClass().add(styleName);
			}
		} else {
			if (ui.getStyleClass().contains(styleName)) {
				ui.getStyleClass().remove(styleName);
			}
		}
	}
	
	private static void setBackgroundColor(Labeled ui, ClusterViewModel model) {
		BackgroundFill fill = new BackgroundFill(model.getCluster().getSyntaxGroup().getColor(), CornerRadii.EMPTY, Insets.EMPTY);
		ui.setBackground(new Background(fill));
		ui.setPadding(new Insets(1.0d));
		manageStyle(true, ui, Style.COLOREDTABLE);
	}

}
