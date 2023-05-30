package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ResetHandler implements EventHandler<MouseEvent> {
	private SelectionAreaHandler selectionHandler;
	
	public ResetHandler(SelectionAreaHandler selectionHandler) {
		this.selectionHandler = selectionHandler;
	}
	
	@Override
	public void handle(MouseEvent event) {
		
			selectionHandler.hideSelectionRectangle();
			event.consume();
		} 		
	}