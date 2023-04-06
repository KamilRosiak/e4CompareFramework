package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DragHandler implements EventHandler<MouseEvent> {
	private Pane root;
	
	private final double DRAG_SPEED = 1.0d;
	
	private double lastX = -1;
	private double lastY = -1;
	
	private double translateX = 0;
	private double translateY = 0;
	
	public DragHandler(Pane root) {
		this.root = root;
	}
	
	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			if(event.isControlDown() && event.isPrimaryButtonDown()) {				
				shift(event);
			} 
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			reset();
		}
		event.consume();
	}
	
	private void shift(MouseEvent event) {		
		if (lastX == -1) {
			lastX = event.getX();
		}
		if (lastY == -1) {
			lastY = event.getY();
		}			
		
		translateX = (event.getX() - lastX) * DRAG_SPEED;
		lastX = event.getX(); 
		translateY = (event.getY() - lastY) * DRAG_SPEED;
		lastY = event.getY();		
		
		root.setLayoutX(root.getLayoutX() + translateX);
		root.setLayoutY(root.getLayoutY() + translateY);
	}
	
	private void reset() {
		lastX = -1;
		lastY = -1;
	}
	
	

}
