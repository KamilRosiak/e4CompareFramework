package de.tu_bs.cs.isf.e4cf.graph.core.elements.model;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphItem;

public class GraphEdge extends AbstractGraphItem {

    /**
     * Generated UUID
     */
    private static final long serialVersionUID = 6065237357753406466L;
    private float weight = 0.0f;
    private GraphNode source;
    private GraphNode target;
    private boolean connected;

    public void connect(GraphNode source, GraphNode target) {
        if (source == null || target == null || source == target) {
            throw new IllegalArgumentException();
        }
        disconnect();
        this.source = source;
        this.target = target;
        reconnect();
    }

    public void disconnect() {
        if (connected) {
            source.removeOutgoingConnection(this);
            target.removeIncomingConnection(this);
            connected = false;
        }
    }

    public GraphNode getSource() {
        return source;
    }

    public GraphNode getTarget() {
        return target;
    }

    public void reconnect() {
        if (!connected) {
            source.addOutgoingConnection(this);
            target.addIncomingConnection(this);
            connected = true;
        }
    }

    public void setSource(GraphNode source) {
        this.source = source;
    }

    public void setTarget(GraphNode target) {
        this.target = target;
    }

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
}