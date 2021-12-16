package de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface Configuration extends Serializable {
	public String getName();
	
	public List<UUID> getConfiguration();
	
	public default void addUUID(UUID uuid) {
		getConfiguration().add(uuid);
	}
	
	public default void removeUUID(UUID uuid) {
		getConfiguration().remove(uuid);
	}
}
