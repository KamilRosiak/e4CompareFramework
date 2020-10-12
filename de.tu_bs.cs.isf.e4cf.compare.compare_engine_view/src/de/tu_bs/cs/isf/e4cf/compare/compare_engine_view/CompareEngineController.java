 
package de.tu_bs.cs.isf.e4cf.compare.compare_engine_view;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.string_table.CompareST;
import de.tu_bs.cs.isf.e4cf.compare.compare_engine_view.view.CompareEngineView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * This class initializes the fxml ui located at the location that is stored in COMPARE_ENGINE_FXML 
 * @author Kamil Rosiak
 *
 */
public class CompareEngineController {
    private static final String COMPARE_ENGINE_FXML = "/ui/view/CompareEngineView.fxml";
    public static final String COMPARE_ENGINE_CSS_LOCATION ="css/compare_engine.css";
    
    
    @PostConstruct
    public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
        FXCanvas canvans = new FXCanvas(parent, SWT.None);
        FXMLLoader<CompareEngineView> loader = new FXMLLoader<CompareEngineView>(context, CompareST.BUNDLE_NAME,COMPARE_ENGINE_FXML);
        
        Scene scene = new Scene(loader.getNode());
        scene.getStylesheets().add(COMPARE_ENGINE_CSS_LOCATION);
        canvans.setScene(scene);	
    }
}