package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FMEditorPaneView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.constraints.ConstraintEditor;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

/**
 * This class represents the ToolBar in the FeatureDiagramEditor.
 * @author {Kamil Rosiak}
 */
public class FMEditorToolbar extends ToolBar  {
	private ServiceContainer services;
	//private FMEditorPaneView view;
	
	public FMEditorToolbar(ServiceContainer services) {
		this.services = services;
		constructUI();
	}

	/**
	 * This method initializes the ToolBar and adds all buttons to it.
	 */
	private void constructUI() {		
		/**
		 * Add Buttons to ToolBar
		 */
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_NEW_DIAGRAM, e-> {
			services.eventBroker.send(FDEventTable.NEW_FEATURE_DIAGRAM,"");
		}));
		
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_CONSTRAINTS, e -> {
			try {
				ConstraintEditor cEditor = ContextInjectionFactory.make(ConstraintEditor.class, EclipseContextFactory.create());
				cEditor.showStage();	
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		}));
		
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_SAVE,e -> {
			services.eventBroker.send(FDEventTable.SAVE_FEATURE_DIAGRAM,"");
		}));
		
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_LOAD,e -> {
			services.eventBroker.send(FDEventTable.LOAD_FEATURE_DIAGRAM_FROM_FILE, "");
		}));
		
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_FORMAT_DIAGRAM, e-> {
			services.eventBroker.send(FDEventTable.FORMAT_DIAGRAM_EVENT,"");
		}));
		
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_SHOW_CONFIG, e-> {
			services.partService.showPart(FDStringTable.FD_FEATURE_CONFIG_PART_NAME);
			//services.eventBroker.send(FDEventTable.EVENT_SHOW_CONFIGURATION_VIEW, view.currentModel());
		}));
		
		
		Button loggerButton = JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_START_LOGGER, e-> {
    		if (((Button)e.getSource()).getText().equals(FDStringTable.FD_BAR_MENU_START_LOGGER)) {
    			((Button)e.getSource()).setText(FDStringTable.FD_BAR_MENU_STOP_LOGGER);
    			services.eventBroker.send(FDEventTable.START_LOGGER_DIAGRAM_EVENT,true);	
    		} else {
    			((Button)e.getSource()).setText(FDStringTable.FD_BAR_MENU_START_LOGGER);
    			services.eventBroker.send(FDEventTable.START_LOGGER_DIAGRAM_EVENT,false);
    		}	
		});
		
		getItems().add(loggerButton);
	}
}