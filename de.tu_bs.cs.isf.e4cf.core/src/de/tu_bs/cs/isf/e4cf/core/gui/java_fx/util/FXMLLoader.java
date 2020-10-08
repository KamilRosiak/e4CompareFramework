package de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util;

import java.io.IOException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.fx.ui.di.InjectingFXMLLoader;

import javafx.scene.Parent;

/**
 * Helper class that supports loading of JavaFX fxml files.
 */
public class FXMLLoader<ControllerType> {
    private ControllerType controller;
    private Parent node;
    
    /**
     * 
     * @param context the current eclipse context injectable in any class
     * @param bundleName the bundle name of the bundle where the fxml is located
     * @param relativeLocation relative path to the fxml in the given bundle
     */
    public FXMLLoader(IEclipseContext context,String bundleName, String relativeLocation) {
	init(context,bundleName, relativeLocation);
    }
    
    /**
     * 
     * @param context
     * @param bundleName
     * @param relativeLocation
     */
    private void init(IEclipseContext context, String bundleName, String relativeLocation) {
	InjectingFXMLLoader<Parent> iFXMLLoader = InjectingFXMLLoader.create(context, Platform.getBundle(bundleName), relativeLocation);
	InjectingFXMLLoader.
	Data<Parent, Object> fxmlData;
	try {
	    fxmlData = iFXMLLoader.loadWithController();
	    setController((ControllerType) fxmlData.getController());
	    setNode(fxmlData.getNode());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public ControllerType getController() {
	return controller;
    }

    public void setController(ControllerType controller) {
	this.controller = controller;
    }

    public Parent getNode() {
	return node;
    }

    public void setNode(Parent node) {
	this.node = node;
    }
}
