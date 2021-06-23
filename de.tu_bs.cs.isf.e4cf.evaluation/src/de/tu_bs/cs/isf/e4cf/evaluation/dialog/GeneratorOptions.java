package de.tu_bs.cs.isf.e4cf.evaluation.dialog;

import java.nio.file.Path;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.evaluation.generator.Granularity;

public class GeneratorOptions {
	public Path outputRoot;
	public Granularity granularity;
	public Map<String, Boolean> options;
}


