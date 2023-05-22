package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.util.List;

public interface IComponentFeature extends IFeature {
	
	default List<IFeature> getChildren() {
		throw new UnsupportedOperationException();
	}
	default void addChild(IFeature child) {
		throw new UnsupportedOperationException();
	}
	default void removeChild(IFeature child) {
		throw new UnsupportedOperationException();
	}
	
	default GroupVariability getGroupVariability() {
		throw new UnsupportedOperationException();
	}
	default void setGroupVariability(GroupVariability groupVariability) {
		throw new UnsupportedOperationException();
	}
	default void setAbstract(boolean isAbstract) {
		throw new UnsupportedOperationException();
	}
	
	default boolean isComponent() {
		return true;
	}
	
	FeatureDiagram getDiagram();
	void setDiagram(FeatureDiagram diagram);
	
	IFeatureConfiguration getConfiguration();	
}
