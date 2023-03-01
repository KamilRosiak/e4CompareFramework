package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Feature implements IFeature {
	private String name;
	private final UUID uuid = UUID.randomUUID();;
	private Variability variability = Variability.MANDATORY;
	private GroupVariability groupVariability = GroupVariability.OR;
	private final List<IFeature> children = new ArrayList<>();
	
	public Feature(String name) {
		this.name = name;
	}
	
	public Feature(String name, Variability variability, GroupVariability groupVariability) {
		this(name);
		this.variability = variability;
		this.groupVariability = groupVariability;
	}

	/**
	 * @return the name
	 */
	@Override
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
	 * @return the variability
	 */
	@Override
	public Variability getVariability() {
		return variability;
	}

	/**
	 * @param variability the variability to set
	 */
	public void setVariability(Variability variability) {
		this.variability = variability;
	}

	/**
	 * @return the groupVariability
	 */
	@Override
	public GroupVariability getGroupVariability() {
		return groupVariability;
	}

	/**
	 * @param groupVariability the groupVariability to set
	 */
	public void setGroupVariability(GroupVariability groupVariability) {
		this.groupVariability = groupVariability;
	}

	/**
	 * @return the uuid
	 */
	@Override
	public UUID getUuid() {
		return uuid;
	}
	
	@Override
	public List<IFeature> getChildren() {
		return this.children;
	}
	
	@Override
	public void addChild(IFeature child) {
		this.children.add(child);
	}
	
	@Override
	public void removeChild(IFeature child) {
		this.children.remove(child);
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupVariability, name, uuid, variability);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Feature))
			return false;
		Feature other = (Feature) obj;
		return groupVariability == other.groupVariability && Objects.equals(name, other.name)
				&& Objects.equals(uuid, other.uuid) && variability == other.variability;
	}

	@Override
	public String toString() {
		return "Feature [name=" + name + ", uuid=" + uuid + ", variability=" + variability + ", groupVariability="
				+ groupVariability + "]";
	}
	
	
}
