package de.tu_bs.cs.isf.e4cf.refactoring.events;

import org.osgi.service.event.EventHandler;

import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;


public abstract class EventHandlerBase implements EventHandler {

	protected ClusterEngine clusterEngine;

	public EventHandlerBase(ClusterEngine clusterEngine) {
		super();
		this.clusterEngine = clusterEngine;
	}

}
