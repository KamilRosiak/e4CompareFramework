package de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractConfiguration implements Configuration {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String configName;
	private Set<UUID> configuration;
	private UUID root;
	private List<ComponentConfiguration> componentConfigurations = new ArrayList<ComponentConfiguration>();

	public AbstractConfiguration(String name) {
		setName(name);
		setConfiguration(new HashSet<UUID>());
	}

	public List<ComponentConfiguration> getComponentConfigurations() {
		return componentConfigurations;
	}

	public void addComponentConfiguraiton(ComponentConfiguration configuration) {
		componentConfigurations.add(configuration);
	}

	/**
	 * Returns the UUID of the root
	 * 
	 */
	@Override
	public UUID getRootUUID() {
		return root;
	}

	@Override
	public void setRootUUID(UUID rootUUID) {
		this.root = rootUUID;
	}

	@Override
	public String getName() {
		return this.configName;
	}

	public void setName(String configName) {
		this.configName = configName;
	}

	public void setConfiguration(Set<UUID> configuration) {
		this.configuration = configuration;
	}

	@Override
	public Set<UUID> getUUIDs() {
		return configuration;
	}

	@Override
	public void addUUID(UUID uuid) {
		configuration.add(uuid);
	}

	@Override
	public void addUUIDs(List<UUID> uuids) {
		configuration.addAll(uuids);
	}

	@Override
	public void removeUUID(UUID uuid) {
		configuration.remove(uuid);
	}

	@Override
	public void removeUUIDs(List<UUID> uuids) {
		uuids.forEach(uuid -> {
			removeUUID(uuid);
		});
	}
}
