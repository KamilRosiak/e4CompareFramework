package de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view;

import java.util.List;
import java.util.Objects;

import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.Cluster.ChildSelectionModel;
import de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view.Cluster.Variability;
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
	private ObjectProperty<ChildSelectionModel> childSelectionModel;
	private StringProperty childrenDisplay;
	
	private Cluster cluster;
	
	ClusterViewModel(Cluster cluster) {
		this.cluster = cluster;
		this.name = new SimpleStringProperty(cluster.getName());
		this.root = new SimpleBooleanProperty(cluster.isRoot());
		this.variability = new SimpleObjectProperty<>(cluster.getVariability());
		this.childSelectionModel = new SimpleObjectProperty<>(cluster.getChildSelection());
		this.childrenDisplay = new SimpleStringProperty(flattenChildren(cluster.getChildren()));
		
		this.name.addListener((obs, oldVal, newVal) -> {
			this.cluster.setName(newVal);
		});
		this.root.addListener((obs, oldVal, newVal) -> {
			this.cluster.setRoot(newVal);
		});
		this.variability.addListener((obs, oldVal, newVal) -> {
			this.cluster.setVariability(newVal);
		});
		this.childSelectionModel.addListener((obs, oldVal, newVal) -> {
			this.cluster.setChildSelection(newVal);
		});
	}
	
	private String flattenChildren(List<Cluster> children) {
		String concat = children.stream()
				.map(Cluster::getName)
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
		this.cluster.setName(name);
	}
	

	public final BooleanProperty rootProperty() {
		return this.root;
	}
	

	public final boolean isRoot() {
		return this.rootProperty().get();
	}
	

	public final void setRoot(final boolean root) {
		this.rootProperty().set(root);
		this.cluster.setRoot(root);
	}
	
	public final ObjectProperty<Cluster.Variability> variabilityProperty() {
		return this.variability;
	}
	
	public final Variability getVariability() {
		return this.variability.get();
	}
	
	public final void setVariability(final Cluster.Variability variability) {
		this.variability.set(variability);
		this.cluster.setVariability(variability);
	}
	

	public final ObjectProperty<ChildSelectionModel> childSelectionModelProperty() {
		return this.childSelectionModel;
	}
	

	public final ChildSelectionModel getChildSelectionModel() {
		return this.childSelectionModelProperty().get();
	}
	

	public final void setChildSelectionModel(final ChildSelectionModel childSelectionModel) {
		this.childSelectionModelProperty().set(childSelectionModel);
		this.cluster.setChildSelection(childSelectionModel);
	}	

	public final void setChildren(final List<Cluster> children) {
		this.cluster.setChildren(children);
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
	 * @return the cluster
	 */
	public Cluster getCluster() {
		return cluster;
	}

	/**
	 * @param cluster the cluster to set
	 */
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cluster);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ClusterViewModel))
			return false;
		ClusterViewModel other = (ClusterViewModel) obj;
		return Objects.equals(cluster, other.cluster);
	}

	@Override
	public String toString() {
		return "ClusterViewModel [cluster=" + cluster + "]";
	}	
	
	public void style(TableRow<ClusterViewModel> row) {
		ClusterStyler.style(row, this);
	}
	
	
}
