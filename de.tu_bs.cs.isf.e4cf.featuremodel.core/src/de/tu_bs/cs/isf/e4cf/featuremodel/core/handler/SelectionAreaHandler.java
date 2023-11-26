package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionAreaHandler implements EventHandler<MouseEvent> {
	private double xPressPosition = -1;
	private double yPressPosition = -1;

	private double xStartPosition = -1;
	private double yStartPosition = -1;
	private double xPosMax;
	private double yPosMax;

	private Rectangle selectionRectangle;
	private Pane root;

	public SelectionAreaHandler(Rectangle selectionRectangle, Pane rootPane) {
		this.selectionRectangle = selectionRectangle;
		this.root = rootPane;
	}

	@Override
	public void handle(MouseEvent event) {

		if (event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			if (event.isPrimaryButtonDown()) {
				xPressPosition = event.getX();
				yPressPosition = event.getY();
			}
		} else if (event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			if (!event.isControlDown() && event.isPrimaryButtonDown()) {
				selectionRectangle.setDisable(false);
				selectionRectangle.setStroke(Color.BLACK);

				xStartPosition = Math.min(event.getX(), xPressPosition);
				yStartPosition = Math.min(event.getY(), yPressPosition);

				xPosMax = Math.max(event.getX(), xPressPosition);
				yPosMax = Math.max(event.getY(), yPressPosition);

				selectionRectangle.setX(xStartPosition);
				selectionRectangle.setY(yStartPosition);

				selectionRectangle.setHeight(Math.abs(yPosMax - yStartPosition));
				selectionRectangle.setWidth(Math.abs(xPosMax - xStartPosition));
			}
		} else if (event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
			if (xStartPosition != -1 && yStartPosition != -1) {
				hideSelectionRectangle();
			}
		}
		event.consume();

	}

	/**
	 * Only when the selection rectangle is drawn and the ResetHandler jumps to this
	 * method, the selected items are retrieved. Else, the ResetJumps to this
	 * location but the selection rectangle has not been drawn ( due to drag event )
	 */
	public void hideSelectionRectangle() {
		selectionRectangle.setDisable(true);
		selectionRectangle.setStroke(Color.TRANSPARENT);
		getSelectedItems();
		xStartPosition = -1;
		yStartPosition = -1;
	}

	/**
	 * Retrieves the features within the selection rectangle. 
	 * Hence, if a features x- and y coordinates are within the boundaries of the
	 * selection rectangle, they are added to a list, which is then returned to the 
	 * FeatureModelHandler
	 */
	private void getSelectedItems() {
		List<FXGraphicalFeature> selectedFeatures = new ArrayList<FXGraphicalFeature>();
		for (Node node : root.getChildren()) {
			if (node instanceof FXGraphicalFeature) {
				FXGraphicalFeature fx = (FXGraphicalFeature) node;
				if (inSelectionBounds(fx)) {
					selectedFeatures.add(fx);
				}
			}
		}
		for (FXGraphicalFeature fx : selectedFeatures) {
			fx.toggleSelected();
		}
	}

	private boolean inSelectionBounds(FXGraphicalFeature fx) {
		// TODO add zoom level to calculation 
		return 	((fx.xPos.get()+root.getLayoutX()) > xStartPosition) && 
				((fx.yPos.get()+root.getLayoutY()) > yStartPosition) && 
				((fx.xPos.get()+root.getLayoutX()) < xPosMax) && 
				((fx.yPos.get()+root.getLayoutY()) < yPosMax);
	}
}
