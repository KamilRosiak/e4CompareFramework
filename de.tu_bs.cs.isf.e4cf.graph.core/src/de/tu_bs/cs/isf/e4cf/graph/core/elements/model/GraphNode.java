package de.tu_bs.cs.isf.e4cf.graph.core.elements.model;

import java.io.Serializable;
import java.util.List;

import org.eclipse.gef.geometry.planar.Rectangle;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphItem;
import javafx.scene.paint.Color;



public class GraphNode extends AbstractGraphItem implements Serializable {

    /**
     * Generated UUID
     */
    private static final long serialVersionUID = 8875579454539897410L;

    public static final String PROP_TITLE = "title";
    public static final String PROP_DESCRIPTION = "description";
    public static final String PROP_COLOR = "color";
    public static final String PROP_BOUNDS = "bounds";
    public static final String PROP_INCOMING_CONNECTIONS = "incomingConnections";
    public static final String PROP_OUTGOGING_CONNECTIONS = "outgoingConnections";

    /**
     * The title of the node
     */
    private String title;

    /**
     * he description of the node, which is optional
     */
    private String description;

    /**
     * The background color of the node
     */
    private Color color;

    /**
     * The size and position of the visual representation
     */
    private Rectangle bounds;

    private List<GraphEdge> incomingConnections = Lists.newArrayList();
    private List<GraphEdge> outgoingConnections = Lists.newArrayList();

    public void addIncomingConnection(GraphEdge conn) {
        incomingConnections.add(conn);
        propertyChgSup.firePropertyChange(PROP_INCOMING_CONNECTIONS, null, conn);
    }

    public void addOutgoingConnection(GraphEdge conn) {
        outgoingConnections.add(conn);
        propertyChgSup.firePropertyChange(PROP_OUTGOGING_CONNECTIONS, null, conn);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Color getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public List<GraphEdge> getIncomingConnections() {
        return incomingConnections;
    }

    public List<GraphEdge> getOutgoingConnections() {
        return outgoingConnections;
    }

    public String getTitle() {
        return title;
    }

    public void removeIncomingConnection(GraphEdge conn) {
        incomingConnections.remove(conn);
        propertyChgSup.firePropertyChange(PROP_INCOMING_CONNECTIONS, conn, null);
    }

    public void removeOutgoingConnection(GraphEdge conn) {
        outgoingConnections.remove(conn);
        propertyChgSup.firePropertyChange(PROP_OUTGOGING_CONNECTIONS, conn, null);
    }

    public void setBounds(Rectangle bounds) {
        propertyChgSup.firePropertyChange(PROP_BOUNDS, this.bounds, (this.bounds = bounds.getCopy()));
    }

    public void setColor(Color color) {
        propertyChgSup.firePropertyChange(PROP_COLOR, this.color, (this.color = color));
    }

    public void setDescription(String description) {
        propertyChgSup.firePropertyChange(PROP_DESCRIPTION, this.description, (this.description = description));
    }

    public void setTitle(String title) {
        propertyChgSup.firePropertyChange(PROP_TITLE, this.title, (this.title = title));
    }
} 