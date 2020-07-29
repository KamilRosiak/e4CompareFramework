package de.tu_bs.cs.isf.e4cf.featuremodel.core.view;

import org.eclipse.e4.core.services.events.IEventBroker;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramFactory;
import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.GraphicalFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FeatureModelEditorContextMenu extends ContextMenu {
	private IEventBroker eventBroker;
	
	public FeatureModelEditorContextMenu(IEventBroker eventBroker, FeatureDiagramm diagram) {
		createControl();
		this.eventBroker = eventBroker;
	}
	
	public void createControl() {
		this.getItems().add(addFeatureMenuItem());
	}
	
	
	private MenuItem addFeatureMenuItem() {
		MenuItem item = new MenuItem(FDStringTable.FD_CONTEXT_MENU_ADD_FEATURE);
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Feature feature = FeatureDiagramFactory.eINSTANCE.createFeature();
            	feature.setName("Feature");
            	GraphicalFeature graphicalFeature = FeatureDiagramFactory.eINSTANCE.createGraphicalFeature();
            	feature.setGraphicalfeature(graphicalFeature);
            	feature.setMandatory(false);
            	
            	eventBroker.send(FDEventTable.ADD_FEATURE_EVENT, feature);
            	event.consume();
            }
        });

		return item;
	}
}
