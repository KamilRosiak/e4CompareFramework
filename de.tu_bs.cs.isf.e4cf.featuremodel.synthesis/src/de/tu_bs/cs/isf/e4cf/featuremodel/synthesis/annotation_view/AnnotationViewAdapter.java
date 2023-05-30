package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class AnnotationViewAdapter {

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) {
		try {
			FXCanvas canvans = new FXCanvas(parent, SWT.None);
			FXMLLoader<AnnotationViewController> loader = new FXMLLoader<>(context, AnnotationST.BUNDLE_NAME,
					AnnotationST.VIEW_FXML);

			Scene scene = new Scene(loader.getNode());
			canvans.setScene(scene);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
