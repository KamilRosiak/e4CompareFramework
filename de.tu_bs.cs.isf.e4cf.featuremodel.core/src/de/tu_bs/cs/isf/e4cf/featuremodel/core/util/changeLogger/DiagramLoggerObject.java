package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger;

import FeatureDiagram.Feature;

public class DiagramLoggerObject {
	
	private String _prefix;
	private String _change_identifier;
	private Feature _feature;
	
	public DiagramLoggerObject(String prefix, String CHANGE_IDENTIFIER, Feature feature) {
		this._prefix = prefix;
		this._change_identifier = CHANGE_IDENTIFIER;
		this._feature = feature;
	}
	
	public DiagramLoggerObject(String prefix, String CHANGE_IDENTIFIER) {
		this._prefix = prefix;
		this._change_identifier = CHANGE_IDENTIFIER;
	}
	
	public String getChangeIdentifier() {
		return this._change_identifier;
	}
	
	public Feature getFeature() {
		return this._feature;
	}
	
	public String getPrefix() {
		return this._prefix;
	}
	
	public String print() {
		try {
			return this._prefix + "\t" + this._change_identifier + "\t" + _feature.getIdentifier() + "\n";			
		} catch (NullPointerException e) {
			return this._prefix + "\t" + this._change_identifier + "\n";
		}
	}

}
