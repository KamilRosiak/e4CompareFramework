package de.tu_bs.cs.isf.e4cf.graph.core.plugin;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphItem;
import javafx.event.EventType;

public interface IGraphHandler<T extends AbstractGraphItem> {

	
	public void execute(T element, EventType<?> event, ServiceContainer services);
}
