package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.util;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.Cluster;

public class FeatureUtil {
	
	public static IFeature toFeature(Cluster cluster) {
		Feature feature = new Feature(cluster.getName());
		feature.setColor(cluster.getSyntaxGroup().getColor());
		feature.getConfigurations().addAll(cluster.getSyntaxGroup().getConfigurations());
		feature.getArtifactUUIDs().addAll(cluster.getSyntaxGroup().getUuids());
		switch (cluster.getVariability()) {
		case DEFAULT:
			feature.setVariability(Variability.DEFAULT);
			break;
		case MANDATORY:
			feature.setVariability(Variability.MANDATORY);
			break;
		case OPTIONAL:
			feature.setVariability(Variability.OPTIONAL);
			break;		
		}
		switch (cluster.getChildSelection()) {
		case ALTERNATIVE:
			feature.setGroupVariability(GroupVariability.ALTERNATIVE);
			break;
		case OR:
			feature.setGroupVariability(GroupVariability.OR);
			break;
		default:
			feature.setGroupVariability(GroupVariability.DEFAULT);
			break;
		}
		for (Cluster child : cluster.getChildren()) {
			IFeature childFeature = FeatureUtil.toFeature(child);
			feature.addChild(childFeature);
		}
		return feature;
	}

}
