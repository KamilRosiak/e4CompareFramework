package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;

public class ComponentFeature extends AbstractFeature implements IComponentFeature {
	private static final long serialVersionUID = -8645927165765858782L;
	private FeatureDiagram diagram;
	private IFeatureConfiguration config;

	public ComponentFeature() {
		this(FDStringTable.DEFAULT_COMPONENT_FEATURE_NAME);
	}

	public ComponentFeature(String name) {
		super(name);
		this.diagram = new FeatureDiagram(name, new Feature());
		this.config = new FeatureConfiguration(this.diagram);
	}

	public ComponentFeature(FeatureDiagram diagram) {
		this();
		setDiagram(diagram);
		setName(diagram.getName());
	}

	@Override
	public FeatureDiagram getDiagram() {
		return diagram;
	}

	@Override
	public void setDiagram(FeatureDiagram diagram) {
		this.diagram = diagram;
		this.config = new FeatureConfiguration(diagram);
	}

	@Override
	public IFeatureConfiguration getConfiguration() {
		return config;
	}

}
