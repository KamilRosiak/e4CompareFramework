package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.List;
import java.util.Map;

public interface Component extends Node {

	
	public Map<Node, Map<Integer, Configuration>> getNodeComponentRelation();

	public void setNodeComponentRelation(Map<Node, Map<Integer, Configuration>> nodeComponentRelation);

	public List<Configuration> getConfigurations();

	public void setConfigurations(List<Configuration> configurations);
	
	public String getLayer();

	public void setLayer(String layer);
}
