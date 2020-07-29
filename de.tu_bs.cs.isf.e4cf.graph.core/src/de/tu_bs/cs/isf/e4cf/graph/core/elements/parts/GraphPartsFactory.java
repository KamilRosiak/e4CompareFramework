package de.tu_bs.cs.isf.e4cf.graph.core.elements.parts;

import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import javafx.scene.Node;


public class GraphPartsFactory implements IContentPartFactory {

    @Inject
    private Injector injector;

    @Override
    public IContentPart<? extends Node> createContentPart(Object content, Map<Object, Object> contextMap) {
        if (content == null) {
            throw new IllegalArgumentException("Content must not be null!");
        }
        if (content instanceof SimpleGraph) {
            return injector.getInstance(SimpleGraphPart.class);
        } else if (content instanceof GraphNode) {
            return injector.getInstance(GraphNodePart.class);
        } else if (content instanceof GraphEdge) {
            return injector.getInstance(GraphEdgePart.class);
        } else {
            throw new IllegalArgumentException("Unknown content type <" + content.getClass().getName() + ">");
        }
    }
}