package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.util.List;
import java.util.UUID;

public interface IFeature {
	UUID getUuid();
		
	String getName();
	void setName(String name);
		
	List<IFeature> getChildren();
	void addChild(IFeature child);
	void removeChild(IFeature child);
	
	IFeature getParent();
	void setParent(IFeature parent);
	
	Variability getVariability();
	void setVariability(Variability var);
	
	GroupVariability getGroupVariability();
	void setGroupVariability(GroupVariability groupVar);
	
	default boolean isAbstract() {
		return false;
	}
}
