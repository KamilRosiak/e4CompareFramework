package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.SyntaxGroup;

enum ChildSelectionModel {
	ALTERNATIVE,
	OR, 
	DEFAULT;
}

public class Cluster implements Serializable {
	private static final long serialVersionUID = 3304554399330246573L;
	private String name;
	private SyntaxGroup syntaxGroup;
	private boolean isRoot = false;
	private boolean isMandatory = false;
	private ChildSelectionModel childSelection = ChildSelectionModel.DEFAULT;
	private List<Cluster> children = new ArrayList<>();
	
	public Cluster(SyntaxGroup group) {
		this.syntaxGroup = group;
		this.name = group.getNormalizedName();
	}

	/**
	 * @return the syntaxGroup
	 */
	public SyntaxGroup getSyntaxGroup() {
		return syntaxGroup;
	}

	/**
	 * @param syntaxGroup the syntaxGroup to set
	 */
	public void setSyntaxGroup(SyntaxGroup syntaxGroup) {
		this.syntaxGroup = syntaxGroup;
	}

	/**
	 * @return the isRoot
	 */
	public boolean isRoot() {
		return isRoot;
	}

	/**
	 * @param isRoot the isRoot to set
	 */
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	/**
	 * @return the isMandatory
	 */
	public boolean isMandatory() {
		return isMandatory;
	}

	/**
	 * @param isMandatory the isMandatory to set
	 */
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
	public boolean isAbstract() {
		return this.syntaxGroup.getUuids().size() == 0;
	}

	/**
	 * @return the childSelection
	 */
	public ChildSelectionModel getChildSelection() {
		return childSelection;
	}

	/**
	 * @param childSelection the childSelection to set
	 */
	public void setChildSelection(ChildSelectionModel childSelection) {
		this.childSelection = childSelection;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addChild(Cluster child) {
		this.children.add(child);
	}
	
	public boolean removeChild(Cluster child) {
		return this.children.remove(child);
	}
	
	void setChildren(List<Cluster> children) {
		this.children = children;
	}
	
	public boolean isParentOf(Cluster cluster) {
		return this.children.contains(cluster);
	}
	
	public List<Cluster> getChildren() {
		return this.children;
	}

	@Override
	public int hashCode() {
		return Objects.hash(childSelection, children, isMandatory, isRoot, name, syntaxGroup);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Cluster))
			return false;
		Cluster other = (Cluster) obj;
		return childSelection == other.childSelection && Objects.equals(children, other.children)
				&& isMandatory == other.isMandatory && isRoot == other.isRoot && Objects.equals(name, other.name)
				&& Objects.equals(syntaxGroup, other.syntaxGroup);
	}

	@Override
	public String toString() {
		return "Cluster[" + name + "]";
	}
	
	

}
