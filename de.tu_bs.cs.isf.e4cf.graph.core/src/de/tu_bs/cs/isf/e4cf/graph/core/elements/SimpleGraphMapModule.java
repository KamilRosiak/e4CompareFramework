package de.tu_bs.cs.isf.e4cf.graph.core.elements;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.common.adapt.inject.AdapterMaps;
import org.eclipse.gef.mvc.fx.MvcFxModule;
import org.eclipse.gef.mvc.fx.handlers.FocusAndSelectOnClickHandler;
import org.eclipse.gef.mvc.fx.handlers.TranslateSelectedOnDragHandler;
import org.eclipse.gef.mvc.fx.parts.DefaultHoverFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionFeedbackPartFactory;
import org.eclipse.gef.mvc.fx.parts.DefaultSelectionHandlePartFactory;
import org.eclipse.gef.mvc.fx.policies.TransformPolicy;
import org.eclipse.gef.mvc.fx.providers.GeometricOutlineProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeBoundsProvider;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;

import com.google.inject.multibindings.MapBinder;

import de.tu_bs.cs.isf.e4cf.graph.core.elements.parts.GraphEdgePart;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.parts.GraphNodePart;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.parts.GraphPartsFactory;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.provider.SimpleGraphAnchorProvider;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.view.GraphicalEdge.GraphicalEdgeShapeProvider;


public class SimpleGraphMapModule extends MvcFxModule {

	@Override
    protected void bindIContentPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
        // bind MindMapPartsFactory adapter to the content viewer
        adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(GraphPartsFactory.class);
    }

    /**
     *
     * @param adapterMapBinder
     */
    protected void bindMindMapNodePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
        // bind anchor provider used to create the connection anchors
        adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(SimpleGraphAnchorProvider.class);

        // bind a geometry provider, which is used in our anchor provider
        adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(ShapeOutlineProvider.class);
        
        // provides a hover feedback to the shape, used by the HoverBehavior
        AdapterKey<?> role = AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER);
        adapterMapBinder.addBinding(role).to(ShapeOutlineProvider.class);

        // provides a selection feedback to the shape
        role = AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER);
        adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);
        
        // support moving nodes via mouse drag
        adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TransformPolicy.class);
        adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(TranslateSelectedOnDragHandler.class);
        
        // specify the factory to create the geometry object for the selection handles
        role = AdapterKey.role(DefaultSelectionHandlePartFactory.SELECTION_HANDLES_GEOMETRY_PROVIDER);
        adapterMapBinder.addBinding(role).to(ShapeBoundsProvider.class);
    }
    
    /**
    *
    * @param adapterMapBinder
    */
   protected void bindMindMapEdgePartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
       // provides a hover feedback to the shape, used by the HoverBehavior
       AdapterKey<?> role = AdapterKey.role(DefaultHoverFeedbackPartFactory.HOVER_FEEDBACK_GEOMETRY_PROVIDER);
       adapterMapBinder.addBinding(role).to(GeometricOutlineProvider.class);
       
       role = AdapterKey.role(DefaultSelectionFeedbackPartFactory.SELECTION_FEEDBACK_GEOMETRY_PROVIDER);
       adapterMapBinder.addBinding(role).to(GraphicalEdgeShapeProvider.class);

   }
    
    @Override
    protected void bindAbstractContentPartAdapters(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
        super.bindAbstractContentPartAdapters(adapterMapBinder);

        // binding the HoverOnHoverPolicy to every part
        // if a mouse is moving above a part it is set i the HoverModel
        //adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverOnHoverHandler.class);

        // add the focus and select policy to every part, listening to clicks
        // and changing the focus and selection model
        adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(FocusAndSelectOnClickHandler.class);
    }
    
    @Override
    protected void bindIRootPartAdaptersForContentViewer(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
        super.bindIRootPartAdaptersForContentViewer(adapterMapBinder);
        
        // binding a Hover Behavior to the root part. it will react to
        // HoverModel changes and render the hover part
       // adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(HoverBehavior.class);
    }
    
    @Override
    protected void configure() {
        // start the default configuration
        super.configure();
        bindMindMapNodePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), GraphNodePart.class));
        bindMindMapEdgePartAdapters(AdapterMaps.getAdapterMapBinder(binder(), GraphEdgePart.class));
    }
}
