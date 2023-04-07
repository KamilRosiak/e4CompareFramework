package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature;

import org.eclipse.swt.widgets.Display;

import FeatureDiagram.ComponentFeature;
import FeatureDiagram.ConfigurationFeature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.EventBroker;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleTextInputDialog;
import featureConfiguration.FeatureConfiguration;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class FXFeatureNameLabel extends Label {
	private IFeature feature;
	
	public FXFeatureNameLabel(IFeature feature) {
		super(feature.getName());
		this.getStyleClass().add("feature");
		this.feature = feature;
		
		//Opens a Renaming dialog on double click 
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				if (this.feature instanceof ComponentFeature) { // open feature diagram tab for component features
					EventBroker.get().send(FDEventTable.OPEN_FEATURE_DIAGRAM, (ComponentFeature) this.feature);
				} else if (this.feature instanceof ConfigurationFeature) { // TODO clean up
					FeatureDiagramm diagram = ((FeatureDiagramm) feature);
	            	FeatureConfiguration config = ((ConfigurationFeature) feature).getConfigurationfeature();
	            	//services.partService.showPart(FDStringTable.FD_FEATURE_CONFIG_PART_NAME);
					EventBroker.get().send(FDEventTable.EVENT_SHOW_CONFIGURATION_VIEW, new Pair<>(diagram, config));
				} else { // show the rename dialog for normal features
					showRenameFeatureDialog(); // TODO implement
					event.consume();
				}
			}
		});
	}
	
	public void showRenameFeatureDialog() {
		EventBroker.get().send(FDEventTable.LOGGER_SELECTED_FEATURE_TO_RENAME, feature);
		FMESimpleTextInputDialog dialog = new FMESimpleTextInputDialog(FDStringTable.FD_DIALOG_MENU_RENAME_FEATURE, feature.getName());
		String newName = dialog.show(Double.valueOf(Display.getCurrent().getCursorLocation().x), Double.valueOf(Display.getCurrent().getCursorLocation().y));
		if(!newName.equals(feature.getName())) {			
			EventBroker.get().send(FDEventTable.SET_FEATURE_NAME, new Pair<>(this, newName));
		}
	}

}
