package de.tu_bs.cs.isf.e4cf.zest_graph.template;

import org.eclipse.gef.common.adapt.inject.AdapterInjectionSupport;
import org.eclipse.gef.common.adapt.inject.AdapterInjectionSupport.LoggingMode;
import org.eclipse.gef.zest.fx.ZestFxModule;

public class ZestGraphModule extends ZestFxModule {

	@Override
	protected void enableAdapterMapInjection() {
		install(new AdapterInjectionSupport(LoggingMode.PRODUCTION));
	} 
}
