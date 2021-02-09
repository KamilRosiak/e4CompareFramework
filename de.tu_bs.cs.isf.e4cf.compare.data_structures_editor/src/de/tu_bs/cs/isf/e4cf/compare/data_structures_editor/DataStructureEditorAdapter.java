
package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.VisualizeTreeView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * Class to load the FXML File for TreeView
 * 
 * @author Team05
 *
 */

public class DataStructureEditorAdapter {

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<VisualizeTreeView> loader = new FXMLLoader<VisualizeTreeView>(context,
				DataStructuresEditorST.BUNDLE_NAME, DataStructuresEditorST.TREE_VIEW_FXML);

		Scene scene = new Scene(loader.getNode());
		canvans.setScene(scene);
	}
}
