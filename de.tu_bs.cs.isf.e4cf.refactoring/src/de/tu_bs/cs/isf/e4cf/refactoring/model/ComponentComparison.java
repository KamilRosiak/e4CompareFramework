package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;

public class ComponentComparison {

	private Component component1;

	private Component component2;

	private Map<Configuration, Configuration> matchedConfigurations;

	private List<Configuration> addedConfigurations;

	private List<Configuration> removedConfigurations;

	public ComponentComparison(Component component1, Component component2,
			Map<Configuration, Configuration> matchedConfigurations, List<Configuration> addedConfigurations,
			List<Configuration> removedConfigurations) {
		super();
		this.component1 = component1;
		this.component2 = component2;
		this.matchedConfigurations = matchedConfigurations;
		this.addedConfigurations = addedConfigurations;
		this.removedConfigurations = removedConfigurations;
	}

	public Component getComponent1() {
		return component1;
	}

	public void setComponent1(Component component1) {
		this.component1 = component1;
	}

	public Component getComponent2() {
		return component2;
	}

	public void setComponent2(Component component2) {
		this.component2 = component2;
	}

	public Map<Configuration, Configuration> getMatchedConfigurations() {
		return matchedConfigurations;
	}

	public void setMatchedConfigurations(Map<Configuration, Configuration> matchedConfigurations) {
		this.matchedConfigurations = matchedConfigurations;
	}

	public List<Configuration> getAddedConfigurations() {
		return addedConfigurations;
	}

	public void setAddedConfigurations(List<Configuration> addedConfigurations) {
		this.addedConfigurations = addedConfigurations;
	}

	public List<Configuration> getRemovedConfigurations() {
		return removedConfigurations;
	}

	public void setRemovedConfigurations(List<Configuration> removedConfigurations) {
		this.removedConfigurations = removedConfigurations;
	}

}
