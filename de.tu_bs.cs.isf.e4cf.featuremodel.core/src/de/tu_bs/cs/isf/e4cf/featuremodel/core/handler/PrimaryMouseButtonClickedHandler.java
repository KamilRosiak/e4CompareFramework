package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class PrimaryMouseButtonClickedHandler implements EventHandler<MouseEvent> {

	private static double xPosition = 0;
	private static double yPosition = 0;
	private ServiceContainer services;
	
	public PrimaryMouseButtonClickedHandler(ServiceContainer services) {
		this.services = services;
	}
	
	@Override
	public void handle(MouseEvent event) {
		if (event.isPrimaryButtonDown()) {
			xPosition = event.getX();
			yPosition = event.getY();
			if (!event.isShiftDown() && event.isAltDown()) {
				services.eventBroker.send(FDEventTable.RESET_SELECTED_FEATURES, "");			
			}
		}
		event.consume();	
	}
	
	public double getXPosition() {
		return xPosition;
	}
	
	public double getYPosition() {
		return yPosition;
	}	
}
