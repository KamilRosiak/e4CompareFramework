
package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * This class is a adapter which draws the JavaFX UI elements on the SWT
 * composite.
 * 
 * @author Team05, Kamil Rosiak
 *
 */

public class DSEditorAdapter {

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<DSEditorController> loader = new FXMLLoader<DSEditorController>(context,
				DSEditorST.BUNDLE_NAME, DSEditorST.TREE_VIEW_FXML);

		Scene scene = new Scene(loader.getNode());
		canvans.setScene(scene);
	}
}
