package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;

public class Feature extends AbstractFeature implements IFeature, Serializable {
	private static final long serialVersionUID = 6965707151116269397L;

	private GroupVariability groupVariability = GroupVariability.DEFAULT;
	private final List<IFeature> children = new ArrayList<>();
	
	public Feature() {
		super(FDStringTable.DEFAULT_FEATURE_NAME);
	}
	
	public Feature(String name) {
		super(name);
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
	
	@Override
	public List<IFeature> getChildren() {
		return this.children;
	}
	
	@Override
	public void addChild(IFeature child) {
		this.children.add(child);
		child.setParent(this);
	}
	
	@Override
	public void removeChild(IFeature child) {
		this.children.remove(child);
	}

	/**
	 * @return the isAbstract
	 */
	public boolean isAbstract() {
		return this.getArtifactUUIDs().isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(children, groupVariability);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Feature))
			return false;
		Feature other = (Feature) obj;
		return Objects.equals(children, other.children) && groupVariability == other.groupVariability;
	}

	@Override
	public String toString() {
		return "Feature [ name: " + getName() + ", gVar: " + this.groupVariability.toString() + ", " + super.toString() +  "]";
	}
	
}
