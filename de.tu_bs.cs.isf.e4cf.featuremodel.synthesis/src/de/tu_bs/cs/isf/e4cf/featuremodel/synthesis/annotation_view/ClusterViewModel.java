package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import java.util.List;
import java.util.Objects;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableRow;

public class ClusterViewModel {
	private StringProperty name;
	private BooleanProperty root;
	private ObjectProperty<Variability> variability;
	private ObjectProperty<GroupVariability> groupVariability;
	private StringProperty childrenDisplay;
	
	private IFeature feature;
	
	ClusterViewModel(IFeature feature) {
		this.feature = feature;
		this.name = new SimpleStringProperty(feature.getName());
		this.root = new SimpleBooleanProperty(feature.isRoot());
		this.variability = new SimpleObjectProperty<>(feature.getVariability());
		this.groupVariability = new SimpleObjectProperty<>(feature.getGroupVariability());
		this.childrenDisplay = new SimpleStringProperty(flattenChildren(feature.getChildren()));
		
		this.name.addListener((obs, oldVal, newVal) -> {
			this.feature.setName(newVal);
		});
		this.root.addListener((obs, oldVal, newVal) -> {
			this.setRoot(newVal);
		});
		this.variability.addListener((obs, oldVal, newVal) -> {
			this.feature.setVariability(newVal);
		});
		this.groupVariability.addListener((obs, oldVal, newVal) -> {
			this.feature.setGroupVariability(newVal);
		});
	}
	
	private String flattenChildren(List<IFeature> children) {
		String concat = children.stream()
				.map(IFeature::getName)
				.reduce("", (a, b) -> String.format("%s %s", a, b));
		return concat.trim();
	}
	

	public final StringProperty nameProperty() {
		return this.name;
	}
	

	public final String getName() {
		return this.nameProperty().get();
	}
	

	public final void setName(final String name) {
		this.nameProperty().set(name);
		this.feature.setName(name);
	}
	

	public final BooleanProperty rootProperty() {
		return this.root;
	}
	

	public final boolean isRoot() {
		return this.rootProperty().get();
	}
	

	public final void setRoot(final boolean root) {
		this.rootProperty().set(root);
		this.feature.setIsRoot(root);
		
		if (root && this.feature.getParent().isPresent()) {
			IFeature parent = this.feature.getParent().get();
			parent.removeChild(this.getFeature());
		}
		this.feature.setParent(null);
	}
	
	public final ObjectProperty<Variability> variabilityProperty() {
		return this.variability;
	}
	
	public final Variability getVariability() {
		return this.variability.get();
	}
	
	public final void setVariability(final Variability variability) {
		this.variability.set(variability);
		this.feature.setVariability(variability);
	}
	

	public final ObjectProperty<GroupVariability> childSelectionModelProperty() {
		return this.groupVariability;
	}
	

	public final GroupVariability getGroupVariability() {
		return this.childSelectionModelProperty().get();
	}
	

	public final void setGroupVariability(final GroupVariability groupVar) {
		this.childSelectionModelProperty().set(groupVar);
		this.feature.setGroupVariability(groupVar);
	}	

	public final void setChildren(final List<IFeature> children) {
		this.feature.getChildren().clear();
		this.feature.getChildren().addAll(children);
		for (IFeature child : children) {
			child.setParent(this.feature);
		}
		this.childrenDisplay.setValue(flattenChildren(children));
	}
	
	public final StringProperty childrenDisplayProperty() {
		return this.childrenDisplay;
	}
	

	public final String getChildrenDisplay() {
		return this.childrenDisplayProperty().get();
	}
	

	public final void setChildrenDisplay(final String childrenDisplay) {
		this.childrenDisplayProperty().set(childrenDisplay);
	}

	/**
	 * @return the feature
	 */
	public IFeature getFeature() {
		return feature;
	}

	/**
	 * @param feature the feature to set
	 */
	public void setFeature(IFeature feature) {
		this.feature = feature;
	}

	@Override
	public int hashCode() {
		return Objects.hash(feature);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ClusterViewModel))
			return false;
		ClusterViewModel other = (ClusterViewModel) obj;
		return Objects.equals(feature, other.feature);
	}

	@Override
	public String toString() {
		return "ClusterViewModel [cluster=" + feature + "]";
	}	
	
	public void style(TableRow<ClusterViewModel> row) {
		ClusterStyler.style(row, this);
	}
	
	
}
