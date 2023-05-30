package de.tu_bs.cs.isf.e4cf.featuremodel.core.error;

import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;

public interface ErrorListener {

	public static class FeatureModelViewError {

		private static final UUID DEFAULT_FEATURE_ID = UUID.fromString("default_feature_id");

		public FeatureModelViewError(FXGraphicalFeature feature, String event, String msg) {
			this(feature != null ? feature.getFeature() : null, event, msg);
		}

		// TODO:HEAD
		// public FeatureModelViewError(Feature feature, String event, String msg) {
		// this.affectedId = feature != null ? 0 : DEFAULT_FEATURE_ID;

		public FeatureModelViewError(IFeature feature, String event, String msg) {
			this.affectedId = feature != null ? feature.getUuid() : DEFAULT_FEATURE_ID;

			this.event = event;
			this.msg = msg;
		}

		public FeatureModelViewError(String event, String msg) {
			this.affectedId = DEFAULT_FEATURE_ID;
			this.event = event;
			this.msg = msg;
		}

		public UUID affectedId;
		public String event;
		public String msg;
	}

	public void onError(FeatureModelViewError error);
}
