package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 * Translates the position of a node on key events.
 * @author {Kamil Rosiak}
 *
 */
public class KeyTranslateHandler implements EventHandler<KeyEvent> {
	Node root;
	double offset;
	public KeyTranslateHandler(Node root, double offset) {
		this.root = root;
		this.offset = offset;
	}
	
	@Override
	public void handle(KeyEvent event) {
		 if(event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT ) {
			 translateRoot(-offset, 0);
		 } else if(event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP ) {
			 translateRoot(0, -offset);
		 } else if(event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT ) {
			 translateRoot(offset, 0);
		 } else if(event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN ) {
			 translateRoot(0, offset);
		 }
		
	}
	
	 private void translateRoot(double x, double y) {
		 root.setTranslateX(root.getTranslateX() + x);
		 root.setTranslateY(root.getTranslateY() + y);
	 }
}
