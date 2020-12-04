
package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.VisualizeTreeView;
//import de.tu_bs.cs.isf.e4cf.compare.data_structures.editor.src.view.TreeViewController;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;

public class DataStructureEditorController {

	private static final String TREE_VIEW_FXML = "ui/view/VisualizeTreeView.fxml";
	public static final String COMPARATOR_VIEW_CSS_LOCATION = "css/comparator_view.css";
	public static final String BUNDLE_NAME = "de.tu_bs.cs.isf.e4cf.compare.data_structures_editor";

	private Tree currentTree;
	private TreeView<?> view;

	@Inject
	public DataStructureEditorController(Composite parent, ServiceContainer services, IEclipseContext context) {
		postConstruct(parent, services, context);
	}

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<VisualizeTreeView> loader = new FXMLLoader<VisualizeTreeView>(context, BUNDLE_NAME, TREE_VIEW_FXML);

		Scene scene = new Scene(loader.getNode());
		canvans.setScene(scene);

	}

//	@Optional
//	@Inject
//	public void showConstraints(@UIEventTopic("SHOW_CONSTRAINT_EVENT") String event) {
//		updateView();
//	}
//
//	@Focus
//	public void updateView() {
//		try {
//			TreeViewController dsec = ContextInjectionFactory.make(TreeViewController.class,
//					EclipseContextFactory.create());
//			currentTree = dsec.getTree();
//			view.set(dsec.getCurrentView());
//		} catch (Exception e) {
//			RCPMessageProvider.errorMessage("Error", "No Loaded");
//		}
//
//	}

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