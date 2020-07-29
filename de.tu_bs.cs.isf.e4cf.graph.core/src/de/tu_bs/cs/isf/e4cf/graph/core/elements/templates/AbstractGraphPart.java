package de.tu_bs.cs.isf.e4cf.graph.core.elements.templates;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import de.tu_bs.cs.isf.e4cf.core.plugin.ExtensionPointContext;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.graph.core.plugin.GraphPluginStrings;
import de.tu_bs.cs.isf.e4cf.graph.core.plugin.IGraphHandler;
import javafx.event.EventType;
import javafx.scene.Node;


/**
 * Adapter class that enables clients to invoke handlers provided by plugin extensions.
 * The handlers ({@link IGraphHandler}) process events distributed by the concrete parts ({@link IContentPart}).
 * 
 * A handler is typed to a concrete model class <b>T</b> and is only called if a corresponding part triggers an event.
 * 
 * For example, the part controlling the visual graph nodes can be typed to the underlying model representing a graph node. 
 * 
 * @see IGraphHandler
 * 
 * @author Oliver Urbaniak
 *
 * @param <T> model class, in this case derived from {@link AbstractGraphItem} 
 * @param <S> graphical element, in this case derived from {@link Node} 
 */
public abstract class AbstractGraphPart<T extends AbstractGraphItem, S extends Node> extends AbstractContentPart<S> {

		private static ExtensionPointContext extPointContext;
		private static List<IGraphHandler<? extends AbstractGraphItem>> handlers = new ArrayList<>();
		
		/**
		 * Go through the extensions in the graph handler extension point and store typed lists of handlers.
		 */
		public AbstractGraphPart() {
			if (extPointContext == null) {
				extPointContext = new ExtensionPointContext(GraphPluginStrings.EXT_POINT_GRAPH_HANDLER_ID);
				for (IConfigurationElement config : extPointContext.getConfigurationElements()) {
					IGraphHandler<?> graphHandler = createExecutable(config);
					handlers.add(graphHandler);
				}				
			}
		}
		
		@SuppressWarnings({ "unchecked" })
		private IGraphHandler<? extends AbstractGraphItem> createExecutable(IConfigurationElement config) {
			try {				
				return (IGraphHandler<? extends AbstractGraphItem>) config.createExecutableExtension(GraphPluginStrings.INSTANCE_ATTRIBUTE);
			} catch (CoreException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * Invokes calls to all handlers typed to the model element T.
		 * 
		 * @param element
		 * @param event
		 * @param services
		 */
		@SuppressWarnings("unchecked")
		public void invokeHandlers(T element, EventType<?> event, ServiceContainer services) {
			for (IGraphHandler<? extends AbstractGraphItem> graphHandler : handlers) {			
				try {
					Method m = graphHandler.getClass().getMethod("execute", element.getClass(), event.getClass(), services.getClass());
					if (m != null) {
						((IGraphHandler<T>) graphHandler).execute(element, event, services);
					}
				} catch (SecurityException | NoSuchMethodException e) {
					
				}
			}
		}
}
