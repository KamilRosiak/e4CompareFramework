package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.List;
import java.util.Map;

public interface Component extends Node {

	public List<Configuration> getConfigurations();

	public void setConfigurations(List<Configuration> configurations);

	public String getLayer();

	public void setLayer(String layer);

	public Configuration getSelectedConfiguration();

	public void setSelectedConfiguration(Configuration configuration);
	
	public List<Node> getAllTargets();
	
	public Map<Node, Configuration> getConfigurationByTarget();
	

}
