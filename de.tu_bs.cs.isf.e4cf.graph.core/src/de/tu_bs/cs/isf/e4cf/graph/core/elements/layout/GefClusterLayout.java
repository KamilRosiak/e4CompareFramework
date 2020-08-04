package de.tu_bs.cs.isf.e4cf.graph.core.elements.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.parts.GraphNodePart;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.parts.SimpleGraphPart;

public class GefClusterLayout implements Layout<SimpleGraphPart> {

	private static final double CLUSTER_RADIUS_MULTIPLIER = 40.0;

	protected static final double DIST_FROM_TOP = 300.0;
	
	protected Set<GefLayoutNode> layoutNodes = new TreeSet<>();
	
	private double padding;
	
	public GefClusterLayout(double padding) {
		this.padding = padding;
	}
	
	@Override
	public void init(SimpleGraphPart simpleGraphPart) {
		// build layout structure 
		for (Object o : simpleGraphPart.getChildrenUnmodifiable()) {
			if (o instanceof GraphNodePart) {
				GraphNodePart graphNodePart = (GraphNodePart) o;
				GefLayoutNode layoutNode = new GefLayoutNode(graphNodePart);
				layoutNodes.add(layoutNode);
			}
		}
	}
	
	@Override
	public void format() {
		// identify clusters
		List<GefLayoutNode> remainingNodes = new ArrayList<>(layoutNodes);
		List<List<GefLayoutNode>> clusters = new ArrayList<>();

		while(!remainingNodes.isEmpty()) {
			GefLayoutNode gefLayoutNode = remainingNodes.get(0);
			
			List<GefLayoutNode> cluster = getAllReachableNodes(gefLayoutNode);
			remainingNodes.removeAll(cluster);
			
			clusters.add(cluster);
		}

		System.out.println("Identifying Clusters ...");
		int i = 1;
		for (List<GefLayoutNode> cluster : clusters) {
			System.out.println("Cluster #"+(i++));
			for (GefLayoutNode clusterNode : cluster) 
			System.out.println("\t >"+clusterNode.get().getContent().getTitle());
		}
		
		// arrange clusters next to each other
		double center = 0;
		
		for (int clusterIndex = 0; clusterIndex < clusters.size(); clusterIndex++) {
			List<GefLayoutNode> cluster = clusters.get(clusterIndex);

			int memberSize = cluster.size(); // 
			double x = 0, y = 0; // visual coordinates
			double angleStep = 2.0 * Math.PI / memberSize; // in radian [0..2*PI]
			double radius = 0.0; // gets updated for every cluster
			
			if (memberSize == 1) { // single element cluster
				center += padding; // move center according to this single-cluster element
				
				GraphNodePart graphNodePart = cluster.get(0).get();
				graphNodePart.getVisual().setTranslateX(center);
				graphNodePart.getVisual().setTranslateY(DIST_FROM_TOP);
			} else { // multi element cluster circular arrangement
				radius = memberSize * CLUSTER_RADIUS_MULTIPLIER; // circle radius depends on total elements in cluster
				center += padding + radius; // move center to the next cluster center
				
				for (int memberIndex = 0; memberIndex < memberSize; memberIndex++) { // travel along circle and position elements
					double angle = memberIndex * angleStep;
					x = center + Math.cos(angle) * radius; // clusters arranged horizontally
					y = DIST_FROM_TOP + Math.sin(angle) * radius; // cluster center y coordinate is fixed
					
					// translate node to center it along circle
					x -= cluster.get(memberIndex).getWidth() / 2.0;
					
					GraphNodePart graphNodePart = cluster.get(memberIndex).get();
					graphNodePart.getVisual().setTranslateX(x);
					graphNodePart.getVisual().setTranslateY(y);
				}
				
				center += radius; // move center to the right border of the current cluster
			}
		}
		
	}

	private List<GefLayoutNode> getAllReachableNodes(GefLayoutNode layoutNode) {
		List<GefLayoutNode> cluster = new ArrayList<>();
		Stack<GefLayoutNode> nodeStack = new Stack<>();
		nodeStack.push(layoutNode);
		
		while (!nodeStack.isEmpty()) {
			GefLayoutNode curNode = nodeStack.pop();
			if (!cluster.contains(curNode)) { // only continue if the node isn't already contained
				cluster.add(curNode);
				curNode.getConnectedNodes().forEach( adjacentNode -> nodeStack.push(adjacentNode) );
			}
		}
		
		return cluster;
	}

}
