
package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.VisualizeTreeView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * 
 * @author Team05
 *
 */
public class DataStructureEditorController {

	@Inject
	public DataStructureEditorController(Composite parent, ServiceContainer services, IEclipseContext context) {
		postConstruct(parent, services, context);
	}

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<VisualizeTreeView> loader = new FXMLLoader<VisualizeTreeView>(context,
				DataStructuresEditorST.BUNDLE_NAME, DataStructuresEditorST.TREE_VIEW_FXML);

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
