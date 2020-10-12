package de.tu_bs.cs.isf.e4cf.compare.metric_view;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.compare.metric_view.stringtable.MetricST;
import de.tu_bs.cs.isf.e4cf.compare.metric_view.view.MetricView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class MetricViewController {
    private static final String METRIC_VIEW_FXML = "/ui/view/MetricView.fxml";
    public static final String METRIC_CSS_LOCATION = "css/metric_view.css";
    
    @PostConstruct
    public void postConstruct(Composite parent, ServiceContainer services, IEclipseContext context) throws IOException {
        FXCanvas canvans = new FXCanvas(parent, SWT.None);
        //Loading the fxml
        FXMLLoader<MetricView> loader = new FXMLLoader<MetricView>(context, MetricST.BUNDLE_NAME,METRIC_VIEW_FXML);
        //setting the css file
        Scene scene = new Scene(loader.getNode());
        scene.getStylesheets().add(METRIC_CSS_LOCATION);
        canvans.setScene(scene);	
    }
}
