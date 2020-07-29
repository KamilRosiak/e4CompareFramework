package de.tu_bs.cs.isf.e4cf.core.preferences;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.core.preferences.view.PreferenceManagerView;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

@Creatable
public class PreferenceManagerController {
	PreferenceManagerView view;
	
	public PreferenceManagerController(ServiceContainer services) {
		view = new PreferenceManagerView(this,services);
	}
	
}
