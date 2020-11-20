package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

/**
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
*/

public class TreeView {
	
	private ServiceContainer services;
	
	public TreeView(ServiceContainer services) {
		this.services = services;
	}
	/**
	private void createProperties() {
	
		minWidthProperty().bind(view.getRootPane().widthProperty());
	
		getItems().add(JavaFXBuilder.createButton("TestButton", e-> {
		services.partService.showPart(FDStringTable.FD_FEATURE_CONFIG_PART_NAME);
		services.eventBroker.send(FDEventTable.EVENT_CREATE_CONFIGURATION, view.getCurrentModel());
		}));
	
	}
	*/
	public void test() {
		return;
	}
	
	protected void test2() {
		return;
	}
	
	private void test3() {
		return;
	}
}
