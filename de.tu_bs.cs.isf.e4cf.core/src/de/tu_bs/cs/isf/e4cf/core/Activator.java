package de.tu_bs.cs.isf.e4cf.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 * 	This class represents the activator of the RCP E4 platform. You can use it to initialize classes that are needed in the current context.
 *  Its possible to Inject all provided services of the e4 platform like the IEclipseContext.    
 *  @author {Kamil Rosiak}
 */
public class Activator implements BundleActivator, BundleListener {
	private static BundleContext context;
	
	static BundleContext getContext() {
		return context;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) {
	
		//performAuthentification();
		// bundle management
		bundleContext.addBundleListener(this);
		// application management
		initialize(bundleContext);		
	}

	private void initialize(BundleContext bundleContext) {
		Activator.context = bundleContext;		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context.removeBundleListener(this);
		Activator.context = null;
	//	Platform.getInstanceLocation().set(PreferencesUtil.getValue(node, key), false);
	}

	@Override
	public void bundleChanged(BundleEvent event) {

	}
}

