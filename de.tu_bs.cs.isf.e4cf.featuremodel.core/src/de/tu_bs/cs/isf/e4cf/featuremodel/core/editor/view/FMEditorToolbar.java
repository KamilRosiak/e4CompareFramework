package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FMEditorPaneController;
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
	private FMEditorPaneController controller;
	//private FMEditorPaneView view;
	
	
	public FMEditorToolbar(ServiceContainer services, FMEditorPaneController controller) {
		this.services = services;
		this.controller = controller;
		constructUI();
	}

	/**
	 * This method initializes the ToolBar and adds all buttons to it.
	 */
	private void constructUI() {		
		/**
		 * Add Buttons to ToolBar
		 */
		
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_CONSTRAINTS, e -> {
			try {
				ConstraintEditor cEditor = ContextInjectionFactory.make(ConstraintEditor.class, EclipseContextFactory.create());
				cEditor.showStage();	
			} catch (Exception exc) {
				exc.printStackTrace();
			}

		}));
		
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_FORMAT_DIAGRAM, e-> {
			this.controller.formatDiagram();
		}));
		
		getItems().add(JavaFXBuilder.createButton(FDStringTable.FD_BAR_MENU_SHOW_CONFIG, e-> {
			services.partService.showPart(FDStringTable.FD_FEATURE_CONFIG_PART_NAME);
			
			services.eventBroker.send(FDEventTable.EVENT_SHOW_CONFIGURATION_VIEW, null);
			
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
