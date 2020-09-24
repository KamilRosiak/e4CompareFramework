package de.tu_bs.cs.isf.e4cf;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import CrossTreeConstraints.CrossTreeConstraintsPackage;

public class Activator implements BundleActivator, BundleListener{

private static BundleContext context;
	
	static BundleContext getContext() {
		return context;
	}
	
	/**
	 * Initialize the EPackeges to use the meta structure 
	 */
	private void initEP() {
		CrossTreeConstraintsPackage ctcp = CrossTreeConstraintsPackage.eINSTANCE;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) {	
		// bundle management
		bundleContext.addBundleListener(this);
		
		// application management
		initialize(bundleContext);		
	}

	private void initialize(BundleContext bundleContext) {
		Activator.context = bundleContext;
		initEP();			
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
//		Activator.context.removeBundleListener(this);
		Activator.context = null;
	}

	@Override
	public void bundleChanged(BundleEvent event) {
	}

}
