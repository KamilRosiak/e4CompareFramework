package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeatureConfiguration implements IFeatureConfiguration, Serializable {
	private static final long serialVersionUID = -4352729706432310608L;
	private String name = "new_configuration";
	private FeatureDiagram diagram;
	private Map<IFeature, Boolean> featureSelection = new HashMap<IFeature, Boolean>();

	public FeatureConfiguration(FeatureDiagram diagram) {
		this.diagram = diagram;
		createFeatureMap(diagram);

	}

	private void createFeatureMap(FeatureDiagram diagram) {
		if (diagram != null)
			diagram.getAllFeatures().forEach(feature -> {
				featureSelection.put(feature, false);
			});
	}

	@Override
	public FeatureDiagram getDiagram() {
		return diagram;
	}

	@Override
	public List<IFeature> getSelectedFeatures() {
		return featureSelection.entrySet().stream().map((a) -> {
			return a.getKey();
		}).collect(Collectors.toList());
	}

	@Override
	public void selectFeature(IFeature feature) {
		if (diagram.contains(feature)) {
			featureSelection.put(feature, true);
		}
	}

	@Override
	public void deselectFeature(IFeature feature) {
		if (diagram.contains(feature)) {
			featureSelection.put(feature, false);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean isSelected(IFeature feature) {
		return featureSelection.get(feature);
	}

	public void setFeatureSelection(Map<IFeature, Boolean> featureSelection) {
		this.featureSelection = featureSelection;
	}

	public Map<IFeature, Boolean> getFeatureSelection() {
		return this.featureSelection;
	}

	public void setDiagram(FeatureDiagram diagram) {
		this.diagram = diagram;
	}

}
