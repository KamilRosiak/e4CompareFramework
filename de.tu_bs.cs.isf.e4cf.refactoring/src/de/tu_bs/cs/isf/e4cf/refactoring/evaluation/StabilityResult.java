package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;

public class StabilityResult {

	private Map<String, Integer> clusterSplits;

	private Map<String, Integer> clusterMerges;

	public Map<String, Integer> getClusterSplits() {
		return clusterSplits;
	}

	public void setClusterSplits(Map<String, Integer> clusterSplits) {
		this.clusterSplits = clusterSplits;
	}

	public Map<String, Integer> getClusterMerges() {
		return clusterMerges;
	}

	public void setClusterMerges(Map<String, Integer> clusterMerges) {
		this.clusterMerges = clusterMerges;
	}

	private ChangeAction changeAction;

	public ChangeAction getChangeAction() {
		return changeAction;
	}

	public void setChangeAction(ChangeAction changeAction) {
		this.changeAction = changeAction;
	}

	public StabilityResult(Set<Granularity> granularities) {
		this.clusterMerges = new HashMap<String, Integer>();
		this.clusterSplits = new HashMap<String, Integer>();

		for (Granularity granularity : granularities) {
			clusterMerges.put(granularity.getLayer(), 0);
			clusterSplits.put(granularity.getLayer(), 0);
		}

	}

	@Override
	public String toString() {

		String clusterSplitString = "(";
		for (Entry<String, Integer> entry : clusterSplits.entrySet()) {
			clusterSplitString += entry.getKey() + ";" + entry.getValue();
		}
		clusterSplitString += ")";

		String clusterMergeString = "(";
		for (Entry<String, Integer> entry : clusterMerges.entrySet()) {
			clusterMergeString += entry.getKey() + ";" + entry.getValue();
		}
		clusterMergeString += ")";

		return clusterSplitString + "," + clusterMergeString + "," + changeAction.getChangeActionType();
	}

}
