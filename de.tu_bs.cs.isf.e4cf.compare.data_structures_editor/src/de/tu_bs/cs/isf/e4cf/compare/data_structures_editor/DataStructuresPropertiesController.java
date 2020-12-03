package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;
 

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.PropertiesView;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.VisualizeTreeView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class DataStructuresPropertiesController {
	private static final String PROPERTIES_VIEW_FXML = "ui/view/Properties.fxml";
	public static final String COMPARATOR_VIEW_CSS_LOCATION = "css/comparator_view.css";
	public static final String BUNDLE_NAME = "de.tu_bs.cs.isf.e4cf.compare.data_structures_editor";
	
	@Inject
	public DataStructuresPropertiesController(Composite parent, ServiceContainer services, IEclipseContext context) {
		System.out.println("Ich bin properties constructir");
		postConstruct(parent, services, context);
	
	}
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<PropertiesView> loader = new FXMLLoader<PropertiesView>(context, BUNDLE_NAME, PROPERTIES_VIEW_FXML);

		Scene scene = new Scene(loader.getNode());
		canvans.setScene(scene);

	}

	@PreDestroy
	public void preDestroy() {

	}

	@Focus
	public void onFocus() {
	
	}

	@Persist
	public void save() {

	}
	
	
	
	
}