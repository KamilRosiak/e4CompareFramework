package de.tu_bs.cs.isf.e4cf.graph.core.elements.layout;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.parts.GraphEdgePart;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.parts.GraphNodePart;

public class GefLayoutNode implements LayoutNode<GraphNodePart>, Comparable<GefLayoutNode> {

     GraphNodePart nodePart;
     Set<GefLayoutNode> connectedNodes; 
	
    public GefLayoutNode(GraphNodePart nodePart) {
		this.nodePart = nodePart;
	}
    
	@Override
	public Set<GefLayoutNode> getConnectedNodes() {
		if (connectedNodes == null) { // lazy init
			connectedNodes = new HashSet<>();
			collectConnectedNodes();
		}
		
		return connectedNodes;
	}
	
	/**
	 * Collects all the adjacent nodes, wraps them in a layout node and stores them in the connected node set.
	 * Although new layout nodes are created, they are equal to other nodes corresponding to the same graph node part.
	 * 
	 * @see #equals(Object)
	 */
	private void collectConnectedNodes() {
		connectedNodes.clear();
		
		for (IVisualPart<?> anchoredPart : nodePart.getAnchoredsUnmodifiable()) {
			if (anchoredPart instanceof GraphEdgePart) {
				GraphEdgePart edgePart = (GraphEdgePart) anchoredPart;
				for (IVisualPart<?> anchoragePart : edgePart.getAnchoragesUnmodifiable().keys()) {
					if (anchoragePart instanceof GraphNodePart && anchoragePart != this.nodePart) {
						GraphNodePart graphNodePart = (GraphNodePart) anchoragePart;
						connectedNodes.add(new GefLayoutNode(graphNodePart));
					}
				}
			}
		}
	}

	public double getX() {
		return nodePart.getVisual().getTranslateX();
	}
	
	public double getY() {
		return nodePart.getVisual().getTranslateY();
	}
	
	@Override
	public double getWidth() {
		return nodePart.getVisual().getWidth();
	}

	@Override
	public double getHeight() {
		return nodePart.getVisual().getHeight();
	}

	@Override
	public GraphNodePart get() {
		return nodePart;
	}
	

	/**
	 * Checks reference equality with another layout node's wrapped object.
	 * 
	 *  @see #get()
	 */
	@Override public boolean equals(Object o) {
		if (o instanceof LayoutNode<?>) {
			LayoutNode<?> layoutNode = (LayoutNode<?>) o;
			return layoutNode.get() == nodePart;
			
		}
		
		return false;
	}

	@Override
	public int compareTo(GefLayoutNode o) {
		return hashCode() - o.hashCode();
	}

	@Override
	public String toString() {
		return "LayoutNode["+nodePart.toString()+"]";
	}

	
}
