package de.tu_bs.cs.isf.e4cf.core.preferences.interfaces;

import org.eclipse.swt.custom.CTabFolder;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public interface IPreferencePage {
	/**
	 * This method returns the composite that will be shown in the PrferenceManager on the right side.
	 */
	public void createPage(CTabFolder parent ,ServiceContainer services);
	
	/**
	 * This method have to store all values from the provided preference page.
	 */
	public void store();
}
