package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.checker;

import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration;

public interface FeatureConfigurationChecker {

	public enum OperationState {
		UNDEFINED,
		PRECONDITION_ERROR,
		SUCCESS,
		TIMEOUT,
		IO_ERROR,
		PARSE_ERROR;
		
		private String info = null;
		private boolean hasInfo = false;
		
		public boolean hasInfo() {
			return hasInfo;
		}
		
		public void setInfo(String info) {
			this.info = info;
			hasInfo = true;
		}
		
		public String getInfo() {
			if (info != null) {
				return info;				
			} else {
				return "<no additional information>";
			}
		}
	}
	
	public void initialize(FeatureConfiguration fc);
	
	public OperationState check();
	
	public boolean getResult();
}
