package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import javafx.scene.paint.Color;

public interface IFeature {
	UUID getUuid();
		
	String getName();
	void setName(String name);
	
	boolean isRoot();
	void setIsRoot(boolean isRoot);
	
	Optional<Color> getColor();
	void setColor(Color color);
	
	Set<Configuration> getConfigurations();
	
	Set<UUID> getArtifactUUIDs();
		
	List<IFeature> getChildren();
	void addChild(IFeature child);
	void removeChild(IFeature child);
	
	Optional<IFeature> getParent();
	void setParent(IFeature parent);
	
	Variability getVariability();
	void setVariability(Variability var);
	
	GroupVariability getGroupVariability();
	void setGroupVariability(GroupVariability groupVar);
	
	default boolean isAbstract() {
		return false;
	}
	
	default boolean isComponent() {
		return false;
	}
	
	String getDescription();
	void setDescription(String description);
	
	@Override
	public boolean equals(Object other);
	@Override
	public int hashCode();
	
	public default int getDepth(IFeature feature, int depth) {
		if (this.equals(feature)) {
			return depth;
		}
		for (IFeature child : this.getChildren()) {
			int childDepth = child.getDepth(feature, depth + 1);
			if (childDepth != -1) {
				return childDepth;
			}
		}
		return -1;
	}
	
}
