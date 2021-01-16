package de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class FXMLUtil {
    
    /**
     * This method loads a fxml file and returns the controller class
     */
    public static<ControllerClass> ControllerClass loadFXML(Composite parent, IEclipseContext context, String Bundle, String fxmlLocation) {
        FXCanvas canvans = new FXCanvas(parent, SWT.None);
        FXMLLoader<ControllerClass> loader = new FXMLLoader<ControllerClass>(context, Bundle,fxmlLocation);
        Scene scene = new Scene(loader.getNode());
        canvans.setScene(scene);
        return loader.getController();
    }

}
