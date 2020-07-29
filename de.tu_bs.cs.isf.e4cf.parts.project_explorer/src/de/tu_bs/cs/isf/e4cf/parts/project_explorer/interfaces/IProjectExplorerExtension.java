package de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;

public interface IProjectExplorerExtension {
	/**
	 * This method returns the custom icon for the implemented file type.
	 * @return
	 */
	public Image getIcon(RCPImageService imageService);
	
	/**
	 * This method performs the double click action for the implemented file type.
	 * @return
	 */
	public void execute(ServiceContainer container);	
}
