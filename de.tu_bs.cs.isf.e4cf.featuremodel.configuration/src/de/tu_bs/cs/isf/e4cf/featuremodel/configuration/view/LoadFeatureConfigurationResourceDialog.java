package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.view;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.AbstractLoadResourceDialog;

public class LoadFeatureConfigurationResourceDialog extends AbstractLoadResourceDialog {

	public LoadFeatureConfigurationResourceDialog(String title, double width, double rowHeight) {
		super(title, width, rowHeight);
	}

	@Override
	protected void addResourceRows() {
		addResourceRow("Feature Configuration File", "Load");
	}

}
