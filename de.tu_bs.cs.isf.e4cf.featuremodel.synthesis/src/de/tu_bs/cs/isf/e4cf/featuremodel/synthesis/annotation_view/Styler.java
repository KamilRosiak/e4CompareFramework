package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import javafx.scene.Node;

public class Styler {
	
	enum Style {
		ROOT,
		ABSTRACT;
	}
	
	public static void style(Node ui, ClusterViewModel model) {
		manageStyle(model.isRoot(), ui, Style.ROOT.name().toLowerCase());
		manageStyle(model.getCluster().isAbstract(), ui, Style.ABSTRACT.name().toLowerCase());
	}
	
	private static void manageStyle(boolean prop, Node ui, String styleName) {
		if (prop) {
			ui.getStyleClass().add(styleName);
		} else {
			if (ui.getStyleClass().contains(styleName)) {
				ui.getStyleClass().remove(styleName);
			}
		}
	}

}
