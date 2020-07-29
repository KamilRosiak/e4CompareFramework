package de.tu_bs.cs.isf.e4cf.core.benchmark.status;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenchmarkContainer {
	private String benchmarkName;
	private Map<String,Timestamp> eventTimeStemps;
	private List<String> finishedEvents;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("mm.ss.SSS");

	
	public BenchmarkContainer(String name) {
		setBenchmarkName(name);
		eventTimeStemps = new HashMap<String, Timestamp>();
		setFinishedEvents(new ArrayList<String>());
	}
	
	public void startEvent(String eventKey) {
		eventTimeStemps.put(eventKey, new Timestamp(System.currentTimeMillis()));
	}
	
	public void stopEvent(String eventKey) {
		if(eventTimeStemps.get(eventKey) != null){
			Timestamp duration = new Timestamp(System.currentTimeMillis() - eventTimeStemps.get(eventKey).getTime());
			String eventString = eventKey+" : "+ sdf.format(duration);
			finishedEvents.add(eventString);
			eventTimeStemps.remove(eventKey);
		}
	}

	public String getBenchmarkName() {
		return benchmarkName;
	}

	public void setBenchmarkName(String benchmarkName) {
		this.benchmarkName = benchmarkName;
	}

	public List<String> getFinishedEvents() {
		return finishedEvents;
	}

	public void setFinishedEvents(List<String> finishedEvents) {
		this.finishedEvents = finishedEvents;
	}
	
	public void printAllEvents() {
		System.out.println("Benchmark : "+ benchmarkName);
		System.out.println("~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*");
		for(String events : finishedEvents) {
			System.out.println(events);
		}
	}
	
}
