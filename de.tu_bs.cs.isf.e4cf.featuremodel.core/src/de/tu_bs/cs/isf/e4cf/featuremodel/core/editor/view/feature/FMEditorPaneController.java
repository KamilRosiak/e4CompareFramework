package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorToolbar;

public class FMEditorPaneController {
	private FMEditorPaneView view;

	public FMEditorPaneController(ServiceContainer services) {
		this.view = new FMEditorPaneView(new FMEditorToolbar(services, this));
	}

	public FMEditorPaneView ui() {
		return this.view;
	}

	public void displayFeatureDiagram(FXGraphicalFeature root) {
		this.view.setRootFeature(root);
		formatDiagram();
	}

	public void formatDiagram() {
		this.view.formatDiagram();
	}
}
