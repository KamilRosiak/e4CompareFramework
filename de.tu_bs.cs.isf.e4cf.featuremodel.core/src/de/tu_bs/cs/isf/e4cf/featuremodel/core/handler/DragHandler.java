package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import java.util.function.Predicate;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DragHandler implements EventHandler<MouseEvent> {
	private final Node node;
	private final  Predicate<MouseEvent> keyActivation;
	private final double dragFactor;
	
	private static final double DRAG_SPEED_DEFAULT = 1.0d;
	
	private double lastX = -1;
	private double lastY = -1;
	
	private double deltaX = 0;
	private double deltaY = 0;
	
	public DragHandler(Node node, Predicate<MouseEvent> keyActivation, double dragFactor) {
		this.node = node;
		this.keyActivation = keyActivation;
		this.dragFactor = dragFactor;
	}
	
	public DragHandler(Node node, Predicate<MouseEvent> keyActivation) {
		this(node, keyActivation, DRAG_SPEED_DEFAULT);
	}
	
	@Override
	public void handle(MouseEvent event) {
		event.consume();
		if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			if(keyActivation.test(event)) {				
				shift(event);
			} 
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			reset();
		}
	}
	
	private void shift(MouseEvent event) {		
		if (lastX == -1) {
			lastX = event.getX();
		}
		if (lastY == -1) {
			lastY = event.getY();
		}			
		
		deltaX = (event.getX() - lastX) * dragFactor;
		lastX = event.getX(); 
		deltaY = (event.getY() - lastY) * dragFactor;
		lastY = event.getY();	
		
		double newX = node.getLayoutX() + deltaX;
		double newY = node.getLayoutY() + deltaY;
		
		node.relocate(newX, newY);
	}
	
	private void reset() {
		lastX = -1;
		lastY = -1;
	}
	
	

}
