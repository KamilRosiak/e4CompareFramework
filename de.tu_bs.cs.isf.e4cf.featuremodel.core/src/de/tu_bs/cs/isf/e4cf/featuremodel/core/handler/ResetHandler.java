package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ResetHandler implements EventHandler<MouseEvent> {
	private DragHandler dragHandler;
	private SelectionAreaHandler selectionHandler;
	
	public ResetHandler(DragHandler dragHandler, SelectionAreaHandler selectionHandler) {
		this.dragHandler = dragHandler;
		this.selectionHandler = selectionHandler;
	}
	
	@Override
	public void handle(MouseEvent event) {
		
			dragHandler.resetLastPosition();
			selectionHandler.hideSelectionRectangle();
			event.consume();
		} 		
	}