package de.tu_bs.cs.isf.e4cf.featuremodel.core.error;

import FeatureDiagram.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

public interface ErrorListener {

	public static class FeatureModelViewError {
		
		private static final int DEFAULT_FEATURE_ID = -1;
		
		public FeatureModelViewError(FXGraphicalFeature feature, String event, String msg) {
			this(feature != null ? feature.getFeature() : null, event, msg);
		}
		
		public FeatureModelViewError(Feature feature, String event, String msg) {
			this.affectedId = feature != null ? feature.getIdentifier() : DEFAULT_FEATURE_ID;
			this.event 		= event;
			this.msg 		= msg;
		}
		
		public FeatureModelViewError(String event, String msg) {
			this.affectedId = DEFAULT_FEATURE_ID;
			this.event 		= event;
			this.msg 		= msg;
		}
		
		public int affectedId;
		public String event;
		public String msg;
	}
	
	public void onError(FeatureModelViewError error);
}
