package de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Configuration extends Serializable {
	public String getName();

	/**
	 * Set the root uuid
	 * 
	 */
	public void setRootUUID(UUID rootUUID);

	/**
	 * Returns the UUID of the root
	 * 
	 */
	public UUID getRootUUID();

	/**
	 * Returns a list with all contained uuids
	 * 
	 */
	public Set<UUID> getUUIDs();

	/**
	 * Add a UUID to the configuration
	 */
	public void addUUID(UUID uuid);

	/**
	 * Add a List of UUIDs to the configuration
	 */
	public void addUUIDs(List<UUID> uuids);

	/**
	 * Remove a UUID from the configuration
	 */
	public void removeUUID(UUID uuid);

	/**
	 * Remove a List of UUIDs from the configuration
	 */
	public void removeUUIDs(List<UUID> uuids);
	
	public List<ComponentConfiguration> getComponentConfigurations();
	
	public void addComponentConfiguraiton(ComponentConfiguration configuration);
	
	

}
