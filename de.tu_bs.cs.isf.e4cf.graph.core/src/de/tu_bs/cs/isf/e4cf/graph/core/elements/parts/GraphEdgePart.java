package de.tu_bs.cs.isf.e4cf.graph.core.elements.parts;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.mvc.fx.models.SelectionModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.reflect.TypeToken;
import com.google.inject.Provider;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphPart;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.view.GraphicalEdge;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;


public class GraphEdgePart extends AbstractGraphPart<GraphEdge, GraphicalEdge> {

	public class EdgeSelectionListener implements ListChangeListener<IContentPart<? extends Node>> {

		private ServiceContainer services;
		
		public EdgeSelectionListener(ServiceContainer services) {
			this.services = services;
		}

		@Override
		public void onChanged(Change<? extends IContentPart<? extends Node>> change) {
			if (change.getList().contains(GraphEdgePart.this)) {
				GraphEdge edge = GraphEdgePart.this.getContent();
				
				invokeHandlers(edge, MouseEvent.MOUSE_CLICKED, services);
			}
			
		}
	}
	
	
	private static final String START_ROLE = "START";
    private static final String END_ROLE = "END";

    protected EdgeSelectionListener edgeSelectionListener;
    
    @Override
    protected void doAttachToAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
        // find a anchor provider, which must be registered in the module
        // be aware to use the right interfaces (Provider is used a lot)
        @SuppressWarnings("serial")
        Provider<? extends IAnchor> adapter = anchorage
                .getAdapter(AdapterKey.get(new TypeToken<Provider<? extends IAnchor>>() {
                }));
        if (adapter == null) {
            throw new IllegalStateException("No adapter  found for <" + anchorage.getClass() + "> found.");
        }
        IAnchor anchor = adapter.get();

        if (role.equals(START_ROLE)) {
            getVisual().setStartAnchor(anchor);
        } else if (role.equals(END_ROLE)) {
            getVisual().setEndAnchor(anchor);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    @Override
    protected GraphicalEdge doCreateVisual() {
        return new GraphicalEdge(getContent().getWeight());
    }

    @Override
    protected void doDetachFromAnchorageVisual(IVisualPart<? extends Node> anchorage, String role) {
        if (role.equals(START_ROLE)) {
            getVisual().setStartPoint(getVisual().getStartPoint());
        } else if (role.equals(END_ROLE)) {
            getVisual().setEndPoint(getVisual().getEndPoint());
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    @Override
    protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
        SetMultimap<Object, String> anchorages = HashMultimap.create();

        anchorages.put(getContent().getSource(), START_ROLE);
        anchorages.put(getContent().getTarget(), END_ROLE);
        

        return anchorages;
    }

    @Override
    protected List<? extends Object> doGetContentChildren() {
        return Collections.emptyList();
    }

    @Override
    protected void doRefreshVisual(GraphicalEdge visual) {
        SelectionModel selectionModel = this.getViewer().getAdapter(SelectionModel.class);
        if (edgeSelectionListener == null) {
        	ServiceContainer serviceContainer = getViewer().getAdapter(ServiceContainer.class);
        	edgeSelectionListener = new EdgeSelectionListener(serviceContainer);   	
        	selectionModel.getSelectionUnmodifiable().addListener(edgeSelectionListener);
        }
    }

    @Override
    public GraphEdge getContent() {
        return (GraphEdge) super.getContent();
    }
}
