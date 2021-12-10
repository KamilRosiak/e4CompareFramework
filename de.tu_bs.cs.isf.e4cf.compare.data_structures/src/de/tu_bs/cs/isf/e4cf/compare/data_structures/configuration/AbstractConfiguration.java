package de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractConfiguration implements Configuration {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String configName;
	private List<UUID> configuration;

	public AbstractConfiguration(String name) {
		setName(name);
		setConfiguration(new ArrayList<UUID>());
	}

	@Override
	public String getName() {
		return this.configName;
	}

	@Override
	public List<UUID> getConfiguration() {
		return this.configuration;
	}

	public void setName(String configName) {
		this.configName = configName;
	}

	public void setConfiguration(List<UUID> configuration) {
		this.configuration = configuration;
	}

}
