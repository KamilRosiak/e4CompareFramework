package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import javafx.scene.paint.Color;

public abstract class AbstractFeature implements IFeature, Serializable {
	private static final long serialVersionUID = -8018048333363966570L;
	private boolean isAbstract = true;
	private boolean isComponent = false;
	private final UUID uuid = UUID.randomUUID();
	private String name;
	private boolean isRoot = false;
	private String description;
	private Variability variability = Variability.OPTIONAL;
	private IFeature parent = null;
	private FeatureColor color = FeatureColor.Unset;
	private final Set<Configuration> configurations = new HashSet<>();
	private final Set<UUID> artifactUUIDs = new HashSet<>();

	@Override
	public void setComponent(boolean isComponent) {
		this.isComponent = isComponent;
	}

	@Override
	public boolean isComponent() {
		return this.isComponent;
	}

	@Override
	public boolean isAbstract() {
		return this.isAbstract;
	}

	@Override
	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

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
	public boolean isRoot() {
		return isRoot;
	}

	@Override
	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	@Override
	public Optional<IFeature> getParent() {
		if (parent == null) {
			return Optional.empty();
		} else {
			return Optional.of(parent);
		}
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
	public Optional<Color> getColor() {
		if (color.equals(FeatureColor.Unset)) {
			return Optional.empty();
		} else {
			return Optional.of(color.get());
		}
	}

	@Override
	public void setColor(Color color) {
		this.color = new FeatureColor(color);
	}

	@Override
	public Set<Configuration> getConfigurations() {
		return this.configurations;
	}

	@Override
	public Set<UUID> getArtifactUUIDs() {
		return this.artifactUUIDs;
	}

	@Override
	public int hashCode() {
		if (this.getParent().isPresent()) {
			return Objects.hash(artifactUUIDs, color, configurations, description, parent.getUuid(), name, uuid, isRoot,
					variability);
		} else {
			return Objects.hash(artifactUUIDs, color, configurations, description, name, uuid, isRoot, variability);
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof AbstractFeature))
			return false;
		AbstractFeature other = (AbstractFeature) obj;
		return Objects.equals(artifactUUIDs, other.artifactUUIDs) && Objects.equals(color, other.color)
				&& Objects.equals(configurations, other.configurations)
				&& Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(parent, other.parent) && Objects.equals(uuid, other.uuid)
				&& variability == other.variability && isRoot == other.isRoot();
	}

	@Override
	public String toString() {
		return "AbstractFeature [uuid=" + uuid + ", name=" + name + ", description=" + description + ", variability="
				+ variability + ", parent=" + parent + ", color=" + color + ", configurations=" + configurations
				+ ", artifactUUIDs=" + artifactUUIDs + "]";
	}

}
