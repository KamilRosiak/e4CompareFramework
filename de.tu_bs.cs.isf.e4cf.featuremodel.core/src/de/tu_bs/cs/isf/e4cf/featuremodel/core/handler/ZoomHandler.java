package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;

public class ZoomHandler implements EventHandler<ScrollEvent>{
    private static final double MAX_SCALE = 4.0d;
    private static final double MIN_SCALE = 0.6d;
	final double DELTA = 1.1;
    private Node root;
	
	public ZoomHandler(Node root) {
		this.root = root;
	}
	
	@Override
	public void handle(ScrollEvent event) {
		if(event.isControlDown()) {
	        double scale = root.getScaleX(); // currently we only use Y, same value is used for X

	        if (event.getDeltaY() < 0) {
	            scale /= DELTA;
	        } else {
	        	 scale *= DELTA;
	        }

	        scale = clamp(scale, MIN_SCALE, MAX_SCALE);
		    root.setScaleX(scale);
		    root.setScaleY(scale);
		}
		
        event.consume();
		
	}
	
    public static double clamp(double value, double min, double max) {
        if(Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;
        return value;
    }
    
}
