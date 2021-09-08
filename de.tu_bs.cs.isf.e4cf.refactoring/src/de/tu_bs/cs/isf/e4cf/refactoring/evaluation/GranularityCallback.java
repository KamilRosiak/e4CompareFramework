package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;

public interface GranularityCallback {

	public void handle(List<Granularity> granularities);

}
