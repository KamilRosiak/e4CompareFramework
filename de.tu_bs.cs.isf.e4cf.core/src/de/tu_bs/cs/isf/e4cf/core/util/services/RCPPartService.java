package de.tu_bs.cs.isf.e4cf.core.util.services;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.IPartListener;

@Creatable 
@Singleton
public class RCPPartService {
	@Inject public EPartService partService;
	@Inject public EModelService modelService;
	@Inject public MApplication app;
	@Inject public IThemeEngine themeEngine;

	/**
	 * This method shows a part by id.
	 */
	public void showPart(final String id) {
		MPart part = partService.findPart(id);
		if(part != null) {
			String parentID = part.getParent().getElementId();
			part.setVisible(true);
			partService.activate(part);
			if(part instanceof MPartStack) {
				MPartStack partStack = (MPartStack) partService.findPart(parentID);
				partStack.setSelectedElement(part);
			}
		}
	}
	
	/**
	 * Getter for a specific part.
	 * 
	 * @param id
	 * @return part if there is a part with this id, otherwise null
	 */
	public MPart getPart(final String id) {
		MPart part = partService.findPart(id);
		return part;
	}
	
	public void registerPartListener(IPartListener partListener) {
		if (partListener != null) {
			partService.addPartListener(partListener);			
		}
	}
	
	public void deregisterPartListener(IPartListener partListener) {
		if (partListener != null) {
			partService.removePartListener(partListener);
		}
	}
	
	/**
	 * Sets a part to be rendered.
	 */
	public void setPartToBeRendered(final String partID, Boolean toBeRendered) {
		  MUIElement el = modelService.find(partID, app);
		  if (el != null) {
		    el.setToBeRendered(toBeRendered);
		  }
	}
	
	/**
	 * Checks a part for visibility.
	 * 
	 * @param partID
	 * @return <tt>true</tt> if the part is visible
	 */
	public boolean isPartVisible(String partID) {
		MPart part = partService.findPart(partID);
		if (part != null) {
			return partService.isPartVisible(part);			
		}
		return false;
	}
	
	public void setTrimToolBarVisibility(boolean visible) {
		MTrimmedWindow window = (MTrimmedWindow) modelService.find("de.tu_bs.cs.isf.e4cf.core.window.main", app);
		MTrimBar trimBar = modelService.getTrim(window, SideValue.TOP);
		trimBar.setVisible(visible);
	}
	
	/** This method switches the current theme. 
	 */
	public void switchTheme(String themeID) {
		themeEngine.setTheme(themeID, true);
	}
	
}
