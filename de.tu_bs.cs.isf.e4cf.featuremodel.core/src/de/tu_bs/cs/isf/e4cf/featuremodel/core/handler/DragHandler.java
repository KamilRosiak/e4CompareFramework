package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DragHandler implements EventHandler<MouseEvent> {
	private Pane root;
	
	private final double DRAG_SPEED = 1.5d;
	
	private double lastPressedX = 0;
	private double lastPressedY = 0;
	
	private double translateX = 0;
	private double translateY = 0;
	
	public DragHandler(Pane root) {
		this.root = root;
	}
	
	@Override
	public void handle(MouseEvent event) {

		if(event.isControlDown() && event.isPrimaryButtonDown()) {
					
			if (lastPressedX == 0) {
				lastPressedX = event.getX();
			}
			if (lastPressedY == 0) {
				lastPressedY = event.getY();
			}			
			
			if (lastPressedX != event.getX()) {
				translateX = (event.getX() - lastPressedX)*DRAG_SPEED;
				lastPressedX = event.getX();
			}
			
			if (lastPressedY != event.getY()) {
				translateY = (event.getY() - lastPressedY)*DRAG_SPEED;
				lastPressedY = event.getY();
			}			
			
			root.setTranslateX(root.getTranslateX() + translateX);
			root.setTranslateY(root.getTranslateY() + translateY);		
			
			event.consume();
		} 		
	}
	
	/**
	 * USED BY RESETHANDLER TO RESET LOCAL VARIABLES
	 */
	public void resetLastPosition() {
		lastPressedX = 0;
		lastPressedY = 0;
	}
	
	

}
