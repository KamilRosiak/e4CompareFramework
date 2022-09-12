package de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration;

import java.io.Serializable;
import java.util.UUID;

/**
 * Description of a component configuration
 * 
 * @author kamil
 *
 */
public class CloneConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2136232608235710763L;
	public UUID parentUUID;
	public UUID componentUUID;
	public Configuration configuration = new ConfigurationImpl("clone config");
	public UUID getParentUUID() {
		return parentUUID;
	}
	public void setParentUUID(UUID parentUUID) {
		this.parentUUID = parentUUID;
	}
	public UUID getComponentUUID() {
		return componentUUID;
	}

	public void setComponentUUID(UUID componentUUID) {
		this.componentUUID = componentUUID;
	}
	public Configuration getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
