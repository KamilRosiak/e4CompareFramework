package de.tu_bs.cs.isf.e4cf.family_model_view.prototype.util;

import de.tu_bs.cs.isf.e4cf.family_model_view.prototype.LabelProvider;

public class NullLabelProvider implements LabelProvider {

	@Override
	public String getLabel(Object artefact) {
		return null;
	}

}
