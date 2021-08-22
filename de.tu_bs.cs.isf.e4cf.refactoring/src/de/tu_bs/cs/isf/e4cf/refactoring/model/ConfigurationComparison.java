package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;

public class ConfigurationComparison {
	
	private Component component1;
	
	private Component component2;

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

	private Configuration configuration1;
	
	private Configuration configuration2;
	
	private List<ActionScope> actionScopes;
	
	public List<ActionScope> getSynchronizationScopes() {
		return synchronizationScopes;
	}

	public void setSynchronizationScopes(List<ActionScope> synchronizationScopes) {
		this.synchronizationScopes = synchronizationScopes;
	}

	private List<ActionScope> synchronizationScopes;

	public Configuration getConfiguration1() {
		return configuration1;
	}

	public void setConfiguration1(Configuration configuration1) {
		this.configuration1 = configuration1;
	}

	public Configuration getConfiguration2() {
		return configuration2;
	}

	public void setConfiguration2(Configuration configuration2) {
		this.configuration2 = configuration2;
	}

	public List<ActionScope> getActionScopes() {
		return actionScopes;
	}

	public void setActionScopes(List<ActionScope> actionScopes) {
		this.actionScopes = actionScopes;
	}

	public ConfigurationComparison(Configuration configuration1, Configuration configuration2,
			List<ActionScope> actionScopes, Component component1, Component component2) {
		super();
		this.configuration1 = configuration1;
		this.configuration2 = configuration2;
		this.actionScopes = actionScopes;
		this.component1 = component1;
		this.component2 = component2;
	}
	
	
	
	
}
