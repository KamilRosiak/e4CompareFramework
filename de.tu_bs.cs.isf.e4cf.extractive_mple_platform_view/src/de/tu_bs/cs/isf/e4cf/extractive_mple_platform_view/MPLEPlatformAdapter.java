package de.tu_bs.cs.isf.e4cf.extractive_mple_platform_view;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * This class is a adapter which draws the JavaFX UI elements on the SWT
 * composite.
 * 
 * @author Team05, Kamil Rosiak
 *
 */

public class MPLEPlatformAdapter {
	MPLEPlatformController controller;

	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) {
		try {
			FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MPLEPlatform.fxml"));
			Scene scene = null;
			try {
				scene = new Scene(loader.load());
				controller = loader.getController();
				ContextInjectionFactory.inject(controller, context);
			} catch (IOException e) {
				e.printStackTrace();
			}
			canvas.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
