package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.util.Objects;
import java.util.UUID;

public class FeatureDiagram {
	private String name;
	private final UUID uuid = UUID.randomUUID();
	private Feature root;
	
	public FeatureDiagram(String name, Feature rootFeature) {
		this.name = name;
		this.root = rootFeature;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the rootFeature
	 */
	public Feature getRoot() {
		return root;
	}

	/**
	 * @param rootFeature the rootFeature to set
	 */
	public void setRoot(Feature rootFeature) {
		this.root = rootFeature;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, root, uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FeatureDiagram))
			return false;
		FeatureDiagram other = (FeatureDiagram) obj;
		return Objects.equals(name, other.name) && Objects.equals(root, other.root)
				&& Objects.equals(uuid, other.uuid);
	}

	@Override
	public String toString() {
		return "FeatureDiagram [name=" + name + ", uuid=" + uuid + ", rootFeature=" + root + "]";
	}
	
	
	

}
