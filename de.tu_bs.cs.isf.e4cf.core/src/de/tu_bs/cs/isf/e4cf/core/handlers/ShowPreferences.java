 
package de.tu_bs.cs.isf.e4cf.core.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.preferences.PreferenceManagerController;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class ShowPreferences {
	@Execute
	public void execute(ServiceContainer services) {
		new PreferenceManagerController(services);
	}
		
}