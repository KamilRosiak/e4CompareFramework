package de.tu_bs.cs.isf.e4cf.featuremodel.core.view.constraints.listener;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.error.ErrorListener;

public class ConstraintViewErrorListener implements ErrorListener {

	@Override
	public void onError(FeatureModelViewError error) {
		System.out.println(error);
		
	}

}
