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
		return GroupVariability.DEFAULT;
	}

	default void setGroupVariability(GroupVariability groupVariability) {

	}

	FeatureDiagram getDiagram();

	void setDiagram(FeatureDiagram diagram);

	IFeatureConfiguration getConfiguration();
}
