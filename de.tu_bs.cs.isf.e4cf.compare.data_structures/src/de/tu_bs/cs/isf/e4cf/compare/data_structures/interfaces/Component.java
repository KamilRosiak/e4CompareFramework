package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Component extends Node {

	public Map<Node, Set<Node>> getSynchronizationUnit();

	public void setSynchronizationUnit(Map<Node, Set<Node>> synhronizationUnit);

	public Map<Node, Map<Integer, Configuration>> getNodeComponentRelation();

	public void setNodeComponentRelation(Map<Node, Map<Integer, Configuration>> nodeComponentRelation);

	public List<Configuration> getConfigurations();

	public void setConfigurations(List<Configuration> configurations);

}
