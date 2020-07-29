package de.tu_bs.cs.isf.e4cf.zest_graph.template;

import java.util.Collections;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.zest.fx.ZestFxModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import javafx.application.Platform;
import javafx.scene.Scene;

public abstract class AbstractZestGraph {
		protected IDomain domain;
		protected IViewer viewer;
		protected Graph graph;


		protected abstract Graph createGraph();
		
		protected IViewer getContentViewer() {
			return viewer;
		}
	
		public Scene start() {
			// create graph
			graph = createGraph();
			// configure application
			Injector injector = Guice.createInjector(createModule());
			domain = injector.getInstance(IDomain.class);
			viewer = domain.getAdapter(
					AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
			Scene scene = (createScene(viewer));
			// activate domain only after viewers have been hooked
			domain.activate();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					viewer.getContents().setAll(Collections.singletonList(graph));
				}
			});
			
			return scene;
		}

		protected Scene createScene(IViewer viewer) {
			return new Scene(((IViewer) viewer).getCanvas());
		}

		protected Module createModule() {
			return new ZestFxModule();
		}
}
