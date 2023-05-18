package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class AbstractFeature implements IFeature, Serializable {
	private static final long serialVersionUID = -8018048333363966570L;
	
	private final UUID uuid = UUID.randomUUID();
	private String name;
	private String description;
	private Variability variability = Variability.OPTIONAL;
	private IFeature parent = null;
	
	public AbstractFeature(String name) {
		this.name = name;
	}
	
	@Override
	public UUID getUuid() {
		return uuid;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public IFeature getParent() {
		return parent;
	}

	@Override
	public void setParent(IFeature parent) {
		this.parent = parent;
		
	}

	@Override
	public Variability getVariability() {
		return variability;
	}

	@Override
	public void setVariability(Variability var) {
		this.variability = var;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name, parent, uuid, variability);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AbstractFeature))
			return false;
		AbstractFeature other = (AbstractFeature) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(parent, other.parent) && Objects.equals(uuid, other.uuid)
				&& variability == other.variability;
	}

	@Override
	public String toString() {
		return "AbstractFeature [uuid=" + uuid + ", name=" + name + ", description=" + description + ", variability="
				+ variability + ", parent=" + parent + "]";
	}

	

}
