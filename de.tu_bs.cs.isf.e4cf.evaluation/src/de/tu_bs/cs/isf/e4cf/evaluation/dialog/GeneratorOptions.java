package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import java.nio.file.Path;

import de.tu_bs.cs.isf.e4cf.evaluation.generator.Granularity;

/** Struct like class that holds data about a generation process */
public class GeneratorOptions {
	public Path outputRoot;
	public Granularity granularity;
	public int variants;
	public int crossoverPercentage;
	public int modificationRatioPercentage;
	public int variantChangeDegree;
}


