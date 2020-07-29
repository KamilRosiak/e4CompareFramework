package de.tu_bs.cs.isf.e4cf.core.benchmark;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.core.benchmark.status.BenchmarkContainer;

@Creatable
@Singleton
public class BenchmarkSystem {
	public static final String START_EVENT_BENCHMARK = "START_EVENT_BENCHMARK";
	public static final String END_EVENT_BENCHMARK = "END_EVENT_BENCHMARK";
	public static final String START_BENCHMARK = "START_BENCHMARK";
	public static final String END_BENCHMARK = "END_BENCHMARK";
	private BenchmarkContainer benchmark;
	
	public void startEvent(String eventName) {
		if(benchmark != null) {
			benchmark.startEvent(eventName);
		}
	}
	
	public void stopEvent(String eventName) {
		if(benchmark != null) {
			benchmark.stopEvent(eventName);
		}
	}
	
	public void startBenchmark(String benchmarkName) {
		benchmark = new BenchmarkContainer(benchmarkName);
	}
	
	public void finishBenchmark(String benchmarkName) {
		benchmark.printAllEvents();
	}
	
	
}
