package de.tu_bs.cs.isf.e4cf.featuremodel.core.util;

import org.eclipse.e4.core.services.events.IEventBroker;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class EventBroker {
	private static ServiceContainer services;
	
	private EventBroker() {
		services = null;
	}
	
	public static void set(ServiceContainer newServices) {
		services = newServices;
	}
	
	public static IEventBroker get() {
		return services.eventBroker;
	}
	
	

}
