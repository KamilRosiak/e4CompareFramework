package de.tu_bs.cs.isf.e4cf.extractive_mple_platform_view;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;

public interface IFeatureLocaterExtension {
	
	public void locateFeatures(ServiceContainer container, MPLPlatform platform);

}
