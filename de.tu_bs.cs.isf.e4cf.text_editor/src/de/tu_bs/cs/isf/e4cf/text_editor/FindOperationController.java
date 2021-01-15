package de.tu_bs.cs.isf.e4cf.text_editor;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;
import de.tu_bs.cs.isf.e4cf.text_editor.view.FindOperation;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class FindOperationController {
	/**
	 * Creates the window to search terms in from the given .fxml-file.
	 * 
	 * @param parent   anchors the canvas into the program
	 * @param services
	 * @param context  is needed to load the .fxml
	 * @throws IOException
	 * @author Soeren Christmann, Cedric Kapalla
	 */
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<FindOperation> loader = new FXMLLoader<FindOperation>(context, EditorST.BUNDLE_NAME,
				EditorST.FIND_OPERATION_FXML);

		Scene scene = new Scene(loader.getNode());
		loader.getController().setScene(scene);
		// scene.getStylesheets().add(TEXT_EDITOR_CSS_LOCATION);
		canvans.setScene(scene);
	}
	
}
