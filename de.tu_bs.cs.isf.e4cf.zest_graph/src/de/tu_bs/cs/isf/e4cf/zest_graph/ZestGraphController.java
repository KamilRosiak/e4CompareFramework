package de.tu_bs.cs.isf.e4cf.zest_graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.zest.fx.ZestProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Module;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.graph.core.string_table.GraphEvents;
import de.tu_bs.cs.isf.e4cf.zest_graph.template.AbstractZestGraph;
import de.tu_bs.cs.isf.e4cf.zest_graph.template.ZestGraphModule;
import de.tu_bs.cs.isf.e4cf.zest_graph.util.ZestGraphBuilder;
import javafx.embed.swt.FXCanvas;

public class ZestGraphController extends AbstractZestGraph {
	
	
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services) {
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		canvas.setScene(start());
	}
	
	public static Graph createDefaultGraph() {
		// create nodes "0" to "9"
		List<org.eclipse.gef.graph.Node> nodes = new ArrayList<>();
		nodes.addAll(Arrays.asList(ZestGraphBuilder.n("Test", "", "Hallo"), 
				ZestGraphBuilder.n("1", "", "one"), ZestGraphBuilder.n("2", "", "two"), ZestGraphBuilder.n("3", "", "three"),
				ZestGraphBuilder.n("4", "", "four"), ZestGraphBuilder.n("5", "", "five"), ZestGraphBuilder.n("6", "", "six"), ZestGraphBuilder.n("7", "", "seven"), ZestGraphBuilder.n("8", "", "eight"),
				ZestGraphBuilder.n("9", "", "nine")));



		// create some edges between those nodes
		List<Edge> edges = new ArrayList<>();
		edges.addAll(Arrays.asList(ZestGraphBuilder.e(nodes.get(0), nodes.get(9)), ZestGraphBuilder.e(nodes.get(1), nodes.get(8)),
				ZestGraphBuilder.e(nodes.get(2), nodes.get(7)), ZestGraphBuilder.e(nodes.get(3), nodes.get(6)), ZestGraphBuilder.e(nodes.get(4), nodes.get(5)),
				ZestGraphBuilder.e(nodes.get(0), nodes.get(4)), ZestGraphBuilder.e(nodes.get(1), nodes.get(6)), ZestGraphBuilder.e(nodes.get(2), nodes.get(8)),
				ZestGraphBuilder.e(nodes.get(3), nodes.get(5)), ZestGraphBuilder.e(nodes.get(4), nodes.get(7)), ZestGraphBuilder.e(nodes.get(5), nodes.get(1))));

		// directed connections
		HashMap<String, Object> attrs = new HashMap<>();
		
		attrs.put(ZestProperties.LAYOUT_ALGORITHM__G, new SpringLayoutAlgorithm());
		return new Graph(attrs, nodes, edges);
	}


	@Override
	protected Graph createGraph() {
		return createDefaultGraph();
	}

	@Override
	protected Module createModule() {
		return new ZestGraphModule();
	}

	@Optional
	@Inject 
	public void loadGraph(@UIEventTopic(GraphEvents.LOAD_GRAPH_MODEL) Graph model) {
        IViewer viewer = getContentViewer();
        viewer.getContents().clear();
        viewer.getContents().setAll(model);
	}
}
