package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorToolbar;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FMEditorPaneView.FMEditorPaneMouseHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.PrimaryMouseButtonClickedHandler;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.handler.SelectionAreaHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class FMEditorPaneController {
	private ServiceContainer services;
	private FMEditorPaneView view;
	
	public FMEditorPaneController(ServiceContainer services) {
		this.services = services;
		this.view = new FMEditorPaneView(
			new FMEditorToolbar(services),
			new FMEditorPaneMouseHandler() {

				@Override
				public EventHandler<MouseEvent> resetHandler() {
					return e -> e.consume();
				}

				@Override
				public EventHandler<MouseEvent> dragHandler() {
					return e -> e.consume();
				}

				@Override
				public EventHandler<MouseEvent> selectionAreaHandler() {
					return new SelectionAreaHandler(services);
				}

				@Override
				public EventHandler<MouseEvent> primaryMouseBtnHandler() {
					return new PrimaryMouseButtonClickedHandler(services);
				}

				@Override
				public EventHandler<ScrollEvent> zoomHandler() {
					return null;
				}
				
			}
		);
	}
	
	public FMEditorPaneView ui() {
		return this.view;
	}
	
	public void displayFeatureDiagram(FXGraphicalFeature root) {
		this.view.setRootFeature(root);
		for (FXGraphicalFeature child : root.getChildFeatures()) {
			this.view.insertFeatureBelow(root, child);
		}
	}

}
