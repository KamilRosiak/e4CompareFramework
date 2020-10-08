package de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AbstractJavaFXController<ViewControllerType> {
    private ViewControllerType controller;
    private String bundleName;
    private String fxmlLocation;
    private String cssLocation ="";
    private Parent parent;
    
    public AbstractJavaFXController(String bundleName, String fxmlLocation) {
	setBundleName(bundleName);
	setFxmlLocation(fxmlLocation);
    }
    
    public AbstractJavaFXController(String bundleName, String fxmlLocation,String cssLocation) {
	this(bundleName, fxmlLocation);
	setCssLocation(cssLocation);
	
    }
    
    @PostConstruct
    public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
        FXCanvas canvans = new FXCanvas(parent, SWT.None);
        FXMLLoader<ViewControllerType> loader = new FXMLLoader<ViewControllerType>(context, bundleName,fxmlLocation);
        setParent(loader.getNode());
        if(!cssLocation.equals("")) {
            getParent().getStyleClass().addAll(getCssLocation());
        }
        canvans.setScene(new Scene(loader.getNode()));	
    }

    public ViewControllerType getController() {
	return controller;
    }

    public void setController(ViewControllerType controller) {
	this.controller = controller;
    }

    public String getBundleName() {
	return bundleName;
    }

    public void setBundleName(String bundleName) {
	this.bundleName = bundleName;
    }

    public String getFxmlLocation() {
	return fxmlLocation;
    }

    public void setFxmlLocation(String fxmlLocation) {
	this.fxmlLocation = fxmlLocation;
    }

    public Parent getParent() {
	return parent;
    }

    public void setParent(Parent parent) {
	this.parent = parent;
    }

    public String getCssLocation() {
	return cssLocation;
    }

    public void setCssLocation(String cssLocation) {
	this.cssLocation = cssLocation;
    }
}
