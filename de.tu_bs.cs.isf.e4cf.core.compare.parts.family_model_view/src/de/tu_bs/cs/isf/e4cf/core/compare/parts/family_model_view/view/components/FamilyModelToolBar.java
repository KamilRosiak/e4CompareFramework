package de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.components;

import java.nio.file.Path;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.stringtable.FaMoStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.util.FaMoSerializer;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.family_model_view.view.FamilyModelView;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.scene.control.ToolBar;

/**
 * This class represents the bottom toolbar that allows you to select a metric and a matcher.
 * @author Kamil Rosiak
 *
 */
public class FamilyModelToolBar extends ToolBar {
	private ServiceContainer services;
	private FamilyModelView view;

	
	
	public FamilyModelToolBar(ServiceContainer services, FamilyModelView view) {
		this.setServices(services);
		this.view = view;
		initControl();
	}

	/**
	 * This method initializes the ToolBar and adds all buttons to it.
	 */
	private void initControl() {
		minWidthProperty().bind(view.getRootPane().widthProperty());
		
		/**
		 * Add Buttons to ToolBar
		 */
		//Stores the current 
		getItems().add(JavaFXBuilder.createButton("Save Family Model", e-> {
			FileTreeElement root = services.workspaceFileSystem.getWorkspaceDirectory();
			Path rootPath = FileHandlingUtility.getPath(root);
			Path projectPath = rootPath.resolve("");
			FaMoSerializer.encode(view.getFamilyModel(), projectPath.resolve("").toUri()+view.getFamilyModel().getFaMoName(),FaMoStringTable.FILE_ENDING_FAMO);
		})); 
	}

	public ServiceContainer getServices() {
		return services;
	}

	public void setServices(ServiceContainer services) {
		this.services = services;
	}
		
}
