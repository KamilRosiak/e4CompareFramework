package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import java.nio.file.Path;

import de.tu_bs.cs.isf.e4cf.evaluation.generator.Granularity;

/** Struct like class that holds data about a generation process */
public class GeneratorOptions {
	public Path outputRoot;
	public Granularity granularity;
	public boolean enableSaveAll;
	public boolean enableCrossover;
	public int weightType1;
	public int weightType2;
	public int weightType3;
	public int mutations;
}


