package de.tu_bs.cs.isf.e4cf.graph.core.elements.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IHandlePart;
import org.eclipse.gef.mvc.fx.parts.IResizableContentPart;
import org.eclipse.gef.mvc.fx.parts.ITransformableContentPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphPart;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.view.GraphicalNode;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;

public class GraphNodePart extends AbstractGraphPart<GraphNode, GraphicalNode> implements IHandlePart<GraphicalNode>, ITransformableContentPart<GraphicalNode>, IResizableContentPart<GraphicalNode>  {

	public class NodeSelectionListener implements ListChangeListener<IContentPart<? extends Node>> {

		private ServiceContainer services;
		
		public NodeSelectionListener(ServiceContainer services) {
			this.services = services;
		}

		@Override
		public void onChanged(Change<? extends IContentPart<? extends Node>> change) {
			if (change.getList().contains(GraphNodePart.this)) {
				GraphNode node = GraphNodePart.this.getContent();
				
				invokeHandlers(node, MouseEvent.MOUSE_CLICKED, services);
			}
			
		}
	}
	
	private NodeSelectionListener nodeSelectionListener;
	
	
    @Override
    protected GraphicalNode doCreateVisual() {
        return new GraphicalNode();
    }

    @Override
    protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
        // Nothing to anchor to
        return HashMultimap.create();
    }

    @Override
    protected List<? extends Object> doGetContentChildren() {
        // we don't have any children.
        return Collections.emptyList();
    }

    @Override
    protected void doRefreshVisual(GraphicalNode visual) {
        // updating the visuals texts and position
    	GraphNode node = getContent();
        Rectangle rec = node.getBounds();

        visual.setTitle(node.getTitle());
        visual.setDescription(node.getDescription());
        visual.setColor(node.getColor());

        visual.setPrefSize(rec.getWidth(), rec.getHeight());
        // perform layout pass so that visual is resized to its preferred size

        if(visual.getParent() != null)
        	visual.getParent().layout();

        // add selection handler
        SelectionModel selectionModel = this.getViewer().getAdapter(SelectionModel.class);
        if (nodeSelectionListener == null) {
        	ServiceContainer serviceContainer = getViewer().getAdapter(ServiceContainer.class);
        	nodeSelectionListener = new NodeSelectionListener(serviceContainer);
        	selectionModel.getSelectionUnmodifiable().addListener(nodeSelectionListener);
        }
    }

    @Override
    public GraphNode getContent() {
        return (GraphNode) super.getContent();
    }

    @Override
    public Dimension getContentSize() {
        return getContent().getBounds().getSize();
    }

    @Override
    public void setContentSize(Dimension totalSize) {
        // storing the new size
        getContent().getBounds().setSize(totalSize);
    }

	@Override
	public Affine getContentTransform() {
		 Rectangle bounds = getContent().getBounds();
	     return new Affine(new Translate(bounds.getX(), bounds.getY()));
	}

	@Override
	public void setContentTransform(Affine totalTransform) {
        Rectangle bounds = getContent().getBounds().getCopy();
 
        bounds.setX(totalTransform.getTx());
        bounds.setY(totalTransform.getTy());
        getContent().setBounds(bounds);
	}
}
