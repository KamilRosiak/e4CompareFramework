package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.util.List;
import java.util.UUID;

public interface IFeature {
	
	String getName();
	
	UUID getUuid();
	
	List<IFeature> getChildren();
	
	Variability getVariability();
	
	GroupVariability getGroupVariability();
	
	void addChild(IFeature child);
	
	void removeChild(IFeature child);
	
	default boolean isAbstract() {
		return false;
	}

}
