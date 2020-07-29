package de.tu_bs.cs.isf.e4cf.graph.core;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.mvc.fx.domain.HistoricizingDomain;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Guice;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.SimpleGraphMapModule;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import de.tu_bs.cs.isf.e4cf.graph.core.string_table.GraphEvents;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class GraphController {

	private HistoricizingDomain domain;
	private ServiceContainer services;
	
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services) {
		this.services = services;
		
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		canvas.setScene(start());
	}
	
    public Scene start() {
        SimpleGraphMapModule module = new SimpleGraphMapModule();
        // create domain using guice
        this.domain = (HistoricizingDomain) Guice.createInjector(module).getInstance(IDomain.class);
        // create viewers
        Scene scene = hookViewers();
        // activate domain
        domain.activate();
        // load contents
        populateViewerContents();
        return scene;
    }

    /**
     * Returns the content viewer of the domain
     *
     * @return
     */
    private IViewer getContentViewer() {
        return domain.getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
    }

    /**
     * Creating JavaFX widgets and set them to the stage.
     */
    private Scene hookViewers() {
        Scene scene = new Scene(getContentViewer().getCanvas());
        return scene;
    }

    /**
     * Creates the example mind map and sets it as content to the viewer.
     */
    private void populateViewerContents() {
        SimpleGraphExampleFactory fac = new SimpleGraphExampleFactory();

        SimpleGraph graph = fac.createComplexExample();

        IViewer viewer = getContentViewer();        
        viewer.setAdapter(services);
        viewer.getContents().setAll(graph);
    }
    
	@Optional
	@Inject 
	public void loadGraph(@UIEventTopic(GraphEvents.LOAD_GRAPH_MODEL) SimpleGraph model) {
        IViewer viewer = getContentViewer();
        viewer.getContents().clear();
        viewer.getContents().setAll(model);
	}

}
