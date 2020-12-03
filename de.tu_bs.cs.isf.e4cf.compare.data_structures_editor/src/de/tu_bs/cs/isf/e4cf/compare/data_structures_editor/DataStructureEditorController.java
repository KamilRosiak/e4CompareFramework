
package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.VisualizeTreeView;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.editor.src.view.TreeViewController;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class DataStructureEditorController {
	
	public ServiceContainer services;

	private static final String TREE_VIEW_FXML = "ui/view/VisualizeTreeView.fxml";
	public static final String COMPARATOR_VIEW_CSS_LOCATION = "css/comparator_view.css";
	public static final String BUNDLE_NAME = "de.tu_bs.cs.isf.e4cf.compare.data_structures_editor";
	
	@Inject
	public DataStructureEditorController(Composite parent, ServiceContainer services, IEclipseContext context) {
		this.services = services;
		postConstruct(parent, services, context);
	}
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
<<<<<<< HEAD
		FXMLLoader<TreeView> loader = new FXMLLoader<TreeView>(context, BUNDLE_NAME, TREE_VIEW_FXML);
		
=======
		FXMLLoader<VisualizeTreeView> loader = new FXMLLoader<VisualizeTreeView>(context, BUNDLE_NAME, TREE_VIEW_FXML);

>>>>>>> 9a13f5222b7441274167fa8d86af17fac8f21447
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