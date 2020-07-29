package de.tu_bs.cs.isf.e4cf.zest_graph.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Edge.Builder;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.algorithms.HorizontalShiftAlgorithm;
import org.eclipse.gef.zest.fx.ZestProperties;

import de.tu_bs.cs.isf.familymining.ppu_iec.core.compare.solution.pou.POUCompareContainer;
import javafx.scene.shape.Polygon;

public class ZestGraphBuilder {
	private static int id = 0;
	protected static final String ID = ZestProperties.CSS_ID__NE;
	protected static final String LABEL = ZestProperties.LABEL__NE;
	protected static final String CSS_CLASS = ZestProperties.CSS_CLASS__NE;
	protected static final String LAYOUT_IRRELEVANT = ZestProperties.LAYOUT_IRRELEVANT__NE;
	
	public static Graph transformModelToGraph(List<POUCompareContainer> edgeList) {
		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();
		Map<String,Node> nodeMap = new HashMap<String,Node>();
		
		// directed connections and layout
		HashMap<String, Object> attrs = new HashMap<>();
		attrs.put(ZestProperties.LAYOUT_ALGORITHM__G, new HorizontalShiftAlgorithm());
		
		for(POUCompareContainer pouContainer : edgeList) {
			Node first;
			Node second;
			//create a node for the fist and second POU
			if(nodeMap.containsKey(pouContainer.getFirst().getIdentifier())) {
				first = nodeMap.get(pouContainer.getFirst().getIdentifier());
			} else {
				first = ZestGraphBuilder.n(pouContainer.getFirst().getIdentifier(),"","");
				
				nodeMap.put(pouContainer.getFirst().getIdentifier(), first);
				nodes.add(first);			
			}
			
			if(nodeMap.containsKey(pouContainer.getSecond().getIdentifier())) {
				second = nodeMap.get(pouContainer.getSecond().getIdentifier());
			} else {
				second = ZestGraphBuilder.n(pouContainer.getSecond().getIdentifier(),"","");
				nodeMap.put(pouContainer.getSecond().getIdentifier(), second);
				nodes.add(second);
			}
			if(first != second) {
				//connect nodes 
				Edge edge = ZestGraphBuilder.e(first, second);
				
				edges.add(edge);
				edge = ZestGraphBuilder.e(second, first);

				edges.add(edge);

			}
		}
		Graph graph = new Graph(attrs, nodes, edges);
		return graph;
	}
	
	
	protected static String genId() {
		return Integer.toString(id++);
	}
	
	public static Edge e(Node n, Node m, Object... attr) {
		String label = (String) n.attributesProperty().get(LABEL)
				+ (String) m.attributesProperty().get(LABEL);
		Builder builder = new Edge.Builder(n, m).attr(LABEL, label).attr(ID,
				genId());
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildEdge();
	}

	public static Edge e(Graph graph, Node n, Node m, Object... attr) {
		Edge edge = e(n, m, attr);
		graph.getEdges().add(edge);
		return edge;
	}

	public static Node n(Object... attr) {
		Node.Builder builder = new Node.Builder();
		String id = genId();
		builder.attr(ID, id).attr(LABEL, id);
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildNode();
	}
	
	public static Edge e(Node n, Node m) {
		return ZestGraphBuilder.e(n, m, ZestProperties.TARGET_DECORATION__E, new Polygon(0, 0, 10, 3, 10, -3),
				ZestProperties.TARGET_DECORATION_CSS_STYLE__E, "-fx-fill: white;");
	}

	public static Node n(String label, String idPrefix, String tooltip) {
		return ZestGraphBuilder.n(ZestProperties.LABEL__NE, label, ZestProperties.CSS_ID__NE, idPrefix + label,
				ZestProperties.TOOLTIP__N, tooltip);
	}
	public static Node n(Graph graph, Object... attr) {
		Node node = n(attr);
		graph.getNodes().add(node);
		return node;
	}
}
