package de.tu_bs.cs.isf.e4cf.featuremodel.core.handler;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionAreaHandler implements EventHandler<MouseEvent>{
	
	private Rectangle selectionRectangle;
	private PrimaryMouseButtonClickedHandler mouseClickHandler;
	private List<FXGraphicalFeature> features;
	private double xStartPosition;
	private double yStartPosition;
	private double xPosMax;
	private double yPosMax;
	private Pane pane;
	private ServiceContainer services;
    
    public SelectionAreaHandler(Pane root, List<FXGraphicalFeature> featureList, Rectangle selectionRectangle, 
    		PrimaryMouseButtonClickedHandler mouseClickHandler, ServiceContainer services) {
		this.selectionRectangle = selectionRectangle;
		this.mouseClickHandler = mouseClickHandler;
		this.features = featureList;
		this.pane = root;
		this.services = services;
		xStartPosition = 0;
		yStartPosition = 0;
		xPosMax = 0;
		yPosMax = 0;
	}

    
    @Override
	public void handle(MouseEvent event) {
		
		if(!event.isControlDown() && event.isPrimaryButtonDown()) {
			selectionRectangle.setDisable(false);
			selectionRectangle.setStroke(Color.BLACK);
			
			xStartPosition = Math.min(event.getX(), mouseClickHandler.getXPosition());
			yStartPosition = Math.min(event.getY(), mouseClickHandler.getYPosition());
			
			xPosMax = Math.max(event.getX(), mouseClickHandler.getXPosition());
			yPosMax = Math.max(event.getY(), mouseClickHandler.getYPosition());
			
			selectionRectangle.setX(xStartPosition - (pane.getTranslateX()));
			selectionRectangle.setY(yStartPosition - (pane.getTranslateY()));
			
			selectionRectangle.setHeight(Math.abs(yPosMax - yStartPosition));
			selectionRectangle.setWidth(Math.abs(xPosMax - xStartPosition));
		}
		
		event.consume();
		
	}

	/**
	 * Only when the selection rectangle is drawn and the ResetHandler
	 * jumps to this method, the selected items are retrieved.
	 * Else, the ResetJumps to this location but the selection rectangle
	 * has not been drawn ( due to drag event ) 
	 */
	public void hideSelectionRectangle() {
		
		if (!selectionRectangle.isDisable()) {
			pane.getBaselineOffset();
			pane.getBoundsInParent();
			getSelectedItems();
		}
		
		selectionRectangle.setDisable(true);
		selectionRectangle.setStroke(Color.TRANSPARENT);
	}

	/**
	 * Retrieves the features within the selection rectangle. 
	 * Hence, if a features x- and y coordinates are within the boundaries of the
	 * selection rectangle, they are added to a list, which is then returned to the 
	 * FeatureModelHandler
	 */
	private void getSelectedItems() {
		List<FXGraphicalFeature> selectedFeatures = new ArrayList<FXGraphicalFeature>();
		for (FXGraphicalFeature feature : features) {
			if (((feature.getXPos().get()+pane.getTranslateX()) > xStartPosition) && ((feature.getYPos().get()+pane.getTranslateY()) > yStartPosition)
					&& ((feature.getXPos().get()+pane.getTranslateX()) < xPosMax) && ((feature.getYPos().get()+pane.getTranslateY()) < yPosMax)) {
				selectedFeatures.add(feature);
			}
		}
		for (FXGraphicalFeature feature : selectedFeatures) {
			services.eventBroker.send(FDEventTable.SET_SELECTED_FEATURE, feature);
		}
	}
}
