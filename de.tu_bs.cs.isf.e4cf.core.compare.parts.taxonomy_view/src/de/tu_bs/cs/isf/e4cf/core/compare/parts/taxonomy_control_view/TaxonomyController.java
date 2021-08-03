package de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view;

 

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.string_table.TaxonomyST;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.view.TaxonomyControlView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * This class loads and initializes package fxml in TAXONOMY_CONTROL_VIEW_FXML 
 * @author developer_olan
 *
 */

public class TaxonomyController {
	private static final String TAXONOMY_CONTROL_VIEW_FXML = "/ui/view/TaxonomyControlView.fxml";
    public static final String TAXONOMY_CONTROL_VIEW_CSS ="/css/taxonomy_control_view.css";
    
	
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
		FXCanvas canvas = new FXCanvas(parent, SWT.None);
        FXMLLoader<TaxonomyControlView> loader = new FXMLLoader<TaxonomyControlView>(context, TaxonomyST.BUNDLE_NAME, TAXONOMY_CONTROL_VIEW_FXML);
        
        Scene scene = new Scene(loader.getNode());
        scene.getStylesheets().add(TAXONOMY_CONTROL_VIEW_CSS);
        canvas.setScene(scene);	
	}
	
	
}