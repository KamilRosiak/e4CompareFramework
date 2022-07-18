package de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration;

import java.io.Serializable;
import java.util.UUID;

/**
 * Description of a component configuration
 * 
 * @author kamil
 *
 */
public class ComponentConfiguration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2136232608235710763L;
	public UUID parentUUID;
	public UUID componentUUID;
	public Configuration configuration;
}
